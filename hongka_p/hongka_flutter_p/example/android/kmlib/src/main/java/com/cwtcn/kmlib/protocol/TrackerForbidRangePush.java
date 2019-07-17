package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.TrackerMuteResp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.2.1.8推送Tracker静音时段(P_FORBID_RANGE)
 * @author Allen
 */
public class TrackerForbidRangePush extends Packet {
	public static final String CMD = "P_FORBID_RANGE";
	private List<TrackerMuteResp> data;

	public TrackerForbidRangePush() {
		super(CMD);
	}
	
	@Override
	protected Packet dup() {
		return this;
	}
	
	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		
		try{
			msg = sa[offset++];
			status = "0"; 
			Gson gson = new Gson();
			data = gson.fromJson(msg, new TypeToken<List<TrackerMuteResp>>(){}.getType());

		} catch (Exception ex) {

		}
	}
	
	@Override
	public Packet respond(NettyClientManager sc) {
		if(data != null && data.size() > 0) {
			EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_CLASS_MODE_GET, status, msg, data.get(0).imei, data.get(0).mrs));
		}
		return super.respond(sc);
	}
}
