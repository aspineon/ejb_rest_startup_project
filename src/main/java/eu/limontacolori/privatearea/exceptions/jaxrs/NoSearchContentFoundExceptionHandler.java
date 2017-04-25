package eu.limontacolori.privatearea.exceptions.jaxrs;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import eu.limontacolori.privatearea.rest.dto.RestExceptionDto;

@Provider
public class NoSearchContentFoundExceptionHandler implements ExceptionMapper<NoSearchContentFoundException> {
	@Override
	public Response toResponse(NoSearchContentFoundException exception) 
    {
		RestExceptionDto exceptionDto = new RestExceptionDto(exception.getMessage());
        return Response.status(Status.NO_CONTENT).entity(exceptionDto).build();  
    }
}

