package com.tuanche.api.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuanche.api.R;

public class SearchTextView extends LinearLayout implements TextWatcher{

	private TextView mTv;
	private ImageView clearBtn;

	public SearchTextView(Context context) {
		super(context);
		init(context);
	}

	public SearchTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View child = inflater.inflate(R.layout.search_textview, this);

		mTv = (TextView) child.findViewById(R.id.search_tv);
		clearBtn = (ImageView) child.findViewById(R.id.clear_edit_btn);
		clearBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearText();
			}
		});
		mTv.addTextChangedListener(this);
		
	}
	
//	public void setEnabled(boolean enabled){
//		mTv.setEnabled(enabled);
//	}
//	
//	public void setFocusable(boolean focusable){
//		mTv.setFocusable(false);
//	}

	public void setText(CharSequence text) {
		mTv.setText(text);
	}

	public String getText() {
		return mTv.getText().toString();
	}
	
	public void setHint(CharSequence hint){
		mTv.setHint(hint);
	}

	public void clearText() {
		mTv.setText("");
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		if(!TextUtils.isEmpty(s.toString())){
			clearBtn.setVisibility(View.VISIBLE);
		}else{
			clearBtn.setVisibility(View.INVISIBLE);
		}
	}

}
