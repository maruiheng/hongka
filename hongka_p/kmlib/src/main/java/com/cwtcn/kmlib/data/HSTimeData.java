package com.cwtcn.kmlib.data;

/**
 * 家校时间信息
 * @author Allen
 *
 */
public class HSTimeData {
	//{"createdTime":"20170314141713","eachEnabled":1,"enabled":1,"endTime":"1700","id":373236217388879872,"imei":"860860000031034","lastUpdateTime":"20170314141713","name":"inschoolam","startTime":"0700","type":1,"wearerId":360489142947569664,"weekType":"0111110"}
	private String id;
	private String name;
	private String imei;
	private String wearerId;
	/** 时间段类型 1:上午在校时间段，2：下午在校时间段，3：离校到家时间段， 4：午休时间段*/
	private int type;
	/** 是否开启家校提醒 0：关闭， 1：开启 */
	private int enabled;
	/** 是否开启午休提醒 0：关闭， 1：开启 */
	private int eachEnabled;
	/** 星期标识0111110 */
	private String weekType;
	private String startTime;
	private String endTime;
	private String createdTime;
	private String lastUpdatedTime;

	public HSTimeData(String name, String imei, int type, int enabled, String weekType, String startTime, String endTime){
		this.name = name;
		this.imei = imei;
		this.type = type;
		this.enabled = enabled;
		this.weekType = weekType;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public HSTimeData(String name, String imei,String wearerId, int type, int enabled, int eachEnabled, String weekType, String startTime, String endTime){
		this.name = name;
		this.imei = imei;
		this.type = type;
		this.enabled = enabled;
		this.eachEnabled = eachEnabled;
		this.weekType = weekType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.wearerId = wearerId;
	}

	public HSTimeData(String id, String name, String imei, int type, int enabled, String weekType, String startTime, String endTime){
		this.id = id;
		this.name = name;
		this.imei = imei;
		this.type = type;
		this.enabled = enabled;
		this.weekType = weekType;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public HSTimeData(String id, String name, String imei,String wearerId, int type, int enabled, int eachEnabled, String weekType, String startTime, String endTime){
		this.id = id;
		this.name = name;
		this.imei = imei;
		this.type = type;
		this.enabled = enabled;
		this.eachEnabled = eachEnabled;
		this.weekType = weekType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.wearerId = wearerId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getWearerId() {
		return wearerId;
	}

	public void setWearerId(String wearerId) {
		this.wearerId = wearerId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getEachEnabled() {
		return eachEnabled;
	}

	public void setEachEnabled(int eachEnabled) {
		this.eachEnabled = eachEnabled;
	}

	public String getWeekType() {
		return weekType;
	}

	public void setWeekType(String weekType) {
		this.weekType = weekType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
