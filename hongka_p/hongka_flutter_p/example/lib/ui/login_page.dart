import 'package:flutter/material.dart';
import 'package:hongka_flutter_p/watch_plugin.dart';
import 'package:hongka_flutter_p_example/ui/register_page.dart';
import 'package:hongka_flutter_p_example/ui/setting_page.dart';


class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  String _phone, _password;
  bool _isObscure = true;

  //手机号的控制器
  TextEditingController phoneController = TextEditingController();

  //密码的控制器
  TextEditingController passController = TextEditingController();
  Color _eyeColor;

  @override
  void initState() {
    super.initState();

    phoneController.addListener(() {
      debugPrint(phoneController.text);
    });
    phoneController.text = "17610729625";
    passController.text = "123456mrh";
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Form(
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
//                Navigator.pop(context);
              },
              child: new Icon(
                Icons.close,
                color: Colors.black,
              ),
            ),
          ),
          buildTitle(),
          SizedBox(height: 60.0),
          buildEmailTextField(),
          SizedBox(height: 30.0),
          buildPasswordTextField(context),
          SizedBox(height: 43),
          buildLoginButton(context),
          SizedBox(height: 30.0),
        ],
      )),
    );
  }

  Widget buildTitle() {
    return Container(
      margin: EdgeInsets.only(left: 10, top: 60),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.end,
        children: <Widget>[
          new Text(
            "登录",
            style: TextStyle(
                color: Colors.black, fontSize: 30, fontWeight: FontWeight.w600),
          ),
          Container(
            margin: EdgeInsets.only(left: 14),
            alignment: Alignment.bottomLeft,
            child: new Text(
              "没有账号",
              style: TextStyle(color: Colors.black, fontSize: 14),
            ),
          ),
          InkWell(
            onTap: () {
              Navigator.push(
                  context, MaterialPageRoute(builder: (ctx) => RegisterPage()));
            },
            child: Container(
              alignment: Alignment.bottomLeft,
              child: new Text(
                "立即注册",
                style: TextStyle(color: Colors.red, fontSize: 14),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget buildEmailTextField() {
    return Container(
      margin: EdgeInsets.only(left: 10),
      child: TextFormField(
        controller: phoneController,
        keyboardType: TextInputType.number,
        decoration: InputDecoration(
            labelText: '用户名',
            hintText: '请输入用户名',
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
          var emailReg = RegExp(
              r"[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?");
          if (emailReg.hasMatch(value)) {
            return '请输入正确的手机号';
          }
        },
        onSaved: (String value) => _phone = value,
      ),
    );
  }

  Widget buildPasswordTextField(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(left: 10),
      child: TextFormField(
        onSaved: (String value) => _password = value,
        obscureText: _isObscure,
        maxLength: 15,
        maxLengthEnforced: true,
        validator: (String value) {
          if (value.isEmpty) {
            return '请输入密码';
          }
          if (value.length > 10) {
            return '密码太点长了！';
          }
        },
        decoration: InputDecoration(
            labelText: '密码',
            hintText: '请输入密码',
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

  Align buildLoginButton(BuildContext context) {
    return Align(
      child: SizedBox(
        height: 44,
        width: 325,
        child: RaisedButton(
          child: Text(
            "登录",
            style: Theme.of(context).primaryTextTheme.button,
          ),
          color: Color(0xFFF31503),
          onPressed: () async {
            print(
                'phone:${phoneController.value.text} , assword:${passController.value.text}');
            goLogin();
          },
          shape: StadiumBorder(side: BorderSide(color: Color(0xFFF31503))),
        ),
      ),
    );
  }

  Future goLogin() async {
    String _result =
    await HongkaFlutter_p.Login("17610729625", "123456mrh");
    print("$_result");
      if(_result == "success"){
        Navigator.push(context, MaterialPageRoute(builder: (ctx) => SettingPage()));
      }
  }
}

