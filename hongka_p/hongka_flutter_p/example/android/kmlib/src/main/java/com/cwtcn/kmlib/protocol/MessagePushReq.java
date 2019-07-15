package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.MessagePushData;
import com.cwtcn.kmlib.util.DateUtil;
import com.cwtcn.kmlib.util.MyLog;

import com.alibaba.fastjson.JSONObject;

public class MessagePushReq extends Packet {
    public static final String CMD = "M_DOWN_RESOURCE";
    private MessagePushData data;


    @Override
    protected Packet dup() {
        return this;
    }

    public MessagePushReq(MessagePushData data) {
        super(CMD);
        this.data = data;
    }

    @Override
    public String encodeArgs() {

        JSONObject mRequest ;
        try {
            mRequest = (JSONObject) JSONObject.toJSON(this.data);
            MyLog.e("mrh",mRequest.toString());
            put(CMD);
            put(DateUtil.getDalayTimeId());
            put(mRequest.toString());
            return para2String();
        } catch (Exception e) {

        }
        return "";
    }


}
