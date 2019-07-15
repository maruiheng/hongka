package com.cwtcn.kmlib.api;

import android.content.Context;
import android.text.TextUtils;

import com.cwtcn.kmlib.data.LoginData;
import com.cwtcn.kmlib.data.Wearer;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.protocol.TrackerInfoGetReq;
import com.cwtcn.kmlib.protocol.WearerAddReq;
import com.cwtcn.kmlib.protocol.WearerAdminChangeReq;
import com.cwtcn.kmlib.protocol.WearerAvatarGetReq;
import com.cwtcn.kmlib.protocol.WearerAvatarUploadReq;
import com.cwtcn.kmlib.protocol.WearerBoundGetReq;
import com.cwtcn.kmlib.protocol.WearerDelReq;
import com.cwtcn.kmlib.protocol.WearerQueryReq;
import com.cwtcn.kmlib.protocol.WearerUnbindReq;
import com.cwtcn.kmlib.protocol.WearerUpdateReq;
import com.cwtcn.kmlib.protocol.WearerVCCodeGetReq;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 用户管理
 * Created by Allen on 2016/12/16.
 */

public class KMWearerManager {

    public Context mContext;

    private static KMWearerManager mWearManager;

    public LoginData mLoginData;

    /** 登录成功后的认证ID */
    private String mUid = "";

    /** 用于保存设备版本号 **/
    public Map<String, String> mProductIDMap = new HashMap<String, String>();
    /** 用于保存设备软件版本号 **/
    public Map<String, String> mProductSVerMap = new HashMap<String, String>();
    /** 用于保存设备软件蓝牙版本号 **/
    public Map<String, String> mProductFVerMap = new HashMap<String, String>();
    /** 手表列表 */
    private List<Wearer> mWearers = new ArrayList<Wearer>();
    /** 手表的家庭成员 */
    public HashMap<String, List<Wearer>> mWearerBinders = new HashMap<String, List<Wearer>>();
    /** 当前选择手表 */
    private Wearer mCurrentWear;
    /** 是否是首次获取佩戴数据 */
    public boolean isFirstGetWearer = true;

    private KMWearerManager() {

    }

    public static KMWearerManager getInstance() {
        if(mWearManager == null) {
            mWearManager = new KMWearerManager();
        }
        return mWearManager;
    }

    public void init(Context context) {
        mContext = context;
    }

    //手表相关操作Begin
    //获取绑定手表
    //添加手表
    //编辑手表
    //编辑手表头像
    //删除手表
    /***
     * 获取当前设备
     *
     * @return
     */
    public Wearer getCurrentWearer() {
        if (mCurrentWear == null && getWearers() != null && getWearers().size() > 0) {
            mCurrentWear = getWearers().get(0);
        }
        return mCurrentWear;
    }

    /***
     * 获取当前设备
     *
     * @return
     */
    public Wearer getCurrentWearer(String imei) {
        if (imei == null || mWearers == null) {
            return null;
        }
        for (Wearer wear : mWearers) {
            if (wear.getImei().equals(imei)) {
                return wear;
            }
        }
        return mCurrentWear;
    }

    /***
     * 获取用户所有设备列表
     *
     * @return
     */
    public List<Wearer> getWearers() {
        return mWearers;
    }

    /**
     * 设置手表列表
     * @param wearers
     */
    public void setWearers(List<Wearer> wearers) {
        if(mWearers == null) {
            mWearers = new ArrayList<Wearer>();
        }
        mWearers.clear();
        mWearers.addAll(wearers);
    }


    /***
     * 获取手表的名字
     *
     * @param imei
     * @return
     */
    public String getWearerName(String imei) {
        if (imei == null || mWearers == null) {
            return "";
        }
        for (Wearer wear : mWearers) {
            if (wear.getImei().equals(imei)) {
                return wear.getWearerName();
            }
        }
        return "";
    }

    /***
     * 获取绑定手表的ID
     *
     * @param imei
     * @return
     */
    public String getWearerId(String imei) {
        if (imei == null || mWearers == null) {
            return "";
        }
        for (Wearer wear : mWearers) {
            if (wear.getImei().equals(imei)) {
                return wear.getWearerId();
            }
        }
        return "";
    }

    /**
     * 添加手表
     * @param wearer
     */
    public void addWearers(Wearer wearer) {
        if(mWearers == null) {
            mWearers = new ArrayList<>();
        }
        mWearers.add(wearer);
    }

    /***
     * 移除手表
     * @param imei
     */
    public void removeWearer(String imei) {
        if (TextUtils.isEmpty(imei) || mWearers == null) {
            return;
        }
        for (Wearer wear : mWearers) {
            if (wear.getImei().equals(imei)) {
                mWearers.remove(wear);
                return;
            }
        }
    }
    /**
     * 根据二维码绑定手表
     *
     * @param code
     */
    public void wearerAdd(String code) {
        NettyClientManager.addOutQ(new WearerAddReq(code));
    }

    /**
     * 动态验证码方式绑定手表
     *
     * @param wearer
     */
    public void wearerAdd(Wearer wearer, String validationCode) {
        NettyClientManager.addOutQ(new WearerAddReq(wearer, validationCode));
    }

    /***
     * 请求绑定手表的验证码
     * @param imei
     */
    public void getVC4BindWearer(String imei) {
        NettyClientManager.addOutQ(new WearerVCCodeGetReq(imei));
    }

    /**
     * 更新手表
     *
     * @param wearer
     */
    public void wearerUpdate(Wearer wearer) {
        NettyClientManager.addOutQ(new WearerUpdateReq(wearer));
    }

    /**
     * 删除手表
     *
     * @param wearId
     */
    public void wearerDel(String wearId) {
        NettyClientManager.addOutQ(new WearerDelReq(wearId));
    }

    /**
     * 查询绑定的手表
     */
    public void wearerQuery() {
        NettyClientManager.addOutQ(new WearerQueryReq());
    }

    public String getAreaId(String imei){
        if (imei == null || mWearers == null) {
            return "";
        }
        for (Wearer wear : mWearers) {
            if (wear.getImei().equals(imei)) {
                if (wear.getAreas()!=null&&wear.getAreas().size()>0){
                    return wear.getAreas().get(0).getId();
                }else {
                    return "";
                }
            }
        }
        return "";
    }

    /**
     * 上传绑定头像
     *
     * @param wearId
     * @param file
     */
    public void wearerAvatarUpload(String wearId, File file) {
        NettyClientManager.addOutQ(new WearerAvatarUploadReq(wearId, file));
    }

    /**
     * 获取绑定头像
     *
     * @param wearId
     */
    public void wearerAvatarGet(String wearId) {
        NettyClientManager.addOutQ(new WearerAvatarGetReq(wearId));
    }



    /**
     * 获取手表信息
     * @param imei
     */
    public void trackerInfoGet(String imei) {
        NettyClientManager.addOutQ(new TrackerInfoGetReq(imei));
    }
    //手表相关操作End----------------------------------

    //家庭成员Begin
    /**
     * 获取手表的绑定关系
     *
     * @param imei
     */
    public void wearerBoundGet(String imei) {
        NettyClientManager.addOutQ(new WearerBoundGetReq(imei));
    }

    /**
     * 手表管理员变更
     */
    public void wearerAdminChange(String imei, String wearId) {
        NettyClientManager.addOutQ(new WearerAdminChangeReq(imei, wearId));
    }

    /***
     * 手表管理员解除其它成员绑定
     *
     * @param imei
     */
    public void wearerUnbind(String imei) {
        NettyClientManager.addOutQ(new WearerUnbindReq(imei));
    }
    //家庭成员End

    /**
     * 清除数据
     */
    public void clearData() {

    }
}
