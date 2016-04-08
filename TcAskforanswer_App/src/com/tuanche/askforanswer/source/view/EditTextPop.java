package com.tuanche.askforanswer.source.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.utils.ShowMessage;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.source.PopListener.PopListener;

public class EditTextPop extends PopupWindow implements OnClickListener, TextWatcher {

	private PopListener listener;
	private Context context;
	private View view;
	private EditText ask_answer;
	private TextView tv_number;
	private TextView tv_submit;

	public PopListener getListener() {
		return listener;
	}

	public void setListener(PopListener listener) {
		this.listener = listener;
	}

	public EditTextPop(final Context mContext) {
		this.context = mContext;
		view = View.inflate(mContext, R.layout.item_edit_text, null);
		ask_answer = (EditText) view.findViewById(R.id.ask_answer);
		tv_number = (TextView) view.findViewById(R.id.text_num);
		tv_submit = (TextView) view.findViewById(R.id.submit);
		tv_submit.setOnClickListener(this);
		ask_answer.addTextChangedListener(this);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		ColorDrawable dw = new ColorDrawable(R.color.half_transparent);
		// 设置SelectPicPopupWindow弹出窗体的背景
		setBackgroundDrawable(dw);
		// setAnimationStyle(R.style.AnimationFade);
		setFocusable(true);
		setOutsideTouchable(true);
		setContentView(view);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit:
			submit();
			break;

		default:
			break;
		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		if (!TextUtils.isEmpty(arg0.toString())) {
			Resources resources = context.getResources();
			String text = String.format(resources.getString(R.string.textnum), arg0.toString().length());
			tv_number.setText(text);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

	void submit() {
		if (!TextUtils.isEmpty(ask_answer.getText().toString())) {
			listener.submitAnswer(ask_answer.getText().toString());
		} else {
			ShowMessage.showToast(context, context.getString(R.string.textisnull));
		}
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		LogUtils.i("huo de jiao dian");
		ask_answer.requestFocus();
		super.showAtLocation(parent, gravity, x, y);
	}
	
	@Override
	public void dismiss() {
		listener.popDimiss();
		super.dismiss();
	}
}
