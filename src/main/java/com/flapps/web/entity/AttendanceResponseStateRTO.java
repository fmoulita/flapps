package com.flapps.web.entity;

import java.util.List;

public class AttendanceResponseStateRTO<AttendanceStateRTO> {
    private AttendanceStateRTO state;

    // data part
    private boolean partialUpdate;

    private List<AttendanceItemRTO> attendanceList;

    public AttendanceStateRTO getState() {
        return state;
    }

    public void setState(AttendanceStateRTO state) {
        this.state = state;
    }

    public boolean isPartialUpdate() {
        return partialUpdate;
    }

    public void setPartialUpdate(boolean partialUpdate) {
        this.partialUpdate = partialUpdate;
    }


    public List<AttendanceItemRTO> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<AttendanceItemRTO> attendanceList) {
        this.attendanceList = attendanceList;
    }
}
