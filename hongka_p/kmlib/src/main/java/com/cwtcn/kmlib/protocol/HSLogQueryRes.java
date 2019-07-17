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
 * 查询家校提醒提示信息
 * 
 * @author Allen
 */
public class HSLogQueryRes extends Packet {
	public static final String CMD = "R_Q_ALERT_PUSH_HIS";
	public List<HSLogData> data;

	public HSLogQueryRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		status = sa[offset++];
		String jsonID = sa[offset++];
		String jsonData = sa[offset++];
		Gson gson = new Gson();
		try {
			data = gson.fromJson(jsonData, new TypeToken<List<HSLogData>>(){}.getType());
		} catch (Exception e) {
			e.getCause();
		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_HS_LOG_GET, status, msg, data));
		return super.respond(sc);
	}
}
