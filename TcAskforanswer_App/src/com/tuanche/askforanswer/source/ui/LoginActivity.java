package com.tuanche.askforanswer.source.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
 * 登陆
 * @author zpf
 *
 */
public class LoginActivity extends BaseActivity implements OnClickListener,ApiRequestListener,TextWatcher{

	private TimeCount timeGetCode;
	private VoiceTimeCount voiceTimeGetCode;
	
	private View mGoback;
	private View mVoiceCode;
	private View mBtnNext;
	private TextView mGetCode;
	private EditText mUserPhone;
	private EditText mPhoneCode;
	
	
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
		setContentView(R.layout.activity_login_layout);
		
		getViews();
		setViews();
		setListeners();
		
		
	}
	
	
	@Override
	public void getViews() {
		// TODO Auto-generated method stub
		timeGetCode = new TimeCount(60000, 1000);
		voiceTimeGetCode = new VoiceTimeCount(60000, 1000);
		
		mGoback = findViewById(R.id.go_back);
		mGetCode = (TextView) findViewById(R.id.btn_getCode);
		mUserPhone = (EditText) findViewById(R.id.master_phone);
		mPhoneCode = (EditText) findViewById(R.id.master_code);
		mVoiceCode = findViewById(R.id.getVoiceCode);
		mBtnNext = findViewById(R.id.btnNext);
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
		mGoback.setOnClickListener(this);
		mGetCode.setOnClickListener(this);
		mVoiceCode.setOnClickListener(this);
		
		mBtnNext.setOnClickListener(this);
		
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
		case R.id.btn_getCode:
			
			if(isBoolShow1){  //首先判断服务器
				voiceTextShow.setTextColor(Color.parseColor("#55b0ff"));
				isBoolShow2 = true;
			}
			strPhone = mUserPhone.getText().toString().trim();
			if(strPhone.length()<11){
//				ToastUtil.showToast(mContext, "请输入正确的手机号");
			}else{
				timeGetCode.start();
				MobclickAgent.onEvent(LoginActivity.this, "login_captchas_click");
				//获取验证码
				new AppApi().PhoneCode(mContext, strPhone, this);
				ToastUtil.showToast(mContext, "验证码已发送\n请注意查收");
			}
			break;
		case R.id.getVoiceCode:
			strPhone = mUserPhone.getText().toString().trim();
			if(isBoolShow2 && isBoolShow1){
				if(strPhone.length()<11){
					ToastUtil.showToast(mContext, "请输入正确的手机号");
				}else{
					voiceGetShow.setVisibility(View.VISIBLE);
					/**
					 * 在这里请求语音验证码接口
					 */
					MobclickAgent.onEvent(LoginActivity.this, "login_speechcaptchas_click");
					voiceTimeGetCode.start();
					getVoiceDialog();
				}
			}
			
			
			break;
		case R.id.btnNext:
			
			strPhone = mUserPhone.getText().toString();
			if(!CheckUtil.isEmpty(strPhone) && strPhone.length()==11){
				if(!CheckUtil.isEmpty(mPhoneCode.getText().toString().trim())){
					new AppApi().Login_denglu(mContext, strPhone, mPhoneCode.getText().toString().trim(), this);
					RotateShowProgressDialog.ShowProgressOn(mContext, false);
				}else{
					ToastUtil.showToast(mContext, "请输入验证码");
				}
			}else{
				ToastUtil.showToast(mContext, "请输入正确的手机号");
			}
			break;
			
		default:
			break;
		}
		
	}

	
	@Override
	public void onSuccess(Action method, Object obj) {
		// TODO Auto-generated method stub
		switch (method) {
		case PHONECODE_NOSIGN:
			Validate_Code bean = (Validate_Code) obj;
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			LogUtils.i("验证码-----------"+bean.toString());
			break;
		case VOICE_NOSIGN:
			VoiceResult result = (VoiceResult) obj;
			int isVoiceShow1 = result.getResult().getIsShow();
			Log.i("wwwwwwwww", isVoiceShow1+"");
			if(isVoiceShow1 == 0){
				isBoolShow1 = false;
				mVoiceCode.setVisibility(View.GONE);
			}else if(isVoiceShow1 == 1){
				isBoolShow1 = true;
				mVoiceCode.setVisibility(View.VISIBLE);
			}
			LogUtils.i("是否显示验证码-----------"+result.toString());
			break;
		case LOGINDENLU_NOSIGN:     
			LoginOrRegist lo_gi = (LoginOrRegist) obj;
			LogUtils.i("注册信息-----------"+lo_gi.toString());
			UserBean userBean = lo_gi.getResult();
			String ret = lo_gi.getRet();
			int isNewUser = userBean.getIsNewUser();   // 可通过 它 进行 判断      0 需要完善信息 , 1 或其它的 不需要
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			if("10000".equals(ret)){
				Log.i("ret=============",ret);
					MySharedPreferences.save(mContext, MySharedPreferences.USER_ID, userBean.getCrmUser().getId()+"");
					MySharedPreferences.save(mContext, MySharedPreferences.USER_PHONE, userBean.getCrmUser().getPhone()+"");
					Gson gson = new Gson();
					String user_message = gson.toJson(userBean);
					mSession.saveKey(Config.USER_BEAN, user_message);
					mSession.saveKey(Config.USER_ID, userBean.getCrmUser().getId()+"");
					mSession.saveKey(Config.USER_PHONE, userBean.getCrmUser().getPhone()+"");
					mSession.saveKey(Config.IDENTIFY,userBean.getCrmUser().getSoldIdentify());
					openActivity(HomeActivity.class);
					closeSoftWare();
					finish();
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
		case LOGINDENLU_NOSIGN:
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
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
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
	
	/**
	 * 关闭软件盘
	 */
	private void closeSoftWare(){
		View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(mContext.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
	}
	
}
