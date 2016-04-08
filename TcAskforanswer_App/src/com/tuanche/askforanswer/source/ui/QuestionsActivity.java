package com.tuanche.askforanswer.source.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

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
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.source.adapter.AnswerNumAdapter;
import com.tuanche.askforanswer.source.bean.MqaNumBean;
import com.tuanche.askforanswer.source.bean.MqaResultBean.QuestList;
import com.tuanche.askforanswer.source.view.DynamicBox;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * 我的页面： 答题量 和 被采纳 都进入这个页面
 * 
 */
public class QuestionsActivity extends BaseActivity implements ApiRequestListener, OnClickListener {

	private View mGoBack;
	private PullToRefreshListView pulllistview;
	private AnswerNumAdapter adapter;
	List<Object> s = new ArrayList<Object>();
	private TextView title;
	private TextView answer_num;
	private boolean isIdenty;
	private List<QuestList> lists;
	private MqaNumBean mqaNumBean;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mine_answernum);
		String s = getIntent().getStringExtra("identy");
		isFirst = true;
		getViews();
		if ("yes".equals(s)) {
			// 答题量
			isIdenty = true;
			if (isFirst) {
				box.showLoadingLayout();
				new AppApi().getMqaDa(mContext, 1, pageSize, useId, -1, "", this);
			}
			no_content.setText(getResources().getString(R.string.noanswerquestion));
		} else {
			// 采纳量
			isIdenty = false;
			if (isFirst) {
				box.showLoadingLayout();
				new AppApi().getMqaDa(mContext, 1, pageSize, useId, 1, "", this);
			}
			no_content.setText(getResources().getString(R.string.noacceptquestion));
		}
		setViews();
		setListeners();
		
	}

	@Override
	public void getViews() {
		// TODO Auto-generated method stub
		mGoBack = findViewById(R.id.go_back);
		pulllistview = (PullToRefreshListView) findViewById(R.id.pulllistview);
		title = (TextView) findViewById(R.id.title);
		answer_num = (TextView) findViewById(R.id.answer_num);
		pulllistview.setEmptyView(emptyView);
		box = new DynamicBox(mContext, pulllistview);

	}

	@Override
	public void setViews() {
		String second;
		if (isIdenty) {
			title.setText(getResources().getString(R.string.num_myanswer));
			second = String.format(getResources().getString(R.string.answer_num), 0);
			no_content.setText(getResources().getString(R.string.noanswerquestion));
		} else {
			no_content.setText(getResources().getString(R.string.noacceptquestion));
			title.setText(getResources().getText(R.string.num_myreceive));
			second = String.format(getResources().getString(R.string.answer_receive), 0);
		}
		answer_num.setText(second);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		mGoBack.setOnClickListener(this);
		pulllistview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				ispulldown = true;
				firstload();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				ispullup = true;
				pullupload();
			}
		});
		
		pulllistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(QuestionsActivity.this, AskInfoActivity.class);
				intent.putExtra(Config.QUESTIONTODETIAL, lists.get(arg2 - 1));
				startActivity(intent);				
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
		case MQADA_NOSIGN:
			if (obj instanceof MqaNumBean) {
				mqaNumBean = (MqaNumBean) obj;
				fillDateForListView();
				if (isFirst) {
					box.hideAll();
					isFirst = false;
				}
			}
			break;

		default:
			break;
		}
	}

	@SuppressWarnings("static-access")
	private void firstload() {
		if (isIdenty) {
			new AppApi().getMqaDa(mContext, 1, pageSize, useId, -1, "", QuestionsActivity.this);
		} else {
			new AppApi().getMqaDa(mContext, 1, pageSize, useId, 1, "", QuestionsActivity.this);

		}

	}

	@SuppressWarnings("static-access")
	private void pullupload() {
		if (isIdenty) {
			new AppApi().getMqaDa(mContext, pageNum, pageSize, useId, -1, "", QuestionsActivity.this);
		} else {
			new AppApi().getMqaDa(mContext, pageNum, pageSize, useId, 1, "", QuestionsActivity.this);

		}
	}

	@Override
	public void onError(Action method, Object statusCode) {
		switch (method) {
		case NETWORK_FAILED:
			if (AppApi.ERROR_NETWORK_FAILED.equals(statusCode)) {
				ToastUtil.showToast(QuestionsActivity.this, getResources().getString(R.string.net_no));
				if (isFirst) {
					box.showInternetOffLayout();
				} else if (ispulldown) {
					box.showInternetOffLayout();
				}
			}
			break;
		case MQADA_NOSIGN:
			if (statusCode instanceof ResponseErrorMessage) {
				// 后台返回了错误码和错误信息
				ToastUtil.showToast(QuestionsActivity.this, ((ResponseErrorMessage) statusCode).getMsg());
			} else if (statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode)) {
				// 网络超时
				ToastUtil.showToast(QuestionsActivity.this, getResources().getString(R.string.net_timeout));
				if (isFirst) {
					box.showExceptionLayout();
				} else if (ispulldown) {
					box.showExceptionLayout();
				}
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.go_back:
			finish();
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
		if(mqaNumBean.getResult().getTotal_page()==mqaNumBean.getResult().getCurrent_page()){
			pulllistview.setMode(Mode.PULL_FROM_START);
		}else{
			pulllistview.setMode(Mode.BOTH);
		}
		if (null == adapter) {
			pageNum = 2;
			lists = mqaNumBean.getResult().getQ_list();
			adapter = new AnswerNumAdapter(mContext, lists);
			pulllistview.setAdapter(adapter);
			String second = null;
			if (isIdenty) {
				MobclickAgent.onEvent(mContext,"answer_pv");
				// 答题量
				second = String.format(getResources().getString(R.string.answer_num), mqaNumBean.getResult().getTotal_count());
				answer_num.setText(second);
			} else {
				MobclickAgent.onEvent(mContext,"solve_pv");
				second = String.format(getResources().getString(R.string.answer_receive), mqaNumBean.getResult().getAccept_count());
				answer_num.setText(second);
			}
		} else {
			String second = null;
			if (ispulldown) {
				ispulldown = false;
				pageNum = 2;
				lists = mqaNumBean.getResult().getQ_list();
			} else if (ispullup) {
				ispullup = false;
				pageNum++;
				lists.addAll(mqaNumBean.getResult().getQ_list());
			} else {
				pageNum = 2;
				lists = mqaNumBean.getResult().getQ_list();
			}
			if (isIdenty) {
				// 答题量
				second = String.format(getResources().getString(R.string.answer_num), mqaNumBean.getResult().getTotal_count());
				answer_num.setText(second);
			} else {
				second = String.format(getResources().getString(R.string.answer_receive), mqaNumBean.getResult().getAccept_count());
				answer_num.setText(second);
			}
			adapter.setAsks(lists);
			adapter.notifyDataSetChanged();
			
		}
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
