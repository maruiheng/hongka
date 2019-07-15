package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.netty.NettyClientManager;

/**
 * 客户端结束传输语音(SAE)
 *
 * @author Allen
 */
public class IMSendEndReq extends Packet {
    public static final String CMD = "SAE";
    private String audioId;

    public IMSendEndReq() {
        super(CMD);
    }

    public IMSendEndReq(String id) {
        super(CMD);
        this.audioId = id;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(audioId);
        return para2String();
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        this.audioId = sa[offset++];
        super.decodeArgs(sa, offset, length);
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        return sc.onReceiveVoiceEnd(audioId);
    }
}
