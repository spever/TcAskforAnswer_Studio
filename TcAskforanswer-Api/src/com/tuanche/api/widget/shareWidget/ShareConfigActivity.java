package com.tuanche.api.widget.shareWidget;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.*;
import android.content.*;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.*;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.tuanche.api.R;
import com.sina.weibo.sdk.auth.*;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.Authorize;
import com.tencent.weibo.sdk.android.component.sso.*;

public class ShareConfigActivity extends Activity {

	/** 微博 Web 授权类，提供登陆等功能  */
	private WeiboAuth mWeiboAuth;

	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
	private Oauth2AccessToken mAccessToken_sina;
	private String mAccessToken_tenxun;

	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;
	
	private static final int UPDATE_SINA_WHAT = 1001;
	private static final int UPDATE_Tenxun_WHAT = 1002;
	private  Context mContext = null;
	private SharedPreferences mPref = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.act_share_config);

		init();
	}

	private void init() {
		mContext = ShareConfigActivity.this;
		mPref = getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_APPEND);
		// 创建新浪微博实例
		mWeiboAuth=new WeiboAuth(this,Constants.APP_KEY,Constants.REDIRECT_URL,Constants.SCOPE);
		findViewById(R.id.tv_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mAccessToken_sina = AccessTokenKeeper.readSinaAccessToken(mContext);
		findViewById(R.id.sina_layout).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (mAccessToken_sina == null || "".equals(mAccessToken_sina.getToken())){
							
//							//无网络情况下的提示语
//							if(NetworkUtil.getNetworkType(mContext) == NetworkUtil.NOCONNECTION)
//							{
//								Toast.makeText(mContext, ConstantValues.NO_NETWORK_NOTICE, 0).show();
//								return;
//							}
							applyWeiboAuth();
						
						} else {
							AlertDialog.Builder localBuilder = new AlertDialog.Builder(
									mContext);
							localBuilder.setTitle("提示");
							localBuilder.setMessage("确定要解除绑定吗？");

							localBuilder.setPositiveButton(getString(R.string.button_ok),
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Editor editor = mPref.edit();
											editor.putString("sina_access", null);
											editor.putString("sina_user_name", null);
											editor.putString("sina_user_id",null);
											editor.commit();
											AccessTokenKeeper.clear(
													mContext);
											updateSinaLayoutDisplay();
										}
									});

							localBuilder.setNegativeButton(getString(R.string.button_cancel),
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											if (dialog != null) {
												dialog.dismiss();
											}
										}
									});
							localBuilder.create().show();
						}
					}
				});

		findViewById(R.id.qq_layout).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mAccessToken_tenxun =Util.getSharePersistent(mContext, "ACCESS_TOKEN");
				if(mAccessToken_tenxun == null || "".equals(mAccessToken_tenxun)){				
					//无网络情况下的提示语
//					if(NetworkUtil.getNetworkType(mContext) == NetworkUtil.NOCONNECTION)
//					{
//						Toast.makeText(mContext, ConstantValues.NO_NETWORK_NOTICE, 0).show();
//						return;
//					}
				
					auth(Constants.APP_KEY_TenXun, Constants.APP_KEY_SEC);
				} else {
					AlertDialog.Builder localBuilder = new AlertDialog.Builder(
							mContext);
					localBuilder.setTitle("提示");
					localBuilder.setMessage("确定要解除绑定吗？");

					localBuilder.setPositiveButton(getString(R.string.button_ok),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {	
									Util.clearSharePersistent(mContext);
									Toast.makeText(mContext,"解除绑定成功", Toast.LENGTH_SHORT)
						            .show();
									updateTenxunLayoutDisplay();
								}
							});

					localBuilder.setNegativeButton(getString(R.string.button_cancel),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (dialog != null) {
										dialog.dismiss();
									}
								}
							});
					localBuilder.create().show();
				}
			}
		});

	}
	
	private void applyWeiboAuth(){
		mSsoHandler = new SsoHandler((Activity) mContext, mWeiboAuth);
		mSsoHandler.authorize(new AuthListener());
	}
	
	/**
	 * 新浪微博认证授权回调类。
	 * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
	 *    该回调才会被执行。
	 * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
	 * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onCancel() {
			Toast.makeText(mContext, 
					R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onComplete(Bundle arg0) {
			mAccessToken_sina = Oauth2AccessToken.parseAccessToken(arg0);
			if(mAccessToken_sina.isSessionValid()){
				AccessTokenKeeper.writeSinaAccessToken(mContext, mAccessToken_sina); //保存 Token 对象到 SharedPreferences。
				UsersAPI api = new UsersAPI(mAccessToken_sina);
				api.show(Long.parseLong(mAccessToken_sina.getUid()), new RequestListener() {
					@Override
					public void onComplete(String arg0) {
						try {
							JSONObject json = new JSONObject(arg0);
							Editor editor = mPref.edit();
							editor.putString("sina_access",
									mAccessToken_sina.getToken());
							editor.putString("sina_user_name",
									json.optString("name"));
							editor.putString("sina_user_id",
									mAccessToken_sina.getUid());
							editor.putString(
									"sina_expires_in",
									mAccessToken_sina
											.getExpiresTime() + "");
							editor.commit();
						} catch (JSONException e) {
						}
						mHandler.sendEmptyMessage(UPDATE_SINA_WHAT);
					}

					@Override
					public void onWeiboException(WeiboException arg0) {
						// TODO Auto-generated method stub
						
					}

				});
				}else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = arg0.getString("code");
				String message = getString(R.string.weibosdk_demo_toast_auth_failed);
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(mContext, 
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);

		// Sina SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
		if(mSsoHandler!=null){
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	public void onResume() {
		super.onResume();
		updateSinaLayoutDisplay();
		updateTenxunLayoutDisplay();
	}
	private void auth(long appid, String app_secret) {
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
//				Toast.makeText(mContext, "onWeiboVersionMisMatch",
//						1000).show();
				AuthHelper.unregister(mContext);
				Intent i = new Intent(mContext,Authorize.class);
				startActivityForResult(i, 0);
			}

			//如果授权失败，走这里
			@Override
			public void onAuthFail(int result, String err) {
//				Toast.makeText(mContext, "result : " + result, 1000)
//						.show();
				AuthHelper.unregister(mContext);
			}

			//授权成功，走这里
			//授权成功后，所有的授权信息是存放在WeiboToken对象里面的，可以根据具体的使用场景，将授权信息存放到自己期望的位置，
			//在这里，存放到了applicationcontext中
			@Override
			public void onAuthPassed(String name, WeiboToken token) {
				//Toast.makeText(mContext, "passed", 1000).show();
				
				Util.saveSharePersistent(mContext, "ACCESS_TOKEN", token.accessToken);
				Util.saveSharePersistent(mContext, "EXPIRES_IN", String.valueOf(token.expiresIn));
				Util.saveSharePersistent(mContext, "OPEN_ID", token.openID);
				Util.saveSharePersistent(mContext, "REFRESH_TOKEN", "");
				Util.saveSharePersistent(mContext, "NAME", name);
				Util.saveSharePersistent(mContext, "NICK", name);
				Util.saveSharePersistent(mContext, "CLIENT_ID", Util.getConfig().getProperty("APP_KEY"));
				Util.saveSharePersistent(mContext, "AUTHORIZETIME",
						String.valueOf(System.currentTimeMillis() / 1000l));
				AuthHelper.unregister(mContext);
				mHandler.sendEmptyMessage(UPDATE_Tenxun_WHAT);
			}
		});

		AuthHelper.auth(this, "");
	}
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			int what = msg.what;
			switch (what) {
			case UPDATE_SINA_WHAT:
				updateSinaLayoutDisplay();
				break;
			case UPDATE_Tenxun_WHAT:
				updateTenxunLayoutDisplay();
				break;
			}

		}

	};
	
	private void updateSinaLayoutDisplay() {
		mAccessToken_sina = AccessTokenKeeper.readSinaAccessToken(mContext);
		if (mAccessToken_sina != null && !"".equals(mAccessToken_sina.getToken())){
			((TextView) findViewById(R.id.sina_name)).setText(mPref.getString(
					"sina_user_name", "无名"));
			((TextView) findViewById(R.id.sina_text)).setText(getString(R.string.share_settings_dounbind));
		} else {
			((TextView) findViewById(R.id.sina_name))
			.setText(getString(R.string.share_settings_unbind));
			((TextView) findViewById(R.id.sina_text))
			.setText(getString(R.string.share_settings));
		}
	}
	
	private void updateTenxunLayoutDisplay(){
		mAccessToken_tenxun =Util.getSharePersistent(mContext, "ACCESS_TOKEN");
		if(mAccessToken_tenxun == null || "".equals(mAccessToken_tenxun)){		
			((TextView) findViewById(R.id.qq_name))
			.setText(getString(R.string.share_settings_unbind));
			((TextView) findViewById(R.id.qq_text))
			.setText(getString(R.string.share_settings_tenxun));
		}else{
			String name = Util.getSharePersistent(mContext, "NAME");
			((TextView) findViewById(R.id.qq_name)).setText(name);
			((TextView) findViewById(R.id.qq_text)).setText(getString(R.string.share_settings_dounbind));
		}
	}

}
