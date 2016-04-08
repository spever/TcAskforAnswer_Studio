package com.tuanche.askforanswer.source.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.utils.RotateShowProgressDialog;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.utils.CheckUtil;

@SuppressLint("SetJavaScriptEnabled") public class WebViewShowActivity extends BaseActivity implements ApiRequestListener,OnClickListener{

	/**
	 * 标题
	 */
	public  static final String WEBVIEW_TEXT_TAG = "text_title";
	/**
	 * 系统消息
	 */
	public  static final String SYSTEM_MESSAGE_TAG = "system_message";
	/**
	 * 车大师协议
	 */
	public static final String CAR_AGREEMENT_TAG = "car_agreement";
	/**
	 * 答题收益，计算标准
	 */
	public static final String COUNT_MONEY_TAG = "count_money";
	/**
	 * 奖励补贴规则
	 */
	public static final String REWARD_RULE_TAG = "reward_rule";
	
	
	private View goBack;
	private TextView webTitle;
	private WebView mWebView;
	
	private String countTitle; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_webview_show);
		
		getViews();
		setViews();
		setListeners();
		
	}
	
	@Override
	public void getViews() {
		// TODO Auto-generated method stub
		goBack = findViewById(R.id.go_back);
		mWebView = (WebView) findViewById(R.id.webview_show);
		webTitle = (TextView) findViewById(R.id.webview_title);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		mWebView.requestFocus();
		mWebView.setWebViewClient(new MyWebViewClient());
		
		Intent intent = getIntent();
		/**
		 * 公用
		 */
		countTitle = intent.getStringExtra(WEBVIEW_TEXT_TAG);
		if(!CheckUtil.isEmpty(countTitle)){
			webTitle.setText(countTitle);
		}
		
		String car_agreement = intent.getStringExtra(CAR_AGREEMENT_TAG);
		if(!CheckUtil.isEmpty(countTitle)){
		}
		
		String count_money = intent.getStringExtra(COUNT_MONEY_TAG);
		if(!CheckUtil.isEmpty(count_money)){
			mWebView.loadUrl(count_money);
			LogUtils.e("积分兑换url++++++++++++++++++"+count_money);
		}
		String meseageTag = intent.getStringExtra(SYSTEM_MESSAGE_TAG);
		if(!CheckUtil.isEmpty(meseageTag)){
			mWebView.loadUrl(meseageTag);
		}
		String reward_rule = intent.getStringExtra(REWARD_RULE_TAG);
		if(!CheckUtil.isEmpty(reward_rule)){
			mWebView.loadUrl(reward_rule);
		}
		
	}

	@Override
	public void setViews() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		goBack.setOnClickListener(this);
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
		
	}

	@Override
	public void onError(Action method, Object statusCode) {
		// TODO Auto-generated method stub
		
	}

	
	class MyWebViewClient extends WebViewClient{
		
//		 public boolean shouldOverrideUrlLoading(WebView view,String url){  //控制 在当前页面打开   ：暂不需要
//			
//			 loadHistoryUrls.add(url);  
//			 
//			 return true;
//			 }
		
	
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
			RotateShowProgressDialog.ShowProgressOn(mContext, false);

		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}

		}
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			
		}
		
	}
	

}
