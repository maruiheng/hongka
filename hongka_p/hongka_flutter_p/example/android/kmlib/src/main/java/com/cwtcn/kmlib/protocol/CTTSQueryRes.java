package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.api.KMContactManager;
import com.cwtcn.kmlib.api.KMManager;
import com.cwtcn.kmlib.data.ContactData;
import com.cwtcn.kmlib.data.ContactList;
import com.cwtcn.kmlib.data.FriendData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.ContactRespData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 3.2.1.26响应查询手表联系人 (R_MG_CTTS)
 *
 * @author Allen
 */
public class CTTSQueryRes extends Packet {
    public static final String CMD = "R_MG_ALL_CTTS";
    private List<ContactRespData> para;
    private String imei;

    public CTTSQueryRes() {
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
                String mResponse = sa[offset++];
                Gson gson = new Gson();
                para = gson.fromJson(mResponse, new TypeToken<List<ContactRespData>>() {}.getType());
                if (para != null && para.size() > 0) {
                    for (ContactRespData data : para) {
                        List<ContactData> ctts = new ArrayList<ContactData>();
                        int no = 0;
                        if (data.ctts != null && data.ctts.size() > 0) {
                            ctts.addAll(data.ctts);
                            no = data.ctts.get(data.ctts.size() - 1).getNo() + 100;
                        }

                        if (data.friends != null && data.friends.size() > 0) {
                            for (FriendData fri : data.friends) {
                                ctts.add(new ContactData(no, fri.getFriendMobile(), fri.getName(), fri.getPic(), 3, fri.getId(), fri.getImei()));
                                no++;
                            }
                        }
                        if (ctts != null && ctts.size() > 0) {
                            KMContactManager.getInstance().setContacts(data.imei, ctts);
                        } else {
                            KMContactManager.getInstance().setContacts(data.imei, new ArrayList<ContactData>());
                        }
                        if (!TextUtils.isEmpty(data.imageServer)) {
                            KMManager.getInstance().mDownloadUrl = data.imageServer;
                        }
                        imei = data.imei;
                    }
                }
            } else {
                msg = sa[offset++];
            }
        } catch (Exception e) {

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
