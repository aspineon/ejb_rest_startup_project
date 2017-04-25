package eu.limontacolori.privatearea.rest.dto;

public class ImportDocumentPdfDto extends LimcaImportDto {
	
	public ImportMessageDto msg;
	public String filename;
	public String data;

	@Override
	public String toString() {
		return "ImportDocumentPdfDto [data=" + data + ", status=" + status
				+ ", msg=" + msg + "]";
	}
	
	
	
}
