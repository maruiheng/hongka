package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerStepData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.56获取活动量返回
 * @author Allen
 */
public class TrackerStepGetRes extends Packet {
	public static final String CMD = "R_G_DAILY_ACTIVITY";
	private List<TrackerStepData> data;

	public TrackerStepGetRes() {
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
		if(status.equals(NettyClientManager.STR_CODE_OK)) {
			String jsonData = sa[offset++];
			try {
				Gson gson = new Gson();
				data = gson.fromJson(jsonData, new TypeToken<List<TrackerStepData>>(){}.getType());
			} catch (Exception e) {
				e.getCause();
			}
		} else {
			msg = sa[offset++];
		}
	}
	
	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_STEPS_GET, status, msg, data));
		return super.respond(sc);
	}
}
