package com.tuanche.api.widget.indicator;


import com.tuanche.api.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

public class LaShouImgIndicator extends LinearLayout{
	
	private Context mContext;
	private LinearLayout.LayoutParams layoutParams;

	public LaShouImgIndicator(Context context) {
		super(context);
		init(context);
	}
	
	public LaShouImgIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	@SuppressLint("NewApi")
	public LaShouImgIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context){
		mContext = context;
		layoutParams = new LinearLayout.LayoutParams(
				new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
		layoutParams.leftMargin = 10;
		layoutParams.rightMargin = 10;
		layoutParams.width = 18;
		layoutParams.height = 18;
	}
	
	public void setCount(int count){
		int childCount = getChildCount();
		if(childCount>0){
			removeAllViews();
		}
		for (int i = 0; i < count; i++) {
			addView(createIndicator(), layoutParams);
		}
		check(0);
	}
	
	public void check(int check){
		int childCount = getChildCount();
		if(check >= childCount){
			return;
		}
		
		for (int i = 0; i < childCount; i++) {
			getChildAt(i).setSelected(check == i);
		}
		
	}

	private void configIndicator(int count) {
		int i = getChildCount();
		if (i == count)
			return;
		if (i < count) {
			for (; i < count; i++) {
				
			}
		} else if (i > count) {
			for (i--; i >= count; i--) {
				removeViewAt(0);
			}
		}
		getChildAt(0).setSelected(true);
	}
	
	private View createIndicator() {
		ImageView imageView = new ImageView(mContext);
		imageView.setScaleType(ScaleType.FIT_XY);
		imageView.setImageResource(R.drawable.bg_home_indicator_selector);
		return imageView;
	}

}
