package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/***
 * 删除蓝牙朋友请求返回
 * @author Allen
 *
 */
public class WearerFriendDelRes extends Packet {
	public final static String CMD = "R_D_FRI";
	
	public WearerFriendDelRes() {
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
			offset++;//移动指针
			if (status.equals(NettyClientManager.STR_CODE_OK)) {
				offset++;//移动指针
				if(sa.length > offset) {
					msg = sa[offset];
				}else{
					msg = "";
				}
			} else {
				msg = sa[offset++];
			}
		} catch (Exception e) {
			e.getCause();
		}
	}
	
	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_FRIEND_DEL, status, msg));
		return super.respond(sc);
	}
}
