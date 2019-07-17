package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * 重置密码的手机验证码
 * Created by Allen on 2017/4/5.
 */

public class PWDResetVCRes extends StringCallback {

    @Override
    public void onError(Call call, Exception e) {

        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_RESET_PWD_VC, "1", e.getMessage()));
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject mObject = new JSONObject(response);
            int resCode = 1;
            if (mObject.has("ret")) {
                resCode = mObject.optInt("ret");
            }
            String msg = "";
            if (mObject.has("msg")) {
                msg = mObject.optString("msg");
            }
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_RESET_PWD_VC, resCode + "", msg));
        } catch (Exception e) {
            e.getCause();
        }
    }
}
