package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.netty.NettyClientManager;

/**
 * 3.84客户端向手表传输语音
 *
 * @author Allen
 */
public class IMSendReq extends Packet {
    public static final String CMD = "SAU";
    private String imei;
    private String audioID;
    private String part;

    public IMSendReq() {
        super(CMD);
    }

    public IMSendReq(String audioId, String part, byte[] payload) {
        super(CMD);
        this.audioID = audioId;
        this.part = part;
        this.payload = payload;
    }


    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(audioID);
        put(part);
        return para2String() + "&";
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        this.audioID = sa[offset++];
        this.part = sa[offset++];
        super.decodeArgs(sa, offset, length);
    }

    public Packet respond(NettyClientManager sc) {
        // 收到语音字节的处理
        sc.onReceiverRecord(audioID, part, payload);
        // 只接收不回应
        return super.respond(sc);
    }
}
