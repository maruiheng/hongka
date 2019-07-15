package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.TrackerSMSLocResp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.2.1.31	推送Tracker设置短信上报位置开关
 * @author Allen
 *
 */
public class TrackerSMSLocPush extends Packet {
	public static final String CMD = "P_SMS_LOC";
	private List<TrackerSMSLocResp> data;

	public TrackerSMSLocPush() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

	//	P_SMS_LOC&[{"imei":"886200000411231","sms_location":"1"}]
	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		try {
			status = "0";
			Gson gson = new Gson();
			String jsonData = sa[offset++];
			data = gson.fromJson(jsonData, new TypeToken<List<TrackerSMSLocResp>>(){}.getType());
		} catch (Exception e) {

		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		if(data != null && data.size() > 0) {
			EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_SMSLOC_GET, status, msg, data.get(0).imei, data.get(0).sms_location));
		}
		return super.respond(sc);
	}
}
