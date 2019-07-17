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
 * 
 * @author Allen
 */
public class AlertAreaGetRes extends Packet {
	public static final String CMD = "R_G_ALERT_AREA";
	private AreaData data;

	public AlertAreaGetRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

	// R_G_ALERT_AREA&0&20140825121212[{"name":"竹园小学","wearerId ":"2344","type":0,"geometries":[{"coordinates":[[22.5340230585227,114.0281403064728],[22.5328735271180,114.0245354175568],[22.5307924545444,114.0288913249970],[22.5340230585227,114.0281403064728]],"shape":"PG","id":"123"}]}]
	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		status = sa[offset++];
		try {
			if (status.equals(NettyClientManager.STR_CODE_OK)) {
				msg = "";
				Gson gson = new Gson();
				String jsonData = sa[++offset].trim();
				data = gson.fromJson(jsonData.trim(),AreaData.class);
			} else {
				msg = sa[offset++];
			}			
		} catch (Exception e) {
			e.getCause();
		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_AREA_GET, status, msg, data));
		return super.respond(sc);
	}
}
