package com.tuanche.askforanswer.source.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.app.core.Session;
import com.tuanche.askforanswer.source.bean.UserBean;

public class MyReceive extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent arg1) {
		LogUtils.e("收到广播了！！！！");
		Session.get(context).saveKey(Config.IFHAVENSG, "1");
		String json = Session.get(context).loadKey(Config.USER_BEAN, "");
		if (!"".equals(json)) {
			UserBean bean = new Gson().fromJson(json, UserBean.class);
			if (null != bean.getUserIplementInfo()) {
				//收到系统通知状态置为大于0 进入到homeactivity重新请求
				bean.getUserIplementInfo().setStatus(100);
			}
			Session.get(context).saveKey(Config.USER_BEAN, new Gson().toJson(bean));
		}
	}

}
