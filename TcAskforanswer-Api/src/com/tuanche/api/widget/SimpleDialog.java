package com.tuanche.api.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tuanche.api.R;
 

public class SimpleDialog extends Dialog {

    Context context;
    private String title;
    private String content;
    private String button;
    private TextView dialog_title;
    private TextView dialog_content;
    private Button dialog_btn_ok;
    private android.view.View.OnClickListener listener;
    
    public SimpleDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    public SimpleDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }
    public SimpleDialog(Context context, int theme,String title,String content,String button){
        super(context, theme);
        this.context = context;
        this.title = title;
        this.content = content;
        this.button = button;
    }
    public SimpleDialog(Context context, int theme,String title,String content,String button,android.view.View.OnClickListener listener){
        super(context, theme);
        this.context = context;
        this.title = title;
        this.content = content;
        this.button = button;
        this.listener=listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.lashou_dialog);
        dialog_title = (TextView)findViewById(R.id.dialog_title);
        dialog_content = (TextView)findViewById(R.id.dialog_content);
        dialog_btn_ok = (Button)findViewById(R.id.dialog_btn_ok);
        if (title!=null&&!"".equals(title)) {
        	dialog_title.setText(title);
		} if (content!=null&&!"".equals(content)) {
			dialog_content.setText(content);
		} if (button!=null&&!"".equals(button)) {
			dialog_btn_ok.setText(button);
		}
		if (listener==null){
			dialog_btn_ok.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
		}else{
			dialog_btn_ok.setOnClickListener(listener);
		}
    } 
    
}