package it.restproj.dto;

import javax.validation.constraints.NotNull;

public class SignupUserDto {
	
	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String name;
	@NotNull
	private String surname;
	@NotNull
	private String email;
	@NotNull
	private String[] roles;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String[] getRoles() {
		return roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	
}
