package com.flapps.web.utils.business.hibernate;

import com.flapps.web.entity.IStorageEntity;
import com.flapps.web.utils.hqlquery.HqlQuery;
import com.flapps.web.utils.hqlquery.ParamHelper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Deprecated
public class HibQueryHelper {

	@SuppressWarnings("unchecked")
	public static <T extends IStorageEntity> List<T> list(HqlQuery<T> query, EntityManager em) {
		return (List<T>) list(query, query.getAlias(), "", em);
	}

	@SuppressWarnings("unchecked")
	public static List<?> list(HqlQuery query, String selectClause, EntityManager em) {
		return list(query, selectClause, "", em);
	}

	@SuppressWarnings("unchecked")
	public static List<?> list(HqlQuery query, String selectClause, String aliasPref, EntityManager em) {
		ParamHelper params = new ParamHelper();
		String hql = query.getHql(selectClause, aliasPref, params);
		return executeTemplateFind(em, hql, params, query.getFirstResult(), query.getMaxResults());
	}

	@SuppressWarnings("unchecked")
	public static Object single(HqlQuery query, String selectClause, EntityManager em) {
		ParamHelper params = new ParamHelper();
		String hql = query.getHql((selectClause != null) ? selectClause : query.getAlias(), "", params);
		List l = executeTemplateFind(em, hql, params, -1, 1);
		return (l.size() > 0) ? l.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public static <T extends IStorageEntity> T single(HqlQuery query, EntityManager em) {
		return (T) single(query, query.getAlias(), em);
	}

	@SuppressWarnings("unchecked")
	public static int count(HqlQuery query, EntityManager em) {
		ParamHelper params = new ParamHelper();
		String sql = query.getHql("count(*) ", "", false, params);

		Query q = em.createQuery(sql);
		params.doParamsToQuery(q);
		List hres = q.getResultList();
		Object o = (hres.size() > 0) ? hres.get(0) : null;
		return (o != null && (o instanceof Number)) ? ((Number) o).intValue() : 0;
	}

	@SuppressWarnings("unchecked")
	protected static List<?> executeTemplateFind(final EntityManager em, final String queryString, final ParamHelper params, final int firstResult,
												 final int maxResults) {

		Query queryObject = em.createQuery(queryString);
		if (params != null) {
			params.doParamsToQuery(queryObject);
		}
		if (firstResult > 0) {
			queryObject.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			queryObject.setMaxResults(maxResults);
		}

		return queryObject.getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<?> executeNativeSql(final EntityManager em, final String sqlString, final Object[] values) {
		Query q = em.createNativeQuery(sqlString);
		ParamHelper.doParamsToQuery(q, values);
		return q.getResultList();
	}
}
