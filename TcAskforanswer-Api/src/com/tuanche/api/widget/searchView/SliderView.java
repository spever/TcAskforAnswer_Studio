package com.tuanche.api.widget.searchView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tuanche.api.R;

/**
 * 字母导航条
 * @author hezd
 *
 */
public class SliderView extends View {
	private OnItemClickListener mOnItemClickListener;
	public static String[] b = { "#", "$","*","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
			"Y", "Z" };
	int choose = -1;
	Paint paint = new Paint();
	boolean showBkg = false;
	private PopupWindow mPopupWindow;
	private TextView mPopupText;
	private int mCharHeight = 15;
	private boolean isShowPop = true;

	public SliderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mCharHeight = context.getResources().getDimensionPixelSize(R.dimen.blade_view_text_size);
	}

	public SliderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mCharHeight = context.getResources().getDimensionPixelSize(R.dimen.blade_view_text_size);
	}

	public SliderView(Context context) {
		super(context);
		mCharHeight = context.getResources().getDimensionPixelSize(R.dimen.blade_view_text_size);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		if (showBkg) {
//			canvas.drawColor(Color.parseColor("#4d000000"));
//		} else {
//			canvas.drawColor(Color.parseColor("#00000000"));
//		}
		canvas.drawColor(Color.TRANSPARENT);
		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / b.length;
		for (int i = 0; i < b.length; i++) {
			paint.setColor(Color.GRAY);
//			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setTextSize(mCharHeight);
			paint.setFakeBoldText(true);
			paint.setAntiAlias(true);
			if (i == choose) {
				paint.setColor(Color.parseColor("#3399ff"));
			}
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();
		}

	}

	@Override
	protected Parcelable onSaveInstanceState() {
		dismissPopup();
		return super.onSaveInstanceState();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final int c = (int) (y / getHeight() * b.length);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != c) {
				if (c >=0 && c < b.length) {
					performItemClicked(c);
					choose = c;
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c) {
				if (c >= 0 && c < b.length) {
					performItemClicked(c);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			dismissPopup();
			invalidate();
			break;
		}
		return true;
	}

	private void showPopup(int item) {
		if(!isShowPop ) 
			return;
		if (mPopupWindow == null) {
			mPopupText = new TextView(getContext());
			mPopupText
					.setBackgroundResource(R.color.pop_text_color);
			android.view.ViewGroup.LayoutParams layoutParams = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
			mPopupText.setLayoutParams(layoutParams);
			mPopupText.setTextColor(getContext().getResources().getColor(R.color.white));
			mPopupText.setTextSize(25);
			mPopupText.setGravity(Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL);
			mPopupWindow = new PopupWindow(mPopupText,
					100, 100);
		}

//		String text = "";
//		if (item == 0) {
//			text = "#";
//		} else {
//			text = Character.toString((char) ('A' + item - 1));
//		}
		String text = b[item];
		mPopupText.setText(text);
		if (mPopupWindow.isShowing()) {
			mPopupWindow.update();
		} else {
			mPopupWindow.showAtLocation(getRootView(),
					Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
		}
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

	private void dismissPopup() {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		mOnItemClickListener = listener;
	}

	private void performItemClicked(int item) {
		if (mOnItemClickListener != null) {
			mOnItemClickListener.onItemClick(b[item]);
			showPopup(item);
		}
	}

	public interface OnItemClickListener {
		void onItemClick(String s);
	}
	
	/**
	 * 改变导航标签显示内容
	 * @param labels
	 */
	public void setDataChanged(String[] labels) {
		b = labels;
		invalidate();
	}

	public boolean isShowPop() {
		return isShowPop;
	}

	public void setShowPop(boolean isShowPop) {
		this.isShowPop = isShowPop;
	}
	
}
