package eu.limontacolori.privatearea.rest.dto.mappers.orphandocument;

import eu.limontacolori.privatearea.entities.Document;
import eu.limontacolori.privatearea.entities.OrphanDocument;
import eu.limontacolori.privatearea.rest.dto.ImportDocumentDto;

public class OrphanDocumentMapper {

	public OrphanDocument from(Document doc, ImportDocumentDto dto) {
		if(doc == null) {
			return null;
		} else {
			OrphanDocument orph = new OrphanDocument();
			orph.setCreationDate(doc.getCreationDate());
			orph.setCustomerId(dto.CLIENTE);
			orph.setDocDate(doc.getDocDate());
			orph.setDocId(doc.getDocId());
			orph.setSoc(doc.getSoc());
			return orph;
		}
	}
	
	
}
