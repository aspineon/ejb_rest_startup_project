package eu.limontacolori.privatearea.services;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.lang.JoseException;

import eu.limontacolori.privatearea.dao.UserDAO;
import eu.limontacolori.privatearea.entities.User;
import eu.limontacolori.privatearea.exceptions.jaxrs.EmailNotValidException;
import eu.limontacolori.privatearea.exceptions.jaxrs.GenericRuntimeException;
import eu.limontacolori.privatearea.exceptions.jaxrs.NoSearchContentFoundException;
import eu.limontacolori.privatearea.exceptions.jaxrs.UnauthorizedUserException;
import eu.limontacolori.privatearea.rest.dto.ListUsersDto;
import eu.limontacolori.privatearea.rest.dto.LoggedUserDto;
import eu.limontacolori.privatearea.rest.dto.UserDto;
import eu.limontacolori.privatearea.rest.dto.mappers.user.LoggedUserDtoMapper;
import eu.limontacolori.privatearea.rest.dto.mappers.user.UserDtoMapper;
import eu.limontacolori.privatearea.utils.hashpsw.HashingPasswordUtils;
import eu.limontacolori.privatearea.utils.hashpsw.exceptions.GeneratingHashErrorException;

@Stateless
public class UserService {
	
	Logger logger = LogManager.getLogger(UserService.class);
	
	@Inject
	UserDAO userDAO;
	
	@Inject
	AuthService authService;
	
	@Inject
	UserDtoMapper userDtoMapper;
	
	
	public LoggedUserDto login(String username, String password) throws GeneratingHashErrorException, JoseException, UnauthorizedUserException, EmailNotValidException {
		User user = userDAO.getByUsername(username);
		if(user == null)
			throw new UnauthorizedUserException("Username o password errati");
		if(!user.getEmailValidated()) 
			throw new EmailNotValidException("User email not validated");
		// check salt & password
		if(HashingPasswordUtils.validatePassword(password, user.getSalt(), user.getPassword())) {
			LoggedUserDto loggedUser = LoggedUserDtoMapper.to(user);
			String token = authService.getToken(loggedUser);
			loggedUser.setAccessToken(token);
			return loggedUser;
		} else {
			throw new UnauthorizedUserException("Username o password errati");
		}
	}
	
	public void logout(String token) throws UnauthorizedUserException {
		authService.removeToken(token);
	}
	
	public void addUser(User user) throws GenericRuntimeException {
		// check se utente già esistente
		User alreadyRegistered = userDAO.getByUsername(user.getUsername());
		if(alreadyRegistered != null) 
			throw new GenericRuntimeException("User already registered");
		// setto nuova password & salt
		setUserPassword(user);
		userDAO.insert(user);
	}

	private void setUserPassword(User user) throws GenericRuntimeException {
		try {
			byte[] salt = HashingPasswordUtils.generateSalt();
			String newPassword = HashingPasswordUtils.generateHashedPassword(user.getPassword(), salt);
			user.setPassword(newPassword);
			user.setSalt(HashingPasswordUtils.bytetoString(salt));
		} catch (Exception exc) {
			logger.error("Error during user password generation", exc);
			throw new GenericRuntimeException("Error during user password generation");
		}
	}
	
	public ListUsersDto listUsers(String orderBy, String orderDir, int limit, int offset) {
		Set<UserDto> usersDto = new HashSet<>();
		try {
			Set<User> users = userDAO.listUsers(orderBy, orderDir, limit, offset);
			usersDto = users.stream().map(userDtoMapper::getUserDto).collect(Collectors.toSet());
			ListUsersDto dto = new ListUsersDto(offset, limit, 0, orderBy, orderDir, usersDto);
			return dto;
		} catch (NoResultException exc) {
			logger.info("No users found");
			return new ListUsersDto(offset, limit, 0, orderBy, orderDir, new HashSet<>());
		}		
	}
	
	public User getUserEntity(int userId) {
		User user = userDAO.getById(userId);
		return user;
	}
	
	public UserDto getUser(int userId) throws NoSearchContentFoundException {
		try {
			User user = userDAO.getById(userId);
			return userDtoMapper.getUserDto(user);
		} catch (NoResultException exc) {
			String message = String.format("No user found for id {}", userId);
			logger.info(message);
			throw new NoSearchContentFoundException(message);
		}	
	}
	
	public void changePassword(Integer userId, String newPassword) throws NoSearchContentFoundException, GenericRuntimeException {
		try {
			User user = userDAO.getById(userId);
			user.setPassword(newPassword);
			setUserPassword(user);
			userDAO.update(user);
		} catch (NoResultException exc) {
			String message = String.format("No user found for id {}", userId);
			logger.info(message);
			throw new NoSearchContentFoundException(message);
		}
	}
	
}
