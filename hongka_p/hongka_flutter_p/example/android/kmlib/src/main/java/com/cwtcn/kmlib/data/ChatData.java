package com.cwtcn.kmlib.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;

import com.cwtcn.kmlib.api.KMUserManager;
import com.cwtcn.kmlib.util.DateUtil;
import com.cwtcn.kmlib.util.FileUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/***
 * 聊天数据
 *  @author Allen
 */
@Entity
public class ChatData {
	/** 数据库中的id */
	@Id(autoincrement = true)
	private Long id;
	/** 设备的IMEI */
	private String imei;
	/** 当前用户 */
	private String user;
	/** 录音是发送还是接收，发送则是1，接收为0*/
	private int sourceSend;
	/*** 录音时间ms*/
	private long recordeTime;
	/*** 本条录音接收时间	*/
	private long time;
	/**对方是否已播放 播放1,未播放0*/
	private int hasRead;
	/** 接受的语音id，发出的语音id默认为0*/
	private String sourceID;
	/** 发送状态 */
	private int sendState;
	/** 信息类型 */
	private int sourceType = TYPE_IM_DF;
	/** 记录从SDCard删除还是界面删除 1为从SDCard,2为界面,0没删除，不需要暴露*/
	private int delSource = 0;
	/** 表情存储地址 */
	private String expressFile;
	/** 位置消息的地址 */
	private String positionAddr;
	/** 位置消息的纬度 */
	private String positionLat;
	/** 位置消息的经度 */
	private String positionLon;
	/** 文本内容 */
	private String content;
	/** 内容类型 不需要暴露 */
	private int txtType;


	/*** 是否播放 效果*/
	@Transient
	public boolean isPlay = true;
	/** * 为了显示出3分钟内时间段的显示效果 */
	public int sameTime = View.VISIBLE;
	/** 多选删除 */
	@Transient
	public boolean isMultiDel = false;
	/** 已播放状态 */
	@Transient
	public static final int VOICE_READ = 1;
	/** 未播放状态 */
	@Transient
	public static final int VOICE_NO_READ = 0;
	/** 发送语音标识 */
	@Transient
	public static final int SOURCE_SEND = 1;
	/** 接收语音标识 */
	@Transient
	public static final int SOURCE_RECEIVE = 0;
	/**发送状态：正在发送*/
	@Transient
	public static final int STATE_SENDING = 0;
	/**发送状态：正在超时*/
	@Transient
	public static final int STATE_SEND_OVER = 1;
	/**发送状态：正在完成*/
	@Transient
	public static final int STATE_SEND_OK = 2;
	/** 普通聊天语语音 */
	@Transient
	public static final int TYPE_IM_DF = 0;
	/**表情**/
	@Transient
	public static final int TYPE_IM_EM = 1;
	/** "DI"摄像头数字图片类型(Digital Image)*/
	@Transient
	public static final int TYPE_IM_DI = 2;
	/** "HJ"环境语音  */
	@Transient
	public static final int TYPE_IM_HJ = 3;
	/** "CZ"村长语音*/
	@Transient
	public static final int TYPE_IM_CZ = 4;
	/** "PO"位置*/
	@Transient
	public static final int TYPE_IM_PO = 5;
	/** "PI"拍照定位*/
	@Transient
	public static final int TYPE_IM_PI = 6;
	/** "RV"录音(针对)针对X2智能手机*/
	@Transient
	public static final int TYPE_IM_RV = 7;
	/** "TM"字符数据文本*/
	@Transient
	public static final int TYPE_IM_TM = 8;
	/** "IM"智能(町聊)数据文本*/
	@Transient
	public static final int TYPE_IM_IM = 9;
	/** "VF"静默录影*/
	@Transient
	public static final int TYPE_IM_VF = 10;
	/** 录音从哪里删除了？ 0没删除 1sdcard 2从界面 */
	@Transient
	public static final int DEL_TYPE_DEFAULT = 0;
	@Transient
	public static final int DEL_TYPE_SDCARD = 1;
	@Transient
	public static final int DEL_TYPE_VIEW = 2;
	
	public ChatData() {
	}

	public ChatData(String imei, String user, long recordeTime, int sourceType) {
		this.imei = imei;
		this.user = user;
		this.recordeTime = recordeTime;
		this.sourceType = sourceType;
	}

	public ChatData(String imei, String user, int source, long recordeTime, long time, int hasRead, int sourceType) {
		this.imei = imei;
		this.user = user;
		this.sourceSend = source;
		this.recordeTime = recordeTime;
		this.time = time;
		this.hasRead = hasRead;
		this.sourceType = sourceType;
	}

	public ChatData(long id, String imei, String user, int source, long recordeTime, long time, int hasRead, int sourceType) {
		this.id = id;
		this.imei = imei;
		this.user = user;
		this.sourceSend = source;
		this.recordeTime = recordeTime;
		this.time = time;
		this.hasRead = hasRead;
		this.sourceType = sourceType;
	}

	public ChatData(String imei, String user, int source, long recordeTime, long time, int hasRead, String sourceID) {
		this.imei = imei;
		this.user = user;
		this.sourceSend = source;
		this.recordeTime = recordeTime;
		this.time = time;
		this.hasRead = hasRead;
		this.sourceID = sourceID;
	}

	public ChatData(Long id, String imei, String user, int source, long recordeTime, long time, int hasRead, String sourceID) {
		this.id = id;
		this.imei = imei;
		this.user = user;
		this.sourceSend = source;
		this.recordeTime = recordeTime;
		this.time = time;
		this.hasRead = hasRead;
		this.sourceID = sourceID;
	}

	@Generated(hash = 334740877)
	public ChatData(Long id, String imei, String user, int sourceSend, long recordeTime, long time, int hasRead,
									String sourceID, int sendState, int sourceType, int delSource, String expressFile, String positionAddr,
									String positionLat, String positionLon, String content, int txtType, int sameTime) {
					this.id = id;
					this.imei = imei;
					this.user = user;
					this.sourceSend = sourceSend;
					this.recordeTime = recordeTime;
					this.time = time;
					this.hasRead = hasRead;
					this.sourceID = sourceID;
					this.sendState = sendState;
					this.sourceType = sourceType;
					this.delSource = delSource;
					this.expressFile = expressFile;
					this.positionAddr = positionAddr;
					this.positionLat = positionLat;
					this.positionLon = positionLon;
					this.content = content;
					this.txtType = txtType;
					this.sameTime = sameTime;
	}

	public int getDelAuto() {
		return delSource;
	}

	public void setDelAuto(int delAuto) {
		this.delSource = delAuto;
	}
	
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getSourceSend() {
		return sourceSend;
	}

	public boolean getSourceSendBoolean() {
		return sourceSend == SOURCE_SEND;
	}

	public int getHasRead() {
		return hasRead;
	}

	public boolean getHasReadBoolean() {
		return hasRead == VOICE_READ;
	}

	public void setHasRead(int hasRead) {
		this.hasRead = hasRead;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int type) {
		this.sourceType = type;
	}

	public String getSourceID() {
		return sourceID;
	}

	public int getSendState() {
		return sendState;
	}

	public void setSendState(int sendState) {
		this.sendState = sendState;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	public Long getId() {
		return id;
	}


	/**
	 * 获取到用户的图像
	 * 
	 * @return
	 */
	public Bitmap getUserBitmap() {
		// return ActivityPager.bitmapsMap.get(Utils.KEY_DEFAULT_IMAGE);
		File outFile = new File(Environment.getExternalStorageDirectory(),	"/yu_user.jpg");
		return BitmapFactory.decodeFile(outFile.getName());
	}

	public long getRecordeTime() {
		return recordeTime;
	}

	public String getRecordeTimeString() {
		return getRecordeTimeInt() + "″";
	}

	public int getRecordeTimeIntsss() {
		return getRecordeTimeInt();
	}

	public int getRecordeTimeInt() {
		if (recordeTime % 1000 == 0) {
			return ((int) recordeTime / 1000) == 0 ? 1 : (int) recordeTime / 1000;
		} else {
			return (int) recordeTime / 1000 + 1;
		}

	}

	public String getTimeString() {
		return DateUtil.getTime(new Date(time));
	}

	public long getTimeBettwen() {
		return time;
	}

	public long getTime() {
		return time;
	}

	/***
	 * 音频文件的地址
	 * @return
	 */
	public String getRecordeFile() {
		return FileUtils.getRecorderFile(KMUserManager.getInstance().getUID(), String.valueOf(time), imei).toString();
	}
	
	/**
	 * 图片的目录的地址
	 * @param imei
	 * @return
	 */
	public String getPicFile(String imei) {
		return FileUtils.getImageFilePath(KMUserManager.getInstance().getUID(), String.valueOf(time), imei).toString();
	}

	public String getExpressFile() {
		return expressFile;
	}

	public void setExpressFile(String expressIndex) {
		this.expressFile = expressIndex;
	}

	public String getPositionAddr() {
		return positionAddr;
	}

	public void setPositionAddr(String positionAddr) {
		this.positionAddr = positionAddr;
	}

	public String getPositionLat() {
		return positionLat;
	}

	public void setPositionLat(String positionLat) {
		this.positionLat = positionLat;
	}

	public String getPositionLon() {
		return positionLon;
	}

	public void setPositionLon(String positionLon) {
		this.positionLon = positionLon;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public int getContentType() {
		return txtType;
	}

	public void setContentType(int txtType) {
		this.txtType = txtType;
	}
	
	/***
	 * 生成定位信息的字节
	 * @return
	 */
	public byte[] getLocInfoBytes() {
		JSONObject mObject = new JSONObject();
		try {
			mObject.put("txtType", txtType);
			mObject.put("address", positionAddr);
			mObject.put("lat", positionLat);
			mObject.put("lon", positionLon);
			mObject.put("content", content);
		} catch (Exception e) {

		}
		return mObject.toString().getBytes();
	}

	public void setId(long id) {
					this.id = id;
	}

	public void setSourceSend(int sourceSend) {
					this.sourceSend = sourceSend;
	}

	public void setRecordeTime(long recordeTime) {
					this.recordeTime = recordeTime;
	}

	public void setTime(long time) {
					this.time = time;
	}

	public int getDelSource() {
					return this.delSource;
	}

	public void setDelSource(int delSource) {
					this.delSource = delSource;
	}

	public int getTxtType() {
					return this.txtType;
	}

	public void setTxtType(int txtType) {
					this.txtType = txtType;
	}

	public int getSameTime() {
					return this.sameTime;
	}

	public void setSameTime(int sameTime) {
					this.sameTime = sameTime;
	}

	public void setId(Long id) {
					this.id = id;
	}

}