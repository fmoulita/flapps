package com.flapps.web.data;

public enum ActivityTypeEnum {
	forbidden, allowed, mandatory;

	public static boolean allowedProjectWithTypeActivity(ActivityTypeEnum e) {
		return ActivityTypeEnum.allowed.equals(e) || ActivityTypeEnum.mandatory.equals(e);
	}

	public static boolean requiredProjectWithTypeActivity(ActivityTypeEnum e) {
		return ActivityTypeEnum.mandatory.equals(e);
	}
}