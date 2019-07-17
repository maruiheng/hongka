package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMConstants;
import com.cwtcn.kmlib.data.LocationMark;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 3.59更新位置到达
 *
 * @author Allen
 */
public class LocMarkUpdateReq extends Packet {
    public static final String CMD = "U_LOC_MARK";
    private LocationMark data;

    public LocMarkUpdateReq() {
        super(CMD);
    }

    public LocMarkUpdateReq(LocationMark data) {
        super(CMD);
        this.data = data;
    }


    @Override
    protected Packet dup() {
        return this;
    }

    public String encodeArgs() {
        JSONObject object = new JSONObject();
        try {
            object.put("id", data.getId());
            object.put("wearerId", data.getWearerId());
            object.put("locationType", data.getLocationType());
            object.put("name", data.getName());
            object.put("lat", data.getLat());
            object.put("lon", data.getLon());
            object.put("enabled", data.isEnabled());
            object.put("address", data.getAddress());
            JSONObject notice = new JSONObject();
            notice.put("enabled", data.isEnabled());
            notice.put("inTime", "120000");
            notice.put("outTime", "120000");
            notice.put("noticeTrigger", "1111111");
            notice.put("deviation", 0);

            object.put("notice", notice);
        } catch (JSONException e) {

        }
        String cmdId = DateUtil.getDalayTimeId();
        if (data.getLocationType() == KMConstants.LocMarkType.TYPE_LOC_MARK_HOME) {
            cmdId = DateUtil.getDalayTimeId() + "HOME";
        }
        put(CMD);
        put(cmdId);
        put(object.toString());
        return para2String();
    }
}
