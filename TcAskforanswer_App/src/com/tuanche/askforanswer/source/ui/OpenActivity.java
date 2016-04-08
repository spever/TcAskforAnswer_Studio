package com.tuanche.askforanswer.source.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.utils.ActivitiesManager;
import com.umeng.analytics.MobclickAgent;
/**
 * 技师登陆 、 技师注册
 * @author zpf
 *
 */
public class OpenActivity extends BaseActivity implements OnClickListener{

	
	private View mMasterRegister;
	private View mMasterLogin;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_layout);
		
		getViews();
		setViews();
		setListeners();
		
//		if(useId!=0){
//			openActivity(HomeActivity.class);
//			finish();
//		}
		
	}
	
	@Override
	public void getViews() {
		// TODO Auto-generated method stub
		mMasterRegister = findViewById(R.id.master_register);
		mMasterLogin = findViewById(R.id.master_login);
		
		
	}

	@Override
	public void setViews() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		mMasterRegister.setOnClickListener(this);
		mMasterLogin.setOnClickListener(this);
		
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.master_register:
			MobclickAgent.onEvent(OpenActivity.this, "index_register_click");
			openActivity(RegisterActivity.class);
			break;
		case R.id.master_login:
			MobclickAgent.onEvent(OpenActivity.this, "index_login_click");
			openActivity(LoginActivity.class);
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			 ActivitiesManager.getInstance().popAllActivities();
			 finish();
		}
		return false;
	}
	

}
