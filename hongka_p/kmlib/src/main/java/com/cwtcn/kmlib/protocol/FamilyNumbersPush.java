package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMContactManager;
import com.cwtcn.kmlib.data.ContactList;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.ContactRespData;
import com.cwtcn.kmlib.util.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.15 推送亲情号码
 *
 * @author Allen
 */
public class FamilyNumbersPush extends Packet {
    public static final String CMD = "P_FN";
    private List<ContactRespData> para;
    private String imei = null;

    public FamilyNumbersPush() {
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }


    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        try {
            String mResponse = sa[offset++];
            status = "0";
            Gson gson = new Gson();
            para = gson.fromJson(mResponse, new TypeToken<List<ContactRespData>>() {
            }.getType());
            if (para != null && para != null && para.size() > 0) {
                for (ContactRespData mFn : para) {
                    KMContactManager.getInstance().setContacts(mFn.imei, mFn.fns);
                    imei = mFn.imei;
                }
            }
        } catch (Exception ex) {
            MyLog.e(CMD, "exception");
        }
    }

    @Override
    public Packet respond(NettyClientManager cm) {
        if (para != null) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_CONTACT_GET, status, msg, new ContactList(imei, KMContactManager.getInstance().getContacts(imei))));
        }
        return super.respond(cm);
    }
}
