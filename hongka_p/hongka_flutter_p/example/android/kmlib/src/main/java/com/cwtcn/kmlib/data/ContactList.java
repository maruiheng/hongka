package com.cwtcn.kmlib.data;

import java.util.List;

/**
 * 联系人列表
 * 用于传递给UI
 * @author Allen
 */
public class ContactList {
	/** imei */
	private String imei;
	/** 联系人列表 */
	private List<ContactData> list;

	public ContactList(String imei, List<ContactData> list) {
		this.imei = imei;
		this.list = list;
	}

	public String getImei() {
		return imei;
	}

	public List<ContactData> getList() {
		return list;
	}
}