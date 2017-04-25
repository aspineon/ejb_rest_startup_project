package eu.limontacolori.privatearea.rest.dto;

import java.util.Date;
import java.util.Set;

import eu.limontacolori.privatearea.entities.enums.Role;

public class UserDto {

	private int id;
	private String username;
	private Date insertionDate;
	private String name;
	private String surname;
	private String email;
	private Boolean emailValidated;
	private Boolean emailSent;
	private Set<Role> roles;
	
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
	public Date getInsertionDate() {
		return insertionDate;
	}
	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
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
		
	public Boolean getEmailValidated() {
		return emailValidated;
	}
	public void setEmailValidated(Boolean emailValidated) {
		this.emailValidated = emailValidated;
	}
	public Boolean getEmailSent() {
		return emailSent;
	}
	public void setEmailSent(Boolean emailSent) {
		this.emailSent = emailSent;
	}
	
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username
				+ ", insertionDate=" + insertionDate + ", name=" + name
				+ ", surname=" + surname + ", email=" + email
				+ ", emailValidated=" + emailValidated + ", emailSent="
				+ emailSent + ", roles=" + roles + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof UserDto)) 
			return false;
		else 
			return this.id == ((UserDto) obj).id;
	}
	
	@Override
	public int hashCode() {
		return this.id;
	}
	
}
