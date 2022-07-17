package com.flapps.web.entity;

import com.flapps.web.data.ApprovedStatusEnum;

public class AttendanceLogDTO {
    protected Long id;
    protected Long time;
    private boolean warningPM = false;
    private ApprovedStatusEnum approvedStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public boolean isWarningPM() {
        return warningPM;
    }

    public void setWarningPM(boolean warningPM) {
        this.warningPM = warningPM;
    }

    public ApprovedStatusEnum getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(ApprovedStatusEnum approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String note;
}
