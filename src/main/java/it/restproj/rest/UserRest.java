package it.restproj.rest;

import it.restproj.dto.SignupUserDto;
import it.restproj.exceptions.jaxRs.GenericRuntimeException;
import it.restproj.services.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Stateless
@Path("users")
public class UserRest extends BaseRest {
	
	@Inject
	UserService userService;
	
	Logger logger = LogManager.getLogger("it.restproj.rest.UserRest");

	
	@Path("signup")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(@Valid SignupUserDto userDto) throws GenericRuntimeException {
		logger.info("Signup user request received");
		userService.addUser(userDto);
		return Response.ok().build();
	}
	
	
}
