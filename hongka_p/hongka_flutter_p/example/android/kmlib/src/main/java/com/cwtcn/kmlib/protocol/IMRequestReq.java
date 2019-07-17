package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.netty.NettyClientManager;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * 3.2.4.1客户端请求传输语音（RAU）
 *
 * @author Allen
 */
public class IMRequestReq extends Packet {
    public static final String CMD = "RAU";
    /**
     * 发送方语音ID
     */
    private String audioID;
    /**
     * 目标
     */
    private String target;
    /**
     * 文本长度
     */
    private int fileLength;
    /**
     * 发送的包数
     */
    private int packages;
    /**
     * 发送时长
     */
    private long recordTime;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 发送时间
     */
    private long sendTime;

    private String imei;

    public IMRequestReq() {
        super(CMD);
    }

    public IMRequestReq(String imei, int audioID, byte[] mutiData) {
        super(CMD);
        /*this.imei = imei;
		this.audioID = audioID;
		this.payload = mutiData;*/
    }

    public IMRequestReq(String target, String id, int fileLength, int packages, long recordTime, String type) {
        super(CMD);
        this.target = NettyClientManager.sendTarget(target)/* "M1136188874293248" */;
        this.audioID = id;
        this.fileLength = fileLength;
        this.packages = packages;
        this.recordTime = recordTime;
        this.type = type;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(target);
        put(audioID);
        put(fileLength);
        put(packages);
        put(recordTime + "");
        put(type);
        return para2String();
    }

    // 命令的文本：RAU&T860860000010830&26449079001567232&8102&1&5&DF
    //[RAU, T867293021964260, 231590081860100096, 3238, 1, 2, DF, , {"t":"20160217172602"}]
    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        try {
            this.target = NettyClientManager.receiverTarget(sa[offset++]);
            this.audioID = sa[offset++];
            this.fileLength = Integer.parseInt(sa[offset++]);
            this.packages = Integer.parseInt(sa[offset++]);
            this.recordTime = Long.parseLong(sa[offset++]) * 1000;
            if (offset >= length) {
                // this.type = RecordVoice.TYPE_DF;
            } else {
                this.type = sa[offset++];
            }
            offset++;//{"t":"20160217172602"}
            if (offset < length) {
                String mTime = sa[offset];
                JSONObject jsonObj = new JSONObject(mTime);
                String json = jsonObj.optString("t");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                sendTime = sdf.parse(json).getTime();//毫秒

            }
            super.decodeArgs(sa, offset, length);
        } catch (Exception e) {
            this.recordTime = 0;

        }
    }

    public Packet respond(NettyClientManager sc) {
        // 收到语音开始的请求处理 添加对芒果台的imei的匹配
        sc.onStartReceiverRecord(target, audioID, fileLength, packages, recordTime, type, sendTime);
        return new IMRequestRes(NettyClientManager.CODE_OK, audioID);
    }
}
