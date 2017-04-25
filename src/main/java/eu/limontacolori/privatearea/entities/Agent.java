package eu.limontacolori.privatearea.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="AGENT")
public class Agent {
	
	@Id
	@Column(name="ID")
	private String agentId;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="CREATION_DATE")
	private Date creationDate;
	
	@OneToMany(mappedBy="agent")
	private List<Customer> customers;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	@Override
	public String toString() {
		return "Agent [agentId=" + agentId + ", description=" + description
				+ ", creationDate=" + creationDate + ", customers=" + customers
				+ "]";
	}
}
