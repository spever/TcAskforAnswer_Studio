package com.tuanche.askforanswer.source.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.tuanche.api.utils.LogUtils;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.utils.MySharedPreferences;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.source.view.MyRelativeLayout;
import com.tuanche.askforanswer.source.view.MyRelativeLayout.onChangeKeyBoard;
import com.umeng.analytics.MobclickAgent;

public class EditDialog extends BaseActivity implements TextWatcher, android.view.View.OnClickListener {
	private TextView tv_number; // 字数
	private TextView tv_submit; // 提交
	private EditText askAnswer; //
	private String conString;
	private MyRelativeLayout relativeLayout;
	private boolean isFirst = true;
	private Intent intent;
	private String content;
	//private String id;
	//private int code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setFinishOnTouchOutside(false);
		setContentView(R.layout.dialog_edit);
		intent=getIntent();
		conString = intent.getStringExtra("content");
		//id = intent.getStringExtra("id");
		//Log.i("id====",id+"");
		//content = MySharedPreferences.getValueByKey(this,id+"");//根据id拿到保存的数据
		getViews();
		setViews();
		setListeners();
//		if(content!=null){
//			askAnswer.setText(content);
//			askAnswer.setSelection(askAnswer.getText().length());
//		}

	}

	@Override
	public void getViews() {
		tv_number = (TextView) findViewById(R.id.text_num);
		tv_submit = (TextView) findViewById(R.id.submit);
		askAnswer = (EditText) findViewById(R.id.ask_answer);
		relativeLayout = (MyRelativeLayout) findViewById(R.id.rel_listener);

	}

	@Override
	public void setViews() {
		Resources resources = getResources();
		String text = String.format(resources.getString(R.string.textnum), 0);
		tv_number.setText(text);
		askAnswer.setText(conString);
		askAnswer.setSelection(askAnswer.getText().length());
	}

	@Override
	public void setListeners() {
		tv_submit.setOnClickListener(this);
		askAnswer.addTextChangedListener(this);
		relativeLayout.setStateChangeKeyBoard(new onChangeKeyBoard() {

			@Override
			public void onKeyboardStateChanged(int state, int keyheight) {
				if (state == -2) {
					if (isFirst) {
						isFirst = false;
					} else {
						submitAnswer(-2);
					}
				}
			}
		});
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		if (!TextUtils.isEmpty(arg0.toString())) {
			Resources resources = getResources();
			String text = String.format(resources.getString(R.string.textnum), arg0.toString().length());
			tv_number.setText(text);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		if (!TextUtils.isEmpty(arg0.toString())) {
			Resources resources = getResources();
			String text = String.format(resources.getString(R.string.textnum), 0);
			tv_number.setText(text);
		}
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.submit:
			submitAnswer(1);
			break;
		}
	}

	private void submitAnswer(int state) {
		if (state == 1) {
			if (!TextUtils.isEmpty(askAnswer.getText().toString().trim())) {
				MobclickAgent.onEvent(mContext, "questiondetails_answer_click");
				//hideSoftInput(askAnswer.getWindowToken());
				Intent intent = new Intent(EditDialog.this,AskInfoActivity.class);
				intent.putExtra("issubmit", true);
				intent.putExtra("content", askAnswer.getText().toString());
				LogUtils.e(askAnswer.getText().toString() + "------------");
				EditDialog.this.setResult(RESULT_OK, intent);
				//code = state;
				EditDialog.this.finish();
			} else {
				ToastUtil.showToast(mContext, getResources().getString(R.string.much_content));
			}
		} else if (state == -2) {
			//hideSoftInput(askAnswer.getWindowToken());
			Intent intent = new Intent(EditDialog.this,AskInfoActivity.class);
			intent.putExtra("content", askAnswer.getText().toString());
			intent.putExtra("issubmit", false);
			//saveContent();
			EditDialog.this.setResult(RESULT_OK, intent);
			LogUtils.e(-2+"------------");
			EditDialog.this.finish();

		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {

			// 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
			View v = getCurrentFocus();
			if (isShouldHideInput(askAnswer, ev) && v instanceof EditText) {
				hideSoftInput(v.getWindowToken());
				submitAnswer(-2);
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 判断是否隐藏输入法
	 * 
	 * @param v
	 * @param event
	 * @return
	 */
	private boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int[] l2 = { 0, 0 };
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
			tv_submit.getLocationInWindow(l2);
			int left1 = l2[0], top1 = l2[1], bottom1 = top1 + tv_submit.getHeight(), right1 = left1 + tv_submit.getWidth();

			if ((event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom)
					|| (event.getX() > left1 && event.getX() < right1 && event.getY() > top1 && event.getY() < bottom1)) {
				// 点击EditText的事件，或是提交的按钮忽略它。
				return false;
			} else {
				return true;
			}
		}
		// 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
		return false;
	}

	/**
	 * 多种隐藏软件盘方法的其中一种
	 * 
	 * @param token
	 */
	private void hideSoftInput(IBinder token) {
		if (token != null) {
			@SuppressWarnings("static-access")
			InputMethodManager im = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(token, 0);
		}
	}

//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		saveContent();
//	}
//	public void saveContent(){
//		content = askAnswer.getText().toString();
//		Log.i("code====", code + "");
//		if(code!=1){//提交失败或者返回
//			MySharedPreferences.save(this, id+"", content);//根据id保存内容
//		}
//		else{
//			MySharedPreferences.remove(this,id+"");
//		}
//	}
}
