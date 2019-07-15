import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:hongka_flutter_p/src/hongka_flutter_p.dart';


class RegisterPage extends StatefulWidget {
  @override
  _RegisterPageState createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  final _forgotmKey = GlobalKey<FormState>();
  bool _isObscure = true;

  String _phone, _pwd, _code;

  Uint8List _imageData;
  String _imageCode;

  //倒计时
  int _countdown = 60;

  //倒计时控制器 true 显示倒计时  false 隐藏倒计时
  bool _countdownController = false;

//  图片验证码
  TextEditingController invitationcodeController = TextEditingController();

  //手机号的控制器
  TextEditingController phoneController = TextEditingController();

  //验证码的控制器
  TextEditingController codeController = TextEditingController();

  //密码的控制器
  TextEditingController passPwdController = TextEditingController();
  Color _eyeColor;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Form(
          key: _forgotmKey,
          child: ListView(
            padding: EdgeInsets.symmetric(horizontal: 22.0),
            children: <Widget>[
              Container(
                height: 40,
                width: 40,
                alignment: Alignment.topLeft,
                margin: EdgeInsets.only(top: kToolbarHeight),
                child: InkWell(
                  onTap: () {
                    Navigator.pop(context);
                  },
                  child: new Icon(
                    Icons.arrow_back_ios,
                    color: Colors.black,
                  ),
                ),
              ),
              buildTitle(),
              SizedBox(height: 20),
              buildPhoneNumTextField(),
              SizedBox(height: 10.0),
              _imageWidget(),
              SizedBox(height: 10.0),
              buildinvitationcodeTextField(),
              SizedBox(height: 10.0),
              buildcountdownView(context),
              SizedBox(height: 10.0),
              buildPasswordTextField(context),
              SizedBox(height: 10.0),
              buildSurePasswordTextField(context),
              SizedBox(height: 43.0),
              buildSureButton(context),
            ],
          )),
    );
  }

  Widget buildTitle() {
    return Container(
      margin: EdgeInsets.only(left: 10, top: 28),
      child: Column(
        children: <Widget>[
          Row(
            crossAxisAlignment: CrossAxisAlignment.end,
            children: <Widget>[
              new Text(
                "注册",
                style: TextStyle(
                    color: Colors.black,
                    fontSize: 30,
                    fontWeight: FontWeight.w600),
              ),
              Container(
                margin: EdgeInsets.only(left: 14),
                alignment: Alignment.bottomLeft,
                child: new Text(
                  "已有账号",
                  style: TextStyle(color: Colors.black, fontSize: 14),
                ),
              ),
              InkWell(
                onTap: () {
                  Navigator.pop(context);
                },
                child: Container(
                  alignment: Alignment.bottomLeft,
                  child: new Text(
                    "立即登录",
                    style: TextStyle(color: Colors.red, fontSize: 14),
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _imageWidget() {
    if (_imageData != null) {
      return Image.memory(
        _imageData,
        width: 100,
        height: 50,
        fit: BoxFit.contain,
      );
    } else {
      return Center(
        child: Text(''),
      );
    }
  }

  //手机号
  Widget buildPhoneNumTextField() {
    return Container(
      margin: EdgeInsets.only(left: 10),
      child: TextFormField(
        controller: phoneController,
        keyboardType: TextInputType.number,
        decoration: InputDecoration(
            labelText: '手机号',
            hintText: '请输入国内手机号',
            suffixIcon: new IconButton(
                icon: Icon(
                  Icons.clear,
                  color: _eyeColor,
                ),
                onPressed: () {
                  setState(() {
                    //清空输入
                    phoneController.clear();
                  });
                })),
        validator: (String value) {
          if (value.isEmpty) {
            return '请输入手机号';
          }
        },
        onSaved: (String value) => _phone = value,
      ),
    );
  }

  //密码
  Widget buildPasswordTextField(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(left: 10),
      child: TextFormField(
        controller: passPwdController,
        obscureText: _isObscure,
        validator: (String value) {
          if (value.length < 6) {
            return '密码长度少于6位';
          }
          if (value.length > 6) {
            return '密码长度多于10位';
          }
          if (value.isEmpty) {
            return '请输入6-10位字母或数字的密码';
          }
        },
        onSaved: (String value) => _pwd = value,
        decoration: InputDecoration(
            labelText: '密码',
            hintText: '请输入6-10位字母或数字的密码',
            suffixIcon: IconButton(
                icon: Icon(
                  Icons.remove_red_eye,
                  color: _eyeColor,
                ),
                onPressed: () {
                  setState(() {
                    _isObscure = !_isObscure;
                    _eyeColor = _isObscure
                        ? Colors.grey
                        : Theme.of(context).iconTheme.color;
                  });
                })),
      ),
    );
  }

  //确认密码
  Widget buildSurePasswordTextField(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(left: 10),
      child: TextFormField(
        obscureText: _isObscure,
        validator: (String value) {
          if (value.isEmpty) {
            return '请再次输入密码';
          }
          if (value != passPwdController.value.text) {
            return '两次输入密码不一致';
          }
        },
        decoration: InputDecoration(
            labelText: '确认密码',
            hintText: '请再次输入新密码',
//          hintText: pwd,
            suffixIcon: IconButton(
                icon: Icon(
                  Icons.remove_red_eye,
                  color: _eyeColor,
                ),
                onPressed: () {
                  setState(() {
                    _isObscure = !_isObscure;
                    _eyeColor = _isObscure
                        ? Colors.grey
                        : Theme.of(context).iconTheme.color;
                  });
                })),
      ),
    );
  }

  //图片验证码
  Widget buildinvitationcodeTextField() {
    return Container(
      margin: EdgeInsets.only(left: 10),
      child: Row(
        children: <Widget>[
          Expanded(
            child: TextFormField(
              controller: invitationcodeController,
              decoration: InputDecoration(
                labelText: '图片验证码',
                hintText: '请输入图片验证码',
              ),
              onSaved: (String value) => _imageCode = value,
            ),
          ),
          buildImgCodetext(),
        ],
      ),
    );
  }

  Widget buildImgCodetext() {
    return new Container(
      child: FlatButton(
        child: Container(
            height: 27,
            alignment: Alignment.center,
            decoration: new BoxDecoration(
                color: Color(0xFFF31503),
                borderRadius: new BorderRadius.all(Radius.circular(14))),
            child: Padding(
              padding: EdgeInsets.only(left: 15, right: 15),
              child: Text(
                "获取图片验证码",
                style: TextStyle(fontSize: 13, color: Colors.white),
              ),
            )),
        onPressed: () async {
          if (phoneController.value.text == "") {
            print('手机号不能为空');
            return;
          }
          print('mrh   ${phoneController.value.text}');
          //获取验证码
          _imageData = await HongkaFlutter_p.ImageVC4Register(
              phoneController.value.text);
          setState(() {});
        },
      ),
    );
  }

  //验证码
  TextFormField buildCodeTextField(BuildContext context) {
    return TextFormField(
      controller: codeController,
      validator: (String value) {
        if (value.isEmpty) {
          return '请输入验证码';
        }
      },
      onSaved: (String value) => _code = value,
      decoration: InputDecoration(
        labelText: '验证码',
        hintText: '请输入验证码',
      ),
    );
  }

  //倒计时
  Widget buildcountdownView(BuildContext context) {
    return new Container(
      margin: EdgeInsets.only(left: 10),
      child: new Row(
//        crossAxisAlignment: CrossAxisAlignment.end,
        children: [
          new Expanded(
            child: new Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                buildCodeTextField(context),
              ],
            ),
          ),
          buildCodetext(),
        ],
      ),
    );
  }

  //提交
  Align buildSureButton(BuildContext context) {
    return Align(
      child: SizedBox(
        height: 44,
        width: 325,
        child: RaisedButton(
          child: Text(
            '注册',
            style: Theme.of(context).primaryTextTheme.button,
          ),
          color: Color(0xFFF31503),
          onPressed: () async {
            String _result = await HongkaFlutter_p.RegisterByPhone(
                phoneController.value.text,
                passPwdController.value.text,
                codeController.value.text);

            print('mrh _result  ${_result}');
          },
          shape: StadiumBorder(side: BorderSide(color: Color(0xFFF31503))),
        ),
      ),
    );
  }

  Widget buildCodetext() {
    if (!_countdownController) {
      return new Container(
        child: FlatButton(
          child: Container(
              height: 27,
              alignment: Alignment.center,
              decoration: new BoxDecoration(
                  color: Color(0xFFF31503),
                  borderRadius: new BorderRadius.all(Radius.circular(14))),
              child: Padding(
                padding: EdgeInsets.only(left: 15, right: 15),
                child: Text(
                  "获取验证码",
                  style: TextStyle(fontSize: 13, color: Colors.white),
                ),
              )),
          onPressed: () async {
            if (phoneController.value.text == "") {
              print('手机号不能为空');
              return;
            }
            if (invitationcodeController.value.text == "") {
              print('图片验证码不能为空');
              return;
            }
            String _result = await HongkaFlutter_p.CommitImgCode(
                phoneController.value.text,
                invitationcodeController.value.text);

            countDown();
          },
        ),
      );
    } else {
      return new Container(
        child: new Row(
          children: <Widget>[
            new Text(
              _countdown.toString() + "s",
              style: TextStyle(
                color: Colors.blue,
                fontWeight: FontWeight.bold,
              ),
            ),
            new Text('后重新发送'),
          ],
        ),
      );
    }
  }

  void countDown() {
    //设置倒计时1秒后执行跳转方法
    var duration = new Duration(seconds: 1);
    new Future.delayed(duration, text1);
  }

  void text1() {
    setState(() {
      if (_countdown == 0) {
        _countdownController = false;
        _countdown = 60;
      } else {
        _countdown = _countdown - 1;
        countDown();
        _countdownController = true;
      }
    });
  }
}
