package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.HSOkData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.2.17.14	修改新版家校提醒时间信息
 * @author Allen
 */
public class HSLocationUpdateRes extends Packet {
	public static final String CMD = "R_U_LOCATION_INFO";
	public List<HSOkData> data;

	public HSLocationUpdateRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		status = sa[offset++];
		if(!NettyClientManager.STR_CODE_OK.equals(status)) {
			msg = sa[++offset];
		}else {
			String jsonData = sa[++offset];
			Gson gson = new Gson();
			try {
				data = gson.fromJson(jsonData, new TypeToken<List<HSOkData>>(){}.getType());
			} catch (Exception e) {
				e.getCause();
			}
		}
	}
	
	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_HS_LOCATION_UPDATE, status, msg,data));
		return super.respond(sc);
	}
}