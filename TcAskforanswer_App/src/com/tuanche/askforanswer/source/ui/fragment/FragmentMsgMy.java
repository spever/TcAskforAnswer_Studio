package com.tuanche.askforanswer.source.ui.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.tuanche.api.core.InitViews;
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
import com.tuanche.askforanswer.source.PopListener.ActivityChangFragmenListener;
import com.tuanche.askforanswer.source.adapter.MsgMineAdapter;
import com.tuanche.askforanswer.source.bean.MsgInfo;
import com.tuanche.askforanswer.source.bean.MsgInfoListBean;
import com.tuanche.askforanswer.source.ui.AskInfoActivity;
import com.tuanche.askforanswer.source.ui.AskMsgActivity;
import com.tuanche.askforanswer.source.view.DynamicBox;
import com.umeng.analytics.MobclickAgent;

/*
 * 我的消息fragment
 */
public class FragmentMsgMy extends BaseFragment implements ApiRequestListener,
		InitViews, ActivityChangFragmenListener {
	private FragmentMsgMy frMainAnSwer = null;
	private PullToRefreshListView pulllistview;
	private View view;
	private MsgMineAdapter adapter;
	private MsgInfo info;
	private List<MsgInfoListBean> lists;

	public FragmentMsgMy newInstance(Bundle bundle) {
		if (frMainAnSwer == null) {
			frMainAnSwer = new FragmentMsgMy();
		}
		frMainAnSwer.setArguments(bundle);
		return frMainAnSwer;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((AskMsgActivity) getActivity()).setListener(FragmentMsgMy.this);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
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
		if(isFirst){
			box.showLoadingLayout();
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
					isFirst=false;
					box.hideAll();
				}
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
				pulllistview.onRefreshComplete();
				if(isFirst){
					box.showInternetOffLayout();
				}
				if(ispulldown){
					box.showInternetOffLayout();
				}
				
			}
			break;
		case ASKMSG_NOSIGN:
			if (statusCode instanceof ResponseErrorMessage) {
				// 后台返回了错误码和错误信息
				showToast(((ResponseErrorMessage) statusCode).getMsg());
			} else if (statusCode instanceof String
					&& AppApi.ERROR_TIMEOUT.equals(statusCode)) {
				showToast(R.string.net_timeout);
				if (isFirst) {
					box.showExceptionLayout();
				}
				if(ispulldown){
					box.showExceptionLayout();
				}
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void getViews() {
		pulllistview = (PullToRefreshListView) view
				.findViewById(R.id.pulllistview);
		no_content.setText(context.getResources().getString(R.string.nomsg));
		box=new DynamicBox(context, pulllistview);
		pulllistview.setEmptyView(emptyView);

	}

	@Override
	public void setViews() {
		pulllistview.getRefreshableView().setSelector(
				R.drawable.item_list_button);
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
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				adapter.updateView(arg2);
				Intent intent = new Intent(getActivity(), AskInfoActivity.class);
				intent.putExtra(Config.MSGINFOTODETIAL, lists.get(arg2 - 1));
				getActivity().startActivityForResult(intent, 1);
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
		if (null == adapter) {
			pageNum=2;
			((AskMsgActivity) getActivity()).setPageTabPointHide(0);
			lists = info.getResult().getList();
			adapter = new MsgMineAdapter(getActivity(), lists,pulllistview.getRefreshableView());
			pulllistview.setAdapter(adapter);

		} else {
			if (ispulldown) {
				ispulldown=false;
				pageNum = 2;
				lists.clear();
				lists = info.getResult().getList();
			} else if (ispullup) {
				ispullup=false;
				pageNum++;
				lists.addAll(info.getResult().getList());
			} else {
				pageNum = 2;
				lists.clear();
				lists = info.getResult().getList();
			}
			adapter.setAsks(lists);
			adapter.notifyDataSetChanged();
			pulllistview.onRefreshComplete();
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void firstload() {
		new AppApi().getMsg(getActivity(), userId, 1, 1, pageSize, "",
				FragmentMsgMy.this);
	}

	@Override
	public void pullUpload(int index) {
		// TODO Auto-generated method stub
		new AppApi().getMsg(getActivity(), userId, 1, pageNum, pageSize, "",
				FragmentMsgMy.this);

	}

	@Override
	public void change() {
		firstload();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	    MobclickAgent.onPageStart("MsgMy"); //统计页面
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("MsgMy");
	}
}
