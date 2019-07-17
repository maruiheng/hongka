package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMWearerManager;
import com.cwtcn.kmlib.data.AreaData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 3.47创建报警区域
 *
 * @author Allen
 */
public class AlertAreaSetReq extends Packet {
    public static final String CMD = "C_ALERT_AREA";
    private String wearID;
    private AreaData data;

    public class coordinates {
        private List<List<Double>> coordinates;
    }

    public AlertAreaSetReq() {
        super(CMD);
    }

    public AlertAreaSetReq(String imei, AreaData data) {
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
            mRequest.put("wearerId", wearID);
            mRequest.put("type", 0);
            mRequest.put("shape", "PG");
            JSONObject mCoordinate = new JSONObject();
            mCoordinate.put("coordinates", data.getCors());
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
