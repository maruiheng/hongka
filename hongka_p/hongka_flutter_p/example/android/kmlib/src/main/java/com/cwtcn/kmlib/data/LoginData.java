package com.cwtcn.kmlib.data;

import com.cwtcn.kmlib.api.KMManager;
import com.cwtcn.kmlib.util.Constants;
import com.cwtcn.kmlib.util.OauthPassword;
import com.cwtcn.kmlib.util.Utils;

/**
 * 登录数据
 * Created by Allen on 2016/12/8.
 */

public class LoginData {
    /** 用户名(手机号) */
    private String loginName;
    /** 密码*/
    private String password;
    /** 第三方登录成功的认证ID */
    private String authId;
    /** 第三方登录的途径 */
    private int authSource;
    /**第三方登录用户名*/
    private String newName;
    /** 客户端ID */
    private String clientID = "abardeen";
    /** 地图类型 amap;baidu;google */
    private String mapType;
    /** 第三方推送ID */
    private String deviceToken;
    /** token，用于客户端重连，优化登录操作 */
    //private String token = "";

    /** 协议版本 */
    private String pVer = Constants.PROTOCAL_VERSION;
    /** 软件版本 */
    private String sVer;
    /** 客户端环境 */
    private String os = "Android " + android.os.Build.VERSION.RELEASE;
    /** 客户端语言 */
    private String lang;//"cn";

    public LoginData(String username, String pwd, String clientId, String mapType, String deviceToken) {
        this.loginName = username;
        this.password = pwd;
        this.clientID = clientId;
        this.mapType = mapType;
        this.deviceToken = deviceToken;
        this.sVer = Utils.getAppVersion(KMManager.getInstance().mContext);
        this.lang = Utils.getLocale(KMManager.getInstance().mContext);
    }

    public LoginData(String username,String clientId, String mapType, String deviceToken) {
        this.loginName = username;
        this.password = "11111111";
        this.clientID = clientId;
        this.mapType = mapType;
        this.deviceToken = deviceToken;
        this.sVer = Utils.getAppVersion(KMManager.getInstance().mContext);
        this.lang = Utils.getLocale(KMManager.getInstance().mContext);
    }

    public LoginData(String authId, int souce, String pwd, String clientId, String mapType, String deviceToken) {
        this.authId = authId;
        this.newName = OauthPassword.getOauthName(souce, this.authId);
        this.password = pwd;
        this.clientID = clientId;
        this.mapType = mapType;
        this.deviceToken = deviceToken;
        this.sVer = Utils.getAppVersion(KMManager.getInstance().mContext);
        this.lang = Utils.getLocale(KMManager.getInstance().mContext);
        this.authSource = souce;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthId() {
        return authId;
    }

    public int getAuthSource() {
        return authSource;
    }

    public String getNewName() {
        return newName;
    }

    public String getClientID() {
        return clientID;
    }

    public String getMapType() {
        return mapType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getpVer() {
        return pVer;
    }

    public String getsVer() {
        return sVer;
    }

    public String getOs() {
        return os;
    }

    public String getLang() {
        return lang;
    }
}
