package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.netty.NettyClientManager;

/**
 * 3.30设置手表时间返回应答
 * @author Allen
 */
public class TrackerTimeSetRes extends Packet {
	public static final String CMD = "R_S_DATETIME";
	private String msg;

	public TrackerTimeSetRes() {
		super(CMD);
	}
	
	@Override
	protected Packet dup() {
		return this;
	}
	
	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		status = sa[offset++];
		offset++;
		if (status.equals(NettyClientManager.STR_CODE_OK)) {

		} else {
			msg = sa[offset++];
		}
	}

	@Override
	public Packet respond(NettyClientManager cm) {
		return super.respond(cm);
	}
}
