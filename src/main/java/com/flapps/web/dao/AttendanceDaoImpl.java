package com.flapps.web.dao;

import com.flapps.web.common.RequestFilterTo;
import com.flapps.web.common.ResponseFilterRowsTo;
import com.flapps.web.common.ResponseRowsUtil;
import com.flapps.web.entity.*;
import com.flapps.web.helper.DateHelper;
import com.flapps.web.helper.PredicatesHelper;
import org.joda.time.DateTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.Date;

public class AttendanceDaoImpl implements AttendanceDao {

    @PersistenceContext(unitName = "pu-instance")
    private EntityManager em;

    @Override
    public ResponseFilterRowsTo<AttendanceDetailFilterRTO, AttendanceDetailRTO> getAttendanceDetailList(RequestFilterTo<AttendanceDetailFilterRTO> req) {

        if (req == null) req = new RequestFilterTo<>();
        final RequestFilterTo request = req;
        if (req.getFilter() == null) req.setFilter(new AttendanceDetailFilterRTO());
        AttendanceDetailFilterRTO filter = req.getFilter();

        return ResponseRowsUtil.doQueryResponse(em, AttendanceEntity.class, req, (cb, q, root, conf) -> {
            Join<AttendanceEntity, AttendanceLog> rIn = root.join("attendance_log_in_id");
            Join<AttendanceEntity, AttendanceLog> rOut = root.join("attendance_log_out_id", JoinType.LEFT);

            PredicatesHelper ph = createPredicatesHelper(filter, cb, q, rIn, rOut);

            conf.where(ph.toPredicates(cb));

            if (conf.isForCount()) return AttendanceConverter::convert;

            if ("start".equals(request.getPaginator().getSortBy())) {
                conf.orderBy(request.getPaginator().isSortAsc() ? cb.asc(rIn.get("time")) : cb.desc(rIn.get("time")));
            } else if ("end".equals(request.getPaginator().getSortBy())) {
                conf.orderBy(
                        request.getPaginator().isSortAsc() ? cb.asc(cb.coalesce(rOut.get("time"), cb.currentDate())) : cb.desc(rIn.get("time")),
                        request.getPaginator().isSortAsc() ? cb.asc(rIn.get("time")) : cb.desc(rIn.get("time"))
                );
            }
            conf.orderBy(request.getPaginator().isSortAsc() ? cb.asc(rIn.get("modified")) : cb.desc(rIn.get("modified")));

            return AttendanceConverter::convert;
        });
    }

    protected PredicatesHelper createPredicatesHelper(AttendanceDetailFilterRTO filter, CriteriaBuilder cb, CriteriaQuery<?> q, Join<AttendanceEntity, AttendanceLog> rIn, Join<AttendanceEntity, AttendanceLog> rOut) {
        Date[] dts = DateHelper.getDatesByDuration(filter.getPeriodType(), null, null);

        Date timeFrom = dts[0] == null ? new DateTime(filter.getDateFrom()).withTimeAtStartOfDay().toDate() : dts[0];
        Date timeTo = dts[1] == null ? new DateTime(filter.getDateTo()).withTimeAtStartOfDay().plusDays(1).minusMillis(1).toDate() : dts[1];

        PredicatesHelper ph = new PredicatesHelper();

        if (filter.getUserId() != null) {
            ph.add(cb.equal(rIn.get("asp_tms_flapps_user").get("id"), filter.getUserId()));
        }

        if (timeTo != null) {
            ph.add(cb.lessThan(rIn.get("time"), timeTo));
        }
        if (timeFrom != null) {
            ph.add(cb.or(
                    cb.isNull(rOut),
                    cb.greaterThan(rOut.get("time"), timeFrom)
            ));
        }

        return ph;
    }
}
