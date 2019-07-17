package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.AreaData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.48创建报警区域返回
 * @author Allen
 */
public class AlertAreaSetRes extends Packet {
	public static final String CMD = "R_C_ALERT_AREA";
	private AreaData para;

	public AlertAreaSetRes() {
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
		if (status.equals(NettyClientManager.STR_CODE_OK)) {
			String jsonData = sa[++offset];
			Gson gson = new Gson();
			try {
				para = gson.fromJson(jsonData, new TypeToken<AreaData>(){}.getType());
			} catch (Exception e) {

			}
			msg ="";
		}else{
			msg = sa[++offset];
		}
	}	

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_AREA_CREATE, status, msg, para.getId()));
		return super.respond(sc);
	}
}
