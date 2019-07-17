package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.2.2.26取Tracker许愿信息(Q_WISHES)
 *
 * @author Allen
 */
public class TrackerWishesGetReq extends Packet {
    public static final String CMD = "Q_WISHES";
    private String imei;

    public TrackerWishesGetReq() {
        super(CMD);
    }

    public TrackerWishesGetReq(String imei) {
        super(CMD);
        this.imei = imei;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject mRequest = new JSONObject();
        try {
            mRequest.put("imei", imei);
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }
}
