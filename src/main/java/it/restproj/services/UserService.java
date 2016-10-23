package it.restproj.services;

import it.restproj.dao.UserDAO;
import it.restproj.dto.LoggedUserDto;
import it.restproj.dto.SignupUserDto;
import it.restproj.entities.User;
import it.restproj.exceptions.jaxRs.GenericRuntimeException;
import it.restproj.exceptions.jaxRs.UnauthorizedUserException;
import it.restproj.utils.converters.UserConverter;
import it.restproj.utils.hashpsw.HashingPasswordUtils;
import it.restproj.utils.hashpsw.exceptions.GeneratingHashErrorException;

import java.nio.charset.StandardCharsets;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.lang.JoseException;

@Stateless
public class UserService {
	
	Logger logger = LogManager.getLogger("it.restproj.services.UserService");
	
	@Inject
	UserDAO userDAO;
	
	@Inject
	AuthService authService;
	
	
	public LoggedUserDto login(String username, String password) throws GeneratingHashErrorException, JoseException, UnauthorizedUserException {
		User user = userDAO.getByUsername(username);
		if(user == null) {
			throw new UnauthorizedUserException("User not found");
		}
		// check salt & password
		if(HashingPasswordUtils.validatePassword(password, user.getSalt(), user.getPassword())) {
			LoggedUserDto loggedUser = UserConverter.to(user);
			String token = authService.getToken(loggedUser);
			loggedUser.setAccessToken(token);
			return loggedUser;
		} else {
			return null;
		}
	}
	
	public void logout(String token) throws UnauthorizedUserException {
		authService.removeToken(token);
	}
	
	
	public void addUser(SignupUserDto userDto) throws GenericRuntimeException {
		
		// check se utente già esistente
		User alreadyRegistered = userDAO.getByUsername(userDto.getUsername());
		if(alreadyRegistered != null) 
			throw new GenericRuntimeException("User already registered");
		
		User user = UserConverter.from(userDto);
		// setto nuova password & salt
		try {
			byte[] salt = HashingPasswordUtils.generateSalt();
			String newPassword = HashingPasswordUtils.generateHashedPassword(userDto.getPassword(), salt);
			user.setPassword(newPassword);
			user.setSalt(HashingPasswordUtils.bytetoString(salt));
			userDAO.insert(user);
		} catch (Exception exc) {
			logger.error("Error during user insertion", exc);
			throw new GenericRuntimeException("Fatal error during user insertion");
		}
	}
	
}
