package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.15.26响应手表管理员解除其它成员绑定（R_WEARER_ADMIN_DELETE）
 * @author Allen
 */
public class WearerUnbindRes extends Packet {
	public static final String CMD = "R_WEARER_ADMIN_DELETE";
	private String msg;

	public WearerUnbindRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		//R_WEARER_ADMIN_DELETE&0&123456789&操作成功
		try {
			status = sa[offset++];
			offset++;
			msg = sa[offset++];
		} catch (Exception e) {

		}
	}

	@Override
	public Packet respond(NettyClientManager cm) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_UNBIND, status, msg));
		return super.respond(cm);
	}
}
