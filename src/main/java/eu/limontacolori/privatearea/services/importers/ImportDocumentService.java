package eu.limontacolori.privatearea.services.importers;

import java.util.HashMap;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.hibernate.tool.hbm2x.StringUtils;

import eu.limontacolori.privatearea.entities.Document;
import eu.limontacolori.privatearea.exceptions.jaxrs.BadImportException;
import eu.limontacolori.privatearea.rest.client.ImportType;
import eu.limontacolori.privatearea.rest.client.RestEasyClientFactory;
import eu.limontacolori.privatearea.rest.client.RestEasyImportClient;
import eu.limontacolori.privatearea.rest.dto.ImportDocumentPdfDto;

@Stateless
public class ImportDocumentService extends ImportService<ImportDocumentPdfDto> {
	

	public ImportDocumentPdfDto importSingleDocument(Document doc) throws BadImportException {
		HashMap<String,String> params = new HashMap<>();
		String filename = "document.pdf";
		if(doc != null) {
			params.put("soc", doc.getSoc());
			params.put("anno", doc.getDocId().getDocYear().substring(0, 4));
			params.put("tipo", doc.getDocId().getDocType());
			params.put("numero", StringUtils.leftPad(doc.getDocId().getDocNum(), 6, "0"));
			filename = "document_" + doc.getDocId().getDocType() + "_" + doc.getDocId().getDocNum() + ".pdf";
		}
		ImportDocumentPdfDto docDto = importDataEntity(params);
		docDto.filename = filename;
		return docDto;
	}

	@Override
	public void importData(HashMap<String,String> params) throws BadImportException {

	}

	private ImportDocumentPdfDto performRemoteCall(HashMap<String,String> params) throws BadImportException {
		// Call Params
		logger.info("Import doc pdf started, calling server...");
		RestEasyImportClient client = RestEasyClientFactory.getInstance(ImportType.DOCUMENT_PDF).setFormParams(params);
        Response response = client.post();
        logger.info("Server has been called");
        isResponseValid(response);
        logger.info("Response is valid, starting import data");
        ImportDocumentPdfDto documents = response.readEntity(ImportDocumentPdfDto.class);
        if(!isImportDtoValid(documents)) {
        	throw new BadImportException("Import doc pdf dto is invalid: " + documents);
        }
		return documents;
	}

	@Override
	public ImportDocumentPdfDto importDataEntity(HashMap<String,String> params) throws BadImportException {
		return performRemoteCall(params);
	}
	
}
