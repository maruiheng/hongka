package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.HSLogData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 家校提醒推送
 * @author Allen
 *
 */
public class HSLogPush extends Packet {

	public static final String CMD = "P_HS";
	public List<HSLogData> data;

	public HSLogPush() {
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
			Gson gson = new Gson();
			String jsonData = sa[offset++];
			data = gson.fromJson(jsonData, new TypeToken<List<HSLogData>>(){}.getType());
		} catch (Exception e) {

		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_HS_LOG_GET, status, msg, data));
		return super.respond(sc);
	}
}
