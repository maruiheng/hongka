package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMWearerManager;
import com.cwtcn.kmlib.data.Wearer;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.2.15.22响应手表管理员获取该表所有绑定人（R_WEARER_GET_ORDER_BINDINGS）
 * @author Allen
 */
public class WearerBoundGetRes extends Packet {
	public static final String CMD = "R_WEARER_GET_ALL_BINDINGS";
	private String imei = "";

	public WearerBoundGetRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}

	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		Gson gson = new Gson();
		try {
			status = sa[offset++];
			if ((NettyClientManager.STR_CODE_OK).equals(status)) {
				offset++;
				msg = sa[offset++];
				List<Wearer> data = gson.fromJson(msg, new TypeToken<List<Wearer>>(){}.getType());
				if(data != null && data.size() > 0) {
					imei = data.get(0).getImei();
					KMWearerManager.getInstance().mWearerBinders.put(data.get(0).getImei(), data);
				}
			}
		} catch (Exception e) {

		}
	}

	@Override
	public Packet respond(NettyClientManager cm) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_BINDERS_GET, status, msg, KMWearerManager.getInstance().mWearerBinders.get(imei)));
		return super.respond(cm);
	}
}
