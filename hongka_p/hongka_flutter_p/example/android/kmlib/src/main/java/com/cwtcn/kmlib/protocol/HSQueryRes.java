package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.HSData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.2.17.17	响应查询新版家校提醒信息
 * @author Allen
 */
public class HSQueryRes extends Packet {
	public static final String CMD = "R_Q_LOCATION_DATA";
	public List<HSData> data;
	public HSQueryRes() {
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
		String jsonData = sa[offset++];
		Gson gson = new Gson();
		try {
			data = gson.fromJson(jsonData, new TypeToken<List<HSData>>(){}.getType());
		} catch (Exception e) {
			e.getCause();
		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_HS_INFO_GET, status, msg, data));
		return super.respond(sc);
	}
}
