package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.data.LoginData;

/**
 * 3.5 登陆 建立socket连接和认证的请求
 * @author Allen
 *
 */
public class ConnectReq extends Packet {

	public static final String CMD = "REQ";
	
	private LoginData data;

	public ConnectReq() {
		super(CMD);
	}

	public ConnectReq(LoginData data) {
		super(CMD);
		this.data = data;
	}

	@Override
	protected Packet dup() {
		return new ConnectReq();
	}

	@Override
	public String encodeArgs() {
		//REQ&1&1.0.2&15641196256&*******&Android 4.4.4|4.3.0|release
		//&cn&abardeen&ApBJMbSL6dhaME6AWll5kJStamnih5zD2tUaffyQM_Ip&amap
		if(data != null) {
			put(CMD);
			put(1);
			put(data.getpVer());
			if(TextUtils.isEmpty(data.getNewName())) {
				put(data.getLoginName());
			} else {
				put(data.getNewName());
			}
			put(data.getPassword());
			put(data.getOs() + "|" + data.getsVer() + "|" + "release");
			put(data.getLang());
			put(data.getClientID());
			put(data.getDeviceToken());
			put(data.getMapType());
			if(!TextUtils.isEmpty(data.getNewName())) {
				put(data.getAuthSource());
				put(data.getAuthId());
			}
			return para2String();
		}
		return "";
	}
}
