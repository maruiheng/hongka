package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.HSTimeData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 查询位置提醒返回
 * @author Allen
 */
public class HSTimeUpdateRes extends Packet {
	public static final String CMD = "R_U_LOCATION_TIMES";
	private List<HSTimeData> data;

	public HSTimeUpdateRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		status = sa[offset++];
		offset++;
		String jsonData = sa[offset++];
		Gson gson = new Gson();
		try {
			data = gson.fromJson(jsonData, new TypeToken<List<HSTimeData>>(){}.getType());
		} catch (Exception e) {
			e.getCause();
		}
	}
	
	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_HS_TIME_UPDATE, status, msg));
		return super.respond(sc);
	}
}
