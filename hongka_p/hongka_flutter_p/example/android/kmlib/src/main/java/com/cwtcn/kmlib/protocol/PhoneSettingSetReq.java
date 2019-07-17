package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.PhoneSettingData;
import com.cwtcn.kmlib.util.DateUtil;

/**
 * 手机设置
 * Created by xhl on 2017/4/25.
 */

public class PhoneSettingSetReq extends Packet{
    public static final String CMD = "X2_SETTING";
    private PhoneSettingData data;

    public PhoneSettingSetReq() {
        super(CMD);
    }

    public PhoneSettingSetReq(PhoneSettingData data) {
        super( CMD);
        this.data = data;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(data.getJSONData());
        return para2String();
    }
}
