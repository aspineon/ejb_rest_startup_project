package eu.limontacolori.privatearea.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.limontacolori.privatearea.auth.RequiredRole;
import eu.limontacolori.privatearea.auth.TrackUser;
import eu.limontacolori.privatearea.entities.enums.Role;
import eu.limontacolori.privatearea.exceptions.jaxrs.GenericRuntimeException;
import eu.limontacolori.privatearea.exceptions.jaxrs.IncorrectUserRoleException;
import eu.limontacolori.privatearea.exceptions.jaxrs.NoSearchContentFoundException;
import eu.limontacolori.privatearea.exceptions.jaxrs.UnauthorizedUserException;
import eu.limontacolori.privatearea.rest.dto.ListUsersDto;
import eu.limontacolori.privatearea.rest.dto.LoggedUserDto;
import eu.limontacolori.privatearea.rest.dto.SignupUserDto;
import eu.limontacolori.privatearea.rest.dto.UserDto;
import eu.limontacolori.privatearea.rest.dto.mappers.user.UserMapper;
import eu.limontacolori.privatearea.services.UserService;

@Stateless
@Path("users")
public class UserRest extends BaseRest {

	@Inject
	UserService userService;
	
	@Inject 
	UserMapper userMapper;

	Logger logger = LogManager.getLogger(UserRest.class);

	@Path("signup")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(@Valid SignupUserDto userDto) 
			throws GenericRuntimeException {
		logger.info("Signup user request received");
		userService.addUser(userMapper.from(userDto));
		return Response.ok().build();
	}
	
	
	@Path("listUsers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@TrackUser
	@RequiredRole(Role.ADMIN)
	public ListUsersDto listUsers(@QueryParam("limit") int limit,
			@QueryParam("offset") int offset,
			@QueryParam("orderBy") String orderBy,
			@QueryParam("orderDir") String orderDir) throws GenericRuntimeException, UnauthorizedUserException, IncorrectUserRoleException {
		
		logger.trace("List users request received");
		try {
			return userService.listUsers(orderBy, orderDir, limit, offset);
		} catch (Exception genericExc) {
			logger.error("Generic error during getUsers call", genericExc);
			throw new GenericRuntimeException(String.format("Runtime error, {}", genericExc.getMessage()));
		}
	}
	
	@Path("{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@TrackUser
	@RequiredRole(Role.ADMIN)
	public UserDto getUser(@PathParam("userId") Integer userId) throws GenericRuntimeException, NoSearchContentFoundException, UnauthorizedUserException, IncorrectUserRoleException {
		logger.trace("get user request received for id {}", userId);
		try {
			if(userId == null)
				throw new GenericRuntimeException("user id can't be null");
			return userService.getUser(userId);
		} catch(NoSearchContentFoundException nc) {
			throw nc;
		} catch (Exception genericExc) {
			logger.error("Generic error during getUserCall for userId {}", userId, genericExc);
			throw new GenericRuntimeException(String.format("Runtime error, {}", genericExc.getMessage()));
		}
	}
	
	@Path("{userId}/psw")
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@TrackUser
	@RequiredRole({Role.ADMIN, Role.USER})
	public Response changePassword(@PathParam("userId") Integer userId, @FormParam("newPassword") String newPassword) throws GenericRuntimeException, NoSearchContentFoundException,
			UnauthorizedUserException, IncorrectUserRoleException {
		
		LoggedUserDto loggedUser = this.getLoggedUser();
		logger.info("Change password requested for user {}, by user {}", userId, loggedUser.getId());
		if (userId != loggedUser.getId()) {
			if(!loggedUser.getRoles().contains(Role.ADMIN)) {
				return Response.status(Status.FORBIDDEN).build();
			}
		}		
		userService.changePassword(userId, newPassword);
		return Response.ok().build();
	}

}
