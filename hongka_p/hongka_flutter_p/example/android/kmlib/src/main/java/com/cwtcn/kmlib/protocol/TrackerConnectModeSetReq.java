package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerConnectMode;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.2.1.26设置Tracker链接方式(S_CONNECT_MODE)
 *
 * @author Allen
 */
public class TrackerConnectModeSetReq extends Packet {
    public static final String CMD = "S_CONNECT_MODE";
    private TrackerConnectMode data;

    public TrackerConnectModeSetReq() {
        super(CMD);
    }

    public TrackerConnectModeSetReq(TrackerConnectMode data) {
        super(CMD);
        this.data = data;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject request = new JSONObject();
        try {
            request.put("connect_mode", data.getConnectMode());
            request.put("imei", data.getImei());
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(request.toString());
        return para2String();
    }

}
