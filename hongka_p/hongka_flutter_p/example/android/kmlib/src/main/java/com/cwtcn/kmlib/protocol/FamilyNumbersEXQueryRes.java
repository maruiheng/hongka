package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMContactManager;
import com.cwtcn.kmlib.data.ContactList;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.ContactRespData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * kt04查询亲情号码返回
 *
 * @author Allen
 */
public class FamilyNumbersEXQueryRes extends Packet {
    public static final String CMD = "R_Q_FN_EX";
    private List<ContactRespData> para;
    private String imei;

    public FamilyNumbersEXQueryRes() {
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
            status = sa[offset++];
            offset++;
            if (status.equals(NettyClientManager.STR_CODE_OK)) {
                String jsonData = sa[offset++];
                String mResponse = jsonData;
                Gson gson = new Gson();
                para = gson.fromJson(jsonData, new TypeToken<List<ContactRespData>>() {}.getType());
                if (para != null && para.size() > 0) {
                    for (ContactRespData fn : para) {
                        KMContactManager.getInstance().setContacts(fn.imei, fn.fns);
                        imei = fn.imei;
                    }
                }
            } else {
                msg = sa[offset++];
            }
        } catch (Exception e) {

        }
    }

    @SuppressWarnings("static-access")
    @Override
    public Packet respond(NettyClientManager cm) {
        if (para != null) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_CONTACT_GET, status, msg, new ContactList(imei, KMContactManager.getInstance().getContacts(imei))));
        }
        return super.respond(cm);
    }
}
