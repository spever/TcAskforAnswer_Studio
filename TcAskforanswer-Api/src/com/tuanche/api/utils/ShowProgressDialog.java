
/**************************************************************************************
* [Project]
*       MyProgressDialog
* [Package]
*       com.lxd.widgets
* [FileName]
*       CustomProgressDialog.java
* [Copyright]
*       Copyright 2012 LXD All Rights Reserved.
* [History]
*       Version          Date              Author                        Record
*--------------------------------------------------------------------------------------
*       1.0.0           2012-4-27         lxd (rohsuton@gmail.com)        Create
**************************************************************************************/
	
package com.tuanche.api.utils;

 

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuanche.api.R; 

public class ShowProgressDialog extends Dialog {
	private static ShowProgressDialog progressDialog = null;
	 
	public ShowProgressDialog(Context context, int theme) {
        super(context, theme);
    }
	public static void ShowProgressOn(Context context,String title,String content){
		progressDialog=ShowProgressDialog.createDialog(context);
		progressDialog.setTitile(title);
		progressDialog.setMessage(content);
		progressDialog.show();
	}
	public static void ShowProgressOn(Context context,String title,String content,boolean cancelableOutside){
		progressDialog=ShowProgressDialog.createDialog(context);
		progressDialog.setCanceledOnTouchOutside(cancelableOutside);
		progressDialog.setTitile(title);
		progressDialog.setMessage(content);
		progressDialog.show();
	}
	public static void ShowProgressOn(Context context,String title,String content,boolean cancelable,boolean cancelableOutside){
		progressDialog=ShowProgressDialog.createDialog(context);
		progressDialog.setCancelable(cancelable);
		progressDialog.setCanceledOnTouchOutside(cancelableOutside);
		progressDialog.setTitile(title);
		progressDialog.setMessage(content);
		progressDialog.show();
	}
    public static void ShowProgressOff() {
        if (progressDialog != null)
        	progressDialog.dismiss();
    }
	public static ShowProgressDialog createDialog(Context context){
		progressDialog = new ShowProgressDialog(context,R.style.CustomProgressDialog);
		progressDialog.setContentView(R.layout.progress_dialog);
		progressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return progressDialog;
	}
 
    public void onWindowFocusChanged(boolean hasFocus){
    	if (progressDialog == null){
    		return;
    	}
    	
        ImageView imageView = (ImageView) progressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }
 
    /**
     * setTitile 设置标题
     * @param strTitle
     * @return
     *
     */
    public ShowProgressDialog setTitile(String strTitle){
    	return progressDialog;
    }
    
    /**
     * setMessage 设置提示信息
     * @param strMessage
     * @return
     *
     */
    public ShowProgressDialog setMessage(String strMessage){
    	TextView tvMsg = (TextView)progressDialog.findViewById(R.id.id_tv_loadingmsg);
    	
    	if (tvMsg != null){
    		tvMsg.setText(strMessage);
    	}
    	return progressDialog;
    }
    /**
     * isClose 是否被关闭
     * @return boolean
     *
     */
    public static boolean isDialogShowing() {
    	if (progressDialog!=null){
    		return progressDialog.isShowing();
    	}else
    	{
    		return true;
    	}
	}
}
