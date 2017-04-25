package eu.limontacolori.privatearea.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@SuppressWarnings("serial")
@Embeddable
public class DocumentId implements Serializable{

	@Column(name="DOC_TYPE")
	private String docType;
	
	@Column(name="DOC_YEAR")
	private String docYear;
	
	@Column(name="DOC_NUM")
	private String docNum;

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocYear() {
		return docYear;
	}

	public void setDocYear(String docYear) {
		this.docYear = docYear;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	@Override
	public String toString() {
		return "DocumentId [docType=" + docType + ", docYear=" + docYear
				+ ", docNum=" + docNum + "]";
	}
	
	
	
}
