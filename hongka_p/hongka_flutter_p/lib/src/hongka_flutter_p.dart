import 'dart:async';
import 'dart:convert';
import 'dart:typed_data';

import 'package:flutter/services.dart';
import 'package:hongka_flutter_p/bean/wearer_bean.dart';
import 'package:hongka_flutter_p/bean/LocBean.dart';
import 'package:hongka_flutter_p/src/config.dart';

import 'package:hongka_flutter_p/bean/message_bean.dart';

class HongkaFlutter_p {
  static const MethodChannel _channel = const MethodChannel('hongka_flutter_p');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  ///初始化
  static Future init() async {
    print('mrh  init');
    final String resulu1 = await _channel.invokeMethod(KM_INIT);
    return resulu1;
  }

  ///登录
  static Future<String> Login(String phone, String pwd) async {
    print('mrh  login');
    final String result = await _channel
        .invokeMethod(KM_LOGIN, {"phone": "$phone", "pwd": "$pwd"});
    return result;
  }

  ///快速登录
  static Future<String> quickLogin(String phone) async {
    final String result =
    await _channel.invokeMethod(KM_QUICK_LOGIN, {"phone": "$phone"});
    return result;
  }


  ///注册 获取图片验证码
  static Future<Uint8List> ImageVC4Register(String phone) async {
    print('mrh  ImageVC4Register');
    final Uint8List imagedata =
        await _channel.invokeMethod(KM_GETIMAGE_CODE, {"phone": "$phone"});
    return imagedata;
  }
  ///注册提交图片验证码
  static Future<String> CommitImgCode(String phone,String imgCode) async {
    final String result =
        await _channel.invokeMethod(KM_COMMIT_IMGCODE, {"phone": "$phone","imgCode":"$imgCode"});
    return result;
  }

  ///手机注册
  static Future<String> RegisterByPhone(String phone,String pwd,String code) async {
    final String result =
    await _channel.invokeMethod(KM_REGISTER_MOBLE, {"phone": "$phone","pwd":"$pwd","code":"$code"});
    return result;
  }

  ///快速注册注册
  static Future<String> quickRegisterByPhone(String phone) async {
    final String result =
    await _channel.invokeMethod(KM_QUICK_REGISTER, {"phoneNum": "$phone"});
    return result;
  }

  ///手表绑定
  static Future<String> BindWearer(String imei) async {
    print("mrh getVC4BindWearer");
    final String result =
        await _channel.invokeMethod(KM_BIND_WEQARER, {"imei": "$imei"});
    return result;
  }

  ///手表添加佩戴人
  static Future<String> wearerAdd(WearerBean bean, String validationCode) async {
    print("mrh getVC4BindWearer");
    final String result =
    await _channel.invokeMethod(KM_ADD_WEARER, {"wearer":bean.toJson().toString(),"validationCode": "$validationCode"});
    return result;
  }

  ///手表解绑
  static Future<String> UnBindWearer(String imei) async {
    print("mrh wearerDel");
    final String result = await _channel.invokeMethod(KM_WEARER_UNBIND, {"imei":"$imei"});
    return result;
  }

  ///获取所有手表位置
  static Future<LocBean> allTrackerLDGet() async{
    print("mrh allTrackerLDGet");
     var result = await _channel.invokeMethod(KM_ALL_TRACKER_LDGET, {});
    LocBean locBean =  LocBean.fromJson(json.decode(result));
    return locBean;
  }

  ///获取单个手表最新位置
  static Future<Map> trackerLDGet(String imei) async{
    print("mrh trackerLDGet");
    Map result = await _channel.invokeMethod(KM_TRACKER_LDGET, {"imei":"$imei"});
    print(result);
//    TrackerLeastBean trackerLeastBean = TrackerLeastBean.fromJson(result);
    return result;
  }

  ///向手表推送消息
  static Future<String> pushMessage(MessageBean bean) async{
    print("mrh pushMessage");
    final String result =
    await _channel.invokeMethod(KM_MESSAGE_PUSH, {"message":bean.toJson().toString()});
    return result;
  }
}
