import 'package:amap_base/amap_base.dart';
import 'package:flutter/material.dart';
import 'package:hongka_flutter_p/bean/LocBean.dart';
import 'package:hongka_flutter_p/src/hongka_flutter_p.dart';
import 'package:hongka_flutter_p/bean/message_bean.dart';
import 'package:hongka_flutter_p/bean/wearer_bean.dart';
import 'package:hongka_flutter_p_example/map/draw_on_map/draw_point.screen.dart';

import '../text_bean.dart';

class SettingPage extends StatefulWidget {
  @override
  _SettingPageState createState() => _SettingPageState();
}

class _SettingPageState extends State<SettingPage> {
  String result = "结果：";
  LocBean _locBean;

  LatLng latLng ;

  WearerBean wearerBean;
  String addCode = "";

  MessageBean messageBean;
  @override
  void initState() {
    super.initState();
    wearerBean = WearerBean.fromJson(wearerData);
    messageBean = MessageBean.fromJson(messageData);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ListView(
        children: <Widget>[
          Container(
            margin: EdgeInsets.only(top: 29),
            child: Text("$result"),
          ),
          buildBindButton(context),
          addInput(),
          buildaddwearButton(context),
          SizedBox(height: 20),
          buildMapButton(context),
          SizedBox(height: 20,),
          buildsendMessageButton(context),
        ],
      ),
    );
  }

  Align buildaddwearButton(BuildContext context) {
    return Align(
      child: SizedBox(
        height: 44,
        width: 325,
        child: RaisedButton(
          child: Text(
            "添加佩戴人",
            style: Theme.of(context).primaryTextTheme.button,
          ),
          color: Color(0xFFF31503),
          onPressed: () async {
            add();
          },
          shape: StadiumBorder(side: BorderSide(color: Color(0xFFF31503))),
        ),
      ),
    );
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

  Future add() async {
    result = await HongkaFlutter_p.wearerAdd(wearerBean, "$addCode");
  }

  Align buildBindButton(BuildContext context) {
    return Align(
      child: SizedBox(
        height: 44,
        width: 325,
        child: RaisedButton(
          child: Text(
            "绑定手表",
            style: Theme.of(context).primaryTextTheme.button,
          ),
          color: Color(0xFFF31503),
          onPressed: () async {
            bind();
          },
          shape: StadiumBorder(side: BorderSide(color: Color(0xFFF31503))),
        ),
      ),
    );
  }

  Future bind() async {
    result = await HongkaFlutter_p.BindWearer("865852032302588");
  }

  Align buildMapButton(BuildContext context) {
    return Align(
      child: SizedBox(
        height: 44,
        width: 325,
        child: RaisedButton(
          child: Text(
            "手表定位",
            style: Theme.of(context).primaryTextTheme.button,
          ),
          color: Color(0xFFF31503),
          onPressed: () async {
            loc1();

          },
          shape: StadiumBorder(side: BorderSide(color: Color(0xFFF31503))),
        ),
      ),
    );
  }


  Future loc() async {
    _locBean = await HongkaFlutter_p.allTrackerLDGet();
    print(_locBean.toString());
    if (_locBean != null) {
      latLng = new LatLng(_locBean.lat, _locBean.lon);
      Navigator.push(context,
          new MaterialPageRoute(builder: (context) {
            return DrawPointScreen(latLng: latLng,);
          }));
    }
  }

  Future loc1() async {
    Map data =
    await HongkaFlutter_p.trackerLDGet("865852032302588");
    print("aaaaaaa   ${data["lon"]}    ${data["lat"]} ");
    latLng = new LatLng(data["lat"], data["lon"]);
      latLng = new LatLng(data["lat"], data["lon"]);
      Navigator.push(context,
          new MaterialPageRoute(builder: (context) {
            return DrawPointScreen(latLng: latLng,);
          }));
  }

  Align buildsendMessageButton(BuildContext context) {
    return Align(
      child: SizedBox(
        height: 44,
        width: 325,
        child: RaisedButton(
          child: Text(
            "发送消息到手表",
            style: Theme.of(context).primaryTextTheme.button,
          ),
          color: Color(0xFFF31503),
          onPressed: () async {
            sendmessage();
          },
          shape: StadiumBorder(side: BorderSide(color: Color(0xFFF31503))),
        ),
      ),
    );
  }

  Future sendmessage() async {
    result =
        await HongkaFlutter_p.pushMessage(messageBean);
  }
}
