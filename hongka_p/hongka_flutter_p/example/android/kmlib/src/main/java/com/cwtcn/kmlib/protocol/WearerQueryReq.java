package com.cwtcn.kmlib.protocol;


import com.cwtcn.kmlib.util.DateUtil;

/**
 * 3.41查询所有佩戴对象
 * @author Allen
 */
public class WearerQueryReq extends Packet {
	public static final String CMD = "Q_WEARER";

	public WearerQueryReq() {
		super(CMD);
	}
	
	@Override
	protected Packet dup() {
		return this;
	}
	
	@Override
	public String encodeArgs() {
		put(CMD);
		put(DateUtil.getDalayTimeId());
		return para2String();
	}
}
