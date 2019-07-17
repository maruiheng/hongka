package com.cwtcn.kmlib.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class that returns a encrypted string
 *
 * @version
 * @author lance
 */

public class Encrypt {

	/**
	 * 加密资金账号
	 *
	 * @param fund_account
	 * @return
	 */
	public static String encryptFundAccount(String fund_account) {
		char[] array = fund_account.toCharArray();
		String enc_account = "";
		String tmp = null;
		// 用11减账号的每一位，然后取个位，再拼到每一位的后面
		// 3 0 1 3 9 4 9	加密前
		// 38011038924792	加密后
		for (int i = 0; i < array.length; i++) {
			enc_account = enc_account + array[i];
			tmp = String.valueOf(11 - Integer.parseInt(String.valueOf(array[i])));
			if (tmp.length() > 1) {
				enc_account = enc_account + tmp.substring(1);
			} else {
				enc_account = enc_account + tmp;
			}
		}
		return Base64.encodeString(enc_account);
	}

	/**
	 * encrypt password
	 *
	 * @param str
	 * @return
	 */
	public static String encryptPwd(String str) {
		try {
			byte[] tdesKeyData = {(byte) 0xA2, (byte) 0x15, (byte) 0x37, (byte) 0x07, (byte) 0xCB, (byte) 0x62, (byte) 0xC1, (byte) 0xD3, (byte) 0xF8, (byte) 0xF1, (byte) 0x97, (byte) 0xDF, (byte) 0xD0, (byte) 0x13, (byte) 0x4F, (byte) 0x79, (byte) 0x01, (byte) 0x67, (byte) 0x7A, (byte) 0x85, (byte) 0x94, (byte) 0x16, (byte) 0x31, (byte) 0x92};


			byte[] myIV = {(byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57};

			Cipher c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			SecretKeySpec myKey = new SecretKeySpec(tdesKeyData, "DESede");
			IvParameterSpec ivspec = new IvParameterSpec(myIV);

			c3des.init(Cipher.ENCRYPT_MODE, myKey, ivspec);
			byte[] utf8 = str.getBytes("UTF8");
			byte[] enc = c3des.doFinal(utf8);
			return new String(Base64.encode(enc));
		} catch (javax.crypto.BadPaddingException e) {
		} catch (javax.crypto.IllegalBlockSizeException e) {
		} catch (java.io.IOException e) {
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
		return null;
	}

	/**
	 * decrypt password
	 *
	 * @param str
	 * @return
	 */
	public static String decryptPwd(String str) {
		try {
			if (str == null) return "";

			byte[] tdesKeyData = {(byte) 0xA2, (byte) 0x15, (byte) 0x37, (byte) 0x07, (byte) 0xCB, (byte) 0x62, (byte) 0xC1, (byte) 0xD3, (byte) 0xF8, (byte) 0xF1, (byte) 0x97, (byte) 0xDF, (byte) 0xD0, (byte) 0x13, (byte) 0x4F, (byte) 0x79, (byte) 0x01, (byte) 0x67, (byte) 0x7A, (byte) 0x85, (byte) 0x94, (byte) 0x16, (byte) 0x31, (byte) 0x92};


			byte[] myIV = {(byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57};

			Cipher c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			SecretKeySpec myKey = new SecretKeySpec(tdesKeyData, "DESede");
			IvParameterSpec ivspec = new IvParameterSpec(myIV);

			c3des.init(Cipher.DECRYPT_MODE, myKey, ivspec);

			// Decode base64 to get bytes
			byte[] dec = Base64.decode(str);

			// Decrypt
			byte[] utf8 = c3des.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (javax.crypto.BadPaddingException e) {
		} catch (javax.crypto.IllegalBlockSizeException e) {
		} catch (java.io.IOException e) {
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密的时候用到了用户名作为salt
	 * 所以解密的时候要用用户名解
	 *
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String decryptPwdWithSalt(String str, String salt) {
		return decryptPwd(str).replaceFirst(salt, "");
	}

	/**
	 * encrypt password
	 *
	 * @param str
	 * @param password
	 * @return
	 */
	public static String encryptPwd(String str, String password) {
		try {
			/* The logic followed is: 
			 * The password can be 8-12 chars in length
			 * Suppose the length of password is 8 chars: numOfCharsToBeAppended = (24-8)-8  = 8
			 * 								  is 9 chars: numOfCharsToBeAppended = (24-9)-9 = 6
			 * 								  is 10 chars:numOfCharsToBeAppended = (24-10)-10 = 4
			 */
			int numOfCharsToBeAppended = 24 - password.length(); //16 for password length 8
			int numOfCharsRemaining = numOfCharsToBeAppended - password.length(); //16 - 8 = 8

			String strPadding = password + password + password.substring(0, numOfCharsRemaining);
			;

			byte[] tdesKeyData = strPadding.getBytes();
			String strMyIV = password.substring(0, 8);
			byte[] myIV = strMyIV.getBytes();

			Cipher c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			SecretKeySpec myKey = new SecretKeySpec(tdesKeyData, "DESede");
			IvParameterSpec ivspec = new IvParameterSpec(myIV);

			c3des.init(Cipher.ENCRYPT_MODE, myKey, ivspec);
			byte[] utf8 = str.getBytes("UTF8");
			byte[] enc = c3des.doFinal(utf8);
			return new String(Base64.encode(enc));

		} catch (javax.crypto.BadPaddingException e) {
		} catch (javax.crypto.IllegalBlockSizeException e) {
		} catch (java.io.IOException e) {
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
		return null;
	}

	/**
	 * decrypt password
	 *
	 * @param str
	 * @param password
	 * @return
	 */
	public static String decryptPwd(String str, String password) {
		try {			
			/* The logic followed is: 
			 * The password can be 8-12 chars in length
			 * Suppose the length of password is 8 chars: numOfCharsToBeAppended = (24-8)-8  = 8
			 * 								  is 9 chars: numOfCharsToBeAppended = (24-9)-9 = 6
			 * 								  is 10 chars:numOfCharsToBeAppended = (24-10)-10 = 4
			 */
			int numOfCharsToBeAppended = 24 - password.length(); //16 for password length 8
			int numOfCharsRemaining = numOfCharsToBeAppended - password.length(); //16 - 8 = 8

			String strPadding = password + password + password.substring(0, numOfCharsRemaining);
			;

			byte[] tdesKeyData = strPadding.getBytes();
			String strMyIV = password.substring(0, 8);
			byte[] myIV = strMyIV.getBytes();

			Cipher c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			SecretKeySpec myKey = new SecretKeySpec(tdesKeyData, "DESede");
			IvParameterSpec ivspec = new IvParameterSpec(myIV);

			c3des.init(Cipher.DECRYPT_MODE, myKey, ivspec);

			// Decode base64 to get bytes
			byte[] dec = Base64.decode(str);

			// Decrypt
			byte[] utf8 = c3des.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (javax.crypto.BadPaddingException e) {
		} catch (javax.crypto.IllegalBlockSizeException e) {
		} catch (java.io.IOException e) {
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
		return null;
	}

	public static void main(String args[]) {
		// 3 0 1 3 9 4 9
		// 38011038924792
		System.out.println(Encrypt.encryptFundAccount("3023949"));

		//NotifyHelper.feedback();
//		String password = "+kY6qF8F/Hcjik3EUPt+mg==";
//		password = Encrypt.encryptPwd(password);
//		System.out.println("After encrypt : [" + password + "]");
//		password = Encrypt.decryptPwd(password);
//		System.out.println("After decryptPwd : [" + password + "]");		
//		System.out.println(String.valueOf((Math.random()+1)*1000000).substring(1,7));
		//System.out.println(Encrypt.decryptPwd("TCiQ9cOxCSH1TmzyEJL4L19XuTbmqEP7"));

//		byte[] bte = Base64.decode("eyJjdHlwZSI6IjEiLCJhY2NvdW50IjoiSTAwNDI3MzMiLCJsaXN0VHlwZSI6IjIiLCJwYWdlSW5kZXgiOiIxIiwicGFnZVNpemUiOiIzIn0=");		
//		String sendMsg = Base64.encode("{\"ctype\":\"1\",\"mail\":\"dev01.cn@sanofi-aventis.com\",\"pid\":\"000001000001\"}".getBytes());
//		System.out.println(sendMsg);
//		System.out.println(new String(bte));

		//Base64.encode("");
		//String str = "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";
		//byte[] raw = org.apache.axis.encoding.Base64.decode(str);
		//ByteArrayInputStream in = new ByteArrayInputStream(raw);
//		try {
//			BufferedImage image = ImageIO.read(in);
//			File f = new File("d://xx.gif");
//			// 创建图片
//			ImageIO.write(image, "gif", f);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}		
//		System.out.println(new String(raw));
//		MessageDigestPasswordEncoder enc = new MessageDigestPasswordEncoder("SHA-1");
//		// 00000a 是密码，guest作为salt
//		System.out.println(enc.encodePassword("00000a", "guest"));

	}
}
