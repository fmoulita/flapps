package com.flapps.web.entity;

import javax.persistence.*;

@Entity
@Table(name = "asp_tms_attendance")
public class AttendanceEntity extends AbstractFlappsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_log_in_id", referencedColumnName = "id", nullable = false)
    protected AttendanceLog in;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_log_out_id", referencedColumnName = "id", nullable = true)
    protected AttendanceLog out;

    public AttendanceLog getIn() {
        return in;
    }

    public void setIn(AttendanceLog in) {
        this.in = in;
    }

    public AttendanceLog getOut() {
        return out;
    }

    public void setOut(AttendanceLog out) {
        this.out = out;
    }

}
