package com.cwtcn.kmlib.protocol;


import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

public class ChangePasswordReq extends Packet {
    public static final String CMD = "U_PASSWORD";
    private String newPassword;
    private String oldPassword;

    @Override
    protected Packet dup() {
        return this;
    }

    public ChangePasswordReq() {
        super(CMD);
    }

    public ChangePasswordReq(String newPassword, String oldPassword) {
        super(CMD);
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    @Override
    public String encodeArgs() {
        JSONObject mRequest = new JSONObject();
        try {
            mRequest.put("newPassword", newPassword);
            mRequest.put("oldPassword", oldPassword);
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }
}
