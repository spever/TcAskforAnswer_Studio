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


/**
 * 
 */
//public class Session_copy {
//	private static Session_copy mInstance;
//	/**需求引导图的版本 如果当前版本需要引导图则配置 后缀7.10为当前版本*/
//	private static final String P_APP_FIRST_NEED_GUIDE = "pref.lashou.first.ng.7.10";
//	
//	/** Log tag */
//	private final static String TAG = "Session";
//
//	/** Application 上下文 */
//	private Context mContext;
//
//	/** The application debug mode Debug模式开启 */
//	public boolean isDebug;
//
//	/** The mobile device screen size 屏幕尺寸 */
//	private String screenSize;
//
//	/** The version of OS */
//	private int osVersion;
//
//	/** The user login status */
//	private boolean isLogin;
//
//	/** The Application Debug flag */
//	private String debugType;
//
//	/** The Application Version Code */
//	private int versionCode;
//
//	/** The Application package name */
//	private String packageName;
//
//	/** The Application version name */
//	private String versionName;
//
//	/** The Application version name */
//	private String appName;
//
//	/** The mobile IMEI code */
//	private String imei="";
//
//	/** The mobile sim code */
//	private String sim;
//
//	/** The mobile mac address */
//	private String macAddress;
//
//	/**
//	 * The mobile model such as "Nexus One" Attention: some model type may have
//	 * illegal characters
//	 */
//	private String model;
//
//	/** The user-visible version string. E.g., "1.0" */
//	private String buildVersion;
////
////	/** User login name */
////	private String user_name;
////	/** userId,显示用户名 */
////	private String user_id;
////	/** uid,请求接口时携带 */
////	private String uid;
////	/** User login password */
////	private String user_password;
////	//保存登录的用户名。
////	private String login_name;
////	/** 保存上次登录成功时的用户名 */
////	private String last_login_name;
////	/** 保存上次快速登录成功之后记录的手机号 */
////	private String last_quick_login_mobile;
////	/** 手机号 */
////	private String user_contact;
////	/** 联合登录登录名 */
////	private String union_login_name;
////	/** 第三方新浪登录ID */
////	private String sina_user_id;
////	/** 第三方新浪登录name */
////	private String sina_user_name;
////	/** 第三方新浪登录access */
////	private String sina_access;
////	/** 第三方新浪登录过期时间 */
////	private String sina_expires_in;
////
////	/** 是否安装新浪客户端 */
////	private static final String HAS_SINA_CLIENT = "587654";
////	/** 保存登录界面是否显示密码 */
////	private boolean show_pwd;
////	/** 保存用户6.0版本之前的支付密码 */
////	
////	/*** baidu push params */
////	private String baidu_channel_id;
////	private String baidu_user_id;
//	/**
//	 * 屏幕密度
//	 */
//	public float density = 1.5f;
//
//	/**
//	 * Tab的左Margin 110px
//	 */
//	public int mTabMargin110 = (int) (74 * density);
//
//	/**
//	 * Tab的左Margin 72px
//	 */
//	public int mTabMargin72 = (int) (48 * density);
//
//	/**
//	 * Tab的右Margin 9px
//	 */
//	public int mTabMargin9 = (int) (6 * density);
//
//	/**
//	 * Gallery的宽度
//	 */
//	public int mGalleryItemWidth = (int) (116 * density);
//
//	/**
//	 * Gallery的高度
//	 */
//	public int mGalleryItemHeight = (int) (200 * density);
////
////	/** The singleton instance */
////	private static Session mInstance;
////
////	/** 默认的支付方式 */
////	private String mDefaultChargeType;
////
////	/** 标示是否无图模式 */
////	private boolean mIsShowImage = false;
////	/** 是否是第一次使用周边团购 */
////	private boolean isFirstUseTuan = true;
////	/** 是否是第一次使用更多 */
////	private boolean isFirstUseMore = true;
////	/** 是否是第一次启动 */
////	private boolean isFirstStart = true;
////	/** 是否已经显示引导图，没有显示则显示 */
////	private boolean isFirstNeedGuide = true;
////	/** 当前是否定位成功 */
////	private boolean isLocationSuccess = false;
////	/** 标识是否有硬键盘 */
////	public boolean mIsKeyboardAvailable;
//
//	SaveFileData mPreference;
//
//	/** 渠道号 */
//	private String channelId;
//	/**渠道名称*/
//	private String channelName;
//	private Object obj;
//	/** 是否设置过支付密码|0代表未设置支付密码 1代表已设置 */
//	private String safe;
//	/** 支付密码 */
//	private String safe_code;
//	/** 当前选择使用的城市id */
//	private String city_id;
//	/** 当前选择使用的城市名称 */
//	private String city_name;
//	/** 当前选择使用的城市纬度 */
//	private String city_lat;
//	/** 当前选择使用的城市经度 */
//	private String city_lng;
//
//	/** 定位到的城市id */
//	private String location_city_id;
//	/** 定位到的城市名称 */
//	private String location_city_name;
//	/** 定位到的纬度 */
//	private String location_city_lat;
//	/** 定位到的经度 */
//	private String location_city_lng;
//
//	/** 仅wifi下显示图片 */
//	private boolean isOnlyWifi;
//	// 启动配置接口中的ThinkID
//	private String THINK_ID;
//
//	// 启动配置接口的是否是安全
//	private static final String IS_SAFE = "is_safe";
//	/** 是否接受每日推荐 */
//	private boolean isFavorable;
//	/** 是否接受活动通知 */
//	private boolean isAction;
//	/** 是否接受订单通知 */
//	private boolean isReceiveOrder;
//	/** 是否把拉手券同步到我的日历 */
//	private boolean isTicketSynchroni;
//	/** 登录相关token */
//	private String token;
//	/** 当天的日期 YY-MM-DD */
//	private String date;
//	/** 查看路线处的默认第三方地图 */
//	private String defaultMap;
//	/**连接Wifi时的分店ID*/
//	private String fdId;
//	/**网络连接方式*/
//	private String netType;
//
//	/** 每日优惠提醒时间 */
//	private String remindTime;
//
//	private int mins;
//	private int hours;
//	
//	private boolean isRunning;
//	
//	public boolean isApkDownloading=false;
//	/**
//	 * 是否为第一次启动MainActivity,与firstRun做区分
//	 * */
//	public boolean isFirstMain=false;
//	
//	/**是否为第一次进入MainActivity页面*/
//	public boolean isFirstEnterMainActivity = false;
//
//	/** 帮助的详情 */
//	public static final String URL_HELP = "http://api.mobile.lashou.com/help/detail.html";
//	
//	private String deviceid;
//	
//	private String locationAddress;
//	
//	private boolean isHasUpdate=false;
//	
//	/**
//	 * 是否由摇一摇进入分享
//	 */
//	private boolean isShakeShare = false;
//	/**
//	 * 是否由摇一摇进入分享后是否成功标识
//	 * 0为初始值 -1为分享失败 1为分享成功
//	 */
//	private int isShakeShareFlag = 0;
//	
//	private String num;
//	/**记录微信支付异步调用支付接口中用到的订单号*/
//	private String trade_no;
//	/**记录在微信支付中是电影支付还是普通订单*/
//	private int type;
//	private String red_show;
//	private String more_show;
//	/** 联合登录 的type 是 1,2,3,4 **/
//	private String loginType;
//	
//	private String themeTime;
//	private boolean isStartMain = true;
//	/**保存快捷方式显示的通知数据个数*/
//	private int shortcutNum;
//	/**小米推送返回的client_id */
//	private String xiaomi_user_id;
//	
//	private AdsEntity ads;
//	private String uid;
//	
//	public String getUid() {
//		return uid;
//	}
//
//	public void setUid(String uid) {
//		this.uid = uid;
//		writePreference(new Pair<String, Object>(P_APP_UID, uid));
//	}
//
//	public String getXiaomi_user_id() {
//		return xiaomi_user_id;
//	}
//
//	public void setXiaomi_user_id(String xiaomi_user_id) {
//		this.xiaomi_user_id = xiaomi_user_id;
//		writePreference(new Pair<String, Object>(P_APP_XIAOMIPUSH_CLIENTID, xiaomi_user_id));
//	}
//
//	public int getShortcutNum() {
//		return shortcutNum;
//	}
//
//	public void setShortcutNum(int shortcutNum) {
//		this.shortcutNum = shortcutNum;
//		writePreference(new Pair<String, Object>(P_APP_SHORTCUT_NUM, shortcutNum));
//	}
//
//	public boolean isStartMain() {
//		return isStartMain;
//	}
//
//	public void setStartMain(boolean isStartMain) {
//		this.isStartMain = isStartMain;
//	}
//
//	public String getThemeTime() {
//		return themeTime;
//	}
//
//	public void setThemeTime(String themeTime) {
//		this.themeTime = themeTime;
//		writePreference(new Pair<String, Object>(P_APP_THEME_SHOW, themeTime));
//	}
//
//
//	public String getRed_show() {
//		return red_show;
//	}
//
//	public void setRed_show(String red_show) {
//		this.red_show = red_show;
//		writePreference(new Pair<String, Object>(P_APP_RED_SHOW, red_show));
//	}
//
//	public String getMore_show() {
//		return more_show;
//	}
//
//	public void setMore_show(String more_show) {
//		this.more_show = more_show;
//		writePreference(new Pair<String,Object>(P_APP_MORE_SHOW,more_show));
//	}
//    
//	public String getLoginType() {
//		return loginType;
//	}
//
//	public void setLoginType(String loginType) {
//		this.loginType = loginType;
//		writePreference(new Pair<String,Object>(P_APP_LOGIN_TYPE,loginType));
//	}
//	
//	public String getTrade_no() {
//		return trade_no;
//	}
//
//	public void setTrade_no(String trade_no) {
//		this.trade_no = trade_no;
//	}
//	
//	public int getType() {
//		return type;
//	}
//
//	public void setType(int type) {
//		this.type = type;
//	}
//
//
//	//	
//	/**
//	 * default constructor
//	 * 
//	 * @param context
//	 */
//	private Session_copy(Context context) {
//
//		mContext = context;
//		mPreference = new SaveFileData(context, "app");
//		osVersion = Build.VERSION.SDK_INT;
//		buildVersion = Build.VERSION.RELEASE;
//		try {
//			model = android.os.Build.MODEL;
//			AppUtils.clearExpiredFile(context,false);
//			AppUtils.clearExpiredCacheFile(context);
//			readSettings();
//		} catch ( Exception e) {
//			LogUtils.e(e.getMessage());
//		}
//
//		
//	}
//
//	public static Session_copy get(Context context) {
//
//		if (mInstance == null) {
//			mInstance = new Session_copy(context);
//		}
//		return mInstance;
//	}
//	
//	
//	
//	public String getNum() {
//		return num;
//	}
//
//	public void setNum(String num) {
//		this.num = num;
//	}
//
//	public boolean isRunning() {
//		return isRunning;
//	}
//
//	public void setRunning(boolean isRunning) {
//		this.isRunning = isRunning;
//		writePreference(new Pair<String, Object>(P_APP_IS_RUNNING, isRunning));
//	}
//
//	/*
//	 * 读取用户所有的设置
//	 */
//	private void readSettings() {
////		union_login_name = TextUtils.isEmpty(unionusername) ? "" : SecurityUtil.decrypt(unionusername);
//		isRunning = mPreference.loadBooleanKey("isRunning", false);
//		isLogin = mPreference.loadBooleanKey(P_ISLOGIN, false);
//		token = mPreference.loadStringKey(P_APP_TOKEN, null);
//		date = mPreference.loadStringKey(P_APP_DATE, null);
//		loginType = mPreference.loadStringKey(P_APP_LOGIN_TYPE, null);
//		THINK_ID = mPreference.loadStringKey(P_APP_THINKID, null);
//		String username = mPreference.loadStringKey(P_APP_USER_NAME, null);
//		String unionusername = mPreference.loadStringKey(P_APP_UNIONUSERNAME,null);
//		
//
//		String userid = mPreference.loadStringKey(P_APP_USERID, null);
//		String uuid = mPreference.loadStringKey(P_APP_UID, null);
//		String pw = mPreference.loadStringKey(P_APP_PASSWORD, null);
//		String safee = mPreference.loadStringKey(P_APP_SAFE, null);
//		safe = TextUtils.isEmpty(safee) ? "" : SecurityUtil.decrypt(safee);
//		safe_code = mPreference.loadStringKey(P_APP_SAFE_CODE, null);
//
//		defaultMap = mPreference.loadStringKey(P_APP_DEFAULT_MAP, null);
//		fdId = mPreference.loadStringKey(P_APP_FD_ID, "0");
//		netType = mPreference.loadStringKey(P_APP_NET_TYPE, "");
//		
//		//channelId = mPreference.loadStringKey(P_APP_CHANNELID, null);
//		// 默认城市ID为北京（2419）
//		city_id = mPreference.loadStringKey(P_APP_CITYID, "2419");
//		city_name = mPreference.loadStringKey(P_APP_CITYNAME, null);
//		city_lng = mPreference.loadStringKey(P_APP_CITYLNG, null);
//		city_lat = mPreference.loadStringKey(P_APP_CITYLAT, null);
////		latitude = mPreference.loadStringKey(P_APP_LATITUDE, null);
////		longitude = mPreference.loadStringKey(P_APP_LONGITUDE, null);
//
//		location_city_id = mPreference.loadStringKey(P_APP_LOCATION_CITYID,
//				"2419");
//		location_city_name = mPreference.loadStringKey(P_APP_LOCATION_CITYNAME,
//				"北京");
//		locationAddress = mPreference.loadStringKey(P_APP_LOCATION_ADDRESS,
//				"我的位置");
//		//经度
//		location_city_lng = mPreference.loadStringKey(P_APP_LOCATION_CITYLNG, "116.38");
//		//纬度
//		location_city_lat = mPreference.loadStringKey(P_APP_LOCATION_CITYLAT, "39.9");
//
//		isOnlyWifi = mPreference.loadBooleanKey(P_APP_SHOW_IMAGE_ONLY_WIFI,
//				false);
//
//		isFavorable = mPreference.loadBooleanKey(
//				P_APP_IS_RECEIVE_FAVORABLE_MESSAGE, true);
//		isAction = mPreference.loadBooleanKey(P_APP_IS_RECEIVE_ACTION_MESSAGE,
//				true);
//		isReceiveOrder = mPreference.loadBooleanKey(
//				P_APP_IS_RECEIVE_ORDER_MESSAGE, true);
//		isTicketSynchroni = mPreference.loadBooleanKey(P_APP_TICKET_SYNCHRONI,
//				true);
//
//		remindTime = mPreference.loadStringKey(P_APP_FAVORABLE_TIME, null);
//		hours = mPreference.loadIntKey(P_APP_FAVORABLE_HOUR, 0);
//		mins = mPreference.loadIntKey(P_APP_FAVORABLE_MINS, 0);
//
//		isFirstEnterMainActivity = mPreference.loadBooleanKey(P_APP_IS_FIRST_ENTER_MAINACTIVITY, true);
//		isFirstUseMore = mPreference.loadBooleanKey(P_APP_IS_FIRST_ENTER_MORE, true);
//		isFirstUseTuan = mPreference.loadBooleanKey(P_APP_IS_FIRST_ENTER_GOURPBUY, true);
//		
//		
//		themeTime = mPreference.loadStringKey(P_APP_THEME_SHOW, null);
//		shortcutNum = mPreference.loadIntKey(P_APP_SHORTCUT_NUM, 0);
//		xiaomi_user_id = mPreference.loadStringKey(P_APP_XIAOMIPUSH_CLIENTID, null);
//		
//		deviceid=STIDUtil.getDeviceId(mContext);
//		setDeviceid(deviceid);
//		getApplicationInfo();
//
//		/** 清理App缓存 */
//		AppUtils.clearExpiredFile(mContext, false);
//		
//		/**刷机防止操作*/
//		readDeviceNum();
//	}
//	public void readDeviceNum(){
//		String path=AppUtils.getPath(mContext, StorageFile.other);
//		String readNum;
//		File destFile=new File(path,"num.txt");
//		try {
//			if(destFile.exists()){
//				 readNum=FileUtils.readFileToString(destFile);
//				 if(!TextUtils.isEmpty(readNum)){
//					 setNum(readNum);
//				 }
//			}else {
//				readNum=getMac()+"-"+System.currentTimeMillis(); 
//				destFile.createNewFile();
//				FileUtils.writeStringToFile(destFile, readNum);
//				setNum(readNum);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			LogUtils.e(e.toString());
//		}
//		
//	}
//	public String getCity_id() {
//		return city_id;
//	}
//
//	public void setCity_id(String city_id) {
//		this.city_id = city_id;
//		writePreference(new Pair<String, Object>(P_APP_CITYID, city_id));
//	}
//
//	public String getCity_name() {
//		return city_name;
//	}
//
//	public void setCity_name(String city_name) {
//		this.city_name = city_name;
//		writePreference(new Pair<String, Object>(P_APP_CITYNAME, city_name));
//	}
//
//	public String getCity_lat() {
//		return city_lat;
//	}
//
//	public void setCity_lat(String city_lat) {
//		this.city_lat = city_lat;
//		writePreference(new Pair<String, Object>(P_APP_CITYLAT, city_lat));
//	}
//
//	public String getCity_lng() {
//		return city_lng;
//	}
//
//	public void setCity_lng(String city_lng) {
//		this.city_lng = city_lng;
//		writePreference(new Pair<String, Object>(P_APP_CITYLNG, city_lng));
//	}
//
//
//	public String getLocation_city_id() {
//		return location_city_id;
//	}
//
//	public void setLocation_city_id(String location_city_id) {
//		this.location_city_id = location_city_id;
//		writePreference(new Pair<String, Object>(P_APP_LOCATION_CITYID,
//				location_city_id));
//	}
//
//	public String getLocation_city_name() {
//		return location_city_name;
//	}
//
//	public void setLocation_city_name(String location_city_name) {
//		this.location_city_name = location_city_name;
//		writePreference(new Pair<String, Object>(P_APP_LOCATION_CITYNAME,
//				location_city_name));
//	}
//
//	public String getLocation_city_lat() {
//		return location_city_lat;
//	}
//
//	public void setLocation_city_lat(String location_city_lat) {
//		if(TextUtils.isEmpty(location_city_lat)) return;
//		this.location_city_lat = location_city_lat;
//		writePreference(new Pair<String, Object>(P_APP_LOCATION_CITYLAT,
//				location_city_lat));
//	}
//
//	public String getLocation_city_lng() {
//		return location_city_lng;
//	}
//
//	public void setLocation_city_lng(String location_city_lng) {
//		if(TextUtils.isEmpty(location_city_lng))return;
//		this.location_city_lng = location_city_lng;
//		writePreference(new Pair<String, Object>(P_APP_LOCATION_CITYLNG,
//				location_city_lng));
//	}
//
//	public String getChannelId() {
//		return channelId;
//	}
//
//	public void setChannelId(String channelId) {
//		this.channelId = channelId;
//		writePreference(new Pair<String, Object>(P_APP_CHANNELID, channelId));
//	}
//
//
//	public String getChannelName() {
//		return channelName;
//	}
//
//	public void setChannelName(String channelName) {
//		this.channelName = channelName;
//	}
//
//
//	public String getScreenSize() {
//		return screenSize;
//	}
//
//	public int screenWidth;
//	public int screenHeight;
//	/** 支付密码 */
////	private String pCode;
//
//	public void setScreenSize(Activity activity) {
//
//		DisplayMetrics dm = new DisplayMetrics();
//		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//		screenWidth = dm.widthPixels;
//		screenHeight = dm.heightPixels;
//		screenSize = dm.widthPixels < dm.heightPixels ? dm.widthPixels + "#"
//				+ dm.heightPixels : dm.heightPixels + "#" + dm.widthPixels;
//		density = dm.density;
//		mTabMargin110 = (int) (74 * density);
//		mTabMargin72 = (int) (48 * density);
//		mTabMargin9 = (int) (6 * density);
//		mGalleryItemHeight = (int) (200 * density);
//		mGalleryItemWidth = (int) (116 * density);
//	}
//
//	public int getOsVersion() {
//		return osVersion;
//	}
//
//	/*
//	 * 读取App配置信息
//	 */
//	private void getApplicationInfo() {
//
//		final PackageManager pm = mContext.getPackageManager();
//		try {
//			final PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),
//					0);
//			versionName = pi.versionName;
//			versionCode = pi.versionCode;
//
//			final ApplicationInfo ai = pm.getApplicationInfo(
//					mContext.getPackageName(), PackageManager.GET_META_DATA);
//			channelName=ai.metaData.get("UMENG_CHANNEL").toString();
//			channelId=STIDUtil.getChannelIdByChannelName(channelName);
//			debugType = ai.metaData.get("app_debug").toString();
//
//			if ("1".equals(debugType)) {
//				// developer mode
//				isDebug = true;
//			} else if ("0".equals(debugType)) {
//				// release mode
//				isDebug = false;
//			}
//			LogUtils.allow = isDebug;
//
//			appName = String.valueOf(ai.loadLabel(pm));
//			LogUtils.appTagPrefix = appName;
//			packageName = mContext.getPackageName();
//
//			TelephonyManager telMgr = (TelephonyManager) mContext
//					.getSystemService(Context.TELEPHONY_SERVICE);
//			imei = telMgr.getDeviceId();
//			sim = telMgr.getSimSerialNumber();
//		} catch (NameNotFoundException e) {
//			Log.d(TAG, "met some error when get application info");
//		}
//	}
//
//	public String getVersionName() {
//		if (TextUtils.isEmpty(versionName)) {
//			getApplicationInfo();
//		}
//		return versionName;
//	}
//
//	public int getVersionCode() {
//		if (versionCode <= 0) {
//			getApplicationInfo();
//		}
//		return versionCode;
//	}
//
//	public String getIMEI() {
//		if (TextUtils.isEmpty(imei)) {
//			getApplicationInfo();
//		}
//		return imei;
//	}
//
//	public String getPackageName() {
//		if (TextUtils.isEmpty(packageName)) {
//			getApplicationInfo();
//		}
//		return packageName;
//	}
//
//	public String getSim() {
//		if (TextUtils.isEmpty(sim)) {
//			getApplicationInfo();
//		}
//		return sim;
//	}
//
//	public String getMac() {
//		if (TextUtils.isEmpty(macAddress)) {
//			try {
//				WifiManager wifi = (WifiManager) mContext
//						.getSystemService(Context.WIFI_SERVICE);
//				WifiInfo info = wifi.getConnectionInfo();
//				macAddress = info.getMacAddress();
//			} catch (Exception ex) {
//				LogUtils.e(ex.toString());
//			}
//		}
//		return macAddress;
//	}
//
//	public boolean isLogin() {
//		return isLogin;
//	}
//
//	public void setLogin(boolean isLogin) {
//		if (this.isLogin == isLogin) {
//			return;
//		}
//
//		this.isLogin = isLogin;
//		writePreference(new Pair<String, Object>(P_ISLOGIN, isLogin));
//	}
//
//
//	public String getAppName() {
//		return appName;
//	}
//
//	public String getModel() {
//		return model;
//	}
//
//	public String getBuildVersion() {
//		return buildVersion;
//	}
//
//	public String getDebugType() {
//		return debugType;
//	}
//
//	public boolean isOnlyWifi() {
//		return isOnlyWifi;
//	}
//
//	public void setOnlyWifi(boolean isOnlyWifi) {
//		this.isOnlyWifi = isOnlyWifi;
//		writePreference(new Pair<String, Object>(P_APP_SHOW_IMAGE_ONLY_WIFI,
//				isOnlyWifi));
//	}
//
//	public String getThinkId() {
//		return THINK_ID;
//	}
//
//	public void saveThinkId(String thinkId) {
//		this.THINK_ID = thinkId;
//		writePreference(new Pair<String, Object>(P_APP_THINKID, thinkId));
//
//	}
//
//	public boolean isFavorable() {
//		return isFavorable;
//	}
//
//	public void setFavorable(boolean isFavorable) {
//		this.isFavorable = isFavorable;
//		writePreference(new Pair<String, Object>(
//				P_APP_IS_RECEIVE_FAVORABLE_MESSAGE, isFavorable));
//	}
//
//	public boolean isAction() {
//		return isAction;
//	}
//
//	public void setAction(boolean isAction) {
//		this.isAction = isAction;
//		writePreference(new Pair<String, Object>(
//				P_APP_IS_RECEIVE_ACTION_MESSAGE, isAction));
//	}
//
//	public boolean isReceiveOrder() {
//		return isReceiveOrder;
//	}
//
//	public void setReceiveOrder(boolean isReceiveOrder) {
//		this.isReceiveOrder = isReceiveOrder;
//		writePreference(new Pair<String, Object>(
//				P_APP_IS_RECEIVE_ORDER_MESSAGE, isReceiveOrder));
//	}
//
//	public boolean isTicketSynchroni() {
//		return isTicketSynchroni;
//	}
//
//	public void setTicketSynchroni(boolean isTicketSynchroni) {
//		this.isTicketSynchroni = isTicketSynchroni;
//		writePreference(new Pair<String, Object>(P_APP_TICKET_SYNCHRONI,
//				isTicketSynchroni));
//	}
//
//	public void setDefaultMap(String mapPkg) {
//		this.defaultMap = mapPkg;
//		writePreference(new Pair<String, Object>(P_APP_DEFAULT_MAP, mapPkg));
//	}
//
//	public String getDefaultMap() {
//		return defaultMap;
//	}
//	
//	
//
//	public void setLocationAddress(String locationAddress) {
//		this.locationAddress = locationAddress;
//		writePreference(new Pair<String, Object>(P_APP_LOCATION_ADDRESS, locationAddress));
//	}
//	
//	public String getLocationAddress() {
//		return locationAddress;
//	}
//
//	public String getFdId() {
//		return fdId;
//	}
//
//	public void setFdId(String fdId) {
//		this.fdId = fdId;
//		writePreference(new Pair<String, Object>(P_APP_FD_ID, fdId));
//	}
//	
//	public void setNetType(String netType) {
//		this.netType = netType;
//		writePreference(new Pair<String, Object>(P_APP_NET_TYPE, netType));
//	}
//	
//	public String getNetType() {
//		return netType;
//	}
//
//	public String getRemindTime() {
//		return remindTime;
//	}
//
//	public void setRemindTime(String remindTime) {
//		this.remindTime = remindTime;
//		writePreference(new Pair<String, Object>(P_APP_FAVORABLE_TIME,
//				remindTime));
//	}
//
//	public int getMins() {
//		return mins;
//	}
//
//	public void setMins(int mins) {
//		this.mins = mins;
//		writePreference(new Pair<String, Object>(P_APP_FAVORABLE_MINS, mins));
//	}
//
//	public int getHours() {
//		return hours;
//	}
//
//	public void setHours(int hours) {
//		this.hours = hours;
//		writePreference(new Pair<String, Object>(P_APP_FAVORABLE_HOUR, hours));
//	}
//
//	/**
//	 * 清除上一个版本数据
//	 */
//	public void clearData() {
//		mPreference.clear();
//		readSettings();
//	}
//
//	public Object getObj(String key) {
//		String string = mPreference.loadStringKey(key, "");
//		Object object = null;
//		if (!TextUtils.isEmpty(string)) {
//			try{
//			object = StringToObject(string);
//			}catch(Exception ex){
//				Log.e("wang","异常"+ex.toString());
//			}
//		}
//		return object;
//	}
//
//	public void setObj(String key, Object obj) {
//		this.obj = obj;
//		try{
//			writePreference(new Pair<String, Object>(key, obj));
//		}catch(Exception ex){
//			Log.e("wang",ex.toString());
//		}
//	}
//
//	public String getSafe() {
//		return safe;
//	}
//
//	public void setSafe(String safe) {
//		this.safe = safe;
//		writePreference(new Pair<String, Object>(P_APP_SAFE, safe));
//	}
//
//	public String getSafe_code() {
//		return safe_code;
//	}
//
//	public void setSafe_code(String safe_code) {
//		this.safe_code = safe_code;
//		writePreference(new Pair<String, Object>(P_APP_SAFE_CODE, safe_code));
//	}
//
//	public String getToken() {
//		return token;
//	}
//
//	public void setToken(String token) {
//		this.token = token;
//		writePreference(new Pair<String, Object>(P_APP_TOKEN, token));
//	}
//	
//	public String getDate() {
//		return date;
//	}
//
//	public void setDate(String date) {
//		this.date = date;
//		writePreference(new Pair<String, Object>(P_APP_DATE, date));
//	}
//	/**
//	 * 是否是第一次进入首页：用来操作首页的Spanner
//	 * @return
//	 */
//	public boolean isFirstEnterMainActivity() {
//		return isFirstEnterMainActivity;
//	}
//
//	public void setFirstEnterMainActivity(boolean isFirstEnterMainActivity) {
//		this.isFirstEnterMainActivity = isFirstEnterMainActivity;
//		writePreference(new Pair<String, Object>(P_APP_IS_FIRST_ENTER_MAINACTIVITY, isFirstEnterMainActivity));
//	}
//	public String getDeviceid() {
//		return deviceid;
//	}
//
//	public void setDeviceid(String deviceid) {
//		this.deviceid = deviceid;
//		writePreference(new Pair<String, Object>(P_APP_DEVICEID, deviceid));
//	}
//
//	/**
//	 * Write session value back to preference
//	 */
//	public void writePreference(Pair<String, Object> updateItem) {
//
//		// the preference key
//		final String key = (String) updateItem.first;
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
//		} else if (P_DEFAULT_CHARGE_TYPE.equals(key)
//				|| P_APP_CHANNELID.equals(key) || P_APP_CITYID.equals(key)
//				|| P_APP_TOKEN.equals(key) || P_APP_CITYNAME.equals(key)
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
//				|| P_APP_THEME_SHOW.equals(key)) {
//			mPreference.saveStringKey(key, (String) updateItem.second);
//
//		} else if (P_APP_RENCENTLY_CITY.equals(key)
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
//
//	}
//
//	/**返回简版头信息*/
//	public String getDeviceHeartInfo() {
////		StringBuffer buffer = new StringBuffer();
////		String userid=getUid();
////		String mac=getMac();
////		if(!TextUtils.isEmpty(userid)){
////			buffer.append("userid=");
////			buffer.append(getUid());
////		}else if(!TextUtils.isEmpty(mac)){
////			buffer.append("macaddress=");
////			buffer.append(getMac());
////		}
////		buffer.append(";location=");
////		buffer.append("");
////		buffer.append(city_lng);
////		buffer.append(",");
////		buffer.append(city_lat);
//		
//		StringBuffer buffer = new StringBuffer("{");
//		String userid=getUid();
//		String mac=getMac();
//		if(!TextUtils.isEmpty(userid)){
//			buffer.append("\"uid\":").append(getUid()).append(",");
//		}else{
//			buffer.append("\"uid\":").append("\"\"").append(",");
//		}
//		
//		buffer.append("\"long\":").append("\"").append(city_lng).append("\"").append(",");
//		buffer.append("\"lat\":").append("\"").append(city_lat).append("\"").append(",");
//		
//		if(!TextUtils.isEmpty(mac)){
//			buffer.append("\"mac\":").append("\"").append(getMac()).append("\"");
//		}else{
//			buffer.append("\"mac\":").append("\"\"");
//		}
//		buffer.append("}");
//		return buffer.toString();
//	}
//	/** 返回设备相关信息 */
//	public String getDeviceInfo() {
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("userid=");
//		buffer.append(DesUtils.encrypt(getUid()));
//		buffer.append(";versionname=");
//		buffer.append(versionName);
//		buffer.append(";versioncode=");
//		buffer.append(versionCode);
//		buffer.append(";imei=");
//		buffer.append(imei);
//		buffer.append(";sim=");
//		buffer.append(sim);
//		buffer.append(";macaddress=");
//		buffer.append(getMac());
//		buffer.append(";buildversion=");
//		buffer.append(buildVersion);
//		buffer.append(";osversion=");
//		buffer.append(osVersion);
//		buffer.append(";model=");
//		buffer.append(model);
//
//		buffer.append(";appname=");
//		buffer.append("groupbuy");
//		buffer.append(";clientname=");
//		buffer.append("android");
//		buffer.append(";network=");
//		/** 需要修改 */
//		buffer.append(netType);
//		buffer.append(";location=");
//		buffer.append("");
//		buffer.append(city_lng);
//		buffer.append(",");
//		buffer.append(city_lat);
//		buffer.append(";channelid=");
//		buffer.append(channelId);
//		buffer.append(";cityid=");
//		buffer.append(city_id);
//		buffer.append(";clientid=");
//		buffer.append(deviceid);
//		/**加入流水号*/
//		buffer.append(";seq=");
//		buffer.append(imei+AppUtils.getXuHao());
//		buffer.append(";num=");
//		buffer.append(num);
//		LogUtils.e(buffer.toString());
//		return buffer.toString();
//	}
//
//	/** field about preference */
//	public static final String P_ISLOGIN = "pref.lashou.isLogin";
//	public static final String P_APP_USER_NAME = "user_name";
//	public static final String P_APP_USERID = "userid";
//	public static final String P_APP_UID = "uid";
//	public static final String P_APP_PASSWORD = "user_password";
//	public static final String P_APP_UNIONUSERNAME = "union_login_name";
//	public static final String P_NOT_DOWNLOAD_IMAGE = "pref.lashou.not_download_image";
//	public static final String P_APP_FIRST_START = "pref.lashou.first.start";
//	public static final String P_DEFAULT_CHARGE_TYPE = "pref.lashou.charge.type";
//	public static final String P_APP_CHANNELID = "pref.lashou.channelid";
//	/** 选择城市 */
//	public static final String P_APP_CITYID = "pref.lashou.city_id";
//	public static final String P_APP_CITYNAME = "pref.lashou.city_name";
//	public static final String P_APP_CITYLNG = "pref.lashou.city_lng";
//	public static final String P_APP_CITYLAT = "pref.lashou.city_lat";
//	public static final String P_APP_LONGITUDE = "pref.lashou.longitude";
//	public static final String P_APP_LATITUDE = "pref.lashou.latitude";
//	
//	public static final String P_APP_IS_RUNNING = "pref.lashou.isRunning";
//	/** （location）定位城市 */
//	public static final String P_APP_LOCATION_CITYID = "pref.lashou.location_city_id";
//	public static final String P_APP_LOCATION_CITYNAME = "pref.lashou.location_city_name";
//	public static final String P_APP_LOCATION_CITYLNG = "pref.lashou.location_city_lng";
//	public static final String P_APP_LOCATION_CITYLAT = "pref.lashou.location_city_lat";
//
//	/** 最近浏览城市集合 */
//	public static final String P_APP_RENCENTLY_CITY = "pref.lashou.rencentlycity";
//	/** 最近支付方式 */
//	public static final String P_APP_RECENTLY_PAY_WAY = "recently_pay_way";
//	/** updata借口默认支付方式 */
//	public static final String P_APP_LOCAL_PAYWAYS_NAME = "local_payways_name";
//	/** updata接口默认分类 */
//	public static final String P_APP_LOCAL_CATEGORYS_NAME = "local_categorys_name";
//	/** updata接口默认商圈 */
//	public static final String P_APP_LOCAL_DISTRICT_NAME = "local_districts_name";
//	/** updata接口地铁商圈 */
//	public static final String P_APP_LOCAL_SUBWAY_NAME = "local_subways_name";
//	/** 是否设置过安全码 */
//	public static final String P_APP_SAFE = "safe";
//	/** 安全码 */
//	public static final String P_APP_SAFE_CODE = "safe_code";
//	/** 用户token */
//	public static final String P_APP_TOKEN = "token";
//	public static final String P_APP_DATE = "date";
//	public static final String P_APP_OAUTH = "pref.lashou.oauth";
//	
//	public static final String P_APP_LOGIN_NAME = "login_name";
//	public static final String P_APP_LAST_LOGIN_NAME = "last_login_name";
//	public static final String P_APP_LAST_QUICK_LOGIN_MOBILE = "last_quick_login_mobile";
//	public static final String P_APP_USER_CONTACT = "user_contact";
//	public static final String P_APP_SINA_USER_ID = "pref.lashou.sina_user_id";
//	public static final String P_APP_SINA_USER_NAME = "pref.lashou.sina_user_name";
//	public static final String P_APP_SINA_ACCESS = "pref.lashou.sina_access";
//	public static final String P_APP_SINA_EXPIRES_IN = "pref.lashou.sina_expires_in";
//	public static final String P_APP_SINA_USERS = "pref.lashou.sina_users";
//	public static final String P_APP_SHOW_PWD = "pref.lashou.show_pwd";
//	public static final String P_APP_PCODE = "pcode";
//
//	public static final String P_APP_KJ_ACCESS = "pref.lashou.kj_access";
//	public static final String P_APP_WX_ACCESS = "pref.lashou.wx_access";
//	public static final String P_APP_KJ_ACCESS_SECRET = "pref.lashou.kj_access_secret";
//	public static final String P_APP_KJ_USER_NAME = "pref.lashou.kj_user_name";
//	public static final String P_APP_KJ_USER_SCREEN_NAME = "pref.lashou.kj_user_screen_name";
//
//	public static final String P_APP_TENC_USER_OPENID = "pref.lashou.tenc_user_openid";
//	public static final String P_APP_TENC_USER_NAME = "pref.lashou.tenc_user_name";
//	public static final String P_APP_ALI_USER_OPENID = "pref.lashou.ali_user_openid";
//	public static final String P_APP_ALI_USER_NAME = "pref.lashou.ali_user_name";
//	public static final String P_APP_WX_USER_OPENID = "pref.lashou.wx_user_openid";
//	public static final String P_APP_WX_USER_NAME = "pref.lashou.wx_user_name";
//	/** 百度通道id */
//	public static final String BAIDU_CHANNEL_ID = "baidu_channel_id";
//	/** 百度用户id */
//	public static final String BAIDU_USER_ID = "baidu_user_id";
//
//	/** 仅wifi下显示图片 */
//	public static final String P_APP_THINKID = "ThinkID";
//	public static final String P_APP_SHOW_IMAGE_ONLY_WIFI = "pref.lashou.image_show_state";
//	public static final String P_APP_HOTCITY = "pref.lashou.hotcity";
//	/** 是否接受每日优惠 */
//	private static final String P_APP_IS_RECEIVE_FAVORABLE_MESSAGE = "pref.lashou.is_receive_favorable_message";
//	/** 是否接受活动通知 */
//	private static final String P_APP_IS_RECEIVE_ACTION_MESSAGE = "pref.lashou.is_receive_action_message";
//	/** 是否接受订单通知 */
//	private static final String P_APP_IS_RECEIVE_ORDER_MESSAGE = "pref.lashou.is_receive_order_message";
//	/** 是否把拉手券同步到我的日历 */
//	private static final String P_APP_TICKET_SYNCHRONI = "pref.lashou.is_ticket_synchroni";
//	/** 最近浏览商品 */
//	public static final String P_APP_RECENTLY_GOODS = "pref.lashou.recently_goods";
//	/** 查看路线时的默认第三方地图 */
//	private static final String P_APP_DEFAULT_MAP = "pref.lashou.default_map";
//	/** 连接Wifi时的分店ID */
//	private static final String P_APP_FD_ID = "pref.lashou.fd_id";
//	/** 连接Wifi时的分店ID */
//	private static final String P_APP_NET_TYPE = "pref.lashou.net_type";
//	/** 每日优惠提醒时间 */
//	private static final String P_APP_FAVORABLE_TIME = "pref.lashou.favorable_time";
//
//	/** 每日优惠提醒时间 */
//	private static final String P_APP_FAVORABLE_HOUR = "pref.lashou.favorable_hours";
//	/** 每日优惠提醒时间 */
//	private static final String P_APP_FAVORABLE_MINS = "pref.lashou.favorable_mins";
//	
//	/** 拉手券列表信息 */
//	public static final String P_APP_CODE_LIST = "codeList";
//	/** 用户信息 */
//	public static final String P_APP_USER_PROFILE = "pref.lashou.user_profile";
//	/** 最近浏览 */
//	public static final String P_APP_RECENT_VIEW = "pref.lashou.recent_view";
//	/**列表筛选条件保存*/
//	public static final String P_LIST_FILTER_STATUS = "pref.lashou.list_filter_status";
//	/**selectors*/
//	public static final String P_APP_SELECTORS = "pref.lashou.selectors";
//	/**第一次进入MainActivity*/
//	public static final String P_APP_IS_FIRST_ENTER_MAINACTIVITY = "pref.lashou.isfirst.enter.mainactivity";
//	/**第一次进入周边团购*/
//	public static final String P_APP_IS_FIRST_ENTER_GOURPBUY = "pref.lashou.isfirst.enter.groupbuy";
//	/**第一次进入更多*/
//	public static final String P_APP_IS_FIRST_ENTER_MORE = "pref.lashou.isfirst.enter.more";
//	/** 定位的位置信息 */
//	private static final String P_APP_LOCATION_ADDRESS = "pref.lashou.location_address";
//	/**设备deviceId*/
//	private static final String P_APP_DEVICEID = "pref.lashou.deviceid";
//	/**我的是否显示小红点提示*/
//	private static final String P_APP_RED_SHOW = "pref.lashou.redshow";
//	/**更多是否显示小红点提示*/
//	private static final String P_APP_MORE_SHOW = "pref.lashou.moreshow";
//	/** 联合登录采用哪种方式登录  */
//	private static final String P_APP_LOGIN_TYPE = "pref.lashou.logintype";
//	/**是否有底部icon更换的活动*/
//	private static final String P_APP_THEME_SHOW = "pref.lashou.theme";
//	
//	private static final String P_APP_SHORTCUT_NUM ="pref.lashou.shortcut.number";
//	private static final String P_APP_XIAOMIPUSH_CLIENTID ="pref.lashou.xiaomipush.clientid";
//	private String ObjectToString(Object obj) {
//		String productBase64 = null;
//		ByteArrayOutputStream baos = null;
//		ObjectOutputStream oos = null;
//		try {
//			baos = new ByteArrayOutputStream();
//			oos = new ObjectOutputStream(baos);
//			oos.writeObject(obj);
//			productBase64 = new String(Base64.encodeBase64(baos.toByteArray()));
//		} catch (Exception e) {
//			Log.e("错误","保存错误"+e.toString());
//		} finally {
//			if (baos != null) {
//				try {
//					baos.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (oos != null) {
//				try {
//					oos.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return productBase64;
//	}
//
//	private Object StringToObject(String str) {
//		Object obj = null;
//		byte[] base64Bytes;
//		ByteArrayInputStream bais = null;
//		try {
//			String productBase64 = str;
//			if (null == productBase64
//					|| TextUtils.isEmpty(productBase64.trim())) {
//				return null;
//			}
//
//			base64Bytes = Base64.decodeBase64(productBase64.getBytes());
//			bais = new ByteArrayInputStream(base64Bytes);
//			ObjectInputStream ois = new ObjectInputStream(bais);
//			obj = ois.readObject();
//			ois.close();
//		} catch (Exception e) {
//		} finally {
//			if (bais != null) {
//				try {
//					bais.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return obj;
//	}
//	
//	/**
//	 * 清除用户数据
//	 */
//	public void clearUserData() {
//		try {
//			this.setToken("");
//			this.setObj(P_APP_USER_PROFILE, null);
//			this.setObj(P_APP_CODE_LIST, null);
//		} catch (Exception e) {
//			LogUtils.d("Session -> clearUserData:", e);
//		}
//	}
//	public boolean isApkDownloading() {
//		return isApkDownloading;
//	}
//
//	public void setApkDownloading(boolean isApkDownloading) {
//		this.isApkDownloading = isApkDownloading;
//	}
//
//	public boolean isFirstMain() {
//		return isFirstMain;
//	}
//
//	public void setFirstMain(boolean isFirstMain) {
//		this.isFirstMain = isFirstMain;
//	}
//
//	public boolean isHasUpdate() {
//		return isHasUpdate;
//	}
//
//	public void setHasUpdate(boolean isHasUpdate) {
//		this.isHasUpdate = isHasUpdate;
//	}
//
//	public boolean isShakeShare() {
//		return isShakeShare;
//	}
//
//	public void setShakeShare(boolean isShakeShare) {
//		this.isShakeShare = isShakeShare;
//	}
//
//	public int getIsShakeShareFlag() {
//		return isShakeShareFlag;
//	}
//
//	public void setIsShakeShareFlag(int isShakeShareFlag) {
//		this.isShakeShareFlag = isShakeShareFlag;
//	}
//
//
//	public AdsEntity getAds() {
//		return ads;
//	}
//
//	public void setAds(AdsEntity ads) {
//		this.ads = ads;
//	}
//
//
//	
//
//}