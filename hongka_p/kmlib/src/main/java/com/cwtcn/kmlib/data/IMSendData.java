package com.cwtcn.kmlib.data;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/***
 * 用于发送和接收语音数据
 * @author Allen
 *
 */
public class IMSendData {
	public static final String ALARM_VOICE_NAME_TITLE = "ALARM_";
	/** 普通聊天语音 */
	public static final String TYPE_DF = "DF";
	/** 闹铃语音 */
	public static final String TYPE_AM = "AM";
	/** 环境监听语音 */
	public static final String TYPE_HJ = "HJ";
	/** 村长语音 */
	public static final String TYPE_CZ = "CZ";
	/** 表情*/
	public static final String TYPE_EI = "EI";
	/** 手表的上传的图片 */
	public static final String TYPE_DI = "DI";
	/** 位置 */
	public static final String TYPE_PO = "PO";
	/** 位置照片 */
	public static final String TYPE_PI = "PI";
	/** 录影 */
	public static final String TYPE_VF = "VF";
	/** 录音(针对X2智能手机) */
	public static final String TYPE_RV = "RV";
	/** 字符数据文本 */
	public static final String TYPE_TM = "TM";
	/** 智能(盯聊)数据文本 */
	public static final String TYPE_IM = "IM";
	
	
	private String target;// imei号发送的 目标
	private byte[][] allVoice;// 所有的语音字节数组
	private int allCount;// 录音分发发送的总包数
	private long recordTime;// 录音的总时长,发送表情是要传1
	private long sendTime;// 录音的时的时间点
	private int fileLength;// 录音的总字节长度
	
	private String type;
	
	public PositionMsg mPositionMsg;

	public IMSendData(int allCount) {
		super();
		this.allCount = allCount;
		allVoice = new byte[allCount][];
	}

	public int getAllCount() {
		return allCount;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	public byte[] getByte(int index) {
		return allVoice[index];
	}

	public int getFileLength() {
		return fileLength;
	}

	public void setFileLength(int fileLength) {
		this.fileLength = fileLength;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
//		if(TYPE_DF.equals(type)||TYPE_AM.equals(type)||TYPE_HJ.equals(type)){
			this.type = type;
//		}else{
//			this.type=TYPE_DF;
//		}
	}

	public void setByte(List<byte[]> list) {
		for (int i = 0; i < list.size(); i++) {
			setByte(i, list.get(i));
		}
	}

	public void setByte(int index, byte[] bytes) {
		allVoice[index] = bytes;
	}

	public String getNeed() {
		if(isAll()){
			return getNeedPri(false);
		}else{
			return getNeedPri(true);
		}
	}

	private String getNeedPri(boolean isAll) {
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		for (int i = 0; i < allVoice.length; i++) {
			if (isAll || allVoice[i] == null) {
				sb.append(i + "-");
				flag = true;
			}
		}
		if (flag) {
			return sb.substring(0, sb.length() - 1).toString();
		}
		return "";
	}

	public void saveFile(File f) {
		try {
			FileOutputStream fos = new FileOutputStream(f);
			for (byte[] b : allVoice) {
				fos.write(b);
			}
			fos.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}

	}
	
	public void getPosition() {
		try {
			String msg = "";
			for (byte[] b : allVoice) {
				msg += new String(b);
			}
			Gson gson = new Gson();
			mPositionMsg = gson.fromJson(msg, PositionMsg.class);
		} catch (Exception e) {
			e.getClass();
		}
	}

	// 判断是否是否全部接受完全
	private boolean isAll() {
		int length = 0;
		for (int i = 0; i < allVoice.length; i++) {
			length += allVoice[i].length;
		}

		if (length == 0) {
			return false;
		}
		return length == fileLength;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public static class PositionMsg {
		/** 详细地址 */
		public String address;
		/** 纬度 */
		public String lat;
		/** 精度 */
		public String lon;
		/**信息类型 */
		public int txtType;
		/** 文本内容 */
		public String content;
	}
}
