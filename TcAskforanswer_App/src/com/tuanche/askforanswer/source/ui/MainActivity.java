package com.tuanche.askforanswer.source.ui;

import java.util.UUID;

import android.os.Bundle;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi.Action;

public class MainActivity extends BaseActivity implements ApiRequestListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String token = UUID.randomUUID().toString().substring(0, 20);
//		new AppApi().login(mContext, "liuxu", "Admin999", token, this);
	}

	@Override
	public void getViews() {
		
	}

	@Override
	public void setViews() {
		
	}

	@Override
	public void setListeners() {
		
	}

	@Override
	public void onSuccess(Action method, Object obj) {
		switch (method) {
		case LOGIN_NOSIGN:
			
			break;

		default:
			break;
		}
	}

	@Override
	public void onError(Action method, Object statusCode) {
		
	}

	

	
}
