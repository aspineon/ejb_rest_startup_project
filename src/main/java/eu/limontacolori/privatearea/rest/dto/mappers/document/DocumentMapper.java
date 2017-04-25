package eu.limontacolori.privatearea.rest.dto.mappers.document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import javax.inject.Inject;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import eu.limontacolori.privatearea.entities.Customer;
import eu.limontacolori.privatearea.entities.Document;
import eu.limontacolori.privatearea.entities.DocumentId;
import eu.limontacolori.privatearea.rest.dto.DocumentDto;
import eu.limontacolori.privatearea.rest.dto.ImportDocumentDto;
import eu.limontacolori.privatearea.rest.dto.PaginationDto;
import eu.limontacolori.privatearea.rest.dto.mappers.customer.CustomerMapper;
import eu.limontacolori.privatearea.services.CustomerService;

public class DocumentMapper {
	
	Logger logger = LogManager.getLogger(this.getClass());
	
	@Inject
	CustomerService customerService;
	
	@Inject 
	CustomerMapper customerMapper;

	
	public Document from(ImportDocumentDto dto) {
		if(dto == null) {
			return null;
		} else {
			Document document = new Document();
			document.setCreationDate(new Date());
			if(dto.DT_DOC != null) {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
				try {
					document.setDocDate(fmt.parse(dto.DT_DOC));
				} catch (ParseException e) {
					logger.warn("Bad date {}", dto);
				}
			}
			if(dto.CLIENTE != null) {
				Customer customer = customerService.getCustomer(dto.CLIENTE);
				document.setCustomer(customer);
			}
			DocumentId docId = new DocumentId();
			docId.setDocNum(dto.NR_DOC);
			docId.setDocType(dto.TIPO);
			docId.setDocYear(dto.DT_DOC);
			document.setDocId(docId);
			document.setSoc(dto.SOCIETA);
			return document;
		}
	}
	
	
	public DocumentDto to(Document doc) {
		if(doc == null)
			return null;
		else {
			DocumentDto dto = new DocumentDto();
			dto.docDate = doc.getDocDate();
			dto.creationDate = doc.getCreationDate();
			dto.docNum = doc.getDocId().getDocNum();
			dto.docType = doc.getDocId().getDocType();
			dto.docYear = doc.getDocId().getDocYear();
			dto.soc = doc.getSoc();
			dto.customer = customerMapper.to(doc.getCustomer());
			return dto;
		}
	}
	
	public PaginationDto<DocumentDto> to(PaginationDto<Document> documents) {
		if(documents == null) 
			return null;
		else {
			PaginationDto<DocumentDto> dto = new PaginationDto<>();
			dto.paginationReq = documents.paginationReq;
			dto.totalCount = documents.totalCount;
			if(documents.results != null) {
			dto.results = new ArrayList<DocumentDto>();
				documents.results.stream().forEach( singleDoc -> {
					dto.results.add(to(singleDoc));
				});
			} 
			return dto;
		}
	}
	
	
	
}
