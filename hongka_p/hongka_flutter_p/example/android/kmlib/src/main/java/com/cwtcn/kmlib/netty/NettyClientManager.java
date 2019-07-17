package com.cwtcn.kmlib.netty;

import android.content.Context;
import android.text.TextUtils;

import com.cwtcn.kmlib.api.KMDBHelper;
import com.cwtcn.kmlib.api.KMManager;
import com.cwtcn.kmlib.api.KMUserManager;
import com.cwtcn.kmlib.api.KMWearerManager;
import com.cwtcn.kmlib.data.ChatData;
import com.cwtcn.kmlib.data.DelayTimeData;
import com.cwtcn.kmlib.data.IMSendData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.protocol.*;
import com.cwtcn.kmlib.util.Constants;
import com.cwtcn.kmlib.util.FileUtils;
import com.cwtcn.kmlib.util.MyLog;
import com.cwtcn.kmlib.util.NetworkUtil;
import com.cwtcn.kmlib.util.PrefsUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslHandler;

/**
 * 客户端连接类
 */
public class NettyClientManager implements BusinessBaseHandler.ChannelHandlerContextListener {

    private static final String TAG = "NettyClientManager";

    /**
     * APP Application
     */
    public Context mContext;
    /**
     * 上次接收到数据的时间，如果发送心跳时，读取这个值，如果这个值与发送心跳时的时间差大于心跳时间，说明服务器掉线，需要重新连接
     */
    public static long lastReceiverMessageTime = 0;
    private static NettyClientManager mNettyManager = null;

    private ChannelHandlerContext mChannel;
    private EventLoopGroup group;
    private ChannelFuture channelFuture;

    /**
     * 连接状态常量
     */
    // 初始化
    public static final int CONNECT_INIT = 0;
    // 正在处理中
    public static final int CONNECT_PROCESSORING = 1;
    // 连接成功
    public static final int CONNECT_SUCCESS = 2;
    // 连接失敗
    public static final int CONNECT_FAILED = -1;
    // 连接关闭
    public static final int CONNECT_CLOSED = -2;
    // 连接超时
    public static final int CONNECT_TIMEOUT = -3;
    // 连接异常
    public static final int CONNECT_EXCEPTION = -4;
    // 连接状态
    private int connectState = CONNECT_INIT;
    //屏幕是否点亮
    public static boolean isBright = true;
    //60秒内  连续登录失败的次数
    public static int connecy_fail_index = 0;

    /**
     * 成功 除了acm命令，不在线也是这个命令
     */
    public static final int CODE_OK = 0;
    /**
     * 失败
     */
    public static final int CODE_ERR = 1;
    /**
     * 不在线，目前只对acm命令
     */
    public static final int CODE_NOT_LINE = 2;
    /**
     * 返回码 成功
     */
    public static final String STR_CODE_OK = "0";
    /**
     * 返回码 失败
     */
    public static final String STR_CODE_ERR = "1";
    /**
     * 返回码:不在线
     */
    public static final String STR_NOT_ONLINE = "2";
    /**
     * 返回码:短信不在线
     */
    public static final String STR_SMS_NOT_ONLINE = "SGIP:24";
    /**
     * 返回码:不在线
     */
    public static final String STR_CODE_NOT_LINE = "W001";
    /**
     * 返回码 错误码
     */
    public static final String STR_CODE_SERVER_ERR = "E500";
    /**
     * 用户名不存在
     */
    public static final String STR_CODE_NOFOUNT_ERR = "E105";
    /**
     * 密码错误
     */
    public static final String STR_CODE_PASSERR = "E108";
    /**
     * 存储要发送的包
     */
    protected static final BlockingQueue<Packet> outQ = new LinkedBlockingQueue<Packet>();
    /**
     * 延时包的处理
     */
    protected static final DelayQueue<DelayTimeData> DELAY_QUEUE = new DelayQueue<DelayTimeData>();
    /**
     * 实现了多线程线程同步的原子性操作
     */
    public final AtomicBoolean isRunning = new AtomicBoolean(true);
    /**
     * 是否已连接
     */
    public final AtomicBoolean isConnected = new AtomicBoolean(false);
    /**
     * 最近连接时间
     */
    private volatile long lastReconnect;
    /**
     * 最多的重连次数
     */
    public static final int MAX_RECONNECT = 100;
    /**
     * 重连次数
     */
    private volatile int reconnectCount = 0;

    /**
     * 发送线程
     */
    private volatile Thread writeThread;
    /**
     * 延迟线程
     */
    private volatile Thread delayThread;
    /**
     * 长连接有效标识
     */
    public final AtomicBoolean enableConn = new AtomicBoolean(true);
    public static int loginMethod = 0;

    /**
     * 连接超时
     */
    public static final int SO_TIMEOUT = 90 * 1000;
    /**
     * 正常的心跳时间
     */
    public static final int KEEP_ALIVE = 60 * 1000;

    public static int currentKeepAliveTime = KEEP_ALIVE;
    /**
     * 超时次数
     */
    public static int mTryCount = 3;

    public static final Packet NULL_PACKET = new Packet("") {
        @Override
        protected Packet dup() {
            return this;
        }
    };

    public static final String TARGET_ID_TITILE = "T";
    /**
     * 用来区分发送和接收语音的id
     */
    public static final String SEND_IM = "S";
    public static final String RECEIVE_IM = "R";
    /**
     * 语音发送队列
     */
    public static final Map<String, IMSendData> FACTORY_VOICE = new TreeMap<String, IMSendData>();

    private NettyClientManager() {

    }

    /**
     * 构造方法传递Context
     *
     * @param context
     */
    private NettyClientManager(Context context) {
        mContext = context;
        isRunning.set(true);
    }

    public static NettyClientManager getInstance() {
        if (mNettyManager == null) {
            mNettyManager = new NettyClientManager();
        }
        return mNettyManager;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public void loop() {
        //重连进行中
        while (enableConn.get()) {
            try {
                if (!NetworkUtil.hasNetwork(mContext)) {
                    isRunning.set(false);
                    break;
                }
                reconnect();
            } catch (InterruptedException e) {
                isRunning.set(false);
            } catch (Throwable e) {
                isRunning.set(false);
            }
        }
        disconnect();
    }


    /**
     * 连接服务器
     */
    public void connect() throws InterruptedException {
        long now = System.currentTimeMillis();
        while (!isRunning.get() && now - lastReconnect < 5 * 1000) {// lastReconnect是发生IO错误那一刻
            MyLog.e(TAG, "wait 5s and retry");
            Thread.sleep(1000);
            now = System.currentTimeMillis();
        }

        if (isConnected.get() || isConnected() || connectState == CONNECT_PROCESSORING) {
            // 连接成功 停止重连
            return;
        }
        MyLog.i(getClass().getName(), "socket重连开始");
        lastReconnect = now;
        if (!outQ.isEmpty()) {
            outQ.clear();// 清除掉开始给发的包了
        } else {
            MyLog.i(TAG, "reconnect, outQ为空");
        }
        String mHost = KMManager.getInstance().host;
        int mPort = KMManager.getInstance().port;
        if (TextUtils.isEmpty(mHost) || mPort < 1) {
            //域名和端口号错误
            return;
        }
        stopThread();
        connectState = CONNECT_PROCESSORING;
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("java.net.preferIPv6Addresses", "false");
        //ChannelFuture channelFuture = null;
        if (group == null) {
            group = new NioEventLoopGroup();
        }
        try {
            lastReconnect = System.currentTimeMillis();
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.option(ChannelOption.TCP_NODELAY, true);
            //b.option(ChannelOption.SO_TIMEOUT, Constants.TIME_OUT);
            b.remoteAddress(new InetSocketAddress(mHost, mPort));
            // 有连接到达时会创建一个channel
            b.handler(new ChannelInitializer<SocketChannel>() {
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    //20170414 添加SSL支持
                    SSLContext mSSLContext = SSLContext.getInstance("SSL");
                    mSSLContext.init(null, new TrustManager[]{new MyX509TrustManager()}, new SecureRandom());
                    SSLEngine engine = mSSLContext.createSSLEngine();
                    engine.setUseClientMode(true);
                    pipeline.addFirst("ssl", new SslHandler(engine));
                    //20170414 添加SSL支持 end

                    //pipeline.addFirst("IDLE_STATE_CHECK_AFTER_LOGIN", new IdleStateHandler(6, 10, 30, TimeUnit.SECONDS));
                    pipeline.addLast("decoder", new NettyDecoder());
                    pipeline.addLast("encoder", new NettyEncode());
                    pipeline.addLast("handler", new BusinessBaseHandler(NettyClientManager.this));
                    //pipeline.addLast("listener",new NettyDecoder());
                }
            });
            channelFuture = b.connect().sync();
            channelFuture.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        SocketAddress sa = future.channel().remoteAddress();
                        if (sa != null) {
                            MyLog.i(getClass().getName(), "socket连接成功");
                            // 连接成功
                            connectState = CONNECT_SUCCESS;
                            ConnectReq req = new ConnectReq(KMUserManager.getInstance().mLoginData);
                            sendRequestPacket(req);
                        } else {
                            MyLog.w(getClass().getName(), "socket连接失败 ：sa为null");
                            // 连接失败
                            connectState = CONNECT_FAILED;
                            future.cause().printStackTrace();
                            future.channel().close();
                            // 连接失败 启动重连
                            isConnected.set(false);
                        }
                    } else {
                        MyLog.w(getClass().getName(), "socket连接失败： future 为fail");
                        // 连接失败
                        connectState = CONNECT_FAILED;
                        future.cause().printStackTrace();
                        future.channel().close();
                        // 连接失败 启动重连
                        isRunning.set(false);
                        isConnected.set(false);
                    }
                }
            });
            // Wait until the connection is closed.
        } catch (Exception e) {
            setConnectFailIndex();
            MyLog.e(getClass().getName(), "socket连接异常" + e.toString());
            connectState = CONNECT_EXCEPTION;
            isConnected.set(false);
        } finally {
            //group.shutdownGracefully();
            //disconnect(channelFuture);
        }
    }

    /**
     * 重连
     */
    public void reconnect() throws InterruptedException {
        connect();
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        if (channelFuture != null) {
            channelFuture.channel().closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    MyLog.w(getClass().getName(), "socket断开连接");
                    setConnectFailIndex();
                    if (channelFuture != null) {
                        connectState = CONNECT_CLOSED;
                    }
                }
            });
        }
        onDisconnected();
    }

    public void onConnected() {
        startThread();
        isConnected.set(true);
    }

    public void onDisconnected() {
        isRunning.set(false);
        isConnected.set(false);
        enableConn.set(false);
        connectState = CONNECT_CLOSED;
        stopThread();
    }

    /**
     * 掉线处理
     *
     * @param msg
     */
    public void onKot(final String msg) {
        enableConn.set(false);
        PrefsUtil.setSharedPreferencesAll(mContext, true, Constants.Prefs.KEY_EXIT_STATE);
        isRunning.set(false);
        isConnected.set(false);
        connectState = CONNECT_INIT;
        KMManager.getInstance().clearData();
    }

    private void startThread() {
        if (writeThread == null) {
            writeThread = new Thread(new Writer(this));
            writeThread.start();
        }
        if (delayThread == null) {
            delayThread = new Thread(new Delay(this));
            delayThread.start();
        }
    }

    private void stopThread() {
        if (null != writeThread) {
            writeThread.interrupt();
            writeThread = null;
        }

        if (null != delayThread) {
            delayThread.interrupt();
            delayThread = null;
        }
    }

    /**
     * 是否连接成功
     *
     * @return
     */
    public boolean isConnected() {
        return connectState == CONNECT_SUCCESS ? true : false;
    }

    private void setConnectFailIndex() {
        //60秒内连接失败，计数++
        if (System.currentTimeMillis() - lastReconnect < 60 * 1000) {
            connecy_fail_index++;
        } else {
            connecy_fail_index = 0;
        }
        MyLog.d(getClass().getName(), "连接失败次数" + connecy_fail_index);
    }

    @Override
    public void onChannelHandlerContextSet(ChannelHandlerContext c) {
        mChannel = c;
    }

    /**
     * 发送请求
     *
     * @param data
     * @return
     */
    public boolean sendRequestPacket(Packet data) {
        if (mChannel != null) {
            mChannel.writeAndFlush(data);
            return true;
        } else {
            return false;
        }
    }

    /***
     * 发送心跳包
     */
    public void sendHeartPacket() {
        if (mChannel != null) {
            mChannel.writeAndFlush(NULL_PACKET);
        }
    }

    /**
     * 关闭
     */
    public void closeChannel() {
        if (mChannel != null) {
            mChannel.close();
            mChannel = null;
        }
    }

    /**
     * 发送队列中的请求
     *
     * @return
     */
    protected boolean sending() {
        // log("process sending");
        try {
            Packet p = outQ.poll(currentKeepAliveTime, TimeUnit.MILLISECONDS);
            if (NULL_PACKET == p) {
                return true;
            }
            if (null == p) {
                p = new PingReq();
                MyLog.i("PingReq", "---p == null ,send heart");
            } else if (TextUtils.isEmpty(p.cmd)) {// 心跳以外的命令不应该出现cmd为空，所以不发
                MyLog.i("PingReq", "the cmd should not be empty");
                return true;
            }
            sendRequestPacket(p);
        } catch (InterruptedException e) {
            return false;
        }
        /*catch (IOException e) {
			e.printStackTrace();
			return true;
		}*/ catch (Exception e) {
            return true;
        }
        return true;
    }

    /**
     * 发送延迟队列中的请求
     *
     * @return
     */
    protected boolean delaying() {
        try {
            DelayTimeData s = DELAY_QUEUE.take();
            if (s != null) {
                if (s.getId().equals("STOP")) {
                    return false;
                }
                s.start();
            }
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    private static final class Writer implements Runnable {

        private final NettyClientManager ns;

        public Writer(NettyClientManager ns) {
            this.ns = ns;
        }

        @Override
        public void run() {
            // sm.log("Writer start");
            while (ns.sending())
                ;
            // sm.log("Writer done");
        }
    }

    private static final class Delay implements Runnable {

        private final NettyClientManager ns;

        public Delay(NettyClientManager ns) {
            this.ns = ns;
        }

        @Override
        public void run() {
            while (ns.delaying())
                ;
        }
    }

    /**
     * 添加请求到发送队列
     *
     * @param p
     */
    public static void addOutQ(Packet p) {
        if (outQ != null) {
            MyLog.d("Packet", "添加进发送包队列:cmd=" + p.cmd);
            try {
                if (p != null) {
                    outQ.put(p);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送请求到延迟队列
     *
     * @param d
     */
    public static void addDelayQ(DelayTimeData d) {
        if (DELAY_QUEUE != null) {
            MyLog.d("Packet", "添加进发送包队列:cmd=" + d.getId());
            DELAY_QUEUE.put(d);
        }
    }

    /**
     * 移除延迟队列的请求
     *
     * @param id
     * @return
     */
    public DelayTimeData removeDelayQ(String id) {
        for (DelayTimeData delay : DELAY_QUEUE) {
            if (delay.getId().equals(id)) {
                DELAY_QUEUE.remove(delay);
                return delay;
            }
        }
        return null;
    }

    public static class MyX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }

    // begin:发送语音

    /**
     * 添加一个语音包
     *
     * @param chat       语音ChatBean对象
     * @param record     录音的原文件
     * @param recordTime 录音的时长
     */
    public static void addRecordPacket(final ChatData chat, File record, long recordTime) {
        String type = IMSendData.TYPE_DF;
        if (chat.getSourceType() == ChatData.TYPE_IM_DF) {
            type = IMSendData.TYPE_DF;
        } else if (chat.getSourceType() == ChatData.TYPE_IM_EM) {
            type = IMSendData.TYPE_EI;
        } else if (chat.getSourceType() == ChatData.TYPE_IM_PO) {
            type = IMSendData.TYPE_PO;
        } else if (chat.getSourceType() == ChatData.TYPE_IM_DI) {
            type = IMSendData.TYPE_DI;
        } else if (chat.getSourceType() == ChatData.TYPE_IM_TM) {
            type = IMSendData.TYPE_TM;
        } else if (chat.getSourceType() == ChatData.TYPE_IM_IM) {
            type = IMSendData.TYPE_IM;
        }
        addRecordPacket(chat.getImei(), chat.getId() + "", record, recordTime, type, chat.getLocInfoBytes());
    }

    /***
     * 添加语音到发送队列
     *
     * @param imei
     * @param id
     * @param record
     * @param recordTime
     * @param sourceType
     */
    @SuppressWarnings("resource")
    public static void addRecordPacket(String imei, String id, File record, long recordTime, String sourceType,
                                       byte[] sendBytes) {
        List<byte[]> list = new ArrayList<byte[]>();
        FileInputStream fis;
        int allLength = 0;
        if (record != null) {
            try {
                fis = new FileInputStream(record);
                byte[] buffer = new byte[1024 * 10];
                int length = 0;
                while ((length = fis.read(buffer)) != -1) {
                    byte[] byTemp = new byte[length];
                    System.arraycopy(buffer, 0, byTemp, 0, length);
                    list.add(byTemp);
                    allLength += length;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else if (sourceType == IMSendData.TYPE_PO || sourceType == IMSendData.TYPE_TM || sourceType == IMSendData.TYPE_IM) {
            allLength = sendBytes.length;
            list.add(sendBytes);
        }

        if (list.size() == 0) {
            MyLog.e("tag", "没有语音字节，不发送");
            return;
        }

        IMSendData re = new IMSendData(list.size());
        re.setByte(list);
        re.setTarget(imei);
        re.setFileLength(allLength);
        FACTORY_VOICE.put(SEND_IM + id + "", re);
        // 首先发送RAU
        addOutQ(new IMRequestReq(imei, id + "", allLength, list.size(), recordTime, sourceType));
    }

    /**
     * 开始发送语音
     *
     * @param audioId
     */
    public void startSendVoice(String audioId) {
        IMSendData re = FACTORY_VOICE.get(SEND_IM + audioId);
        if (re != null) {
            for (int i = 0; i < re.getAllCount(); i++) {
                addOutQ(new IMSendReq(audioId, i + "", re.getByte(i)));
            }
        }
        // 最后发一个发送结束的包
        addOutQ(new IMSendEndReq(audioId));
    }

    /***
     * 重发失败的语音
     *
     * @param id
     * @param need
     */
    public void repeatSendVoice(String id, String need) {
        String[] indexs = need.split("-");
        IMSendData re = FACTORY_VOICE.get(SEND_IM + id);
        if (re != null) {
            for (String s : indexs) {
                int i = Integer.parseInt(s);
                addOutQ(new IMSendReq(id, i + "", re.getByte(i)));
            }
            // 最后发一个发送结束的包
            addOutQ(new IMSendEndReq(id));
        }
    }

    /***
     * 对发不在线，服务器缓存
     * @param audioId
     */
    public void sendVoiceNotOnline(final long audioId) {
        KMDBHelper.getInstance().updateChatSendState(audioId, ChatData.STATE_SEND_OK, new KMDBHelper.INotifyDBChange() {

            @Override
            public void onChange(Object... obj) {

            }

            @Override
            public void err(int... code) {

            }
        });
    }

    /****
     * 开始接收语音
     *
     * @param target
     * @param id
     * @param fileLength
     * @param length
     * @param recordTime
     * @param type
     */
    public void onStartReceiverRecord(String target, String id, int fileLength, int length, long recordTime, String type, long sendtime) {
        IMSendData re = new IMSendData(length);
        re.setTarget(target);
        re.setRecordTime(recordTime);
        re.setFileLength(fileLength);
        re.setType(type);
        re.setSendTime(sendtime);
        FACTORY_VOICE.put(RECEIVE_IM + id, re);
    }

    /***
     * 接收到语音传送结束
     *
     * @param id
     * @return
     */
    public IMSendEndRes onReceiveVoiceEnd(String id) {
        IMSendData re = FACTORY_VOICE.get(RECEIVE_IM + id);
        if (re == null) {
            return null;
        }
        String need = re.getNeed();
        if ("".equals(need)) {
            saveVoice(re, id);
            return new IMSendEndRes(CODE_OK, id, need);
        } else {
            return new IMSendEndRes(CODE_ERR, id, need);
        }
    }

    // 全部接收语音完成后，保存语音并且通知界面更新
    private void saveVoice(final IMSendData re, final String id) {
        // Begin:如果当前的imei的手表已经删除了，就只接受数据不处理逻辑
        if (re.getTarget() == null || TextUtils.isEmpty(KMWearerManager.getInstance().getWearerName(re.getTarget()))) {
            FACTORY_VOICE.remove(RECEIVE_IM + id);
            return;
        }
        // end
        String user = PrefsUtil.getStringSharedPreferences(mContext, Constants.Prefs.KEY_USER);
        String userId = KMUserManager.getInstance().getUID();
        long name = re.getSendTime() > 0 ? re.getSendTime() : System.currentTimeMillis();
        File file = null;
        final ChatData chatBean = new ChatData(re.getTarget(), user, ChatData.SOURCE_RECEIVE, re.getRecordTime(), name, ChatData.VOICE_NO_READ, id);
        if (re.getType().equals(IMSendData.TYPE_DF)) {
            file = FileUtils.getRecorderFile(userId, name + "", re.getTarget());
            re.saveFile(file);
            chatBean.setSourceType(ChatData.TYPE_IM_DF);
        } else if (re.getType().equals(IMSendData.TYPE_HJ)) {
            file = FileUtils.getRecorderFile(userId, name + "", re.getTarget());
            re.saveFile(file);
            chatBean.setSourceType(ChatData.TYPE_IM_HJ);
        }
        // "DI"摄像头数字图片类型
        else if (re.getType().equals(IMSendData.TYPE_DI)) {
            file = FileUtils.getImageFilePath(userId, name + "", re.getTarget());
            re.saveFile(file);
            chatBean.setSourceType(ChatData.TYPE_IM_DI);
        }
        // "EI"手表发上来的表情图标
        else if (re.getType().equals(IMSendData.TYPE_EI)) {
            file = FileUtils.getImageFilePath(userId, name + "", re.getTarget());
            re.saveFile(file);
            chatBean.setExpressFile(file.toString());
            chatBean.setSourceType(ChatData.TYPE_IM_EM);
        }
        // "PO"手表发上来的位置
        else if (re.getType().equals(IMSendData.TYPE_PO)) {
            re.getPosition();
            chatBean.setPositionAddr(re.mPositionMsg.address);
            chatBean.setPositionLat(re.mPositionMsg.lat);
            chatBean.setPositionLon(re.mPositionMsg.lon);
            chatBean.setContent(re.mPositionMsg.content);
            chatBean.setContentType(re.mPositionMsg.txtType);
            chatBean.setSourceType(ChatData.TYPE_IM_PO);
        }
        // "TM"手表发上来的字符文本数据
        else if (re.getType().equals(IMSendData.TYPE_TM)) {
            re.getPosition();
            chatBean.setPositionAddr(re.mPositionMsg.address);
            chatBean.setPositionLat(re.mPositionMsg.lat);
            chatBean.setPositionLon(re.mPositionMsg.lon);
            chatBean.setContent(re.mPositionMsg.content);
            chatBean.setContentType(re.mPositionMsg.txtType);
            chatBean.setSourceType(ChatData.TYPE_IM_TM);
        }
        // "IM"手表发上来的智能(小盯)字符文本数据
        else if (re.getType().equals(IMSendData.TYPE_IM)) {
            re.getPosition();
            chatBean.setPositionAddr(re.mPositionMsg.address);
            chatBean.setPositionLat(re.mPositionMsg.lat);
            chatBean.setPositionLon(re.mPositionMsg.lon);
            chatBean.setContent(re.mPositionMsg.content);
            chatBean.setContentType(re.mPositionMsg.txtType);
            chatBean.setSourceType(ChatData.TYPE_IM_IM);
        }
        // "PI"摄像头数字图片类型
        else if (re.getType().equals(IMSendData.TYPE_PI)) {
            file = FileUtils.getImageFilePath(userId, name + "", re.getTarget());
            re.saveFile(file);
            chatBean.setSourceType(ChatData.TYPE_IM_PI);
        }
        // "VF"静默录影类型
        else if (re.getType().equals(IMSendData.TYPE_VF)) {
            file = FileUtils.getVideoFile(userId, name + "", re.getTarget());
            re.saveFile(file);
            chatBean.setSourceType(ChatData.TYPE_IM_VF);
            chatBean.setSourceType(ChatData.TYPE_IM_VF);
        }
        // "RV"录音数据类型(针对X2智能手机)
        else if (re.getType().equals(IMSendData.TYPE_RV)) {
            file = FileUtils.getRecorderFile(userId, name + "", re.getTarget());
            re.saveFile(file);
            chatBean.setSourceType(ChatData.TYPE_IM_RV);
        }

        chatBean.setSourceID(id);
        KMDBHelper.getInstance().insertChatData(chatBean, new KMDBHelper.INotifyDBChange() {

            public void onChange(Object... obj) {
                FACTORY_VOICE.remove(RECEIVE_IM + id);
                EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_IM_RECEIVER, "0", "", chatBean));
            }

            public void err(int... code) {

            }
        });
    }

    /***
     * 接收语音
     *
     * @param id
     * @param part
     * @param payload
     */
    public void onReceiverRecord(String id, String part, byte[] payload) {
        IMSendData re = FACTORY_VOICE.get(RECEIVE_IM + id);
        if (re != null) {
            re.setByte(Integer.parseInt(part), payload);
        }
    }

    /***
     * 更新语音已读
     * @param id
     */
    public void onUpdateVoiceRead(final long id) {
        KMDBHelper.getInstance().updateChatReaded(id, new KMDBHelper.INotifyDBChange() {

            @Override
            public void onChange(Object... obj) {
                EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_IM_READ, "0", ""));
            }

            @Override
            public void err(int... code) {

            }
        });
    }

    /**
     * 多个和一个imei号的处理
     *
     * @param id 860860000000107 或者 860860000000107;860860000000107;860860000000107
     * @return T860860000000107 或者是 T860860000000107;T860860000000107;T860860000000107
     */
    public static String sendTarget(String id) {
        String[] ss = id.split(";");
        if (ss.length == 1) {
            return TARGET_ID_TITILE + id;
        } else {
            StringBuffer sb = new StringBuffer();
            for (String s : ss) {
                sb.append(TARGET_ID_TITILE + s + ";");
            }
            if (sb.length() > 0) {
                return sb.substring(0, sb.length() - 1);
            }
        }
        return TARGET_ID_TITILE + id;
    }

    /**
     * 反解为多个 imei号
     *
     * @param id T860860000000107 或者是
     * @return 860860000000107
     */
    public static String receiverTarget(String id) {
        if (id.startsWith(TARGET_ID_TITILE)) {
            return id.replaceAll("T", "");
        }
        return id;
    }
    // end:发送语音

    public static final Map<String, Packet> PACKET_FACTORY;

    static {
        PACKET_FACTORY = new TreeMap<String, Packet>();
        //ARE登录连接返回
        PACKET_FACTORY.put(ConnectRes.CMD, new ConnectRes());
        //心跳
        PACKET_FACTORY.put(PingRes.CMD, new PingRes());
        //查询佩戴对象返回
        PACKET_FACTORY.put(WearerQueryRes.CMD, new WearerQueryRes());
        //添加佩戴对象返回
        PACKET_FACTORY.put(WearerAddRes.CMD, new WearerAddRes());
        //更新佩戴对象返回
        PACKET_FACTORY.put(WearerUpdateRes.CMD, new WearerUpdateRes());
        //删除佩戴对象返回
        PACKET_FACTORY.put(WearerDelRes.CMD, new WearerDelRes());
        //上传佩戴对象图像返回
        PACKET_FACTORY.put(WearerAvatarUploadRes.CMD, new WearerAvatarUploadRes());
        //获取佩戴对象图像返回
        PACKET_FACTORY.put(WearerAvatarGetRes.CMD, new WearerAvatarGetRes());
        //获取绑定手表二维码
        PACKET_FACTORY.put(WearerQRCodeGetRes.CMD, new WearerQRCodeGetRes());
        //获取手表四位动态验证码
        PACKET_FACTORY.put(WearerVCCodeGetRes.CMD, new WearerVCCodeGetRes());
        //获取KT01系列联系人
        PACKET_FACTORY.put(FamilyNumbersQueryRes.CMD, new FamilyNumbersQueryRes());
        //设置KT01系列联系人
        PACKET_FACTORY.put(FamilyNumbersSetRes.CMD, new FamilyNumbersSetRes());
        //KT01系列联系人推送
        PACKET_FACTORY.put(FamilyNumbersPush.CMD, new FamilyNumbersPush());
        //获取KT04系列联系人
        PACKET_FACTORY.put(FamilyNumbersEXQueryRes.CMD, new FamilyNumbersEXQueryRes());
        //设置KT04系列联系人
        PACKET_FACTORY.put(FamilyNumbersEXSetRes.CMD, new FamilyNumbersEXSetRes());
        //KT04系列联系人推送
        PACKET_FACTORY.put(FamilyNumbersEXPush.CMD, new FamilyNumbersEXPush());
        //获取1601系列联系人
        PACKET_FACTORY.put(CTTSQueryRes.CMD, new CTTSQueryRes());
        //设置1601系列联系人
        PACKET_FACTORY.put(CTTSSetRes.CMD, new CTTSSetRes());
        //1601系列联系人推送
        PACKET_FACTORY.put(CTTSPush.CMD, new CTTSPush());
        //获取KT04系列小伙伴
        PACKET_FACTORY.put(WearerFriendQueryRes.CMD, new WearerFriendQueryRes());
        //更新KT04系列小伙伴
        PACKET_FACTORY.put(WearerFriendUpdateRes.CMD, new WearerFriendUpdateRes());
        //删除KT04系列小伙伴
        PACKET_FACTORY.put(WearerFriendDelRes.CMD, new WearerFriendDelRes());
        //KT04系列小伙伴推送
        PACKET_FACTORY.put(WearerFriendPush.CMD, new WearerFriendPush());
        //IM RAU
        PACKET_FACTORY.put(IMRequestReq.CMD, new IMRequestReq());
        //IM ARA
        PACKET_FACTORY.put(IMRequestRes.CMD, new IMRequestRes());
        //IM SAU
        PACKET_FACTORY.put(IMSendReq.CMD, new IMSendReq());
        //IM SAE
        PACKET_FACTORY.put(IMSendEndReq.CMD, new IMSendEndReq());
        //IM ASA
        PACKET_FACTORY.put(IMSendEndRes.CMD, new IMSendEndRes());
        //IM RAD
        PACKET_FACTORY.put(IMReadReq.CMD, new IMReadReq());
        //电子栅栏获取
        PACKET_FACTORY.put(AlertAreaGetRes.CMD, new AlertAreaGetRes());
        //电子栅栏创建
        PACKET_FACTORY.put(AlertAreaSetRes.CMD, new AlertAreaSetRes());
        //电子栅栏更新
        PACKET_FACTORY.put(AlertAreaUpdateRes.CMD, new AlertAreaUpdateRes());
        //电子栅栏开关
        PACKET_FACTORY.put(AlertAreaEnableRes.CMD, new AlertAreaEnableRes());
        //电子栅栏推送
        PACKET_FACTORY.put(AlertAreaPush.CMD, new AlertAreaPush());
        //所有设备位置
        PACKET_FACTORY.put(TrackerLDGetAllRes.CMD, new TrackerLDGetAllRes());
        //设备最新位置
        PACKET_FACTORY.put(TrackerLDGetRes.CMD, new TrackerLDGetRes());
        //设备最新位置推送
        PACKET_FACTORY.put(TrackerLDPush.CMD, new TrackerLDPush());
        //家校位置删除
        PACKET_FACTORY.put(HSLocationDelRes.CMD, new HSLocationDelRes());
        //家校位置设置
        PACKET_FACTORY.put(HSLocationSetRes.CMD, new HSLocationSetRes());
        //家校位置更新
        PACKET_FACTORY.put(HSLocationUpdateRes.CMD, new HSLocationUpdateRes());
        //家校时间设置
        PACKET_FACTORY.put(HSTimeSetRes.CMD, new HSTimeSetRes());
        //家校时间更新
        PACKET_FACTORY.put(HSTimeUpdateRes.CMD, new HSTimeUpdateRes());
        //家校查询
        PACKET_FACTORY.put(HSQueryRes.CMD, new HSQueryRes());
        //家校日志查询
        PACKET_FACTORY.put(HSLogQueryRes.CMD, new HSLogQueryRes());
        //家校日志推送
        PACKET_FACTORY.put(HSLogPush.CMD, new HSLogPush());
        //常用位置查询
        PACKET_FACTORY.put(LocMarkQueryRes.CMD, new LocMarkQueryRes());
        //常用位置设置
        PACKET_FACTORY.put(LocMarkSetRes.CMD, new LocMarkSetRes());
        //常用位置更新
        PACKET_FACTORY.put(LocMarkUpdateRes.CMD, new LocMarkUpdateRes());
        //常用位置删除
        PACKET_FACTORY.put(LocMarkDelRes.CMD, new LocMarkDelRes());
        //历史位置
        PACKET_FACTORY.put(TrackerLocHistoryGetRes.CMD, new TrackerLocHistoryGetRes());
        //WIFI定位修正
        PACKET_FACTORY.put(WifiLocUpdateRes.CMD, new WifiLocUpdateRes());
        //工作模式查询
        PACKET_FACTORY.put(TrackerWorkModeQueryRes.CMD, new TrackerWorkModeQueryRes());
        //工作模式设置
        PACKET_FACTORY.put(TrackerWorkModeSetRes.CMD, new TrackerWorkModeSetRes());
        //工作模式推送
        PACKET_FACTORY.put(TrackerWorkModePush.CMD, new TrackerWorkModePush());
        //工作模式查询
        PACKET_FACTORY.put(TrackerWorkModeHVQueryRes.CMD, new TrackerWorkModeHVQueryRes());
        //工作模式设置
        PACKET_FACTORY.put(TrackerWorkModeHVSetRes.CMD, new TrackerWorkModeHVSetRes());
        //工作模式推送
        PACKET_FACTORY.put(TrackerWorkModeHVPush.CMD, new TrackerWorkModeHVPush());
        //静音时段设置
        PACKET_FACTORY.put(TrackerMuteRangeSetRes.CMD, new TrackerMuteRangeSetRes());
        //静音时段查询
        PACKET_FACTORY.put(TrackerMuteRangeQueryRes.CMD, new TrackerMuteRangeQueryRes());
        //静音时段推送
        PACKET_FACTORY.put(TrackerMuteRangePush.CMD, new TrackerMuteRangePush());
        //静默时段设置
        PACKET_FACTORY.put(TrackerForbidRangeSetRes.CMD, new TrackerForbidRangeSetRes());
        //静默时段查询
        PACKET_FACTORY.put(TrackerForbidRangeQueryRes.CMD, new TrackerForbidRangeQueryRes());
        //静黙时段推送
        PACKET_FACTORY.put(TrackerForbidRangePush.CMD, new TrackerForbidRangePush());
        //响铃模式设置
        PACKET_FACTORY.put(TrackerRingModeSetRes.CMD, new TrackerRingModeSetRes());
        //响铃模式推送
        PACKET_FACTORY.put(TrackerRingModePush.CMD, new TrackerRingModePush());
        //计步数据获取
        PACKET_FACTORY.put(TrackerStepGetRes.CMD, new TrackerStepGetRes());
        //计步目标获取
        PACKET_FACTORY.put(TrackerStepTargetGetRes.CMD, new TrackerStepTargetGetRes());
        //计步目标设置
        PACKET_FACTORY.put(TrackerStepTargetSetRes.CMD, new TrackerStepTargetSetRes());
        //闹钟查询
        PACKET_FACTORY.put(TrackerAlarmQueryRes.CMD, new TrackerAlarmQueryRes());
        //闹钟设置
        PACKET_FACTORY.put(TrackerAlarmSetRes.CMD, new TrackerAlarmSetRes());
        //闹钟推送
        PACKET_FACTORY.put(TrackerAlarmPush.CMD, new TrackerAlarmPush());
        //短信上报
        PACKET_FACTORY.put(TrackerSMSLocSetRes.CMD, new TrackerSMSLocSetRes());
        //定时开关机查询
        PACKET_FACTORY.put(TrackerPowerOnOffQueryRes.CMD, new TrackerPowerOnOffQueryRes());
        //定时开关机设置
        PACKET_FACTORY.put(TrackerPowerOnOffSetRes.CMD, new TrackerPowerOnOffSetRes());
        //定时开关机推送
        PACKET_FACTORY.put(TrackerPowerOnOffPush.CMD, new TrackerPowerOnOffPush());
        //自动接听模式设置
        PACKET_FACTORY.put(TrackerAutoAnswerSetRes.CMD, new TrackerAutoAnswerSetRes());
        //自动接听模式推送
        PACKET_FACTORY.put(TrackerAutoAnswerPush.CMD, new TrackerAutoAnswerPush());
        //白名单开始设置
        PACKET_FACTORY.put(TrackerWhiteListSetRes.CMD, new TrackerWhiteListSetRes());
        //白名单开始推送
        PACKET_FACTORY.put(TrackerWhiteListPush.CMD, new TrackerWhiteListPush());
        //时间校准
        PACKET_FACTORY.put(TrackerDateTimeSetRes.CMD, new TrackerDateTimeSetRes());
        //时间格式设置
        PACKET_FACTORY.put(TrackerTimeModeSetRes.CMD, new TrackerTimeModeSetRes());
        //话费查询
        PACKET_FACTORY.put(TrackerPhoneFeeQueryRes.CMD, new TrackerPhoneFeeQueryRes());
        //话费查询结果推送
        PACKET_FACTORY.put(TrackerPhoneFeePush.CMD, new TrackerPhoneFeePush());
        //WIFI设置
        PACKET_FACTORY.put(TrackerWifiSetRes.CMD, new TrackerWifiSetRes());
        //检索城市列表
        PACKET_FACTORY.put(TrackerCityListQueryRes.CMD, new TrackerCityListQueryRes());
        //设置城市
        PACKET_FACTORY.put(TrackerCitySetRes.CMD, new TrackerCitySetRes());
        //获取设置城市
        PACKET_FACTORY.put(TrackerCityQueryRes.CMD, new TrackerCityQueryRes());
        //获取心愿
        PACKET_FACTORY.put(TrackerWishesGetReq.CMD, new TrackerWishesGetReq());
        //心愿推送
        PACKET_FACTORY.put(TrackerWishesPush.CMD, new TrackerWishesPush());
        //开启游戏
        PACKET_FACTORY.put(TrackerGameStartRes.CMD, new TrackerGameStartRes());
        //游戏状态推送
        PACKET_FACTORY.put(TrackerGamePush.CMD, new TrackerGamePush());
        //修改密码
        PACKET_FACTORY.put(ChangePasswordRes.CMD, new ChangePasswordRes());
        //CMD指令
        PACKET_FACTORY.put(TrackerCMDRes.CMD, new TrackerCMDRes());

        //手机功能接口
        //获取吐槽信息
        PACKET_FACTORY.put(TauntQueryRes.CMD, new TauntQueryRes());
        //设置手机应用使用时间,是否卸载
        PACKET_FACTORY.put(PhoneAppSettingSetRes.CMD, new PhoneAppSettingSetRes());
        //设置手机应用使用时间,是否卸载(推送)
        PACKET_FACTORY.put(PhoneAppListPush.CMD, new PhoneAppListPush());
        //手机设置
        PACKET_FACTORY.put(PhoneSettingSetRes.CMD, new PhoneSettingSetRes());
        //手机设置推送
        PACKET_FACTORY.put(PhoneSettingPush.CMD, new PhoneSettingPush());
        //获取手机第三方应用列表
        PACKET_FACTORY.put(PhoneAppListQueryRes.CMD, new PhoneAppListQueryRes());
        //获取手机某一天应用使用情况
        PACKET_FACTORY.put(PhoneUsageDetailDataGetRes.CMD, new PhoneUsageDetailDataGetRes());
        //获取手机使用情况
        PACKET_FACTORY.put(PhoneUsageDataGetRes.CMD, new PhoneUsageDataGetRes());
        //设置好习惯
        PACKET_FACTORY.put(HobbySetRes.CMD, new HobbySetRes());
        //推送好习惯
        PACKET_FACTORY.put(HobbyPush.CMD, new HobbyPush());
        //获取好习惯
        PACKET_FACTORY.put(HobbyQueryRes.CMD, new HobbyQueryRes());
        //获取手机设置信息
        PACKET_FACTORY.put(PhoneSettingQueryRes.CMD, new PhoneSettingQueryRes());
        //推送手机最新消息
        PACKET_FACTORY.put(PhoneAlertMessage.CMD, new PhoneAlertMessage());

        PACKET_FACTORY.put(SendStorySetRes.CMD,new SendStorySetRes());

        PACKET_FACTORY.put(MessagePushRes.CMD,new MessagePushRes());
    }
}
