package com.tuanche.askforanswer.source.ui.fragment;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tuanche.api.core.InitViews;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.utils.RotateShowProgressDialog;
import com.tuanche.api.widget.pulltorefresh.library.PullToRefreshBase;
import com.tuanche.api.widget.pulltorefresh.library.PullToRefreshBase.Mode;
import com.tuanche.api.widget.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.tuanche.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.core.Session;
import com.tuanche.askforanswer.app.utils.MySharedPreferences;
import com.tuanche.askforanswer.app.utils.Utils;
import com.tuanche.askforanswer.source.adapter.AskKindsAdapter;
import com.tuanche.askforanswer.source.adapter.AskWaitAnwserAdapter;
import com.tuanche.askforanswer.source.bean.AuditStatusBean;
import com.tuanche.askforanswer.source.bean.BeanIsAudited;
import com.tuanche.askforanswer.source.bean.HomeListInfo;
import com.tuanche.askforanswer.source.bean.HomeListInfoBean.HomeInfoQuestionBean;
import com.tuanche.askforanswer.source.bean.UserBean;
import com.tuanche.askforanswer.source.dialog.IntegralExchangeDialog;
import com.tuanche.askforanswer.source.ui.AskInfoActivity;
import com.tuanche.askforanswer.source.ui.HomeActivity;
import com.tuanche.askforanswer.source.ui.PerfectDataActivity;
import com.tuanche.askforanswer.source.view.DynamicBox;
import com.umeng.analytics.MobclickAgent;

import org.w3c.dom.Text;

/*
 * 等待回答fragment
 */
public class FragmentMainAnSwer extends BaseFragment implements ApiRequestListener, InitViews, OnClickListener {
	private FragmentMainAnSwer frMainAnSwer = null;
	private PullToRefreshListView pulllistview;
	private View view;
	private View headerView;
	private AskWaitAnwserAdapter adapter;
	private AskKindsAdapter kindsAdapter;
	private GridView gridView;
	private HomeListInfo info;
	private TextView reply_num;
	private List<HomeInfoQuestionBean> asks;
	private String tag = null;
	private boolean isTag;//点击的是6个item标签
	private RelativeLayout rl_audit_dialog;
	private TextView tv_audit_content;
	private Button btn_auditGuide;
	private AuditStatusBean statusBean;
	private boolean isNeedUpdataStatus;//是否需要更新用户的状态
	private String audit_desc;

	public FragmentMainAnSwer newInstance(Bundle bundle) {
		if (frMainAnSwer == null) {
			frMainAnSwer = new FragmentMainAnSwer();
		}
		frMainAnSwer.setArguments(bundle);
		return frMainAnSwer;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LogUtils.e("onCreate------------------");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogUtils.e("onCreateView------------------");
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_home_zhu, null);
			getViews();
			setViews();
			setListeners();

		} else {
			ViewGroup p = (ViewGroup) view.getParent();
			if (p != null) {
				p.removeAllViewsInLayout();
			}
		}
		if (isFirst) {
			box.showLoadingLayout();
			firstload();
		}
		/*if (status != 0) {// 0 ，代表是第一次进入app，一定是未审核状态
			AppApi.getAuditStatus(context, userId, "", this);
		} else {
			Session.get(context).saveKey(Config.STATUS_BEAN, "");
			fillAuditStatusForView(status);
		}*/

		return view;
	}
	@Override
	public void getViews() {
		pulllistview = (PullToRefreshListView) view.findViewById(R.id.pulllistview);
		headerView = View.inflate(getActivity(), R.layout.wait_answer_headerview, null);
		gridView = (GridView) headerView.findViewById(R.id.grid_ask_kind);
		reply_num = (TextView) headerView.findViewById(R.id.reply_number);
		box = new DynamicBox(context, pulllistview);

		rl_audit_dialog = (RelativeLayout) headerView.findViewById(R.id.rl_audio_dialog);
		tv_audit_content = (TextView) headerView.findViewById(R.id.tv_auditContent);
		btn_auditGuide = (Button) headerView.findViewById(R.id.btn_auditGuide);
	}

	@Override
	public void setViews() {
		pulllistview.getRefreshableView().setSelector(R.drawable.item_list_button);
		reply_num.setText(String.format(getActivity().getResources().getString(R.string.item_people_ask), "0"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setListeners() {

		pulllistview.setOnRefreshListener(new OnRefreshListener2() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				ispulldown = true;
				firstload();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				ispullup = true;
				pullUpload(pageNum);
			}
		});

		// 条目设置监听
		pulllistview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				LogUtils.e(arg2 + "");
				Intent intent = new Intent(getActivity(), AskInfoActivity.class);
				intent.putExtra(Config.HOMETODETIAL, asks.get(arg2 - 2));
				getActivity().startActivity(intent);
			}

		});

		// 六个问题分类监听
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				kindsAdapter.updateView(arg2);
				pageNum = 1;
				if (kindsAdapter.lastindex == 100) {
					tag = "";
					RotateShowProgressDialog.ShowProgressOn(context, false);
					isTag = true;
					new AppApi().getHomeListInfo(getActivity(), userId, 1, pageNum, pageSize, "", "", FragmentMainAnSwer.this);

				} else {
					tag = arg2 + 1 + "";
					RotateShowProgressDialog.ShowProgressOn(context, false);
					isTag = true;
					new AppApi().getHomeListInfo(getActivity(), userId, 1, pageNum, pageSize, arg2 + 1 + "", "", FragmentMainAnSwer.this);
				}
			}
		});

		box.setClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				isFirst = true;
				showLoading();
				firstload();
			}
		});

	}

	@Override
	public void onSuccess(Action method, Object obj) {
		switch (method) {
		case HOME_NOSIGN:
			if (obj instanceof HomeListInfo) {
				info = (HomeListInfo) obj;
				/*if(info.getResult().getCueWords()!=null){
					System.out.println ("积分兑换规则："+info.getResult().getCueWords().toString());
				}*/

				fillDateForListView();
				if (isFirst) {
					isFirst = false;
					box.hideAll();
				}
				if(isTag){
					isTag=false;
					RotateShowProgressDialog.ShowProgressOff();
				}
			}
			break;
			case AUDIT_NOSIGN:
				if (obj instanceof AuditStatusBean) {
					Gson gson = new Gson();
					statusBean = (AuditStatusBean) obj;
					status = statusBean.getResult().getStatus();
					audit_desc = statusBean.getResult().getMark();
					Session.get(context).saveKey(Config.STATUS_BEAN, gson.toJson(statusBean));
					String json =Session.get(context).loadKey(Config.USER_BEAN, "");
					if (!"".equals(json)) {
						UserBean bean = gson.fromJson(json, UserBean.class);
						if (null != bean.getUserIplementInfo()) {
							bean.getUserIplementInfo().setStatus(statusBean.getResult().getStatus());
						}
						Session.get(context).saveKey(Config.USER_BEAN, gson.toJson(bean));
					}
					fillAuditStatusForView(statusBean.getResult().getStatus(),audit_desc);
				}
				break;
		default:
			break;
		}
	}

	@Override
	public void onError(Action method, Object statusCode) {
		switch (method) {
		case NETWORK_FAILED:
			if (AppApi.ERROR_NETWORK_FAILED.equals(statusCode)) {
				showToast(R.string.net_no);
				// 断网了
				if (isFirst) {
					box.showInternetOffLayout();
				}
				if (ispulldown) {
					box.showInternetOffLayout();
				}
				if(isTag){
					isTag=false;
					RotateShowProgressDialog.ShowProgressOff();
				}
			}

			break;
		case HOME_NOSIGN:
			if (statusCode instanceof ResponseErrorMessage) {
				// 后台返回了错误码和错误信息
				showToast(((ResponseErrorMessage) statusCode).getMsg());
			} else if (statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode)) {
				// 网络超时
				showToast(R.string.net_timeout);
				/*
				 * 报异常
				 */
				if (isFirst) {
					box.showExceptionLayout();
				}

				if (ispulldown) {
					box.showExceptionLayout();
				}
			}
			if(isTag){
				isTag=false;
				RotateShowProgressDialog.ShowProgressOff();
			}
			if (pulllistview.isRefreshing()) {
				pulllistview.onRefreshComplete();
			}
			break;
		case AUDIT_NOSIGN:
				tv_audit_content.setText("您还没有签约技师，签约了才能问答问题");
				btn_auditGuide.setVisibility(View.VISIBLE);
				btn_auditGuide.setText("签约技师");
				btn_auditGuide.setTag(0);
				btn_auditGuide.setOnClickListener(FragmentMainAnSwer.this);
			if (0 != Utils.getUserStatus(context)) {
				Utils.setUserStatus(context, 0);
			}
				break;
		default:
			break;
		}
	}


	/*
	 * 数据填充listview
	 */
	public void fillDateForListView() {
		pulllistview.onRefreshComplete();

		if(info.getResult().getPageNum()==info.getResult().getCurrentPage()){
			pulllistview.setMode(Mode.PULL_FROM_START);
		}else{
			pulllistview.setMode(Mode.BOTH);
		}
		setHomePoint();
		ifShowIntegralExchangeDialog();
		if (null == adapter || null == kindsAdapter) {
			asks = info.getResult().getList();
			adapter = new AskWaitAnwserAdapter(getActivity(), asks);
			kindsAdapter = new AskKindsAdapter(getActivity(), info.getResult().getTitle_tag(), gridView);
			pulllistview.getRefreshableView().addHeaderView(headerView);
			gridView.setAdapter(kindsAdapter);
			adapter.setLables(info.getResult().getTitle_tag());
			pulllistview.setAdapter(adapter);
			reply_num.setText(String.format(getActivity().getResources().getString(R.string.item_people_ask), info.getResult().getQuestionCount()
					+ ""));
			// 第一次的加载和下拉后pageNumber都变2
			pageNum = 2;
		} else {
			if (ispulldown) {
				pageNum = 2;
				asks.clear();
				ispulldown = false;
				asks = info.getResult().getList();
			} else if (ispullup) {
				pageNum++;
				asks.addAll(info.getResult().getList());
				ispullup = false;
			} else {
				pageNum = 2;
				asks.clear();
				asks = info.getResult().getList();

			}

			adapter.setAsks(asks);
			adapter.notifyDataSetChanged();
			reply_num.setText(String.format(getActivity().getResources().getString(R.string.item_people_ask), info.getResult().getQuestionCount()
					+ ""));
			pulllistview.onRefreshComplete();
		}
	}

	/**
	 * 是否展示积分兑换规则对话框
	 */
	private void ifShowIntegralExchangeDialog() {
		if(null!=info.getResult().getCueWords()){
			if(Session.get(context).getInt("currentVersion", 0)==0 || Integer.parseInt(info.getResult().getCueWords().getVersion())>Session.get(context).getInt("currentVersion", 0)){
				IntegralExchangeDialog dialog=new IntegralExchangeDialog(context,info.getResult().getCueWords().getContext(),info.getResult().getCueWords().getUrl(),info.getResult().getCueWords().getTitle());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.show();
				//MySharedPreferences.putInt(context, "currentVersion", Integer.parseInt(info.getResult().getCueWords().getVersion()));
				Session.get(context).putInt("currentVersion",Integer.parseInt(info.getResult().getCueWords().getVersion()));
			}
		}




	}

	private void setHomePoint() {
		 ((HomeActivity) getActivity()).setViewAfterNet(info);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void firstload() {
		new AppApi().getHomeListInfo(getActivity(), userId, 1, 1, pageSize, tag, "", FragmentMainAnSwer.this);
		if(MySharedPreferences.getBoolean(context,"isNeedUpdataStatus",false)==true){
			rl_audit_dialog.setVisibility(View.VISIBLE);
			tv_audit_content.setText("你提交的认证申请还在审核中，审核通过了就可以回答问题了");
			btn_auditGuide.setVisibility(View.GONE);
			MySharedPreferences.putBoolean(context,"isNeedUpdataStatus",false);
		}else{
			if (status != 0) {// 0 ，代表是第一次进入app，一定是未审核状态
				AppApi.getAuditStatus(context, userId, "", this);
			} else {
				Session.get(context).saveKey(Config.STATUS_BEAN, "");
				fillAuditStatusForView(status,audit_desc);
			}
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void pullUpload(int index) {
		new AppApi().getHomeListInfo(getActivity(), userId, 1, pageNum, pageSize, tag, "", FragmentMainAnSwer.this);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	    MobclickAgent.onPageStart("MainAnSwer"); //统计页面
		MobclickAgent.onEvent(context, "questionlist_typewait_click");

	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("MainAnSwer");
		
	}
	/**
	 * 根据status的值，展示不同的view
	 * @param status
	 */
	private void fillAuditStatusForView(int status,String audit_desc) {
		BeanIsAudited beanIsAudited;
		switch(status){
			case 1://正在审核中
				//diAuditNo = new DialogAuditNo(mContext, DialogListener.DialogEnum.audit_ing);
				rl_audit_dialog.setVisibility(View.VISIBLE);
				tv_audit_content.setText("你提交的认证申请还在审核中，审核通过了就可以回答问题了");
				btn_auditGuide.setVisibility(View.GONE);
				break;
			case 2://审核已通过
				if ("".equals(Session.get(context).loadKey(Config.ISPASS, ""))) {
					beanIsAudited = new BeanIsAudited();
					beanIsAudited.setAudited(false);//true审核通过的对话框展示过一次，false 没有展示过，状态改变需要提示用户
					beanIsAudited.setUserId(userId);
				} else {
					beanIsAudited = new Gson().fromJson(Session.get(context).loadKey(Config.ISPASS, ""), BeanIsAudited.class);
				}
				/*if (userId == beanIsAudited.getUserId() && beanIsAudited.isAudited() == false) {
					diAuditNo = new DialogAuditNo(context, DialogListener.DialogEnum.audit_ed);
				} else if (userId != beanIsAudited.getUserId()) {
					diAuditNo = new DialogAuditNo(mContext, DialogListener.DialogEnum.audit_ed);
				}*/
				rl_audit_dialog.setVisibility(View.GONE);
				break;
			case 3://审核拒绝
				rl_audit_dialog.setVisibility(View.VISIBLE);
				if ("".equals(Session.get(context).loadKey(Config.ISPASS, ""))) {
					beanIsAudited = new BeanIsAudited();
					beanIsAudited.setAudited(false);
					beanIsAudited.setUserId(userId);
				} else {
					beanIsAudited = new Gson().fromJson(Session.get(context).loadKey(Config.ISPASS, ""), BeanIsAudited.class);
				}
				beanIsAudited.setAudited(false);
				Session.get(context).saveKey(Config.ISPASS, new Gson().toJson(beanIsAudited));
				//diAuditNo = new DialogAuditNo(mContext, DialogListener.DialogEnum.audit_refused);
				if(!TextUtils.isEmpty(audit_desc)){
					tv_audit_content.setText("认证信息审核拒绝："+audit_desc);
					btn_auditGuide.setVisibility(View.VISIBLE);
					btn_auditGuide.setText("重新提交");
					btn_auditGuide.setOnClickListener(FragmentMainAnSwer.this);
					btn_auditGuide.setTag(status);
				}

				break;
			default:
				//diAuditNo = new DialogAuditNo(mContext, DialogListener.DialogEnum.audit_no);
				rl_audit_dialog.setVisibility(View.VISIBLE);
				tv_audit_content.setText("您还没有签约技师，签约了才能问答问题");
				btn_auditGuide.setVisibility(View.VISIBLE);
				btn_auditGuide.setText("签约技师");
				btn_auditGuide.setTag(status);
				btn_auditGuide.setOnClickListener(FragmentMainAnSwer.this);
				LogUtils.e("未认证");
				break;
		}
	}

	@Override
	public void onClick(View view) {
	switch ((Integer)view.getTag()){
		case 0:
			gotoNextActivity();
			break;
		case 3:
			gotoNextActivity();
			break;
	}
	}
	public void gotoNextActivity(){
		Intent intent = new Intent(context, PerfectDataActivity.class);
		intent.putExtra("fromHome", true);
		context.startActivity(intent);
	}

	/**
	 * 来自注册页面home页的审核状态需要改变
	 */
	public void fromPerfectDataActivity(){
		MySharedPreferences.putBoolean(context,"isNeedUpdataStatus",true);
	}
}
