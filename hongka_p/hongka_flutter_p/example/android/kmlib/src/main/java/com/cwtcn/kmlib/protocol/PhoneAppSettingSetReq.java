package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 设置X2应用使用时间,是否卸载(X2_APP_SETTING)
 * Created by xhl on 2017/4/25.
 */

public class PhoneAppSettingSetReq extends Packet {
    public static final String CMD = "X2_APP_SETTING";
    private String content;
    private String imei;
    private String appId;
    private String packageName;
    private int useTime;
    private int uninstall;

    public PhoneAppSettingSetReq() {
        super(CMD);
    }

    public PhoneAppSettingSetReq(String imei, String appId, String packageName, int useTime, int uninstall) {
        super(CMD);
        this.imei = imei;
        this.appId = appId;
        this.packageName = packageName;
        this.useTime = useTime;
        this.uninstall = uninstall;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        JSONObject object = new JSONObject();
        try {
            object.put("id", appId);
            object.put("packageName", packageName);
            object.put("imei", imei);
            object.put("useTime", useTime);
            object.put("uninstall", uninstall);
        } catch (Exception e) {

        }
        put(object.toString());
        return para2String();
    }
}
