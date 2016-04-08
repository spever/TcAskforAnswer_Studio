package com.tuanche.askforanswer.source.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.tuanche.api.bitmap.BitmapDisplayConfig;
import com.tuanche.api.core.InitViews;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.Session;
import com.tuanche.askforanswer.app.utils.ActivitiesManager;
import com.tuanche.askforanswer.app.utils.PictureUtils;
import com.tuanche.askforanswer.app.utils.RelayouTool;
import com.tuanche.askforanswer.source.application.MyApp;

abstract public class BaseActivity2 extends Activity implements InitViews{

	protected Context mContext;
    protected Session mSession;
	protected PictureUtils pictureUtils;
	protected BitmapDisplayConfig config;
	
	@Override
	public void setContentView(int layoutResID) {
		View view = View.inflate(getApplicationContext(), layoutResID, null);
		this.setContentView(view);
	}
	
	@Override
	public void setContentView(View view) {
		RelayouTool.relayoutViewHierarchy(view, MyApp.screenWidthScale);
		super.setContentView(view);
	}
	
	@Override
	public void setContentView(View view, LayoutParams params) {
		RelayouTool.relayoutViewHierarchy(view, MyApp.screenWidthScale);
		RelayouTool.scaleLayoutParams(params, MyApp.screenWidthScale);
		super.setContentView(view, params);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		ActivitiesManager.getInstance().pushActivity(this);
		mSession = Session.get(this);
//		mSession = Session.get(getApplicationContext());
		
	}
	
	
	protected void initBitmapUtils() {
		pictureUtils = PictureUtils.getInstance(this);
		config = new BitmapDisplayConfig();
		config.setLoadingDrawable(getApplicationContext().getResources().getDrawable(
		 R.drawable.ic_empty));
		config.setLoadFailedDrawable(getApplicationContext().getResources().getDrawable(
				R.drawable.ic_empty));
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// onDestory时，出栈操作
		ActivitiesManager.getInstance().popActivity(this);
	}
	
	
	
	
	
	protected void openActivity(Class<?> pClass) {
//		if (pClass == LoginActivity.class) {
//			ActivityStackUtil.clearStack();
//		}
		openActivity(pClass, null);
	}

	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	protected void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	protected void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}
	
}
