package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.12设置亲情号码返回
 * @author Allen
 */
public class FamilyNumbersSetRes extends Packet {
	public static final String CMD = "R_S_FN";

	public FamilyNumbersSetRes() {
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
			offset++;
			if(status.equals(NettyClientManager.STR_CODE_OK)) {
				offset++;
				if(sa.length > offset) {
					msg = sa[offset];
				}else{
					msg = "";
				}
			} else {
				msg = sa[offset];
			}			
		} catch (Exception e) {
			
		}
		if(status.equals(NettyClientManager.STR_CODE_OK)) {
			//设置完亲情号码后，查询下最新的亲情号码，保持数据的统一性
			//SocketManager.addFamilyNumbersQueryPkg(null);
		}
	}

	@Override
	public Packet respond(NettyClientManager sc) {
		EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_CONTACT_SET, status, msg));
		return super.respond(sc);
	}
}
