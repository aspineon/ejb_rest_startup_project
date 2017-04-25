package eu.limontacolori.privatearea.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.jose4j.lang.JoseException;

import eu.limontacolori.privatearea.auth.jwt.JwtManager;
import eu.limontacolori.privatearea.exceptions.jaxrs.UnauthorizedUserException;
import eu.limontacolori.privatearea.rest.dto.LoggedUserDto;

@Stateless
public class AuthService {
	
	@EJB
	JwtManager jwtManager;
	
	public String getToken(LoggedUserDto user) throws JoseException {
		return jwtManager.generateJwtToken(user);
	}
	
	public void removeToken(String token) throws UnauthorizedUserException {
		jwtManager.invalidateToken(token);
	}
	
}
