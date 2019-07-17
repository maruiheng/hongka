package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * function: functionName
 *
 * @author xhl
 * @date 2019/6/25 11:11
 */

public class SendStorySetRes extends Packet {
//    public static final String CMD = "R_M_DOWN_RESOURCE";
    public static final String CMD = "sss";

    private String msg;

    public SendStorySetRes(){
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_PUSH_STORY_INFO, status, msg));
        return super.respond(sc);
    }
    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        try {
            status = sa[offset++];
            offset++;
            if(status.equals(NettyClientManager.STR_CODE_OK)) {
                offset++;
                if(sa.length > offset) {
                    msg = sa[offset];
                }else{
                    msg = "";
                }
            } else if(status.equals(NettyClientManager.STR_CODE_NOT_LINE)) {
                msg = sa[offset];
            } else {
                offset++;
                msg = sa[offset];
            }
        } catch (Exception e) {

        }
    }
}

