package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/***
 * 3.2.1.12新版查询Tracker工作模式(Q_WORK_MODE_HV)
 * @author Allen
 *
 */
public class TrackerWorkModeHVQueryReq extends Packet {
    public static final String CMD = "Q_WORK_MODE_HV";
    private String imei;

    @Override
    protected Packet dup() {
        return this;
    }

    public TrackerWorkModeHVQueryReq() {
        super(CMD);
    }

    public TrackerWorkModeHVQueryReq(String imei) {
        super(CMD);
        this.imei = imei;
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
