package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.ContactData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 3.2.1.23设置手表联系人(MS_CTTS)
 *
 * @author Allen
 */
public class CTTSSetReq extends Packet {
    public static final String CMD = "MS_CTTS";
    private String imei;
    private List<ContactData> data;

    public CTTSSetReq() {
        super(CMD);
    }

    public CTTSSetReq(String imei, List<ContactData> data) {
        super(CMD);
        this.imei = imei;
        this.data = data;
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
            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    JSONObject mItem = new JSONObject();
                    mItem.put("no", i + 1);
                    mItem.put("mobile", data.get(i).getMobile());
                    mItem.put("familyMobile", data.get(i).getFamilyMobile());
                    mItem.put("name", data.get(i).getName());
                    mItem.put("avatar", data.get(i).getAvatar());
                    mItem.put("source", data.get(i).getType());
                    mItem.put("picId", data.get(i).getPicId());
                    fns.put(mItem);
                }
            }
            para.put("ctts", fns);
        } catch (JSONException e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();
    }

}
