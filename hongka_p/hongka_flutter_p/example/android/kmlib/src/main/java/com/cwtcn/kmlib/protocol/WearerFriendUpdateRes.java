package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/***
 * 修改蓝牙朋友请求返回
 * @author Allen
 *
 */
public class WearerFriendUpdateRes extends Packet {
	public final static String CMD = "R_U_FRI";
	
	public WearerFriendUpdateRes() {
		super(CMD);
	}

	@Override
	protected Packet dup() {
		return this;
	}
	
	//R_Q_TARIFFS&W001&20140825001212&860860000000108不在线
	@Override
	public void decodeArgs(String[] sa, int offset, int length) {
		super.decodeArgs(sa, offset, length);
		try {
			status = sa[offset++];
			offset++;
			if (status.equals(NettyClientManager.STR_CODE_OK)) {
				offset++;//移动指针
				if(sa.length > offset) {
					msg = sa[offset];
				}else{
					msg = "";
				}
			} else {
				msg = sa[offset];
			}
		} catch (Exception e) {
			e.getCause();
		}
	}
	
	@Override
	public Packet respond(NettyClientManager cm) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_FRIEND_UPDATE, status, msg));
		return super.respond(cm);
	}
}
