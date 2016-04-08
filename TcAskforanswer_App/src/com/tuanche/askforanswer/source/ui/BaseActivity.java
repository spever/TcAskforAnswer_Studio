package com.tuanche.askforanswer.source.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tuanche.api.bitmap.BitmapDisplayConfig;
import com.tuanche.api.core.InitViews;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.app.core.Session;
import com.tuanche.askforanswer.app.utils.ActivitiesManager;
import com.tuanche.askforanswer.app.utils.PictureUtils;
import com.tuanche.askforanswer.source.bean.UserBean;
import com.tuanche.askforanswer.source.view.DynamicBox;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

abstract public class BaseActivity extends Activity implements InitViews {

	protected Context mContext;
	protected Session mSession;
	protected PictureUtils pictureUtils;
	protected BitmapDisplayConfig config;
	// private PushAgent mPushAgent;
	protected int pageNum=1;
	protected int pageSize = 20;
	protected int state;
	protected boolean ispulldown = false;
	protected boolean ispullup = false;
	protected boolean isFirst = false;
	protected DynamicBox box;
	protected RelativeLayout emptyView;
	protected TextView no_content;
	/**
	 * 用户userId
	 */
	protected int useId = 0;
	protected UserBean bean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		ActivitiesManager.getInstance().pushActivity(this);
		mSession = Session.get(this);
		String json = mSession.loadKey(Config.USER_BEAN, "");
		if (!"".equals(json)) {
			bean = new Gson().fromJson(json, UserBean.class);
			useId = bean.getCrmUser().getId();
			if (null != bean.getUserIplementInfo()) {
				state = bean.getUserIplementInfo().getStatus();
			}
		}
		initBitmapUtils();
		// mSession = Session.get(getApplicationContext());
		// mPushAgent = PushAgent.getInstance(this);
		// mPushAgent.onAppStart();
		// mPushAgent.enable(MyApp.mRegisterCallback);
		emptyView = (RelativeLayout) View.inflate(this, R.layout.exception_nocontent, null);
		// emptyView.setVisibility(View.GONE);
		no_content = (TextView) emptyView.findViewById(R.id.no_content);
		PushAgent.getInstance(mContext).onAppStart();
	}

	protected void initBitmapUtils() {
		pictureUtils = PictureUtils.getInstance(this);
		config = new BitmapDisplayConfig();
		config.setLoadingDrawable(getApplicationContext().getResources().getDrawable(R.drawable.head_user));
		config.setLoadFailedDrawable(getApplicationContext().getResources().getDrawable(R.drawable.head_user));
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
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

	protected void showLoading() {
		box.showLoadingLayout();
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
