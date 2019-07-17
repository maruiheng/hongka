package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.18.11响应设置佩戴对象报警开关（R_ENABLE_ALERT_AREA）
 * @author Allen
 */
public class AlertAreaEnableRes extends Packet {
	public static final String CMD = "R_ENABLE_ALERT_AREA";
	private String msg;

	public AlertAreaEnableRes() {
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
		if (status.equals(NettyClientManager.STR_CODE_OK)) {
			msg = "";
		}else{
			msg = sa[offset++];
		}
	}	

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_AREA_ENABLE, status, msg));
		return super.respond(sc);
	}
}
