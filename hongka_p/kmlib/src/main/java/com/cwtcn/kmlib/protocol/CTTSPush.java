package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.api.KMContactManager;
import com.cwtcn.kmlib.api.KMManager;
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
 * 3.2.1.17推送Tracker联系人列表(P_CTTS)
 *
 * @author allen
 */
public class CTTSPush extends Packet {
    public static final String CMD = "P_CTTS";
    private List<ContactRespData> para;
    private String imei;

    public CTTSPush() {
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
            if (para != null && para.size() > 0) {
                for (ContactRespData mFn : para) {
                    KMContactManager.getInstance().setContacts(mFn.imei, mFn.ctts);
                    imei = mFn.imei;
                    if (!TextUtils.isEmpty(mFn.imageServer)) {
                        KMManager.getInstance().mDownloadUrl = mFn.imageServer;
                    }
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
