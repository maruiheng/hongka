package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.1.19响应设置Tracker闹铃(R_ S_ALARM_RING)
 * @author Allen
 */
public class TrackerAlarmSetRes extends Packet {
	public static final String CMD = "R_S_ALARM_RING";

	public TrackerAlarmSetRes() {
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
		if(sa.length > offset) {
			msg = sa[offset++];
		}else{
			msg = "";
		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_ALARM_SET, status, msg));
		return super.respond(sc);
	}
}
