package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.AreaData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.50获取报警区域返回
 * @author Allen
 */
public class AlertAreaPush extends Packet {
	public static final String CMD = "P_ALERT";
	private AreaData data;
	private String msg;

	public AlertAreaPush() {
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
			String jsonData = sa[++offset].trim();
			data = gson.fromJson(jsonData.trim(), new TypeToken<AreaData>(){}.getType());
		} catch (Exception e) {

		}
	}	

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_AREA_GET, status, msg, data));
		return super.respond(sc);
	}
}
