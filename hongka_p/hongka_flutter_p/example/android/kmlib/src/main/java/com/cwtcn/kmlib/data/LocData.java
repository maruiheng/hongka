package com.cwtcn.kmlib.data;

import java.util.List;

/***
 * 定位数据
 * 
 * @author Allen
 * 
 */
public class LocData {
	/**
	 * TLD:{"i":"150724152007","id":"null","isStatic":false,"o":121.5230048,"p":
	 * 100,"radius":"300","t":"2","u":38.861262,"wifi":[{},{}]}
	 ****/
	/** 时间戳 **/
	public String i;
	/** 经度 **/
	public double o;
	/** 纬度 **/
	public double u;
	/** 位置类型,"0":GPS,"1":基站定位,"2":WIFI定位 **/
	public String t;
	/** 误差半径 **/
	public String radius;
	/** WIFI列表 */
	public List<WiFiData> wifi;
	/** 手表信号 */
	public String signal = "0";
	private int p;
	
	public String address;
	/** wifi修正标识 0为未修正，1为修正 */
	public int wt = 0;

}
