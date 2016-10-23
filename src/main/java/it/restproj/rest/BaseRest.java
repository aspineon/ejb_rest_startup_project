package it.restproj.rest;

import java.util.Set;

import it.restproj.auth.jwt.JwtManager;
import it.restproj.dto.LoggedUserDto;
import it.restproj.entities.enums.Role;
import it.restproj.exceptions.jaxRs.UnauthorizedUserException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;



@Stateless
public class BaseRest {
	
	@Context
	Context context;
	
	@Context
	private HttpServletRequest httpRequest;
	
	@Context
	private HttpServletResponse httpResponse;
	
	@Inject 
	private LoggedUserDto loggedUser;
	
	@Inject
	private JwtManager jwtManager;
	
	public String getHttpHeader(String name) {
		return httpRequest.getHeader(name);
	}
	
	public void validateUser() throws UnauthorizedUserException {
		String token = getHttpHeader("Authorization");
		if(StringUtils.isEmpty(token))
			this.loggedUser = null;
		else
			this.loggedUser = jwtManager.validateToken(token);
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

}
