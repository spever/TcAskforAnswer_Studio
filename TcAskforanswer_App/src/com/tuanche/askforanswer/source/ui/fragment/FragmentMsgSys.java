package com.tuanche.askforanswer.source.ui.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

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
import com.tuanche.askforanswer.source.adapter.MsgSysAdapter;
import com.tuanche.askforanswer.source.bean.AuditStatusBean;
import com.tuanche.askforanswer.source.bean.MsgInfo;
import com.tuanche.askforanswer.source.bean.MsgInfoListBean;
import com.tuanche.askforanswer.source.bean.UserBean;
import com.tuanche.askforanswer.source.ui.AskMsgActivity;
import com.tuanche.askforanswer.source.ui.PerfectDataActivity;
import com.tuanche.askforanswer.source.ui.WebViewShowActivity;
import com.tuanche.askforanswer.source.view.DynamicBox;
import com.umeng.analytics.MobclickAgent;

/*
 * 系统消息fragment
 */
public class FragmentMsgSys extends BaseFragment implements ApiRequestListener, InitViews {
	private FragmentMsgSys frMainAnSwer = null;
	private PullToRefreshListView pulllistview;
	private View view;
	private MsgSysAdapter adapter;
	private MsgInfo info;
	private List<MsgInfoListBean> lists;
	private AuditStatusBean statusBean; // 状态
	private int state;
	public FragmentMsgSys newInstance(Bundle bundle) {
		if (frMainAnSwer == null) {
			frMainAnSwer = new FragmentMsgSys();
		}
		frMainAnSwer.setArguments(bundle);
		return frMainAnSwer;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_msg_mine, null);
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
			pulllistview.setEmptyView(emptyView);
			firstload();
		}

		return view;
	}

	@Override
	public void onSuccess(Action method, Object obj) {
		switch (method) {
		case ASKMSG_NOSIGN:
			if (obj instanceof MsgInfo) {
				info = (MsgInfo) obj;
				fillDateForListView();
				if (isFirst) {
					isFirst = false;
					box.hideAll();
				}
			}
			break;
		case AUDIT_NOSIGN:
			if (obj instanceof AuditStatusBean) {
				Gson gson = new Gson();
				statusBean = (AuditStatusBean) obj;
				state = statusBean.getResult().getStatus();
				Session.get(context).saveKey(Config.STATUS_BEAN, gson.toJson(statusBean));
				String json = Session.get(context).loadKey(Config.USER_BEAN, "");
				if (!"".equals(json)) {
					UserBean bean = gson.fromJson(json, UserBean.class);
					if (null != bean.getUserIplementInfo()) {
						bean.getUserIplementInfo().setStatus(statusBean.getResult().getStatus());
					}
					Session.get(context).saveKey(Config.USER_BEAN, gson.toJson(bean));
				}
				if(RotateShowProgressDialog.isDialogShowing())
					RotateShowProgressDialog.ShowProgressOff();
				Intent intent=new Intent(context, PerfectDataActivity.class);
				intent.putExtra("fromHome", true);
				context.startActivity(intent);
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
			}
			if (isFirst) {
				box.showInternetOffLayout();
			} else if (ispulldown) {
				box.showInternetOffLayout();
			}
			if(RotateShowProgressDialog.isDialogShowing())
				RotateShowProgressDialog.ShowProgressOff();
			break;
		case ASKMSG_NOSIGN:
			if (statusCode instanceof ResponseErrorMessage) {
				// 后台返回了错误码和错误信息
				showToast(((ResponseErrorMessage) statusCode).getMsg());
			} else if (statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode)) {
				// 网络超时
				if (isFirst) {
					box.showExceptionLayout();
				} else if (ispulldown) {
					box.showExceptionLayout();
				}
				showToast(R.string.net_timeout);
			}
			break;
		case AUDIT_NOSIGN:
			if (statusCode instanceof ResponseErrorMessage) {
				// 后台返回了错误码和错误信息
				//ToastUtil.showToast(this, ((ResponseErrorMessage) statusCode).getMsg());
			} else if (statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode)) {
				// 网络超时
				//ToastUtil.showToast(this, getResources().getString(R.string.net_timeout));
			}
			if(RotateShowProgressDialog.isDialogShowing())
			RotateShowProgressDialog.ShowProgressOff();

			break;
		default:
			break;
		}
	}

	@Override
	public void getViews() {
		pulllistview = (PullToRefreshListView) view.findViewById(R.id.pulllistview);
		no_content.setText(context.getResources().getString(R.string.nomsg));
		box = new DynamicBox(context, pulllistview);

	}

	@Override
	public void setViews() {

		pulllistview.getRefreshableView().setSelector(R.drawable.item_list_button);
		// adapter = new MsgSysAdapter(getActivity(), new ArrayList<Object>());
		// pulllistview.setAdapter(adapter);
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
				LogUtils.i(arg2 + "");
				adapter.updateView(arg2);
				if (!TextUtils.isEmpty(lists.get(arg2-1).getUrl())&&!"".equals(lists.get(arg2-1).getUrl())) {
					Intent intent = new Intent(context, WebViewShowActivity.class);
					intent.putExtra(WebViewShowActivity.WEBVIEW_TEXT_TAG, "系统消息");
					intent.putExtra(WebViewShowActivity.SYSTEM_MESSAGE_TAG, lists.get(arg2 - 1).getUrl());
					// intent.putExtra(WebViewShowActivity.SYSTEM_MESSAGE_TAG,
					// "www.baidu.com");
					context.startActivity(intent);
				}else{
					RotateShowProgressDialog.ShowProgressOn(context);
					new AppApi().getAuditStatus(context, userId, "", FragmentMsgSys.this);
				}
				
				new AppApi().msgReaded(context, lists.get(arg2-1).getId(), FragmentMsgSys.this);
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

	@SuppressWarnings("static-access")
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {

		}
	}

	/*
	 * 数据填充listview
	 */
	public void fillDateForListView() {
		pulllistview.onRefreshComplete();

		if (info.getResult().getPageNum() == info.getResult().getCurrentPage()) {
			pulllistview.setMode(Mode.PULL_FROM_START);
		} else {
			pulllistview.setMode(Mode.BOTH);
		}
		if (null == adapter) {
			pageNum = 2;
			lists = info.getResult().getList();
			((AskMsgActivity) getActivity()).setPageTabPointHide(0);
			adapter = new MsgSysAdapter(getActivity(), lists, pulllistview.getRefreshableView());
			pulllistview.setAdapter(adapter);
			//showPoint();
		} else {
			//showPoint();
			if (ispulldown) {
				ispulldown = false;
				pageNum = 2;
				lists = info.getResult().getList();
			} else if (ispullup) {
				ispullup = false;
				pageNum++;
				lists.addAll(info.getResult().getList());
			} else {
				pageNum = 2;
				lists = info.getResult().getList();
			}
			adapter.setAsks(lists);
			adapter.notifyDataSetChanged();

		}
	}

	void showPoint() {
		if (info.getResult().isMsgIsOrNo()) {
			((AskMsgActivity) getActivity()).setPageTabPointShow(1);
		} else {
			((AskMsgActivity) getActivity()).setPageTabPointHide(1);
		}
	}

	@Override
	public void firstload() {
		new AppApi().getMsg(getActivity(), userId, 0, 1, pageSize, "", FragmentMsgSys.this);
	}

	@Override
	public void pullUpload(int index) {
		new AppApi().getMsg(getActivity(), userId, 0, pageNum, pageSize, "", FragmentMsgSys.this);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 2:
			firstload();
			break;

		default:
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("MsgSys"); // 统计页面
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("MsgSys"); // 统计页面
	}

}
