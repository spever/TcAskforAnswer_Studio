package com.tuanche.askforanswer.source.ui;

import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.app.utils.CheckUtil;
import com.tuanche.askforanswer.source.bean.UserBean;

public class WelcomeActivity extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_welcome);
		
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run() {
				
				
				String json=mSession.loadKey(Config.USER_BEAN, "");
				if(!"".equals(json)){
					UserBean bean=new Gson().fromJson(json, UserBean.class);
					if(!CheckUtil.isEmpty(bean.getUserIplementInfo())){
						openActivity(HomeActivity.class);
						finish();
					}
				}else{
					openActivity(OpenActivity.class);
				}
				
				WelcomeActivity.this.finish();
				
			}
			
		}, 3000);
			
		
	}
	
	
	@Override
	public void getViews() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setViews() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub

	}

}
