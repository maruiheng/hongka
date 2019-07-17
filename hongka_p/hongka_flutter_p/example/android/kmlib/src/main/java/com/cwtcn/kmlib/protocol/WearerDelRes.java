package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.40删除佩戴对象返回
 * @author Allen
 */
public class WearerDelRes extends Packet {
	public static final String CMD = "R_D_WEARER";
	private String orderId;
	private String jsonStr;
	
	public WearerDelRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}
	
	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		//[R_D_WEARER, 0, 181634180, 860860000060021]
		super.decodeArgs(sa, offset, length);
		status = sa[offset++];
		orderId = sa[offset++];
		try {
			if (!status.equals(NettyClientManager.STR_CODE_OK)) {
				msg = sa[offset++];
			}
		} catch (Exception e) {
			e.getClass();
		}
	}

	@Override
	public Packet respond(NettyClientManager cm) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_DEL, status, msg));
		return super.respond(cm);
	}
}
