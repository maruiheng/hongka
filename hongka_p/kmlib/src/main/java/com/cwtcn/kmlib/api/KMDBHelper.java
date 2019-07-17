package com.cwtcn.kmlib.api;

import android.database.sqlite.SQLiteDatabase;

import com.cwtcn.kmlib.data.ChatData;
import com.cwtcn.kmlib.data.ChatDataDao;
import com.cwtcn.kmlib.data.DaoMaster;
import com.cwtcn.kmlib.data.DaoSession;
import com.cwtcn.kmlib.data.TrackerNotice;
import com.cwtcn.kmlib.data.TrackerNoticeDao;

import java.util.List;

/**
 * 数据库操作
 * Created by Allen on 2017/2/14.
 */

public class KMDBHelper {

    private static DaoMaster.DevOpenHelper mHelper;
    private static SQLiteDatabase db;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    private static KMDBHelper mDbhelper;

    private KMDBHelper() {

    }

    public static synchronized KMDBHelper getInstance() {
        if(mDbhelper == null) {
            mDbhelper = new KMDBHelper();
        }
        return mDbhelper;
    }
    /**
     * 初始化greenDao，这个操作建议在Application初始化的时候添加；
     */
    public void initDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(KMApplication.getInstance(), "km-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * 插入推送信息
     * @param data
     */
    public void insertTracerNotice(TrackerNotice data, INotifyDBChange dbChange) {
        getDaoSession().getTrackerNoticeDao().insert(data);
        if(dbChange != null) {
            dbChange.onChange();
        }
    }

    /***
     * 查询消息
     * @param uid
     * @param offset
     * @return
     */
    public List<TrackerNotice> queryNotices(String uid, int offset, int limit) {
        return getDaoSession().getTrackerNoticeDao().queryBuilder()
                .where(TrackerNoticeDao.Properties.MemberId.eq(uid))
                .offset(offset)
                .limit(limit)
                .build()
                .list();
    }

    /***
     * 获取推送消息的未读数
     * @return
     */
    public long queryUnreadNoticeCount(String uid) {
        return getDaoSession().getTrackerNoticeDao().queryBuilder()
                .where(TrackerNoticeDao.Properties.MemberId.eq(uid), TrackerNoticeDao.Properties.Unread.eq(0))
                .count();
    }

    /**
     * 更新推送消息记录为已读
     * @param id
     */
    public void updateNoticeReaded(long id, INotifyDBChange dbChange) {
        TrackerNotice notice = getDaoSession().getTrackerNoticeDao().load(id);
        notice.setUnread(1);
        getDaoSession().getTrackerNoticeDao().update(notice);
        if(dbChange != null) {
            dbChange.onChange();
        }
    }

    /***
     * 删除推送消息
     * @param ids
     */
    public void delNotice(List<Long> ids, INotifyDBChange dbChange) {
        if(ids == null || ids.size() < 1) {
            return;
        }
        for(long id : ids) {
            getDaoSession().getTrackerNoticeDao().deleteByKey(id);
        }
        if(dbChange != null) {
            dbChange.onChange();
        }
    }

    /***
     * 删除所有推送消息
     */
    public void delAllNotice(String uid, INotifyDBChange dbChange) {
        String sql = "delete from TRACKER_NOTICE where USER=" + uid;
        getDaoSession().getDatabase().execSQL(sql);
        if(dbChange != null) {
            dbChange.onChange();
        }
    }

    /**
     * 更新全部推送消息已读
     *
     */
    public void updateNoticeReadAll(String uid, INotifyDBChange dbChange) {
        List<TrackerNotice> mList = getDaoSession().getTrackerNoticeDao().queryBuilder().where(TrackerNoticeDao.Properties.Unread.eq(0)).list();
        for(TrackerNotice data : mList) {
            data.setUnread(1);
            getDaoSession().getTrackerNoticeDao().update(data);
        }
        if(dbChange != null) {
            dbChange.onChange();
        }
    }

    /**
     * 添加一个聊天记录
     */
    public void insertChatData(ChatData chat, INotifyDBChange dbChange) {
        getDaoSession().getChatDataDao().insert(chat);
        if(dbChange != null) {
            dbChange.onChange();
        }
    }

    /**
     * 通过imei号和账号来查询历史聊天记录
     */
    public List<ChatData> queryChatInfo(String user, String imei, int limit, int offset) {
        return getDaoSession().getChatDataDao().queryBuilder()
                .where(ChatDataDao.Properties.User.eq(user),
                        ChatDataDao.Properties.Imei.eq(imei),
                        ChatDataDao.Properties.DelSource.notEq(ChatData.DEL_TYPE_VIEW),
                        ChatDataDao.Properties.SourceType.notEq(ChatData.TYPE_IM_PI),
                        ChatDataDao.Properties.SourceType.notEq(ChatData.TYPE_IM_RV),
                        ChatDataDao.Properties.SourceType.notEq(ChatData.TYPE_IM_IM)
                )
                .offset(offset)
                .limit(limit)
                .build()
                .list();
    }

    /**
     * 通过imei号和账号来查询聊天未读数
     */
    public synchronized long queryChatUnReadCount(String user, String imei) {
        return getDaoSession().getChatDataDao().queryBuilder()
                .where(ChatDataDao.Properties.User.eq(user),
                        ChatDataDao.Properties.Imei.eq(imei),
                        ChatDataDao.Properties.SourceSend.eq(ChatData.SOURCE_RECEIVE),
                        ChatDataDao.Properties.DelSource.eq(ChatData.DEL_TYPE_DEFAULT),
                        ChatDataDao.Properties.HasRead.eq(ChatData.VOICE_NO_READ),
                        ChatDataDao.Properties.SourceType.notEq(ChatData.TYPE_IM_PI),
                        ChatDataDao.Properties.SourceType.notEq(ChatData.TYPE_IM_RV),
                        ChatDataDao.Properties.SourceType.notEq(ChatData.TYPE_IM_IM)
                )
                .count();
    }

    /**
     * 通过聊天内容类型来查询历史聊天记录
     */
    public List<ChatData> queryChatInfoByType(String user, String imei, int type, int limit, int offset) {
        return getDaoSession().getChatDataDao().queryBuilder()
                .where(ChatDataDao.Properties.User.eq(user),
                        ChatDataDao.Properties.Imei.eq(imei),
                        ChatDataDao.Properties.SourceSend.eq(ChatData.SOURCE_RECEIVE),
                        ChatDataDao.Properties.DelSource.notEq(ChatData.DEL_TYPE_VIEW),
                        ChatDataDao.Properties.SourceType.eq(type)
                )
                .offset(offset)
                .limit(limit)
                .build()
                .list();
    }

    /**
     * 通过聊天内容类型来查询未读数
     * @param user
     * @param imei
     * @return
     */
    public synchronized long queryUnReadCountByType(String user, String imei, int type) {
        return getDaoSession().getChatDataDao().queryBuilder()
                .where(ChatDataDao.Properties.User.eq(user),
                        ChatDataDao.Properties.Imei.eq(imei),
                        ChatDataDao.Properties.SourceSend.eq(ChatData.SOURCE_RECEIVE),
                        ChatDataDao.Properties.DelSource.eq(ChatData.DEL_TYPE_DEFAULT),
                        ChatDataDao.Properties.HasRead.eq(ChatData.VOICE_NO_READ),
                        ChatDataDao.Properties.SourceType.eq(type)
                )
                .count();
    }

    /**
     * 更新聊天记录中的发送状态
     */
    public void updateChatSendState(long id, int state, INotifyDBChange dbChange) {
        ChatData data = getDaoSession().getChatDataDao().load(id);
        data.setSendState(state);
        getDaoSession().getChatDataDao().update(data);
        if(dbChange != null) {
            dbChange.onChange();
        }
    }

    /**
     * 更新聊天记录中的语音已读
     */
    public synchronized void updateChatReaded(long id, INotifyDBChange dbChange) {
        ChatData data = getDaoSession().getChatDataDao().load(id);
        data.setHasRead(ChatData.VOICE_READ);
        getDaoSession().getChatDataDao().update(data);
        if(dbChange != null) {
            dbChange.onChange();
        }
    }

    /**
     * 更新IM聊天已读
     * @param user
     * @param imei
     */
    public synchronized void updateChatReadAll(String user, String imei, INotifyDBChange dbChange) {
        String sql = "update CHAT_DATA set HAS_READ=1 where USER=" + user + "and IMEI=" + imei;
        getDaoSession().getDatabase().execSQL(sql);
        if(dbChange != null) {
            dbChange.onChange();
        }
    }

    /**
     * 根据聊天内容类型更新IM聊天已读
     * @param user
     * @param imei
     */
    public void updateChatReadByType(String user, String imei, int type, INotifyDBChange dbChange) {
        String sql = "update CHAT_DATA set HAS_READ=1 where USER=" + user + "and IMEI=" + imei + "and SOURCE_TYPE=" + type;
        getDaoSession().getDatabase().execSQL(sql);
        if(dbChange != null) {
            dbChange.onChange();
        }
    }

    /**
     * 删除聊天数据BY ID
     */
    public synchronized void delChatInfo(long id, INotifyDBChange dbChange){
        getDaoSession().getChatDataDao().deleteByKey(id);
        if(dbChange != null) {
            dbChange.onChange();
        }
    }

    /***
     * 删除聊天消息
     * @param ids
     */
    public void delChatInfo(List<Long> ids, INotifyDBChange dbChange) {
        if(ids == null || ids.size() < 1) {
            return;
        }
        for(long id : ids) {
            getDaoSession().getChatDataDao().deleteByKey(id);
        }
        if(dbChange != null) {
            dbChange.onChange();
        }
    }

    /**
     * 根据类型删除所有的录音数据
     * @param user
     * @param imei
     */
    public synchronized void delAllChatByType(String user, String imei, int type, INotifyDBChange dbChange) {
        String sql = "delete from CHAT_DATA where USER=" + user + "and IMEI=" + imei + "and SOURCE_TYPE=" + type;
        getDaoSession().getDatabase().execSQL(sql);
        if(dbChange != null) {
            dbChange.onChange();
        }
    }

    /**
     * 通知本地数据库有变化
     *
     */
    public interface INotifyDBChange {

        void onChange(Object... obj);

        void err(int... code);
    }
}