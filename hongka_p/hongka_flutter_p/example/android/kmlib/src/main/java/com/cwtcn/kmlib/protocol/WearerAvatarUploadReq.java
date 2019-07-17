package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;
import com.cwtcn.kmlib.util.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * 3.43上传佩戴对象图像
 *
 * @author Allen
 */
public class WearerAvatarUploadReq extends Packet {
    public static final String CMD = "C_WEARER_AVATAR";
    private String wearID;
    private String fileName;
    private long fileLength;

    public WearerAvatarUploadReq() {
        super(CMD);
    }

    public WearerAvatarUploadReq(String wearId, File file) {
        super(CMD, FileUtils.file2Bytes(file));
        this.wearID = wearId;
        this.fileName = file.getName();
        this.fileLength = file.length();
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject mRequest = new JSONObject();
        try {
            mRequest.putOpt("wearerId", wearID);
            mRequest.putOpt("dataLength", fileLength);
            mRequest.putOpt("filename", fileName);
            mRequest.putOpt("lut", DateUtil.getCurrentTime());
        } catch (JSONException e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }
}
