package com.flapps.web.dao;

import com.flapps.web.common.RequestFilterTo;
import com.flapps.web.common.ResponseFilterRowsTo;
import com.flapps.web.entity.AttendanceDetailFilterRTO;
import com.flapps.web.entity.AttendanceDetailRTO;

public interface AttendanceDao {

    ResponseFilterRowsTo<AttendanceDetailFilterRTO, AttendanceDetailRTO> getAttendanceDetailList(RequestFilterTo<AttendanceDetailFilterRTO> filter);
}
