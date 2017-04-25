package eu.limontacolori.privatearea.rest.dto.mappers.user;

import eu.limontacolori.privatearea.entities.User;
import eu.limontacolori.privatearea.rest.dto.UserDto;

public class UserDtoMapper {
	
	public UserDto getUserDto(User usr) {
		if(usr == null)
			return null;
		else {
			UserDto usrDto = new UserDto();
			usrDto.setId(usr.getId());
			usrDto.setInsertionDate(usr.getCreationDate());
			usrDto.setName(usr.getName());
			usrDto.setSurname(usr.getSurname());
			usrDto.setRoles(usr.getRoles());
			usrDto.setUsername(usr.getUsername());
			usrDto.setEmailSent(usr.getEmailSent());
			usrDto.setEmailValidated(usr.getEmailValidated());
			return usrDto;
		}
	}

}
