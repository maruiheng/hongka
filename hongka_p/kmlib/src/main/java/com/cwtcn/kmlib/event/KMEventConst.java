package com.cwtcn.kmlib.event;

/**
 * 定义事件常量
 * Created by Allen on 2017/3/8.
 */

public interface KMEventConst {
    /** 登录 */
    int EVENT_LOGIN = 100;
    /** 互踢 */
    int EVENT_KOT = 101;
    /** 注册(邮箱) */
    int EVENT_REGISTER_V1 = 102;
    /** 注册(手机号) */
    int EVENT_REGISTER_V2 = 103;
    /** 重置密码 */
    int EVENT_RESET_PWD_VC = 104;
    /** 重置密码 */
    int EVENT_RESET_PWD = 105;
    /** 修改密码 */
    int EVENT_MODIFY_PWD = 106;
    /** 获取用户信息 */
    int EVENT_USER_PROFILE_GET = 107;
    /** 更新用户信息 */
    int EVENT_USER_PROFILE_UPDATE = 108;
    /** 修改手机号码 */
    int EVENT_USER_PHONE_UPDATE = 109;
    /** 修改手机号码的验证码 */
    int EVENT_USER_PHONE_UPDATE_VC = 110;
    /** 注册图片验证码 */
    int EVENT_REGISTER_IVC_GET = 111;
    /** 提交验证图片验证码 */
    int EVENT_REGISTER_IVC_SEND = 112;

    /** 手表添加 */
    int EVENT_WEARER_ADD = 200;
    /** 手表更新 */
    int EVENT_WEARER_UPDATE = 201;
    /** 手表删除 */
    int EVENT_WEARER_DEL = 202;
    /** 手表绑定查询 */
    int EVENT_WEARER_QUERY = 203;
    /** 手表头像获取 */
    int EVENT_WEARER_AVATAR_GET = 204;
    /** 手表头像上传 */
    int EVENT_WEARER_AVATAR_UPLOAD = 205;
    /** 手表获取验证码 */
    int EVENT_WEARER_VC_GET = 206;
    /** 手表获取二维码 */
    int EVENT_WEARER_QR_GET = 207;
    /** 手表信息获取 */
    int EVENT_WEARER_INFO_GET = 208;
    /** 手表绑定者查询 */
    int EVENT_WEARER_BINDERS_GET = 209;
    /** 手表管理者转换 */
    int EVENT_WEARER_ADMIN_TRANSFER = 210;
    /** 解绑手表绑定者 */
    int EVENT_WEARER_UNBIND = 211;

    /** 手表联系人获取 */
    int EVENT_WEARER_CONTACT_GET = 300;
    /** 手表联系人设置 */
    int EVENT_WEARER_CONTACT_SET = 301;
    /** 手表小伙伴获取 */
    int EVENT_WEARER_FRIEND_GET = 302;
    /** 手表小伙伴更新 */
    int EVENT_WEARER_FRIEND_UPDATE = 303;
    /** 手表小伙伴删除 */
    int EVENT_WEARER_FRIEND_DEL = 304;

    /** 发送聊天消息 */
    int EVENT_IM_SEND = 400;
    /** 正在发送聊天消息 */
    int EVENT_IM_SENDING = 401;
    /** 接收聊天消息 */
    int EVENT_IM_RECEIVER = 402;
    /** 聊天消息已读 */
    int EVENT_IM_READ = 403;


    /** 创建电子栅栏 */
    int EVENT_AREA_CREATE = 500;
    /** 更新电子栅栏 */
    int EVENT_AREA_UPDATE = 501;
    /** 获取电子栅栏 */
    int EVENT_AREA_GET = 502;
    /** 启用电子栅栏 */
    int EVENT_AREA_ENABLE = 503;
    /** 获取最新位置 */
    int EVENT_TLD_GET = 504;
    /** 获取所有绑定设备位置 */
    int EVENT_TLL_GET = 505;
    /** 历史位置获取 */
    int EVENT_LOC_HISTORY_GET = 506;
    /** 家校信息查询 */
    int EVENT_HS_INFO_GET = 507;
    /** 家校位置信息设置 */
    int EVENT_HS_LOCATION_SET = 508;
    /** 家校位置信息更新 */
    int EVENT_HS_LOCATION_UPDATE = 509;
    /** 家校位置信息删除 */
    int EVENT_HS_LOCATION_DEL = 510;
    /** 家校时间信息设置 */
    int EVENT_HS_TIME_SET = 511;
    /** 家校时间信息更新 */
    int EVENT_HS_TIME_UPDATE = 522;
    /** 家校日志 */
    int EVENT_HS_LOG_GET = 523;
    /** 常用位置设置 */
    int EVENT_LOC_MARK_SET = 524;
    /** 常用位置更新 */
    int EVENT_LOC_MARK_UPDATE = 525;
    /** 常用位置删除 */
    int EVENT_LOC_MARK_DEL = 526;
    /** 常用位置查询 */
    int EVENT_LOC_MARK_GET = 527;
    /** 家的位置设置 */
    int EVENT_HOME_MARK_SET = 528;
    /** 家的位置更新 */
    int EVENT_HOME_MARK_UPDATE = 529;
    /** 家的位置查询 */
    int EVENT_HOME_MARK_GET = 530;
    /** WIFI修正 */
    int EVENT_WIFI_LOC_UPDATE = 531;

    /** 工作模式获取 */
    int EVENT_WORK_MODE_GET = 600;
    /** 工作模式设置 */
    int EVENT_WORK_MODE_SET = 601;
    /** 工作模式（带星期设置）获取 */
    int EVENT_WORK_MODE_HV_GET = 602;
    /** 工作模式（带星期设置）设置 */
    int EVENT_WORK_MODE_HV_SET = 603;
    /** 静音时段设置 */
    int EVENT_CLASS_MODE_SET = 604;
    /** 静音时段获取 */
    int EVENT_CLASS_MODE_GET = 605;
    /** 静默时段设置 */
    int EVENT_FORBID_RANGE_SET = 606;
    /** 静默时段设置 */
    int EVENT_FORBID_RANGE_GET = 607;
    /** 联网模式设置 */
    int EVENT_CONNECT_MODE_SET = 608;
    /** 联网模式获取 */
    int EVENT_CONNECT_MODE_GET = 609;
    /** 响铃模式设置 */
    int EVENT_RING_MODE_SET = 610;
    /** 响铃模式获取 */
    int EVENT_RING_MODE_GET = 611;
    /** 计步数据获取 */
    int EVENT_STEPS_GET = 612;
    /** 计步目标获取 */
    int EVENT_STEP_TARGET_GET = 613;
    /** 计步目标设置 */
    int EVENT_STEP_TARGET_SET = 614;
    /** 闹钟设置 */
    int EVENT_ALARM_SET = 615;
    /** 闹钟获取 */
    int EVENT_ALARM_GET = 616;
    /** 短信上报设置 */
    int EVENT_SMSLOC_SET = 617;
    /** 短信上报查询 */
    int EVENT_SMSLOC_GET = 618;
    /** 定时开关机设置 */
    int EVENT_POWER_SET = 619;
    /** 定时开关机获取 */
    int EVENT_POWER_GET = 620;
    /** 翻腕接听设置 */
    int EVENT_AUTOANSWER_SET = 621;
    /** 翻腕接听获取 */
    int EVENT_AUTOANSWER_GET = 622;
    /** 白名单开关设置 */
    int EVENT_EWL_SET = 623;
    /** 白名单开关获取 */
    int EVENT_EWL_GET = 624;
    /** 时间格式获取 */
    int EVENT_TIMEMODE_GET = 625;
    /** 时间格式设置 */
    int EVENT_TIMEMODE_SET = 626;
    /** 时间设置 */
    int EVENT_DATETIME_SET = 627;
    /** 查询话费 */
    int EVENT_CHECK_FEE = 628;
    /** 话费结果 */
    int EVENT_PHONE_FEE_PUSH = 629;
    /** 手表WIFI设置 */
    int EVENT_WIFI_SET = 630;
    /** 城市设置 */
    int EVENT_CITY_SET = 631;
    /** 城市获取 */
    int EVENT_CITY_GET = 632;
    /** 城市列表获取 */
    int EVENT_CITYLIST_SEARCH = 633;
    /** 查询心愿 */
    int EVENT_WISHES_GET = 634;
    /** 开始游戏 */
    int EVENT_GAME_START = 635;
    /** 游戏状态推送 */
    int EVENT_GAME_STATUS_PUSH = 636;
    /** 恢复出厂 */
    int EVENT_RESET = 637;
    /** 脱落报警 */
    int EVENT_OFF_ALARM = 638;
    /** 碰撞报警 */
    int EVENT_ACCIDENT_ALARM = 639;
    /** 碰撞报警等级 */
    int EVENT_ACCIDENT_ALARM_LEVEL = 640;
    /** 单次定位 */
    int EVENT_LOC_ONCE = 641;
    /** 手机吐槽信息获取*/
    int EVENT_TAUNT_INFO = 642;
    /**手机应用使用时间,是否卸载*/
    int EVENT_APP_USETIMEORUNINSTALL = 643;
    /**手机应用使用时间,是否卸载 推送*/
    int EVENT_APP_USETIMEORUNINSTALLPUSH = 644;
    /**手机设置*/
    int EVENT_PHONE_SET = 645;
    /**手机设置推送*/
    int EVENT_PHONE_SET_PUSH = 646;
    /**手机第三方应用列表获取*/
    int EVENT_PHONE_3RDAPP_LIST = 647;
    /**获取手机某一天应用使用情况*/
    int EVENT_PHONE_USEAPPINFOBYDAY = 648;
    /**获取手机使用情况*/
    int EVENT_PHONE_USAGEDATA = 649;
    /**设置好习惯*/
    int EVENT_SET_GOOD_HABIT = 650;
    /**推送好习惯*/
    int EVENT_SET_GOOD_HABIT_PUSH = 651;
    /**获取好习惯*/
    int EVENT_QUERY_GOOD_HABIT = 652;
    /**获取手机设置信息*/
    int EVENT_QUERY_PHONE_SET= 653;
    /**推送手机最新消息*/
    int EVENT_NEW_INFO_PUSH= 654;
}
