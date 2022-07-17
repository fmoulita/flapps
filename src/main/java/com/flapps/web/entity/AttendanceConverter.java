package com.flapps.web.entity;

import com.flapps.web.helper.DateHelper;
import org.apache.commons.lang3.ObjectUtils;
import com.flapps.web.entity.ActivityEntity;
import org.joda.time.DateTime;

public class AttendanceConverter {

    public static String getAttendanceOperationText(Long opId, ActivityEntity activity) {
        if (ObjectUtils.equals(opId, AttendanceOperationRTO.activityId_CLOCK_IN)) return "CLOCK_IN";
        if (ObjectUtils.equals(opId, AttendanceOperationRTO.activityId_CLOCK_OUT)) return "CLOCK_OUT";
        //if (ObjectUtils.equals(opId, AttendanceOperationRTO.activityId_TOGGLE)) return "TOOGLE";
        return "???op=" + opId + "???";
    }

    public static AttendanceDetailRTO convert(AttendanceEntity attendance) {
        AttendanceDetailRTO ret = new AttendanceDetailRTO();
        ret.setManual(false);
        if (attendance == null) throw new IllegalStateException("Attendance is null!!!");

        ret.setId(attendance.getId());

        if (attendance.getIn() != null && attendance.getIn().getTime() != null) {
            ret.setStart(convertAttendanceLogDTO(attendance.getIn()));
        }

        if (attendance.getIn() != null) {
            if (attendance.getIn().getUser() != null) {
                String getUser = attendance.getIn().getUser().toString();
                ret.setUser(getUser);
            }

            ret.setCardId(attendance.getIn().getCardId());

            if (attendance.getIn().isManualChanged()) {
                ret.setManual(true);
            }
        }

        DateTime dtEnd = new DateTime();
        if (attendance.getOut() != null) {
            if (attendance.getOut().getTime() != null) {
                ret.setEnd(convertAttendanceLogDTO(attendance.getOut()));
            }
            dtEnd = new DateTime(attendance.getOut().getTime());
            if (attendance.getOut().isManualChanged()) {
                ret.setManual(true);
            }
        }
        int duration = 0;
        try {
            if (attendance.getIn() != null && attendance.getIn().getTime() != null) {
                duration = DateHelper.getDurationInMinutes(new DateTime(attendance.getIn().getTime()), dtEnd);
            }
            if (duration < 0) duration = 0;
        } catch (Exception e) {

        }
        ret.setDuration(String.valueOf(duration));

        return ret;
    }

    static public AttendanceLogDTO convertAttendanceLogDTO(AttendanceLog log) {
        AttendanceLogDTO dto = new AttendanceLogDTO();
        if (log == null) return dto;
        if (log.getTime() == null) return dto;

        dto.setId(log.getId());
        dto.setApprovedStatus(log.getApprovedStatus());
        dto.setNote(log.getNote());
        dto.setTime(log.getTime().getTime());
        return dto;
    }
}
