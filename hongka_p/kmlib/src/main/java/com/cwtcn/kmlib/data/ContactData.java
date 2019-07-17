package com.cwtcn.kmlib.data;
/**
 * 联系人
 * @author Allen
 * "no":7,"mobile":"13533335555","picld":1,"name":"test2"
 */
public class ContactData {
	/** 序号 */
	private int no;
	/** 电话号码 */
	private String mobile;
	/** 短号 */
	private String familyMobile;
	/** 头像ID */
	private int picId;
	/** 联系人名称 */
	private String name;
	/** 头像地址 */
	private String avatar;
	/** 来源 1 app  2手表 3小伙伴 */
	private int source;
	/** 小伙伴ID */
	private String id;
	/** 小伙伴ID为了见名之意 */
	private String friendId;
	/** 小伙伴IMEI */
	private String friendImei;

	/**
	 * KT01系列构造函数
	 * @param no
	 * @param mobile
     */
	public ContactData(int no, String mobile) {
		this.no = no;
		this.mobile = mobile;
	}

	/**
	 * KT04系列构造函数
	 * @param no
	 * @param mobile
	 * @param picId
     * @param name
     */
	public ContactData(int no, String mobile, int picId, String name){
		this.no = no;
		this.mobile = mobile;
		this.picId = picId;
		this.name  = name;
	}

	/**
	 * 1601等系列
	 * @param no
	 * @param mobile
	 * @param shortMobile
	 * @param name
	 * @param avatar
     * @param source
     */
	public ContactData(int no, String mobile, String shortMobile, String name, String avatar, int source){
		this.no = no;
		this.mobile = mobile;
		this.familyMobile = shortMobile;
		this.name  = name;
		this.avatar = avatar;
		this.source = source;
	}

	/**
	 * 1601等系列
	 * @param no
	 * @param mobile
	 * @param name
	 * @param avatar
	 * @param source
	 * @param id
	 * @param friImei
	 */
	public ContactData(int no, String mobile, String name, String avatar, int source, String id, String friImei) {
		this.no = no;
		this.mobile = mobile;
		this.name  = name;
		this.avatar = avatar;
		this.source = source;
		this.id = id;
		this.friendImei = friImei;
	}

	/**
	 * 1601等系列
	 * @param no
	 * @param mobile
	 * @param name
	 * @param avatar
	 * @param source
	 * @param id
     * @param friImei
     */
	public ContactData(int no, String mobile, String shortMobile, String name, String avatar, int source, String id, String friImei){
		this.no = no;
		this.mobile = mobile;
		this.familyMobile = shortMobile;
		this.name  = name;
		this.avatar = avatar;
		this.source = source;
		this.id = id;
		this.friendImei = friImei;
	}	


	public void setContactData(int no, String mobile) {
		this.no = no;
		this.mobile = mobile;
	}

	public void setContactData(int no, String mobile, int picId, String name) {
		this.no = no;
		this.mobile = mobile;
		this.picId = picId;
		this.name  = name;
	}
	
	public void setContactData(int no, String mobile, String name, String avatar, int source){
		this.no = no;
		this.mobile = mobile;
		this.name  = name;
		this.avatar = avatar;
		this.source = source;
	}

	public int getNo() {
		return no;
	}

	public String getMobile() {
		return mobile;
	}

	public String getFamilyMobile() {
		return familyMobile;
	}

	public int getPicId() {
		return picId;
	}

	public String getName() {
		return name;
	}

	public String getAvatar() {
		return avatar;
	}
	/**
	 * 类型
	 * 1 app添加的联系人
	 * 2 手表添加的联系人
	 * 3 小伙伴
	 */
	public int getType() {
		return source;
	}

	public String getFriendId() {
		return id;
	}

	public String getFriendImei() {
		return friendImei;
	}
}
