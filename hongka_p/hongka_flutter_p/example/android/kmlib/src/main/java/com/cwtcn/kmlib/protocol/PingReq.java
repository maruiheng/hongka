package com.cwtcn.kmlib.protocol;

/**
 * 手表心跳
 * @author Allen
 */
public class PingReq extends Packet {

	public static final String CMD = "";

	public PingReq() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

}
