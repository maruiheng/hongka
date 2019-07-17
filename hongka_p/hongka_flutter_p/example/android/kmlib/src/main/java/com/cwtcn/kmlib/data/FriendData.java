package com.cwtcn.kmlib.data;

/**
 * 小伙伴数据
 *
 * @author Allen
 */
public class FriendData {

    private String id;
    private String imei;
    private int picId;
    private String name;
    private String friendMobile;
    private String pic;

    public FriendData(String id, String imei, int picId, String name, String friendMobile) {
        this.id = id;
        this.imei = imei;
        this.picId = picId;
        this.name = name;
        this.friendMobile = friendMobile;
    }

    public String getId() {
        return id;
    }

    public String getImei() {
        return imei;
    }

    public int getPicId() {
        return picId;
    }

    public String getName() {
        return name;
    }

    public String getFriendMobile() {
        return friendMobile;
    }

    public String getPic() {
        return pic;
    }
}
