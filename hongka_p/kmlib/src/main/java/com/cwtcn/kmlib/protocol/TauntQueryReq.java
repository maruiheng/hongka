package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMConstants;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 吐槽信息获取
 * Created by xhl on 2017/4/25.
 */

public class TauntQueryReq extends Packet {
    public static final String CMD = "Q_USER_MOMENTS";
    private String userId;
    private String count;
    // momentsId:最后一条消息ID，第一次获取传0
    private String momentsId;

    public TauntQueryReq() {
        super(CMD);
    }

    public TauntQueryReq(String userId, String count, String momentsId) {
        super(CMD);
        this.userId = userId;
        this.count = count;
        this.momentsId = momentsId;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        JSONObject para = new JSONObject();
        try {
            para.put("count", count);
            para.put("momentsId", momentsId);
            para.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();
    }
}
