package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.netty.NettyClientManager;

/**
 * 3.87客户端标记语音已读
 *
 * @author Allen
 */
public class IMReadReq extends Packet {
    public static final String CMD = "ARD";
    private String audioID;
    private String target;

    public IMReadReq() {
        super(CMD);
    }

    public IMReadReq(String audioID) {
        super(CMD);
        this.audioID = audioID;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(id);
        put(target);
        return para2String();
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        this.audioID = sa[offset++];
        this.target = NettyClientManager.receiverTarget(sa[offset++]);
        super.decodeArgs(sa, offset, length);
    }

    public Packet respond(NettyClientManager sc) {
        sc.onUpdateVoiceRead(Long.parseLong(audioID));
        return super.respond(sc);
    }
}