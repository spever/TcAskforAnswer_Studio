package com.tuanche.askforanswer.source.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class MyRelativeLayout extends RelativeLayout {
	private onChangeKeyBoard stateChangeKeyBoard;
	public static final byte KEYBOARD_STATE_SHOW = -3;
	public static final byte KEYBOARD_STATE_HIDE = -2;
	public static final byte KEYBOARD_STATE_INIT = -1;
	public MyRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyRelativeLayout(Context context) {
		super(context);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// super.onSizeChanged(w, h, oldw, oldh);

		if (stateChangeKeyBoard != null) {
			if (h < oldh) {
				stateChangeKeyBoard.onKeyboardStateChanged(KEYBOARD_STATE_SHOW, h);

			} else {
				stateChangeKeyBoard.onKeyboardStateChanged(KEYBOARD_STATE_HIDE, h);
			}
		}

	}

	public onChangeKeyBoard getStateChangeKeyBoard() {
		return stateChangeKeyBoard;
	}

	public void setStateChangeKeyBoard(onChangeKeyBoard stateChangeKeyBoard) {

		this.stateChangeKeyBoard = stateChangeKeyBoard;
	}

	public interface onChangeKeyBoard {
		/**
		 * 
		 * @param state软件盘的状态
		 *            -3 显示-2隐藏
		 * @param keyheight
		 *            软件盘的高度
		 */
		public void onKeyboardStateChanged(int state, int keyheight);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		super.onLayout(changed, l, t, r, b);

	}

}
