package com.tuanche.askforanswer.source.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.tuanche.api.utils.RotateShowProgressDialog;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.source.bean.AwardMoney;
import com.tuanche.askforanswer.source.bean.MonentyGet;
import com.tuanche.askforanswer.source.bean.MoneyResult;
/**
 * 收益
 * @author zpf
 *
 */
public class AwardMoneyActivity extends BaseActivity implements ApiRequestListener,OnClickListener{

	
	private View goBack;
	/**
	 * 收益余额
	 */
	private TextView mMoneyCanGet;
	/**
	 * 累计收益
	 */
	private TextView mMoneyAll;
	/**
	 * 累计提现
	 */
	private TextView mMoneyGot;
	/**
	 * 计算标准
	 */
	private View moneyDetail;
	/**
	 * 本次可提现现金
	 */
	private TextView canGetMoney;
	private float mGetMoney;
	/**
	 * 答题收益
	 */
	private TextView answerMoney;
	/**
	 * 系统奖励
	 */
	private TextView systemMoney;
	
	
	private View btnRequest;
	
	
	String calUrl;
	
	/**
	 *  0：没有完善信息，  1：完善信息
	 */
	private int mBank_tag = 0 ;   
	
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_award_money);
		
		getViews();
		setViews();
		setListeners();
		
	    new AppApi().MONEY_All(mContext, this.useId+"", this);
	    RotateShowProgressDialog.ShowProgressOn(mContext, false);
	    calUrl = getIntent().getStringExtra("calUrl");

	}
	
	@Override
	public void getViews() {
		// TODO Auto-generated method stub
		goBack = findViewById(R.id.go_back);
		mMoneyCanGet = (TextView) findViewById(R.id.money_can_get);
		mMoneyAll = (TextView) findViewById(R.id.money_all);
		mMoneyGot = (TextView) findViewById(R.id.money_Got);
		moneyDetail = findViewById(R.id.money_datail);
		canGetMoney = (TextView) findViewById(R.id.can_get_money);
		btnRequest = (TextView) findViewById(R.id.btnRequest);
		answerMoney = (TextView) findViewById(R.id.answer_money_show);
		systemMoney = (TextView) findViewById(R.id.system_money_show);
		
	}

	@Override
	public void setViews() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		goBack.setOnClickListener(this);
		moneyDetail.setOnClickListener(this);
		btnRequest.setOnClickListener(this);
	}

	@SuppressWarnings("static-access")
	@Override
	public void onSuccess(Action method, Object obj) {
		// TODO Auto-generated method stub
		switch (method) {
		case MONEYS_NOSIGN:
			AwardMoney awardMoney = (AwardMoney) obj;
			MonentyGet moneyBootyGet = awardMoney.getResult().getMyBooty();
			Log.i("awardMoney---------------------", awardMoney.toString());
			
			mMoneyCanGet.setText(moneyBootyGet.getBalance()+"");
			mMoneyAll.setText(moneyBootyGet.getTotal()+"");
			mMoneyGot.setText(moneyBootyGet.getCasheTotal()+"");
			//本次 可提现金额  （要加处理）
			canGetMoney.setText(moneyBootyGet.getBalance()+"元");
			mGetMoney = moneyBootyGet.getBalance();
			answerMoney.setText(moneyBootyGet.getAnswerTotal()+"元");
			systemMoney.setText(moneyBootyGet.getSysTotal()+"元");
			mBank_tag = moneyBootyGet.getBank_tag();
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			break;
		case GETMONEY_NOSIGN:
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			MoneyResult moneyResult = (MoneyResult) obj;
			Log.i("awardMoney---------------------", moneyResult.toString());
			if("10000".equals(moneyResult.getRet())){
				ToastUtil.showToast(mContext, "提现请求，已授理");
				new AppApi().MONEY_All(mContext, this.useId+"", this);
//			    RotateShowProgressDialog.ShowProgressOn(mContext, false);
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
			break;
		case MONEYS_NOSIGN:
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
		case GETMONEY_NOSIGN:
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
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.go_back:
			finish();
			break;
		case R.id.money_datail:
			Intent intent = new Intent(AwardMoneyActivity.this,WebViewShowActivity.class);
			intent.putExtra(WebViewShowActivity.WEBVIEW_TEXT_TAG,"计算标准");
			intent.putExtra(WebViewShowActivity.COUNT_MONEY_TAG,calUrl);
			startActivity(intent);
			break;
		case R.id.btnRequest:
			if(mGetMoney>=100){
				if(mBank_tag == 0){
					dialogBankShow();
				}else{
					dialogShow(mGetMoney+"");
				}
				
			}else{
				ToastUtil.showToast(mContext, "100元以上才可进行提现");
			}
			break;
		}
	}

	/**
	 * 提现弹框
	 * @param money
	 */
	private void dialogShow(final String money){
		
		View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_money_item, null);
		
		 final Dialog dialog = new AlertDialog.Builder(mContext).create();
         dialog.show();

         dialog.getWindow().setContentView(view);
		
         dialog.setCanceledOnTouchOutside(false);
         
		TextView moneyShow = (TextView) view.findViewById(R.id.money_show);
		moneyShow.setText(money+"元");
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
				// TODO Auto-generated method stub
//				ToastUtil.showToast(mContext, "即将发起请求");
				new AppApi().GETMONEY(mContext, useId+"", money, AwardMoneyActivity.this);
				RotateShowProgressDialog.ShowProgressOn(mContext, false);
				dialog.cancel();
			}
		});
	}
	
	/**
	 * 提示 是否完善  银行卡信息
	 * @param money
	 */
	private void dialogBankShow(){
		
		View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_go_bank_view, null);
		
		 final Dialog dialog = new AlertDialog.Builder(mContext).create();
         dialog.show();

         dialog.getWindow().setContentView(view);
		
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
				// TODO Auto-generated method stub
				openActivity(BankInfoActivity.class);
				dialog.cancel();
				finish();
			}
		});
	}
}
