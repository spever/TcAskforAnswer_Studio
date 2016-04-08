package com.tuanche.askforanswer.source.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuanche.askforanswer.R;

public class MsgView extends LinearLayout {
	private Context context;

	private TextView tv;
	private ImageView iv;

	public MsgView(Context context) {
		super(context);
		this.context = context;
		initView();
	}

	public MsgView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;

		initView();
	}

	public MsgView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		initView();
	}

	private void initView() {
		View.inflate(context, R.layout.textview_point, this);
		iv = (ImageView) findViewById(R.id.iv);
		tv = (TextView) findViewById(R.id.tv);
		

	}

	/*
	 * 显示红点
	 */
	public void showTopMsg() {
		if (null != iv) {
			iv.setVisibility(View.VISIBLE);
		}

	}

	/*
	 * 隐藏红点
	 */
	public void hideTopMsg() {
		if (null != iv) {
			iv.setVisibility(View.INVISIBLE);
		}

	}

	void setTvSize(int textSize) {
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
	}

	void setTvColor(int color) {
		tv.setTextColor(color);
	}

	void setTvGrivaty(int grivaty) {
		tv.setGravity(grivaty);
	}

	void setTextTitle(String text) {
		tv.setText(text);
	}

	void setAllCaps(boolean Caps) {
		tv.setAllCaps(Caps);
	}

	String getText() {
		return tv.getText().toString();
	}
}
