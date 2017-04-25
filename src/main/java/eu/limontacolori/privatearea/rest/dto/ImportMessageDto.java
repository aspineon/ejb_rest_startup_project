package eu.limontacolori.privatearea.rest.dto;

public class ImportMessageDto {
	public String errno;
	public String code;
	public String path;
	
	@Override
	public String toString() {
		return "ImportMessageDto [errno=" + errno + ", code=" + code
				+ ", path=" + path + "]";
	}
	
}
