package it.restproj.dto;

import it.restproj.entities.enums.Role;

import java.util.List;
import java.util.Set;

public class LoggedUserDto {
	
	private int id;
	private String username;
	private String name;
	private String surname;
	private String email;
	private String accessToken;
	private Set<Role> roles;
	
	public LoggedUserDto() {
		
	}
	
	public LoggedUserDto(int id, String username, String name, String surname, String email, Set<Role> roles) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.surname = surname; 
		this.email = email;
		this.roles = roles;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void updateUser(int id, String username, String name, String surname, String email, Set<Role> roles) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.surname = surname; 
		this.email = email;
		this.roles = roles;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
