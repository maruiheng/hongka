package com.cwtcn.kmlib.util;



import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

public class OauthPassword {
	/**
	 * 将字符串进行加密处理  翻转+md5
	 * 
	 * @param text
	 *            要加密的信息
	 * @return 加密后的信息
	 */
	public static String getMD5Psw(String text) {
		StringBuffer sb = new StringBuffer(text);
		String reversePsw = sb.reverse().toString();  //翻转
		//Log.i(TAG, "要加密的字符 : " + text + "  加密后的字符  :"	+ DigestUtils.md5Hex(getContentBytes(text, "utf-8")));
		return (DigestUtils.md5Hex(getContentBytes(reversePsw, "utf-8"))).toLowerCase();

	}
	
	/**
	 * 将字符串转成 byte 数组
	 * 
	 * @param content
	 *            内容
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}
	
	/***
	 * 
	 * @param type
	 * @param name
	 * @return
	 */
	public static String getOauthName(int type, String name) {
		String newName = name;
		if (type == 2) {
			newName = "WX_" + name;
		} else if (type == 3) {
			newName = "QQ_" + name;
		} else if (type == 4) {
			newName = "WB_" + name;
		}
		return newName;
	}
}
