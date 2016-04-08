package com.tuanche.api.widget.shareWidget;

import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.Authorize;
import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
import com.tencent.weibo.sdk.android.component.sso.WeiboToken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ShareToTenXunWeibo extends Activity {
	
	private String mShareImageUrl;
	private String mShareContent;
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mShareImageUrl = getIntent().getStringExtra("picUrl");
		mShareContent = getIntent().getStringExtra("text");
		mContext = ShareToTenXunWeibo.this;
		authLogin();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if( requestCode == 0 && resultCode == RESULT_OK){
			startWBShareActivity();
		}
		finish();
	}

	private void authLogin()
	{
		String accessToken =Util.getSharePersistent(mContext, "ACCESS_TOKEN");
		if(accessToken != null && !"".equals(accessToken)){
			startWBShareActivity();
		}
		else{
			auth(Constants.APP_KEY_TenXun, Constants.APP_KEY_SEC);	
		}
	}

	private void auth(long appid, String app_secret) {
		final Context context = this.getApplicationContext();
		//注册当前应用的appid和appkeysec，并指定一个OnAuthListener
		//OnAuthListener在授权过程中实施监听
		AuthHelper.register(this, appid, app_secret, new OnAuthListener() {

			//如果当前设备没有安装腾讯微博客户端，走这里
			@Override
			public void onWeiBoNotInstalled() {
//				Toast.makeText(mContext, "onWeiBoNotInstalled", 1000)
//						.show();
				AuthHelper.unregister(mContext);
				Intent i = new Intent(mContext,Authorize.class);
				startActivityForResult(i, 0);
			}

			//如果当前设备没安装指定版本的微博客户端，走这里
			@Override
			public void onWeiboVersionMisMatch() {
				Toast.makeText(mContext, "onWeiboVersionMisMatch",
						1000).show();
				AuthHelper.unregister(mContext);
				Intent i = new Intent(mContext,Authorize.class);
				startActivityForResult(i, 0);
			}

			//如果授权失败，走这里
			@Override
			public void onAuthFail(int result, String err) {
				Toast.makeText(mContext, "result : " + result, 1000)
						.show();
				AuthHelper.unregister(mContext);
			}

			//授权成功，走这里
			//授权成功后，所有的授权信息是存放在WeiboToken对象里面的，可以根据具体的使用场景，将授权信息存放到自己期望的位置，
			//在这里，存放到了applicationcontext中
			@Override
			public void onAuthPassed(String name, WeiboToken token) {
				Toast.makeText(mContext, "passed", 1000).show();
				//
				Util.saveSharePersistent(context, "ACCESS_TOKEN", token.accessToken);
				Util.saveSharePersistent(context, "EXPIRES_IN", String.valueOf(token.expiresIn));
				Util.saveSharePersistent(context, "OPEN_ID", token.openID);
				Util.saveSharePersistent(context, "REFRESH_TOKEN", "");
				Util.saveSharePersistent(context, "CLIENT_ID", Util.getConfig().getProperty("APP_KEY"));
				Util.saveSharePersistent(context, "AUTHORIZETIME",
						String.valueOf(System.currentTimeMillis() / 1000l));
				AuthHelper.unregister(mContext);
				startWBShareActivity();
			}
		});

		AuthHelper.auth(this, "");
	}
	
	private void startWBShareActivity() {
		Intent intent = new Intent();
		intent.setClass(mContext, ShareEditActivity.class);
		intent.putExtra("flag", 1);
		intent.putExtra("text", mShareContent);
		intent.putExtra("picUrl", mShareImageUrl);
		startActivityForResult(intent, 1);
	}
}
