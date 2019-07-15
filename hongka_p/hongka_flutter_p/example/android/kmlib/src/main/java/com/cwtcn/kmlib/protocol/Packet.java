package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.netty.RequestParameters;

import java.util.List;

/**
 * socket请求实体的基类，包含协议的封装(encode)和解析(decode)
 * @author Allen
 *
 */
public abstract class Packet {

	public static final String TAG = "PACKET";
	// private static final byte[] TITLE = new byte[] { 0x26 };
	public static final byte[] NULL_BUF = new byte[0];
	public static final byte[] SEP = new byte[] { 0x26 };
	public static final String TITLE = "CWT";

	public long id;
	public String cmd;
	/** 存储二进制内容 */
	public byte[] payload;
	/** 回包的返回值 */
	public String status;
	public String msg = "";

	private RequestParameters parameters = new RequestParameters();

	public Packet(String cmd) {
		this(cmd, NULL_BUF);
	}
	
	public Packet(int id, String cmd) {
		this(id, cmd, NULL_BUF);
	}

	public Packet(String cmd, byte[] payload) {
		if (null == cmd) {
			throw new NullPointerException("cmd can not be null");
		}
		this.cmd = cmd;
		this.payload = payload;
	}

	public Packet(int id, String cmd, byte[] payload) {
		if (null == cmd) {
			throw new NullPointerException("cmd can not be null");
		}
		this.id = id;
		this.cmd = cmd;
		this.payload = payload;
	}

	public Packet respond(NettyClientManager cm) {
		return cm.NULL_PACKET;
	}

	public String encodeArgs() {
		return "";
	}

	public void decodeArgs(String[] sa, int offset, int length) {

	}

	protected abstract Packet dup();
	
	public static byte int3(int x) {
		return (byte) (x >> 24);
	}

	public static byte int2(int x) {
		return (byte) (x >> 16);
	}

	public static byte int1(int x) {
		return (byte) (x >> 8);
	}

	public static byte int0(int x) {
		return (byte) (x);
	}

	public static int getCount(byte b0, byte b1) {
		return (changNum(b0) << 8) + changNum(b1);
	}
	
	public static int getCount(byte b0, byte b1, byte b2, byte b3) {
		return changNum(b0) + (changNum(b1) << 8) + (changNum(b2) << 16) + (changNum(b3) << 24);
	}
	
	/**
	 * 将符号位的byte转化为无符号位的byte
	 * @param intValue
	 * @return
	 */
	public static int changNum(byte intValue) {
		int value = 0;
		int temp = intValue % 256;
		if (intValue < 0) {
			value = (int) (temp >= -128 ? 256 + temp : temp);
		} else {
			value = (int) (temp > 127 ? temp - 256 : temp);
		}
		return value;
	}

	protected void put(String value) {
		parameters.put(value);
	}

	protected void put(int value) {
		parameters.put(value);
	}

	protected void put(double value) {
		parameters.put(value);
	}

	protected String listToString(List<String> list) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					sb.append(list.get(i) + ",");
				} else {
					sb.append(list.get(i));
				}
			}
		}
		return sb.toString();
	}

	public String para2String() {
		return parameters.toString();
	}

	public static byte[] byteMerger(byte[] byte1, byte[] byte2){
		byte[] byte3 = new byte[byte1.length + byte2.length];
		System.arraycopy(byte1, 0, byte3, 0, byte1.length);
		System.arraycopy(byte2, 0, byte3, byte1.length, byte2.length);
		return byte3;
	}

	public static byte[] byteMerger3(byte[] byte1, byte[] byte2, byte[] byte3){
		byte[] byte4 = new byte[byte1.length + byte2.length + byte3.length];
		System.arraycopy(byte1, 0, byte4, 0, byte1.length);
		System.arraycopy(byte2, 0, byte4, byte1.length, byte2.length);
		System.arraycopy(byte3, 0, byte4, byte1.length + byte2.length, byte3.length);
		return byte4;
	}
}
