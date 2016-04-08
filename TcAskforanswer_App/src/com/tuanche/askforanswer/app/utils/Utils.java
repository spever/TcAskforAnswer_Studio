package com.tuanche.askforanswer.app.utils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.tuanche.api.utils.AppUtils;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.app.core.Session;
import com.tuanche.askforanswer.source.bean.UserBean;

public class Utils {
	// 获取AppKey
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	/**
	 * 设置是否要请求网络图片
	 */
	public static boolean shouldShowImage(Context context) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		boolean wifi_show_picture = sp.getBoolean("image_show_state", false);
		if (wifi_show_picture) {
			// Wifi下显示图片，不是wifi网络就不显示图片
			if (!(AppUtils.getNetworkType(context) == AppUtils.WIFI)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否是3G网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isFastMobileNetwork(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		switch (telephonyManager.getNetworkType()) {
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			return false; // ~ 50-100 kbps
		case TelephonyManager.NETWORK_TYPE_CDMA:
			return false; // ~ 14-64 kbps
		case TelephonyManager.NETWORK_TYPE_EDGE:
			return false; // ~ 50-100 kbps
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			return true; // ~ 400-1000 kbps
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			return true; // ~ 600-1400 kbps
		case TelephonyManager.NETWORK_TYPE_GPRS:
			return false; // ~ 100 kbps
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			return true; // ~ 2-14 Mbps
		case TelephonyManager.NETWORK_TYPE_HSPA:
			return true; // ~ 700-1700 kbps
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			return true; // ~ 1-23 Mbps
		case TelephonyManager.NETWORK_TYPE_UMTS:
			return true; // ~ 400-7000 kbps
		case TelephonyManager.NETWORK_TYPE_EHRPD:
			return true; // ~ 1-2 Mbps
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			return true; // ~ 5 Mbps
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			return true; // ~ 10-20 Mbps
		case TelephonyManager.NETWORK_TYPE_IDEN:
			return false; // ~25 kbps
		case TelephonyManager.NETWORK_TYPE_LTE:
			return true; // ~ 10+ Mbps
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			return false;
		default:
			return false;
		}
	}

	/**
	 * 获取网络连接方式
	 * 
	 * @param context
	 *            上下文
	 */
	public static String getNetWorkType(Context context) {
		String netType = "";
		try {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = manager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				String type = networkInfo.getTypeName();
				if (type.equalsIgnoreCase("WIFI")) {
					netType = "WIFI";
				} else if (type.equalsIgnoreCase("MOBILE")) {
					if (isFastMobileNetwork(context)) {
						netType = "M-3G";
					} else {
						netType = "M-2G";
					}
				}
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogUtils.i("网络连接方式：" + netType);
		return netType;
	}

	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 为程序创建桌面快捷方式
	 * 
	 * @param context
	 * @param clazz
	 *            启动Activity
	 */
	public static void addShortcut(Context context, Class<? extends Activity> clazz) {
		// 此处是为了判断是否已经有了快捷方式
		if (Utils.hasShortCut(context)) {
			return;
		}

		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

		// 快捷方式的名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
		shortcut.putExtra("duplicate", false); // 不允许重复创建

		Intent intent = new Intent(context, clazz);
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);

		// 快捷方式的图标
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(context, R.drawable.ic_launcher);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

		context.sendBroadcast(shortcut);
	}

	/**
	 * 判断当前应用在桌面是否有桌面快捷方式
	 * 
	 * @param context
	 */
	public static boolean hasShortCut(Context context) {
		Cursor cursor = null;
		try {
			// 获取当前应用名称
			String title = null;
			final PackageManager pm = context.getPackageManager();
			title = pm.getApplicationLabel(pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)).toString();
			if (TextUtils.isEmpty(title)) {
				title = context.getResources().getString(R.string.app_name);
			}
			Uri uri = null;
			String spermi = getAuthorityFromPermission(context, "com.android.launcher.permission.READ_SETTINGS");
			if (spermi == null) {
				return true;
			}
			if (getSystemVersion() < 8) {
				uri = Uri.parse("content://" + spermi + "/favorites?notify=true");
			} else {
				uri = Uri.parse("content://" + spermi + "/favorites?notify=true");
			}
			final ContentResolver cr = context.getContentResolver();
			cursor = cr.query(uri, null, "title=?", new String[] { title }, null);
			if (cursor != null && cursor.getCount() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LogUtils.i(e.toString());
			// String eString = e.getMessage();
			// return hasShortcut(context);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return false;
	}

	public static String getAuthorityFromPermission(Context context, String permission) {

		if (permission == null)
			return null;
		List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
		if (packs != null) {
			for (PackageInfo pack : packs) {
				ProviderInfo[] providers = pack.providers;
				if (providers != null) {
					for (ProviderInfo provider : providers) {
						if (permission.equals(provider.readPermission))
							return provider.authority;
						if (permission.equals(provider.writePermission))
							return provider.authority;
					}
				}
			}
		}
		return null;
	}

	public static int getSystemVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * 当读取桌面快捷方式权限有问题时，执行该方法判断
	 * 
	 * @param context
	 * @return
	 */
	private static boolean hasShortcut(Context context) {
		Cursor c = null;
		try {
			boolean isInstallShortcut = false;
			Uri uri = null;
			if (getSystemVersion() < 8) {
				uri = Uri.parse("content://com.android.launcher.settings/favorites?notify=true");
			} else {
				uri = Uri.parse("content://com.android.launcher2.settings/favorites?notify=true");
			}
			c = context.getContentResolver().query(uri, new String[] { "title", "iconResource" }, "title=?",
					new String[] { context.getString(R.string.app_name).trim() }, null);
			if (null != c && c.getCount() > 0) {
				isInstallShortcut = true;
			}

			return isInstallShortcut;
		} catch (Exception e) {
			return true;
		} finally {
			if (c != null) {
				c.close();
			}
		}

	}

	/**
	 * 普通url转带规范url,暂时无Token
	 * http://appdemo.tuanche.com/user/manger/testPost?token
	 * =XXX&uuid=12321312312312&random=1111&key=233
	 */

	public static String getSecuityUrl(String url) {
		long random = new Date().getTime();
		String ranString = String.valueOf(random);
		String key = Validator.getSafeKey(ranString);
		return url + "?random=" + ranString + "&key=" + key;
	}

	/**
	 * 获得通知蓝的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	public static int getHeight(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindow().getWindowManager().getDefaultDisplay().getMetrics(metric);
		//int width = metric.widthPixels; // 屏幕宽度（像素）
		int height = metric.heightPixels; // 屏幕高度（像素）
		//float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
		//int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
		return height;
	}

	public static int getWidth(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindow().getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		//int height = metric.heightPixels; // 屏幕高度（像素）
		// float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
		// int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
		return width;
	}
	/**
	 * 加上后台所需统计的数据
	 * @param url
	 * @return
	 */
	public static String AddUrlExtra(Context mContext,String url,Boolean ifHtml5){
		String safeRequestUrl = url;
		Session session = Session.get(mContext);  // 团车网后台统计
	/*	String userId = SPUtils.getUserId();
		String identify = SPUtils.getUserIdenty();*/
		//String userId = SharePreCache
		String userId = session.loadKey(Config.USER_ID, null);
		String identify = session.loadKey(Config.IDENTIFY,null);
		if (ifHtml5) {
			if (!safeRequestUrl.contains("?")) {
				safeRequestUrl = safeRequestUrl + "?source=12";
			} else {
				safeRequestUrl = safeRequestUrl + "&source=12";
			}
		}

		if(!CheckUtil.isEmpty(identify)&&ifHtml5){
			safeRequestUrl = safeRequestUrl + "&identify="+identify;
		}
//		if(!"".equals(identify)&& ifHtml5){
//			safeRequestUrl = safeRequestUrl + "&identify="+identify;
//		}

		if(!CheckUtil.isEmpty(userId)){
			if(ifHtml5){
				safeRequestUrl = safeRequestUrl + "&userId="+userId;
			}
			else{
				if (!safeRequestUrl.contains("?")) {
					safeRequestUrl = safeRequestUrl + "?userId="+userId;
				} else {
					safeRequestUrl = safeRequestUrl + "&userId="+userId;
				}
			}

			/*if(ifHtml5&&!safeRequestUrl.contains("?")){
				safeRequestUrl = safeRequestUrl + "?userId="+userId;
			}
			else if(ifHtml5&&safeRequestUrl.contains("?")){
				safeRequestUrl = safeRequestUrl + "&userId="+userId;
			}
			else if(!ifHtml5){
				safeRequestUrl = safeRequestUrl + "?userId="+userId;
			}*/
		}else{
			if(ifHtml5){
				safeRequestUrl = safeRequestUrl + "&userId=-1";
			}
			else{
				if (!safeRequestUrl.contains("?")) {
					safeRequestUrl = safeRequestUrl + "?userId=-1";
				} else {
					safeRequestUrl = safeRequestUrl + "&userId=-1";
				}
			}

		}
		safeRequestUrl = safeRequestUrl + "&system=android" +"&version=" + session.getVersionName()
				+"&deviceId="+session.getDeviceid();
		if("Channel ID".equals(session.getChannelName())){
			safeRequestUrl = safeRequestUrl +"&channel=ChannelID";
		}else{
			safeRequestUrl = safeRequestUrl +"&channel="+session.getChannelName();
		}
		Log.d("##################aa",safeRequestUrl);
		return safeRequestUrl;
	}

	/**
	 * 获取userBean
	 */
	public static UserBean getUserBeann(Context mContext){
 		UserBean bean=new Gson().fromJson(Session.get(mContext).loadKey(Config.USER_BEAN, ""), UserBean.class);
		return  bean;
	}

	/**
	 * 获取userId
	 * @param mContext
	 * @return
	 */
	public static int getUserId(Context mContext){
		int userId=getUserBeann(mContext).getCrmUser().getId();
		return  userId;
	}
	/**
	 * 获取status
	 * @param mContext
	 * @return
	 */
	public static int getUserStatus(Context mContext){
		//if（）
		int status=getUserBeann(mContext).getUserIplementInfo().getStatus();
		return  status;
	}
	/**
	 * 设置status
	 * @param mContext
	 * @return
	 */
	public static void setUserStatus(Context mContext,int status){
		UserBean bean=getUserBeann(mContext);
				bean.getUserIplementInfo().setStatus(status);
		Session.get(mContext).saveKey(Config.USER_BEAN,new Gson().toJson(bean));
	}
	public static void settingsWebView(Activity activity, WebView webView) {
//		if (getPhoneAndroidSDK() >= 14) {// 4.0需打开硬件加速
//			activity.getWindow().setFlags(
//					WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
//					WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
//		}
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		settings.setPluginState(WebSettings.PluginState.ON);
		settings.setAllowFileAccess(true);
		settings.setLoadWithOverviewMode(true);
		// settings.setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.2.1; zh-cn; MB525 Build/3.4.2-117) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
		// settings.setSupportMultipleWindows(true);
	}

}
