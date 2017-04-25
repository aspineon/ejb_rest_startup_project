package eu.limontacolori.privatearea.services;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.limontacolori.privatearea.dao.OrphanDocumentDAO;
import eu.limontacolori.privatearea.entities.DocumentId;
import eu.limontacolori.privatearea.entities.OrphanDocument;

public class OrphanDocumentService {
	
	Logger logger = LogManager.getLogger(OrphanDocumentService.class);
	
	@Inject
	OrphanDocumentDAO docDao;

	
	public void insertDocument(OrphanDocument doc) {
		docDao.insert(doc);
	}
	
	public OrphanDocument getDocument(DocumentId docId) {
		return docDao.getById(docId);
	}
}
