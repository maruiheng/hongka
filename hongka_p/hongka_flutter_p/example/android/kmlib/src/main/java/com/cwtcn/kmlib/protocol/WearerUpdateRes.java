package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.38更新佩戴对象返回
 * @author Allen
 */
public class WearerUpdateRes extends Packet {
	public static final String CMD = "R_U_WEARER";
	private String orderId;
	private String jsonStr;// json串
	private String errorStr;

	public WearerUpdateRes() {
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
			orderId = sa[offset++];
			if (status.equals(NettyClientManager.STR_CODE_OK)) {
				jsonStr = sa[offset++];
			} else {
				errorStr = sa[offset++];
			}
		} catch (Exception e) {

		}
	}

	@Override
	public Packet respond(NettyClientManager cm) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_UPDATE, status, msg));
		return super.respond(cm);
	}
}
