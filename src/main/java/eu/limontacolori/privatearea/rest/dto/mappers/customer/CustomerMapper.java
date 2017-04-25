package eu.limontacolori.privatearea.rest.dto.mappers.customer;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import eu.limontacolori.privatearea.entities.Agent;
import eu.limontacolori.privatearea.entities.Customer;
import eu.limontacolori.privatearea.rest.dto.CustomerDto;
import eu.limontacolori.privatearea.rest.dto.ImportCustomerDto;
import eu.limontacolori.privatearea.services.AgentService;

public class CustomerMapper {
	
	@Inject
	AgentService agentService;
	
	public Customer from(ImportCustomerDto dto) {
		if(dto != null) {
			Customer customer = new Customer();
			customer.setId(dto.CODICE);
			customer.setAddress(dto.INDIRIZZO);
			if(!StringUtils.isEmpty(dto.AGENTE)) {
				Agent agent = agentService.getAgent(dto.AGENTE);
				if(agent == null) {
					agent = new Agent();
					agent.setAgentId(dto.AGENTE);
					agent.setCreationDate(new Date());
					agent.setDescription(dto.DESAGENTE);
				}
				customer.setAgent(agent);
			}
			customer.setCap(dto.CAP);
			customer.setCity(dto.LOCALITA);
			customer.setCodFisc(dto.CODFISC);
			customer.setEmail(dto.MAIL);
			customer.setName(dto.RAGSOC1);
			customer.setNameExt(dto.RAGSOC2);
			customer.setpIva(dto.PIVA);
			customer.setProvince(dto.PROV);
			customer.setTelephone(dto.TELEPHONE);
			return customer;
		} else {
			return null;
		}
	}
	
	public CustomerDto to(Customer cust) {
		if (cust == null)
			return null;
		else {
			CustomerDto dto = new CustomerDto();
			dto.address = cust.getAddress();
			dto.cap = cust.getCap();
			dto.city = cust.getCity();
			dto.codFisc = cust.getCodFisc();
			dto.creationDate = cust.getCreationDate();
			dto.email = cust.getEmail();
			dto.id = cust.getId();
			dto.name = cust.getName();
			dto.nameExt = cust.getNameExt();
			dto.pIva = cust.getpIva();
			dto.province = cust.getProvince();
			dto.telephone = cust.getTelephone();
			return dto;
		}
	}
	
}
