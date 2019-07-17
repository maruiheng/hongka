package com.cwtcn.kmlib.resp;

import com.cwtcn.kmlib.data.ContactData;
import com.cwtcn.kmlib.data.TrackerMuteData;

import java.util.List;

/**
 * 登录返回数据
 * Created by Allen on 2016/12/8.
 */
public class LoginRespData {

    /** Build版本 */
    public String bVer;
    /** 手表型号 */
    public String productId;
    /** 蓝牙版本 */
    public String fVer;
    /** 手表的软件版本 */
    public String sVer;
    public String imei;
    /** 是否在线 */
    public String online;
    /** 亲情号码 */
    //public FnStatus fnStatus;
    /** 静音时段状态对象 */
    //public MrStatus mrStatus;

    public static class FnStatus {
        public List<ContactData> fns;
        public String imei;
        public String lut;
        public String userId;
        public String userName;
    }

    public static class MrStatus {
        public List<TrackerMuteData> mrs;
        public String userId;
        public String userName;
    }

    /** 客户端登录的ID */
    public String memberID;
}
