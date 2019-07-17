package com.cwtcn.kmlib.util;

import android.text.TextUtils;

import com.cwtcn.kmlib.api.KMManager;
import com.cwtcn.kmlib.api.KMWearerManager;

/***
 * 手表的功能配置
 * @author Allen
 *
 */
public class FunUtils {

	/**
	 * 是否支持语音聊天功能
	 * @param imei
	 * @return
     */
	public static boolean isTrackerSupportVoiceMsg(String imei) {
		if(getGeneration(imei) >= 3) {
			return true;
		}
		return false;
	}

	/**
	 * 是否支持游戏功能
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportGame(String imei) {
		if (isX2(imei) || isT1506(imei) || isV118(imei) || isB108(imei)) {
			return false;
		}
		if (getGeneration(imei) >= 3) {
			return true;
		}
		return false;
	}

	/**
	 * 是否支持位置到达，家校提醒(3代表开始支持WIFI定位)
	 * @param imei
	 * @return
     */
	public static boolean isTrackerSupportPositionTo(String imei) {
		if(getGeneration(imei) >= 3) {
			return true;
		}
		return false;
	}

	
	/***
	 * 手表是否支持铃声设置
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportRingModeSet(String imei) {
		if (isT1506(imei) || isV118(imei) || isX2(imei)) {
			return false;
		}
		if(getGeneration(imei) > 3) {
			return true;
		}
		return false;
	}
	
	/***
	 * 是否支持短信上报
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportSMSLoc(String imei) {
		if(getGeneration(imei) > 3) {
			return true;
		}
		return false;
	}
	
	/***
	 * 手表是否支持自动接听
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportAutoAnswer(String imei) {
		if (isT1506(imei) || isV118(imei) || isX2(imei) || isB108(imei)) {
			return false;
		}
		if(getGeneration(imei) > 3) {
			return true;
		}
		return false;
	}
	
	/***
	 * 手表是否支持恢复出厂设置
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportReset(String imei) {
		if (isT1506(imei) || isV118(imei) || isX2(imei)) {
			return true;
		}
		if(getGeneration(imei) > 3) {
			return true;
		}
		return false;
	}

	/***
	 * 手表是否允许所有人呼叫 KT04_abr_01_hv01sms_sv71_cn_20151026
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerAllowAllCall(String imei) {
		if(getGeneration(imei) > 3) {
			return true;
		}
		return false;
	}
	
	/***
	 * 手表是否有蓝牙小伙伴功能
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerHasBleFriends(String imei) {
		if(isT1501(imei) || isKT04(imei) || isKT04SE(imei)) {
			return true;
		}
		return false;
	}

	/**
	 * 手表是否支持快捷模板(X2支持)
	 * @param imei
	 * @return
     */
	public static boolean isTrackerSupportFastTemplate(String imei){
		if(isX2(imei)) {
			return true;
		}
		return false;
	}

	/**
	 * 手表是否支持设置随意拨打时长(X2支持)
	 * @param imei
	 * @return
     */
	public static boolean isTrackerSupportCallTime(String imei){
		if(isX2(imei)) {
			return true;
		}
		return false;
	}
	
	/***
	 * 手表是否支持静默模式
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportSilenceMode(String imei) {
		if(getGeneration(imei) > 3) {
			return true;
		}
		return false;
	}
	
	/***
	 * 手表是否支持连续定位功能的星期重复选择
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportWeekRepeat(String imei) {
		if(getGeneration(imei) > 3) {
			return true;
		}
		return false;
	}	
	
	/***
	 * 手表是否支持蓝牙升级
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerCanBleUpdate(String imei) {
		if(isT1601(imei)) {
			return true;
		}
		return false;
	}
	
	/***
	 * 手表是否支持计步查询的指令
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportUPJB(String imei) {
		if(getGeneration(imei) > 3) {
			return true;
		}
		return false;
	}
	
	/***
	 * 手表是否支持查询话费
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportQCharge(String imei) {
		if(KMManager.IS_FOREIGN_VERSION) {
			return false;
		}
		if(getGeneration(imei) > 3) {
			return true;
		}
		return false;
	}

	/**
	 * 手表是否支持通用表情
	 * @param imei
	 * @return
     */
	public static boolean isTrackerSupportCommonFace(String imei){
		if (isT1506(imei) || isX2(imei)){
			return true;
		}
		return false;
	}
	
	/***
	 * 是否支持保险
	 * @param imei
	 * @return
	 */
	public static boolean isSupportInsurance(String imei) {
		return false;
	}
	
	/***
	 * 是否第三版的关系头像（此处为绑定关系）
	 * @param imei
	 * @return
	 */
	public static boolean isSupport3rdRelation(String imei) {
		if(isT1501(imei)) {
			return true;
		}
		if(getGeneration(imei) > 3) {
			return true;
		}
		return false;
	}
	
	/***
	 * 是否支持回家导航
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportHomeNavigation(String imei) {
		if (isT1506(imei) || isX2(imei) || isKT04(imei) || isV118(imei)) {
			return true;
		}
		return false;
	}
	
	/***
	 * 是否支持拍照定位
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportPhotoLoc(String imei) {
		if (isT1601(imei) || isT1506(imei)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否支持城市设置
	 * @param imei
	 * @return
     */
	public static boolean isTrackerSupportCitySet(String imei){
		if (isT1601(imei) || isT1506(imei) || isV118(imei) || isB108(imei)) {
			return true;
		}
		return false;
	}
	
	/***
	 * 手表是否24小时设置
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportHour24(String imei) {
		if(isKT04(imei) || isKT04SE(imei) || isT1601(imei) || isB108(imei)) {
			return true;
		}
		return false;
	}
	
	/***
	 * 手表是否支持闹铃设置
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportAlarm(String imei) {
		if(isX2(imei)) {
			return false;
		}
		if(getGeneration(imei) > 3) {
			return true;
		}
		return false;
	}
	
	/***
	 * 手表是否支持联系人模式 20160222
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportCTTS(String imei) {
		if(isT1506(imei) || isX2(imei) || isT1601(imei)) {
			return true;
		}
		return false;
	}

	/**
	 * 手表是否支持讲故事
	 */
	public static boolean isTrackerSupportStory(String imei) {
		if (isT1601(imei)|| isT1506(imei) || isV118(imei)) {
			return true;
		}
		return false;
	}

	/***
	 * 手表是否支持阿巴町神灯(许愿) 20160223
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportMagicLamp(String imei) {
		if (isT1506(imei) || isX2(imei) || isV118(imei)) {
			return true;
		}
		return false;
	}
	
	/***
	 * 手表是否支持自动开关机(使用睡眠时段设置接口)
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportAutoPower(String imei) {
		if(isT1601(imei)) {
			return true;
		}
		return false;
	}

	/***
	 * 手表是否支持自动开关机
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportAutoTurnOnOff(String imei) {
		if(isX2(imei) || isV118(imei)) {
			return true;
		}
		return false;
	}

	
	/***
	 * 手表是否支持wifi连接
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportConnectWifi(String imei) {
		if(isT1601(imei) || isT1506(imei) || isV118(imei) || isB108(imei)) {
			return true;
		}
		return false;
	}

	/**
	 * 手表是否支持走路禁用
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportWalkToDisable(String imei){
		if(isX2(imei)) {
			return true;
		}
		return false;
	}

	/**
	 * 手表是否支持姿势提醒(X2)
	 * @param imei
	 * @return
     */
	public static boolean isTrackerSupportPostRemind(String imei) {
		if(isX2(imei)) {
			return true;
		}
		return false;
	}
	
	/***
	 * 手表是否开启连续定位，此判断用户开启电子栅栏和家校提醒时的设置
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerOpenFrequentlyPosition(String imei) {
		if(isT1506(imei)) {
			return false;
		}
		return true;
	}
	
	/***
	 * 电子栅栏，常用位置，家校提醒的报警使用服务器的推送
	 * @param imei
	 * @return
	 */
	public static boolean isAlarmByServer(String imei) {
		if(isT1506(imei) || isX2(imei) || isV118(imei)) {
			return true;
		}
		return false;
	}
	
	/***
	 * 是否支持WIFI修正
	 * @param imei
	 * @return
	 */
	public static boolean isSupportWIFICorrection(String imei) {
		if(isT1506(imei) || isX2(imei)) {
			return false;
		}
		return true;
	}
	
	/***
	 * 手表是否支持表情发送（20160826）
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportExpress(String imei) {
		if(isT1601(imei) || isB108(imei)) {
			return false;
		}
		if(getGeneration(imei) <= 3) {
			return false;
		}
		return true;
	}
	/***
	 * 手表聊天功能是否支持多类型（表情，图片，定位）发送（20161027）
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportChatMultitype(String imei) {
		if(isT1506(imei) || isX2(imei) || isV118(imei)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否支持碰撞提醒
	 * @param imei
	 * @return
     */
	public static boolean isTrackerSupportCrashAlarm(String imei) {
		if (isT1506(imei) || isX2(imei) || isV118(imei) || isB108(imei)) {
			return false;
		}
		return true;
	}

	/***
	 * 是否支持吐槽
	 * @param imei
	 * @return
     */
	public static boolean isTrackerSupportTuCao(String imei) {
		if(isX2(imei)) {
			return true;
		}
		return false;
	}

	/***
	 * 是否支持主题设置
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportThemeSet(String imei) {
		if(isT1601(imei)) {
			return false;
		}
		return false;
	}

	/***
	 * 电话监听时分两种情况：功能机等通话结束后回复指令，智能机指令发过去就回复
	 * @param imei
	 * @return
     */
	public static boolean isTrackerMonitorRespQuick(String imei) {
		if(isT1506(imei) || isX2(imei)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否支持脱落提醒
	 *
	 * @param imei
	 * @return
	 */
	public static boolean isTrackerSupportOffAlarm(String imei) {
		if (isX2(imei) || isB108(imei) || isV118(imei)) {
			return false;
		}
		return true;
	}
	
	/***
	 * 返回手表的软件版本号
	 * @param imei
	 * @return
	 */
	public static int getSVerCode(String imei) {
		if(TextUtils.isEmpty(imei)) {
			return 0;
		}
		String sVer = KMWearerManager.getInstance().mProductSVerMap.get(imei);
		if(sVer == null) {
			return 0;
		}
		String tmpStr = sVer.substring(sVer.lastIndexOf("sv"));
		String[] tmps = tmpStr.split("_");
		if(tmps.length > 0) {
			//如果"sVer":"KT04_abr_01_hv01sms_sv63_cn_20151026"
			int ver = Integer.valueOf(tmps[0].substring(2));
			return ver;
		}
		return 0;
	}

	/***
	 * KT01L,KT01N
	 * @param imei
	 * @return
	 */
	public static boolean isKT01LN(String imei) {
		if(TextUtils.isEmpty(imei)) {
			return false;
		}
		if(KMWearerManager.getInstance().mProductIDMap.size() > 0
				&& KMWearerManager.getInstance().mProductIDMap.get(imei) != null
				&& ((KMWearerManager.getInstance().mProductIDMap.get(imei).toLowerCase()).equals(("kt01l").toLowerCase())
				|| (KMWearerManager.getInstance().mProductIDMap.get(imei).toLowerCase()).equals(("kt01n").toLowerCase()))) {
			return true;
		}
		return false;
	}

	/***
	 * KT01S
	 * @param imei
	 * @return
	 */
	public static boolean isKT01S(String imei) {
		if(TextUtils.isEmpty(imei)) {
			return false;
		}
		if(KMWearerManager.getInstance().mProductIDMap.size() > 0
				&& KMWearerManager.getInstance().mProductIDMap.get(imei) != null
				&& (KMWearerManager.getInstance().mProductIDMap.get(imei).toLowerCase()).equals(("kt01s").toLowerCase())) {
			return true;
		}
		return false;
	}

	/***
	 * KT01W
	 * @param imei
	 * @return
	 */
	public static boolean isKT01W(String imei) {
		if(TextUtils.isEmpty(imei)) {
			return false;
		}
		if(KMWearerManager.getInstance().mProductIDMap.size() > 0
				&& KMWearerManager.getInstance().mProductIDMap.get(imei) != null
				&& (KMWearerManager.getInstance().mProductIDMap.get(imei).toLowerCase()).equals(("kt01w").toLowerCase())) {
			return true;
		}
		return false;
	}

	/***
	 * KT04
	 * @param imei
	 * @return
	 */
	public static boolean isKT04(String imei) {
		if(TextUtils.isEmpty(imei)) {
			return false;
		}
		if(KMWearerManager.getInstance().mProductIDMap.size() > 0
				&& KMWearerManager.getInstance().mProductIDMap.get(imei) != null
				&& (KMWearerManager.getInstance().mProductIDMap.get(imei).toLowerCase()).equals(("kt04").toLowerCase())) {
			return true;
		}
		return false;
	}

	/***
	 * T1501做为KT01W的升级版，亲情号码增加到60个
	 * @param imei
	 * @return
	 */
	public static boolean isT1501(String imei) {
		if(TextUtils.isEmpty(imei)) {
			return false;
		}
		if(KMWearerManager.getInstance().mProductIDMap.size() > 0
				&& KMWearerManager.getInstance().mProductIDMap.get(imei) != null
				&& (KMWearerManager.getInstance().mProductIDMap.get(imei).toLowerCase()).equals(("T1501").toLowerCase())) {
			return true;
		}
		return false;
	}

	/***
	 * 20160110:新机型T1506(Android系统智能机)
	 * @param imei
	 * @return
	 */
	public static boolean isT1506(String imei) {
		if(TextUtils.isEmpty(imei)) {
			return false;
		}

		if(KMWearerManager.getInstance().mProductIDMap.size() > 0
				&& KMWearerManager.getInstance().mProductIDMap.get(imei) != null
				&& (KMWearerManager.getInstance().mProductIDMap.get(imei).toLowerCase()).equals(("T1506").toLowerCase())) {
			return true;
		}
		
		return false;		
	}
	
	/***
	 * 20160629:新机型KT04SE(同KT04但不支持地磁)
	 * @param imei
	 * @return
	 */
	public static boolean isKT04SE(String imei) {
		if(TextUtils.isEmpty(imei)) {
			return false;
		}

		if(KMWearerManager.getInstance().mProductIDMap.size() > 0
				&& KMWearerManager.getInstance().mProductIDMap.get(imei) != null
				&& (KMWearerManager.getInstance().mProductIDMap.get(imei).toLowerCase()).equals(("KT04SE").toLowerCase())) {
			return true;
		}
		return false;		
	}
	
	/***
	 * 20160629:新机型T1601(圆表)
	 * @param imei
	 * @return
	 */
	public static boolean isT1601(String imei) {
		if(TextUtils.isEmpty(imei)) {
			return false;
		}

		if(KMWearerManager.getInstance().mProductIDMap.size() > 0
				&& KMWearerManager.getInstance().mProductIDMap.get(imei) != null
				&& (KMWearerManager.getInstance().mProductIDMap.get(imei).toLowerCase()).equals(("T1601").toLowerCase())) {
			return true;
		}
		return false;		
	}

	/**
	 * 20161011 新增机型X2,20161104变更为x926
	 * @param imei
	 * @return
     */
	public static boolean isX2(String imei) {
		if(TextUtils.isEmpty(imei)) {
			return false;
		}

		if(KMWearerManager.getInstance().mProductIDMap.size() > 0
				&& KMWearerManager.getInstance().mProductIDMap.get(imei) != null
				&& (KMWearerManager.getInstance().mProductIDMap.get(imei).toLowerCase()).equals(("X926").toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * 20161226 新增机型小胖V118
	 * @param imei
	 * @return
	 */
	public static boolean isV118(String imei) {
		if(TextUtils.isEmpty(imei)) {
			return false;
		}

		if(KMWearerManager.getInstance().mProductIDMap.size() > 0
				&& KMWearerManager.getInstance().mProductIDMap.get(imei) != null
				&& (KMWearerManager.getInstance().mProductIDMap.get(imei).toLowerCase()).equals(("v118").toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * 20170106 新增机型B200
	 * 20170315 机型名称变更为B108
	 * @param imei
	 * @return
	 */
	public static boolean isB108(String imei) {
		if(TextUtils.isEmpty(imei)) {
			return false;
		}

		if(KMWearerManager.getInstance().mProductIDMap.size() > 0
				&& KMWearerManager.getInstance().mProductIDMap.get(imei) != null
				&& ((KMWearerManager.getInstance().mProductIDMap.get(imei).toLowerCase()).equals(("b108").toLowerCase()))) {
			return true;
		}
		return false;
	}

	/***
	 * 手表的型号
	 * @param imei
	 * @return
     */
	public static int getGeneration(String imei) {
		if (isKT01LN(imei)) {
			return 1;
		}
		if (isKT01S(imei)) {
			return 2;
		}
		if (isKT01W(imei) || isT1501(imei)) {
			return 3;
		}
		if (isKT04(imei) || isKT04SE(imei)) {
			return 4;
		}
		if (isT1601(imei)) {
			return 6;
		}
		if(isB108(imei)) {
			return 6;
		}
		if (isT1506(imei)) {
			return 7;
		}
		if (isV118(imei)) {
			return 7;
		}
		if (isX2(imei)) {
			return 8;
		}
		return 0;
	}
}
