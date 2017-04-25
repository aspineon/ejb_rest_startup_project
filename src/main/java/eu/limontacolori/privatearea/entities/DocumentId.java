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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docNum == null) ? 0 : docNum.hashCode());
		result = prime * result + ((docType == null) ? 0 : docType.hashCode());
		result = prime * result + ((docYear == null) ? 0 : docYear.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentId other = (DocumentId) obj;
		if (docNum == null) {
			if (other.docNum != null)
				return false;
		} else if (!docNum.equals(other.docNum))
			return false;
		if (docType == null) {
			if (other.docType != null)
				return false;
		} else if (!docType.equals(other.docType))
			return false;
		if (docYear == null) {
			if (other.docYear != null)
				return false;
		} else if (!docYear.equals(other.docYear))
			return false;
		return true;
	}
	
	
	
}
