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
 * 3.2.1.45	响应查询手表自动开关机时间(R_MQ_AUTO_POWER) (T1506用)
 * @author Allen
 *
 */
public class TrackerPowerOnOffQueryRes extends Packet {
	public final static String CMD = "R_MQ_AUTO_POWER";
	private List<TrackerPowerOnOff> data;

	public TrackerPowerOnOffQueryRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

	//R_MQ_AUTO_POWER&0&20151217170151882
	//&[{"bootTime":"0000","enable":0,"imei":"867327020018825","shutdownTime":"0000","week":""}]
	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		status = sa[offset++];
		offset++;
		try {
			if(NettyClientManager.STR_CODE_OK.equals(status)) {
				String jsonData = sa[offset++];
				Gson gson = new Gson();
				data = gson.fromJson(jsonData, new TypeToken<List<TrackerPowerOnOff>>(){}.getType());
			} else {
				msg = sa[offset];
			}
		} catch (Exception e) {

		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		if(data != null && data.size() > 0) {
			EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_POWER_GET, status, msg, data));
		} else {
			EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_POWER_GET, status, msg));
		}
		return super.respond(sc);
	}
}
