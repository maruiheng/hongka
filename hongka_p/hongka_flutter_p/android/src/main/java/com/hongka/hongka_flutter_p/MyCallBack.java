package com.hongka.hongka_flutter_p;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public interface MyCallBack {
     void getdata(MethodCall call, MethodChannel.Result result);
}
