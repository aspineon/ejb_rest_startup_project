package it.restproj.exceptions.jaxRs;

import it.restproj.dto.RestExceptionDto;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IncorrectUserRoleExceptionHandler implements ExceptionMapper<IncorrectUserRoleException> {
	
	public Response toResponse(IncorrectUserRoleException exception) {
		RestExceptionDto exceptionDto = new RestExceptionDto(exception.getMessage());
        return Response.status(Status.FORBIDDEN).entity(exceptionDto).build();  
    }
}
