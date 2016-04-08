package com.tuanche.api.widget.gallery;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ClickImageView extends ImageView implements OnGestureListener {

	private Context mContext;
	private GestureDetectorCompat detectorCompat;
	private boolean isBlockClick = true;

	public boolean isBlockClick() {
		return isBlockClick;
	}

	public void setBlockClick(boolean isBlockClick) {
		this.isBlockClick = isBlockClick;
	}

	public ClickImageView(Context context) {
		super(context);
		init(context);
	}

	public ClickImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ClickImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		detectorCompat = new GestureDetectorCompat(context, this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isBlockClick) {
			// 使用这种方式时，ImageView的Click事件将无法触发
			detectorCompat.onTouchEvent(event);
			return true; 
		} else {
			return super.onTouchEvent(event);
		}
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		// ShowMessage.ShowToast(mContext, "onDown");
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// ShowMessage.ShowToast(mContext, "onShowPress");
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// ShowMessage.ShowToast(mContext, "onSingleTapUp");
		if (isBlockClick) {
			if (mContext != null && mContext instanceof Activity) {
				((Activity) mContext).finish();
			}
		}
		return false;
	}
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// ShowMessage.ShowToast(mContext, "onScroll");
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// ShowMessage.ShowToast(mContext, "onLongPress");
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// ShowMessage.ShowToast(mContext, "onFling");
		return false;
	}

}
