package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.ContactData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 3.11 设置亲情号码
 *
 * @author Allen
 */
public class FamilyNumbersSetReq extends Packet {
    public static final String CMD = "S_FN";
    private String imei;
    private List<ContactData> mFamliyNumbers;

    public FamilyNumbersSetReq() {
        super(CMD);
    }

    public FamilyNumbersSetReq(String imei, List<ContactData> mFns) {
        super(CMD);
        this.imei = imei;
        mFamliyNumbers = mFns;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject para = new JSONObject();
        try {
            para.put("imei", imei);
            JSONArray fns = new JSONArray();
            if (mFamliyNumbers != null && mFamliyNumbers.size() > 0) {
                for (ContactData mFamliyNumber : mFamliyNumbers) {
                    JSONObject mItem = new JSONObject();
                    mItem.put("no", mFamliyNumber.getNo());
                    mItem.put("mobile", mFamliyNumber.getMobile());
                    fns.put(mItem);
                }
            }
            para.put("fns", fns);
        } catch (JSONException e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();
    }
}
