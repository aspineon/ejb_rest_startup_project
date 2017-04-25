package eu.limontacolori.privatearea.rest.dto.mappers.user;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.DateTime;

import eu.limontacolori.privatearea.entities.User;
import eu.limontacolori.privatearea.entities.enums.Role;
import eu.limontacolori.privatearea.rest.dto.ImportCustomerDto;
import eu.limontacolori.privatearea.rest.dto.SignupUserDto;

public class UserMapper {
	
	public User from(ImportCustomerDto customer) {
		if(customer == null) {
			return null;
		} else {
			User user = new User();
			user.setCreationDate(new Date());
			user.setEmail(customer.MAIL);
			user.setEmailSent(false);
			user.setEmailValidated(false);
			user.setName(customer.RAGSOC1);
			user.setSurname(customer.RAGSOC2);
			user.setUsername(customer.CODICE);
			user.setPassword("l1mc@_t3mp0r4ry_PsW");
			Set<Role> roles = new HashSet<>();
			roles.add(Role.USER);
			user.setRoles(roles);
			return user;
		}
	}
	
	public User from(SignupUserDto signupRequest) {
		if(signupRequest == null) {
			return null;
		} else {
			User user = new User();
			List<String> roles = Arrays.asList(signupRequest.roles);
			Set<Role> rolesSet = roles.stream().map(Role::valueOf).collect(Collectors.toCollection(HashSet::new));
			user.setCreationDate(new DateTime().toDate());
			user.setEmail(signupRequest.email);
			user.setName(signupRequest.name);
			user.setSurname(signupRequest.surname);
			user.setUsername(signupRequest.username);
			user.setRoles(rolesSet);
			return user;
		}
	}
	
}
