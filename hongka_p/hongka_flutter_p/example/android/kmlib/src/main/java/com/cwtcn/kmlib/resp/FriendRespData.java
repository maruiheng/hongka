package com.cwtcn.kmlib.resp;

import com.cwtcn.kmlib.data.FriendData;

import java.util.List;

/**
 * 蓝牙交友
 * @author Allen
 *
 */
public class FriendRespData extends BaseResp {

	//{"imei" : "860860000010829","picId" : 0,"name" : "小名"
	public String imei;
	public List<FriendData> friends;
	public String lut;//
	//"imei":"860860000010826","lut","20140909101012","friends" : [{"imei" : "860860000010829","picId" : 0,"name" : "小名"}
	
	public FriendRespData(String imei, String lut, List<FriendData> friends){
		this.imei =imei;
		this.friends = friends;
		this.lut = lut;
	}
}
