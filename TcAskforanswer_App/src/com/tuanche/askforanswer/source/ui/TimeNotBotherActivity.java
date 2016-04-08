package com.tuanche.askforanswer.source.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.utils.RotateShowProgressDialog;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.source.bean.TimeNotResult;
import com.tuanche.askforanswer.source.view.SelectTimePopwindow;
import com.tuanche.askforanswer.source.view.SwitchButton;
/**
 * 免打扰
 * @author zpf
 *
 */
public class TimeNotBotherActivity extends BaseActivity implements OnClickListener,ApiRequestListener{

	private View mGoBack;
	/**
	 * 免打扰开关
	 */
	private SwitchButton mSwitchButton;
	private View mTimeSelectShow;
	private SelectTimePopwindow timePopwindow;
	private TimePicker timePickerStart,timePickerEnd;
	
	private TextView time_show;
	
	private Boolean isViewShow = false;
	
	private String notTimeDuring;
	private int switch_status;
	
	private int startHour, startMinute , endHour , endMinute;
	
	/**
	 * 是否  把当前页面的  免打扰时间  传给 上一级页面
	 */
	private Boolean isForResult = false;
	private Boolean isForOpen = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_time_not_bother);
		
		getViews();
		setViews();
		setListeners();
		
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.go_back:
//				Intent intent = new Intent(this,MineActivity.class);	
//				intent.putExtra("TimeForShow", isForResult);
//				if(isForResult){
//					intent.putExtra("TimeForResult", notTimeDuring);
//				}else{
//					intent.putExtra("TimeForResult", "");
//				}
//				startActivity(intent);
				finish();
			break;
		case R.id.time_not_show: //是否显示 选择 器
			//实例化SelectPicPopupWindow
			timePopwindow = new SelectTimePopwindow(TimeNotBotherActivity.this, itemsOnClick);
			
			timePickerStart = timePopwindow.mTimePickerStart;
			timePickerEnd = timePopwindow.mTimePickerEnd;
			
			//显示窗口
			if(isViewShow){
				timePopwindow.showAtLocation(TimeNotBotherActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
			}
			break;
		}
	}
	
	@Override
	public void getViews(){
		// TODO Auto-generated method stub
		mGoBack = findViewById(R.id.go_back);
		
		Intent intent = getIntent();
		notTimeDuring = intent.getStringExtra("notBrotherText");
		switch_status = intent.getIntExtra("switch_status", 0);
		
		mSwitchButton = (SwitchButton) findViewById(R.id.switch_bother_button);
		mTimeSelectShow = findViewById(R.id.time_not_show);
		time_show = (TextView) findViewById(R.id.time_select_show);
		
		
	}

	@Override
	public void setViews() {
		// TODO Auto-generated method stub
		if(switch_status == 1){
			mSwitchButton.setChecked(true);
			isViewShow = true;
			time_show.setText(notTimeDuring);
		}else{
			isForResult = false;
		}
		
	}
	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		mGoBack.setOnClickListener(this);
		mSwitchButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@SuppressWarnings("static-access")
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					isViewShow = true;
				}else{
					isViewShow = false;
					new AppApi().NOT_TIME_BROTHER(mContext, useId+"", startHour+":"+startMinute, endHour+":"+endMinute, 0, TimeNotBotherActivity.this);
					RotateShowProgressDialog.ShowProgressOn(mContext, false);
				}
			}
		});
		mTimeSelectShow.setOnClickListener(this);
	}

	 //为弹出窗口实现监听类
    private OnClickListener itemsOnClick = new OnClickListener(){

		@SuppressWarnings("static-access")
		public void onClick(View v) {
			timePopwindow.dismiss();
			switch (v.getId()) {
			case R.id.btn_sure:	  //完成时间选择			
				startHour = timePickerStart.getCurrentHour();
				startMinute = timePickerStart.getCurrentMinute();
				endHour = timePickerEnd.getCurrentHour();
				endMinute = timePickerEnd.getCurrentMinute();
				notTimeDuring = startHour+":"+startMinute+" —— "+endHour+":"+endMinute;
				time_show.setText(notTimeDuring);
				Log.e("notTimeDuring*************************", ""+notTimeDuring);
				new AppApi().NOT_TIME_BROTHER(mContext, useId+"", startHour+":"+startMinute, endHour+":"+endMinute, 1, TimeNotBotherActivity.this);
				RotateShowProgressDialog.ShowProgressOn(mContext, false);
				break;
			default:
				break;
			}
		}
    };
	
	@Override
	public void onSuccess(Action method, Object obj) {
		// TODO Auto-generated method stub
		switch (method) {
		case TIMEBOTHER_NOSIGN:
			TimeNotResult bean = (TimeNotResult) obj;
			if("10000".equals(bean.getRet()) && isViewShow){
				isForResult = true;
			}else{
				isForResult = false;
			}
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			LogUtils.i("免打扰-----------"+bean.toString());
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
			break;
		case TIMEBOTHER_NOSIGN:
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

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if(keyCode == KeyEvent.KEYCODE_BACK){
//		Intent intent = new Intent(this,MineActivity.class);	
//		intent.putExtra("TimeForShow", isForResult);
//		if(isForResult){
//			intent.putExtra("TimeForResult", notTimeDuring);
//		}else{
//			intent.putExtra("TimeForResult", "");
//		}
//		startActivity(intent);
//		finish();
//		}
//		return super.onKeyDown(keyCode, event);
//		
//	}

}
