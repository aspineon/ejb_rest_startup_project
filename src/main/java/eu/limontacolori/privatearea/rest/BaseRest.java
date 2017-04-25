package eu.limontacolori.privatearea.rest;

import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;

import eu.limontacolori.privatearea.auth.jwt.JwtManager;
import eu.limontacolori.privatearea.entities.User;
import eu.limontacolori.privatearea.entities.enums.Role;
import eu.limontacolori.privatearea.exceptions.jaxrs.UnauthorizedUserException;
import eu.limontacolori.privatearea.rest.dto.LoggedUserDto;
import eu.limontacolori.privatearea.services.UserService;



@Stateless
public class BaseRest {
	
	@Context
	Context context;
	
	@Inject
	UserService userService;
	
	@Context
	private HttpServletRequest httpRequest;
	
	@Context
	private HttpServletResponse httpResponse;
	
	@Inject 
	private LoggedUserDto loggedUser;
	
	@EJB
	private JwtManager jwtManager;
	
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	
	public String getHttpHeader(String name) {
		return httpRequest.getHeader(name);
	}
	
	public void validateUser() throws UnauthorizedUserException {
		String token = getHttpHeader("Authorization");
		if(StringUtils.isEmpty(token))
			this.loggedUser = null;
		else {
			token = token.replace("Bearer ", "");
			this.loggedUser = jwtManager.validateToken(token);
		}
	}
	
	
	public LoggedUserDto getLoggedUser() {
		return this.loggedUser;
	}
	
	
	public HttpServletResponse getHttpResponse() {
		return this.httpResponse;
	}

	public Set<Role> getCurrentUserRole() {
		if (this.loggedUser == null)
			return null;
		return this.loggedUser.getRoles();
	}
	
	protected User getLoggedUserEntity() {
		LoggedUserDto loggedUser = this.getLoggedUser();
		User user = userService.getUserEntity(loggedUser.getId());
		return user;
	}

}
