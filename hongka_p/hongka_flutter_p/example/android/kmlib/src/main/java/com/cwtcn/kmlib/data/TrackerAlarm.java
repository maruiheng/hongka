package com.cwtcn.kmlib.data;

/**
 * 闹铃实体
 * @author Allen
 *
 */
public class TrackerAlarm implements Comparable<TrackerAlarm> {
	/** 时间 */
	private String time = "0001";
	/** 重复星期标识 0000000*/
	private String week = "1111100";
	/** 响铃方式：1-只响铃 2-只振动 3-响铃带振动*/
	private int ringType = 3;
	/** 可用状态*/
	public boolean enabled = false;

	public TrackerAlarm() {
		super();
	}
	
	public TrackerAlarm(String time) {
		super();
		this.time = time;
	}
	
	public TrackerAlarm(String time, boolean enable, String week, int type) {
		super();
		this.time = time;
		this.enabled = enable;
		this.week = week;
		this.ringType = type;
	}	


	@Override
	public int compareTo(TrackerAlarm another) {
		return Integer.parseInt(another.time) - Integer.parseInt(this.time);
	}	
	
	public boolean isEnabled() {
		return enabled;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public int getRingType() {
		return ringType;
	}

	public void setRingType(int ringType) {
		this.ringType = ringType;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
