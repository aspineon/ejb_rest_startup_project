package eu.limontacolori.privatearea.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// Entity per documento (fattura, bolla, nota di accredito)
@Entity
@Table(name="DOCUMENT")
public class Document {
	
	@EmbeddedId
	private DocumentId docId;
	
	@Column(name="DOC_DATE")
	private Date docDate;
	
	@Column(name="CREATION_DATE")
	private Date creationDate;
	
	@Column(name="SOC")
	private String soc;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CUSTOMER_ID") // TODO: manca liquibase tabelle sia modifica di questa che creazione altre!!!
	private Customer customer;
	

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public DocumentId getDocId() {
		return docId;
	}

	public void setDocId(DocumentId docId) {
		this.docId = docId;
	}

	public String getSoc() {
		return soc;
	}

	public void setSoc(String soc) {
		this.soc = soc;
	}

	@Override
	public String toString() {
		return "Document [docId=" + docId + ", docDate=" + docDate
				+ ", creationDate=" + creationDate + ", customer=" + customer
				+ "]";
	}
	
	
	
}
