package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerWorkMode;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/***
 * 3.2.1.11推送新版Tracker工作模式(P_WORK_MODE_HV)
 * @author Allen
 *
 */
public class TrackerWorkModeHVPush extends Packet {
	public static final String CMD = "P_WORK_MODE_HV";
	private List<TrackerWorkMode> data;

	public TrackerWorkModeHVPush() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		String jsonString = sa[offset++];
		try {
			status = "0";
			Gson gson = new Gson();
			data = gson.fromJson(jsonString, new TypeToken<List<TrackerWorkMode>>(){}.getType());
		} catch (Exception e) {

		}
	}

	@Override
	public Packet respond(NettyClientManager sav) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WORK_MODE_GET, status, msg, (data != null && data.size() > 0) ? data.get(0) : null));
		return super.respond(sav);
	}

}
