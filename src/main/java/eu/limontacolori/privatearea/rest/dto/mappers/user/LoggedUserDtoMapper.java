package eu.limontacolori.privatearea.rest.dto.mappers.user;

import eu.limontacolori.privatearea.entities.User;
import eu.limontacolori.privatearea.rest.dto.LoggedUserDto;

public class LoggedUserDtoMapper {
	
	public static LoggedUserDto to(User user) {
		if(user == null)
			return null;
		else {
			LoggedUserDto loggedUser = new LoggedUserDto(user.getId(), user.getUsername(), user.getName(),
					user.getSurname(), user.getEmail(), user.getRoles());
			return loggedUser;
		}
	}

}
