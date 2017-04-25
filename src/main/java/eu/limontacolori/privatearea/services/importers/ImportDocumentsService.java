package eu.limontacolori.privatearea.services.importers;

import java.util.HashMap;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import eu.limontacolori.privatearea.entities.Document;
import eu.limontacolori.privatearea.entities.OrphanDocument;
import eu.limontacolori.privatearea.exceptions.jaxrs.BadImportException;
import eu.limontacolori.privatearea.exceptions.jaxrs.GenericRuntimeException;
import eu.limontacolori.privatearea.rest.client.ImportType;
import eu.limontacolori.privatearea.rest.client.RestEasyClientFactory;
import eu.limontacolori.privatearea.rest.client.RestEasyImportClient;
import eu.limontacolori.privatearea.rest.dto.ImportDocumentDto;
import eu.limontacolori.privatearea.rest.dto.ImportDocumentsDto;
import eu.limontacolori.privatearea.rest.dto.mappers.document.DocumentMapper;
import eu.limontacolori.privatearea.rest.dto.mappers.orphandocument.OrphanDocumentMapper;
import eu.limontacolori.privatearea.services.DocumentService;
import eu.limontacolori.privatearea.services.OrphanDocumentService;

@Stateless
public class ImportDocumentsService extends ImportService<ImportDocumentsDto> {

	@Inject
	DocumentMapper dtoMapper;
	
	@Inject
	OrphanDocumentMapper orphMapper;
	
	@Inject
	DocumentService docService;
	
	@Inject
	OrphanDocumentService orphService;
	
	public static final String SEARCH_DATE_KEY = "amg";
	public static final String SEARCH_DATE = "20160101";

	@Override
	public void importData(HashMap<String,String> params) throws BadImportException {
		ImportDocumentsDto documents = performRemoteCall();
    	documents.data.forEach(dto -> {
    		try {
    			importDocument(dto);
			} catch (Exception exc) {
				logger.fatal("Error importing document {}", dto);
			}
    	});
	}

	private ImportDocumentsDto performRemoteCall() throws BadImportException {
		// Call Params
		HashMap<String,String> params = new HashMap<>();
		params.put(SEARCH_DATE_KEY, SEARCH_DATE);
		logger.info("Import documents started, calling server...");
		RestEasyImportClient client = RestEasyClientFactory.getInstance(ImportType.DOCUMENTS).setFormParams(params);
        Response response = client.post();
        logger.info("Server has been called");
        isResponseValid(response);
        logger.info("Response is valid, starting import data");
        ImportDocumentsDto documents = response.readEntity(ImportDocumentsDto.class);
        if(!isImportDtoValid(documents)) {
        	throw new BadImportException("Import document dto is invalid: " + documents);
        }
		return documents;
	}
	
	public boolean isSingleDocDtoValid(ImportDocumentDto dto) {
		
		if ((dto.NR_DOC == null) || (dto.DT_DOC == null) || (dto.TIPO == null)) {
			return false;
		}
			
		return true;
	}

	@Transactional
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	private void importDocument(ImportDocumentDto dto) throws GenericRuntimeException {
		if(!isSingleDocDtoValid(dto)) {
			throw new GenericRuntimeException("Single ImportDocumentDto " + dto + " is not valid");
		}
		Document doc = dtoMapper.from(dto);
		if(doc.getCustomer() != null) {
			if(docService.getDocument(doc.getDocId()) == null) {
				docService.insertDocument(doc);
			} else {
				logger.info("Document {} already present in database, skipping...", doc);
			}
		} else  {
			logger.info("Dto {} has no customer associated. Skipping...", dto);
			try {
				handleOrphanDocument(doc,dto);
			} catch (Exception exc) {
				logger.warn("Can't save orphan {}", dto);
			}
		}
	}
	
	private void handleOrphanDocument(Document doc, ImportDocumentDto dto) {
		OrphanDocument orph = orphMapper.from(doc, dto);
		if(orphService.getDocument(doc.getDocId()) == null) {
			orphService.insertDocument(orph);
			logger.warn("New orphan inserted for dto {}", dto);
		} else {
			logger.info("Orphan {} already inserted. Skipping...", orph);
		}
	}

	@Override
	public ImportDocumentsDto importDataEntity(HashMap<String,String> params) throws BadImportException {
		return performRemoteCall();
	}
	
}
