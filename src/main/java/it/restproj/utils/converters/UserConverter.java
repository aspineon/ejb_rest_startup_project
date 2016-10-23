package it.restproj.utils.converters;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.DateTime;

import it.restproj.dto.LoggedUserDto;
import it.restproj.dto.SignupUserDto;
import it.restproj.entities.User;
import it.restproj.entities.enums.Role;

public class UserConverter {
	
	public static LoggedUserDto to(User user) {
		if(user == null)
			return null;
		else {
			LoggedUserDto loggedUser = new LoggedUserDto(user.getId(), user.getUsername(), user.getName(),
					user.getSurname(), user.getEmail(), user.getRoles());
			return loggedUser;
		}
	}
	
	public static User from(SignupUserDto signupRequest) {
		if(signupRequest == null) {
			return null;
		} else {
			User user = new User();
			List<String> roles = Arrays.asList(signupRequest.getRoles());
			Set<Role> rolesSet = (Set<Role>) roles.stream().map(Role::valueOf).collect(Collectors.toCollection(HashSet::new));
			user.setCreationDate(new DateTime().toDate());
			user.setEmail(signupRequest.getEmail());
			user.setName(signupRequest.getName());
			user.setSurname(signupRequest.getSurname());
			user.setUsername(signupRequest.getUsername());
			user.setRoles(rolesSet);
			return user;
		}
	}

}
