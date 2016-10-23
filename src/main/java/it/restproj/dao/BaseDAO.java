package it.restproj.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class BaseDAO<T> {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager em;

	private Class<T> entityClass;

	public EntityManager getEntityManager() {
		return em;
	}

	public Class<T> getEntityClass() {
		if (entityClass == null)
			entityClass = getRuntimeEntityClass();
		return entityClass;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Class<T> getRuntimeEntityClass() {

		ParameterizedType parameterizedType = null;
		Type genericSuperclass = getClass().getGenericSuperclass();

		if (genericSuperclass instanceof ParameterizedType)
			parameterizedType = (ParameterizedType) genericSuperclass;
		else {
			Class class1 = (Class) genericSuperclass;
			parameterizedType = (ParameterizedType) class1.getGenericSuperclass();
		}

		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	public void delete(T object) {
		em.remove(object);
	}

	public void delete(Collection<T> list) {
		for (T object : list) {
			em.remove(object);
		}
	}

	public void insert(@Valid T object) throws ConstraintViolationException {

		/*if (object instanceof InternalDateEnable) {
			InternalDateEnable bean = InternalDateEnable.class.cast(object);
			InternalDate internalDate = bean.getInternalDate();
			if (internalDate == null)
				internalDate = new InternalDate();
			internalDate.setInsertdate(calendarService.getCurrentLocalDateTime());
			bean.setInternalDate(internalDate);
		}*/

		em.persist(object);

	}

	public void merge(T object) throws ConstraintViolationException {
		em.merge(object);
	}

	public void update(T object) throws ConstraintViolationException {

		/*if (object instanceof InternalDateEnable) {
			InternalDateEnable bean = InternalDateEnable.class.cast(object);
			InternalDate internalDate = bean.getInternalDate();
			if (internalDate == null)
				internalDate = new InternalDate();
			internalDate.setLastupdate(calendarService.getCurrentLocalDateTime());
			bean.setInternalDate(internalDate);
		} */

		em.merge(object);

	}

	public void detach(T object) {
		em.detach(object);
	}

	public void refresh(@Valid T object) throws ConstraintViolationException {
		em.refresh(object);
	}

	public void flush() {
		em.flush();
	}

	@SuppressWarnings("unchecked")
	public List<Object> executeQuery(String query) {
		Query createQuery = em.createQuery(query);
		createQuery.setMaxResults(1000);
		return createQuery.getResultList();
	}

	public int executeNativeQuery(String query) {
		logger.debug("Execute native sql: {}.", query);
		return em.createNativeQuery(query).executeUpdate();
	}

	public Query createNativeQuery(String query) {
		logger.debug("Create native sql: {}.", query);
		return em.createNativeQuery(query);
	}

	public T getById(Object id) {
		return em.find(getEntityClass(), id);
	}

	public List<T> getAll() {
		CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(getEntityClass());
		cq.select(cq.from(getEntityClass()));
		TypedQuery<T> query = em.createQuery(cq);
		query.setMaxResults(1000);
		return query.getResultList();
	}

	public List<T> getAllOrderBy(String orderBy) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = builder.createQuery(getEntityClass());
		Root<T> root = cq.from(getEntityClass());
		cq.select(root);
		cq.orderBy(builder.asc(root.get(orderBy)));

		TypedQuery<T> query = em.createQuery(cq);

		query.setMaxResults(1000);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllByRange(int[] range) {
		CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(getEntityClass());
		cq.select(cq.from(getEntityClass()));
		javax.persistence.Query q = em.createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int count() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery cq = builder.createQuery();
		Root<T> rt = cq.from(getEntityClass());
		cq.select(builder.count(rt));
		javax.persistence.Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	public boolean keyExist(Object id) {
		return getById(id) != null;
	}

	/**
	 * Fatto perchè se cerchi con un set di enum, invece di avere string hai
	 * varbinary nel database
	 * 
	 * @param in
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Collection<String> convertEnumToStringCollection(Collection<? extends Enum> in) {
		Collection<String> out = new HashSet<String>();
		for (Enum e : in)
			out.add(e.name());
		return out;
	}

}