package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.15.24响应手表管理员变更（R_WEARER_CHANGE_ADMIN）
 * @author Allen
 */
public class WearerAdminChangeRes extends Packet {
	public static final String CMD = "R_WEARER_CHANGE_ADMIN";

	public WearerAdminChangeRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		//R_WEARER_CHANGE_ADMIN&0&123456789&操作成功
		try {
			status = sa[offset++];
			offset++;
			msg = sa[offset++];
		} catch (Exception e) {

		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_ADMIN_TRANSFER, status, msg));
		return super.respond(sc);
	}
}
