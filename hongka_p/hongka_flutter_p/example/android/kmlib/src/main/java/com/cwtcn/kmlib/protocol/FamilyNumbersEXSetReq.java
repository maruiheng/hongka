package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.ContactData;
import com.cwtcn.kmlib.util.DateUtil;
import com.cwtcn.kmlib.util.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * kt04亲情号码设置
 *
 * @author Allen
 */
public class FamilyNumbersEXSetReq extends Packet {
    public static final String CMD = "S_FN_EX";
    private String imei;
    private List<ContactData> mFamliyNumbers;

    public FamilyNumbersEXSetReq() {
        super(CMD);
    }

    public FamilyNumbersEXSetReq(String imei, List<ContactData> mFns) {
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
                for (int i = 0; i < mFamliyNumbers.size(); i++) {
                    JSONObject mItem = new JSONObject();
                    mItem.put("no", i + 1);
                    mItem.put("mobile", mFamliyNumbers.get(i).getMobile());
                    mItem.put("picId", mFamliyNumbers.get(i).getPicId());
                    mItem.put("name", mFamliyNumbers.get(i).getName());
                    fns.put(mItem);
                }
            }
            para.put("fns", fns);
        } catch (JSONException e) {
            MyLog.e(CMD, e.toString());
        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();
    }
}
