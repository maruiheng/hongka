package com.cwtcn.kmlib.data;


/**
 * 手表绑定验证码数据
 * @author Allen
 *
 */
public class BindCodeData {
	/** 返回码 */
	private String code;
	/** 创建时间 */
	private String createdTime;
	/** 有效时间，分钟 */
	private int survival;
	/** 版本号 */
	public VersionData versionData;
	
	public class VersionData{
		public String versionServer;
	}
}
