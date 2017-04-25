package eu.limontacolori.privatearea.rest.client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import eu.limontacolori.privatearea.exceptions.jaxrs.BadImportException;

public class RestEasyImportClientCustomer extends RestEasyImportClient {
	
	public RestEasyImportClientCustomer() throws BadImportException {
		super();
		// TODO Auto-generated constructor stub
	}


	private final String CUSTOMER_POSTFIX = "/cli";
	
	
	@Override
	public Response post() {
		ResteasyWebTarget target = client.target(BASE_URL + CUSTOMER_POSTFIX); 
        return target.request().post(Entity.form(form));
	}
	
}
