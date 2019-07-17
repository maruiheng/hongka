package com.cwtcn.kmlib.data;

/***
 * 手表的头像数据
 * @author Allen
 *
 */
public class WearerAvatar {
	//{"wearerId":"1234567","dataLength":102400,"filename":"123.jpg","lut":"20140505101010"}
	/** 佩戴ID */
	private String wearerId;
	/** 数据长度 */
	private String dataLength;
	/** 文件名 */
	private String filename;
	/** 更新时间 */
	private String lut;

	public String getWearerId() {
		return wearerId;
	}

	public void setWearerId(String wearerId) {
		this.wearerId = wearerId;
	}

	public String getDataLength() {
		return dataLength;
	}

	public void setDataLength(String dataLength) {
		this.dataLength = dataLength;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getLut() {
		return lut;
	}

	public void setLut(String lut) {
		this.lut = lut;
	}
}
