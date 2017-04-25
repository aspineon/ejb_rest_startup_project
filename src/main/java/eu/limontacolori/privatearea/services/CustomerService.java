package eu.limontacolori.privatearea.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.limontacolori.privatearea.dao.CustomerDAO;
import eu.limontacolori.privatearea.entities.Customer;


@Stateless
public class CustomerService {

	Logger logger = LogManager.getLogger(CustomerService.class);
	
	@Inject
	CustomerDAO customerDAO;
	
	public void insertCustomer(Customer customer) {
		customerDAO.insert(customer);
	}
	
	public Customer getCustomer(String id) {
		return customerDAO.getById(id);
	}
	
}
