package com.tuanche.askforanswer.source.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.utils.RotateShowProgressDialog;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.utils.CheckUtil;
import com.tuanche.askforanswer.app.utils.MySharedPreferences;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.source.bean.LoginOrRegist;
import com.tuanche.askforanswer.source.bean.UserBean;
import com.tuanche.askforanswer.source.bean.Validate_Code;
import com.tuanche.askforanswer.source.bean.VoiceResult;
import com.umeng.analytics.MobclickAgent;

/**
 * 技师注册
 * @author zpf
 *
 */
public class RegisterActivity extends BaseActivity implements ApiRequestListener,OnClickListener,TextWatcher{

	private TimeCount timeGetCode;
	private VoiceTimeCount voiceTimeGetCode;
	
	private View mGoBack;
	private Button mRegistNext;
	
	private EditText mUserPhone;
	private TextView mGetCode;
	private EditText mPhoneCode;
	private EditText mInvateCode;
	
	private View mVoiceCode;
	private View mCarProtocol;
	
	private Boolean mRegisterResult = false ;
	
	/**
	 * 是否 同意  协议
	 */
	private Boolean mAgree = true;
	
	private ImageView sureXieYi;
	
	
	/**
	 * 手机号
	 */
	private String strPhone;
	
	/**
	 * isBoolShow1,isBoolShow2 这两者必须同时 为true,才显示  语音验证码
	 */
	private boolean isBoolShow1 = false;
	private boolean isBoolShow2 = false;
	
	private TextView voiceTextShow;
	private TextView voiceGetShow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		getViews();
		setViews();
		setListeners();
		
		
	}
	
	
	@Override
	public void getViews() {
		// TODO Auto-generated method stub
		timeGetCode = new TimeCount(60000, 1000);
		voiceTimeGetCode = new VoiceTimeCount(60000, 1000);
		
		mGoBack = findViewById(R.id.go_back);
		mRegistNext = (Button) findViewById(R.id.btnNext);
		mGetCode = (TextView) findViewById(R.id.btn_getCode);
		mUserPhone = (EditText) findViewById(R.id.master_phone);
		mPhoneCode = (EditText) findViewById(R.id.master_code);
		mInvateCode = (EditText) findViewById(R.id.request_code);
		
		mVoiceCode = findViewById(R.id.getVoiceCode);
		sureXieYi = (ImageView) findViewById(R.id.check_xieyi);
		
		mCarProtocol = findViewById(R.id.car_protocol);
		
		voiceTextShow = (TextView) findViewById(R.id.voice_show_text);
		voiceGetShow = (TextView) findViewById(R.id.voiceCodeShow);
	}

	@SuppressWarnings("static-access")
	@Override
	public void setViews() { 
		// TODO Auto-generated method stub
//		new AppApi().VOICE_SHOW(mContext, this);   //语音验证码
	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		mGoBack.setOnClickListener(this);
		mRegistNext.setOnClickListener(this);
		mGetCode.setOnClickListener(this);
		
		mVoiceCode.setOnClickListener(this);
		sureXieYi.setOnClickListener(this);
		
		mCarProtocol.setOnClickListener(this);
		
		mUserPhone.addTextChangedListener(this);
		
	}

	
	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.go_back:
			finish();
			break;
		case R.id.btn_getCode:  //获取验证码
			
			if(isBoolShow1){  //首先判断服务器
				voiceTextShow.setTextColor(Color.parseColor("#55b0ff"));
				isBoolShow2 = true;
			}
			
			strPhone = mUserPhone.getText().toString();
			if(strPhone.length()<11){
//				ToastUtil.showToast(mContext, "请输入正确的手机号");
			}else{
				timeGetCode.start();
				MobclickAgent.onEvent(RegisterActivity.this, "register_captchas_click");
				//获取验证码
				new AppApi().PhoneCode(mContext, strPhone, this);
				ToastUtil.showToast(mContext, "验证码已发送\n请注意查收");
				
			}
			break;
		case R.id.getVoiceCode:
			if(isBoolShow2 && isBoolShow1){
				if(strPhone.length()<11){
					ToastUtil.showToast(mContext, "请输入正确的手机号");
				}else{
					
					voiceGetShow.setVisibility(View.VISIBLE);
					MobclickAgent.onEvent(RegisterActivity.this, "register_speechcaptchas_click");
					voiceTimeGetCode.start();
					getVoiceDialog();
					
				}
				
			}
			
			break;
		case R.id.car_protocol:  // 车大师协议   直接跳入web
			Intent intent = new Intent(RegisterActivity.this,WebViewShowActivity.class);
			intent.putExtra(WebViewShowActivity.WEBVIEW_TEXT_TAG, "车大师协议");
			intent.putExtra(WebViewShowActivity.CAR_AGREEMENT_TAG, Config.CAR_PROTECOL);
			startActivity(intent);
			break;
		case R.id.check_xieyi:
			if(mAgree){
				sureXieYi.setImageResource(R.drawable.btn_xieyi_none);
				mAgree = false;
			}else{
				sureXieYi.setImageResource(R.drawable.btn_xieyi);
				mAgree = true;
			}
			break;
		case R.id.btnNext:  
			if(mAgree){   
				
				strPhone = mUserPhone.getText().toString();
				String phoneCode =  mPhoneCode.getText().toString().trim();
				String InvateCode = mInvateCode.getText().toString().trim();
				if(!CheckUtil.isEmpty(strPhone) && strPhone.length() == 11){
					if(!CheckUtil.isEmpty(phoneCode)){
						if(!CheckUtil.isEmpty(InvateCode)){
							new AppApi().LoginOrRegister(mContext, strPhone, phoneCode,
									InvateCode, this);
							RotateShowProgressDialog.ShowProgressOn(mContext, false);
						}else{
							ToastUtil.showToast(mContext, "请输入邀请码");
						}
					}else{
						ToastUtil.showToast(mContext, "请输入验证码");
					}
				}else{
					ToastUtil.showToast(mContext, "请输入正确的手机号");
				}
				
//				openActivity(RegisterActivity2.class);
				
			}else{
				ToastUtil.showToast(mContext, "您不同意《车大师协议》，不能进行注册");
			}
			break;
		}
	}
	

	@Override
	public void onSuccess(Action method, Object obj) {
		// TODO Auto-generated method stub
		switch (method) {
		case PHONECODE_NOSIGN:
			Validate_Code bean = (Validate_Code) obj;
			LogUtils.i("验证码-----------"+bean.toString());
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			break;
		case VOICE_NOSIGN:
			VoiceResult result = (VoiceResult) obj;
			int isVoiceShow = result.getResult().getIsShow();
			if(isVoiceShow == 0){
				isBoolShow1 = false;
				mVoiceCode.setVisibility(View.GONE);
			}else if(isVoiceShow == 1){
				isBoolShow1 = true;
				mVoiceCode.setVisibility(View.VISIBLE);
			}
			LogUtils.i("是否显示验证码-----------"+result.toString());
			break;
		case LOGIN_NOSIGN:
			LoginOrRegist lo_gi = (LoginOrRegist) obj;
			UserBean userBean = lo_gi.getResult();
			int isNewUser = userBean.getIsNewUser();   // 可通过 它 进行 判断      0 需要完善信息 , 1 或其它的 不需要
			String ret = lo_gi.getRet();
			LogUtils.i("注册信息-----------"+lo_gi.toString());
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			if("10000".equals(ret)){
				if(isNewUser != 0){
					ToastUtil.showToast(mContext, "您已注册过，请\n直接登陆");
				}else{
					/**
					 * 保存已注册状态
					 */
					MySharedPreferences.save(mContext, MySharedPreferences.USER_ID, userBean.getCrmUser().getId()+"");
					MySharedPreferences.save(mContext, MySharedPreferences.USER_PHONE, userBean.getCrmUser().getPhone()+"");
					mSession.saveKey(Config.USER_ID, userBean.getCrmUser().getId()+"");
					mSession.saveKey(Config.USER_PHONE, userBean.getCrmUser().getPhone()+"");
					Gson gson = new Gson();
					String user_message = gson.toJson(userBean);
					mSession.saveKey(Config.USER_BEAN, user_message);
					openActivity(RegisterActivity2.class);
					finish();
				}
			}
			break;
		}
	}



	@Override
	public void onError(Action method, Object statusCode) {
		// TODO Auto-generated method stub
		switch (method) {
		case NETWORK_FAILED:
			if(AppApi.ERROR_NETWORK_FAILED.equals(statusCode)){
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_no));
			}
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			break;
		case LOGIN_NOSIGN:
			if(statusCode instanceof ResponseErrorMessage){
				//后台返回了错误码和错误信息
				ToastUtil.showToast(mContext, ((ResponseErrorMessage)statusCode).getMsg()+"" );
			} else if(statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode) ){
				//网络超时
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_timeout));
			}
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			break;
		case VOICE_NOSIGN:
			if(statusCode instanceof ResponseErrorMessage){
				//后台返回了错误码和错误信息
				ToastUtil.showToast(mContext, ((ResponseErrorMessage)statusCode).getMsg()+"" );
			} else if(statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode) ){
				//网络超时
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_timeout));
			}
			break;
		case PHONECODE_NOSIGN:
			if(statusCode instanceof ResponseErrorMessage){
				//后台返回了错误码和错误信息
				ToastUtil.showToast(mContext, ((ResponseErrorMessage)statusCode).getMsg()+"" );
			} else if(statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode) ){
				//网络超时
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_timeout));
			}
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			break;
		
		default:
			break;
		}
	}

	class TimeCount extends CountDownTimer {       
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval); //参数依次为总时长,和计时的时间间隔，单位为：毫秒
        }

        @Override
        public void onFinish() { //计时完毕时触发
        	mGetCode.setText("点击重新获取");
        	mGetCode.setTextColor(Color.parseColor("#ff4359"));
        	mGetCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) { //计时过程显示
        	mGetCode.setClickable(false);
        	mGetCode.setText(millisUntilFinished / 1000 + "秒后重发");
        	mGetCode.setTextColor(Color.parseColor("#cccccc"));
        }

    }

	class VoiceTimeCount extends CountDownTimer {       
		public VoiceTimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval); //参数依次为总时长,和计时的时间间隔，单位为：毫秒
		}
		
		@Override
		public void onFinish() { //计时完毕时触发
			voiceGetShow.setVisibility(View.GONE);
			mVoiceCode.setClickable(true);
			voiceTextShow.setTextColor(Color.parseColor("#5ab2ff"));
		}
		
		@Override
		public void onTick(long millisUntilFinished) { //计时过程显示
			mVoiceCode.setClickable(false);
			voiceGetShow.setText("获取中..."+ millisUntilFinished / 1000 + "S");
			voiceTextShow.setTextColor(Color.parseColor("#999999"));
		}
		
	}
	
	//语音验证码
		private void getVoiceDialog(){
			View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_voice_code_item, null);
			
			 final Dialog dialog = new AlertDialog.Builder(mContext).create();
	        dialog.show();

	        dialog.getWindow().setContentView(view);
			
			View btnSure = view.findViewById(R.id.btn_sure);
			//确认按钮
			btnSure.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					dialog.cancel();
					
				}
			});
		}


	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		if(arg0.length()>0){
			mGetCode.setTextColor(Color.parseColor("#ff4359"));
		}else{
			mGetCode.setTextColor(Color.parseColor("#999999"));
		}
		
	}
	
}
