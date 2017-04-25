package eu.limontacolori.privatearea.exceptions.jaxrs;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import eu.limontacolori.privatearea.rest.dto.RestExceptionDto;

@Provider
public class BadImportExceptionHandler implements ExceptionMapper<BadImportException> {
	
	@Override
	public Response toResponse(BadImportException exception) {
		RestExceptionDto exceptionDto = new RestExceptionDto(exception.getMessage());
        return Response.status(Status.fromStatusCode(500)).entity(exceptionDto).build();  
    }
}
