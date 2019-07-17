package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 3.2.17.18	删除新版家校提醒时间信息
 *
 * @author Allen
 */
public class HSLocationDelReq extends Packet {
    public static final String CMD = "D_LOCATION_INFO";
    private List<String> locAlertId = new ArrayList<String>();
    private String imei;
    private String wearerId;

    public HSLocationDelReq() {
        super(CMD);
    }

    public HSLocationDelReq(List<String> locAlertId, String imei, String wearerId) {
        super(CMD);
        this.locAlertId = locAlertId;
        this.imei = imei;
        this.wearerId = wearerId;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONArray para = new JSONArray();
        try {
            if (locAlertId != null && locAlertId.size() > 0) {
                for (int i = 0; i < locAlertId.size(); i++) {
                    JSONObject mItem = new JSONObject();
                    mItem.put("imei", imei);
                    mItem.put("wearerId", wearerId);
                    mItem.put("id", locAlertId.get(i));
                    para.put(mItem);
                }
            }
        } catch (JSONException e) {
            e.getCause();
        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();
    }
}
