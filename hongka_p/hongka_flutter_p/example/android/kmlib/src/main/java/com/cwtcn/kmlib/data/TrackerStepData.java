package com.cwtcn.kmlib.data;

/**
 * 活动量数据
 * @author Allen
 *
 */
public class TrackerStepData {

	private int steps;

	private int stepsTarget;

	private String activityDate;

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public int getStepsTarget() {
		return stepsTarget;
	}

	public void setStepsTarget(int stepsTarget) {
		this.stepsTarget = stepsTarget;
	}

	public String getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}
}
