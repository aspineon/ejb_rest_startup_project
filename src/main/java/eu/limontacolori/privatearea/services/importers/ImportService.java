package eu.limontacolori.privatearea.services.importers;

import java.util.HashMap;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.limontacolori.privatearea.exceptions.jaxrs.BadImportException;
import eu.limontacolori.privatearea.rest.dto.ImportCustomersDto;
import eu.limontacolori.privatearea.rest.dto.ImportDocumentsDto;
import eu.limontacolori.privatearea.rest.dto.LimcaImportDto;

public abstract class ImportService<T extends LimcaImportDto> {
	
	Logger logger = LogManager.getLogger(this.getClass());
	
	public abstract void importData(HashMap<String,String> params) throws BadImportException;
	public abstract T importDataEntity(HashMap<String,String> params) throws BadImportException;
	
	public boolean isImportDtoValid(T dto) {
		if (dto != null) {
        	if (!dto.isError()) {
        		if(dto instanceof ImportDocumentsDto) {
        			return ((ImportDocumentsDto) dto).data != null;
        		}
        		
        		if(dto instanceof ImportCustomersDto) {
        			return ((ImportCustomersDto) dto).data != null;
        		}
        		
        		return true;
        	}
		}
		return false;
	}
	
	protected void isResponseValid(Response response) throws BadImportException {
		if(response.getStatus() != 200) {
        	logger.warn("REST Client - Retrieve customer failed with HTTP code {}", response.getStatus());
            throw new BadImportException("Limca import customer call Failed with HTTP error code : " 
                   + response.getStatus());
        }
	}
	
	

}
