package com.cwtcn.kmlib.protocol;


import com.cwtcn.kmlib.data.WishesData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.TrackerWishesResp;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.2.27响应查询Tracker许愿信息(R_Q_WISHES)
 * @author Allen
 */
public class TrackerWishesGetRes extends Packet {
	public static final String CMD = "R_Q_WISHES";
	public TrackerWishesResp data;

	public TrackerWishesGetRes() {
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
			if (status.equals(NettyClientManager.STR_CODE_OK)) {
				String jsonData = sa[++offset];
				Gson gson = new Gson();
				data = gson.fromJson(jsonData, TrackerWishesResp.class);
			} else {
				msg = sa[++offset];
			}
		} catch (Exception e) {
			e.getCause();
		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent<WishesData>(KMEventConst.EVENT_WISHES_GET, status, msg, data != null ? data.list : null));
		return super.respond(sc);
	}
}
