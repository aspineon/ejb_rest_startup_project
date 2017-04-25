package eu.limontacolori.privatearea.rest;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.limontacolori.privatearea.auth.RequiredRole;
import eu.limontacolori.privatearea.auth.TrackUser;
import eu.limontacolori.privatearea.entities.Customer;
import eu.limontacolori.privatearea.entities.Document;
import eu.limontacolori.privatearea.entities.DocumentId;
import eu.limontacolori.privatearea.entities.User;
import eu.limontacolori.privatearea.entities.enums.Role;
import eu.limontacolori.privatearea.exceptions.jaxrs.BadImportException;
import eu.limontacolori.privatearea.rest.dto.DocumentDto;
import eu.limontacolori.privatearea.rest.dto.ImportDocumentPdfDto;
import eu.limontacolori.privatearea.rest.dto.ListDocumentsReqDto;
import eu.limontacolori.privatearea.rest.dto.PaginationDto;
import eu.limontacolori.privatearea.services.CustomerService;
import eu.limontacolori.privatearea.services.DocumentService;
import eu.limontacolori.privatearea.services.importers.ImportDocumentService;

@Stateless
@Path("documents")
public class DocumentRest extends BaseRest{
	
	@Inject
	DocumentService docService;
	
	@Inject 
	ImportDocumentService importDocService;
	
	@Inject 
	CustomerService custService;
	
	Logger logger = LogManager.getLogger(DocumentRest.class);
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@TrackUser
	@RequiredRole({Role.ADMIN, Role.USER})
	public PaginationDto<DocumentDto> listDocuments(ListDocumentsReqDto listDocReq) {
		User user = getLoggedUserEntity();
		Customer customer = null;
		if((user.isAdmin()) && (StringUtils.isNotEmpty(listDocReq.customerId))) 
			customer = custService.getCustomer(listDocReq.customerId);
		else
			customer = user.getCustomer();
		SimpleDateFormat fmt = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		Date dateStart = null;
		Date dateEnd = null;
		try {
			dateStart = (StringUtils.isEmpty(listDocReq.dateStart)) ? null : fmt.parse(listDocReq.dateStart);
			dateEnd = (StringUtils.isEmpty(listDocReq.dateEnd)) ? null : fmt.parse(listDocReq.dateEnd);
		} catch (ParseException pe) {
			logger.fatal("Bad date format in list documents req");
			throw new BadRequestException("Bad date format in list documents req");
		}
		return docService.listDocuments(customer, dateStart, dateEnd, listDocReq.pagination);
	}

	@GET
	@Path("document")
	@Produces("application/pdf")
	@TrackUser
	@RequiredRole({Role.ADMIN, Role.USER})
	public Response getDocument(@QueryParam("docNum") String docNum, @QueryParam("docYear") String docYear, @QueryParam("docType") String docType) throws BadImportException {
		User user = getLoggedUserEntity();
		
		// Cerco documento sul db
		DocumentId docId = new DocumentId();
		docId.setDocNum(docNum);
		docId.setDocYear(docYear);
		docId.setDocType(docType);
		Document doc = docService.getDocument(docId);
		
		if ((!user.isAdmin()) && (user.getCustomer() != doc.getCustomer())) {
			throw new ForbiddenException("User not authorized");
		}
		
		// Ottengo documento online
		ImportDocumentPdfDto docDto = importDocService.importSingleDocument(doc);
		Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(docDto.data);
        ResponseBuilder response = Response.ok(new ByteArrayInputStream(decodedBytes));
        response.header("Content-Disposition", "attachment; filename=" + docDto.filename);
        return response.build();
		
	}
	
	


	
}
