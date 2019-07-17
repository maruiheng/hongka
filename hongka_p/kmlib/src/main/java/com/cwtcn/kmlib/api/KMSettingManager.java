package com.cwtcn.kmlib.api;

import android.content.Context;

import com.cwtcn.kmlib.data.GameData;
import com.cwtcn.kmlib.data.HabitData;
import com.cwtcn.kmlib.data.PhoneSettingData;
import com.cwtcn.kmlib.data.TrackerAlarm;
import com.cwtcn.kmlib.data.TrackerCityData;
import com.cwtcn.kmlib.data.TrackerConnectMode;
import com.cwtcn.kmlib.data.TrackerMuteData;
import com.cwtcn.kmlib.data.TrackerPowerOnOff;
import com.cwtcn.kmlib.data.TrackerWorkMode;
import com.cwtcn.kmlib.data.Wearer;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.protocol.HobbyQueryReq;
import com.cwtcn.kmlib.protocol.HobbySetReq;
import com.cwtcn.kmlib.protocol.PhoneAppListQueryReq;
import com.cwtcn.kmlib.protocol.PhoneSettingQueryReq;
import com.cwtcn.kmlib.protocol.PhoneSettingSetReq;
import com.cwtcn.kmlib.protocol.PhoneUsageDataGetReq;
import com.cwtcn.kmlib.protocol.PhoneUsageDetailDataGetReq;
import com.cwtcn.kmlib.protocol.TauntQueryReq;
import com.cwtcn.kmlib.protocol.TrackerAlarmQueryReq;
import com.cwtcn.kmlib.protocol.TrackerAlarmSetReq;
import com.cwtcn.kmlib.protocol.TrackerAutoAnswerSetReq;
import com.cwtcn.kmlib.protocol.TrackerCMDReq;
import com.cwtcn.kmlib.protocol.TrackerCityListQueryReq;
import com.cwtcn.kmlib.protocol.TrackerCityQueryReq;
import com.cwtcn.kmlib.protocol.TrackerCitySetReq;
import com.cwtcn.kmlib.protocol.TrackerConnectModeSetReq;
import com.cwtcn.kmlib.protocol.TrackerDateTimeSetReq;
import com.cwtcn.kmlib.protocol.TrackerForbidRangeQueryReq;
import com.cwtcn.kmlib.protocol.TrackerForbidRangeSetReq;
import com.cwtcn.kmlib.protocol.TrackerGameStartReq;
import com.cwtcn.kmlib.protocol.TrackerMuteRangeQueryReq;
import com.cwtcn.kmlib.protocol.TrackerMuteRangeSetReq;
import com.cwtcn.kmlib.protocol.TrackerPhoneFeeQueryReq;
import com.cwtcn.kmlib.protocol.TrackerPowerOnOffQueryReq;
import com.cwtcn.kmlib.protocol.TrackerPowerOnOffSetReq;
import com.cwtcn.kmlib.protocol.TrackerResetReq;
import com.cwtcn.kmlib.protocol.TrackerRingModeSetReq;
import com.cwtcn.kmlib.protocol.TrackerSMSLocSetReq;
import com.cwtcn.kmlib.protocol.TrackerStepGetReq;
import com.cwtcn.kmlib.protocol.TrackerStepTargetGetReq;
import com.cwtcn.kmlib.protocol.TrackerStepTargetSetReq;
import com.cwtcn.kmlib.protocol.TrackerTimeModeSetReq;
import com.cwtcn.kmlib.protocol.TrackerWhiteListSetReq;
import com.cwtcn.kmlib.protocol.TrackerWifiSetReq;
import com.cwtcn.kmlib.protocol.TrackerWishesGetReq;
import com.cwtcn.kmlib.protocol.TrackerWorkModeHVQueryReq;
import com.cwtcn.kmlib.protocol.TrackerWorkModeHVSetReq;
import com.cwtcn.kmlib.protocol.TrackerWorkModeQueryReq;
import com.cwtcn.kmlib.protocol.TrackerWorkModeSetReq;
import com.cwtcn.kmlib.protocol.PhoneAppSettingSetReq;
import com.cwtcn.kmlib.util.FunUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用设置
 * Created by Allen on 2016/12/16.
 */

public class KMSettingManager {

    public Context mContext;

    private static KMSettingManager settingManager;

    /**
     * 用于保存设备的禁用时段
     */
    public Map<String, List<TrackerMuteData>> mMrMaps = new HashMap<String, List<TrackerMuteData>>();

    private KMSettingManager() {

    }

    public static KMSettingManager getInstance() {
        if (settingManager == null) {
            settingManager = new KMSettingManager();
        }
        return settingManager;
    }

    public void init(Context context) {
        mContext = context;
    }

    /***
     * 获取所有手表的工作模式
     */
    private void getAllWorkMode() {
        if (KMWearerManager.getInstance().getWearers() != null && KMWearerManager.getInstance().getWearers().size() > 0) {
            for (Wearer wearer : KMWearerManager.getInstance().getWearers()) {
                if (wearer != null && wearer.getImei() != null) {
                    if (FunUtils.isTrackerSupportWeekRepeat(wearer.getImei())) {
                        //SocketManager.addGetTrackerWorkModeHVPkg(wearer.imei);
                    } else {
                        //SocketManager.addGetTrackerWorkModePkg(wearer.imei);
                    }
                }
            }
        }
    }
    //工作模式----------------start----------//

    /***
     * 获取工作模式
     * @param imei
     */
    public void getWorkMode(String imei) {
        if (FunUtils.getGeneration(imei) > 3) {
            NettyClientManager.addOutQ(new TrackerWorkModeHVQueryReq(imei));
        } else {
            NettyClientManager.addOutQ(new TrackerWorkModeQueryReq(imei));
        }
    }

    /**
     * 设置工作模式
     *
     * @param mode
     */
    public void setWorkMode(TrackerWorkMode mode) {
        if (mode != null) {
            if (FunUtils.getGeneration(mode.getImei()) > 3) {
                NettyClientManager.addOutQ(new TrackerWorkModeHVSetReq(mode));
            } else {
                NettyClientManager.addOutQ(new TrackerWorkModeSetReq(mode));
            }
        }
    }

    //睡眠时段
    //工作模式----------------end------------//

    //联网设置----------------start----------//

    /***
     * 联网模式设置
     * @param data
     */
    public void setConnectMode(TrackerConnectMode data) {
        if (data != null) {
            NettyClientManager.addOutQ(new TrackerConnectModeSetReq(data));
        }
    }

    /**
     * 联网模式获取
     *
     * @return
     */
    public int getConnectMode(String imei) {
        if (KMLocationManager.getInstance().mTLDMap.get(imei) != null) {
            return Integer.valueOf(KMLocationManager.getInstance().mTLDMap.get(imei).getConnectMode());
        } else {
            return 0;
        }
    }
    //联网设置----------------end----------//

    //上课禁用时段----------------Start--------//

    /**
     * 设置上课禁用
     *
     * @param imei
     * @param data
     */
    public void setClassMode(String imei, List<TrackerMuteData> data) {
        if (FunUtils.isTrackerSupportSilenceMode(imei)) {
            NettyClientManager.addOutQ(new TrackerForbidRangeSetReq(imei, data));
        } else {
            NettyClientManager.addOutQ(new TrackerMuteRangeSetReq(imei, data));
        }
    }

    /***
     * 获取上课禁用
     * @param imei
     */
    public void getClassMode(String imei) {
        if (FunUtils.isTrackerSupportSilenceMode(imei)) {
            NettyClientManager.addOutQ(new TrackerForbidRangeQueryReq(imei));
        } else {
            NettyClientManager.addOutQ(new TrackerMuteRangeQueryReq(imei));
        }
    }
    //上课禁用时段----------------end----------//

    //响铃设置----------------Start----------//

    /***
     * 设置铃声模式
     * @param imei
     * @param mode
     */
    public void setRingMode(String imei, int mode) {
        if (!FunUtils.isTrackerSupportRingModeSet(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_RING_MODE_SET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new TrackerRingModeSetReq(imei, mode));
    }

    /***
     * 获取铃声模式
     * @param imei
     * @return
     */
    public int getRingMode(String imei) {
        if (KMLocationManager.getInstance().mTLDMap.get(imei) != null) {
            return Integer.valueOf(KMLocationManager.getInstance().mTLDMap.get(imei).getRingMode());
        } else {
            return 3;
        }
    }
    //响铃设置----------------end----------//

    //计步----------------Start----------//

    /***
     * 获取计步数据
     * @param imei
     * @param dateFrom
     * @param dateTo
     */
    public void getSteps(String imei, String dateFrom, String dateTo) {
        NettyClientManager.addOutQ(new TrackerStepGetReq(KMWearerManager.getInstance().getWearerId(imei), dateFrom, dateTo));
    }

    /**
     * 获取计步目标
     *
     * @param imei
     */
    public void getStepTarget(String imei) {
        NettyClientManager.addOutQ(new TrackerStepTargetGetReq(KMWearerManager.getInstance().getWearerId(imei)));
    }

    /**
     * 设置计步目标
     *
     * @param imei
     * @param target
     */
    public void setStepTarget(String imei, int target) {
        NettyClientManager.addOutQ(new TrackerStepTargetSetReq(KMWearerManager.getInstance().getWearerId(imei), target));
    }
    //计步----------------end----------//

    //闹铃----------------start----------//
    public void setAlarm(String imei, TrackerAlarm alarm) {
        if (!FunUtils.isTrackerSupportAlarm(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_ALARM_SET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new TrackerAlarmSetReq(imei, alarm));
    }

    public void getAlarm(String imei) {
        NettyClientManager.addOutQ(new TrackerAlarmQueryReq(imei));
    }
    //闹铃----------------end----------//

    //短信上报位置----------------start----------//

    /**
     * 设置短信上报位置
     *
     * @param imei
     * @param value
     */
    public void setSMSLoc(String imei, int value) {
        NettyClientManager.addOutQ(new TrackerSMSLocSetReq(imei, String.valueOf(value)));
    }

    /**
     * 获取短信上报位置开关
     *
     * @param imei
     * @return
     */
    public int getSMSLoc(String imei) {
        if (KMLocationManager.getInstance().mTLDMap.get(imei) != null) {
            return Integer.valueOf(KMLocationManager.getInstance().mTLDMap.get(imei).getSmsLocation());
        } else {
            return 0;
        }
    }
    //短信上报位置----------------end----------//

    //定时开关机----------------start----------//

    /**
     * 设置定时开关机
     *
     * @param data
     */
    public void setPowerOnOff(TrackerPowerOnOff data) {
        if (data == null) {
            return;
        }
        if (!FunUtils.isTrackerSupportAutoTurnOnOff(data.getImei())) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_POWER_SET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new TrackerPowerOnOffSetReq(data));
    }

    /**
     * 定时开关机获取
     *
     * @param imei
     */
    public void getPowerOnOff(String imei) {
        NettyClientManager.addOutQ(new TrackerPowerOnOffQueryReq(imei));
    }
    //定时开关机----------------end----------//

    //翻腕接听----------------Start----------//

    /**
     * 设置接听模式
     *
     * @param imei
     * @param mode 0:关闭 2：开启翻腕接听
     */
    public void setAutoAnswerMode(String imei, int mode) {
        if (!FunUtils.isTrackerSupportAutoAnswer(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_AUTOANSWER_SET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new TrackerAutoAnswerSetReq(imei, mode));
    }

    /**
     * 获取接听模式
     *
     * @param imei
     * @return
     */
    public int getAutoAnswerMode(String imei) {
        if (KMLocationManager.getInstance().mTLDMap.get(imei) != null) {
            return Integer.valueOf(KMLocationManager.getInstance().mTLDMap.get(imei).getAutoType());
        } else {
            return 0;
        }
    }
    //翻腕接听----------------end----------//

    //通话过滤----------------Start----------//

    /**
     * 设置电话白名单模式
     *
     * @param imei
     * @param mode 0：允许所有人呼入 1：仅限联系人呼入
     */
    public void setWhiteListMode(String imei, int mode) {
        NettyClientManager.addOutQ(new TrackerWhiteListSetReq(imei, mode));
    }

    /**
     * 获取白名单设置模式
     *
     * @param imei
     * @return
     */
    public int getWhiteListMode(String imei) {
        if (KMLocationManager.getInstance().mTLDMap.get(imei) != null) {
            return Integer.valueOf(KMLocationManager.getInstance().mTLDMap.get(imei).getEnableFnWl());
        } else {
            return 0;
        }
    }
    //通话过滤----------------end----------//
    //时间校准----------------Start----------//

    /**
     * 时间校准
     *
     * @param imei
     * @param datetime 如201704181200
     */
    public void setDateTime(String imei, String datetime) {
        NettyClientManager.addOutQ(new TrackerDateTimeSetReq(imei, datetime));
    }

    /**
     * 时间显示格式
     *
     * @param imei
     * @param mode 0：12小时 1：24小时
     */
    public void setTimeMode(String imei, int mode) {
        if (!FunUtils.isTrackerSupportHour24(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_TIMEMODE_SET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new TrackerTimeModeSetReq());
    }

    /**
     * 获取时间格式
     *
     * @param imei
     * @return
     */
    public int getTimeMode(String imei) {
        if (KMLocationManager.getInstance().mTLDMap.get(imei) != null) {
            return Integer.valueOf(KMLocationManager.getInstance().mTLDMap.get(imei).getHourTime24());
        } else {
            return 0;
        }
    }
    //时间校准----------------end----------//

    //查询资费----------------Start----------//

    /**
     * 查询资费
     *
     * @param imei
     * @param mobile
     * @param type   0：话费 1：流量
     * @param code   自定义查询指令
     */
    public void queryPhoneFee(String imei, String mobile, int type, String code) {
        if (!FunUtils.isTrackerSupportQCharge(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_CHECK_FEE, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new TrackerPhoneFeeQueryReq(imei, mobile, type, code));
    }
    //查询资费----------------end----------//

    //WIFI设置----------------Start----------//

    /**
     * 设置手表连接WIFI
     *
     * @param imei
     * @param wifiName
     * @param wifiPassword
     */
    public void setWiFi(String imei, String wifiName, String wifiPassword) {
        if (!FunUtils.isTrackerSupportConnectWifi(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WIFI_SET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new TrackerWifiSetReq(imei, wifiName, wifiPassword));
    }
    //WIFI设置----------------end----------//
    //城市设置----------------Start----------//

    /**
     * 搜索城市列表
     *
     * @param imei
     * @param keyword
     */
    public void searchCityList(String imei, String keyword) {
        if (!FunUtils.isTrackerSupportCitySet(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_CITY_GET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new TrackerCityListQueryReq(imei, keyword));
    }

    /**
     * 设置城市
     *
     * @param imei
     * @param data
     */
    public void setCity(String imei, TrackerCityData data) {
        if (!FunUtils.isTrackerSupportCitySet(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_CITY_SET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new TrackerCitySetReq(imei, data));
    }

    /**
     * 获取城市
     *
     * @param imei
     */
    public void getCity(String imei) {
        if (!FunUtils.isTrackerSupportCitySet(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_CITY_GET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new TrackerCityQueryReq(imei));
    }
    //城市设置----------------end----------//

    //查询心愿----------------Start----------//
    public void getWishes(String imei) {
        if (!FunUtils.isTrackerSupportMagicLamp(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WISHES_GET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
        }
        NettyClientManager.addOutQ(new TrackerWishesGetReq(imei));
    }
    //查询心愿----------------end----------//
    //游戏----------------Start----------//

    /**
     * 开始游戏
     *
     * @param data
     */
    public void startGame(GameData data) {
        if (!FunUtils.isTrackerSupportGame(data.getImei())) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_GAME_START, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new TrackerGameStartReq(data));
    }
    //游戏----------------end----------//

    //恢复出厂----------------Start----------//

    /**
     * 手表恢复出厂设置
     *
     * @param imei
     */
    public void reset(String imei) {
        if (!FunUtils.isTrackerSupportReset(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_RESET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new TrackerResetReq(imei));
    }
    //恢复出厂----------------End----------//

    //脱落报警

    /***
     * 设置脱落报警开关
     * @param imei
     * @param enable
     */
    public void setTrackerOffAlarmSwitch(String imei, boolean enable) {
        if (!FunUtils.isTrackerSupportOffAlarm(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_OFF_ALARM, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        String cmd = "";
        String cmdKey = KMConstants.TrackerCMD.CMD_OFF_ALARM;
        if (enable) {
            cmd = cmdKey + "kq*" + imei + "*";
        } else {
            cmd = cmdKey + "gb*" + imei + "*";
        }
        NettyClientManager.addOutQ(new TrackerCMDReq(cmdKey, imei, cmd));
    }

    /**
     * 获取脱落开关
     *
     * @param imei
     * @return
     */
    public boolean getTrackerOffAlarmSwitch(String imei) {
        if (KMLocationManager.getInstance().mTLDMap.get(imei) != null) {
            return KMLocationManager.getInstance().mTLDMap.get(imei).getFallOffSwitch() == 1 ? true : false;
        } else {
            return false;
        }
    }

    /**
     * 设置碰撞报警开关
     *
     * @param imei
     * @param enable
     */
    public void setTrackerAccidentAlarmSwitch(String imei, boolean enable) {
        if (!FunUtils.isTrackerSupportOffAlarm(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_ACCIDENT_ALARM, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        String cmd = "";
        String cmdKey = KMConstants.TrackerCMD.CMD_ACCIDENT_ALARM;
        if (enable) {
            cmd = cmdKey + "kq*" + imei + "*";
        } else {
            cmd = cmdKey + "gb*" + imei + "*";
        }
        NettyClientManager.addOutQ(new TrackerCMDReq(cmdKey, imei, cmd));
    }

    /**
     * 设置碰撞报警开关
     *
     * @param imei
     * @param level
     */
    public void setTrackerAccidentAlarmLevel(String imei, int level) {
        if (!FunUtils.isTrackerSupportOffAlarm(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_ACCIDENT_ALARM_LEVEL, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        String cmdKey = KMConstants.TrackerCMD.CMD_ACCIDENT_ALARM_LEVEL;
        String cmdLevel = cmdKey + level + imei + "*";
        NettyClientManager.addOutQ(new TrackerCMDReq(cmdKey, imei, cmdLevel));
    }

    /**
     * 获取碰撞报警开关
     *
     * @param imei
     * @return
     */
    public boolean getTrackerAccidentAlarmSwitch(String imei) {
        if (KMLocationManager.getInstance().mTLDMap.get(imei) != null) {
            return KMLocationManager.getInstance().mTLDMap.get(imei).getCrashSwitch() == 1 ? true : false;
        } else {
            return false;
        }
    }

    /**
     * 获取碰撞报警等级
     *
     * @param imei
     * @return
     */
    public int getTrackerAccidentAlarmLevel(String imei) {
        if (KMLocationManager.getInstance().mTLDMap.get(imei) != null) {
            return KMLocationManager.getInstance().mTLDMap.get(imei).getCrashLevel();
        } else {
            return 1;
        }
    }

    /**
     * 单次定位
     */
    public void startTrackerLoc(String imei) {
        String cmd = "";
        String cmdKey = KMConstants.TrackerCMD.CMD_LOC_ONCE;
        cmd = cmdKey + imei + "*";
        NettyClientManager.addOutQ(new TrackerCMDReq(cmdKey, imei, cmd));
    }

    //X2接口----------------start----------------//

    /**
     * 获取吐槽信息（X2用）
     *
     * @param imei
     * @param count
     * @param lastOneId
     */
    public void queryTauntInfo(String imei, String count, String lastOneId) {
        if (!FunUtils.isTrackerSupportTuCao(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_TAUNT_INFO, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new TauntQueryReq("T" + imei, count, lastOneId));
    }

    /**
     * 设置X2应用使用时间,是否卸载
     *
     * @param imei
     * @param appId
     * @param packageName
     * @param useTime
     * @param uninstall
     */
    public void setPhoneAppUseTimeOrUninstall(String imei, String appId, String packageName, int useTime, int uninstall) {
        if (!FunUtils.isX2(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_APP_USETIMEORUNINSTALL, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new PhoneAppSettingSetReq(imei, appId, packageName, useTime, uninstall));
    }

    /**
     * 手机设置
     *
     * @param imei
     * @param data
     */
    public void setPhone(String imei, PhoneSettingData data) {
        if (!FunUtils.isX2(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_PHONE_SET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new PhoneSettingSetReq(data));
    }

    /**
     * 获取第三方app列表
     *
     * @param imei
     */
    public void query3rdAppList(String imei) {
        if (!FunUtils.isX2(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_PHONE_3RDAPP_LIST, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new PhoneAppListQueryReq(imei));
    }

    /**
     * 获取手机某一天应用使用情况
     *
     * @param imei
     * @param durationDate
     */
    public void queryUseDetailDataByDay(String imei, String durationDate) {
        if (!FunUtils.isX2(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_PHONE_USEAPPINFOBYDAY, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new PhoneUsageDetailDataGetReq(imei, durationDate));
    }

    /**
     * 获取手机使用情况
     *
     * @param imei
     * @param dateFrom
     * @param dateTo
     */
    public void queryPhoneUsageData(String imei, String dateFrom, String dateTo) {
        if (!FunUtils.isX2(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_PHONE_USAGEDATA, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new PhoneUsageDataGetReq(imei, dateFrom, dateTo));
    }

    /**
     * 设置好习惯
     * @param imei
     * @param data
     */
    public void setGoodHabit(String imei, HabitData data) {
//        if (!FunUtils.isX2(imei)) {
//            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_SET_GOOD_HABIT, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
//            return;
//        }
        NettyClientManager.addOutQ(new HobbySetReq(data));
    }

    /**
     * 获取好习惯
     *
     * @param imei
     */
    public void queryGoodHabit(String imei) {
//        if (!FunUtils.isX2(imei)) {
//            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_QUERY_GOOD_HABIT, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
//            return;
//        }
        NettyClientManager.addOutQ(new HobbyQueryReq(imei));
    }

    /**
     * 获取手机设置信息
     * @param imei
     */
    public void queryPhoneSet(String imei) {
        if (!FunUtils.isX2(imei)) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_QUERY_PHONE_SET, "-1", KMConstants.KMTips.ERROR_NOT_SUPPORT));
            return;
        }
        NettyClientManager.addOutQ(new PhoneSettingQueryReq(imei));
    }

    //X2接口----------------end----------------//

    //碰撞报警
    //连续定位开启
    //上传拍照
    //蓝牙升级

    public void clearData() {

    }
}
