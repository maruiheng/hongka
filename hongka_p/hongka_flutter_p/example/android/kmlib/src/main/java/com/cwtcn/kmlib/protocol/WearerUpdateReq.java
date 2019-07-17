package com.cwtcn.kmlib.protocol;


import com.cwtcn.kmlib.data.Wearer;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.37更新佩戴对象
 *
 * @author Allen
 */
public class WearerUpdateReq extends Packet {
    public static final String CMD = "U_WEARER";
    private Wearer mWear;

    public WearerUpdateReq() {
        super(CMD);
    }

    public WearerUpdateReq(Wearer wearer) {
        super(CMD);
        this.mWear = wearer;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject mRequest = new JSONObject();
        try {
            mRequest.put("imei", mWear.getImei());
            mRequest.put("name", mWear.getWearerName());
            mRequest.put("gender", mWear.getGender());
            mRequest.put("height", mWear.getHeight());
            mRequest.put("weight", mWear.getWeight());
            mRequest.put("dob", mWear.getDob());
            mRequest.put("markPicID", mWear.getMarkPicID());
            mRequest.put("relationShip", mWear.getRelationship());
            mRequest.put("relationShipPic", mWear.getRelationshipPic());
            mRequest.put("mobile", mWear.getMobile());
            mRequest.put("userMobile", mWear.getUserMobile());
            mRequest.put("prevUserMobile", mWear.getPrevUserMobile());
            mRequest.put("id", mWear.getWearerId());
            mRequest.put("relationshipName", mWear.getRelationshipName());
            mRequest.put("avatarFn", mWear.getAvatarFn());
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }
}
