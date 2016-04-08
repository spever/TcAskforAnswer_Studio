package com.tuanche.askforanswer.source.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.Config;

/**
 * 关于我们
 * @author zpf
 *
 */
public class AboutActivity extends BaseActivity implements OnClickListener, RadioGroup.OnCheckedChangeListener {

	private View mGoBack;
	private RadioGroup rg_runningnet;
	private RadioButton rb_line;
	private RadioButton rb_testyangche;
	private RadioButton rb_test2;
	private RadioButton rb_test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_ous);
		
		getViews();
		setViews();
		setListeners();
		
	}
	

	@Override
	public void getViews() {
		// TODO Auto-generated method stub
		mGoBack = findViewById(R.id.go_back);
		rg_runningnet = (RadioGroup) findViewById(R.id.rg_runningnet);
		rb_line = (RadioButton) findViewById(R.id.rb_line);//线上环境
		rb_test = (RadioButton) findViewById(R.id.rb_test);
		rb_test2 = (RadioButton) findViewById(R.id.rb_test2);
		rb_testyangche = (RadioButton) findViewById(R.id.rb_tesyangche);

	}

	@Override
	public void setViews() {
		// TODO Auto-generated method stub
		rg_runningnet.setVisibility(View.VISIBLE);
		switch (Config.LOCALTESTURL){
			case "http://mapi.tuanche.com/sold":
				rg_runningnet.check(R.id.rb_line);
				break;
			case "http://testyangche.mapi.tuanche.com/sold":
				rg_runningnet.check(R.id.rb_tesyangche);
				break;
			case "http://test.mapi.tuanche.com/sold":
				rg_runningnet.check(R.id.rb_test);
				break;
			case "http://test2.mapi.tuanche.com/sold":
				rg_runningnet.check(R.id.rb_test2);
				break;
		}
				rg_runningnet.setOnCheckedChangeListener(this);
				//tv_net_info.setText("现在运行环境："+Config.LOCALTESTURL);
	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		mGoBack.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.go_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId){
			case R.id.rb_line:
				Config.LOCALTESTURL="http://mapi.tuanche.com/sold";
				break;
			case R.id.rb_test:
				Config.LOCALTESTURL="http://test.mapi.tuanche.com/sold";
			break;
			case R.id.rb_test2:
				Config.LOCALTESTURL="http://test2.mapi.tuanche.com/sold";
				break;
			case R.id.rb_tesyangche:
				Config.LOCALTESTURL="http://testyangche.mapi.tuanche.com/sold";
				break;
		}
	}
}
