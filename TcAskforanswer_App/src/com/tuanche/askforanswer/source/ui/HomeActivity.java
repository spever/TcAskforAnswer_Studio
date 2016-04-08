package com.tuanche.askforanswer.source.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.core.Session;
import com.tuanche.askforanswer.app.utils.ActivitiesManager;
import com.tuanche.askforanswer.app.utils.PushAgentUtils;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.app.utils.Utils;
import com.tuanche.askforanswer.source.PopListener.DialogListener;
import com.tuanche.askforanswer.source.application.MyApp;
import com.tuanche.askforanswer.source.bean.AuditStatusBean;
import com.tuanche.askforanswer.source.bean.BeanIsAudited;
import com.tuanche.askforanswer.source.bean.HomeListInfo;
import com.tuanche.askforanswer.source.bean.LableBean;
import com.tuanche.askforanswer.source.bean.UserBean;
import com.tuanche.askforanswer.source.dialog.DialogAuditNo;
import com.tuanche.askforanswer.source.service.MyPushIntentService;
import com.tuanche.askforanswer.source.ui.fragment.FragmentAppendAnSwer;
import com.tuanche.askforanswer.source.ui.fragment.FragmentMainAnSwer;
import com.tuanche.askforanswer.source.view.PagerSlidingTabStrip;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.umeng.update.UmengUpdateAgent;

public class HomeActivity extends BaseFragmentActivity implements OnClickListener,ApiRequestListener,DialogListener, ViewPager.OnPageChangeListener {
	private MsgStateChanageReceiver receiver;
	private ViewPager viewPager;
	private ImageView my_info;
	private ImageView my_mys;
	private MyPagerAdapter adapter;
	private PushAgent mPushAgent;
	private TextView audit;
	private PagerSlidingTabStrip tabs;
	private DialogAuditNo diAuditNo;
	private AuditStatusBean statusBean; // 状态
	private int state;
	private boolean isFromPerfectDataActivity;//判断是不是来自完善信息上传资料页面
	public Fragment mFragment;
	private int index=0;
	private String audit_desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
if(getIntent().getExtras()!=null){
		isFromPerfectDataActivity =getIntent().getExtras().getBoolean("isFromPerfectDataActivity");
		if(isFromPerfectDataActivity==true && index==0){
			new FragmentMainAnSwer().fromPerfectDataActivity();
		}if (isFromPerfectDataActivity==true && null !=mFragment && index==1){
			new FragmentAppendAnSwer().fromPerfectDataActivity();
		}
}
		getViews();
		setViews();
		setListeners();
		initUM();

		/**
		 * 友盟自动更新
		 */
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		//firstShow();
	}

	/*public void firstShow() {
		if (status != 0) {// 0 ，代表是第一次进入app，一定是未审核状态
			new AppApi().getAuditStatus(mContext, userId, "", this);
		} else {
			state = status;
			mSession.saveKey(Config.STATUS_BEAN, "");
			fillSetDataForView(status);
		}
	}*/

	@Override
	public void getViews() {
		viewPager = (ViewPager) findViewById(R.id.vPager);
		my_info = (ImageView) findViewById(R.id.top_left_my);
		my_mys = (ImageView) findViewById(R.id.top_right_msg);
		//audit = (TextView) findViewById(R.id.audited);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		receiver = new MsgStateChanageReceiver();
	}

	@Override
	public void setViews() {
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		tabs.setViewPager(viewPager);
		tabs.setOnPageChangeListener(this);
		// 判断是否有消息标志
		String ifhavemsg = mSession.loadKey(Config.IFHAVENSG, "0");
		if ("1".equals(ifhavemsg)) {
			showMsgPoint();
		} else {
			hideMsgPoint();
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(MyApp.RECEIVER_ACTION);
		registerReceiver(receiver, filter);
	}

	void initUM() {
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable(MyApp.mRegisterCallback);
		mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
		String deviceToken = UmengRegistrar.getRegistrationId(mContext);
		LogUtils.e(deviceToken);
		LogUtils.e(mPushAgent.isRegistered() + "");
		PushAgentUtils.addAlias(mContext, userId + "");
	}

	@Override
	public void setListeners() {
		my_info.setOnClickListener(this);
		my_mys.setOnClickListener(this);
		//audit.setOnClickListener(this);
	}

	/**
	 * 网络返回请求
	 */
	@Override
	public void onSuccess(Action method, Object obj) {
		switch (method) {
		case AUDIT_NOSIGN:
			if (obj instanceof AuditStatusBean) {
				Gson gson = new Gson();
				statusBean = (AuditStatusBean) obj;
				state = statusBean.getResult().getStatus();
				audit_desc = statusBean.getResult().getMark();
				mSession.saveKey(Config.STATUS_BEAN, gson.toJson(statusBean));
				String json = mSession.loadKey(Config.USER_BEAN, "");
				if (!"".equals(json)) {
					UserBean bean = gson.fromJson(json, UserBean.class);
					if (null != bean.getUserIplementInfo()) {
						bean.getUserIplementInfo().setStatus(statusBean.getResult().getStatus());
					}
					mSession.saveKey(Config.USER_BEAN, gson.toJson(bean));
				}
				fillSetDataForView(statusBean.getResult().getStatus(),audit_desc);
			}
			break;
		default:
			break;
		}
	}

	private void fillSetDataForView(int status,String audit_desc) {

		if (status == 1) {
			//diAuditNo = new DialogAuditNo(mContext, DialogEnum.audit_ing);
			ToastUtil.showToast(mContext,"你提交的认证申请正在审核中，审核通过了就可以回答问题了");
		} else if (status == 2) {
			BeanIsAudited beanIsAudited;
			if ("".equals(Session.get(mContext).loadKey(Config.ISPASS, ""))) {
				beanIsAudited = new BeanIsAudited();
				beanIsAudited.setAudited(false);//判断是否已经弹出过审核通过的对话框了
				beanIsAudited.setUserId(userId);
			} else {
				beanIsAudited = new Gson().fromJson(Session.get(mContext).loadKey(Config.ISPASS, ""), BeanIsAudited.class);
			}
			/*if (userId == beanIsAudited.getUserId() && beanIsAudited.isAudited() == false) {
				diAuditNo = new DialogAuditNo(mContext, DialogEnum.audit_ed);
			} else if (userId != beanIsAudited.getUserId()) {
				diAuditNo = new DialogAuditNo(mContext, DialogEnum.audit_ed);
			}*/
			openActivity(AskMsgActivity.class);
			hideMsgPoint();
			mSession.saveKey(Config.IFHAVENSG, "0");
		} else if (status == 3) {  // 拒绝审核
			BeanIsAudited beanIsAudited;
			if ("".equals(Session.get(mContext).loadKey(Config.ISPASS, ""))) {
				beanIsAudited = new BeanIsAudited();
				beanIsAudited.setAudited(false);
				beanIsAudited.setUserId(userId);
			} else {
				beanIsAudited = new Gson().fromJson(Session.get(mContext).loadKey(Config.ISPASS, ""), BeanIsAudited.class);
			}
			beanIsAudited.setAudited(false);
			mSession.saveKey(Config.ISPASS, new Gson().toJson(beanIsAudited));
			//diAuditNo = new DialogAuditNo(mContext, DialogEnum.audit_refused);
			if(!TextUtils.isEmpty(audit_desc)){
				ToastUtil.showToast(mContext,"认证信息审核被拒绝："+audit_desc);
			}

		} else {
			//diAuditNo = new DialogAuditNo(mContext, DialogEnum.audit_no);
			LogUtils.e("未认证");
			ToastUtil.showToast(mContext, "您还没有签约技师，签约了才能回答问题");
		}
		// diAuditNo.setCanceledOnTouchOutside(true);
		/*if (null != diAuditNo) {
			diAuditNo.setListener(this);
			diAuditNo.show();
		}*/
		my_mys.setClickable(true);

	}

	public void onError(Action method, Object statusCode) {
		switch (method) {
		case NETWORK_FAILED:
			fillSetDataForView(status,audit_desc);

			if (AppApi.ERROR_NETWORK_FAILED.equals(statusCode)) {
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_no));
			}
			break;

		/*case AUDIT_NOSIGN:
			fillSetDataForView(status);
			if (statusCode instanceof ResponseErrorMessage) {
				// 后台返回了错误码和错误信息
				// ToastUtil.showToast(this, ((ResponseErrorMessage)
				// statusCode).getMsg());
			} else if (statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode)) {
				// 网络超时
				// ToastUtil.showToast(this,
				// getResources().getString(R.string.net_timeout));
			}
			break;*/
			case AUDIT_NOSIGN:
				ToastUtil.showToast(this, "您还没有签约技师，签约了才能问答问题");

				if (0 != Utils.getUserStatus(this)) {
					Utils.setUserStatus(this,0);
				}
				my_mys.setClickable(true);
				break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_left_my:
			openActivity(MineActivity.class);
			break;
		case R.id.top_right_msg:
			my_mys.setClickable(false);
			if (status != 0) {// 0 ，代表是第一次进入app，一定是未审核状态
				 AppApi.getAuditStatus(mContext, userId, "", this);
			} else {
				state = status;
				mSession.saveKey(Config.STATUS_BEAN, "");
				fillSetDataForView(status,audit_desc);
			}
			//openActivity(AskMsgActivity.class);
			//hideMsgPoint();
			//mSession.saveKey(Config.IFHAVENSG, "0");
			break;
		/*case R.id.audited:
			openActivity(PerfectDataActivity.class);
			break;*/
		default:
			break;
		}
	}

	@Override
	public void onPageScrolled(int i, float v, int i1) {

	}

	@Override
	public void onPageSelected(int i) {
	index=i;
	}

	@Override
	public void onPageScrollStateChanged(int i) {

	}


	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { getResources().getString(R.string.home_wait_answer), getResources().getString(R.string.home_append_answer) };
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
		/*	Bundle bundle=new Bundle();
			bundle.putBoolean("isNeedUpdate", true);*/
			if (position == 0) {

				mFragment = new FragmentMainAnSwer().newInstance(new Bundle());
			}
			else {
				mFragment = new FragmentAppendAnSwer().newInstance(new Bundle());
			}
			return mFragment;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// mPushAgent.disable();
		// 取消注册
		unregisterReceiver(receiver);
	}

	public void showMsgPoint() {
		my_mys.setScaleType(ScaleType.CENTER_CROP);
		my_mys.setImageResource(R.drawable.icon_mess_point);
	}

	public void hideMsgPoint() {
		my_mys.setScaleType(ScaleType.CENTER_INSIDE);
		my_mys.setImageResource(R.drawable.icon_mess);
	}

	// private boolean isdialogbutton;
	/**
	 * dialog 监听
	 */
	@Override
	public void buttomOnclickListener(DialogEnum diagEnum) {
		switch (diagEnum) {
		case audit_no:
			// isdialogbutton=true;
			diAuditNo.dismiss();
			// openActivity(PerfectDataActivity.class);
			gotoNext();

			break;
		case audit_ing:

			break;
		case audit_refused:
			// isdialogbutton=true;
			diAuditNo.dismiss();
			// openActivity(PerfectDataActivity.class);
			gotoNext();
			break;
		case audit_ed:

			break;

		default:
			break;
		}
	}

	void gotoNext() {
		Intent intent = new Intent(this, PerfectDataActivity.class);
		intent.putExtra("fromHome", true);
		startActivity(intent);

	}

	@Override
	public void topOnclickListener(DialogEnum diagEnum) {

	}

	@Override
	public void topDeleteClickListener(DialogEnum diagEnum) {

	}
	/*
	 * fragment 调用activity方法填充数据
	 */
	public void setViewAfterNet(HomeListInfo info) {
		// if (info.getResult().getMsgCount() > 0) {
		// showMsgPoint();
		// } else {
		// hideMsgPoint();
		// }
	}

	public void setLable(LableBean bean) {
		mSession.saveKey(Config.LABLES, new Gson().toJson(bean));
	}

	@Override
	public void dissMiss(DialogEnum diagEnum) {
		/*if (diagEnum != DialogEnum.audit_ed) {
			// 状态不是认证通过都调到登录页
			openActivity(OpenActivity.class);
			ActivitiesManager.getInstance().popAllActivities();
			finish();
		} else {
			BeanIsAudited auIsAudited = new BeanIsAudited();
			auIsAudited.setUserId(userId);
			auIsAudited.setAudited(true);//true审核通过的对话框展示过一次，false 没有展示过，状态改变需要提示用户
			mSession.saveKey(Config.ISPASS, new Gson().toJson(auIsAudited));
		}*/

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

	/**
	 * 有新消息发送过来的广播接收者
	 */
	class MsgStateChanageReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			showMsgPoint();
		}
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN ) {// 为什么要满足==2？？？？
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				// Toast.makeText(getApplicationContext(), "再按一次退出程序",
				// Toast.LENGTH_SHORT).show();
				ToastUtil.showToast(mContext, "再按一次退出程序");
				exitTime = System.currentTimeMillis();
			} else {
				ActivitiesManager.getInstance().popAllActivities();
				finish();
			}
		}
		return false;
	}
}
