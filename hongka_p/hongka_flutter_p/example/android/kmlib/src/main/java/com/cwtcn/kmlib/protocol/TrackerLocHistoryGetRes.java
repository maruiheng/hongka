package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.LocHistoryCountData;
import com.cwtcn.kmlib.data.LocHistoryData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.82获取手表历史位置返回
 * @author Allen
 */
public class TrackerLocHistoryGetRes extends Packet {
	public static final String CMD = "R_Q_LOCATION";
	private LocHistoryCountData data;

	public TrackerLocHistoryGetRes() {
		super( CMD);
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
		if(NettyClientManager.STR_CODE_OK.equals(status)) {
			try {
				String res = sa[offset++];
				Gson gson = new Gson();
				data = gson.fromJson(res, LocHistoryCountData.class);
			} catch (Exception e) {
				e.getCause();
			}
		} else {
			msg = sa[offset++];
		}
	}

	
	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_LOC_HISTORY_GET, status, msg, data));
		return super.respond(sc);
	}
}
