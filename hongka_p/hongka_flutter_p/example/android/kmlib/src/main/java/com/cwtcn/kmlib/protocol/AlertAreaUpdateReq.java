package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMWearerManager;
import com.cwtcn.kmlib.data.AreaData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 3.48.1更新报警区域
 *
 * @author Allen
 */
public class AlertAreaUpdateReq extends Packet {
    public static final String CMD = "U_ALERT_AREA";
    private String wearID;
    private AreaData data;

    public AlertAreaUpdateReq() {
        super(CMD);
    }

    public AlertAreaUpdateReq(String imei, AreaData data) {
        super(CMD);
        this.wearID = KMWearerManager.getInstance().getWearerId(imei);
        this.data = data;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject mRequest = new JSONObject();
        try {
            mRequest.put("name", data.getName());
            mRequest.put("id", data.getId());
            mRequest.put("wearerId", wearID);
            mRequest.put("type", 0);
            JSONObject mCoordinate = new JSONObject();
            mCoordinate.put("coordinates", data.getCors());
            mCoordinate.put("shape", "PG");
            JSONArray geometries = new JSONArray();
            geometries.put(mCoordinate);
            mRequest.put("geometries", geometries);
        } catch (JSONException e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String().replace("]\"", "]").replace("\"[", "[");
    }

}
