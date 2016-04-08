package com.tuanche.api.widget.shareWidget;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AccessTokenKeeper {

	private static final String KEY_UID = "uid_sina";
	private static final String KEY_ACCESS_TOKEN = "access_token_sina";
	private static final String KEY_EXPIRES_IN = "expires_in_sina";
	
	 /**
     * 保存 Token 对象到 SharedPreferences。
     * 
     * @param context 应用程序上下文环境
     * @param token   Token 对象
     */
	public static void writeSinaAccessToken(Context context, Oauth2AccessToken token){
		if (null == context || null == token){
			return;
		}
		
		SharedPreferences pref = context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putString(KEY_UID, token.getUid());
		editor.putString(KEY_ACCESS_TOKEN, token.getToken());
		editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
		editor.commit();
	}
	
	   /**
     * 从 SharedPreferences 读取 Token 信息。
     * 
     * @param context 应用程序上下文环境
     * 
     * @return 返回 Token 对象
     */
	public static Oauth2AccessToken readSinaAccessToken(Context context){
		if(null == context){
			return null;
		}
		
		Oauth2AccessToken token = new Oauth2AccessToken();
		SharedPreferences pref = context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_APPEND);
		token.setUid(pref.getString(KEY_UID, ""));
		token.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
		token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));
		return token;
	}
	
	 /**
     * 清空 SharedPreferences 中 Token信息。
     * 
     * @param context 应用程序上下文环境
     */
	public static void clear(Context context){
		if (null == context){
			return;
		}
		
		SharedPreferences pref = context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putString(KEY_UID, null);
		editor.putString(KEY_ACCESS_TOKEN, null);
		editor.putLong(KEY_EXPIRES_IN,0);
		editor.clear();
		editor.commit();
	}
}
