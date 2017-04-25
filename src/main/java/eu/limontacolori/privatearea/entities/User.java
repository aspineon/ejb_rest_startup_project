package eu.limontacolori.privatearea.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import eu.limontacolori.privatearea.entities.enums.Role;


@Entity
@Table(name="USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@NotNull
	@Column(name="USERNAME", nullable=false)
	private String username;
	
	@Column(name="PASSWORD", nullable=false)
	private String password;
	
	@Column(name="SALT", nullable=false)
	private String salt;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Column(name="SURNAME", nullable=false)
	private String surname;
	
	@Column(name="EMAIL", nullable=false)
	private String email;
	
	@Column(name="EMAIL_VALIDATED")
	private Boolean emailValidated;
	
	@Column(name="EMAIL_SENT")
	private Boolean emailSent;
	
	@Column(name="CREATION_DATE", nullable=false)
	private Date creationDate;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	@CollectionTable(name = "USERS_ROLES", joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id") )
	@OrderColumn
	private Set<Role> roles;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID") // TODO: manca liquibase tabelle sia modifica di questa che creazione altre!!!
	private Customer customer;
	
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
	
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
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
	
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public boolean isAdmin() {
		if(this.getRoles() != null) {
			if(this.getRoles().contains(Role.ADMIN))
				return true;
		}
		return false;
	}
	
	public User() {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof User)) 
			return false;
		else 
			return this.id == ((User) obj).id;
	}
	
	@Override
	public int hashCode() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", salt=" + salt + ", name=" + name + ", surname="
				+ surname + ", email=" + email + ", emailValidated="
				+ emailValidated + ", emailSent=" + emailSent
				+ ", creationDate=" + creationDate + ", roles=" + roles
				+ ", customer=" + customer + "]";
	}
}
