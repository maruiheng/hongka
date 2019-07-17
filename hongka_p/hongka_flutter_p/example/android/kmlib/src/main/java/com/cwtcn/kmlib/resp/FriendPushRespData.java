package com.cwtcn.kmlib.resp;

import java.util.List;

/**
 * 蓝牙交友推送数据
 * @author Allen
 *
 */
public class FriendPushRespData extends BaseResp {

	public List<FriendRespData> friends;
	public String userName;//
	public String message;//
	public int reason;//
	
	public FriendPushRespData(String userName, int reason, String message, List<FriendRespData> friends){
		this.userName =userName;
		this.message = message;
		this.friends = friends;
		this.reason = reason;
	}
}
