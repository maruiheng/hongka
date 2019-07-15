package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.GameData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.97游戏
 *
 * @author Allen
 */
public class TrackerGameStartReq extends Packet {
    public static final String CMD = "SET_GAME";

    private GameData data;

    public TrackerGameStartReq() {
        super(CMD);
    }

    public TrackerGameStartReq(GameData data) {
        super(CMD);
        this.data = data;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject object = new JSONObject();
        try {
            object.put("imei", data.getImei());
            object.put("hand", data.getHand());
            object.put("game", data.getGame());
            object.put("enable", data.isEnable());
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(object.toString());
        return para2String();
    }
}
