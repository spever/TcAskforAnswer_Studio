package com.tuanche.askforanswer.source.application;


import java.util.List;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.tuanche.api.utils.AppUtils;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.vo.NotifyObject;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.utils.PictureUtils;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.IUmengUnregisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

public class MyApp extends Application{

	public static String screen = null;
	private static Context mContext;
	private String mScreenSize = "0";
	public static Context getmContext() {
		return mContext;
	}


	public static void setmContext(Context mContext) {
		MyApp.mContext = mContext;
	}


	/**
	 * 屏幕缩放比
	 */
	public static float screenWidthScale = 1f; 
	
	/**
	 * 设计图宽度
	 */
	public static final float UI_DESIGN_WIDTH = 750;
	
	private PushAgent mPushAgent;
	
	public static final String CALLBACK_RECEIVER_ACTION = "callback_receiver_action";
	
	public static IUmengRegisterCallback mRegisterCallback;
	
	public static IUmengUnregisterCallback mUnregisterCallback;
	
	/**
	 * 接受消息推送后从service里发广播
	 */
	public static final String RECEIVER_ACTION = "receive_action";
	public static final String RECEIVER_ACTION_PUBLIC = "receive_action_public";

	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		screen = displayMetrics.widthPixels + "x" +displayMetrics.heightPixels;
		screenWidthScale = displayMetrics.widthPixels / UI_DESIGN_WIDTH;
		mPushAgent = PushAgent.getInstance(this);
//		mPushAgent.setDebugMode(true);
		
		/**
		 * 该Handler是在IntentService中被调用，故
		 * 1. 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
		 * 2. IntentService里的onHandleIntent方法是并不处于主线程中，因此，如果需调用到主线程，需如下所示;
		 * 	      或者可以直接启动Service
		 * */
		UmengMessageHandler messageHandler = new UmengMessageHandler(){
			@Override
			public void dealWithCustomMessage(final Context context, final UMessage msg) {
				new Handler(getMainLooper()).post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						UTrack.getInstance(getApplicationContext()).trackMsgClick(msg, false);
						Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
					}
				});
			}
			
			@Override
			public Notification getNotification(Context context,
					UMessage msg) {
				switch (msg.builder_id) {
				case 1:
					NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
					RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
					myNotificationView.setTextViewText(R.id.notification_title, msg.title);
					myNotificationView.setTextViewText(R.id.notification_text, msg.text);
					myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
					myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
					builder.setContent(myNotificationView);
					builder.setAutoCancel(true);
					Notification mNotification = builder.build();
					//由于Android v4包的bug，在2.3及以下系统，Builder创建出来的Notification，并没有设置RemoteView，故需要添加此代码
					mNotification.contentView = myNotificationView;
					return mNotification;
				default:
					//默认为0，若填写的builder_id并不存在，也使用默认。
					return super.getNotification(context, msg);
				}
			}
		};
		mPushAgent.setMessageHandler(messageHandler);

		/**
		 * 该Handler是在BroadcastReceiver中被调用，故
		 * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
		 * */
		UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler(){
			@Override
			public void dealWithCustomAction(Context context, UMessage msg) {
				Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
			}
		};
		mPushAgent.setNotificationClickHandler(notificationClickHandler);
		
		mRegisterCallback = new IUmengRegisterCallback() {
			
			@Override
			public void onRegistered(String registrationId) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CALLBACK_RECEIVER_ACTION);
				sendBroadcast(intent);
			}

		};
		mPushAgent.setRegisterCallback(mRegisterCallback);
		
		mUnregisterCallback = new IUmengUnregisterCallback() {
			
			@Override
			public void onUnregistered(String registrationId) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CALLBACK_RECEIVER_ACTION);
				sendBroadcast(intent);
			}
		};
		mPushAgent.setUnregisterCallback(mUnregisterCallback);
	
	}
	
	
	private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
	
	public void clearCache(){
//		final ApiRequestListener requestListener=this;
		AppUtils.clearAllCache(mContext,new NotifyObject() {
			@Override
			public void message(String msg) {
				// TODO Auto-generated method stub
//				new AppApi().getCacheSize(mContext, requestListener);
			}
		});
		AppUtils.clearAllFile(mContext);
		try {
			PictureUtils.getInstance(mContext).clearCache();
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e(e.toString());
		}
	}
	private boolean hasSinaClient(){
		boolean state = false;
		PackageManager pm = getPackageManager();
		List<PackageInfo> infos = pm.getInstalledPackages(0);
		for(PackageInfo info:infos){
			if(info.packageName.equals("com.sina.weibo")){
				state = true;
			}
		}
		return state;
	}


	public String getmScreenSize() {
		return mScreenSize;
	}


	public void setmScreenSize(String mScreenSize) {
		this.mScreenSize = mScreenSize;
	}
}
