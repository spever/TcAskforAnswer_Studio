package com.tuanche.askforanswer.app.utils;

import android.content.Context;

import com.umeng.message.PushAgent;
/**
 * 推送工具类
 * @author chenhq
 *
 */
public class PushAgentUtils {
	/**
	 * 推送alias别名
	 */
	public static final String ALIAS_TYPE = "tc_chedashi";

	/**
	 * 设置自定义service
	 * @param context
	 */
	public static void setPushService(Context context){
		PushAgent mPushAgent = PushAgent.getInstance(context);
		mPushAgent.setPushIntentServiceClass(com.tuanche.askforanswer.source.service.MyPushIntentService.class);
	}
	
	/**
	 * 添加别名
	 * @param context
	 * @param alias
	 */
	public static void addAlias(final Context context, final String alias) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					PushAgent mPushAgent = PushAgent.getInstance(context);
					mPushAgent.addAlias("tc_"+alias, ALIAS_TYPE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	
	/**
	 * 去掉别名
	 * @param context
	 * @param alias
	 */
	public static void removeAlias(final Context context, final String alias) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					PushAgent mPushAgent = PushAgent.getInstance(context);
					mPushAgent.removeAlias("tc_"+alias, ALIAS_TYPE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
