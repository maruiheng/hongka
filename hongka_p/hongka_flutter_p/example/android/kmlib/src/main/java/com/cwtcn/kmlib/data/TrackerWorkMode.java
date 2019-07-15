package com.cwtcn.kmlib.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/***
 * 工作模式
 * @author Allen 20170316
 */
public class TrackerWorkMode {
	private long id;
	private String imei;
	/** 当前的工作模式：0：精确 1：正常 2：省电 默认是1 */
	private int mode = 1;
	/** 定位的时间间隔（以秒为单位存储）默认为300秒 */
	private int interval = 300;
	private boolean intervalEnabled;
	private boolean sleepEnabled;
	private String sleepStartTime;
	private String sleepEndTime;
	private List<TrackerTimeData> intervalRange;
	/** 最后被修改的人 */
	private String lastUpdateBy;
	private String lut;

	public TrackerWorkMode(String imei, int mode, int interval, boolean intervalEnabled, String sleepStartTime, String sleepEndTime, boolean sleepEnable) {
		this.imei = imei;
		this.mode = mode;
		this.interval = interval;
		this.intervalEnabled = intervalEnabled;
		this.sleepStartTime = sleepStartTime;
		this.sleepEndTime = sleepEndTime;
		this.sleepEnabled = sleepEnable;
	}

	public TrackerWorkMode(String imei, int mode, int interval, boolean intervalEnabled, String sleepStartTime, String sleepEndTime, boolean sleepEnable, List<TrackerTimeData> range) {
		this.imei = imei;
		this.mode = mode;
		this.interval = interval;
		this.intervalEnabled = intervalEnabled;
		this.sleepStartTime = sleepStartTime;
		this.sleepEndTime = sleepEndTime;
		this.sleepEnabled = sleepEnable;
		this.intervalRange = range;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public boolean isIntervalEnabled() {
		return intervalEnabled;
	}

	public void setIntervalEnabled(boolean intervalEnabled) {
		this.intervalEnabled = intervalEnabled;
	}

	public boolean isSleepEnabled() {
		return sleepEnabled;
	}

	public void setSleepEnabled(boolean sleepEnabled) {
		this.sleepEnabled = sleepEnabled;
	}

	public String getSleepStartTime() {
		return sleepStartTime;
	}

	public void setSleepStartTime(String sleepStartTime) {
		this.sleepStartTime = sleepStartTime;
	}

	public String getSleepEndTime() {
		return sleepEndTime;
	}

	public void setSleepEndTime(String sleepEndTime) {
		this.sleepEndTime = sleepEndTime;
	}

	public List<TrackerTimeData> getIntervalRange() {
		return intervalRange;
	}

	public void setIntervalRange(List<TrackerTimeData> intervalRange) {
		this.intervalRange = intervalRange;
	}

	public String getMessage() {
		JSONObject json = new JSONObject();
		try {
			json.put("imei", imei);
			json.put("mode", mode);
			json.put("interval", interval);
			json.put("intervalEnabled", intervalEnabled);
			json.put("sleepStartTime", sleepStartTime);
			json.put("sleepEndTime", sleepEndTime);
			json.put("sleepEnabled", sleepEnabled);
		} catch (JSONException e) {

		}
		return json.toString();
	}
}
