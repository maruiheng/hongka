package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.data.TrackerPhoneFee;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

/**
 * 推送资费
 * @author Allen
 *
 */
public class TrackerPhoneFeePush extends Packet {
	public static final String CMD = "P_TARIFFS";
	private TrackerPhoneFee data;

	public TrackerPhoneFeePush() {
		super(CMD);
	}


	@Override
	protected Packet dup() {
		return this;
	}

	//P_TARIFFS &{"imei":"860860000000108","selType":"0","result":"结果"}
	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		String jsonData = sa[offset++];
		Gson gson = new Gson();
		try {
			data = gson.fromJson(jsonData, new TypeToken<TrackerPhoneFee>(){}.getType());
		} catch (Exception e) {
			e.getCause();
		}
	}


	@Override
	public Packet respond(NettyClientManager sc) {
		if(data != null) {
			EventBus.getDefault().post(new KMEvent<TrackerPhoneFee>(KMEventConst.EVENT_PHONE_FEE_PUSH, status, msg, data));
		}
		return super.respond(sc);
	}

}
