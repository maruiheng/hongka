package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMUserManager;
import com.cwtcn.kmlib.api.KMWearerManager;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.2.15.25手表管理员解除其它成员绑定（WEARER_ADMIN_DELETE）
 *
 * @author Allen
 */
public class WearerUnbindReq extends Packet {
    public static final String CMD = "WEARER_ADMIN_DELETE";
    private String imei;
    /**
     * wearerId : 配带人ID
     */
    private String wearId;
    /**
     * memberId : 绑定人ID
     */
    private String memberId;

    public WearerUnbindReq() {
        super(CMD);
    }

    public WearerUnbindReq(String imei) {
        super(CMD);
        this.imei = imei;
        this.wearId = KMWearerManager.getInstance().getWearerId(imei);
        this.memberId = KMUserManager.getInstance().getUID();
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject mObject = new JSONObject();
        try {
            mObject.put("imei", imei);
            mObject.put("wearerId", wearId);
            mObject.put("memberId", memberId);
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mObject.toString());
        return para2String();
    }
}
