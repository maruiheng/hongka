package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.HSData;
import com.cwtcn.kmlib.data.HSOkData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.2.17.13	响应创建新版家校提醒时间信息
 * @author Allen
 *
 */
public class HSLocationSetRes extends Packet {
	public static final String CMD = "R_C_LOCATION_INFO";
	public List<HSOkData> data;

	public HSLocationSetRes() {
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
				offset++;//移动指针
				msg = "";
				String jsonData = sa[offset];
				Gson gson = new Gson();
				try {
					data = gson.fromJson(jsonData, new TypeToken<List<HSOkData>>(){}.getType());
				} catch (Exception e) {
					e.getCause();
				}
			} else {
				offset++;//移动指针
				msg = sa[offset++];
			}
		} catch (Exception e) {

		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_HS_LOCATION_SET, status, msg,data));
		return super.respond(sc);
	}
}
