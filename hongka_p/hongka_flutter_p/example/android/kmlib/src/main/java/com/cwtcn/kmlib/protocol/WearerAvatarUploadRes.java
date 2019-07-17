package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.WearerAvatar;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.44上传佩戴对象图像返回
 * @author Allen
 */
public class WearerAvatarUploadRes extends Packet {
	public static final String CMD = "R_C_WEARER_AVATAR";
	private String orderId;
	private String jsonStr;// json串
	WearerAvatar mAvatar;

	public WearerAvatarUploadRes() {
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
		orderId = sa[offset++];
		if (status.equals(NettyClientManager.STR_CODE_OK)) {
			jsonStr = sa[offset++];
			Gson gson = new Gson();
			mAvatar = gson.fromJson(jsonStr, WearerAvatar.class);
		} else {//失败
			msg = sa[offset++];
		}
	}
	
	@Override
	public Packet respond(NettyClientManager cm) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_AVATAR_UPLOAD, status, msg));
		return super.respond(cm);
	}
}
