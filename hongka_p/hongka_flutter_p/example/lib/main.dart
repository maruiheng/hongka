import 'dart:typed_data';

import 'package:amap_base/amap_base.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:hongka_flutter_p/watch_plugin.dart';
import 'package:hongka_flutter_p_example/text_bean.dart';
import 'package:hongka_flutter_p_example/ui/login_page.dart';

import 'map/draw_on_map/draw_point.screen.dart';

void main() => runApp(MyApp1());

class MyApp1 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    init();
    return new MaterialApp(
      title: 'Welcome to Flutter',
      home: LoginPage(),
//      home: MyApp(),
    );
  }

  Future init() async {
    String _result = await HongkaFlutter_p.init();
  }
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String _result = "";

  String _message = "aaa";
  static const EventChannel eventChannel =
      const EventChannel('com.hongka.hongka_flutter_p_example/EventHandler');

  Uint8List _imageData;

  String imgCode = "";
  String code = "";
  String addCode = "";

  WearerBean wearerBean;

  MessageBean messageBean;

  LocBean _locBean;
  LatLng latLng ;
  @override
  void initState() {
    super.initState();
    initPlatformState();
    initEventChannel();
    wearerBean = WearerBean.fromJson(wearerData);
    messageBean = MessageBean.fromJson(messageData);
  }

  Future<void> initEventChannel() {
    eventChannel.receiveBroadcastStream().listen(_onEvent, onError: _onError);
  }

  void _onEvent(Object event) {
    if (event is String) {
      setState(() {
        _message = event;
      });
      return;
    }
    Uint8List imageData;
    setState(() {
      try {
        final result = event;
        imageData = result;
      } on PlatformException {
        imageData = null;
      }
      setState(() {
        _imageData = imageData;
      });
//
    });
  }

  void _onError(Object error) {
    setState(() {
      PlatformException exception = error;
      _message = exception?.message ?? 'Battery status: unknown.';
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await HongkaFlutter_p.platformVersion;
      _result = await HongkaFlutter_p.init();
      setState(() {});
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('手表插件Demo'),
        ),
        body: ListView(
          padding: EdgeInsets.symmetric(horizontal: 22.0),
          children: <Widget>[
            _imageWidget(),
            Container(
              child: Text("$_message", style: TextStyle(color: Colors.red)),
            ),
            Center(
              child: Text('Running on: $_platformVersion\n' '$_result'),
            ),
            InkWell(
              onTap: () async {
                _result = await HongkaFlutter_p.init();
                setState(() {});
              },
              child: new Text("初始化"),
            ),
            Container(
              margin: EdgeInsets.only(top: 20),
              child: InkWell(
                  onTap: () async {
                    _imageData =
                        await HongkaFlutter_p.ImageVC4Register("17610729625");
                    setState(() {});
                  },
                  child: Text("获取图片验证码")),
            ),
            ImgCodeInput(),
            Container(
              margin: EdgeInsets.only(top: 20),
              child: InkWell(
                  onTap: () async {
                    _result = await HongkaFlutter_p.CommitImgCode(
                        "17610729625", imgCode);
                    setState(() {});
                  },
                  child: Text("提交图片验证码")),
            ),
            codeInput(),
            Container(
              margin: EdgeInsets.only(top: 20),
              child: InkWell(
                  onTap: () async {
                    _result = await HongkaFlutter_p.RegisterByPhone(
                        "17610729625", "123456mrh", code);
                    setState(() {});
                  },
                  child: Text("注册")),
            ),
            Container(
              margin: EdgeInsets.only(top: 20),
              child: InkWell(
                  onTap: () async {
                    _result =
                        await HongkaFlutter_p.Login("17610729625", "123456mrh");
                    setState(() {});
                  },
                  child: Text("登录")),
            ),
            Container(
              margin: EdgeInsets.only(top: 20),
              child: InkWell(
                  onTap: () async {
                    _result =
                        await HongkaFlutter_p.BindWearer("865852032302588");
                    setState(() {});
                  },
                  child: Text("绑定手机")),
            ),
            addInput(),
            Container(
              margin: EdgeInsets.only(top: 20),
              child: InkWell(
                  onTap: () async {
                    _result =
                        await HongkaFlutter_p.wearerAdd(wearerBean, "$addCode");
                    setState(() {});
                  },
                  child: Text("添加佩戴人")),
            ),
            Container(
              margin: EdgeInsets.only(top: 20),
              child: InkWell(
                  onTap: () async {
                    _locBean = await HongkaFlutter_p.allTrackerLDGet();
                    latLng = new LatLng(_locBean.lat, _locBean.lon);
                  },
                  child: Text("获取手表位置")),
            ),
            Container(
              margin: EdgeInsets.only(top: 20),
              child: InkWell(
                  onTap: () {
                    Navigator.push(context,
                        new MaterialPageRoute(builder: (context) {
                      return DrawPointScreen(
                        latLng: latLng,
                      );
                    }));
                  },
                  child: Text("地图")),
            ),
            Container(
              margin: EdgeInsets.only(top: 20),
              child: InkWell(
                  onTap: () async {
                    print("消息推送" + messageBean.imei);
                    _result = await HongkaFlutter_p.pushMessage(messageBean);
                    setState(() {});
                  },
                  child: Text("发送消息")),
            ),
            Container(
              margin: EdgeInsets.only(top: 20),
              child: InkWell(
                  onTap: () async {
                    Map data =
                        await HongkaFlutter_p.trackerLDGet("865852032302588");
                    print("aaaaaaa   ${data["lon"]}    ${data["lat"]} ");
                    latLng = new LatLng(data["lat"], data["lon"]);
                    setState(() {});
                    Navigator.push(context,
                        new MaterialPageRoute(builder: (context) {
                          return DrawPointScreen(
                            latLng: latLng,
                          );
                        }));
                  },
                  child: Text("获取手表最新位置")),
            ),
            Container(
              margin: EdgeInsets.only(top: 20),
              child: InkWell(
                  onTap: () async {
                    _result =
                        await HongkaFlutter_p.UnBindWearer("865852032302588");
                    setState(() {});
                  },
                  child: Text("解绑手表")),
            ),
          ],
        ),
      ),
    );
  }

  Widget ImgCodeInput() {
    return Container(
      margin: EdgeInsets.symmetric(vertical: 20),
      child: TextField(
        decoration: InputDecoration(
          contentPadding: EdgeInsets.all(10.0),
          icon: Icon(Icons.text_fields),
          labelText: '请输入图片验证码',
        ),
        onChanged: _textFieldChanged,
      ),
    );
  }

  void _textFieldChanged(String str) {
    imgCode = str;
    print(str);
  }

  Widget codeInput() {
    return Container(
      margin: EdgeInsets.symmetric(vertical: 20),
      child: TextField(
        decoration: InputDecoration(
          contentPadding: EdgeInsets.all(10.0),
          icon: Icon(Icons.text_fields),
          labelText: '请输入短信验证码',
        ),
        onChanged: _codetextChanged,
      ),
    );
  }

  void _codetextChanged(String str) {
    code = str;
    print(str);
  }

  Widget addInput() {
    return Container(
      margin: EdgeInsets.symmetric(vertical: 20),
      child: TextField(
        decoration: InputDecoration(
          contentPadding: EdgeInsets.all(10.0),
          icon: Icon(Icons.text_fields),
          labelText: '请输入手表验证码',
        ),
        onChanged: _addChanged,
      ),
    );
  }

  void _addChanged(String str) {
    addCode = str;
    print(str);
  }

  Widget _imageWidget() {
    if (_imageData != null) {
      return Image.memory(
        _imageData,
        width: 200,
        height: 100,
        fit: BoxFit.contain,
      );
    } else {
      return Center(
        child: Text('I am not image'),
      );
    }
  }
}
