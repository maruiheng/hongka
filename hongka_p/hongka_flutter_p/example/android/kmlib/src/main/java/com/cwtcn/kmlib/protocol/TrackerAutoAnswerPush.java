package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerAutoAnswer;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.2.1.34推送Tracker设置翻腕开关(P_AA)
 * @author Allen
 */
public class TrackerAutoAnswerPush extends Packet {
	public static final String CMD = "P_AA";
	private List<TrackerAutoAnswer> data;

	public TrackerAutoAnswerPush() {
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
			String mResponse = sa[offset++];
			status = "0";
			Gson gson = new Gson();
			data = gson.fromJson(mResponse, new TypeToken<List<TrackerAutoAnswer>>(){}.getType());
		} catch(Exception ex) {
			ex.getCause();
		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		if(data != null && data.size() > 0) {
			EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_AUTOANSWER_SET, status, msg, data.get(0)));
		}
		return super.respond(sc);
	}
}
