package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.55获取活动量
 *
 * @author Allen
 */
public class TrackerStepGetReq extends Packet {
    public static final String CMD = "G_DAILY_ACTIVITY";
    private String wearID;
    /**
     * 起始时间20150401000000
     */
    private String dateFrom;
    private String dateTo;

    public TrackerStepGetReq() {
        super(CMD);
    }

    public TrackerStepGetReq(String wearId, String dateFrom, String dateTo) {
        super(CMD);
        this.wearID = wearId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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
            mRequest.put("dateFrom", dateFrom);
            mRequest.put("dateTo", dateTo);
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }
}
