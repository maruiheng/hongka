package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.Wearer;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.35添加佩戴对象
 *
 * @author Allen
 */
public class WearerAddReq extends Packet {
    public static final String CMD = "C_WEARER";
    /**
     * 扫码添加的识别码
     */
    private String bindingValidationCode;
    private Wearer mWear;
    /**
     * 手动添加的验证码
     */
    private String validationCode;
    /**
     * 是否是二维码扫描
     */
    private boolean isQRCode = false;

    public WearerAddReq() {
        super(CMD);
    }

    public WearerAddReq(String code) {
        super(CMD);
        this.bindingValidationCode = code;
        this.isQRCode = true;
    }

    public WearerAddReq(Wearer wearer, String validationCode) {
        super(CMD);
        this.mWear = wearer;
        this.validationCode = validationCode;
        this.isQRCode = false;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject mRequest = new JSONObject();
        try {
            if (isQRCode) {
                mRequest.put("bindingValidationCode", bindingValidationCode);
            } else {
                mRequest.put("validationCode", validationCode);
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
                mRequest.put("relationshipName", mWear.getRelationshipName());
                mRequest.put("avatarFn", mWear.getAvatarFn());
            }
            put(CMD);
            put(DateUtil.getDalayTimeId());
            put(mRequest.toString());
            return para2String();
        } catch (Exception e) {

        }
        return "";
    }
}
