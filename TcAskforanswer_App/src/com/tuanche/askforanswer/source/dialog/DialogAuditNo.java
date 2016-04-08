package com.tuanche.askforanswer.source.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.source.PopListener.DialogListener;
import com.tuanche.askforanswer.source.PopListener.DialogListener.DialogEnum;
import com.tuanche.askforanswer.source.view.CircularImage;

public class DialogAuditNo extends AlertDialog implements android.view.View.OnClickListener {
	private TextView dialog_button;
	private CircularImage dialog_iv;
	private DialogListener listener;
	private ImageView dialog_cancle;
	private DialogEnum dialogEnum;
	public DialogAuditNo(Context context, DialogEnum dialogEnum) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = null;
		this.dialogEnum=dialogEnum;
		if (dialogEnum == DialogEnum.audit_no) {
			view = inflater.inflate(R.layout.dialog_auditno_item, null);
			dialog_button = (TextView) view.findViewById(R.id.dialog_sure);
			dialog_iv = (CircularImage) view.findViewById(R.id.dialog_iv);
			dialog_button.setOnClickListener(this);
			dialog_iv.setOnClickListener(this);
			setCancelable(true);
			setCanceledOnTouchOutside(true);
		}else if(dialogEnum == DialogEnum.audit_ing){
			view = inflater.inflate(R.layout.dialog_auditing_item, null);
			dialog_cancle=(ImageView) view.findViewById(R.id.dialog_cancle);
			dialog_cancle.setOnClickListener(this);
			setCancelable(true);
			setCanceledOnTouchOutside(true);
		}else if(dialogEnum==DialogEnum.audit_refused){
			view = inflater.inflate(R.layout.dialog_auditrefused_item, null);
			dialog_button = (TextView) view.findViewById(R.id.dialog_sure);
			dialog_iv = (CircularImage) view.findViewById(R.id.dialog_iv);
			dialog_button.setOnClickListener(this);
			dialog_iv.setOnClickListener(this);
			setCancelable(true);
			setCanceledOnTouchOutside(true);
		}else{
			view = inflater.inflate(R.layout.dialog_audited_item, null);
			dialog_cancle=(ImageView) view.findViewById(R.id.dialog_cancle);
			dialog_cancle.setOnClickListener(this);
			setCancelable(true);
			setCanceledOnTouchOutside(true);
	
		}
		setView(view);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.dialog_sure:
			listener.buttomOnclickListener(dialogEnum);
			break;
		case R.id.dialog_iv:
			listener.topOnclickListener(dialogEnum);
			break;
		case R.id.dialog_cancle:	
			cancel();
		default:
			
			break;
		}
	}

	public DialogListener getListener() {
		return listener;
	}

	public void setListener(DialogListener listener) {
		this.listener = listener;
	}

	@Override
	public void dismiss() {
		listener.dissMiss(dialogEnum);
		super.dismiss();
	}
	
}
