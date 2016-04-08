package com.tuanche.askforanswer.source.ui;

import java.util.List;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
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
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.app.utils.Utils;
import com.tuanche.askforanswer.source.PopListener.DialogListener;
import com.tuanche.askforanswer.source.adapter.AskInfoAdapter;
import com.tuanche.askforanswer.source.bean.AuditStatusBean;
import com.tuanche.askforanswer.source.bean.BeanIsAudited;
import com.tuanche.askforanswer.source.bean.DatialBean;
import com.tuanche.askforanswer.source.bean.HomeListInfoBean.HomeInfoQuestionBean;
import com.tuanche.askforanswer.source.bean.LableBean;
import com.tuanche.askforanswer.source.bean.MqaResultBean.QuestList;
import com.tuanche.askforanswer.source.bean.MsgInfoListBean;
import com.tuanche.askforanswer.source.bean.MsgReaded;
import com.tuanche.askforanswer.source.bean.QuestionDatialBean.DetialResultDetialBean.ReplyItemDetail;
import com.tuanche.askforanswer.source.bean.SubmiterResultBean;
import com.tuanche.askforanswer.source.bean.UserBean;
import com.tuanche.askforanswer.source.dialog.DialogAuditNo;
import com.tuanche.askforanswer.source.view.CircularImage;
import com.tuanche.askforanswer.source.view.DynamicBox;
import com.umeng.analytics.MobclickAgent;

public class AskInfoActivity extends BaseActivity implements ApiRequestListener, OnClickListener ,DialogListener{

	private PullToRefreshListView refreshListView;
	private LinearLayout headerView;
	private AskInfoAdapter adapter;
	private ImageView back;
	private LinearLayout edit_linear; // 输入框
	private TextView title;
	private TextView question_nick, question_time, question_info, question_tag;
	private TextView numView;
	private DatialBean detiaBean;
	private MsgInfoListBean msgInfo;// 消息列表传来的数据
	private HomeInfoQuestionBean homeInfo; // 首页传来的数据
	private QuestList qulist;// 采纳和问答页传来的数据
	private List<ReplyItemDetail> replyItemDetails;
	private boolean isreload = false;
	private int height;
	private LinearLayout fillHeiLayout;
	private String nowContent;
	private CircularImage  itme_circle_iv;

	private DialogAuditNo diAuditNo;
	private AuditStatusBean statusBean; // 状态
	private String audit_desc;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ask_info);
		getViews();
		setViews();
		setListeners();
		Intent intent = getIntent();
		isFirst = true;
		if (intent.getSerializableExtra(Config.MSGINFOTODETIAL) != null) {
			// 问题列表跳转到详情
			msgInfo = (MsgInfoListBean) intent.getSerializableExtra(Config.MSGINFOTODETIAL);
			// new AppApi().Detail(mContext, 5, 1, 1, 2, "", this);
			if (isFirst) {
				box.showLoadingLayout();
				new AppApi().Detail(mContext, msgInfo.getAskId(), useId, pageNum, pageSize, "", this);
			}
			new AppApi().msgReaded(mContext, msgInfo.getId(), this);

		} else if (intent.getSerializableExtra(Config.HOMETODETIAL) != null) {
			// 首页跳转到详情
			homeInfo = (HomeInfoQuestionBean) intent.getSerializableExtra(Config.HOMETODETIAL);
			if (isFirst) {
				box.showLoadingLayout();
				new AppApi().Detail(mContext, homeInfo.getId(), useId, pageNum, pageSize, "", this);
			}
		} else if (intent.getSerializableExtra(Config.QUESTIONTODETIAL) != null) {
			qulist = (QuestList) intent.getSerializableExtra(Config.QUESTIONTODETIAL);
			if (isFirst) {
				box.showLoadingLayout();
				new AppApi().Detail(mContext, qulist.getId(), useId, pageNum, pageSize, "", this);
			}
		}
	}

	@Override
	public void getViews() {
		refreshListView = (PullToRefreshListView) findViewById(R.id.asklistview);
		box = new DynamicBox(mContext, refreshListView);
		headerView = (LinearLayout) View.inflate(mContext, R.layout.listview_header_askinfo, null);
		refreshListView.getRefreshableView().addHeaderView(headerView);
		back = (ImageView) findViewById(R.id.top_back);
		// stickLayout = (LinearLayout) findViewById(R.id.stick_layout);
		edit_linear = (LinearLayout) findViewById(R.id.edit_linear);
		title = (TextView) findViewById(R.id.title);
		numView = (TextView) headerView.findViewById(R.id.numview);
		question_info = (TextView) headerView.findViewById(R.id.question_info);
		question_nick = (TextView) headerView.findViewById(R.id.question_nick);
		question_tag = (TextView) headerView.findViewById(R.id.question_tag);
		question_time = (TextView) headerView.findViewById(R.id.question_time);
		itme_circle_iv = (CircularImage)headerView.findViewById(R.id.itme_circle_iv);
		fillHeiLayout = (LinearLayout) findViewById(R.id.fill_height);
	}

	@Override
	public void setViews() {
		// refreshListView.getRefreshableView().addHeaderView(numView);
		Resources resources = getResources();
		Gson gson = new Gson();
		gson.fromJson(mSession.loadKey(Config.LABLES, ""), LableBean.class);
		numView.setText(String.format(resources.getString(R.string.item_header_info), 0));
		no_content.setText(resources.getString(R.string.noanswer));
		refreshListView.getRefreshableView().setFadingEdgeLength(0);
		// RelativeLayout.LayoutParams params = new
		// RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.MATCH_PARENT,
		// height);
		// 获得高度
		// emptyView.setLayoutParams(params);
		nowContent = "";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setListeners() {
		back.setOnClickListener(this);
		// 上下拉刷新
		refreshListView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				ispulldown = true;
				firstLoad();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				ispullup = true;
				pullupload();
			}

		});

		refreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				LogUtils.i(arg2 + "--position");
			}
		});
		box.setClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				isFirst = true;
				showLoading();
				firstLoad();
			}
		});

		edit_linear.setOnClickListener(this);
	}

	/**
	 * 网络请求成功
	 */
	@Override
	public void onSuccess(Action method, Object obj) {
		switch (method) {
		case DETAIL_NOSIGN:
			if (obj instanceof DatialBean) {
				detiaBean = (DatialBean) obj;
				fillDateForView();
				if (isFirst) {
					box.hideAll();
					isFirst = false;
				}
				if (RotateShowProgressDialog.isDialogShowing()) {
					ToastUtil.showToast(mContext, "提交成功");
					RotateShowProgressDialog.ShowProgressOff();
				}
			}
			break;
		case ANSWER_NOSIGN:
			if (obj instanceof SubmiterResultBean) {
				SubmiterResultBean bean = (SubmiterResultBean) obj;
				if (bean.getResult().getList().get("contentType").equals("1")) {
					Toast.makeText(mContext, getResources().getString(R.string.sensitive), Toast.LENGTH_SHORT).show();
					if (RotateShowProgressDialog.isDialogShowing()) {
						RotateShowProgressDialog.ShowProgressOff();
					}
				} else {
					nowContent = "";
					reload();
				}
				
			}
			break;
		case ASKMSG_READED_NOSIGN:
			if (obj instanceof MsgReaded) {
			}
			break;
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

	/**
	 * 加载请求后加入数据
	 */
	private void fillDateForView() {
		refreshListView.onRefreshComplete();
		if (detiaBean.getResult().getPageNum() <= (detiaBean.getResult().getCurrentPage())) {
			refreshListView.setMode(Mode.PULL_FROM_START);
		} else {
			refreshListView.setMode(Mode.BOTH);
		}
			question_info.setText(detiaBean.getResult().getDetail().getQuestion().get("content"));
		question_nick.setText(detiaBean.getResult().getDetail().getQuestion().get("nickName"));
		question_time.setText(detiaBean.getResult().getDetail().getQuestion().get("askDate"));
		question_tag.setText(detiaBean.getResult().getDetail().getQuestion().get("tag").replace(",", " "));
		pictureUtils.getInstance(this).display(itme_circle_iv, detiaBean.getResult().getDetail().getQuestion().get("icon")+"?"+System.currentTimeMillis(),config);
		
		numView.setText(String.format(getResources().getString(R.string.item_header_info), detiaBean.getResult().getDetail().getQuestion().get("replyNum")));
		title.setText(detiaBean.getResult().getDetail().getQuestion().get("nickName") + "提问");
		if (null == adapter) {
			pageNum = 2;
			replyItemDetails = detiaBean.getResult().getDetail().getAnswer();
			height = Utils.getHeight(mContext) - fillHeiLayout.getHeight() - edit_linear.getHeight() - Utils.getStatusBarHeight(mContext)
					- getResources().getDimensionPixelSize(R.dimen.top_bar_height);
			adapter = new AskInfoAdapter(mContext, replyItemDetails, emptyView, height);
			refreshListView.setAdapter(adapter);
			// showEmptyVIew();
		} else {
			if (ispulldown) {
				ispulldown = false;
				pageNum = 2;
				replyItemDetails = detiaBean.getResult().getDetail().getAnswer();
			} else if (ispullup) {
				pageNum++;
				ispullup = false;
				replyItemDetails.addAll(detiaBean.getResult().getDetail().getAnswer());
				LogUtils.e("pulll");
			} else if (isreload) {
				replyItemDetails = detiaBean.getResult().getDetail().getAnswer();
				pageNum = 2;
				isreload = false;
				LogUtils.e("reload");
			} else {
				pageNum = 2;
				replyItemDetails = detiaBean.getResult().getDetail().getAnswer();
			}
			// showEmptyVIew();
			adapter.setAsks(replyItemDetails);
			adapter.notifyDataSetChanged();
			if (refreshListView.isRefreshing()) {
				refreshListView.onRefreshComplete();
			}
		}

	}

	/**
	 * 网络请求失败
	 */
	@Override
	public void onError(Action method, Object statusCode) {
		switch (method) {
		case NETWORK_FAILED:
			if (AppApi.ERROR_NETWORK_FAILED.equals(statusCode)) {
				ToastUtil.showToast(this, getResources().getString(R.string.net_no));
				if (isFirst) {
					box.showInternetOffLayout();
				}
				if (ispulldown) {
					box.showInternetOffLayout();
				}
				if (RotateShowProgressDialog.isDialogShowing())
					RotateShowProgressDialog.ShowProgressOff();

			}
			break;
		case DETAIL_NOSIGN:
			if (statusCode instanceof ResponseErrorMessage) {
				// 后台返回了错误码和错误信息
				ToastUtil.showToast(this, ((ResponseErrorMessage) statusCode).getMsg());
			} else if (statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode)) {
				// 网络超时
				ToastUtil.showToast(this, getResources().getString(R.string.net_timeout));
				if (isFirst) {
					box.showExceptionLayout();
				}
				if (ispulldown) {
					box.showExceptionLayout();
				}
			}
			break;
		case ANSWER_NOSIGN:
			if (statusCode instanceof ResponseErrorMessage) {
				// 后台返回了错误码和错误信息
				ToastUtil.showToast(this, ((ResponseErrorMessage) statusCode).getMsg());
			} else if (statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode)) {
				// 网络超时
				ToastUtil.showToast(this, getResources().getString(R.string.net_timeout));
			}
			if (RotateShowProgressDialog.isDialogShowing())
				RotateShowProgressDialog.ShowProgressOff();
			break;
		case ASKMSG_READED_NOSIGN:
			if (statusCode instanceof ResponseErrorMessage) {
				// 后台返回了错误码和错误信息
				ToastUtil.showToast(this, ((ResponseErrorMessage) statusCode).getMsg());
			} else if (statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode)) {
				// 网络超时
				ToastUtil.showToast(this, getResources().getString(R.string.net_timeout));
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_back:
			if (msgInfo != null) {
				finish();
			} else {
				this.finish();
			}
			break;

		case R.id.edit_linear:
			// showBigText();
			edit_linear.setClickable(false);
			getAuditState();
			/*Intent intent = new Intent(mContext, EditDialog.class);
			intent.putExtra("content", nowContent);
			intent.putExtra("id",homeInfo.getId());
			startActivityForResult(intent, 10);;*/
			break;
		default:
			break;
		}
	}

	@SuppressWarnings("static-access")
	public void firstAsk(String s) {
		if (null != homeInfo) {
			new AppApi().askAnswer(mContext, homeInfo.getId(), useId, s, 2, "", this);
		} else if (null != msgInfo) {
			new AppApi().askAnswer(mContext, msgInfo.getAskId(), useId, s, 2, "", this);
		}
	}

	/**
	 * 回答提交后重新刷新
	 */
	@SuppressWarnings("static-access")
	public void reload() {
		isreload = true;
		if (null != homeInfo) {
			new AppApi().Detail(mContext, homeInfo.getId(), useId, 1, pageSize, "", this);
		} else if (null != msgInfo) {
			new AppApi().Detail(mContext, msgInfo.getAskId(), useId, 1, pageSize, "", this);
		} else if (null != qulist) {
			new AppApi().Detail(mContext, qulist.getId(), useId, 1, pageSize, "", this);
		}

	}

	@SuppressWarnings("static-access")
	private void firstLoad() {
		if (null != homeInfo) {
			new AppApi().Detail(mContext, homeInfo.getId(), useId, 1, pageSize, "", AskInfoActivity.this);
		} else if (null != msgInfo) {
			new AppApi().Detail(mContext, msgInfo.getAskId(), useId, 1, pageSize, "", AskInfoActivity.this);
		} else if (null != qulist) {
			new AppApi().Detail(mContext, qulist.getId(), useId, 1, pageSize, "", this);
		}
	}

	@SuppressWarnings("static-access")
	private void pullupload() {
		if (null != homeInfo) {
			new AppApi().Detail(mContext, homeInfo.getId(), useId, pageNum, pageSize, "", AskInfoActivity.this);
		} else if (null != msgInfo) {
			new AppApi().Detail(mContext, msgInfo.getAskId(), useId, pageNum, pageSize, "", AskInfoActivity.this);
		} else if (null != qulist) {
			new AppApi().Detail(mContext, qulist.getId(), useId, pageNum, pageSize, "", this);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (msgInfo != null) {
				finish();
			} else {
				this.finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		MobclickAgent.onEvent(mContext, "questiondetails_pv");

	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			String s = data.getStringExtra("content");
			nowContent = s;
			boolean issubmit=data.getBooleanExtra("issubmit", false);
			if (!TextUtils.isEmpty(s)&&!"".equals(s)&&issubmit) {
				RotateShowProgressDialog.ShowProgressOn(mContext, false);
				firstAsk(s);
			}
			break;

		default:
			break;
		}
	}

	public void getAuditState() {
		if (state != 0) {// 0 ，代表是第一次进入app，一定是未审核状态
			new AppApi().getAuditStatus(mContext, useId, "", this);
		} else {
			//state = status;
			mSession.saveKey(Config.STATUS_BEAN, "");
			fillSetDataForView(state,audit_desc);
		}
		edit_linear.setClickable(true);
	}

	/**
	 * 根据status的值，展示不同的对话框
	 * @param status
	 */
	private void
	fillSetDataForView(int status,String audit_desc) {
		BeanIsAudited beanIsAudited;
		switch(status){
			case 1:
				//diAuditNo = new DialogAuditNo(mContext, DialogListener.DialogEnum.audit_ing);
				ToastUtil.showToast(mContext, "你提交的认证申请正在审核中，审核通过了就可以回答问题了");
				break;
			case 2:
				if ("".equals(Session.get(mContext).loadKey(Config.ISPASS, ""))) {
					beanIsAudited = new BeanIsAudited();
					beanIsAudited.setAudited(false);
					beanIsAudited.setUserId(useId);
				} else {
					beanIsAudited = new Gson().fromJson(Session.get(mContext).loadKey(Config.ISPASS, ""), BeanIsAudited.class);
				}
				/*if (userId == beanIsAudited.getUserId() && beanIsAudited.isAudited() == false) {
					diAuditNo = new DialogAuditNo(mContext, DialogListener.DialogEnum.audit_ed);
				} else if (userId != beanIsAudited.getUserId()) {
					diAuditNo = new DialogAuditNo(mContext, DialogListener.DialogEnum.audit_ed);
				}*/

				//认证通过之后点击跳转的页面
			Intent intent = new Intent(mContext, EditDialog.class);
			intent.putExtra("content", nowContent);
			intent.putExtra("id",detiaBean.getResult().getDetail().getQuestion().get("id"));
			startActivityForResult(intent, 10);
				break;
			case 3:
				if ("".equals(Session.get(mContext).loadKey(Config.ISPASS, ""))) {
					beanIsAudited = new BeanIsAudited();
					beanIsAudited.setAudited(false);
					beanIsAudited.setUserId(useId);
				} else {
					beanIsAudited = new Gson().fromJson(Session.get(mContext).loadKey(Config.ISPASS, ""), BeanIsAudited.class);
				}
				beanIsAudited.setAudited(false);
				mSession.saveKey(Config.ISPASS, new Gson().toJson(beanIsAudited));
				//diAuditNo = new DialogAuditNo(mContext, DialogListener.DialogEnum.audit_refused);
				if(!TextUtils.isEmpty(audit_desc))
				ToastUtil.showToast(mContext,"认证信息审核被拒绝："+audit_desc);
				break;
			default:
				//diAuditNo = new DialogAuditNo(mContext, DialogListener.DialogEnum.audit_no);
				LogUtils.e("未认证");
				ToastUtil.showToast(mContext, "您还没有签约技师，签约了才能回答问题");
				break;
		}
		/*if (null != diAuditNo) {
			diAuditNo.setListener(this);
			diAuditNo.show();
		}*/
	}
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
			auIsAudited.setAudited(true);
			mSession.saveKey(Config.ISPASS, new Gson().toJson(auIsAudited));
		}*/
		/*BeanIsAudited auIsAudited = new BeanIsAudited();
		auIsAudited.setUserId(userId);
		auIsAudited.setAudited(true);
		mSession.saveKey(Config.ISPASS, new Gson().toJson(auIsAudited));*/
	}

}
