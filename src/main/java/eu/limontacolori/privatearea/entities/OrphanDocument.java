package eu.limontacolori.privatearea.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

// Entity per documento orfano, ossia senza cliente associato
@Entity
@Table(name="ORPHAN_DOCUMENT")
public class OrphanDocument {
	
	@EmbeddedId
	private DocumentId docId;
	
	@Column(name="DOC_DATE")
	private Date docDate;
	
	@Column(name="CREATION_DATE")
	private Date creationDate;
	
	@Column(name="SOC")
	private String soc;
	
	@Column(name="CUSTOMER_ID") 
	private String customerId;
	

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
		return "OrphanDocument [docId=" + docId + ", docDate=" + docDate
				+ ", creationDate=" + creationDate + ", customerId="
				+ customerId + "]";
	}
	
	
	
}
