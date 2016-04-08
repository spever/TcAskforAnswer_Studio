package com.tuanche.askforanswer.source.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.tuanche.api.bitmap.BitmapDisplayConfig;
import com.tuanche.api.core.InitViews;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.Session;
import com.tuanche.askforanswer.app.utils.ActivitiesManager;
import com.tuanche.askforanswer.app.utils.PictureUtils;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.app.utils.Utils;
import com.umeng.message.PushAgent;

abstract public class BaseFragmentActivity extends FragmentActivity implements InitViews, ApiRequestListener {

	@Override
	public void onError(Action method, Object statusCode) {
		switch (method) {
		case NETWORK_FAILED:
			if (AppApi.ERROR_NETWORK_FAILED.equals(statusCode)) {
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_no));
			}
			break;
		default:
			break;
		}

	}

	protected Context mContext;
	protected Session mSession;
	protected PictureUtils pictureUtils;
	protected BitmapDisplayConfig config;
	// private PushAgent mPushAgent;
	// protected int useId=0;
	protected int userId;
	protected int status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		mSession=Session.get(mContext);
		ActivitiesManager.getInstance().pushActivity(this);
		//String json = Session.get(this).loadKey(Config.USER_BEAN, "");
		//LogUtils.e(json);
		if (null!=Utils.getUserBeann(mContext)) {
			userId = Utils.getUserId(mContext);
			if (null !=Utils.getUserBeann(mContext).getUserIplementInfo()) {
				status = Utils.getUserStatus(mContext);
			}
		}
		// useId=Integer.parseInt(mSession.loadKey(Config.USER_ID, "0"));
		// mSession = Session.get(getApplicationContext());
		// mPushAgent = PushAgent.getInstance(this);
		// mPushAgent.onAppStart();
		// mPushAgent.enable(MyApp.mRegisterCallback);
		// mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
		PushAgent.getInstance(mContext).onAppStart();
	}

	protected void initBitmapUtils() {
		pictureUtils = PictureUtils.getInstance(this);
		config = new BitmapDisplayConfig();
		config.setLoadingDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_empty));
		config.setLoadFailedDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_empty));
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
		// mPushAgent.disable();
	}

	protected void openActivity(Class<?> pClass) {
		// if (pClass == LoginActivity.class) {
		// ActivityStackUtil.clearStack();
		// }
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
