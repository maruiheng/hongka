package com.hongka.hongka_flutter_p_example;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cwtcn.kmlib.api.KMChatManager;
import com.cwtcn.kmlib.api.KMConstants;
import com.cwtcn.kmlib.api.KMContactManager;
import com.cwtcn.kmlib.api.KMLocationManager;
import com.cwtcn.kmlib.api.KMManager;
import com.cwtcn.kmlib.api.KMPushMamager;
import com.cwtcn.kmlib.api.KMSettingManager;
import com.cwtcn.kmlib.api.KMUserManager;
import com.cwtcn.kmlib.api.KMWearerManager;
import com.cwtcn.kmlib.data.ContactList;
import com.cwtcn.kmlib.data.FriendData;
import com.cwtcn.kmlib.data.FriendDataList;
import com.cwtcn.kmlib.data.LocHistoryCountData;
import com.cwtcn.kmlib.data.LocHistoryData;
import com.cwtcn.kmlib.data.LoginData;
import com.cwtcn.kmlib.data.MessagePushData;
import com.cwtcn.kmlib.data.TrackerLeastData;
import com.cwtcn.kmlib.data.Wearer;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.google.gson.Gson;
import com.hongka.hongka_flutter_p.Config;
import com.hongka.hongka_flutter_p.HongkaFlutter_pPlugin;
import com.hongka.hongka_flutter_p.MyCallBack;
import com.hongka.hongka_flutter_p_example.plugin.FlutterEventHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity implements MyCallBack {
    EventChannel.EventSink eventSink;
    ConcurrentHashMap<String, MethodChannel.Result> hashtable = new ConcurrentHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        GeneratedPluginRegistrant.registerWith(this);
        HongkaFlutter_pPlugin.getInstance().setCallback(this);
        KMManager.getInstance().init(getApplicationContext());
        FlutterEventHandler.registerWith(getFlutterView());
    }

    @Override
    public void getdata(MethodCall call, MethodChannel.Result result) {
        switch (call.method) {
            case Config.KM_INIT:
                //初始化
                KMManager.getInstance().init(getApplicationContext());
                result.success("success");
                break;
            case Config.KM_LOGIN:
                //登录
                LoginData loginData = new LoginData(call.<String>argument("phone"),
                        call.<String>argument("pwd"),
                        "kmdemo", "amap", "");
                KMUserManager.getInstance().login(loginData);
                hashtable.put(Config.KM_LOGIN, result);
                break;
            case Config.KM_QUICK_LOGIN:
                //快速登录
                LoginData loginData1 = new LoginData(call.<String>argument("phone"),
                        Config.KM_CLIENT_ID, "amap", "");
                KMUserManager.getInstance().login(loginData1);
                hashtable.put(Config.KM_QUICK_LOGIN, result);
                break;
            case Config.KM_GETIMAGE_CODE:
                //获取图片验证码
                KMUserManager.getInstance().getImageVC4Register(call.<String>argument("phone"));
                hashtable.put(Config.KM_GETIMAGE_CODE, result);
                break;
            case Config.KM_COMMIT_IMGCODE:
                //提交图片验证码
                Log.e("mrh", Config.KM_COMMIT_IMGCODE);
                KMUserManager.getInstance().submitImageVC4Register(call.<String>argument("phone"), call.<String>argument("imgCode"));
                hashtable.put(Config.KM_COMMIT_IMGCODE, result);
                break;
            case Config.KM_REGISTER_MOBLE:
                //手机注册提交
                KMUserManager.getInstance().registerWithMobile(call.<String>argument("phone"), call.<String>argument("pwd"),
                        call.<String>argument("code"), Config.KM_CLIENT_ID);
                hashtable.put(Config.KM_REGISTER_MOBLE, result);
                break;
            case Config.KM_QUICK_REGISTER:
                //快速注册
                KMUserManager.getInstance().quickRegisterWithMobile(call.<String>argument("phoneNum"), Config.KM_CLIENT_ID);
                hashtable.put(Config.KM_QUICK_REGISTER, result);
                break;
            case Config.KM_BIND_WEQARER:
                //绑定手机 给手表发送验证码
                KMWearerManager.getInstance().getVC4BindWearer(call.<String>argument("imei"));
                hashtable.put(Config.KM_BIND_WEQARER, result);
                break;
            case Config.KM_ADD_WEARER:
                //添加佩戴人
                Gson gson = new Gson();
                Wearer wearer;
                String str = call.<String>argument("wearer");
                wearer = gson.fromJson(str, Wearer.class);
                KMWearerManager.getInstance().wearerAdd(wearer, call.<String>argument("validationCode"));
                hashtable.put(Config.KM_ADD_WEARER, result);
                break;
            case Config.KM_ALL_TRACKER_LDGET:
                //获取所有手表位置
                KMLocationManager.getInstance().allTrackerLDGet();
                hashtable.put(Config.KM_ALL_TRACKER_LDGET, result);
                break;
            case Config.KM_TRACKER_LDGET:
                //获取单个手表最新位置
                KMLocationManager.getInstance().trackerLDGet(call.<String>argument("imei"));
                hashtable.put(Config.KM_TRACKER_LDGET, result);
                break;
            case Config.KM_MESSAGE_PUSH:
                //故事推送
                Gson gson1 = new Gson();
                MessagePushData messagePushData;
                Log.e("mrh", call.<String>argument("message"));
                messagePushData = gson1.fromJson(call.<String>argument("message"), MessagePushData.class);
                KMPushMamager.getInstance().pushSrore(messagePushData);
                hashtable.put(Config.KM_MESSAGE_PUSH, result);
                break;
            case Config.KM_WEARER_UNBIND:
                //解绑手表
                KMWearerManager.getInstance().wearerUnbind(call.<String>argument("imei"));
                hashtable.put(Config.KM_WEARER_UNBIND, result);
                break;
            case Config.KM_QUERY_CONTACTS:
                //获取手表联系人
                KMContactManager.getInstance().queryContacts(call.<String>argument("imei"));
                hashtable.put(Config.KM_QUERY_CONTACTS, result);
                break;
            case Config.KM_SETCONTACTS:
                //保存联系人
                Gson gson2 = new Gson();
                ContactList contactList = gson2.fromJson(call.<String>argument("contactListBean"), ContactList.class);
                KMContactManager.getInstance().setContacts(contactList.getImei(), contactList.getList());
                hashtable.put(Config.KM_SETCONTACTS, result);
                break;
            case Config.KM_UPGRADE_FRIEND:
                //更新小伙伴
                Gson gson3 = new Gson();
                FriendData friendData = gson3.fromJson(call.<String>argument("friendDataBean"), FriendData.class);
                KMContactManager.getInstance().updateFriend(call.<String>argument("imei"), friendData);
                hashtable.put(Config.KM_UPGRADE_FRIEND, result);
                break;
            case Config.KM_DEL_FRIEND:
                //删除小伙伴
                Gson gson4 = new Gson();
                FriendDataList friendDatas = gson4.fromJson(call.<String>argument("friendDataBean"), FriendDataList.class);
                KMContactManager.getInstance().delFriend(friendDatas);
                hashtable.put(Config.KM_DEL_FRIEND, result);
                break;
            case Config.KM_WIFI_SET:
                //手表Wi-Fi设置
                KMSettingManager.getInstance().setWiFi(call.<String>argument("imei"),
                        call.<String>argument("wifiName"),
                        call.<String>argument("wifiPassword"));
                hashtable.put(Config.KM_WIFI_SET, result);
                break;
            case Config.KM_SNED_TEXTMSG:
                KMChatManager.getInstance().sendTextMsg(call.<String>argument("imei"),
                        call.<String>argument("txt"));
                hashtable.put(Config.KM_SNED_TEXTMSG, result);
                break;
            case Config.KM_TRACKER_HISTORY_GET:
                KMLocationManager.getInstance().trackerHistoryGet(call.<String>argument("imei"),
                        call.<String>argument("date"));
                hashtable.put(Config.KM_TRACKER_HISTORY_GET, result);
                break;
        }

//        if (call.method.equals(Config.KM_LOGIN)) {
//            //登录
//            LoginData loginData = new LoginData(call.<String>argument("phone"),
//                    call.<String>argument("pwd"),
//                    "kmdemo", "amap", "");
//            KMUserManager.getInstance().login(loginData);
////            result.success("login");
//            hashtable.put(Config.KM_LOGIN, result);
//        } else if (call.method.equals(Config.KM_QUICK_LOGIN)) {
//            //快速登录
//            LoginData loginData = new LoginData(call.<String>argument("phone"),
//                    Config.KM_CLIENT_ID, "amap", "");
//            KMUserManager.getInstance().login(loginData);
//            hashtable.put(Config.KM_QUICK_LOGIN, result);
//        } else if (call.method.equals("getPlatformVersion")) {
//            result.success("Android " + android.os.Build.VERSION.RELEASE);
//        } else if (call.method.equals(Config.KM_INIT)) {
//            Log.e("mrh", "init s");
//            KMManager.getInstance().init(getApplicationContext());
//            result.success("success");
//        } else if (call.method.equals(Config.KM_BIND_WEQARER)) {
//            //绑定手机
//            KMWearerManager.getInstance().getVC4BindWearer(call.<String>argument("imei"));
//            hashtable.put(Config.KM_BIND_WEQARER, result);
//        } else if (call.method.equals(Config.KM_ADD_WEARER)) {
//            //添加佩戴人
//            Gson gson = new Gson();
//            Wearer wearer;
//            Log.e("mrh", call.<String>argument("wearer"));
//            String str = call.<String>argument("wearer");
//            wearer = gson.fromJson(str, Wearer.class);
//            Log.e("mrh", wearer.getImei());
//            Log.e("mrh", call.<String>argument("validationCode"));
//            KMWearerManager.getInstance().wearerAdd(wearer, call.<String>argument("validationCode"));
//            hashtable.put(Config.KM_ADD_WEARER, result);
//        } else if (call.method.equals(Config.KM_GETIMAGE_CODE)) {
//            //获取图片验证码
//            KMUserManager.getInstance().getImageVC4Register(call.<String>argument("phone"));
//            hashtable.put(Config.KM_GETIMAGE_CODE, result);
//        } else if (call.method.equals(Config.KM_COMMIT_IMGCODE)) {
//            //提交图片验证码
//            Log.e("mrh", Config.KM_COMMIT_IMGCODE);
//            KMUserManager.getInstance().submitImageVC4Register(call.<String>argument("phone"), call.<String>argument("imgCode"));
//            hashtable.put(Config.KM_COMMIT_IMGCODE, result);
//        } else if (call.method.equals(Config.KM_REGISTER_MOBLE)) {
//            //手机注册提交
//            Log.e("mrh", Config.KM_REGISTER_MOBLE);
//            KMUserManager.getInstance().registerWithMobile(call.<String>argument("phone"), call.<String>argument("pwd"),
//                    call.<String>argument("code"), Config.KM_CLIENT_ID);
//            hashtable.put(Config.KM_REGISTER_MOBLE, result);
//        } else if (call.method.equals(Config.KM_QUICK_REGISTER)) {
//            //快速注册
//            Log.e("mrh", Config.KM_QUICK_REGISTER);
//            KMUserManager.getInstance().quickRegisterWithMobile(call.<String>argument("phoneNum"), Config.KM_CLIENT_ID);
//            hashtable.put(Config.KM_QUICK_REGISTER, result);
//        } else if (call.method.equals(Config.KM_ALL_TRACKER_LDGET)) {
//            //获取所有手表位置
//            Log.e("mrh", Config.KM_ALL_TRACKER_LDGET);
//            KMLocationManager.getInstance().allTrackerLDGet();
//            hashtable.put(Config.KM_ALL_TRACKER_LDGET, result);
//        } else if (call.method.equals(Config.KM_WEARER_UNBIND)) {
//            //解绑手表
//            KMWearerManager.getInstance().wearerUnbind(call.<String>argument("imei"));
//            hashtable.put(Config.KM_WEARER_UNBIND, result);
//        } else if (call.method.equals(Config.KM_TRACKER_LDGET)) {
//            //获取单个手表最新位置
//            Log.e("mrh", Config.KM_TRACKER_LDGET);
//            KMLocationManager.getInstance().trackerLDGet(call.<String>argument("imei"));
//            hashtable.put(Config.KM_TRACKER_LDGET, result);
//        } else if (call.method.equals(Config.KM_MESSAGE_PUSH)) {
//            //消息推送
//            Gson gson = new Gson();
//            MessagePushData messagePushData;
//            Log.e("mrh", call.<String>argument("message"));
//            String str = call.<String>argument("message");
//            messagePushData = gson.fromJson(str, MessagePushData.class);
//            KMPushMamager.getInstance().pushSrore(messagePushData);
//            hashtable.put(Config.KM_MESSAGE_PUSH, result);
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(KMEvent mEvent) {
        if (!KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
//            eventSink.error(KMConstants.CODE_OK, mEvent.getEventMsg(), "");
        } else {
//            eventSink.success("ImageVC4Register  get img success");
        }

        Log.e("mrh", mEvent.getEventId() + "  code");

        if (mEvent.getEventId() == KMEventConst.EVENT_WEARER_VC_GET) {
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                hashtable.get(Config.KM_BIND_WEQARER).success(mEvent.getEventMsg());
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_BIND_WEQARER).success(mEvent.getEventMsg());
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
            //手动添加佩戴人
            Log.e("mrh ", "BindWearer  " + mEvent.getEventMsg().toString());
        } else if (mEvent.getEventId() == KMEventConst.EVENT_REGISTER_IVC_GET) {
            //获取图片验证码
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                getbitmapByte(mEvent);
            } else {
                Log.e("mrh ", "sss " + mEvent.getEventMsg());
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_LOGIN == mEvent.getEventId()) {
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                Log.e("mrh ", "Login success");
                if (hashtable.get(Config.KM_LOGIN) != null) {
                    hashtable.get(Config.KM_LOGIN).success("success");
                } else {
                    hashtable.get(Config.KM_QUICK_LOGIN).success("success");
                }
                Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_LOGIN).success(mEvent.getEventMsg() + "");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_REGISTER_IVC_SEND == mEvent.getEventId()) {
            //提交图片验证码回调
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                hashtable.get(Config.KM_COMMIT_IMGCODE).success("success");
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_COMMIT_IMGCODE).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_REGISTER_V2 == mEvent.getEventId()) {
            //注册提交回调
            Log.e("mrh", "注册提交回调");
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                hashtable.get(Config.KM_REGISTER_MOBLE).success("success");
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_REGISTER_MOBLE).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_REGISTER_V3 == mEvent.getEventId()) {
            //快速注册
            Log.e("mrh", "快速注册");
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                hashtable.get(Config.KM_QUICK_REGISTER).success("success");
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_QUICK_REGISTER).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_WEARER_ADD == mEvent.getEventId()) {
            //添加佩戴人
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                hashtable.get(Config.KM_ADD_WEARER).success("success");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_ADD_WEARER).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_TLL_GET == mEvent.getEventId()) {
            //获取所有手表位置
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
//                hashtable.get(Config.KM_ALL_TRACKER_LDGET).success("success");
                List<LocHistoryData> list = (List<LocHistoryData>) mEvent.getEventDataList();
                Log.e("mrh", list.get(0).toString());
                hashtable.get(Config.KM_ALL_TRACKER_LDGET).success(new Gson().toJson(list.get(0)));
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_ALL_TRACKER_LDGET).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_TLD_GET == mEvent.getEventId()) {
            //获取手表最新位置
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                TrackerLeastData data = (TrackerLeastData) mEvent.getEventData();
                Log.e("mrh", data.toString());
                Map<String, Double> l = new HashMap<>();
                l.put("lon", data.getLct().o);
                l.put("lat", data.getLct().u);
                hashtable.get(Config.KM_TRACKER_LDGET).success(l);
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_TRACKER_LDGET).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_WEARER_UNBIND == mEvent.getEventId()) {
            //解绑手表
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                hashtable.get(Config.KM_WEARER_UNBIND).success("success");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_WEARER_UNBIND).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_MESSAGE_PUSH == mEvent.getEventId()) {
            //消息推送
            Log.e("mrh", "消息推送返回 " + mEvent.getEventStatus());
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                hashtable.get(Config.KM_MESSAGE_PUSH).success("success");
                Toast.makeText(this, "推送成功", Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_MESSAGE_PUSH).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_WEARER_CONTACT_GET == mEvent.getEventId()) {
            //获取手表联系人
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                ContactList contactList = (ContactList) mEvent.getEventData();
                hashtable.get(Config.KM_QUERY_CONTACTS).success(new Gson().toJson(contactList));
                Log.e("mrh", contactList.toString());
                Toast.makeText(this, "获取手表联系人", Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_QUERY_CONTACTS).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_WEARER_CONTACT_SET == mEvent.getEventId()) {
            //保存手表联系人
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                hashtable.get(Config.KM_SETCONTACTS).success("success");
                Toast.makeText(this, "保存手表联系人", Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_SETCONTACTS).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_WEARER_FRIEND_UPDATE == mEvent.getEventId()) {
            //更新小伙伴
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                hashtable.get(Config.KM_UPGRADE_FRIEND).success("success");
                Toast.makeText(this, "更新小伙伴", Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_UPGRADE_FRIEND).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_WEARER_FRIEND_DEL == mEvent.getEventId()) {
            //删除小伙伴
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                hashtable.get(Config.KM_DEL_FRIEND).success("success");
                Toast.makeText(this, "删除小伙伴", Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_DEL_FRIEND).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (KMEventConst.EVENT_WIFI_SET == mEvent.getEventId()) {
            //手表Wi-Fi设置
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                hashtable.get(Config.KM_WIFI_SET).success("success");
                Toast.makeText(this, "手表设置Wi-Fi", Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_WIFI_SET).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        }else if (KMEventConst.EVENT_IM_SEND == mEvent.getEventId()) {
            //发送文字
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {
                hashtable.get(Config.KM_SNED_TEXTMSG).success("success");
                Toast.makeText(this, "发送文字", Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_SNED_TEXTMSG).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        }else if (KMEventConst.EVENT_LOC_HISTORY_GET == mEvent.getEventId()) {
            //获取手表历史位置
            if (KMConstants.CODE_OK.equals(mEvent.getEventStatus())) {

                LocHistoryCountData data = (LocHistoryCountData) mEvent.getEventData();
                Log.e("mrh", data.toString());

                hashtable.get(Config.KM_TRACKER_HISTORY_GET).success(new Gson().toJson(data));
                Toast.makeText(this, "获取手表历史位置", Toast.LENGTH_SHORT).show();
            } else {
                hashtable.get(Config.KM_SNED_TEXTMSG).success("failed");
                Toast.makeText(this, mEvent.getEventMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setEventSink(EventChannel.EventSink eventSink) {
        this.eventSink = eventSink;
    }

    private void getbitmapByte(KMEvent mEvent) {
        new Thread(() -> {
            Bitmap bitmap = (Bitmap) mEvent.getEventData();
            ByteBuffer allocate = ByteBuffer.allocate(bitmap.getByteCount());
            bitmap.copyPixelsToBuffer(allocate);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();

//                    eventSink.success(byteArray);
            hashtable.get(Config.KM_GETIMAGE_CODE).success(byteArray);
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        hashtable.clear();
    }

}
