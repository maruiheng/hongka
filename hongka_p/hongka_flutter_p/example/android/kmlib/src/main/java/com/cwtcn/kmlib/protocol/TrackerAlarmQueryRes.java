package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.TrackerAlarmResp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.2.1.22响应查询Tracker闹铃(R_Q_ALARM_RING)
 * @author Allen
 */
public class TrackerAlarmQueryRes extends Packet {
	public static final String CMD = "R_Q_ALARM_RING";
	private List<TrackerAlarmResp> data;

	public TrackerAlarmQueryRes() {
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
		try {
			if(status.equals(NettyClientManager.STR_CODE_OK)) {
				String jsonData = sa[offset++];
				msg = jsonData;
				Gson gson = new Gson();
				data = gson.fromJson(msg, new TypeToken<List<TrackerAlarmResp>>(){}.getType());
			} else {
				msg = sa[offset++];
			}
		} catch (Exception e) {

		}
	}
		
	@Override
	public Packet respond(NettyClientManager sc) {
		if(data != null && data.size() > 0) {
			EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_ALARM_GET, status, msg, data.get(0).imei, data.get(0).ars));
		} else {
			EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_ALARM_GET, status, msg));
		}
		return super.respond(sc);
	}
}
