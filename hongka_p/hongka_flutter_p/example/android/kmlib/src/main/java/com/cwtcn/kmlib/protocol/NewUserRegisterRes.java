package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;

/**
 * function: 新版注册接口
 *
 * @author xhl
 * @date 2019/7/16 17:46
 */

public class NewUserRegisterRes extends StringCallback {

    @Override
    public void onError(Call call, Exception e) {

        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_REGISTER_V3, "1", e.getMessage()));
    }

    @Override
    public void onResponse(String response) {

        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_REGISTER_V3, "0", response));
    }
}
