package com.flapps.web.utils.business.storage;

import com.flapps.web.entity.IStorageEntity;
import com.flapps.web.utils.hqlquery.HqlQuery;

import java.util.List;

@Deprecated // vyuzivaj standardny JPA EntityManager a DAO vrstvu s kompilacnymi queries
public interface IReadStorageManager {
    public <T extends IStorageEntity> T load(Class<T> type, long id);

    public void refresh(Object entity);

    public <T extends IStorageEntity> List<T> list(HqlQuery<T> query);

    public List<?> list(HqlQuery<?> query, String selectClause);

    public <T> List<T> list(HqlQuery<?> query, String selectClause, Class<T> cls);

    public int count(HqlQuery<?> query);

    public <T extends IStorageEntity> T single(HqlQuery<T> query);

    public Object single(HqlQuery<?> query, String selectClause);

    public <T> T single(HqlQuery<?> query, String selectClause, Class<T> cls);


    public List<?> executeSql(final String sqlString, final Object... values);

    public List<?> executeNativeSql(final String sqlString, final Object... values);

//	public AppProperties getAppProperties();
}
