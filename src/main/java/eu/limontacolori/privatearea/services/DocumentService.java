package eu.limontacolori.privatearea.services;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.limontacolori.privatearea.dao.DocumentDAO;
import eu.limontacolori.privatearea.entities.Customer;
import eu.limontacolori.privatearea.entities.Document;
import eu.limontacolori.privatearea.entities.DocumentId;
import eu.limontacolori.privatearea.rest.dto.DocumentDto;
import eu.limontacolori.privatearea.rest.dto.PaginationDto;
import eu.limontacolori.privatearea.rest.dto.PaginationReqDto;
import eu.limontacolori.privatearea.rest.dto.mappers.document.DocumentMapper;

@Stateless
public class DocumentService {

	Logger logger = LogManager.getLogger(DocumentService.class);
	
	@Inject
	DocumentDAO docDao;
	
	@Inject
	DocumentMapper docMapper;

	
	public void insertDocument(Document doc) {
		docDao.insert(doc);
	}
	
	public Document getDocument(DocumentId docId) {
		return docDao.getById(docId);
	}
	
	public PaginationDto<DocumentDto> listDocuments(Customer customer, Date dateStart, Date dateEnd, PaginationReqDto paginationReq) {
		PaginationDto<Document> documents = docDao.getDocumentsList(customer, dateStart, dateEnd, paginationReq);
		return docMapper.to(documents);
	}
	
}
