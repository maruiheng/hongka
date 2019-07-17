package com.cwtcn.kmlib.api;

import android.text.TextUtils;

import com.cwtcn.kmlib.protocol.NewUserRegisterRes;
import com.cwtcn.kmlib.protocol.PWDResetRes;
import com.cwtcn.kmlib.protocol.PWDResetVCRes;
import com.cwtcn.kmlib.protocol.RegisterEmailRes;
import com.cwtcn.kmlib.protocol.RegisterImageVCRes;
import com.cwtcn.kmlib.protocol.RegisterMobileRes;
import com.cwtcn.kmlib.protocol.RegisterMobileVCRes;
import com.cwtcn.kmlib.protocol.UserMobileUpdateRes;
import com.cwtcn.kmlib.protocol.UserMobileVCRes;
import com.cwtcn.kmlib.protocol.UserProfileGetRes;
import com.cwtcn.kmlib.protocol.UserProfileSetRes;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * 处理HTTP请求
 * Created by Allen on 2017/4/5.
 */

public class KMHttpHelper {

    /**
     * 手机号注册
     *
     * @param mobile
     * @param pwd
     * @param captcha
     */
    public static void userRegister(String mobile, String pwd, String captcha,String clientId) {
        OkHttpUtils.post().url(KMConstants.HTTP_API + "phoneRegistration?")
                .addParams("loginName", mobile)
                .addParams("password", pwd)
                .addParams("captcha", captcha)
                .addParams("source", KMConstants.UserType.TYPE_MOBILE)
                .addParams("clientId", clientId)
                .build()
                .execute(new RegisterMobileRes());
    }

    /**
     * QQ用户注册
     *
     * @param authId
     * @param password
     */
    public static void userRegister4QQ(String authId, String password) {
        OkHttpUtils.post().url(KMConstants.HTTP_API + "registration?")
                .addParams("loginName", "QQ_" + authId)
                .addParams("password", password)
                .addParams("confirmPassword", password)
                .addParams("email", authId + "@abardeen.com")
                .addParams("uid", authId)
                .addParams("source", KMConstants.UserType.TYPE_QQ)
                .build()
                .execute(new RegisterEmailRes());
    }

    /**
     * 微信用户注册
     *
     * @param authId
     * @param password
     */
    public static void userRegister4WX(String authId, String password) {
        OkHttpUtils.post().url(KMConstants.HTTP_API + "registration?")
                .addParams("loginName", "WX_" + authId)
                .addParams("password", password)
                .addParams("confirmPassword", password)
                .addParams("email", authId + "@abardeen.com")
                .addParams("uid", authId)
                .addParams("source", KMConstants.UserType.TYPE_WX)
                .build()
                .execute(new RegisterEmailRes());
    }

    /**
     * 微信用户注册
     *
     * @param authId
     * @param password
     */
    public static void userRegister4WB(String authId, String password) {
        OkHttpUtils.post().url(KMConstants.HTTP_API + "registration?")
                .addParams("loginName", "WB_" + authId)
                .addParams("password", password)
                .addParams("confirmPassword", password)
                .addParams("email", authId + "@abardeen.com")
                .addParams("uid", authId)
                .addParams("source", KMConstants.UserType.TYPE_WB)
                .build()
                .execute(new RegisterEmailRes());
    }

    /***
     * 获取注册图片验证码
     * @param mobile
     */
    public static void getImageVC4Register(String mobile) {
        OkHttpUtils.post().url(KMConstants.HTTP_API + "registration_img?")
                .addHeader("Connection", "close")
                .addParams("loginName", mobile)
                .build()
                .execute(new RegisterImageVCRes());
    }

    /**
     * 提交图片验证码，获取手机验证码
     * @param mobile
     * @param code
     */
    public static void submitImageVC4Register(String mobile, String code) {
        OkHttpUtils.post().url(KMConstants.HTTP_API + "img_code?")
                .addParams("loginName", mobile)
                .addParams("code", code)
                .build()
                .execute(new RegisterMobileVCRes());
    }

    /**
     * 获取重置密码的验证码
     *
     * @param mobile
     */
    public static void getVC4ResetPWD(String mobile) {
        OkHttpUtils.post().url(KMConstants.HTTP_API + "password_reset_requestEx?")
                .addParams("param", mobile)
                .build()
                .execute(new PWDResetVCRes());
    }

    /**
     * 重置密码
     *
     * @param mobile
     * @param password
     * @param captcha
     */
    public static void resetPwd(String mobile, String password, String captcha) {
        OkHttpUtils.post().url(KMConstants.HTTP_API + "password_resetEx?")
                .addParams("loginName", mobile)
                .addParams("password", password)
                .addParams("captcha", captcha)
                .build()
                .execute(new PWDResetRes());
    }

    /***
     * 用户信息查询
     * @param uid
     */
    public static void getUserInfo(String uid) {
        OkHttpUtils.post().url(KMConstants.HTTP_API + "member_info?")
                .addParams("memberId", uid)
                .build()
                .execute(new UserProfileGetRes());
    }

    /***
     * 更新用户信息
     * @param uid
     * @param userName
     * @param email
     */
    public static void updateUserInfo(String uid, String userName, String email) {
        OkHttpUtils.post().url(KMConstants.HTTP_API + "member_modify?")
                .addParams("name", userName)
                .addParams("email", email)
                .addParams("memberId", uid)
                .build()
                .execute(new UserProfileSetRes());
    }

    /**
     * 获取更改用户手机号的验证码
     *
     * @param uid
     * @param phone
     * @param newPhone
     */
    public static void getVC4updateUserMobile(String uid, String phone, String newPhone) {
        OkHttpUtils.post().url(KMConstants.HTTP_API + "phone_mod_captcha?")
                .addParams("phone", phone)
                .addParams("newPhone", newPhone)
                .addParams("memberId", uid)
                .build()
                .execute(new UserMobileVCRes());
    }

    /**
     * 更新手机号
     *
     * @param uid
     * @param phone
     * @param newPhone
     * @param captcha
     */
    public static void updateUserMobile(String uid, String phone, String newPhone, String captcha) {
        OkHttpUtils.post().url(KMConstants.HTTP_API + "phone_modify?")
                .addParams("phone", phone)
                .addParams("newPhone", newPhone)
                .addParams("memberId", uid)
                .addParams("captcha", captcha)
                .build()
                .execute(new UserMobileUpdateRes());
    }


    /**
     * 新版手机号快速注册20190716
     * @param loginName
     * @param clientId
     */
    public static void userRegister(String loginName,String clientId){

        String emailstr = "";
        if (TextUtils.isEmpty(clientId)){
            emailstr = loginName+"@"+"demo.com";
        }else {
            emailstr = loginName+"@"+clientId+".com";
        }

        OkHttpUtils.post().url(KMConstants.HTTP_API + "registration?")
                .addParams("loginName", loginName)
                .addParams("email", emailstr)
                .addParams("password", "11111111")
                .addParams("confirmPassword", "11111111")
                .addParams("source", KMConstants.UserType.TYPE_DF)
                .addParams("clientId", clientId)
                .build()
                .execute(new NewUserRegisterRes());
    }

}
