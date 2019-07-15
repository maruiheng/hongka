package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMConstants;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

public class TrackerResetRes extends Packet {
	public static final String CMD = "R_RESET_TRACKER";
	
	private String msg;
	@Override
	protected Packet dup() {
		return this;
	}

	public TrackerResetRes() {
		super(CMD);
	}

	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		status = sa[offset++];
		offset++;
		if(sa.length > offset) {
			msg = sa[offset++];
		}
	}

	
	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_RESET, status, msg));
		return super.respond(sc);
	}
}
