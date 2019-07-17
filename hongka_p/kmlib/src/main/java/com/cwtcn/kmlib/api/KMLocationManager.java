package com.cwtcn.kmlib.api;

import android.content.Context;

import com.cwtcn.kmlib.data.AreaData;
import com.cwtcn.kmlib.data.HSLocationData;
import com.cwtcn.kmlib.data.HSTimeData;
import com.cwtcn.kmlib.data.LocationMark;
import com.cwtcn.kmlib.data.TrackerLeastData;
import com.cwtcn.kmlib.data.Wearer;
import com.cwtcn.kmlib.data.WiFiData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.protocol.AlertAreaEnableReq;
import com.cwtcn.kmlib.protocol.AlertAreaGetReq;
import com.cwtcn.kmlib.protocol.AlertAreaSetReq;
import com.cwtcn.kmlib.protocol.AlertAreaUpdateReq;
import com.cwtcn.kmlib.protocol.HSLocationDelReq;
import com.cwtcn.kmlib.protocol.HSLocationSetReq;
import com.cwtcn.kmlib.protocol.HSLocationUpdateReq;
import com.cwtcn.kmlib.protocol.HSLogQueryReq;
import com.cwtcn.kmlib.protocol.HSQueryReq;
import com.cwtcn.kmlib.protocol.HSTimeSetReq;
import com.cwtcn.kmlib.protocol.HSTimeUpdateReq;
import com.cwtcn.kmlib.protocol.LocMarkDelReq;
import com.cwtcn.kmlib.protocol.LocMarkQueryReq;
import com.cwtcn.kmlib.protocol.LocMarkSetReq;
import com.cwtcn.kmlib.protocol.LocMarkUpdateReq;
import com.cwtcn.kmlib.protocol.TauntQueryReq;
import com.cwtcn.kmlib.protocol.TrackerLDGetAllReq;
import com.cwtcn.kmlib.protocol.TrackerLDGetReq;
import com.cwtcn.kmlib.protocol.TrackerLocHistoryGetReq;
import com.cwtcn.kmlib.protocol.WifiLocUpdateReq;
import com.cwtcn.kmlib.util.FunUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

/**
 * 位置管理
 * Created by Allen on 2016/12/16.
 */

public class KMLocationManager {

    public Context mContext;

    private static KMLocationManager locationManager;

    private HashMap<String, AreaData> mAreaMap = new HashMap<String, AreaData>();
    public HashMap<String, TrackerLeastData> mTLDMap = new HashMap<String, TrackerLeastData>();

    private KMLocationManager() {

    }

    public static KMLocationManager getInstance() {
        if(locationManager == null) {
            locationManager = new KMLocationManager();
        }
        return locationManager;
    }

    public void init(Context context) {
        mContext = context;
    }

    //最新位置
    /**
     * 添加请求:3.77获取手表最新数据
     *
     * @param imei
     */
    public void trackerLDGet(String imei) {
        NettyClientManager.addOutQ(new TrackerLDGetReq(imei));
    }

    /**
     * 添加请求:3.79获取所有手表最新位置
     */
    public void allTrackerLDGet() {
        NettyClientManager.addOutQ(new TrackerLDGetAllReq());
    }

    /**
     * 添加请求:3.81获取手表历史位置
     *
     * @param imei
     * @param date
     */
    public void trackerHistoryGet(String imei, String date) {
        NettyClientManager.addOutQ(new TrackerLocHistoryGetReq(imei, date));
    }

    //电子栅栏---------start-------------//
    /**
     * 初始化电子栅栏
     */
    public void initAlertArea() {
        if (KMWearerManager.getInstance().getWearers() != null && KMWearerManager.getInstance().getWearers().size() > 0) {
            for (Wearer wearer : KMWearerManager.getInstance().getWearers()) {
                if (wearer.areas != null && wearer.areas.size() > 0) {
                    alertAreaGet(wearer.areas.get(0).getId());
                }
            }
        }
    }

    /**
     * 添加请求:3.49获取报警区域
     *
     * @param areaID
     */
    public void alertAreaGet(String areaID) {
        NettyClientManager.addOutQ(new AlertAreaGetReq(areaID));
    }

    /**
     * 添加请求:3.47创建报警区域
     *
     * @param imei
     * @param data
     */
    public void alertAreaCreate(String imei, AreaData data) {
        NettyClientManager.addOutQ(new AlertAreaSetReq(imei, data));
    }

    /**
     * 添加请求:3.48更新报警区域
     *
     * @param imei
     * @param data
     */
    public void alertAreaUpdate(String imei, AreaData data) {
        NettyClientManager.addOutQ(new AlertAreaUpdateReq(imei, data));
    }

    /**
     * 添加请求:3.2.18.10设置佩戴对象报警开关（ENABLE_ALERT_AREA）
     *
     * @param areaID
     */
    public void alertAreaEnable(String imei, String areaID, boolean enable) {
        NettyClientManager.addOutQ(new AlertAreaEnableReq(imei, areaID, enable));
    }

    /**
     * 添加请求:3.50删除报警区域
     *
     * @param areaID
     */
    public void alertAreaDel(String areaID) {

    }

    public AreaData getAlertArea(String id) {
        return mAreaMap.get(id);
    }

    public void setAlertArea(AreaData data) {
        mAreaMap.put(data.getId(), data);
    }

    //电子栅栏---------end-------------//

    //家校提醒---------start-------------//
    /**
     * 家校信息查询
     * @param imei
     */
    public void getHSInfo(String imei) {
        NettyClientManager.addOutQ(new HSQueryReq(imei, Long.valueOf(KMWearerManager.getInstance().getWearerId(imei))));
    }

    public void getHSInfo() {
        NettyClientManager.addOutQ(new HSQueryReq());
    }

    /***
     * 创建家的位置
     * @param data
     */
    public void createHomeLocation(HSLocationData data) {
        if(data != null) {
            data.setWearerId(Long.parseLong(KMWearerManager.getInstance().getWearerId(data.getImei())));
            data.setType(1);
            NettyClientManager.addOutQ(new HSLocationSetReq(data));
        }
    }

    /***
     * 更新家的位置
     * @param data
     */
    public void updateHomeLocation(HSLocationData data) {
        if(data != null) {
            data.setWearerId(Long.parseLong(KMWearerManager.getInstance().getWearerId(data.getImei())));
            data.setType(1);
            NettyClientManager.addOutQ(new HSLocationUpdateReq(data));
        }
    }

    /**
     * 删除家的位置
     * @param imei
     * @param ids
     */
    public void delHomeLocation(String imei, List<String> ids) {
        NettyClientManager.addOutQ(new HSLocationDelReq(ids, imei, KMWearerManager.getInstance().getWearerId(imei)));
    }

    /**
     * 创建学校的位置
     * @param data
     */
    public void createSchoolLocation(HSLocationData data) {
        if(data != null) {
            data.setWearerId(Long.parseLong(KMWearerManager.getInstance().getWearerId(data.getImei())));
            data.setType(2);
            NettyClientManager.addOutQ(new HSLocationSetReq(data));
        }
    }

    /**
     * 更新学校的位置
     * @param data
     */
    public void updateSchoolLocation(HSLocationData data) {
        if(data != null) {
            data.setWearerId(Long.parseLong(KMWearerManager.getInstance().getWearerId(data.getImei())));
            data.setType(2);
            NettyClientManager.addOutQ(new HSLocationUpdateReq(data));
        }
    }

    /**
     * 创建家校时间
     * @param data
     */
    public void createHSTime(List<HSTimeData> data) {
        if(data != null) {
            NettyClientManager.addOutQ(new HSTimeSetReq(data));
        }
    }

    /***
     * 更新家校时间
     * @param data
     */
    public void updateHSTime(List<HSTimeData> data) {
        if(data != null) {
            NettyClientManager.addOutQ(new HSTimeUpdateReq(data));
        }
    }

    /***
     * 获取家校日志
     * @param imei
     */
    public void getHSLog(String imei) {
        NettyClientManager.addOutQ(new HSLogQueryReq(imei));
    }
    //家校提醒---------end-------------//

    //常用位置---------start-----------//

    /**
     * 查询常用位置
     * @param imei
     */
    public void getLocMark(String imei) {
        NettyClientManager.addOutQ(new LocMarkQueryReq(KMWearerManager.getInstance().getWearerId(imei), KMConstants.LocMarkType.TYPE_LOC_MARK_COMMON));
    }

    /**
     * 创建常用位置
     * @param data
     */
    public void createLocMark(LocationMark data) {
        if(data != null) {
            data.setLocationType(KMConstants.LocMarkType.TYPE_LOC_MARK_COMMON);
            NettyClientManager.addOutQ(new LocMarkSetReq(data));
        }
    }

    /***
     * 更新常用位置
     * @param data
     */
    public void updateLocMark(LocationMark data) {
        if(data != null) {
            data.setLocationType(KMConstants.LocMarkType.TYPE_LOC_MARK_COMMON);
            NettyClientManager.addOutQ(new LocMarkUpdateReq(data));
        }
    }

    /***
     * 删除常用位置
     * @param imei
     * @param id
     */
    public void delLocMark(String imei, List<Long> id) {
        NettyClientManager.addOutQ(new LocMarkDelReq(KMWearerManager.getInstance().getWearerId(imei), id));
    }
    //常用位置---------end-------------//

    //一键回家---------start-------------//
    /**
     * 查询家的位置
     * @param imei
     */
    public void getHomeMark(String imei) {
        NettyClientManager.addOutQ(new LocMarkQueryReq(imei, KMConstants.LocMarkType.TYPE_LOC_MARK_HOME));
    }

    /**
     * 创建家的位置
     * @param data
     */
    public void createHomeMark(LocationMark data) {
        if(data != null) {
            data.setLocationType(KMConstants.LocMarkType.TYPE_LOC_MARK_HOME);
            NettyClientManager.addOutQ(new LocMarkSetReq(data));
        }
    }

    /***
     * 更新家的位置
     * @param data
     */
    public void updateHomeMark(LocationMark data) {
        if(data != null) {
            data.setLocationType(KMConstants.LocMarkType.TYPE_LOC_MARK_HOME);
            NettyClientManager.addOutQ(new LocMarkUpdateReq(data));
        }
    }
    //一键回家---------end-------------//

    //WIFI修正---------start-------------//
    public void updateWiFiLocMark(String imei, double lat, double lon, List<WiFiData> wifis) {
        if(!FunUtils.isSupportWIFICorrection(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WIFI_LOC_UPDATE, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new WifiLocUpdateReq(imei, lat, lon, wifis));
    }
    //WIFI修正---------end-------------//


    public void clearData() {

    }
}
