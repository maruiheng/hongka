package com.cwtcn.kmlib.api;

import android.text.TextUtils;

import com.cwtcn.kmlib.data.ChatData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

/**
 * 聊天API
 * Created by Allen on 2017/3/2.
 */

public class KMChatManager {

    private static KMChatManager chatManager;

    private KMChatManager() {

    }

    public static synchronized KMChatManager getInstance() {
        if(chatManager == null) {
            chatManager = new KMChatManager();
        }
        return chatManager;
    }

    /**
     * 发送语音消息
     * @return
     */
    public void sendVoiceMsg(String imei, long recordeTime, final File f) {
        if (f == null || f.length() < 1) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_IM_SEND, "-1", KMConstants.KMTips.ERROR_FILE_NOT_EXIST));
        }
        String name = "";
        if(f.getName().indexOf(".") != -1) {
            name = f.getName().substring(0, f.getName().indexOf("."));
        } else {
            name = f.getName();
        }
        final ChatData chatBean = new ChatData(imei, KMUserManager.getInstance().getUser(), ChatData.SOURCE_SEND, recordeTime, Long.parseLong(name), ChatData.VOICE_READ, ChatData.TYPE_IM_DF);
        KMDBHelper.getInstance().insertChatData(chatBean, new KMDBHelper.INotifyDBChange() {

            @Override
            public void onChange(Object... obj) {
                NettyClientManager.addRecordPacket(chatBean, f, chatBean.getRecordeTimeInt());
                EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_IM_SEND, "0", ""));
            }

            @Override
            public void err(int... code) {

            }
        });
    }

    /**
     * 发送图片
     * @param f
     */
    public void sendPicture(String imei, final File f) {
        if (f == null || f.length() < 1) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_IM_SEND, "-1", KMConstants.KMTips.ERROR_FILE_NOT_EXIST));
        }
        String name = "";
        if(f.getName().indexOf(".") != -1) {
            name = f.getName().substring(0, f.getName().indexOf("."));
        } else {
            name = f.getName();
        }
        final ChatData chatBean = new ChatData(imei, KMUserManager.getInstance().getUser(), ChatData.SOURCE_SEND, 1, Long.parseLong(name), ChatData.VOICE_READ, ChatData.TYPE_IM_DF);
        KMDBHelper.getInstance().insertChatData(chatBean, new KMDBHelper.INotifyDBChange() {

            @Override
            public void onChange(Object... obj) {
                NettyClientManager.addRecordPacket(chatBean, f, chatBean.getRecordeTimeInt());
                EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_IM_SEND, "0", ""));
            }

            @Override
            public void err(int... code) {

            }
        });
    }

    /**
     * 发送表情
     * @param imei
     * @param emoticonFile
     */
    public void sendEmoticon(String imei, final File emoticonFile) {
        if (emoticonFile == null || emoticonFile.length() < 1) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_IM_SEND, "-1", KMConstants.KMTips.ERROR_FILE_NOT_EXIST));
        }
        final ChatData chatBean = new ChatData(imei, KMUserManager.getInstance().getUser(), ChatData.SOURCE_SEND, 1, System.currentTimeMillis(), 1, ChatData.TYPE_IM_EM);
        KMDBHelper.getInstance().insertChatData(chatBean, new KMDBHelper.INotifyDBChange() {

            @Override
            public void onChange(Object... obj) {
                NettyClientManager.addRecordPacket(chatBean, emoticonFile, chatBean.getRecordeTimeInt());
                EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_IM_SEND, "0", ""));
            }

            @Override
            public void err(int... code) {

            }
        });
    }

    /**
     * 发送文字
     * @param imei
     * @param txt
     */
    public void sendTextMsg(String imei, String txt) {
        final ChatData chatBean = new ChatData(imei, KMUserManager.getInstance().getUser(), ChatData.SOURCE_SEND, 1, System.currentTimeMillis(), 1, ChatData.TYPE_IM_TM);
        chatBean.setContent(txt);
        KMDBHelper.getInstance().insertChatData(chatBean, new KMDBHelper.INotifyDBChange() {

            @Override
            public void onChange(Object... obj) {
                NettyClientManager.addRecordPacket(chatBean, null, chatBean.getRecordeTimeInt());
                EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_IM_SEND, "0", ""));
            }

            @Override
            public void err(int... code) {

            }
        });
    }
    /**
     * 发送定位
     * @param imei
     * @param lat
     * @param lon
     * @param address
     */
    public void sendLocation(String imei, double lat, double lon, String address) {
        if (TextUtils.isEmpty(address)) {
            return;
        }
        final ChatData chatBean = new ChatData(imei, KMUserManager.getInstance().getUser(), ChatData.SOURCE_SEND, 1, System.currentTimeMillis(), 1, ChatData.TYPE_IM_PO);
        chatBean.setPositionAddr(address);
        chatBean.setPositionLat(String.valueOf(lat));
        chatBean.setPositionLon(String.valueOf(lon));
        chatBean.setContentType(0);
        KMDBHelper.getInstance().insertChatData(chatBean, new KMDBHelper.INotifyDBChange() {

            @Override
            public void onChange(Object... obj) {
                NettyClientManager.addRecordPacket(chatBean, null, chatBean.getRecordeTimeInt());
                EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_IM_SEND, "0", ""));
            }

            @Override
            public void err(int... code) {

            }
        });
    }

    /**
     * 查询聊天历史
     * @param user
     * @param imei
     * @param limit
     * @param offset
     * @return
     */
    public List<ChatData> getChatHistory(String user, String imei, int limit, int offset) {
        return KMDBHelper.getInstance().queryChatInfo(user, imei, limit, offset);
    }

    /**
     * 获取未读数
     * @param user
     * @param imei
     * @return
     */
    public long getUnreadCount(String user, String imei) {
        return KMDBHelper.getInstance().queryChatUnReadCount(user, imei);
    }

    /**
     * 更新发送状态
     * @param id
     * @param state
     * @param dbChange
     */
    public void updateChatSendState(long id, int state, KMDBHelper.INotifyDBChange dbChange) {
        KMDBHelper.getInstance().updateChatSendState(id, state, dbChange);
    }

    /**
     * 更新为已读
     * @param id
     * @param dbChange
     */
    public void updateChatReaded(long id, KMDBHelper.INotifyDBChange dbChange) {
        KMDBHelper.getInstance().updateChatReaded(id, dbChange);
    }

    /**
     * 更新全部为已读
     * @param user
     * @param imei
     * @param dbChange
     */
    public void updateChatReadAll(String user, String imei, KMDBHelper.INotifyDBChange dbChange) {
        KMDBHelper.getInstance().updateChatReadAll(user, imei, dbChange);
    }

    /**
     * 删除一条聊天记录
     * @param id
     * @param dbChange
     */
    public void delChatDataById(long id, KMDBHelper.INotifyDBChange dbChange) {
        KMDBHelper.getInstance().delChatInfo(id, dbChange);
    }

    /**
     * 删除多条聊天记录
     * @param ids
     * @param dbChange
     */
    public void delChatDataByIds(List<Long> ids, KMDBHelper.INotifyDBChange dbChange) {
        KMDBHelper.getInstance().delChatInfo(ids, dbChange);
    }

    /**
     * 根据类型删除聊天数据
     * @param user
     * @param imei
     * @param type
     * @param dbChange
     */
    public void delChatDataByType(String user, String imei, int type, KMDBHelper.INotifyDBChange dbChange) {
        KMDBHelper.getInstance().delAllChatByType(user, imei, type, dbChange);
    }
}
