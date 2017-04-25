package eu.limontacolori.privatearea.rest.client;

import eu.limontacolori.privatearea.exceptions.jaxrs.BadImportException;

public class RestEasyClientFactory {

	public static RestEasyImportClient getInstance(ImportType type) throws BadImportException {
		if(type.equals(ImportType.CUSTOMER)) {
			return new RestEasyImportClientCustomer();
		} else if (type.equals(ImportType.DOCUMENTS)) {
			return new RestEasyImportClientDocument();
		} else {
			return new RestEasyImportClientDocumentPdf();
		}
	}
	
}
