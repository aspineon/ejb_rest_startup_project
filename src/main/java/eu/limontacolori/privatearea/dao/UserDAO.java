package eu.limontacolori.privatearea.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import eu.limontacolori.privatearea.entities.User;

public class UserDAO extends BaseDAO<User> {

	public User getByUsername(String username) {
		CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> critQuery = criteriaBuilder.createQuery(User.class);
		Root<User> usr = critQuery.from(User.class);
		critQuery.where(criteriaBuilder.equal(usr.get("username"), username));
		TypedQuery<User> query = this.getEntityManager().createQuery(critQuery);
		List<User> users = query.getResultList();
		if(users.isEmpty())
			return null;
		else 
			return users.get(0);
	} 
	
	public Set<User> listUsers(String orderBy, String orderDir, int limit, int offset) {
		CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> critQuery = criteriaBuilder.createQuery(User.class);
		Root<User> usr = critQuery.from(User.class);
		Order order = ("desc".equals(orderBy)) ? criteriaBuilder.desc(usr.get(orderBy)) : criteriaBuilder.asc(usr.get(orderBy));
		critQuery.orderBy(order);
		TypedQuery<User> query = this.getEntityManager().createQuery(critQuery).setFirstResult(offset).setMaxResults(limit);
		Set<User> results = (Set<User>) query.getResultList();
		return results;
	}
	
}
