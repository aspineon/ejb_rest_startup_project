package eu.limontacolori.privatearea.dao;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import eu.limontacolori.privatearea.entities.Customer;
import eu.limontacolori.privatearea.entities.Document;
import eu.limontacolori.privatearea.rest.dto.PaginationDto;
import eu.limontacolori.privatearea.rest.dto.PaginationReqDto;

public class DocumentDAO extends BaseDAO<Document> {

	
	public PaginationDto<Document> getDocumentsList(Customer customer, Date dateStart, Date dateEnd, PaginationReqDto paginationReq) {
		String rootAlias = "d";
		CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Document> critQuery = criteriaBuilder.createQuery(Document.class);
		Root<Document> root = critQuery.from(Document.class);
		root.alias(rootAlias);
		Predicate p = null; 
		
		if(customer != null) {
			p = criteriaBuilder.equal(root.get("customer").get("id"), customer.getId());
		}
		
		if(dateStart != null) {
			Predicate pDateStart = criteriaBuilder.greaterThanOrEqualTo(root.get("docDate"), dateStart);
			p = (p != null) ? criteriaBuilder.and(p,pDateStart) : pDateStart;
		}
		
		if(dateEnd != null) {
			Predicate pDateEnd = criteriaBuilder.lessThanOrEqualTo(root.get("docDate"), dateEnd);
			p = (p != null) ? criteriaBuilder.and(p,pDateEnd) : pDateEnd;
		}
		
		if (StringUtils.isEmpty(paginationReq.orderBy)) { 
			paginationReq.orderBy = "docId.docNum";
			paginationReq.orderDir = "asc";
		}
		
		return this.paginateResults(paginationReq, p, Document.class, rootAlias);		
	}
	
}
