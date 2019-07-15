package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.DynamicCodeData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.34获取手表动态验证码返回应答
 * @author Allen
 */
public class WearerVCCodeGetRes extends Packet {
	public static final String CMD = "R_G_TRACKER_VALIDATION_CODE";
	private String imei;
	private String jsonStr;// 验证码json串
	private String orderId;// 命令id
	private DynamicCodeData data;

	public WearerVCCodeGetRes() {
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
				Gson gson = new Gson();
				try {
					WearerVCCodeGetRes mData = gson.fromJson(jsonStr, new TypeToken<DynamicCodeData>(){}.getType());
					data = mData.data;
				} catch (Exception e) {

				}
			} else {
				msg = sa[offset++];
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public Packet respond(NettyClientManager cm) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_VC_GET, status, msg));
		return super.respond(cm);
	}
}
