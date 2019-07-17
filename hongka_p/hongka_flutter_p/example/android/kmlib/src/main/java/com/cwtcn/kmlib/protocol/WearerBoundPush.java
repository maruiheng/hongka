package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMWearerManager;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.json.JSONObject;

/**
 * 配带人数据变化推送（P_WADC）
 * @author Allen
 */
public class WearerBoundPush extends Packet {
	public static final String CMD = "P_WADC";
	private String msg;

	public WearerBoundPush() {
		super(CMD);
	}
	
	@Override
	protected Packet dup() {
		return this;
	}
	
	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		try {
			KMWearerManager.getInstance().wearerQuery();
			JSONObject mObject = new JSONObject(sa[offset]);
			if(mObject.has("action")) {//action:1为解除绑定，2为转移管理员
				status = mObject.getString("action");
				msg = mObject.getString("desc");
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public Packet respond(NettyClientManager cm) {
		return super.respond(cm);
	}
}
