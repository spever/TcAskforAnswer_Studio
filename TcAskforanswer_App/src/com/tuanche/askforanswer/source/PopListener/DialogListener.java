package com.tuanche.askforanswer.source.PopListener;

public interface DialogListener {
	public static enum DialogEnum{
		audit_no,audit_ing,audit_refused,audit_ed
	}
	void buttomOnclickListener(DialogEnum diagEnum);
	void topOnclickListener(DialogEnum diagEnum);
	void topDeleteClickListener(DialogEnum diagEnum);
	void dissMiss(DialogEnum diagEnum);
}
