package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerPowerOnOff;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.2.1.43	推送Tracker开关机时间设置(P_AUTO_POWER) (T1506用)
 * @author Allen
 *
 */
public class TrackerPowerOnOffPush extends Packet {
	public static final String CMD = "P_AUTO_POWER";
	private List<TrackerPowerOnOff> data;

	public TrackerPowerOnOffPush() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		Gson gson = new Gson();
		try {
			status = "0";
			String jsonData = sa[offset++];
			data = gson.fromJson(jsonData, new TypeToken<List<TrackerPowerOnOff>>(){}.getType());
		} catch (Exception e) {
			e.getCause();
		}
	}

	@Override
	public Packet respond(NettyClientManager sav) {
		if(data != null && data.size() > 0) {
			EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_POWER_GET, status, msg, data));
		} else {
			EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_POWER_GET, status, msg));
		}
		return super.respond(sav);
	}
}
