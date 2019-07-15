package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 3.29 设置手表时间
 *
 * @author Allen
 */
public class TrackerTimeSetReq extends Packet {
    public static final String CMD = "S_DATETIME";
    private String imei;
    private String time;
    private SimpleDateFormat sdfZone = new SimpleDateFormat("Z");

    public TrackerTimeSetReq() {
        super(CMD);
    }

    public TrackerTimeSetReq(String imei, String time) {
        super(CMD);
        this.imei = imei;
        this.time = time;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(imei);
        put(time);
        put(getTimeZone());
        return para2String();
    }

    private String getTimeZone() {
        return sdfZone.format(new Date());
    }
}
