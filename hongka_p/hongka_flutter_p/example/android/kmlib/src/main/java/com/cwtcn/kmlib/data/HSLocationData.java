package com.cwtcn.kmlib.data;

/**
 * 家校位置信息
 * @author Allen
 *
 */
public class HSLocationData {
	private String id;
	/** 位置名称 */
	private String name;
	private String imei;
	private long wearerId;
	/** 家校类型 1：家 2：学校 */
	private int type;
	private double longitude;
	private double latitude;
	/***地图类型，默认为4 */
	private int coordinateType = 4;
	/** 位置地址 */
	private String address = "";
	/** 误差范围 */
	private String deviation = "";
	/** WIFI MAC*/
	private String mac = "";
	/** WIFI SSID */
	private String ssid = "";
	private String createdTime;//创建时间
	private String lastUpdatedTime;//最后更新时间

	/**
	 * 创建学校位置
	 * @param type
	 * @param name
	 * @param imei
	 * @param longitude
	 * @param latitude
	 * @param address
	 */
	public HSLocationData(int type, String name, String imei, double longitude, double latitude, String address) {
		this.type = type;
		this.name = name;
		this.imei = imei;
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
	}

	/**
	 * 创建家的位置
	 * @param type
	 * @param name
	 * @param imei
	 * @param longitude
	 * @param latitude
	 * @param address
	 * @param mac
	 * @param ssid
	 */
	public HSLocationData(int type, String name, String imei, double longitude, double latitude, String address, String mac, String ssid) {
		this.type = type;
		this.name = name;
		this.imei = imei;
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
		this.mac = mac;
		this.ssid = ssid;
	}

	/**
	 * 编辑学校位置
	 * @param id
	 * @param type
	 * @param name
	 * @param imei
	 * @param wearerId
	 * @param longitude
	 * @param latitude
	 * @param address
	 */
	public HSLocationData(String id, int type, String name, String imei, String wearerId, double longitude, double latitude, String address) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.imei=imei;
		this.wearerId = Long.valueOf(wearerId);
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
	}

	/**
	 *  编辑家的位置
	 * @param id
	 * @param type
	 * @param name
	 * @param imei
	 * @param wearerId
	 * @param longitude
	 * @param latitude
	 * @param address
	 * @param mac
	 * @param ssid
	 */
	public HSLocationData(String id, int type, String name, String imei, String wearerId, double longitude, double latitude, String address, String mac, String ssid){
		this.id = id;
		this.type = type;
		this.name = name;
		this.imei=imei;
		this.wearerId = Long.valueOf(wearerId);
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
		this.mac = mac;
		this.ssid = ssid;
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

	public long getWearerId() {
		return wearerId;
	}

	public void setWearerId(long wearerId) {
		this.wearerId = wearerId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public int getCoordinateType() {
		return coordinateType;
	}

	@Override
	public boolean equals(Object o) {
		if(id.equals(((HSLocationData)o).id)) {
			return true;
		} else {
			return false;
		}
	}
}
