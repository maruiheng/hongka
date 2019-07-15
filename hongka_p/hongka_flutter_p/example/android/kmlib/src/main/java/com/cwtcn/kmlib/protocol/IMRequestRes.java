package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.netty.NettyClientManager;

/**
 * 3.85客户端向手表传输语音返回
 *
 * @author Allen
 */
public class IMRequestRes extends Packet {
    public static final String CMD = "ARA";
    private int code;
    private String audioID;
    private String target;

    public IMRequestRes() {
        super(CMD);
    }

    public IMRequestRes(int code, String id) {
        super(CMD);
        this.code = code;
        this.audioID = id;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(code);
        put(audioID);
        return para2String();
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        this.code = Integer.parseInt(sa[offset++]);
        this.audioID = sa[offset++];
        if (code == NettyClientManager.CODE_OK && offset == length - 1) {
            this.target = NettyClientManager.receiverTarget(sa[offset++]);
        } else {
            this.target = null;
        }
        super.decodeArgs(sa, offset, length);
    }

    public Packet respond(NettyClientManager sc) {
        // 收到语音开始的请求处理
        if (NettyClientManager.CODE_OK == code) {
            if (target != null) {
                sc.sendVoiceNotOnline(Long.parseLong(audioID));
            }
            sc.startSendVoice(audioID);
        }
        return super.respond(sc);
    }
}
