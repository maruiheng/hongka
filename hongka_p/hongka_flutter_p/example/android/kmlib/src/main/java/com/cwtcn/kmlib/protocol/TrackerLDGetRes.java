package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMLocationManager;
import com.cwtcn.kmlib.data.TrackerLeastData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.TLDRespData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.78获取手表最新数据返回
 * 
 * @author Allen
 */
public class TrackerLDGetRes extends Packet {
	public static final String CMD = "R_Q_TLD";
	private List<TrackerLeastData> para;

	public TrackerLDGetRes() {
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
			try {
				Gson gson = new Gson();
				String jsonData = sa[++offset];
				para = gson.fromJson(jsonData, new TypeToken<List<TrackerLeastData>>(){}.getType());
				if(para != null && para.size() > 0) {
					KMLocationManager.getInstance().mTLDMap.put(para.get(0).getImei(), para.get(0));
				}
			} catch (Exception e) {
					e.printStackTrace();
			}
		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_TLD_GET, status, msg, (para != null && para.size() > 0) ? para.get(0) : null));
		return super.respond(sc);
	}

}
