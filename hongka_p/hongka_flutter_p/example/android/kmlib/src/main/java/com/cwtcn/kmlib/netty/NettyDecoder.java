package com.cwtcn.kmlib.netty;

import android.util.Log;


import com.cwtcn.kmlib.util.Constants;
import com.cwtcn.kmlib.util.MyLog;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class NettyDecoder extends ByteToMessageDecoder {

	private static final String TAG = "NettyDecoder";

	enum State {
		FIND_REQUEST_FLAG,//头部
		FIND_REQUEST_LENGTH,//长度
		READ_DATA//数据
	}
	private State state;

	private int maxAcceptDataLength;

	private int flagReadIndex = -1;
	private byte[] flagBits = new byte[Constants.PACKAGE_HEADER_FLAG.length];
	private Integer dataLength;
	private byte[] lengthBits = new byte[4];
	private int lengthBitRead = 0;
	private byte[] receiveData;

	public NettyDecoder() {
		state = State.FIND_REQUEST_FLAG;
		maxAcceptDataLength = 1024 * 1024;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		state = State.FIND_REQUEST_FLAG;
		super.channelActive(ctx);
	}


	@Override
	public void channelRead(ChannelHandlerContext arg0, Object arg1) throws Exception {
		super.channelRead(arg0, arg1);
	}


	private void nextState(State newState) {
		state = newState;
	}

	int lastReadedLenght = 0;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//协议头解析：cwt(3) + body长度(4)
		switch (state) {
			case FIND_REQUEST_FLAG:
				try {
					boolean requestFlagFound = findRequestFlag(ctx, in);
					if (requestFlagFound) {
						nextState(State.FIND_REQUEST_LENGTH);
					} else {
						MyLog.w(TAG, "收到错误指令");
						break;
					}
				} catch (Exception e) {
					MyLog.w(TAG, "解析指令出错");
					break;
				}
			case FIND_REQUEST_LENGTH:
				try {
					dataLength = findRequestDataLength(ctx, in);
					if (dataLength == null) {
						MyLog.w(TAG, "解析数据长度出错");
						return;
					} else {
						if (dataLength <= 0) {  //心跳
							NettyClientManager.lastReceiverMessageTime = System.currentTimeMillis();
							MyLog.d(TAG, "服务器心跳");
							nextState(State.FIND_REQUEST_FLAG);
							break;
						} else {
							if (dataLength >= maxAcceptDataLength) {
								MyLog.w(TAG, "服务器返回数据长度太长，不操作");
								return;
							} else {
								receiveData = new byte[dataLength];
							}
							nextState(State.READ_DATA);
						}
					}
				} catch (Exception e) {
					nextState(State.FIND_REQUEST_FLAG);
					break;
				}
			case READ_DATA:
				NettyClientManager.lastReceiverMessageTime = System.currentTimeMillis();
				ByteBufInputStream is = new ByteBufInputStream(in);
				int readLength = in.readableBytes();
				MyLog.d(TAG, "本次可读长度==>" + readLength);
				if (lastReadedLenght == dataLength) {
					lastReadedLenght = 0;
					//String rulust = new String (receiveData);
					out.add(receiveData);
					nextState(State.FIND_REQUEST_FLAG);
				} else if (lastReadedLenght + readLength >= dataLength) {  //上次读到的大小 + 本次可读大小 >= 总长度
					int n = 0;
					while (n < dataLength - lastReadedLenght) {
						int m = is.read(receiveData, lastReadedLenght, dataLength - lastReadedLenght - n);
						if (-1 == m) {
							break;
						}
						n += m;
					}
					lastReadedLenght = 0;
					//String rulust = new String (receiveData);
					out.add(receiveData);
					nextState(State.FIND_REQUEST_FLAG);
					/*if (in.readableBytes() > 0) {
						decode(ctx, in, out);
					}*/
				} else {
					int n = 0;
					while (n < dataLength - lastReadedLenght) {
						int m = is.read(receiveData, lastReadedLenght, dataLength - lastReadedLenght - n);
						if (-1 == m) {
							break;
						}
						n += m;
					}
					lastReadedLenght += readLength;
				}
		}
	}

	public int getCount(byte b0, byte b1, byte b2, byte b3) {
		return changNum(b0) + (changNum(b1) << 8) + (changNum(b2) << 16) + (changNum(b3) << 24);
	}

	/**
	 * 将符号位的byte转化为无符号位的byte
	 *
	 * @param intValue
	 * @return
	 */
	public int changNum(byte intValue) {
		int value = 0;
		int temp = intValue % 256;
		if (intValue < 0) {
			value = (int) (temp >= -128 ? 256 + temp : temp);
		} else {
			value = (int) (temp > 127 ? temp - 256 : temp);
		}
		return value;
	}

	private boolean findRequestFlag(ChannelHandlerContext ctx, ByteBuf in) {
		boolean requestFlagFound = false;
		int capacity = 100;
		if (in.readableBytes() >= Constants.PACKAGE_HEADER_FLAG.length) {
			ByteBuf buf = null;
			byte b;
			for (int i = 0; i < Constants.PACKAGE_HEADER_FLAG.length; i++) {
				if (flagReadIndex > 0 && i < flagReadIndex) {
					b = flagBits[i];
				} else {
					b = in.readByte();
				}

				if (b != Constants.PACKAGE_HEADER_FLAG[i]) {
					flagReadIndex = 0;
					requestFlagFound = false;
					if (in.readableBytes() >= Constants.PACKAGE_HEADER_FLAG.length) {
						i = 0;
						if (buf == null) {
							buf = Unpooled.buffer(capacity);
						}
						if (buf.readableBytes() < capacity) {
							buf.writeByte(b);
						}
						continue;
					} else {
						break;
					}
				}
				requestFlagFound = true;
			}

			if (buf != null) {
				byte[] bs = new byte[buf.capacity()];
				buf.readBytes(bs);
			}
			if (requestFlagFound) {

			}
			flagReadIndex = 0;
		} else {
			flagReadIndex = 0;
			int idx = in.readableBytes();
			for (int i = 0; i < idx; i++) {
				flagBits[flagReadIndex++] = in.readByte();
			}
		}

		return requestFlagFound;
	}

	private Integer findRequestDataLength(ChannelHandlerContext ctx, ByteBuf in) {

		Integer contentLength = null;

		while (lengthBitRead < Constants.LENGTH_BITS && in.readableBytes() > 0) {
			lengthBits[lengthBitRead++] = in.readByte();
		}

		if (lengthBitRead == Constants.LENGTH_BITS) {
			contentLength = getCount(lengthBits[0], lengthBits[1], lengthBits[2], lengthBits[3]);
			//NumberUtil.makeInt(lengthBits[3], lengthBits[2], lengthBits[1], lengthBits[0]);
			lengthBitRead = 0;
		}

		return contentLength;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		try {
			Log.i(getClass().getName(), "exceptionCaught:" + cause.toString());
		} catch (Exception e) {
			System.out.println("exceptionCaught:======================\n" + cause);
		}
	}
}
