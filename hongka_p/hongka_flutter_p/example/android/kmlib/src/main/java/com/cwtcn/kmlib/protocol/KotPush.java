package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;


/**
 * 3.83同一会员登录推送
 * CWTXXXXKOT
 * @author Allen
 *
 */
public class KotPush extends Packet {

	public static final String CMD = "KOT";

	public KotPush() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
	}

	@Override
	public Packet respond(NettyClientManager cm) {
		cm.onKot("");
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_KOT, NettyClientManager.STR_CODE_OK, ""));
		return super.respond(cm);
	}
}
