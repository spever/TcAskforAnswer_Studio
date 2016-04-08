package com.tuanche.askforanswer.source.dialog;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.source.ui.AskMsgActivity;
import com.tuanche.askforanswer.source.ui.BaseActivity;


public class PushDialog extends BaseActivity implements OnClickListener{
	private String msgType;
	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setFinishOnTouchOutside(false);
		setContentView(R.layout.push_dialog);
		Bundle message = getIntent().getExtras();
		url=message.getString(Config.MSGTURL);
		msgType=message.getString(Config.MSGTYPE);
		TextView v = (TextView)findViewById(R.id.dialog_push_text);
		v.setText(message.getString(Config.MSGTITLE));
		TextView confirm_cancel=  (TextView)findViewById(R.id.confirm_cancel);
		TextView confirm_submit=  (TextView)findViewById(R.id.confirm_submit);
		confirm_cancel.setOnClickListener(this);
		confirm_submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.confirm_submit){
			Bundle bundle = new Bundle();
			bundle.putString(Config.MSGTYPE, msgType);
			bundle.putString(Config.MSGTURL, url);
			openActivity(AskMsgActivity.class,bundle);
		}
		this.finish();
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
	
}
