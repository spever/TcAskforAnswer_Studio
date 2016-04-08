package com.tuanche.askforanswer.source.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.tuanche.api.utils.RotateShowProgressDialog;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.source.adapter.CityListAdapter;
import com.tuanche.askforanswer.source.bean.CityAll;
import com.tuanche.askforanswer.source.bean.CityEach;
/**
 * 银行卡开通城市
 * @author zpf
 *
 */
public class BankAboutCity extends BaseActivity implements OnClickListener,ApiRequestListener{

	private View mGoback;
	/**
	 * 每次进入  即 替换成 新的 title
	 */
	private TextView title;   
	 
	/**
	 * 省份对应的id
	 */
	private String province_code;
	private String province_name;
	
	private ListView mListView;
	
	private CityListAdapter cityListAdapter;
	
	private List<CityEach> cityLsit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bank_about_city);
		
		getViews();
		setViews();
		setListeners();
		
	}
	
	
	@Override
	public void getViews() {
		// TODO Auto-generated method stub
		mGoback = findViewById(R.id.go_back);
		title = (TextView) findViewById(R.id.province_title);
		Intent intent = getIntent();
		province_name = intent.getStringExtra(SelectBank_Activity.PROVINCE_NAME);
		title.setText(province_name);
		
		province_code = intent.getStringExtra(SelectBank_Activity.PROVINCE_CODE);
		
		mListView = (ListView) findViewById(R.id.each_city_list);
		
		
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void setViews() {
		// TODO Auto-generated method stub
		new AppApi().CITY_BANK_All(mContext, province_code, this);
		RotateShowProgressDialog.ShowProgressOn(mContext, false);
	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		mGoback.setOnClickListener(this);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BankAboutCity.this,BankInfoActivity.class);
				intent.putExtra("province_code", province_code);
				intent.putExtra("province_name", province_name);
				intent.putExtra("city_code", cityLsit.get(arg2).getCity_code());
				intent.putExtra("city_name", cityLsit.get(arg2).getCity_name());
				startActivity(intent);
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
		default:
			break;
		}
	}

	@Override
	public void onSuccess(Action method, Object obj) {
		// TODO Auto-generated method stub
		switch (method) {
		case CITYBANK_NOSIGN:
			CityAll bean = (CityAll) obj;
			cityLsit =  bean.getResult().getCity();
			cityListAdapter = new CityListAdapter(BankAboutCity.this, cityLsit);
			mListView.setAdapter(cityListAdapter);
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
//			Log.i("城市========================", bean.toString());
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
		case CITYBANK_NOSIGN:
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

}
