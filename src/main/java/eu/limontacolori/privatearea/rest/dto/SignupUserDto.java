package eu.limontacolori.privatearea.rest.dto;

import javax.validation.constraints.NotNull;

public class SignupUserDto {
	
	@NotNull
	public String username;
	@NotNull
	public String password;
	@NotNull
	public String name;
	@NotNull
	public String surname;
	@NotNull
	public String email;
	@NotNull
	public String[] roles;
	
}
