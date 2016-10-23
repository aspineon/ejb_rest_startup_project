package it.restproj.dto;

public class RestExceptionDto {
	private String message;
	
	public RestExceptionDto(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	} 
}
