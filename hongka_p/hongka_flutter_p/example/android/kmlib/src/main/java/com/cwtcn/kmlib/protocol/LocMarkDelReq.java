package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

import java.util.List;

/**
 * 3.61删除位置到达
 *
 * @author Allen
 */
public class LocMarkDelReq extends Packet {
    public static final String CMD = "D_LOC_MARK";
    private List<Long> alertID;
    private String wearerId;

    public LocMarkDelReq() {
        super(CMD);
    }

    public LocMarkDelReq(String wearerId, List<Long> id) {
        super(CMD);
        this.alertID = id;
        this.wearerId = wearerId;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject para = new JSONObject();
        try {
            para.put("wearerId", wearerId);
            para.put("ids", alertID);
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String().replace("]\"", "]").replace("\"[", "[");
    }
}
