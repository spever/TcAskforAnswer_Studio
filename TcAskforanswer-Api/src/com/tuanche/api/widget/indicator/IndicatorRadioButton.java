package com.tuanche.api.widget.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

import com.tuanche.api.R;

/**
 * 索引指示器
 * 
 * @author Wangzhen
 * 
 */
public class IndicatorRadioButton extends RadioButton {

	private Drawable buttonDrawable;

	public IndicatorRadioButton(Context context) {
		super(context);
		buttonDrawable = context.getResources().getDrawable(
				R.drawable.adv_gallery_mark_selector);
		setButtonDrawable(R.drawable.appointment_touch_area);
	}

	public IndicatorRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (buttonDrawable != null) {
			buttonDrawable.setState(getDrawableState());
			final int verticalGravity = getGravity()
					& Gravity.VERTICAL_GRAVITY_MASK;
			final int height = buttonDrawable.getIntrinsicHeight();

			int y = 0;

			switch (verticalGravity) {
			case Gravity.BOTTOM:
				y = getHeight() - height;
				break;
			case Gravity.CENTER_VERTICAL:
				y = (getHeight() - height) / 2;
				break;
			}
			int buttonWidth = buttonDrawable.getIntrinsicWidth();
			int buttonLeft = (getWidth() - buttonWidth) / 2;
			buttonDrawable.setBounds(buttonLeft, y, buttonLeft + buttonWidth, y
					+ height);
			buttonDrawable.draw(canvas);
		}
	}
}
