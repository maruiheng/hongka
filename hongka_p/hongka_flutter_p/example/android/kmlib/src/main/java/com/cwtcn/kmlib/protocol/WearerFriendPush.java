package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMContactManager;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.FriendPushRespData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 推送蓝牙朋友
 * @author Allen
 *
 */
public class WearerFriendPush extends Packet {
	public static final String CMD = "P_FRI";
	private String imei;

	public WearerFriendPush() {
		super(CMD);
	}


	@Override
	protected Packet dup() {
		return this;
	}
	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		String jsonData = sa[offset++];
		Gson gson = new Gson();
		try {
			List<FriendPushRespData> data = gson.fromJson(jsonData, new TypeToken<List<FriendPushRespData>>(){}.getType());
			if(data != null && data.size() > 0) {
				if(data.get(0).friends != null && data.get(0).friends.size() > 0) {
					imei = data.get(0).friends.get(0).imei;
					KMContactManager.getInstance().setFriends(data.get(0).friends.get(0).imei, data.get(0).friends.get(0).friends);
					if (data.get(0).reason == 0) {
						msg = data.get(0).message;
					}
				}
			}
		} catch (Exception e) {
			e.getCause();
		}
	}

	@Override
	public Packet respond(NettyClientManager sav) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_FRIEND_GET, status, msg, KMContactManager.getInstance().getFriends(imei)));
		return super.respond(sav);
	}

}