package com.cwtcn.kmlib.protocol;


import com.cwtcn.kmlib.api.KMConstants;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * APP发送设置指令返回
 *
 * @author Allen
 */
public class TrackerCMDRes extends Packet {
    public static final String CMD = "ACM";
    private String jsonID;
    private String imei;

    public TrackerCMDRes() {
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        status = sa[offset++];
        jsonID = sa[offset++];

        try {
            if (NettyClientManager.STR_CODE_OK.equals(status)) {
                offset++;
                if (sa.length > offset) {
                    msg = jsonID + "-" + sa[offset];
                } else {
                    msg = jsonID;
                }
            } else if (NettyClientManager.STR_CODE_ERR.equals(status)) {
                if (sa.length == 5) {
                    msg = jsonID + "-" + sa[4];
                } else {
                    msg = jsonID + "-" + sa[offset];
                }
            } else {
                msg = jsonID + "-" + sa[++offset];
            }
            int index = jsonID.lastIndexOf(KMConstants.TrackerCMD.CMD_SEPARATOR);
            if (index != -1) {
                imei = jsonID.substring(index + 1);
            }
        } catch (Exception e) {
            e.getCause();
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        int eventID = -1;
        if(jsonID.indexOf(KMConstants.TrackerCMD.CMD_OFF_ALARM) != -1) {
            eventID = KMEventConst.EVENT_OFF_ALARM;
        } else if(jsonID.indexOf(KMConstants.TrackerCMD.CMD_ACCIDENT_ALARM) != -1) {
            eventID = KMEventConst.EVENT_ACCIDENT_ALARM;
        } else if(jsonID.indexOf(KMConstants.TrackerCMD.CMD_ACCIDENT_ALARM_LEVEL) != -1) {
            eventID = KMEventConst.EVENT_ACCIDENT_ALARM_LEVEL;
        } else if(jsonID.indexOf(KMConstants.TrackerCMD.CMD_LOC_ONCE) != -1) {
            eventID = KMEventConst.EVENT_LOC_ONCE;
        }
        EventBus.getDefault().post(new KMEvent(eventID, status, msg));
        return super.respond(sc);
    }
}
