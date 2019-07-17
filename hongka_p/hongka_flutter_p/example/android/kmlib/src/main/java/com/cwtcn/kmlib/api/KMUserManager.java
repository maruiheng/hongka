package com.cwtcn.kmlib.api;

import android.content.Context;
import android.text.TextUtils;

import com.cwtcn.kmlib.data.LoginData;
import com.cwtcn.kmlib.data.TrackerNotice;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.netty.NettyMonitor;
import com.cwtcn.kmlib.protocol.ChangePasswordReq;
import com.cwtcn.kmlib.util.Constants;
import com.cwtcn.kmlib.util.Encrypt;
import com.cwtcn.kmlib.util.PrefsUtil;
import com.cwtcn.kmlib.api.KMDBHelper.INotifyDBChange;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 用户管理
 * Created by Allen on 2016/12/16.
 */

public class KMUserManager {

    public Context mContext;

    private static KMUserManager userManager;

    public LoginData mLoginData;

    /** 登录成功后的认证ID */
    private String mUid = "";

    private KMUserManager() {

    }

    public static KMUserManager getInstance() {
        if(userManager == null) {
            userManager = new KMUserManager();
        }
        return userManager;
    }

    public void init(Context context) {
        mContext = context;
    }

    //注册

    /**
     * 手机号注册
     * @param mobile
     * @param pwd
     * @param code
     */
    public void registerWithMobile(String mobile, String pwd, String code,String clientId) {
        KMHttpHelper.userRegister(mobile, pwd, code,clientId);
    }

    /**
     * 新版手机号注册20190716
     * @param phoneNum
     * @param clientId
     */
    public void quickRegisterWithMobile(String phoneNum,String clientId){
        KMHttpHelper.userRegister(phoneNum,clientId);
    }

    /**
     * 手机号注册图片验证码
     * @param mobile
     */
    public void getImageVC4Register(String mobile) {
        KMHttpHelper.getImageVC4Register(mobile);
    }

    /***
     * 提交注册图片验证码
     * @param mobile
     * @param code
     */
    public void submitImageVC4Register(String mobile, String code) {
        KMHttpHelper.submitImageVC4Register(mobile, code);
    }

    /**
     * QQ用户注册
     * @param authId
     * @param password
     */
    public void registerWithQQ(String authId, String password) {
        KMHttpHelper.userRegister4QQ(authId, password);
    }

    /**
     * 微信用户注册
     * @param authId
     * @param password
     */
    public void registerWithWX(String authId, String password) {
        KMHttpHelper.userRegister4WX(authId, password);
    }

    /**
     * 微博用户注册
     * @param authId
     * @param password
     */
    public void registerWithWB(String authId, String password) {
        KMHttpHelper.userRegister4WB(authId, password);
    }

    //重置密码

    /**
     * 重置密码
     * @param mobile
     * @param password
     * @param code
     */
    public void resetPwd(String mobile, String password, String code) {
        KMHttpHelper.resetPwd(mobile, password, code);
    }

    /**
     * 重置密码手机验证码
     * @param mobile
     */
    public void resetPwdVC(String mobile) {
        KMHttpHelper.getVC4ResetPWD(mobile);
    }

    /**
     * 修改密码
     * @param orgPwd
     * @param newPwd
     */
    public void changePassword(String orgPwd, String newPwd) {
        NettyClientManager.addOutQ(new ChangePasswordReq(orgPwd, newPwd));
    }

    /**
     * 用户信息查看
     */
    public void getUserInfo() {
        KMHttpHelper.getUserInfo(getUID());
    }

    /**
     * 用户信息修改
     * @param userName
     * @param email
     */
    public void updateUserInfo(String userName, String email) {
        KMHttpHelper.updateUserInfo(getUID(), userName, email);
    }

    /**
     * 手机号码修改的验证码
     * @param phone
     * @param newPhone
     */
    public void updateUserPhoneVC(String phone, String newPhone) {
        KMHttpHelper.getVC4updateUserMobile(getUID(), phone, newPhone);
    }

    /**
     * 手机号码修改
     * @param phone
     * @param newPhone
     * @param code
     */
    public void updateUserPhone(String phone, String newPhone, String code) {
        KMHttpHelper.updateUserMobile(getUID(), phone, newPhone, code);
    }

    //登录Begin
    /***
     * 登录
     * @param data
     */
    public void login(LoginData data) {
        if(NettyClientManager.getInstance().isConnected()) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_LOGIN, NettyClientManager.STR_CODE_OK, ""));
            return;
        }
        mLoginData = data;
        if(mContext != null) {
            NettyClientManager.getInstance().enableConn.set(true);
            NettyMonitor.startNettyService(mContext);
        }
        PrefsUtil.setSharedPreferencesAll(mContext, mLoginData.getLoginName(), Constants.Prefs.KEY_USER);
    }


    /**
     * 检测是否有用户名密码
     *
     * @return
     */
    public boolean hasUserAndPwd() {
        if(mContext == null) {
            return false;
        }
        String pass = PrefsUtil.getStringSharedPreferences(mContext, Constants.Prefs.KEY_PWD);
        pass = Encrypt.decryptPwd(pass);
        String user = PrefsUtil.getStringSharedPreferences(mContext, Constants.Prefs.KEY_USER);
        if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(user)) {
            return false;
        }
        return true;
    }

    /**
     * 退出
     */
    public void exit() {
        NettyMonitor.stopNettyService(mContext);
    }

    /***
     * 返回UID
     *
     * @return
     */
    public String getUID() {
        if (TextUtils.isEmpty(mUid)) {
            mUid = PrefsUtil.getStringSharedPreferences(mContext, Constants.Prefs.KEY_UUID);
        }
        return mUid;
    }

    /**
     * 设置用户ID
     */
    public void setUID(String uid) {
        this.mUid = uid;
        if (!TextUtils.isEmpty(uid)) {
            PrefsUtil.setSharedPreferencesAll(mContext, uid, Constants.Prefs.KEY_UUID);
        }
    }

    /***
     * 返回UID
     *
     * @return
     */
    public String getUser() {
        String user = PrefsUtil.getStringSharedPreferences(mContext, Constants.Prefs.KEY_USER);
        return user;
    }
    //登录End--------------------------------------

    //提醒记录

    /**
     * 查询提醒记录
     * @param uid
     * @param offset
     * @param limit
     */
    public List<TrackerNotice> noticeRecordQuery(String uid, int offset, int limit) {
        return KMDBHelper.getInstance().queryNotices(uid, offset, limit);
    }

    /***
     * 查询提醒记录的未读数
     * @param uid
     * @return
     */
    public long noticeRecordUnreadCount(String uid) {
        return KMDBHelper.getInstance().queryUnreadNoticeCount(uid);
    }

    /***
     * 根据id删除提醒记录
     * @param ids
     * @param dbChange
     */
    public void noticeDelById(List<Long> ids, INotifyDBChange dbChange) {
        KMDBHelper.getInstance().delNotice(ids, dbChange);
    }

    /**
     * 删除当前用户所有的提醒记录
     * @param uid
     * @param dbChange
     */
    public void noticeDelAll(String uid, INotifyDBChange dbChange) {
        KMDBHelper.getInstance().delAllNotice(uid, dbChange);
    }

    /**
     * 更新提醒记录已读
     * @param id
     * @param dbChange
     */
    public void noticeUpdateRead(long id, INotifyDBChange dbChange) {
        KMDBHelper.getInstance().updateNoticeReaded(id, dbChange);
    }

    /**
     * 更新提醒记录全部为已读
     * @param uid
     * @param dbChange
     */
    public void noticeUpdateReadAll(String uid, INotifyDBChange dbChange) {
        KMDBHelper.getInstance().updateNoticeReadAll(uid, dbChange);
    }

    public void clearData() {

    }
}
