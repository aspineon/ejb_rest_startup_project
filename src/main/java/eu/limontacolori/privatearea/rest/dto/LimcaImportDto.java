package eu.limontacolori.privatearea.rest.dto;


public class LimcaImportDto {
	private static final String ERROR_STRING = "error";
	public String status;
	
	public boolean isError() {
		return ERROR_STRING.equals(status);
	}
}
