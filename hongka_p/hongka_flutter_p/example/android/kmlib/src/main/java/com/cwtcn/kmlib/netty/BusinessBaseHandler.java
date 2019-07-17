package com.cwtcn.kmlib.netty;

import android.content.Context;
import android.util.Log;


import com.cwtcn.kmlib.protocol.IMSendEndReq;
import com.cwtcn.kmlib.protocol.IMSendReq;
import com.cwtcn.kmlib.protocol.Packet;
import com.cwtcn.kmlib.protocol.WearerAvatarGetRes;
import com.cwtcn.kmlib.util.Constants;
import com.cwtcn.kmlib.util.MyLog;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class BusinessBaseHandler extends SimpleChannelInboundHandler<byte[]> {

	public static final String TAG = "BusinessBaseHandler";

	private Context mContext;

	private ChannelHandlerContextListener mListener;
	
	public BusinessBaseHandler(ChannelHandlerContextListener listener) {
		mListener = listener;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, byte[] data) throws Exception {
		String obj = new String(data);
		if (obj != null) {
			try {
				int index = obj.indexOf(Constants.DELIMITER_AND);
				if(index != -1) {
					String[] sa = obj.split("[&]");
					String cmd = obj.substring(0, index);
					Packet p = NettyClientManager.PACKET_FACTORY.get(cmd);
					if (p != null) {
						p.cmd = sa[0];
						p.decodeArgs(sa, 1, sa.length);
						// 传的是语音字段
						// SAU&%s&%s&
						if (p.cmd.equals(IMSendReq.CMD)) {
							int index1 = (sa[0] + sa[1] + sa[2]).length() + 3;//3是“&”符的长度
							byte[] tempVoice = new byte[data.length - index1];
							System.arraycopy(data, index1, tempVoice, 0, tempVoice.length);
							p.payload = tempVoice;
							// R_G_WEARER_AVATAR&0&20140825121212&{"dataLength":102400,"filename":"123.jpg","lut":"20140505101010"}&10010.....
						} else if (p.cmd.equals(WearerAvatarGetRes.CMD)) {
							if (NettyClientManager.STR_CODE_OK.equals(sa[1])) {
								int index1 = (sa[0] + sa[1] + sa[2] + sa[3]).length() + 4;//4是“&”符的长度
								if(data.length > index) {
									byte[] tempData = new byte[data.length - index];
									System.arraycopy(data, index, tempData, 0, tempData.length);
									p.payload = tempData;
								}
							}
						} else {
							p.payload = new byte[0];
						}
						Packet res = p.respond(NettyClientManager.getInstance());
						if (NettyClientManager.NULL_PACKET != res) {
							Log.e(TAG, "添加进包队列");
							NettyClientManager.getInstance().outQ.put(res);
						}
					} else {
						p = NettyClientManager.NULL_PACKET;
						MyLog.d("TAG", sa[0] + ",命令不存在，不处理此命令...");
					}

				}
				MyLog.i(getClass().getName(), "channelRead:" + obj.toString());
			} catch (Exception e) {
				MyLog.d("TAG", "channelRead:======================\n" + obj);
			}
		}
//		if(arg0.pipeline().get("listener") == null) {
//			arg0.pipeline().addLast("listener",new NettyDecoder());
//		}
	}



	/**
	 * 此方法会在连接到服务器后被调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext chx) throws Exception {
		// 激活后发送设备注册请求
		mListener.onChannelHandlerContextSet(chx);
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)	throws Exception {
		super.exceptionCaught(ctx, cause);
		try {
			MyLog.i(TAG, "exceptionCaught:" + cause.toString());
		} catch (Exception e) {
			MyLog.i(TAG, "exceptionCaught:======================\n" + cause);
		}
	}
	
	@Override
	public void userEventTriggered(final ChannelHandlerContext ctx, Object evt) {
		if (evt instanceof IdleStateEvent) {
			//服务器没回数据
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE){
				MyLog.d(TAG, "收到数据");
			}else if (event.state() == IdleState.WRITER_IDLE){
				MyLog.d(TAG, "发送数据");
			}else if (event.state() == IdleState.ALL_IDLE){
				MyLog.d(TAG, "netty 心跳");
			}
		}
	}


	public interface ChannelHandlerContextListener {
		void onChannelHandlerContextSet(ChannelHandlerContext c);
	}
}
