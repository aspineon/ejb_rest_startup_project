package eu.limontacolori.privatearea.services.importers;

import java.util.Date;
import java.util.HashMap;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import eu.limontacolori.privatearea.entities.Customer;
import eu.limontacolori.privatearea.entities.User;
import eu.limontacolori.privatearea.exceptions.jaxrs.BadImportException;
import eu.limontacolori.privatearea.exceptions.jaxrs.GenericRuntimeException;
import eu.limontacolori.privatearea.rest.client.ImportType;
import eu.limontacolori.privatearea.rest.client.RestEasyClientFactory;
import eu.limontacolori.privatearea.rest.client.RestEasyImportClient;
import eu.limontacolori.privatearea.rest.dto.ImportCustomerDto;
import eu.limontacolori.privatearea.rest.dto.ImportCustomersDto;
import eu.limontacolori.privatearea.rest.dto.mappers.customer.CustomerMapper;
import eu.limontacolori.privatearea.rest.dto.mappers.user.UserMapper;
import eu.limontacolori.privatearea.services.AgentService;
import eu.limontacolori.privatearea.services.CustomerService;
import eu.limontacolori.privatearea.services.UserService;



@Stateless
public class ImportCustomersService extends ImportService<ImportCustomersDto> {	
	
	@Inject
	CustomerService customerService;
	
	@Inject
	UserService userService;
	
	@Inject
	AgentService agentService;
	
	@Inject 
	CustomerMapper custDtoMapper;
	
	@Inject
	UserMapper userMapper;

	@Override
	public void importData(HashMap<String,String> params) throws BadImportException {
		ImportCustomersDto customers = performRemoteCall();
    	logger.info("{} customers retrieved", customers.data.size());
		customers.data.forEach(dto -> {
			try {
				importCustomer(dto);
			} catch (Exception exc) {
				logger.fatal("Error importing customer {}", dto);
			}
		});
  	}

	private ImportCustomersDto performRemoteCall() throws BadImportException {
		RestEasyImportClient client = RestEasyClientFactory.getInstance(ImportType.CUSTOMER);
        Response response = client.post();
        isResponseValid(response);
        ImportCustomersDto customers = response.readEntity(ImportCustomersDto.class);
        if(!isImportDtoValid(customers))
        	throw new BadImportException("Import customer dto is invalid: " + customers);
		return customers;
	}
	
	@Transactional
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	private void importCustomer(ImportCustomerDto dto) throws GenericRuntimeException {
		Customer customer = custDtoMapper.from(dto);
		customer.setCreationDate(new Date());
		if(customerService.getCustomer(customer.getId()) == null) {
			customerService.insertCustomer(customer);
			logger.trace("Customer {} inserted",customer);
		
			User user = userMapper.from(dto);
			user.setCustomer(customer);
			userService.addUser(user);
			logger.trace("User {} inserted",user);
		} else {
			logger.trace("Customer {} already created previously", customer);
		}
	}

	@Override
	public ImportCustomersDto importDataEntity(HashMap<String,String> params) throws BadImportException {
		return performRemoteCall();
	}
	
}
