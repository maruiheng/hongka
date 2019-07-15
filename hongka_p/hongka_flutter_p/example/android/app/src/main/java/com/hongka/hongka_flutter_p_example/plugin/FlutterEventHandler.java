package com.hongka.hongka_flutter_p_example.plugin;

import android.app.Activity;
import android.util.Log;


import com.hongka.hongka_flutter_p_example.MainActivity;

import io.flutter.plugin.common.EventChannel;
import io.flutter.view.FlutterView;

/**
 * Native端主动向Flutter发送消息
 */
public class FlutterEventHandler implements EventChannel.StreamHandler {

    public static final String CHANNEL_NAME = "com.hongka.hongka_flutter_p_example/EventHandler";
    private static EventChannel eventChannel;
    private static MainActivity mActivity;
    private FlutterEventHandler(MainActivity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onListen(Object o, EventChannel.EventSink eventSink) {
        eventSink.success("123");
        mActivity.setEventSink(eventSink);
    }

    @Override
    public void onCancel(Object o) {
        Log.i("FlutterEventHandler", "FlutterEventHandler:onCancel");
    }

    /**
     * 注册
     */
    public static void registerWith(FlutterView flutterView) {
        eventChannel = new EventChannel(flutterView, CHANNEL_NAME);
        FlutterEventHandler instance = new FlutterEventHandler((MainActivity)flutterView.getContext());
        eventChannel.setStreamHandler(instance);
    }
}
