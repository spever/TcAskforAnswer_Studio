/**
 * 
 */
package com.tuanche.askforanswer.app.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tuanche.askforanswer.R;

public class ToastUtil {
	private static  IdentyToast mToast = null;

	/**
	 * 显示toast
	 * 
	 * @param context
	 * @param text
	 */
	public static void showToast(Context context, String text) {

		if (mToast == null) {
			mToast = new IdentyToast(context, text);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public static void cancelToast() {

		if (mToast != null) {
			mToast.cancel();
		}
	}

//	public static void showToast(Context context, int resId) {
//		if (mToast == null) {
//			mToast = new identyToast(context,resId);
//		} else {
//			mToast.setText(context.getResources().getString(resId));
//			mToast.setDuration(Toast.LENGTH_LONG);
//		}
//		mToast.show();
//	}

	public static class IdentyToast extends Toast {
		private TextView textView;

		public IdentyToast(Context context, String text) {
			super(context);
			LayoutInflater inflater = LayoutInflater.from(context);
			View layout = inflater.inflate(R.layout.toast_identify, null);
			textView = (TextView) layout.findViewById(R.id.text_title);
			textView.setText(text);
			setGravity(Gravity.CENTER, 0, 0);
			setDuration(Toast.LENGTH_SHORT);
			setView(layout);
			
		}

		@Override
		public void setText(CharSequence s) {
			// TODO Auto-generated method stub
			// super.setText(s);
			textView.setText(s);
		}
		
		
	}

}
