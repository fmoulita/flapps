package com.flapps.web.service;

import com.flapps.web.common.RequestFilterTo;
import com.flapps.web.common.ResponseFilterRowsTo;
import com.flapps.web.entity.*;

import javax.servlet.http.HttpServletRequest;

public interface AttendanceService {
    AttendanceResponseStateRTO<AttendanceStateRTO> getActualStateToResponseForUser(UserEntity userId);
    ResponseFilterRowsTo<AttendanceDetailFilterRTO, AttendanceDetailRTO>getAttendanceDetailList(RequestFilterTo<AttendanceDetailFilterRTO> filter);
}