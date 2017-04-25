package eu.limontacolori.privatearea.exceptions.jaxrs;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import eu.limontacolori.privatearea.rest.dto.RestExceptionDto;

@Provider
public class GenericRuntimeExceptionHandler implements ExceptionMapper<GenericRuntimeException>  {

	@Override
	public Response toResponse(GenericRuntimeException exception) {
		RestExceptionDto exceptionDto = new RestExceptionDto(exception.getMessage());
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exceptionDto).build();  
	}

}
