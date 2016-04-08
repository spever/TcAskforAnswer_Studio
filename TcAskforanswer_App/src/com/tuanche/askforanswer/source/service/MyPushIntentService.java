package com.tuanche.askforanswer.source.service;

import java.util.Date;

import org.android.agoo.client.BaseConstants;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.tuanche.api.utils.AppUtils;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.widget.notification.NormalNotification;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.app.utils.Utils;
import com.tuanche.askforanswer.source.application.MyApp;
import com.tuanche.askforanswer.source.dialog.PushDialog;
import com.tuanche.askforanswer.source.ui.HomeActivity;
import com.umeng.message.UmengBaseIntentService;
import com.umeng.message.entity.UMessage;

/**
 * 自定义的推送通知
 * 
 * @author lucas
 * 
 */
public class MyPushIntentService extends UmengBaseIntentService {
	private static final String TAG = MyPushIntentService.class.getName();
	private static int notificationId = -1;
	private String url;
	private String key;
	private Intent inten2;

	@Override
	protected void onMessage(Context context, Intent intent) {
		super.onMessage(context, intent);
		try {
			String message = intent.getStringExtra(BaseConstants.MESSAGE_BODY);
			UMessage msg = new UMessage(new JSONObject(message));
			LogUtils.e(message);
			Bundle bundle=new Bundle();
			
			if(null!=msg.extra&&"300".equals(msg.extra.get(Config.MSGTYPE))){
				//系统通知:确定刚点击进来之后，应该指定哪一个tab被选中
				url=msg.extra.get(Config.MSGTURL);
				//bundle.putString(Config.MSGTITLE, msg.title);
				bundle.putString(Config.MSGTYPE,msg.extra.get(Config.MSGTYPE));
				bundle.putString(Config.MSGTURL, url);
			}else{
			
			}
			bundle.putString(Config.MSGTITLE, msg.title);
			if(Utils.getUserStatus(getApplicationContext())==2){
				// 当前应用是顶盏
				if (AppUtils.isAppOnForeground(context)) {
					inten2 = new Intent(MyApp.RECEIVER_ACTION);

				/*	Intent intent3 = new Intent(context, PushDialog.class);
					intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent3.putExtras(bundle);
					startActivity(intent3);  1.3版本去除push弹框提醒*/
				} else {
					LogUtils.e("bu zai dang qian");
					inten2 = new Intent(MyApp.RECEIVER_ACTION_PUBLIC);
					//sendBroadcast(inten2);
				}
				sendBroadcast(inten2);
				/**
				 * 在前台的话弹出dialog，在后台的话弹出notification
				 */
				notificationId = (int) (new Date().getTime() / 100000);
				NormalNotification notification = NormalNotification.createNormalNotification(context, getResources().getString(R.string.app_name),
						R.drawable.logo, msg.title, TextUtils.isEmpty(msg.text) ? "您有一条新消息" : msg.text, notificationId,
						Intent.FLAG_ACTIVITY_NEW_TASK, HomeActivity.class, bundle);
				notification.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
