package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.BindCodeData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.32获取绑定手表二维码返回应答
 * @author Allen
 */
public class WearerQRCodeGetRes extends Packet {
	public static final String CMD = "R_G_BINDING_VALIDATION_CODE";
	private BindCodeData data;
	private String orderId;
	private String jsonStr;

	public WearerQRCodeGetRes() {
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
		orderId = sa[offset++];
		if (status.equals(NettyClientManager.STR_CODE_OK)) {
			jsonStr = sa[offset++];
			Gson gson = new Gson();
			data = gson.fromJson(jsonStr, BindCodeData.class);
		} else {
			msg = sa[offset++];
		}
	}	
	
	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_QR_GET, status, msg, data));
		return super.respond(sc);
	}
}
