package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMDBHelper;
import com.cwtcn.kmlib.data.ChatData;
import com.cwtcn.kmlib.data.IMSendData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.85客户端向手表传输语音返回
 *
 * @author Allen
 */
public class IMSendEndRes extends Packet {
    public static final String CMD = "ASA";
    private int code;
    private String audioID;
    private String need;

    public IMSendEndRes() {
        super(CMD);
    }

    public IMSendEndRes(int code, String id) {
        super(CMD);
        this.code = code;
        this.audioID = id;
    }

    public IMSendEndRes(int code, String id, String need) {
        super(CMD);
        this.code = code;
        this.audioID = id;
        this.need = need;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(audioID);
        put(need);
        return para2String();
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        try {
            this.code = Integer.parseInt(sa[offset++]);
            this.audioID = sa[offset++];
            if (code == NettyClientManager.CODE_OK) {
                this.need = "";
            } else {
                this.need = sa[offset++];
            }
        } catch (Exception e) {

        }
    }

    public Packet respond(final NettyClientManager sc) {
        if (NettyClientManager.CODE_OK == code) {
            final IMSendData re = NettyClientManager.FACTORY_VOICE.remove(NettyClientManager.SEND_IM + audioID);
            if (re != null) {
                sc.removeDelayQ(audioID);
                KMDBHelper.getInstance().updateChatSendState(Long.parseLong(audioID), ChatData.STATE_SEND_OK, new KMDBHelper.INotifyDBChange() {

                    @Override
                    public void onChange(Object... obj) {
                        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_IM_SEND, "0", ""));
                    }

                    @Override
                    public void err(int... code) {

                    }
                });
            }
        } else {
            sc.repeatSendVoice(audioID, need);
        }
        return super.respond(sc);
    }
}
