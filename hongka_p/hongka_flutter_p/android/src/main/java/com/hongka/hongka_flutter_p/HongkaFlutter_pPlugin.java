package com.hongka.hongka_flutter_p;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * HongkaFlutter_pPlugin
 */
public class HongkaFlutter_pPlugin implements MethodCallHandler {
    /**
     * Plugin registration.
     */

    MyCallBack callback;

    private static HongkaFlutter_pPlugin hongkaFlutter_pPlugin;

    private final MethodChannel channel;

    public static HongkaFlutter_pPlugin getInstance(){
       return hongkaFlutter_pPlugin;
    }

    private  HongkaFlutter_pPlugin ( MethodChannel channel){
        this.channel = channel;
    }

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "hongka_flutter_p");
        hongkaFlutter_pPlugin = new HongkaFlutter_pPlugin(channel);
        channel.setMethodCallHandler(hongkaFlutter_pPlugin);
//        return hongkaFlutter_pPlugin;
    }
    public void setCallback(MyCallBack myCallBack){
        this.callback = myCallBack;
    }


    @Override
    public void onMethodCall(MethodCall call, Result result) {
        callback.getdata(call,result);
    }
}

