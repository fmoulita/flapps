package com.flapps.web.entity;

import com.flapps.web.data.ActivityTypeEnum;

public class AttendanceDetailRTO {

    protected Long id;

    protected AttendanceLogDTO start;
    protected AttendanceLogDTO end;

    protected String duration;

    protected String cardId;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    protected String user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttendanceLogDTO getStart() {
        return start;
    }

    public void setStart(AttendanceLogDTO start) {
        this.start = start;
    }

    public AttendanceLogDTO getEnd() {
        return end;
    }

    public void setEnd(AttendanceLogDTO end) {
        this.end = end;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getOperationText() {
        return operationText;
    }

    public void setOperationText(String operationText) {
        this.operationText = operationText;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    protected String operationText;

    protected boolean manual;
    protected boolean canEdit = false;
}
