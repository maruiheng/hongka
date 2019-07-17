package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerConnectMode;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.2.1.28推送Tracker设置链接方式(P_CM)
 * @author Allen
 */
public class TrackerConnectModePush extends Packet {
	public static final String CMD = "P_CM";
	private List<TrackerConnectMode> data;

	public TrackerConnectModePush() {
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
			String jsonString = sa[offset++];
			status = "0";
			Gson gson = new Gson();
			data = gson.fromJson(jsonString, new TypeToken<List<TrackerConnectMode>>(){}.getType());
		} catch(Exception ex) {
			ex.getCause();
		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		if(data != null && data.size() > 0) {
			EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_CONNECT_MODE_GET, status, msg));
		}
		return super.respond(sc);
	}
}
