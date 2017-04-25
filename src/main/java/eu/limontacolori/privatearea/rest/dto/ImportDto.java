package eu.limontacolori.privatearea.rest.dto;

import java.util.List;

public class ImportDto<T> extends LimcaImportDto {
	
	public String msg;
	public List<T> data;

}
