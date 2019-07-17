package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMWearerManager;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 3.2.18.10设置佩戴对象报警开关（ENABLE_ALERT_AREA）
 *
 * @author Allen
 */
public class AlertAreaEnableReq extends Packet {
    public static final String CMD = "ENABLE_ALERT_AREA";
    private String wearID;
    private String areaID;
    private boolean enable;


    public AlertAreaEnableReq() {
        super(CMD);
    }

    public AlertAreaEnableReq(String imei, String areaID, boolean enable) {
        super(CMD);
        this.wearID = KMWearerManager.getInstance().getWearerId(imei);
        this.areaID = areaID;
        this.enable = enable;
    }

    @Override
    protected Packet dup() {
        return this;
    }


    @Override
    public String encodeArgs() {
        JSONObject mRequest = new JSONObject();
        try {
            mRequest.put("wearerId", wearID);
            mRequest.put("id", areaID);
            mRequest.put("enable", enable);
        } catch (JSONException e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }
}
