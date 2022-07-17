package com.flapps.web.utils.business.hibernate;

import com.flapps.web.entity.IStorageEntity;
import com.flapps.web.utils.business.storage.IWriteStorageManager;
import com.flapps.web.utils.business.storage.IWriteStorageManager.ExceptionFunction;
import com.flapps.web.utils.hqlquery.HqlQuery;
import com.flapps.web.utils.hqlquery.ParamHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Transactional
@Deprecated
@Component
public class HibWriteStorageManager implements IWriteStorageManager {

	@PersistenceContext(unitName = "pu-flapps")
	private EntityManager em;

	public EntityManager getEntityManager() {
		return em;
	}

	public int count(HqlQuery<?> query) {
		return HibQueryHelper.count(query, em);
	}

	public List<?> list(HqlQuery<?> query, String selectClause) {
		return HibQueryHelper.list(query, selectClause, em);
	}

	public <T> List<T> list(HqlQuery<?> query, String selectClause, Class<T> cls) {
		return (List) HibQueryHelper.list(query, selectClause, em);
	}

	public <T extends IStorageEntity> List<T> list(HqlQuery<T> query) {
		return HibQueryHelper.list(query, em);
	}

	public <T extends IStorageEntity> T load(Class<T> type, long id) {
		return (T) em.find(type, id);
	}

	public <T extends IStorageEntity> T loadOrNull(Class<T> type, long id) {
		return (T) em.find(type, id);
	}

	public Object single(HqlQuery<?> query, String selectClause) {
		return HibQueryHelper.single(query, selectClause, em);
	}

	public <T> T single(HqlQuery<?> query, String selectClause, Class<T> cls) {
		return (T) single(query, selectClause);
	}

	public <T extends IStorageEntity> T single(HqlQuery<T> query) {
		return HibQueryHelper.single(query, em);
	}

	public <T extends IStorageEntity> T save(T o) {
		em.persist(o);
		return o;
	}

	public <T extends IStorageEntity> T update(T o) {
		return em.merge(o);
	}

	public <T extends IStorageEntity> T saveOrUpdate(T o) {
		if (o.getId() == 0L) {
			em.persist(o);
			return o;
		}
		return em.merge(o);
	}

	public void delete(Object o) {
		em.remove(o);
	}

	public void deleteAll(Collection<?> entities) {
		if (entities == null) return;
		for (Object o : entities) {
			em.remove(o);
		}
	}

	public boolean contains(Object entity) {
		return em.contains(entity);
	}

	public void refresh(Object entity) {
		em.refresh(entity);
	}

	public int bulkUpdate(String hql) {
		return em.createQuery(hql).executeUpdate();
	}

	public int bulkUpdate(String hql, Object param) {
		return em.createQuery(hql).setParameter(1, param).executeUpdate();
	}

	public int bulkUpdate(final String queryString, final String[] paramNames, final Object[] values) {
		return ParamHelper.doParamsToQuery(em.createQuery(queryString), paramNames, values).executeUpdate();
	}

	@Deprecated
	public int bulkUpdate(String hql, Object[] params) {
		return ParamHelper.doParamsToQuery(em.createQuery(hql), params).executeUpdate();
	}

	public void flush() {
		em.flush();
	}

	@SuppressWarnings("unchecked")
	public List<?> executeSql(final String sqlString, final Object... values) {
		Query q = em.createQuery(sqlString);
		ParamHelper.doParamsToQuery(q, values);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<?> executeNativeSql(final String sqlString, final Object... values) {
		Query q = em.createNativeQuery(sqlString);
		ParamHelper.doParamsToQuery(q, values);
		return q.getResultList();
	}


	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public <T> T doNewWritableTransaction(Function<IWriteStorageManager, T> t) {
		T ret = t.apply(this);
		this.flush();
		return ret;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public <T> T doNewWritableTransactionException(ExceptionFunction<IWriteStorageManager, T> t) throws Exception {
		T ret = t.apply(this);
		this.flush();
		return ret;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public <T> T doNewReaonlyTransaction(Function<IWriteStorageManager, T> t) {
		T ret = t.apply(this);
		this.flush();
		return ret;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T> T doReaonlyTransaction(ExceptionFunction<IWriteStorageManager, T> t) throws Exception {
		T ret = t.apply(this);
		this.flush();
		return ret;
	}

}
