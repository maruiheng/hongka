package com.cwtcn.kmlib.data;

/**
 * 自动应答模式实体
 * @author Allen
 *
 */
public class TrackerAutoAnswer {
	//{"auto_time":0,"auto_type":0,"id":157214883845656576,"imei":"867327020000856"}
	public String imei;
	/** 自动应答类型 1 自动接听，2 翻腕接听*/
	public int auto_type = 0;
	/** 自动应答时间 模式2下为来电多少秒后检测抖动消息（默认3秒） */
	public int auto_time = 3;
	
	public long id;
}
