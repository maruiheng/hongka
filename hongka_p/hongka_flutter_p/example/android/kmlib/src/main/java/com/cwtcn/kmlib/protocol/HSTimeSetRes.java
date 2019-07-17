package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 创建新版位置提醒返回
 * @author Allen
 *
 */
public class HSTimeSetRes extends Packet {
	public static final String CMD = "R_C_LOCATION_TIMES";

	public HSTimeSetRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		try {
			status = sa[offset++];
			if (status.equals(NettyClientManager.STR_CODE_OK)) {
				offset++;
				msg = "";
			} else {
				offset++;
				msg = sa[offset++];
			}
		} catch (Exception e) {
			e.getCause();
		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_HS_TIME_SET, status, msg));
		return super.respond(sc);
	}
}
