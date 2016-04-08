package com.tuanche.askforanswer.source.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.tuanche.api.utils.RotateShowProgressDialog;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.utils.CheckUtil;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.source.bean.BankInfo;
import com.tuanche.askforanswer.source.bean.BankResult;
import com.tuanche.askforanswer.source.bean.CompleteBankInfo;
/**
 * 银行卡信息
 * @author zpf
 *
 */
public class BankInfoActivity extends BaseActivity implements OnClickListener,ApiRequestListener{

	public static final int BANK_TYPE = 111;
	
	/**
	 * 未完善
	 */
	private View mNotComplete;
	
	private View mGoBack;
	
	private View mSelectBankCity;
	
	private View mBankType;
	private TextView mBankTypeText;
	
	/**
	 * 开户行名称
	 */
	private EditText mBankNameText;
	/**
	 * 银行账户名
	 */
	private EditText mBankUserText;
	/**
	 * 银行卡账号
	 */
	private EditText mBankCardNumberText;
	
	
	private View mBtnSubmit;
	
	
	/**
	 * 已完善
	 */
	private View mISComplete;
	
	private TextView comBankName;
	private TextView comBankType;
	private TextView comBankOpenName;
	private TextView comBankUserName;
	private TextView comBankCardNumbers;
	
	
	private TextView province_city;
	
	/**
	 * 省份对应的id
	 */
	private String province_code;
	private String province_name;
	
	/**
	 * 城市对应的id
	 */
	private String city_code;
	private String city_name;
	
	/**
	 * 银行可类型code
	 */
	private String bank_code;
	/**
	 * 银行卡 类型
	 */
	private String bankName;
	
	String bank_type;
	String bank_name;
	String bank_account;
	
	
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bankinfo);
		
		getViews();
		setViews();
		setListeners();
		
	}
	
	
	
	@Override
	public void getViews() {
		// TODO Auto-generated method stub
		
		/**
		 * 未完成注册
		 */
		mNotComplete = findViewById(R.id.isBankShow);
		
		mGoBack = findViewById(R.id.go_back);
		mSelectBankCity = findViewById(R.id.select_bank_city);
		mBankType = findViewById(R.id.select_bank_type);
		mBtnSubmit = findViewById(R.id.btnSubmit);
		mBankTypeText = (TextView) findViewById(R.id.bank_type);
		mBankNameText = (EditText) findViewById(R.id.bank_name);
		mBankUserText = (EditText) findViewById(R.id.bank_user_name);
		mBankCardNumberText = (EditText) findViewById(R.id.bank_card_number);
		province_city = (TextView) findViewById(R.id.province_city);
		
		/**
		 * 已完成注册
		 */
		mISComplete = findViewById(R.id.isComplete);
		
		comBankName = (TextView) findViewById(R.id.complete_bank_open_city);
		comBankType = (TextView) findViewById(R.id.complete_bank_type);
		comBankOpenName = (TextView) findViewById(R.id.complete_bank_open);
		comBankUserName = (TextView) findViewById(R.id.complete_bank_user_name);
		comBankCardNumbers = (TextView) findViewById(R.id.complete_bank_numbers);
		
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		
		String pro_code = intent.getStringExtra("province_code");
		String pro_name = intent.getStringExtra("province_name");
		String cit_code = intent.getStringExtra("city_code");
		String cit_name = intent.getStringExtra("city_name");
		
		if(!CheckUtil.isEmpty(pro_code)){
			province_code = pro_code;
		}
		if(!CheckUtil.isEmpty(pro_name)){
			province_name = pro_name;
		}
		if(!CheckUtil.isEmpty(cit_code)){
			city_code = cit_code;
		}
		if(!CheckUtil.isEmpty(cit_name)){
			city_name = cit_name;
		}
		
		if(!CheckUtil.isEmpty(province_name) && !CheckUtil.isEmpty(city_name)){
			province_city.setText(province_name+"  "+city_name);
		}
		
		
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void setViews() {
		// TODO Auto-generated method stub
		new AppApi().BANK_INFO(mContext, this.useId+"", this);
		RotateShowProgressDialog.ShowProgressOn(mContext, false);
	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		mGoBack.setOnClickListener(this);
		
		mSelectBankCity.setOnClickListener(this);
		mBankType.setOnClickListener(this);
		mBtnSubmit.setOnClickListener(this);
		
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.go_back:
			finish();
			break;
		case R.id.select_bank_city:
			openActivity(SelectBank_Activity.class);
			break;
		case R.id.select_bank_type:
			Intent intent = new Intent(mContext,BankTypeActivity.class);
			startActivityForResult(intent, BANK_TYPE);
			break;
		case R.id.select_bank_card_number:
			
			break;
		case R.id.btnSubmit:
			bank_type = mBankNameText.getText().toString().trim();
			bank_name = mBankUserText.getText().toString().trim();
			bank_account = mBankCardNumberText.getText().toString().trim();
			if(!CheckUtil.isEmpty(bank_type)){
				if(!CheckUtil.isEmpty(bank_name)){
					if(!CheckUtil.isEmpty(bank_account)){
						if(!CheckUtil.isEmpty(province_code)){
							new AppApi().UPDATE_BANK_INFO(mContext, this.useId+"", bank_type, bank_name, bank_account, bank_code, province_code, city_code, this);
							RotateShowProgressDialog.ShowProgressOn(mContext, false);
						}else{
							ToastUtil.showToast(mContext, "请选择银行卡开通城市");
						}
					}else{
						ToastUtil.showToast(mContext, "请填写银行卡账号");
					}
				}else{
					ToastUtil.showToast(mContext, "请填写银行账户名");
				}
			}else{
				ToastUtil.showToast(mContext, "请填写开户行名称");
			}
			
			break;

		default:
			break;
		}
	}

	
	@Override
	public void onSuccess(Action method, Object obj) {
		switch (method) {
		case BANKINFO_NOSIGN:
			BankInfo bean = (BankInfo) obj;
			BankResult bankResult = bean.getResult();
			int status = bankResult.getStatus();
			Log.i("银行卡信息", bean.toString()+"");
			if(status == 1){ // status = 1   银行信系已完善    直接展示数据
				dataShowView(bankResult);
			}else{
				mNotComplete.setVisibility(View.VISIBLE);
			}
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			break;
		case UPDATEBANK_NOSIGN:
			CompleteBankInfo bankInfo = (CompleteBankInfo) obj;
			if("10000".equals(bankInfo.getRet())){
				ToastUtil.showToast(mContext, "恭喜你，信息提交成功 ！");
				showSuccessView();
			}
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			break;
		}
		
	}

	private void showSuccessView(){
		mNotComplete.setVisibility(View.GONE);
		mISComplete.setVisibility(View.VISIBLE);
		comBankName.setText(province_name+" "+ city_name);
		comBankType.setText(bankName);
		comBankOpenName.setText(bank_type);
		comBankUserName.setText(bank_name);
		comBankCardNumbers.setText(bank_account);
		
	}
	
	private void dataShowView(BankResult bankResult){
		
		mISComplete.setVisibility(View.VISIBLE);
		
		String openCity = null;
		String province = bankResult.getProvince();
		String city = bankResult.getCity();
		if(!CheckUtil.isEmpty(city)){
			openCity = province+ "  " + city;
		}else{
			openCity = province;
		}
		comBankName.setText(openCity);
		comBankType.setText(bankResult.getTenpayBankType());
		comBankOpenName.setText(bankResult.getBankType());
		comBankUserName.setText(bankResult.getBankName());
		comBankCardNumbers.setText(bankResult.getBankAccount());
		
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
		case BANKINFO_NOSIGN:
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
		case UPDATEBANK_NOSIGN:
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (resultCode) {
		case BankTypeActivity.RESPOSE_SELECT__CODE:
			bankName = data.getStringExtra(BankTypeActivity.RESPOSE_SELECT_BANK_NAME);
			mBankTypeText.setText(bankName);
			
			bank_code = data.getStringExtra(BankTypeActivity.RESPOSE_SELECT_BANK_CODE);
			
			break;

		default:
			break;
		}
		
		
		
	}
	
	
	
}
