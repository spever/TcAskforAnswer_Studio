package com.tuanche.askforanswer.source.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.source.PopListener.ActivityChangFragmenListener;
import com.tuanche.askforanswer.source.bean.UserBean;
import com.tuanche.askforanswer.source.ui.fragment.FragmentMsgMy;
import com.tuanche.askforanswer.source.ui.fragment.FragmentMsgSys;
import com.tuanche.askforanswer.source.view.PagerSlidingTabStripPoint;
import com.umeng.analytics.MobclickAgent;

/**
 * 我的消息的acitivity
 */

public class AskMsgActivity extends BaseFragmentActivity implements OnClickListener, ApiRequestListener {
	private ImageView back;
	private ViewPager viewPager;
	private MsgPagerAdapter adapter;
	private PagerSlidingTabStripPoint tabs;
	private ActivityChangFragmenListener listener;
	private String msgType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_msg);
		Bundle bundle=getIntent().getExtras();
		if(null!=bundle){
			msgType=bundle.getString(Config.MSGTYPE);
		}
		getViews();
		setViews();
		setListeners();
	}

	@Override
	public void getViews() {
		viewPager = (ViewPager) findViewById(R.id.vPager);
		back = (ImageView) findViewById(R.id.top_back);
		tabs = (PagerSlidingTabStripPoint) findViewById(R.id.tabs);

	}

	@Override
	public void setViews() {
		adapter = new MsgPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		tabs.setViewPager(viewPager);
		tabs.setPointHide(1);
		//viewPager.setCurrentItem(1);

		if ("300".equals(msgType)) {
			viewPager.setCurrentItem(1);
		}else{
		}
	}

	@Override
	public void setListeners() {
		back.setOnClickListener(this);
		// tabs.setOnPageChangeListener(this);
	}

	@Override
	public void onSuccess(Action method, Object obj) {

	}

	@Override
	public void onError(Action method, Object statusCode) {
		switch (method) {
		case NETWORK_FAILED:
			if (AppApi.ERROR_NETWORK_FAILED.equals(statusCode)) {
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_no));
			}
			break;
		case ASKMSG_NOSIGN:
			if (statusCode instanceof ResponseErrorMessage) {
				// 后台返回了错误码和错误信息
				ToastUtil.showToast(mContext, ((ResponseErrorMessage) statusCode).getMsg());
			} else if (statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode)) {
				// 网络超时
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_timeout));
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.top_back:
			if("300".equals(msgType)){
				String json = mSession.loadKey(Config.USER_BEAN, "");
				if (!"".equals(json)) {
					UserBean bean = new Gson().fromJson(json, UserBean.class);
					if (null != bean.getUserIplementInfo()) {
						bean.getUserIplementInfo().setStatus(100);
					}
					mSession.saveKey(Config.USER_BEAN, new Gson().toJson(bean));
				}
				openActivity(HomeActivity.class);
			}
			this.finish();
			break;

		default:
			break;
		}
	}

	public class MsgPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { getResources().getString(R.string.top_msg_my), getResources().getString(R.string.top_msg_system) };

		public MsgPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			if (position == 0) {
				fragment = new FragmentMsgMy().newInstance(new Bundle());
			} else {
				fragment = new FragmentMsgSys().newInstance(new Bundle());
			}

			return fragment;
		}

	}

	/*
	 * fragment 调用显示通知
	 */
	public void setPageTabPointHide(int index) {
		tabs.setPointHide(index);
	}

	public void setPageTabPointShow(int index) {
		tabs.setPointShow(index);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			//listener.change();
			break;

		default:
			break;
		}
	}

	/**
	 * activity 控制fragment的回调
	 * 
	 * @return
	 */
	public ActivityChangFragmenListener getListener() {
		return listener;
	}

	public void setListener(ActivityChangFragmenListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onResume() {
		super.onResume();
		 MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onResume(this);
	}

}
