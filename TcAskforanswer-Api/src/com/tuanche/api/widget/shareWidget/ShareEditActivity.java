package com.tuanche.api.widget.shareWidget;

import java.io.IOException;

import com.tuanche.api.R;
import com.sina.weibo.sdk.api.share.*;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.tencent.weibo.sdk.android.api.WeiboAPI;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.*;
import com.tencent.weibo.sdk.android.network.HttpCallback;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.*;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class ShareEditActivity extends Activity {

	private int mFlag;
	private String mShareImageUrl;
	private String mShareContent;
	
	private TextView mLastText;
	private TextView mBackTv;
	private EditText mEditContent;
	private Button mBtSend;

	/** 新浪微博分享接口*/
	StatusesAPI mStatusesAPI;

	//腾讯微博发布微博回调函数
	private HttpCallback mCallBack;
	/** 腾讯微博分享接口*/
	WeiboAPI	mWeiboAPI;
	private String mAccessToken; 
	private String requestFormat = "json";
	private double longitude = 0d;  //初始
	private double latitude = 0d;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.share_edit_activity);

		mFlag=getIntent().getIntExtra("flag",0);
		mShareImageUrl = getIntent().getStringExtra("picUrl");
		mShareContent = getIntent().getStringExtra("text");
		
		init();

		//腾讯微博用户访问令牌
		mAccessToken = Util.getSharePersistent(getApplicationContext(),
				"ACCESS_TOKEN");
	}

	private void init(){
		mLastText = (TextView) findViewById(R.id.last_text);// 字数
		mEditContent = (EditText) findViewById(R.id.edt_text_content);// 输入
		mEditContent.addTextChangedListener(new TextWatcher(){
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mLastText.setText(String.valueOf(140 - mEditContent.length()));
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}	
		});

		if(mShareContent.length() > 140)
		{
			//连接地址保持完整. 获取其长度
			int httpLength = mShareContent.substring(mShareContent.indexOf("http://")).length();
			//需要截取去掉的长度为多出140的个数.. 添加省略号
			mShareContent = mShareContent.substring(0, 140-httpLength - 3) + "..." + mShareContent.substring(mShareContent.indexOf("http://"));
		}
		mEditContent.setText(mShareContent);

		mBtSend=(Button)findViewById(R.id.btn_send);
		mBtSend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				share();
			}

		} );

		/**
	     * 腾讯微博回调接口。
	     */
		mCallBack = new HttpCallback() {
			@Override
			public void onResult(Object object) {
				ModelResult result = (ModelResult) object;
//				if(loadingWindow!=null && loadingWindow.isShowing()){
//
//					loadingWindow.dismiss();
//				}
				if(result!=null && result.isSuccess()){
					Toast.makeText(ShareEditActivity.this,
							"分享成功!", Toast.LENGTH_SHORT)
							.show();
					finish();
//					Intent i = new Intent(ShareEditActivity.this,GeneralDataShowActivity.class);
//					i.putExtra("data", result.getObj().toString());					
//					startActivity(i);
				}else{
					Toast.makeText(ShareEditActivity.this,
							"分享失败!", Toast.LENGTH_SHORT)
							.show();
					finish();
				}
				
			}
		};
	}



	public void share()
	{
		if(mFlag == 0)
		{
			mStatusesAPI = new StatusesAPI(AccessTokenKeeper.readSinaAccessToken(ShareEditActivity.this));
			shareBySina();
		}else{

			AccountModel account = new AccountModel(mAccessToken);
			mWeiboAPI = new WeiboAPI(account);
			shareByTenXun();
		}
	}

	public void shareBySina(){
		if(mShareImageUrl != null){
			mStatusesAPI.uploadUrlText(mShareContent, mShareImageUrl, null, null, null, mSinaListener);
		}else{
			mStatusesAPI.update(mShareContent, null, null, mSinaListener);
		}
	}


	public void shareByTenXun(){
		if(mShareImageUrl != null){
			mWeiboAPI.addWeibo(ShareEditActivity.this, mShareContent, requestFormat, longitude, latitude, 0, 0, mCallBack, null, BaseVO.TYPE_JSON);
		}		else{
			mWeiboAPI.addPicUrl(ShareEditActivity.this, mShareContent, requestFormat, longitude, latitude, mShareImageUrl, 0, 0, mCallBack, null, BaseVO.TYPE_JSON);
		}
	}
	 /**
     * 新浪微博 OpenAPI 回调接口。
     */
	private RequestListener mSinaListener =  new RequestListener() {	
		@Override
		public void onComplete(String arg0) {
			//							dismissProgressDialog();
			//				Intent intent = new Intent();
			//				intent.putExtra("message", arg0);
			//				setResult(0, intent);
			//				finish();
			Toast.makeText(ShareEditActivity.this, "分享成功!", Toast.LENGTH_SHORT).show();
			finish();
		}

		@Override
		public void onWeiboException(WeiboException arg0) {
			//							dismissProgressDialog();
			//				Intent intent = new Intent();
			//				intent.putExtra("message", arg0.getMessage());
			//				setResult(1, intent);
			//				finish();	
			Log.i("ShareEditActivity",arg0.getMessage());
			Toast.makeText(ShareEditActivity.this, "分享失败!", Toast.LENGTH_SHORT).show();
			finish();
		}
	};
}
