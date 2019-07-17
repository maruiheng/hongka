package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * function: functionName
 *
 * @author xhl
 * @date 2019/6/25 11:09
 */

public class SendStorySetReq extends Packet{
    public static final String CMD = "M_DOWN_RESOURCE";
    private String mImei;
    private JSONArray mArray;

    public SendStorySetReq(){
        super(CMD);
    }

    public SendStorySetReq(String imei,JSONArray array){
        super(CMD);
        mImei = imei;
        mArray = array;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject para = new JSONObject();
        put(CMD);
        try {
            para.put("imei", mImei);
            para.put("resource",mArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();

    }

}
