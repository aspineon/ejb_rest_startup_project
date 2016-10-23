package it.restproj.dao;

import java.util.List;

import javax.persistence.Query;

import it.restproj.entities.User;

public class UserDAO extends BaseDAO<User> {

	public User getByUsername(String username) {
		Query query = this.getEntityManager().createQuery("SELECT u FROM User u WHERE u.username = :username");
		query.setParameter("username", username);
		List<User> users = (List<User>) query.getResultList();
		if(users.isEmpty())
			return null;
		else 
			return users.get(0);
	} 
	
}
