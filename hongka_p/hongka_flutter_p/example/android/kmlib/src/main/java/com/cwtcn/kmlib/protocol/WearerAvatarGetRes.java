package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.util.Constants;
import com.cwtcn.kmlib.util.FileUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.46获取佩戴对象图像返回
 * @author Allen
 */
public class WearerAvatarGetRes extends Packet {
	public static final String CMD = "R_G_WEARER_AVATAR";
	private String orderId = "";
	private String jsonStr;// json串

	public WearerAvatarGetRes() {
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
		} else {
			msg = sa[offset++];
		}

	}
	
	@Override
	public Packet respond(NettyClientManager cm) {
		String headPath = Constants.HEAD_PATH +  orderId + ".jpg";
		if (NettyClientManager.STR_CODE_OK.equals(status) && payload != null) {
			FileUtils.byte2File(payload, Constants.HEAD_PATH, orderId + ".jpg");
		}
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_AVATAR_GET, status, msg, headPath));
		return super.respond(cm);
	}
}
