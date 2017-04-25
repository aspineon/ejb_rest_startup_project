package eu.limontacolori.privatearea.rest.client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import eu.limontacolori.privatearea.exceptions.jaxrs.BadImportException;

public class RestEasyImportClientDocumentPdf extends RestEasyImportClient {

	public RestEasyImportClientDocumentPdf() throws BadImportException {
		super();
	}
	
	private final String DOC_POSTFIX = "/pdf";
	
	@Override
	public Response post() {
		ResteasyWebTarget target = client.target(BASE_URL + DOC_POSTFIX); 
        return target.request().post(Entity.form(form));
	}
	
}
