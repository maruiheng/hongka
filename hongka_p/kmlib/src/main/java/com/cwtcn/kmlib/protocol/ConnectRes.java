package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMContactManager;
import com.cwtcn.kmlib.api.KMSettingManager;
import com.cwtcn.kmlib.api.KMUserManager;
import com.cwtcn.kmlib.api.KMWearerManager;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.LoginRespData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.6 登陆返回(建立socket连接和认证的回应)
 * @author Allen
 */
public class ConnectRes extends Packet {
	public static final String CMD = "ARE";

	public ConnectRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}
	
	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		Gson gson = new Gson();
		try {
			//ARE&0&1.0.2&156127746026635264&DefaultSession153133&150724151931&[{}]
			status = sa[offset++];
			if ((NettyClientManager.STR_CODE_OK).equals(status)) {
				String version = sa[offset++];//协议版本号
				KMUserManager.getInstance().setUID(sa[offset++]);//登录成功的UID
				offset++;
				String time = sa[offset++];//时间戳
				String messageJson = sa[offset++];//返回的消息体
				List<LoginRespData> mData = gson.fromJson(messageJson, new TypeToken<List<LoginRespData>>(){}.getType());
				if(mData != null && mData.size() > 0) {
					for(LoginRespData data : mData) {
						KMWearerManager.getInstance().mProductIDMap.put(data.imei, data.productId);
						KMWearerManager.getInstance().mProductSVerMap.put(data.imei, data.sVer);
						KMWearerManager.getInstance().mProductFVerMap.put(data.imei, data.fVer);
						//KMContactManager.getInstance().setContacts(data.imei, data.fnStatus.fns);
						//KMSettingManager.getInstance().mMrMaps.put(data.imei, data.mrStatus.mrs);
					}
				}
			} else {
				offset++;
				msg = sa[offset++];
			}
		} catch (Exception e) {
			e.getCause();
		}
	}

	@Override
	public Packet respond(NettyClientManager cm) {
		if (NettyClientManager.STR_CODE_OK.equals(status)) {
			cm.onConnected();
			EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_LOGIN, NettyClientManager.STR_CODE_OK, ""));
		} else if(NettyClientManager.STR_CODE_NOFOUNT_ERR.equals(status)) {
			cm.disconnect();
			EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_LOGIN, NettyClientManager.STR_CODE_NOFOUNT_ERR, "user not found"));
		} else if(NettyClientManager.STR_CODE_PASSERR.equals(status)) {
			cm.disconnect();
			EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_LOGIN, NettyClientManager.STR_CODE_PASSERR, "username or password is error"));
		}
		return super.respond(cm);
	}
}
