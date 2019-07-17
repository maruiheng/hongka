package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.2.1.40设置Tracker开启/关闭亲情号码白名单 (E_WL)
 *
 * @author Allen
 */
public class TrackerWhiteListSetReq extends Packet {
    public static final String CMD = "E_WL";
    private String imei;
    private int mode;

    public TrackerWhiteListSetReq() {
        super(CMD);
    }

    public TrackerWhiteListSetReq(String imei, int mode) {
        super(CMD);
        this.imei = imei;
        this.mode = mode;
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
            mRequest.put("on", mode);
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }

}
