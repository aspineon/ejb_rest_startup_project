package eu.limontacolori.privatearea.rest;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.lang.JoseException;

import eu.limontacolori.privatearea.auth.TrackUser;
import eu.limontacolori.privatearea.exceptions.jaxrs.EmailNotValidException;
import eu.limontacolori.privatearea.exceptions.jaxrs.UnauthorizedUserException;
import eu.limontacolori.privatearea.rest.dto.LoggedUserDto;
import eu.limontacolori.privatearea.rest.dto.LoginRequestDto;
import eu.limontacolori.privatearea.services.UserService;
import eu.limontacolori.privatearea.utils.hashpsw.exceptions.GeneratingHashErrorException;


@Stateless
@Path("login")
public class LoginRest extends BaseRest{
	
	Logger logger = LogManager.getLogger("it.restproj.rest.LoginRest");
	
	@Inject
	UserService userService;
	
	@Path("in")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(LoginRequestDto loginRequest) throws UnauthorizedUserException, EmailNotValidException {
		logger.info("Login request for user: %s", loginRequest.getUsername());
		ResponseBuilder builder;
		try {
			// ricava utente, se ok genera token, altrimenti rispondi con forbidden
			LoggedUserDto user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
			builder = Response.ok(user, MediaType.APPLICATION_JSON);
			builder.header("Cache-Control", "no-store");
			builder.header("Pragma", "no-cache");
			logger.info("User {} logged in at {}", user.getUsername(), new Date().toString());
		} catch (GeneratingHashErrorException exc) {
			logger.error("Error during login check", exc);
			builder = Response.status(Status.INTERNAL_SERVER_ERROR);
		} catch (JoseException jwtExc) {
			logger.error("Error during jwt generation", jwtExc);
			builder = Response.status(Status.INTERNAL_SERVER_ERROR);
		
		} catch(UnauthorizedUserException unauthExc) {
			throw unauthExc;
		} catch(EmailNotValidException emailNotValidExc) {
			throw emailNotValidExc;
		} catch (Exception genericExc) {
			logger.error("Generic error during user login", genericExc);
			builder = Response.status(Status.INTERNAL_SERVER_ERROR);
		}
		return builder.build();
	}
	
	@TrackUser
	@GET
	@Path("out")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout() throws UnauthorizedUserException {
		LoggedUserDto user = this.getLoggedUser();
		userService.logout(user.getAccessToken());
		return Response.ok().build();
	}
	
}
