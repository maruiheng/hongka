package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.51设置活动目标
 *
 * @author Allen
 */
public class TrackerStepTargetSetReq extends Packet {
    public static final String CMD = "S_ACT_CFG";
    private String wearID;
    private int targetSteps;

    public TrackerStepTargetSetReq() {
        super(CMD);
    }

    public TrackerStepTargetSetReq(String wearId, int targetSteps) {
        super(CMD);
        this.wearID = wearId;
        this.targetSteps = targetSteps;
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
            mRequest.put("stepsTarget", targetSteps);
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }
}
