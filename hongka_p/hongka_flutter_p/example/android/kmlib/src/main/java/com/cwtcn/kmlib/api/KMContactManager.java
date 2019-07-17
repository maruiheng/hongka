package com.cwtcn.kmlib.api;

import android.content.Context;

import com.cwtcn.kmlib.data.ContactData;
import com.cwtcn.kmlib.data.FriendData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.protocol.CTTSQueryReq;
import com.cwtcn.kmlib.protocol.CTTSSetReq;
import com.cwtcn.kmlib.protocol.FamilyNumbersEXQueryReq;
import com.cwtcn.kmlib.protocol.FamilyNumbersEXSetReq;
import com.cwtcn.kmlib.protocol.FamilyNumbersQueryReq;
import com.cwtcn.kmlib.protocol.FamilyNumbersSetReq;
import com.cwtcn.kmlib.protocol.WearerFriendDelReq;
import com.cwtcn.kmlib.protocol.WearerFriendQueryReq;
import com.cwtcn.kmlib.protocol.WearerFriendUpdateReq;
import com.cwtcn.kmlib.util.FunUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联系人管理API
 * Created by Allen on 2016/12/16.
 */

public class KMContactManager {

    public Context mContext;

    private static KMContactManager contactManager;

    /** 用于保存设备的联系人 */
    private Map<String, List<ContactData>> mContactMap = new HashMap<String, List<ContactData>>();
    /** 用于保存设备的小伙伴 */
    private Map<String, List<FriendData>> mFriendMap = new HashMap<String, List<FriendData>>();

    private KMContactManager() {

    }

    public static KMContactManager getInstance() {
        if(contactManager == null) {
            contactManager = new KMContactManager();
        }
        return contactManager;
    }

    public void init(Context context) {
        mContext = context;
    }

    //联系人

    /***
     * 获取联系人
     * KT01系列的协议为Q_FN
     * KT04系列的协议为Q_FN_EX
     * T1601等16年后的新机型为MS_CTTS
     * @param imei
     */
    public void queryContacts(String imei) {
        if(FunUtils.getGeneration(imei) < 4) {
            NettyClientManager.addOutQ(new FamilyNumbersQueryReq(imei));
        } else if(FunUtils.getGeneration(imei) == 4) {
            NettyClientManager.addOutQ(new FamilyNumbersEXQueryReq(imei));
        } else if(FunUtils.getGeneration(imei) > 4) {
            NettyClientManager.addOutQ(new CTTSQueryReq(imei));
        }
    }

    /**
     * 设置联系人
     * @param imei
     * @param data
     */
    public void saveContact(String imei, List<ContactData> data) {
        if(FunUtils.getGeneration(imei) < 4) {
            NettyClientManager.addOutQ(new FamilyNumbersSetReq(imei, data));
        } else if(FunUtils.getGeneration(imei) == 4) {
            NettyClientManager.addOutQ(new FamilyNumbersEXSetReq(imei, data));
        } else if(FunUtils.getGeneration(imei) > 4) {
            NettyClientManager.addOutQ(new CTTSSetReq(imei, data));
        }
    }


    /***
     * 获取联系人缓存
     * @param imei
     * @return
     */
    public List<ContactData> getContacts(String imei) {
        return mContactMap.get(imei);
    }

    /**
     * 保存联系人
     * @param imei
     * @param data
     */
    public void setContacts(String imei, List<ContactData> data) {
        mContactMap.put(imei, data);
    }

    //小伙伴

    /**
     * 查询小伙伴
     * @param imei
     */
    public void queryFriend(String imei) {
        if(!FunUtils.isTrackerHasBleFriends(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_FRIEND_GET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new WearerFriendQueryReq(imei));
    }

    /**
     * 更新小伙伴
     * @param imei
     * @param friend
     */
    public void updateFriend(String imei, FriendData friend) {
        if(!FunUtils.isTrackerHasBleFriends(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_FRIEND_GET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new WearerFriendUpdateReq(imei, friend));
    }

    /**
     * 删除小伙伴
     * @param imei
     * @param friends
     */
    public void delFriend(String imei, List<FriendData> friends) {
        if(!FunUtils.isTrackerHasBleFriends(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_FRIEND_GET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new WearerFriendDelReq(imei, friends));
    }

    /**
     * 获取小伙伴
     * @param imei
     * @return
     */
    public List<FriendData> getFriends(String imei) {
        if(!FunUtils.isTrackerHasBleFriends(imei)) {
            return null;
        }
        return mFriendMap.get(imei);
    }

    /***
     * 保存小伙伴
     * @param imei
     * @param friends
     */
    public void setFriends(String imei, List<FriendData> friends) {
        mFriendMap.put(imei, friends);
    }

    /**
     * 清除数据
     */
    public void clearData() {
        if(mContactMap != null) {
            mContactMap.clear();
        }
        if(mFriendMap != null) {
            mFriendMap.clear();
        }
    }
}
