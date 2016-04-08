package com.tuanche.api.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;

public class TimerButton extends Button{
	private Handler countTimeHandler;
	private static int INTERNAL = 1;// 1 second
	private int totalTimeInMills = 60 ;//60 second
	private static final int WHAT_COUNT_DOWN = 0;
	
	private String startText = "获取验证码";
	private String stopText = "重新获取";
	
	public TimerButton(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs);
	}

	public TimerButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public TimerButton(Context context) {
		super(context);
		 init(context,null);
	}
	
	private void init(Context context,AttributeSet attrs) {
		countTimeHandler = new CountTimeHandler();
	}

	public String getStartText() {
		return startText;
	}

	public void setStartText(String startText) {
		this.startText = startText;
		setText(startText);
	}
	
	public String getStopText() {
		return stopText;
	}

	public void setStopText(String stopText) {
		this.stopText = stopText;
	}

	public long getTimeLength() {
		return totalTimeInMills;
	}

	public void setTimeLength(int timeLength) {
		this.totalTimeInMills = timeLength;
	}
	
	public void startTimer() {
		sendCountDownMsg(totalTimeInMills);
		setEnabled(false);
	}
	
    private void countDown(int restTime) {
    	sendCountDownMsg(restTime);
    }

	private void sendCountDownMsg(int restTime) {
		countTimeHandler.removeMessages(WHAT_COUNT_DOWN);
		Message msg = new Message();
		restTime -= INTERNAL ;
		msg.obj = restTime;
		msg.what = WHAT_COUNT_DOWN;
		countTimeHandler.sendMessageDelayed(msg, INTERNAL * 1000);
	}

	public void stopTimer() {
		countTimeHandler.removeMessages(WHAT_COUNT_DOWN);
		setText(stopText);
		setEnabled(true);
	}

	@SuppressLint("HandlerLeak")
	private class CountTimeHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case WHAT_COUNT_DOWN:
				int restTime = (Integer) msg.obj;
				if (restTime <= 0) {
					setText(stopText);
					setEnabled(true);
					stopTimer();
					return;
				}
				setText(restTime + "秒");
				countDown(restTime);
				break;
			default:
				break;
			}
		}
	}
}
