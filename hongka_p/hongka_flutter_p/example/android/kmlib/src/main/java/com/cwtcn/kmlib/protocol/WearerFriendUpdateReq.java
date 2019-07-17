package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.FriendData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

/***
 * 修改蓝牙朋友请求
 * @author Allen
 *
 */

public class WearerFriendUpdateReq extends Packet {
    public static final String CMD = "U_FRI";
    private FriendData friend;
    private String imei;

    @Override
    protected Packet dup() {
        return this;
    }

    public WearerFriendUpdateReq() {
        super(CMD);
    }

    public WearerFriendUpdateReq(String imei, FriendData data) {
        super(CMD);
        this.imei = imei;
        this.friend = data;
    }

    //U_FRI&76c05320-3313-11e4-b366-d850e6535d08&{"imei":"860861000000009","friendImei":"860860000030075","picId":2,"name":"小米"}&3333333333
    @Override
    public String encodeArgs() {

        JSONObject data = new JSONObject();
        try {
            data.put("imei", imei);
            data.put("id", friend.getId());
            data.put("friendImei", friend.getImei());
            data.put("name", friend.getName());
            data.put("friendMobile", friend.getFriendMobile());
        } catch (JSONException e) {
            e.getCause();
        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(data.toString());
        return para2String();
    }
}
