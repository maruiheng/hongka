package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.FriendData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 删除蓝牙朋友请求
 *
 * @author Allen
 */

public class WearerFriendDelReq extends Packet {
    public static final String CMD = "D_FRI";
    private String imei;
    private List<FriendData> friends;

    @Override
    protected Packet dup() {
        return this;
    }

    public WearerFriendDelReq() {
        super(CMD);
    }

    public WearerFriendDelReq(String imei, List<FriendData> friends) {
        super(CMD);
        this.imei = imei;
        this.friends = friends;
    }

    @Override
    public String encodeArgs() {
        JSONObject data = new JSONObject();
        try {
            data.put("imei", imei);
            JSONArray friend = new JSONArray();
            for (FriendData mfriend : friends) {
                friend.put(mfriend.getImei());
            }
            data.put("friend", friend);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(data.toString());
        return para2String();
    }

}
