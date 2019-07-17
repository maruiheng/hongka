package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMDBHelper;
import com.cwtcn.kmlib.api.KMUserManager;
import com.cwtcn.kmlib.data.TrackerNotice;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.util.MyLog;
import com.google.gson.Gson;

/**
 * 推送Tracker操作通知
 * @author allen
 */
public class TrackerNoticePush extends Packet {
	public static final String CMD = "P_TRACKER_NOTICE_TO_MEMBER";

	public TrackerNoticePush() {
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
			String mResponse = sa[offset++];
			status = "0";
			Gson gson = new Gson();
			//mResponse = mResponse.substring(1, mResponse.length() - 1);
			TrackerNotice para =  gson.fromJson(mResponse, TrackerNotice.class);
			if(para != null ) {
				para.setMemberId(KMUserManager.getInstance().getUID());
				KMDBHelper.getInstance().insertTracerNotice(para, null);
			}
		} catch(Exception ex) {
			MyLog.e(CMD, "exception");
		}
	}
	
	@Override
	public Packet respond(NettyClientManager cm) {
		return super.respond(cm);
	}
}