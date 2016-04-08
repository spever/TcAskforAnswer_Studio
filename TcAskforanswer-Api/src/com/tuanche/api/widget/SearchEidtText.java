package com.tuanche.api.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.tuanche.api.R;

public class SearchEidtText extends LinearLayout implements TextWatcher,OnClickListener{
	
	private EditText mEt;
	private ImageButton clearIBt;
	
	public SearchEidtText(Context context) {
		super(context);
		init(context);
	}
	
	public SearchEidtText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		View content = inflater.inflate(R.layout.search_edittext, this);
		mEt = (EditText) content.findViewById(R.id.search_edit);
		clearIBt = (ImageButton) content.findViewById(R.id.ib_clear_text);
		
		mEt.addTextChangedListener(this);
		clearIBt.setOnClickListener(this);
	}
	
	public EditText getEditText() {
		return mEt;
	}
	
	public ImageButton getClearBt() {
		return clearIBt;
	}
	
	public String getText(){
		return mEt.getText().toString();
	}
	
	public void setText(CharSequence text){
		mEt.setText(text);
	}
	
	public void setHint(CharSequence hint){
		mEt.setHint(hint);
	}
	
	public String getHint(){
		return mEt.getHint().toString();
	}
	
	public void clear(){
		mEt.setText("");
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
		String text = s.toString();
		if(text!=null&&!TextUtils.isEmpty(text)){
			clearIBt.setVisibility(View.VISIBLE);
		}else{
			clearIBt.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		clear();
	}
	
	

}
