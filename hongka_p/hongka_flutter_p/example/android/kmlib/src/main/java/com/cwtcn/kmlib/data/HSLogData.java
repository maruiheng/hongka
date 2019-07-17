package com.cwtcn.kmlib.data;

/**
 * 家校Log
 * @author Allen
 *
 */
public class HSLogData {
	private long id;
	private String imei;
	/** 日志内容 */
	private String content;
	/** 日志类型（家或学校） */
	private int type;
	/** 日志时间 */
	private String tldTime;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTldTime() {
		return tldTime;
	}

	public void setTldTime(String tldTime) {
		this.tldTime = tldTime;
	}
}
