package com.flapps.web.utils.business.storage;

import com.flapps.web.entity.IStorageEntity;

import java.util.Collection;
import java.util.function.Function;

@Deprecated
public interface IWriteStorageManager extends IReadStorageManager {
    public int bulkUpdate(String hql);

    public int bulkUpdate(String hql, Object param);

    public int bulkUpdate(final String queryString, final String[] paramNames, final Object[] values);


    public int bulkUpdate(String hql, Object[] params);

    public <T extends IStorageEntity> T save(T o);

    public <T extends IStorageEntity> T update(T o);

    public <T extends IStorageEntity> T saveOrUpdate(T o);

    public void delete(Object o);

    public void deleteAll(Collection<?> entities);

    public boolean contains(Object entity);

    public void flush();


    public static interface ExceptionFunction<FROM, RET> {
        public RET apply(FROM from) throws Exception;
    }

    public <T> T doNewWritableTransaction(Function<IWriteStorageManager, T> t);

    public <T> T doNewWritableTransactionException(ExceptionFunction<IWriteStorageManager, T> t) throws Exception;

    public <T> T doNewReaonlyTransaction(Function<IWriteStorageManager, T> t);

    public <T> T doReaonlyTransaction(ExceptionFunction<IWriteStorageManager, T> t) throws Exception;

}
