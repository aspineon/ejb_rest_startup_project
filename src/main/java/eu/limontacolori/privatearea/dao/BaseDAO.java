package eu.limontacolori.privatearea.dao;

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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.limontacolori.privatearea.rest.dto.PaginationDto;
import eu.limontacolori.privatearea.rest.dto.PaginationReqDto;

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
	
	public PaginationDto<T> paginateResults(PaginationReqDto paginationReq, Predicate p, Class<T> targetClass, String rootAlias) {
		PaginationDto<T> paginatedResult = new PaginationDto<>();
		CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
		
		// Obtaining total count
		CriteriaQuery<Long> critQueryLong = criteriaBuilder.createQuery(Long.class);
		Root<T> root = critQueryLong.from(targetClass);
		root.alias(rootAlias);
		if(p != null){
			p.alias(rootAlias);
			critQueryLong.where(p);
		}
		critQueryLong.select(criteriaBuilder.count(root));
	    TypedQuery<Long> tq = this.getEntityManager().createQuery(critQueryLong);
	    paginatedResult.totalCount = tq.getSingleResult().intValue();
		
		// Obtaining paginated results
		CriteriaQuery<T> critQuery = criteriaBuilder.createQuery(targetClass);
		Root<T> rootResults = critQuery.from(targetClass);
		rootResults.alias(rootAlias);
		critQuery.select(rootResults);
		if(p != null){
			p.alias(rootAlias);
			critQuery.where(p);
		}
		
		Path<T> valuePath = handleOrderBy(paginationReq.orderBy, rootResults); 
		Order order = ("desc".equals(paginationReq.orderDir)) ? criteriaBuilder.desc(valuePath) : 
					criteriaBuilder.asc(valuePath);
		critQuery.orderBy(order);
		TypedQuery<T> query = this.getEntityManager().createQuery(critQuery)
										.setFirstResult(paginationReq.pageSize*(paginationReq.page - 1))
										.setMaxResults(paginationReq.pageSize);
		
		List<T> results = (List<T>) query.getResultList();
		paginatedResult.results = results;
		paginatedResult.paginationReq = paginationReq;
		return paginatedResult;
	}
	
	private Path<T> handleOrderBy(String orderBy, Root<T> root) {
		if(StringUtils.isNotEmpty(orderBy)) {
			if (orderBy.contains(".")) {
				String[] conditions = orderBy.split("\\.");
				if((conditions != null) && (conditions.length > 0)) {
					Path<T> path = root.get(conditions[0]);
					int index = 0;
					for (String value : conditions){
						if(index != 0) 
							path = path.get(value);
						index++;
					}
					return path;
				}
			} else {
				return root.get(orderBy);
			}
		}
		return null;
	}

}