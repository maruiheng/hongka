package com.cwtcn.kmlib.resp;

import com.cwtcn.kmlib.data.ContactData;
import com.cwtcn.kmlib.data.FriendData;

import java.util.List;

/**
 * 联系人数据返回
 * @author Allen
 */
public class ContactRespData extends BaseResp {

    public String imei;//Tracker的imei。
    public String userId;//最后更新亲情号码的用户ID
    public String userName;//最后更新亲情号码的用户名
    public String lut;//亲情号码的最新更新时间
    public String imageServer;//图片的下载地址
    public List<ContactData> fns;//亲情号码对象
    public List<ContactData> ctts;//亲情号码对象(T1506)
    public List<FriendData> friends;//小伙伴(T1506)
}
