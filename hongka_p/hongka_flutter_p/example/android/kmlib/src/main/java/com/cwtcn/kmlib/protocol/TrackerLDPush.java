package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMLocationManager;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.TLDRespData;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.76推送手表最新数据
 * @author Allen
 */
public class TrackerLDPush extends Packet {
	public static final String CMD = "TLD";
	private TLDRespData para;

	public TrackerLDPush() {
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
			Gson gson = new Gson();
			String jsonData = sa[offset++];
			para = gson.fromJson(jsonData, TLDRespData.class);
			if(para != null && para.data.size() > 0) {
				KMLocationManager.getInstance().mTLDMap.put(para.data.get(0).getImei(), para.data.get(0));
			}
		} catch (Exception e) {

		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_TLD_GET, status, msg, (para != null && para.data.size() > 0) ? para.data.get(0) : null));
		return super.respond(sc);
	}
}
