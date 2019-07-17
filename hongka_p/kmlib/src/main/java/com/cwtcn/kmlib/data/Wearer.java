package com.cwtcn.kmlib.data;


import java.util.List;


/**
 * 穿戴设备
 *
 * @author Allen
 */
public class Wearer {
    /**[{"areas":[],"createdTime":"150724114940","dob":"20140401","gender":0,"height":120.0,"id":"156128757810614272","imei":"860861000000002","lastUpdatedTime":"150724114940","markPicId":0,"name":"三代粉色","relationship":8,"relationshipPic":8,"weight":25.0}]*/
    /**
     * wearID
     */
    private String id;
    /**
     * WearName
     */
    private String name;
    /**
     * 性别
     */
    private int gender;
    /**
     * 身高
     */
    private float height;
    /**
     * 体重
     */
    private float weight;
    /**
     * 出生日期
     */
    private String dob;
    /**
     * 创建时间
     */
    private String createdTime;
    /**
     * 用于设置定位页面泡泡的图标，咱不用
     */
    private int markPicID = 0;
    /**
     * 关系索引
     */
    private int relationship = 8;
    /**
     * 关系头像索引
     */
    private int relationshipPic = 8;
    /**
     * 手表的号码
     */
    private String mobile;
    /**
     * 绑定人的手机号码
     */
    private String userMobile;
    /**
     * 绑定人的原手机号码
     */
    private String prevUserMobile;

    private String imei;
    /**
     * 设备型号
     */
    public String productId;
    /**
     * 绑定关系
     */
    private String relationshipName;

    private String lastUpdatedTime;
    /**
     * 头像的更新时间
     */
    private String avatarLut;
    /**
     * 是否是管理员
     */
    private int isAdmin = 0;
    /**
     * 佩戴关系头像
     */
    private String avatarFn = "";
    /**
     * 佩戴关系头像服务器地址
     */
    private String imageServer = "";
    /**
     * 电子栅栏ID列表
     */
    public List<AreaData> areas;

    public Wearer() {

    }

    public Wearer(String wearName, int gender, float height, float weight, String birthDay, String mobile, String userMobile, String imei, int relationShip, String relationshipName) {
        this.name = wearName;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.dob = birthDay;
        this.relationship = relationShip;
        this.relationshipPic = relationShip;
        this.mobile = mobile;
        this.userMobile = userMobile;
        this.imei = imei;
        this.relationshipName = relationshipName;
    }

    public Wearer(String wearID, String wearName, int gender, float height, float weight, String birthDay, String mobile, String userMobile, String imei, int relationShip, String relationshipName) {
        this.id = wearID;
        this.name = wearName;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.dob = birthDay;
        this.relationship = relationShip;
        this.relationshipPic = relationShip;
        this.mobile = mobile;
        this.userMobile = userMobile;
        this.imei = imei;
        this.relationshipName = relationshipName;
    }

    public void setWearer(String wearID, String wearName, int gender, float height, float weight, String birthDay, String mobile, String userMobile, String imei, int markPicID, int relationShip, int relationShipPic, String relationshipName) {
        this.id = wearID;
        this.name = wearName;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.dob = birthDay;
        this.markPicID = markPicID;
        this.relationship = relationShip;
        this.relationshipPic = relationShipPic;
        this.mobile = mobile;
        this.userMobile = userMobile;
        this.imei = imei;
        this.relationshipName = relationshipName;
    }

    public void setWearer(String wearID, String wearName, int gender, float height, float weight, String birthDay, String mobile, String userMobile, String prevUserMobile, String imei, int markPicID, int relationShip, int relationShipPic, String relationshipName) {
        this.id = wearID;
        this.name = wearName;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.dob = birthDay;
        this.markPicID = markPicID;
        this.relationship = relationShip;
        this.relationshipPic = relationShipPic;
        this.mobile = mobile;
        this.prevUserMobile = prevUserMobile;
        this.userMobile = userMobile;
        this.imei = imei;
        this.relationshipName = relationshipName;
    }

    public String getWearerId() {
        return id;
    }

    public void setWearerId(String id) {
        this.id = id;
    }

    public String getWearerName() {
        return name;
    }

    public void setWearerName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getMarkPicID() {
        return markPicID;
    }

    public void setMarkPicID(int markPicID) {
        this.markPicID = markPicID;
    }

    public int getRelationship() {
        return relationship;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }

    public int getRelationshipPic() {
        return relationshipPic;
    }

    public void setRelationshipPic(int relationshipPic) {
        this.relationshipPic = relationshipPic;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getPrevUserMobile() {
        return prevUserMobile;
    }

    public void setPrevUserMobile(String prevUserMobile) {
        this.prevUserMobile = prevUserMobile;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRelationshipName() {
        return relationshipName;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getImageServer() {
        return imageServer;
    }

    public void setImageServer(String imageServer) {
        this.imageServer = imageServer;
    }

    public String getAvatarFn() {
        return avatarFn;
    }

    public void setAvatarFn(String avatarFn) {
        this.avatarFn = avatarFn;
    }

    public List<AreaData> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaData> areas) {
        this.areas = areas;
    }

}
