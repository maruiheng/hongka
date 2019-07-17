package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.netty.NettyClientManager;

/**
 * 3.8 心跳返回
 * @author Allen
 */
public class PingRes extends Packet {

	public static final String CMD = "";

	public PingRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}
	
	@Override
	public Packet respond(NettyClientManager cm) {// 处理界面信息
		return super.respond(cm);
	}
}
