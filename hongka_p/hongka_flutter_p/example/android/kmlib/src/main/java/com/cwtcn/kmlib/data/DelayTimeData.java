package com.cwtcn.kmlib.data;

import com.cwtcn.kmlib.netty.IDelayChange;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/***
 * 延迟处理的Bean
 * @author Allen
 */
public class DelayTimeData implements Delayed {

	private static final int DEFAULT_TIME = 60 * 1000;
	private long delayTime;
	private String id;
	private String message;
	private String message2;
	private IDelayChange delay;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public DelayTimeData(String id) {
		super();
		this.delayTime = System.currentTimeMillis();
		this.id = id;
	}

	/**
	 * 具体的延迟处理类
	 *
	 * @param delayTime
	 * @param id
	 * @param delay
	 */
	public DelayTimeData(long delayTime, String id, String message, IDelayChange delay) {
		super();
		this.delayTime = delayTime + System.currentTimeMillis();
		this.id = id;
		this.message = message;
		this.delay = delay;
	}

	public DelayTimeData(long delayTime, String id, String message, String message2, IDelayChange delay) {
		super();
		this.delayTime = delayTime + System.currentTimeMillis();
		this.id = id;
		this.message = message;
		this.message2 = message2;
		this.delay = delay;
	}

	/**
	 * 具体的延迟处理类
	 *
	 * @param id
	 * @param delay
	 */
	public DelayTimeData(String id, IDelayChange delay) {
		this(DEFAULT_TIME, id, "", delay);
	}

	public DelayTimeData(String id, String message, IDelayChange delay) {
		this(DEFAULT_TIME, id, message, delay);
	}

	public DelayTimeData(String id, String message, String message2, IDelayChange delay) {
		this(DEFAULT_TIME, id, message, message2, delay);
	}

	@Override
	public int compareTo(Delayed another) {

		return (int) (this.getDelay(TimeUnit.MILLISECONDS) - another.getDelay(TimeUnit.MILLISECONDS));
	}

	@Override
	public long getDelay(TimeUnit unit) {

		return delayTime - System.currentTimeMillis();
	}

	public void start() {
		if (delay != null) {
			delay.endDelay();
		}
	}
}
