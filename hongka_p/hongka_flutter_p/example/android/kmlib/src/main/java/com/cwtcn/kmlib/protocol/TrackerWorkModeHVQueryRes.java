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
 * 3.2.1.13响应新版查询Tracker工作模式(R_Q_WORK_MODE_HV)
 * @author Allen
 *
 */
public class TrackerWorkModeHVQueryRes extends Packet {
	public final static String CMD = "R_Q_WORK_MODE_HV";
	private List<TrackerWorkMode> data;
	
	public TrackerWorkModeHVQueryRes() {
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
			status = sa[offset++];
			if (NettyClientManager.STR_CODE_OK.equals(status)) {
				offset++;
				String jsonString = sa[offset++];
				Gson gson = new Gson();
				data = gson.fromJson(jsonString, new TypeToken<List<TrackerWorkMode>>(){}.getType());
			} else {
				msg = sa[offset++];
			}
		} catch (Exception e) {
			e.getClass();
		}
	}
	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WORK_MODE_GET, status, msg, (data != null && data.size() > 0) ? data.get(0) : null));
		return super.respond(sc);
	}
}
