package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.1.10响应新版设置Tracker工作模式(R_S_WORK_MODE_HV)
 * @author Allen
 *
 */
public class TrackerWorkModeHVSetRes extends Packet {
	public final static String CMD = "R_S_WORK_MODE_HV";

	public TrackerWorkModeHVSetRes() {
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
		msg = sa[offset++];
	}
	
	@Override
	public Packet respond(NettyClientManager sav) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WORK_MODE_SET, status, msg));
		return super.respond(sav);
	}
}
