package eu.limontacolori.privatearea.rest.dto;


public class ImportDocumentsDto extends ImportDto<ImportDocumentDto>{
	
	@Override
	public String toString() {
		return "ImportDocumentsDto [data=" + data + ", status=" + status
				+ ", msg=" + msg + "]";
	}
	
}
