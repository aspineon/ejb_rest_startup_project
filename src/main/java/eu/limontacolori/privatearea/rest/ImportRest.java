package eu.limontacolori.privatearea.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.limontacolori.privatearea.exceptions.jaxrs.BadImportException;
import eu.limontacolori.privatearea.services.importers.ImportCustomersService;
import eu.limontacolori.privatearea.services.importers.ImportDocumentsService;


@Stateless
@Path("import")
public class ImportRest extends BaseRest {
	
	@EJB
	ImportCustomersService customerImporter;
	
	@EJB
	ImportDocumentsService documentImporter;
	
	@Path("customers")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response importCustomers() throws BadImportException {
		this.customerImporter.importData(null);
		return Response.ok().build();
	}
	
	@Path("documents")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response importDocuments() throws BadImportException {
		this.documentImporter.importData(null);
		return Response.ok().build();
	}
	
}
