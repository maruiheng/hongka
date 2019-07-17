package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMLocationManager;
import com.cwtcn.kmlib.api.KMWearerManager;
import com.cwtcn.kmlib.data.Wearer;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 3.42查询所有佩戴对象返回
 * @author Allen
 */
public class WearerQueryRes extends Packet {
	public static final String CMD = "R_Q_WEARER";
	public List<Wearer> wearers;

	public WearerQueryRes() {
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
			//R_Q_WEARER&0&20140825121212&[{}]
			status = sa[offset++];
			String jsonStr;
			if (status.equals(NettyClientManager.STR_CODE_OK)) {
				offset++;//移动指针
				jsonStr = sa[offset++];
				wearers = getObjectList(jsonStr, Wearer.class);
				KMWearerManager.getInstance().setWearers(wearers);
				if(KMWearerManager.getInstance().isFirstGetWearer && wearers != null && wearers.size() > 0) {
					//KMLocationManager.getInstance().initAlertLoction();
					//KMLocationManager.getInstance().initAlertArea();
					//KMLocationManager.getInstance().initAlertPlace();
					//KMSettingManager.getInstance().getAllWorkMode();
					KMWearerManager.getInstance().isFirstGetWearer = false;
				}
				KMLocationManager.getInstance().trackerLDGet(null);
			} else {
				msg = sa[offset++];
			}
		} catch (Exception e) {
			e.getCause();
		}
	}

	@Override
	public Packet respond(NettyClientManager cm) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_QUERY, status, msg, wearers));
		return super.respond(cm);
	}

	/***
	 * Gson 数据解析
	 * @param jsonString
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public <T> List<T> getObjectList(String jsonString, Class<T> cls){
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			JsonArray array = new JsonParser().parse(jsonString).getAsJsonArray();
			for (JsonElement jsonElement : array) {
				list.add(gson.fromJson(jsonElement, cls));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
