package com.tuanche.askforanswer.source.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.source.ui.WebViewShowActivity;

/**
 * 积分兑换对话框
 */
public class IntegralExchangeDialog extends Dialog implements View.OnClickListener {

	private TextView tv_no;
	private TextView tv_toSee;
	private Context mContext;

	private TextView tv_integral_exchange_content;
	private String content;
	private String url;
	private String title;

	public IntegralExchangeDialog(Context context,String content,String url,String title) {
		super(context);
		this.url=url;
		this.mContext=context;
		this.content=content;
		this.title=title;
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.integral_exchange_dialog);
		tv_no = (TextView) findViewById(R.id.no);
		tv_toSee = (TextView) findViewById(R.id.sure);
		tv_integral_exchange_content = (TextView) findViewById(R.id.tv_integral_exchange_content);

		tv_integral_exchange_content.setText(content);
		tv_no.setOnClickListener(this);
		tv_toSee.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sure:
			Intent mIntent =new Intent(mContext, WebViewShowActivity.class);
			/*mIntent.putExtra("webUrl",url);
			mContext.startActivity(mIntent);*/
			mIntent.putExtra(WebViewShowActivity.REWARD_RULE_TAG,url);
			mIntent.putExtra(WebViewShowActivity.WEBVIEW_TEXT_TAG,title);
			mContext.startActivity(mIntent);
			this.cancel();
			//加载webView
			break;
		case R.id.no:
			cancel();
			break;
		default:
			break;
		}
	}

}
