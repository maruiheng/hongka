package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMContactManager;
import com.cwtcn.kmlib.data.FriendData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.FriendRespData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/***
 * 查询蓝牙交友请求返回
 * @author Allen
 *
 */
public class WearerFriendQueryRes extends Packet {
	public final static String CMD = "R_Q_FRI";
	private String imei;
	private List<FriendData> data;
	
	public WearerFriendQueryRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}
	//[R_Q_FRI, 0, 9059, [{"friends":[{"id":167809951241084928,"imei":"860860000000103","name":"好友2","picId":9},{"friendMobile":"18666666666","id":167703470512545792,"imei":"860860000030075","name":"С�쵰","picId":0}],"imei":"860861000000009","lut":1440494794000}]]
	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		status = sa[offset++];
		offset++;
		String jsonData = sa[offset++];
		Gson gson = new Gson();
		try {
			List<FriendRespData> mData = gson.fromJson(jsonData, new TypeToken<List<FriendRespData>>(){}.getType());
			imei = mData.get(0).imei;
			data = mData.get(0).friends;
			KMContactManager.getInstance().setFriends(imei, mData.get(0).friends);
		} catch (Exception e) {
			e.getCause();
		}
	}
	
	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_FRIEND_GET, msg, status, data));
		return super.respond(sc);
	}
}
