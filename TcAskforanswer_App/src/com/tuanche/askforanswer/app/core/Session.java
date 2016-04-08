package com.tuanche.askforanswer.app.core;

/*
 * Copyright (C) 2010 mAPPn.Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;

import org.apache.commons.io.FileUtils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.tuanche.api.utils.AppUtils;
import com.tuanche.api.utils.AppUtils.StorageFile;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.utils.Pair;
import com.tuanche.api.utils.SaveFileData;
import com.tuanche.askforanswer.app.utils.STIDUtil;

/**
 * 
 */
public class Session {
	private final static String TAG = "Session";
	private Context mContext;
	private SaveFileData mPreference;
	private static Session mInstance;
	public boolean isDebug;
	/** The version of OS */
	private int osVersion;

	/** The user login status */
	private boolean isLogin;

	/** The Application Debug flag */
	private String debugType;

	/** The Application Version Code */
	private int versionCode;

	/** The Application package name */
	private String packageName;

	/** The Application version name */
	private String versionName;

	/** The Application version name */
	private String appName;

	/** The mobile IMEI code */
	private String imei="";

	/** The mobile sim code */
	private String sim;

	/** The mobile mac address */
	private String macAddress;

	/**
	 * The mobile model such as "Nexus One" Attention: some model type may have
	 * illegal characters
	 */
	private String model;

	/** The user-visible version string. E.g., "1.0" */
	private String buildVersion;
	
	/**网络连接方式*/
	private String netType;
	/** 渠道号 */
	private String channelId;
	/**渠道名称*/
	private String channelName;
	
	
	
	
	
	private String deviceid;
	private String num;
	private String id;
	private String roles;
	private String token;
	
	private Session(Context context) {

		mContext = context;
		mPreference = new SaveFileData(context, "app");
		osVersion = Build.VERSION.SDK_INT;
		buildVersion = Build.VERSION.RELEASE;
		try {
			model = android.os.Build.MODEL;
			AppUtils.clearExpiredFile(context,false);
			AppUtils.clearExpiredCacheFile(context);
			readSettings();
		} catch ( Exception e) {
			LogUtils.e(e.getMessage());
		}
	}
	
	public static Session get(Context context) {

		if (mInstance == null) {
			mInstance = new Session(context);
		}
		return mInstance;
	}
	
	
	
	private void readSettings() {
		isLogin = mPreference.loadBooleanKey(P_ISLOGIN, false);
		id = mPreference.loadStringKey(P_APP_ID, null);
		roles = mPreference.loadStringKey(P_APP_ROLES, null);
		token = mPreference.loadStringKey(P_APP_TOKEN, null);
//		user_name = TextUtils.isEmpty(username) ? "" : SecurityUtil
//				.decrypt(username);
		deviceid=STIDUtil.getDeviceId(mContext);
		netType = mPreference.loadStringKey(P_APP_NET_TYPE, "");
		channelId = mPreference.loadStringKey(P_APP_CHANNELID, null);
		setDeviceid(deviceid);
		getApplicationInfo();

		/** 清理App缓存 */
		AppUtils.clearExpiredFile(mContext, false);
		
		/**刷机防止操作*/
		readDeviceNum();
	}
	public void readDeviceNum(){
		String path=AppUtils.getPath(mContext, StorageFile.other);
		String readNum;
		File destFile=new File(path,"num.txt");
		try {
			if(destFile.exists()){
				 readNum=FileUtils.readFileToString(destFile);
				 if(!TextUtils.isEmpty(readNum)){
					 setNum(readNum);
				 }
			}else {
				readNum=getMac()+"-"+System.currentTimeMillis(); 
				destFile.createNewFile();
				FileUtils.writeStringToFile(destFile, readNum);
				setNum(readNum);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e(e.toString());
		}
		
	}
	

	public int getOsVersion() {
		return osVersion;
	}
	public String getVersionName() {
		if (TextUtils.isEmpty(versionName)) {
			getApplicationInfo();
		}
		return versionName;
	}

	public int getVersionCode() {
		if (versionCode <= 0) {
			getApplicationInfo();
		}
		return versionCode;
	}

	public String getIMEI() {
		if (TextUtils.isEmpty(imei)) {
			getApplicationInfo();
		}
		return imei;
	}

	public String getPackageName() {
		if (TextUtils.isEmpty(packageName)) {
			getApplicationInfo();
		}
		return packageName;
	}

	public String getSim() {
		if (TextUtils.isEmpty(sim)) {
			getApplicationInfo();
		}
		return sim;
	}

	public String getMac() {
		if (TextUtils.isEmpty(macAddress)) {
			try {
				WifiManager wifi = (WifiManager) mContext
						.getSystemService(Context.WIFI_SERVICE);
				WifiInfo info = wifi.getConnectionInfo();
				macAddress = info.getMacAddress();
			} catch (Exception ex) {
				LogUtils.e(ex.toString());
			}
		}
		return macAddress;
	}


	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		if (this.isLogin == isLogin) {
			return;
		}

		this.isLogin = isLogin;
		writePreference(new Pair<String, Object>(P_ISLOGIN, isLogin));
	}
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
		writePreference(new Pair<String, Object>(P_APP_TOKEN, token));
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		writePreference(new Pair<String, Object>(P_APP_ID, id));
		this.id = id;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		writePreference(new Pair<String, Object>(P_APP_ROLES, roles));
		this.roles = roles;
	}

	public String getModel() {
		return model;
	}

	public String getBuildVersion() {
		return buildVersion;
	}
	
	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
		writePreference(new Pair<String, Object>(P_APP_DEVICEID, deviceid));
	}
	
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	public void setNetType(String netType) {
		this.netType = netType;
		writePreference(new Pair<String, Object>(P_APP_NET_TYPE, netType));
	}
	
	public String getNetType() {
		return netType;
	}
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
		writePreference(new Pair<String, Object>(P_APP_CHANNELID, channelId));
	}


	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public void writePreference(Pair<String, Object> updateItem) {
//
//		// the preference key
		final String key = (String) updateItem.first;
//
//		if (P_APP_USER_NAME.equals(key) || P_APP_PASSWORD.equals(key)
//				|| P_APP_UNIONUSERNAME.equals(key) || P_APP_SAFE.equals(key)
//				|| P_APP_USERID.equals(key) || P_APP_UID.equals(key) 
//				|| P_APP_PCODE.equals(key) || P_APP_RED_SHOW.equals(key)
//				|| P_APP_MORE_SHOW.equals(key)||P_APP_XIAOMIPUSH_CLIENTID.equals(key)) {
//
//			mPreference.saveStringKey(key,
//					SecurityUtil.encrypt(String.valueOf(updateItem.second)));
//
//		} else if (P_ISLOGIN.equals(key) || P_NOT_DOWNLOAD_IMAGE.equals(key)
//				|| P_APP_SHOW_IMAGE_ONLY_WIFI.equals(key)
//				|| P_APP_IS_RECEIVE_FAVORABLE_MESSAGE.equals(key)
//				|| P_APP_IS_RECEIVE_ACTION_MESSAGE.equals(key)
//				|| P_APP_IS_RECEIVE_ORDER_MESSAGE.equals(key)
//				|| P_APP_TICKET_SYNCHRONI.equals(key)
//				|| P_APP_IS_FIRST_ENTER_MORE.equals(key)
//				|| P_APP_IS_FIRST_ENTER_GOURPBUY.equals(key)
//				|| P_APP_FIRST_START.equals(key) || P_APP_SHOW_PWD.equals(key)
//				|| P_APP_IS_FIRST_ENTER_MAINACTIVITY.equals(key)||P_APP_IS_RUNNING.equals(key)
//				|| P_APP_FIRST_NEED_GUIDE.equals(key)) {
//
//			mPreference.saveBooleanKey(key, (Boolean) updateItem.second);
//
//		} else 
		if (P_APP_NET_TYPE.equals(key)
				|| P_APP_CHANNELID.equals(key)
				|| P_APP_ID.equals(key) 
				|| P_APP_ROLES.equals(key) 
				|| P_APP_TOKEN.equals(key) 
//				|| P_APP_CITYID.equals(key)
//				|| P_APP_CITYNAME.equals(key)
//				|| P_APP_DATE.equals(key)
//				|| P_APP_LOGIN_TYPE.equals(key)
//				|| P_APP_LAST_LOGIN_NAME.equals(key)
//				|| P_APP_LAST_QUICK_LOGIN_MOBILE.equals(key)
//				|| P_APP_SINA_USER_ID.equals(key)
//				|| P_APP_SINA_USER_NAME.equals(key)
//				|| P_APP_SINA_ACCESS.equals(key)
//				|| P_APP_SINA_EXPIRES_IN.equals(key)
//				|| P_APP_KJ_ACCESS.equals(key)
//				|| P_APP_WX_ACCESS.equals(key)
//				|| P_APP_KJ_ACCESS_SECRET.equals(key)
//				|| P_APP_ALI_USER_OPENID.equals(key)
//				|| P_APP_ALI_USER_NAME.equals(key)
//				|| P_APP_WX_USER_OPENID.equals(key)
//				|| P_APP_WX_USER_NAME.equals(key)
//				|| P_APP_KJ_USER_NAME.equals(key)
//				|| P_APP_KJ_USER_SCREEN_NAME.equals(key)
//				|| P_APP_DEFAULT_MAP.equals(key)
//				|| P_APP_SAFE_CODE.equals(key) 
//				|| P_APP_FD_ID.equals(key)
//				|| P_APP_NET_TYPE.equals(key)
//				|| P_APP_FAVORABLE_TIME.equals(key)
//				|| P_APP_THINKID.equals(key) || P_APP_CITYID.equals(key)
//				|| P_APP_CITYNAME.equals(key) || P_APP_CITYLNG.equals(key)
//				|| P_APP_CITYLAT.equals(key)
//				|| P_APP_LOCATION_CITYID.equals(key)
//				|| P_APP_LOCATION_CITYNAME.equals(key)
//				|| P_APP_LOCATION_CITYLNG.equals(key)
//				|| P_APP_LOCATION_CITYLAT.equals(key)
//				|| P_APP_CITYLAT.equals(key) || P_APP_RECENT_VIEW.equals(key)
//				|| BAIDU_CHANNEL_ID.equals(key) || BAIDU_USER_ID.equals(key)
//				|| P_APP_LOCATION_ADDRESS.equals(key)
//				|| P_APP_USER_CONTACT.equals(key)
//				|| P_APP_LONGITUDE.equals(key)
//				|| P_APP_LATITUDE.equals(key)
//				|| P_APP_THEME_SHOW.equals(key)
				){
			mPreference.saveStringKey(key, (String) updateItem.second);
		} 
//			else if (P_APP_RENCENTLY_CITY.equals(key)
//				|| P_APP_SINA_USERS.equals(key)
//				|| P_APP_RECENTLY_GOODS.equals(key)
//				|| P_APP_USER_PROFILE.equals(key)
//				|| P_APP_CODE_LIST.equals(key)
//				|| P_APP_RECENTLY_PAY_WAY.equals(key)
//				|| P_APP_LOCAL_PAYWAYS_NAME.equals(key)
//				|| P_APP_LOCAL_CATEGORYS_NAME.equals(key)
//				|| P_APP_LOCAL_DISTRICT_NAME.equals(key)
//				|| P_APP_LOCAL_SUBWAY_NAME.equals(key)
//				|| P_APP_SELECTORS.equals(key)
//				|| P_LIST_FILTER_STATUS.equals(key)) {
//			String string = ObjectToString(updateItem.second);
//			mPreference.saveStringKey(key, string);
//
//		} else if (P_APP_FAVORABLE_HOUR.equals(key)
//				|| P_APP_FAVORABLE_MINS.equals(key)
//				|| P_APP_SINA_USERS.equals(key)
//				|| P_APP_SHORTCUT_NUM.equals(key)) {
//
//			mPreference.saveIntKey(key, (Integer) updateItem.second);
//
//		} else {
//			return;
//		}

	}
	/*
	 * 读取App配置信息
	 */
	private void getApplicationInfo() {

		final PackageManager pm = mContext.getPackageManager();
		try {
			final PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),
					0);
			versionName = pi.versionName;
			versionCode = pi.versionCode;

			final ApplicationInfo ai = pm.getApplicationInfo(
					mContext.getPackageName(), PackageManager.GET_META_DATA);
			channelName=ai.metaData.get("UMENG_CHANNEL").toString();
			channelId=STIDUtil.getChannelIdByChannelName(channelName);
			debugType = ai.metaData.get("app_debug").toString();

			if ("1".equals(debugType)) {
				// developer mode
				isDebug = true;
			} else if ("0".equals(debugType)) {
				// release mode
				isDebug = false;
			}
			LogUtils.allow = isDebug;

			appName = String.valueOf(ai.loadLabel(pm));
			LogUtils.appTagPrefix = appName;
			packageName = mContext.getPackageName();

			TelephonyManager telMgr = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);
			imei = telMgr.getDeviceId();
			sim = telMgr.getSimSerialNumber();
		} catch (NameNotFoundException e) {
			Log.d(TAG, "met some error when get application info");
		}
	}
	
	private static final String P_ISLOGIN = "pref.groupleadder.isLogin";
	private static final String P_APP_DEVICEID = "pref.groupleadder.deviceid";/**设备deviceId*/
	private static final String P_APP_NET_TYPE = "pref.groupleadder.net_type";
	private static final String P_APP_CHANNELID = "pref.groupleadder.channelid";
	
	private static final String P_APP_ID = "pref.groupleadder.id";
	private static final String P_APP_ROLES = "pref.groupleadder.roles";
	private static final String P_APP_TOKEN = "pref.groupleadder.token";
	
	/** 返回设备相关信息 */
	public String getDeviceInfo() {
		StringBuffer buffer = new StringBuffer();
//		buffer.append("userid=");
//		buffer.append(DesUtils.encrypt(getUid()));
		buffer.append(";versionname=");
		buffer.append(versionName);
		buffer.append(";versioncode=");
		buffer.append(versionCode);
		buffer.append(";imei=");
		buffer.append(imei);
		buffer.append(";sim=");
		buffer.append(sim);
		buffer.append(";macaddress=");
		buffer.append(getMac());
		buffer.append(";buildversion=");
		buffer.append(buildVersion);
		buffer.append(";osversion=");
		buffer.append(osVersion);
		buffer.append(";model=");
		buffer.append(model);

		buffer.append(";appname=");
		buffer.append("groupleader");
		buffer.append(";clientname=");
		buffer.append("android");
		buffer.append(";network=");
		/** 需要修改 */
		buffer.append(netType);
//		buffer.append(";location=");
//		buffer.append("");
//		buffer.append(city_lng);
//		buffer.append(",");
//		buffer.append(city_lat);
		buffer.append(";channelid=");
		buffer.append(channelId);
//		buffer.append(";cityid=");
//		buffer.append(city_id);
		buffer.append(";clientid=");
		buffer.append(deviceid);
		/**加入流水号*/
		buffer.append(";seq=");
		buffer.append(imei+AppUtils.getXuHao());
		buffer.append(";num=");
		buffer.append(num);
		LogUtils.e(buffer.toString());
		return buffer.toString();
	}
	public void clearUserInfo(){
		setId("");
		setRoles("");
		setToken("");
	}
	public void saveKey(String key,String value){
		mPreference.saveStringKey(key, value);
	}
	
	public String loadKey(String key,String defaultValue){
		return mPreference.loadStringKey(key, defaultValue);
	}
	public void putInt(String key,Integer value){
		mPreference.saveIntKey(key, value);
	}

	public int getInt(String key,Integer defaultValue){
		return mPreference.loadIntKey(key, defaultValue);
	}
}