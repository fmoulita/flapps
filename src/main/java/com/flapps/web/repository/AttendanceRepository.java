package com.flapps.web.repository;

import com.flapps.web.entity.AttendanceEntity;
import com.flapps.web.utils.business.storage.IWriteStorageManager;
import com.flapps.web.utils.hqlquery.HqlQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AttendanceRepository {

    @Autowired
    @Deprecated
    private IWriteStorageManager storageManager;

    public List<AttendanceEntity> getAttendanceById(Long userId) {

        HqlQuery<AttendanceEntity> hql = new HqlQuery<AttendanceEntity>(AttendanceEntity.class, "a");
        hql.where().eq("a.in.user.id", userId);
        hql.addOrderBy("a.modified", "desc");
        hql.setMaxResults(1);

        return storageManager.list(hql);
    }
}
