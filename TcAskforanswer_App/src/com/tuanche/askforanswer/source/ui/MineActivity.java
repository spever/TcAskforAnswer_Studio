package com.tuanche.askforanswer.source.ui;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.tuanche.api.utils.AppUtils;
import com.tuanche.api.utils.AppUtils.StorageFile;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.utils.RotateShowProgressDialog;
import com.tuanche.api.utils.SaveFileData;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.core.Session;
import com.tuanche.askforanswer.app.utils.CheckUtil;
import com.tuanche.askforanswer.app.utils.FileUtils;
import com.tuanche.askforanswer.app.utils.MySharedPreferences;
import com.tuanche.askforanswer.app.utils.PhotoUtils;
import com.tuanche.askforanswer.app.utils.PictureFileUtils;
import com.tuanche.askforanswer.app.utils.PictureUtils;
import com.tuanche.askforanswer.app.utils.PushAgentUtils;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.app.utils.Utils;
import com.tuanche.askforanswer.source.PopListener.PopListener;
import com.tuanche.askforanswer.source.bean.MineBean;
import com.tuanche.askforanswer.source.bean.MineInfo;
import com.tuanche.askforanswer.source.view.ChooseOrCameraPop;
import com.tuanche.askforanswer.source.view.CircularImage;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
/**
 * 我的页面
 * @author zpf
 *
 */
public class MineActivity extends BaseActivity implements ApiRequestListener,OnClickListener,PopListener{

	private View mGoBack;
	/**
	 * 提现
	 */
	private View mCashInfo;
	/**
	 * 银行卡
	 */
	private View mBankInfo;
	/**
	 * 证件资料
	 */
	private View mPaperInfo;
	/**
	 * 免打扰
	 */
	private View mNotBother;
	/**
	 * 关于我们
	 */
	private View mAboutInfo;
	/**
	 * 检查更新
	 */
	private View mCheckUpdate;
	/**
	 * 客服电话
	 */
	private View mPhoneNumber;
	/**
	 * 答题量
	 */
	private View mQuestions;
	/**
	 * 被采纳量
	 */
	private View mAnswerQuestions;
	/**
	 * 收益
	 */
	private View mAwardMoney;
	
	
	/**
	 * 退出登陆
	 */
	private View btnView;

	/**
	 * 奖励补贴规则
	 */
	private TextView tvView;
	
	
	
	private TextView userName,answerNumbers,getNumbers,moneyAll,notBrother;
	
	/**
	 * 证件资料是否 通过
	 */
	private TextView cardInfo;
	
	/**
	 * 免打扰时间
	 */
	private String notBrotherText = "";
	/**
	 * 免打扰 是否开启    0:关   1：开 
	 */
	private int switch_status = 0;
	
	/**
	 * 计算标准
	 */
	private String urlCalCount = "";
	
	private  ChooseOrCameraPop popCameraPop;
	
	private  CircularImage user_header_image;
	
	private String path;
	
	private static final int CROP_CODE = 88;// 调用裁剪工具的请求码
	private MineInfo bean;
	private TextView tvReward;//收益
	private TextView tvTip1;//两个条的文字
	private TextView tvTip2;
	private TextView tvSign;//签约信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_main);
		
		getViews();
		setViews();
		setListeners();
	}
	
	
	@Override
	public void getViews() {
		// TODO Auto-generated method stub
		mGoBack = findViewById(R.id.go_back);
		mCashInfo = findViewById(R.id.cash_info);
		mBankInfo = findViewById(R.id.bank_info);
		mPaperInfo = findViewById(R.id.papers_info);
		mNotBother = findViewById(R.id.free_of_bother);
		mAboutInfo = findViewById(R.id.about_info);
		mQuestions = findViewById(R.id.questions_numbers);
		mAnswerQuestions = findViewById(R.id.questions_identy);
		mAwardMoney = findViewById(R.id.award_money);
		mCheckUpdate = findViewById(R.id.check_update_new);
		mPhoneNumber = findViewById(R.id.phone_kefu_number);
	
		userName = (TextView) findViewById(R.id.user_name);
		answerNumbers = (TextView) findViewById(R.id.answerNumbers);
		getNumbers = (TextView) findViewById(R.id.getQuestionNum);
		moneyAll = (TextView) findViewById(R.id.moneyAll);
		notBrother = (TextView) findViewById(R.id.timeBrother);
		cardInfo = (TextView) findViewById(R.id.ifIdenty);
		
		btnView = findViewById(R.id.btnExit);
		tvView = (TextView) findViewById(R.id.tv_reward_rule);
		tvView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		tvView.getPaint().setAntiAlias(true);
		popCameraPop = new ChooseOrCameraPop(mContext, null);
		user_header_image = (CircularImage)findViewById(R.id.user_header_image);
		tvReward = (TextView) findViewById(R.id.reward);
		tvTip1 = (TextView) findViewById(R.id.tip1);
		tvTip2 = (TextView) findViewById(R.id.tip2);
		tvSign = (TextView) findViewById(R.id.sign);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void setViews() {
		// TODO Auto-generated method stub
		new AppApi().MINE_INFO(mContext, this.useId+"", this);
		
	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		
		mGoBack.setOnClickListener(this);
		mCashInfo.setOnClickListener(this);
		mBankInfo.setOnClickListener(this);
		mPaperInfo.setOnClickListener(this);
		mNotBother.setOnClickListener(this);
		mAboutInfo.setOnClickListener(this);
		mQuestions.setOnClickListener(this);
		mAnswerQuestions.setOnClickListener(this);
		mAwardMoney.setOnClickListener(this);
		mCheckUpdate.setOnClickListener(this);
		mPhoneNumber.setOnClickListener(this);
		btnView.setOnClickListener(this);
		tvView.setOnClickListener(this);
		popCameraPop.setListener(this);
		user_header_image.setOnClickListener(this);
		
	}

	@SuppressWarnings("static-access")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	//	new AppApi().MINE_INFO(mContext, this.useId+"", this);
//		RotateShowProgressDialog.ShowProgressOn(mContext, false);
		
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		
//		String timeReuslt = intent.getStringExtra("TimeForResult");
//		if(!CheckUtil.isEmpty(timeReuslt)){
//			notBrother.setText(timeReuslt);
//		}else{
//			notBrother.setText("");
//		}
	}
	
	@Override
	public void onSuccess(Action method, Object obj) {
		// TODO Auto-generated method stub
		switch (method) {
		case MINEINFO_NOSIGN:
			bean = (MineInfo) obj;
			if(!CheckUtil.isEmpty(bean.getResult())){
				setData(bean.getResult());
			}
//			if(RotateShowProgressDialog.isDialogShowing()){
//				RotateShowProgressDialog.ShowProgressOff();
//			}
			LogUtils.i("我的信息-----------" + bean.toString());
			break;
		case UPLOAD_PORTRAI_NOSIGN:
			RotateShowProgressDialog.ShowProgressOff();
			new AppApi().MINE_INFO(mContext, this.useId+"", this);
			ToastUtil.showToast(MineActivity.this, "上传头像成功");
			
			
			break;
		}
		
	}


	private void setData(MineBean mineBean){
		tvView.setText(bean.getResult().getRewardText());//设置奖励补贴规则文字
		int status = Utils.getUserStatus(this);//拿到状态
		Log.i("status===",status+"");
		if(status==3){
			tvSign.setText("签约失败");
		}else if(status==1){
			tvSign.setText("签约审核中");
		}else if(status==2){
			tvSign.setVisibility(View.INVISIBLE);
		}else{
			tvSign.setText("提交签约");
		}
 			if(status==2){
			moneyAll.setVisibility(View.VISIBLE);
			tvReward.setVisibility(View.VISIBLE);
			answerNumbers.setVisibility(View.VISIBLE);
			getNumbers.setVisibility(View.VISIBLE);
			tvTip1.setVisibility(View.VISIBLE);
			tvTip2.setVisibility(View.VISIBLE);
			mAwardMoney.setClickable(true);
			mQuestions.setClickable(true);
			mAnswerQuestions.setClickable(true);
			mCashInfo.setClickable(true);
		}else{
				mAwardMoney.setClickable(false);
				mQuestions.setClickable(false);
				mAnswerQuestions.setClickable(false);
				mCashInfo.setClickable(false);
			}
		userName.setText(mineBean.getNick());
		answerNumbers.setText(mineBean.getMyQuestionCount()+"");
		getNumbers.setText(mineBean.getAcceptCount()+ "");
		moneyAll.setText(mineBean.getMoney() + "");
		urlCalCount = mineBean.getCalStandard();
//		cardInfo.setText(mineBean.getCardInfo());  //暂不需要  原因：目前，如果技师不认证通过，就不会进入该页面。若进入一定认证过，所以暂时没必要显示

		notBrotherText = mineBean.getStart_time()+" —— "+mineBean.getEnd_time();
		switch_status = mineBean.getSwitch_status();
		if(switch_status == 0){
			notBrother.setText("未开启");
		}else if(switch_status == 1){
			notBrother.setText(notBrotherText+"");
		}

			PictureUtils.getInstance(this).display(user_header_image,
				mineBean.getHead()+"?"+System.currentTimeMillis(),PictureUtils.createConfig(this, R.drawable.head_user));

	}
	@Override
	public void onError(Action method, Object statusCode) {
		// TODO Auto-generated method stub
		switch (method) {
		case NETWORK_FAILED:
			if(AppApi.ERROR_NETWORK_FAILED.equals(statusCode)){
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_no));
			}
			break;
		case MINEINFO_NOSIGN:
			if(statusCode instanceof ResponseErrorMessage){
				//后台返回了错误码和错误信息
				ToastUtil.showToast(mContext, ((ResponseErrorMessage)statusCode).getMsg()+"" );
			} else if(statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode) ){
				//网络超时
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_timeout));
			}
//			if(RotateShowProgressDialog.isDialogShowing()){
//				RotateShowProgressDialog.ShowProgressOff();
//			}
			break;
			
		case UPLOAD_PORTRAI_NOSIGN:
			RotateShowProgressDialog.ShowProgressOff();
			ToastUtil.showToast(MineActivity.this, "上传头像失败");
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=null;
		switch (v.getId()) {
		case R.id.go_back:
			finish();
			break;
		case R.id.questions_numbers:
			MobclickAgent.onEvent(MineActivity.this, "mine_answer_click");
			intent=new Intent(MineActivity.this,QuestionsActivity.class);
			intent.putExtra("identy", "yes");
			startActivity(intent);
			break;
		case R.id.questions_identy:
			MobclickAgent.onEvent(MineActivity.this, "mine_solve_click");
			intent=new Intent(MineActivity.this,QuestionsActivity.class);
			intent.putExtra("identy", "no");
			startActivity(intent);
			break;
		case R.id.award_money:
			MobclickAgent.onEvent(MineActivity.this, "mine_profit_click");
			Intent intn = new Intent(MineActivity.this,AwardMoneyActivity.class);
			intn.putExtra("calUrl", urlCalCount);
			startActivity(intn);
			break;
			case R.id.cash_info:
				MobclickAgent.onEvent(MineActivity.this, "mine_cash_click");
				Intent intn2 = new Intent(MineActivity.this,AwardMoneyActivity.class);
				intn2.putExtra("calUrl", urlCalCount);
				startActivity(intn2);
				break;
		case R.id.bank_info:
			MobclickAgent.onEvent(MineActivity.this, "mine_bankcard_click");
			openActivity(BankInfoActivity.class);
			break;
		case R.id.papers_info:
			MobclickAgent.onEvent(MineActivity.this, "mine_certificates_click");
			openActivity(PerfectDataActivity.class);
			break;
		case R.id.free_of_bother:
			MobclickAgent.onEvent(MineActivity.this, "mine_freedisturb_click");
			Intent intent2 = new Intent(MineActivity.this,TimeNotBotherActivity.class);
			intent2.putExtra("switch_status", switch_status);
			intent2.putExtra("notBrotherText", notBrotherText);
			startActivity(intent2);
			break;
		case R.id.about_info:
			MobclickAgent.onEvent(MineActivity.this, "mine_aboutus_click");
			openActivity(AboutActivity.class);
			break;
		case R.id.check_update_new:
			MobclickAgent.onEvent(MineActivity.this, "mine_update_click");
			doCheckVersion();
			break;
		case R.id.phone_kefu_number:
			phoneDialog();
			break;
		case R.id.btnExit:
			exitDialog();
			break;
			case R.id.tv_reward_rule:
				showHtml();
				break;
		case R.id.user_header_image:
			if(bean!=null){
				showPopWindow();
			}
			break;
		}
	}
	
	
	void showPopWindow() {
		if (!popCameraPop.isShowing()) {
			// popCameraPop.setAnimationStyle(R.style.AnimationFade);
			popCameraPop.showAtLocation(user_header_image, Gravity.BOTTOM, 0, 0);
		}
	}
	
	
	private void phoneDialog(){
		
		
		View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_phone_view, null);
		
		 final Dialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();

        dialog.getWindow().setContentView(view);
        /**
         * 是否  点击 view 外部 就让她消失
         */
        dialog.setCanceledOnTouchOutside(false);
		
		View btnCancel = view.findViewById(R.id.btn_cancel);
		View btnSure = view.findViewById(R.id.btn_sure);
		//取消按钮
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		//确认按钮
		btnSure.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void onClick(View arg0) {
				MobclickAgent.onEvent(MineActivity.this, "mine_servicephone_click");
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:4006969123"));
           		MineActivity.this.startActivity(intent);
				dialog.cancel();
			}
		});
		
	}
	/**
	 * 退出当前状态
	 */
	private void exitDialog(){
		
		
		View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_exit_view, null);
		
		final Dialog dialog = new AlertDialog.Builder(mContext).create();
		dialog.show();
		
		dialog.getWindow().setContentView(view);
		/**
		 * 是否  点击 view 外部 就让她消失
		 */
		dialog.setCanceledOnTouchOutside(false);
		
		View btnCancel = view.findViewById(R.id.btn_cancel);
		View btnSure = view.findViewById(R.id.btn_sure);
		//取消按钮
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		//确认按钮
		btnSure.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void onClick(View arg0) {
				//MySharedPreferences.clear(MineActivity.this);
				PushAgentUtils.removeAlias(mContext, useId+"");
				mSession.saveKey(Config.USER_BEAN, "");
				mSession.saveKey(Config.USER_ID, "");

				mSession.saveKey(Config.USER_PHONE, "");
				mSession.saveKey(Config.IDENTIFY,"");
				Session.get(MineActivity.this).saveKey(Config.STATUS_BEAN, "");

				MobclickAgent.onEvent(MineActivity.this, "mine_loginout_click");
				openActivity(OpenActivity.class);
				finish();
				dialog.cancel();
			}
		});
		
	}
	
	/**
	 * 检查更新
	 */
	private void doCheckVersion() {
		    ToastUtil.showToast(mContext, "正在检查更新...");
		    UmengUpdateAgent.setUpdateOnlyWifi(false);
	        UmengUpdateAgent.forceUpdate(MineActivity.this);
	        UmengUpdateAgent.setUpdateAutoPopup(true);   //当与 UmengUpdateAgent.forceUpdate(this)连用时设置为true
	        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
				@Override
				public void onUpdateReturned(int updateStatus, UpdateResponse arg1) {
					// TODO Auto-generated method stub
					switch (updateStatus) {
						case 0: // has update
							ToastUtil.cancelToast();
							break;
						case 1: //has no update
							ToastUtil.showToast(mContext, "您的版本已是最新版");
							break;
						case 2: // none wifi
							// Toast.makeText(HomeActivity.this, "没有wifi连接， 只在wifi下更新",
							// Toast.LENGTH_SHORT)
							// .show();
							break;
						case 3: // time out
							ToastUtil.showToast(mContext, "请求超时，请稍后重试");
							break;
					}
				}
			});
	    }


	@Override
	public void takeCamera() {
	
		path = null;
		File file = FileUtils.buildFile(
				AppUtils.getPath(mContext, StorageFile.tempfile) + String.valueOf(System.currentTimeMillis() / 1000) + ".jpg", false);
		path = file.getPath();
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
		startActivityForResult(intent, Config.camera);
	}


	@Override
	public void choosePicture() {
		
		if (Build.VERSION.SDK_INT < 19) {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*");
			startActivityForResult(intent, Config.gallery);
		} else {
			Intent intent = new Intent();
			intent.setType("image/*");
			// 由于Intent.ACTION_OPEN_DOCUMENT的版本是4.4以上的内容
			// 所以注意这个方法的最上面添加了@SuppressLint("InlinedApi")
			// 如果客户使用的不是4.4以上的版本，因为前面有判断，所以根本不会走else，
			// 也就不会出现任何因为这句代码引发的错误
			intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
			startActivityForResult(intent, Config.gallery);

		}
	}


	@Override
	public void submitAnswer(String content) {
		
	}


	@Override
	public void popDimiss() {
		
	}
	/**
		展示奖励补贴规则网页
	 */
	public void showHtml(){
		Intent intent  = new Intent(MineActivity.this,WebViewShowActivity.class);
		intent.putExtra(WebViewShowActivity.REWARD_RULE_TAG,bean.getResult().getRewardUrl());
		intent.putExtra(WebViewShowActivity.WEBVIEW_TEXT_TAG,"奖励补贴规则");
		startActivity(intent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CROP_CODE:
			if (resultCode == RESULT_OK) {
				detialPathAndShowImage();
			}
			break;
		case Config.gallery:
			if (data != null) {
				Uri uri = data.getData();
				path = PhotoUtils.getInstance().getPath(mContext, uri);
				LogUtils.e(path);
				startPhotoZoom(Uri.fromFile(new File(path)));
				//detialPathAndShowImage();
			} else {
				//ToastUtil.showToast(mContext, "图片选择失败请使用拍照功能或重新选择图片！");
			}
			break;

		case Config.camera:
			//detialPathAndShowImage();
			if (!TextUtils.isEmpty(path) && new File(path).exists()) {
				startPhotoZoom(Uri.fromFile(new File(path)));
			}
			break;
		}
	}
	
	/**
	 * 选择的图片加入到集合
	 */
	private void detialPathAndShowImage() {
		if (!TextUtils.isEmpty(path) && new File(path).exists()) {
			new PictureZoomTask().execute(path);
		}
	}
	
	/**
	 * 裁剪图片
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		File file = FileUtils.buildFile(
				AppUtils.getPath(this, StorageFile.cache) + String.valueOf(System.currentTimeMillis() / 1000) + ".jpg", false);
		path = file.getPath();
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("output", Uri.fromFile(new File(path)));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, CROP_CODE);
	}
	
	class PictureZoomTask extends AsyncTask<String, Integer, String> {

		
		@Override
		protected void onPreExecute() {
			RotateShowProgressDialog.ShowProgressOn(MineActivity.this);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			path = result;
			if(path==null){
				RotateShowProgressDialog.ShowProgressOff();
			}else{
				AppApi.upPortraitPicture(MineActivity.this, MineActivity.this.useId, path, MineActivity.this);
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			String path = arg0[0];
			try {
				path = PictureFileUtils.compressImage(mContext, path, "", 80);
			} catch (FileNotFoundException e) {
				ToastUtil.showToast(mContext, "获取图片失败");
				return null;
			}

			return path;
		}

	}

}
