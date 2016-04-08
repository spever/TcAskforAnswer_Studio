package com.tuanche.askforanswer.source.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.source.PopListener.PopListener;

public class ChooseOrCameraPop extends PopupWindow {
	private PopListener listener;
	private LinearLayout ll_popup;
	private Context context;
	public PopListener getListener() {
		return listener;
	}

	public void setListener(PopListener listener) {
		this.listener = listener;
	}

	public ChooseOrCameraPop(final Context mContext, View parent) {
		this.context=mContext;
		View view = View.inflate(mContext, R.layout.item_popupwindows, null);
		//view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		RelativeLayout main = (RelativeLayout) view.findViewById(R.id.main);
		//ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_photo));
		//ll_popup.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_photo));
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		ColorDrawable dw = new ColorDrawable(R.color.half_transparent);
		// 设置SelectPicPopupWindow弹出窗体的背景
		setBackgroundDrawable(dw);
		//setAnimationStyle(R.style.AnimationFade);

		setFocusable(true);
		setOutsideTouchable(true);
		setContentView(view);
		//showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		//update();
		LinearLayout bt1 = (LinearLayout) view.findViewById(R.id.item_popupwindows_camera);
		LinearLayout bt2 = (LinearLayout) view.findViewById(R.id.item_popupwindows_Photo);
		final Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_out_2);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				dismiss();
			}
		});

		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// photo();
				listener.takeCamera();
				dismiss();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Intent intent = new Intent(MyTabActivity.this,
				// TestPicActivity.class);
				// startActivity(intent);
				listener.choosePicture();
				dismiss();
			}
		});
		main.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ll_popup.startAnimation(anim);
				//dismiss();
			}
		});

	}
	
	public void setAnimation(Animation animation){
		ll_popup.setAnimation(animation);
	}
	
	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		ll_popup.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_bottom_in_photo));
		super.showAtLocation(parent, gravity, x, y);
	}
	
	
	
}
