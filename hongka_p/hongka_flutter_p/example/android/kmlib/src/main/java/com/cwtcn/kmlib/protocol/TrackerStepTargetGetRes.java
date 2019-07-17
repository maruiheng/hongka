package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * 3.54获取活动目标返回
 * @author Allen
 */
public class TrackerStepTargetGetRes extends Packet {
	public static final String CMD = "R_G_ACT_CFG";
	private int stepsTarget;

	public TrackerStepTargetGetRes() {
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
				JSONObject mJsonObject = new JSONObject(jsonData);
				stepsTarget = mJsonObject.getInt("stepsTarget");
			} catch (Exception e) {
				
			}
		} else {
			msg = sa[offset++];
		}
	}

	
	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_STEP_TARGET_GET, status, msg, stepsTarget));
		return super.respond(sc);
	}
}
