package com.flapps.web.entity;

public class AttendanceOperationRTO {

    public static final Long activityId_CLOCK_OUT = 0L;
    public static final Long activityId_CLOCK_IN = -1L;

    private Long activityId;
    private String activityName;
    private String code;

    public AttendanceOperationRTO() {
    }

    public static Long getActivityId_CLOCK_OUT() {
        return activityId_CLOCK_OUT;
    }

    public static Long getActivityId_CLOCK_IN() {
        return activityId_CLOCK_IN;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AttendanceOperationRTO(Long activityId, String activityName) {
        this.activityId = activityId;
        this.activityName = activityName;
    }
}
