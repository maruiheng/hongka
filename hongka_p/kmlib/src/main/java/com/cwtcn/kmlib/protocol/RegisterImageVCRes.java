package com.cwtcn.kmlib.protocol;

import android.graphics.Bitmap;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.zhy.http.okhttp.callback.BitmapCallback;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;

/**
 * 注册验证码
 * Created by Allen on 2017/4/5.
 */

public class RegisterImageVCRes extends BitmapCallback {

    @Override
    public void onError(Call call, Exception e) {

        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_REGISTER_IVC_GET, "1", e.getMessage()));
    }

    @Override
    public void onResponse(Bitmap response) {

        EventBus.getDefault().post(new KMEvent<Bitmap>(KMEventConst.EVENT_REGISTER_IVC_GET, "0", "", response));
    }
}
