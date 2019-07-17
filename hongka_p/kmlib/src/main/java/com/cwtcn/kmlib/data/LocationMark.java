package com.cwtcn.kmlib.data;

import java.util.List;

/**
 * 位置标记
 * @author Allen
 *
 */
public class LocationMark {
	private long id;
	private String wearerId;
	private String name;
	private String address;
	private double lat;
	private double lon;
	/** 1为常用位置，3为一键回家的位置 */
	private int locationType;
	private Notice notice;
	private boolean enabled;
	private String imei;
	private String lastUpdateTime;

	public LocationMark(String wearerId,String imei, String name, String address, double lat, double lon, boolean enabled){
		this.wearerId = wearerId;
		this.name = name;
		this.imei = imei;
		this.address = address;
		this.lat = lat;
		this.lon = lon;
		this.enabled = enabled;
	}
	
	public LocationMark(long  id,String wearerId,String imei, String name, String address, double lat, double lon, boolean enabled){
		this.wearerId = wearerId;
		this.name = name;
		this.imei = imei;
		this.address = address;
		this.lat = lat;
		this.lon = lon;
		this.id = id;
		this.enabled = enabled;
	}

	public static class Notice {
		public boolean enabled;
		public String inTime;
		public String outTime;
		public String noticeTrigger;
		public int deviation;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWearerId() {
		return wearerId;
	}

	public void setWearerId(String wearerId) {
		this.wearerId = wearerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public int getLocationType() {
		return locationType;
	}

	public void setLocationType(int locationType) {
		this.locationType = locationType;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getDeviation() {
		return notice.deviation;
	}
}
