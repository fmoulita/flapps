package com.flapps.web.service;

import com.flapps.web.common.RequestFilterTo;
import com.flapps.web.common.ResponseFilterRowsTo;
import com.flapps.web.dao.AttendanceDao;
import com.flapps.web.entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.flapps.web.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    AttendanceDao attendanceDao;

    @Override
    public AttendanceResponseStateRTO<AttendanceStateRTO> getActualStateToResponseForUser(UserEntity user) {
        AttendanceResponseStateRTO<AttendanceStateRTO> ret = new AttendanceResponseStateRTO();

        List<AttendanceEntity> attendances = attendanceRepository.getAttendanceById(user.getId());

        if (attendances != null && attendances.size() > 0 && attendances.get(0).getOut() == null) {
            ret.setAttendanceList(new ArrayList<>(1));
            for (AttendanceEntity attendance : attendances) {
                AttendanceLog attendanceLog = attendance.getOut() == null ? attendance.getIn() : attendance.getOut();
                ret.getAttendanceList().add(setItemAttendance(attendanceLog));
            }
            return ret;
        }
        ret.setAttendanceList(Collections.emptyList());
        return ret;
    }

    @Override
    public ResponseFilterRowsTo<AttendanceDetailFilterRTO, AttendanceDetailRTO>
        getAttendanceDetailList(RequestFilterTo<AttendanceDetailFilterRTO> filter) {
        return attendanceDao.getAttendanceDetailList(filter);
    }

    public AttendanceItemRTO setItemAttendance(AttendanceLog log){
        AttendanceItemRTO item = new AttendanceItemRTO();
        item.setCardId(log.getCardId());
        item.setCardDisplay(log.getUser().getFullName());
        item.setTime(log.getCreated().getTime());
        return item;
    }
}
