package com.tuanche.askforanswer.source.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.source.adapter.BankTypeAdapter;
import com.tuanche.askforanswer.source.bean.BankTypeAll;
import com.tuanche.askforanswer.source.bean.BankTypeEach;
/**
 * 银行类型
 * @author zpf
 *
 */
public class BankTypeActivity extends BaseActivity implements OnClickListener,ApiRequestListener{

	public static final int RESPOSE_SELECT__CODE = 123;
	public static final String  RESPOSE_SELECT_BANK_NAME = "bank_name";
	public static final String  RESPOSE_SELECT_BANK_CODE = "bank_code";
	
	private View mGoBack;
	private ListView typeList;
	private BankTypeAdapter typeAdapter;
	
	private List<BankTypeEach> typeEachs;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bank_type);
		
		getViews();
		setViews();
		setListeners();
		
		
	}
	
	@Override
	public void getViews() {
		// TODO Auto-generated method stub
		mGoBack = findViewById(R.id.go_back);
		typeList = (ListView) findViewById(R.id.bank_type_list);
		
		
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void setViews() {
		// TODO Auto-generated method stub
		new AppApi().BANK_TYPE_INFO(mContext, this);
	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		mGoBack.setOnClickListener(this);
		
		typeList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,BankInfoActivity.class);
				intent.putExtra(RESPOSE_SELECT_BANK_NAME, typeEachs.get(arg2).getBank_name()+"");
				intent.putExtra(RESPOSE_SELECT_BANK_CODE, typeEachs.get(arg2).getBank_code()+"");
				setResult(RESPOSE_SELECT__CODE, intent);
				finish();
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.go_back:
			finish();
			break;
		}
	}

	@Override
	public void onSuccess(Action method, Object obj) {
		// TODO Auto-generated method stub
		switch (method) {
		case BANKTYPE_NOSIGN:
			BankTypeAll bean = (BankTypeAll) obj;
			typeEachs = bean.getResult().getBrankType();

			typeAdapter = new BankTypeAdapter(mContext, typeEachs);
			typeList.setAdapter(typeAdapter);
			
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
		case BANKTYPE_NOSIGN:
			if(statusCode instanceof ResponseErrorMessage){
				//后台返回了错误码和错误信息
				ToastUtil.showToast(mContext, ((ResponseErrorMessage)statusCode).getMsg()+"" );
			} else if(statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode) ){
				//网络超时
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_timeout));
			}
			break;
		}
	}

}
