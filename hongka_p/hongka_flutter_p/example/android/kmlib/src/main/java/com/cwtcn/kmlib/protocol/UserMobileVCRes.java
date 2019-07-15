package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;

/**
 * 更改手机号的验证码
 * Created by Allen on 2017/4/5.
 */

public class UserMobileVCRes extends StringCallback {

    @Override
    public void onError(Call call, Exception e) {

        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_USER_PHONE_UPDATE_VC, "1", e.getMessage()));
    }

    @Override
    public void onResponse(String response) {

        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_USER_PHONE_UPDATE_VC, "0", response));
    }
}
