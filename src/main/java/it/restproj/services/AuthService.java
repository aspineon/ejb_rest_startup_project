package it.restproj.services;

import it.restproj.auth.jwt.JwtManager;
import it.restproj.dto.LoggedUserDto;
import it.restproj.exceptions.jaxRs.UnauthorizedUserException;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jose4j.lang.JoseException;

@Stateless
public class AuthService {
	
	@Inject
	JwtManager jwtManager;
	
	public String getToken(LoggedUserDto user) throws JoseException {
		return jwtManager.generateJwtToken(user);
	}
	
	public void removeToken(String token) throws UnauthorizedUserException {
		jwtManager.invalidateToken(token);
	}
	
}
