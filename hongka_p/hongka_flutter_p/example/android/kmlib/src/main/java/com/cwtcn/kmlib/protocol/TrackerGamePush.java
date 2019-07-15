package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.GameData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.98 设置手表游戏返回
 * @author Allen
 */
public class TrackerGamePush extends Packet {
	public static final String CMD = "SEND_GAME_STATUS";
	private GameData data;
	
	public TrackerGamePush() {
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
			status = "0";
			String jsonData = sa[offset++];
			Gson gson = new Gson();
			data = gson.fromJson(jsonData, GameData.class);
		} catch (Exception e) {
			e.getCause();
		}
	}
	
	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent<GameData>(KMEventConst.EVENT_GAME_STATUS_PUSH, status, msg, data));
		return super.respond(sc);
	}
}
