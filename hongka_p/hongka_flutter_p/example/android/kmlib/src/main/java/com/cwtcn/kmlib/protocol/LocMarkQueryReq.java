package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMConstants;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 3.63查询位置到达
 *
 * @author Allen
 */
public class LocMarkQueryReq extends Packet {
    public static final String CMD = "Q_LOC_MARK";
    private String wearID;
    private int locationType;
    /**
     * 是否是家的位置
     */
    private boolean ISGOHOMEO = false;

    public LocMarkQueryReq() {
        super(CMD);
    }

    public LocMarkQueryReq(String wearID, int locationType) {
        super(CMD);
        this.wearID = wearID;
        this.locationType = locationType;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        JSONObject para = new JSONObject();
        try {
            if (locationType == KMConstants.LocMarkType.TYPE_LOC_MARK_HOME) {
                put(DateUtil.getDalayTimeId() + "HOME");
                para.put("imei", wearID);
                para.put("locationType", locationType);
            } else {
                put(DateUtil.getDalayTimeId());
                para.put("wearerId", Long.valueOf(wearID));
                JSONArray array = new JSONArray();
                array.put(locationType);
                para.put("locationTypes", array.toString());
            }
        } catch (Exception e) {

        }
        put(para.toString());
        return para2String().replace("]\"", "]").replace("\"[", "[");
    }
}
