package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

/**
 * 3.2.1.32设置Tracker翻腕接听开关(S_AUTO_ANSWER)
 *
 * @author Allen
 */
public class TrackerAutoAnswerSetReq extends Packet {
    public static final String CMD = "S_AUTO_ANSWER";
    private String imei;
    /**
     * 0:关闭，2：翻腕
     */
    private int mode;
    private int duration = 3;

    public TrackerAutoAnswerSetReq() {
        super(CMD);
    }

    public TrackerAutoAnswerSetReq(String imei, int mode) {
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
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(imei);
        put(mode);
        put(duration);
        return para2String();
    }

}
