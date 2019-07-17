package com.cwtcn.kmlib.netty;

import android.text.TextUtils;

import com.cwtcn.kmlib.protocol.Packet;
import com.cwtcn.kmlib.util.MyLog;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class NettyEncode extends MessageToByteEncoder<Packet> {

	private static final String TAG = "NettyEncode";

	public NettyEncode() {

	}

	@Override
	protected void encode(ChannelHandlerContext chc, Packet packet, ByteBuf os) throws Exception {
		if (packet == null) {
			return;
		}
		if (os == null) {
			MyLog.e(TAG, os == null ? "true" : "false");
			return;
		}
		String args = null;
		synchronized (os) {
			// 写入头部 CWT
			//os.write(TITLE.getBytes());
			args = packet.encodeArgs();
			MyLog.e(TAG, "args==>" + args + "cmd==>" + packet.cmd + "payload==>" + packet.payload);
			if(packet.payload == null) {
				packet.payload = packet.NULL_BUF;
			}
			//计算长度
			byte[] argBytes = args.getBytes();
			int length = 0;
			length = argBytes.length + packet.payload.length;

			byte[] head = new byte[4];
			head[0] = int0(length);
			head[1] = int1(length);
			head[2] = int2(length);
			head[3] = int3(length);

			if (TextUtils.isEmpty(packet.cmd)) {
				//发送心跳："CWT0000"
				os.writeBytes(packet.byteMerger(packet.TITLE.getBytes(), head));
			}
			MyLog.i(TAG, "发送的命令：" + packet.TITLE + int0(length) + int1(length) + int2(length) + int3(length) + packet.cmd + args.toString() + (packet.payload.length == 0 ? "" : packet.payload.length + "个语音字节"));
			// 写入请求内容
			if (!TextUtils.isEmpty(args)) {
				//合并TITLE+长度+内容
				os.writeBytes(packet.byteMerger3(packet.TITLE.getBytes(), head, argBytes));
			}
			// 写入二进制
			if (packet.payload.length > 0) {
				os.writeBytes(packet.payload);
			}
		}
	}


	public static byte int3(int x) {
		return (byte) (x >> 24);
	}

	public static byte int2(int x) {
		return (byte) (x >> 16);
	}

	public static byte int1(int x) {
		return (byte) (x >> 8);
	}

	public static byte int0(int x) {
		return (byte) (x);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		try {
			MyLog.i(getClass().getName(), "exceptionCaught:" + cause.toString());
		} catch (Exception e) {
			MyLog.d("", "exceptionCaught:======================\n" + cause);
		}
		//网络异常，重连服务器
		MyLog.d(getClass().getName(), "网络异常，重连服务器");
		//设置连接状态-失败
		NettyClientManager.getInstance().isRunning.set(false);
		NettyClientManager.getInstance().isConnected.set(false);
		NettyClientManager.getInstance().reconnect();
	}
}
