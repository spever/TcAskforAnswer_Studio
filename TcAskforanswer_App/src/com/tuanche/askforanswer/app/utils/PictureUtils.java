package com.tuanche.askforanswer.app.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.tuanche.api.bitmap.BitmapDisplayConfig;
import com.tuanche.api.bitmap.callback.BitmapLoadCallBack;
import com.tuanche.api.utils.BitmapUtils;

public class PictureUtils extends BitmapUtils{
	private Context context=null;
	private static PictureUtils pictureUtils=null;
	public PictureUtils(Context context) {
		super(context);
		this.context=context;
		
		// TODO Auto-generated constructor stub
	}
	public static PictureUtils getInstance(Context context){
		if(pictureUtils==null){
			pictureUtils=new PictureUtils(context);
		}
		return pictureUtils;
	}
	public static BitmapDisplayConfig createConfig(Context context,int drawableId){
		BitmapDisplayConfig	config = new BitmapDisplayConfig();
		config.setLoadingDrawable(context.getResources().getDrawable(
				drawableId));
		config.setLoadFailedDrawable(context.getResources().getDrawable(
				drawableId));
		return config;
	}
	
	
	@Override
	public void display(ImageView container, String url) {
		// TODO Auto-generated method stub
		//if(container==null || url==null) return;
		 
//		if (Session.get(context).isOnlyWifi() && AppUtils.isMobileNetwork(context)) {
//			super.display(container, "");
//			return;
//		}
		super.display(container, url);
	}
	public void displayT(ImageView container, String url,
			BitmapDisplayConfig displayConfig) {
		if(container==null || url==null) return;
		 
		super.display(container, url, displayConfig);
	}
	@Override
	public void display(ImageView container, String url,
			BitmapDisplayConfig displayConfig) {
		// TODO Auto-generated method stub
		if(container==null || url==null) return;
//		if (Session.get(context).isOnlyWifi() && AppUtils.isMobileNetwork(context)) {
//			super.display(container, "", displayConfig);
//			return;
//		}
		super.display(container, url, displayConfig);
	}
	@Override
	public <T extends View> void display(T container, String url,
			BitmapDisplayConfig displayConfig, BitmapLoadCallBack<T> callBack) {
		// TODO Auto-generated method stub
		if(container==null || url==null) return;
//		if (Session.get(context).isOnlyWifi() && AppUtils.isMobileNetwork(context)) {
//			super.display(container, "", displayConfig, callBack);
//			return;
//		}
		super.display(container, url, displayConfig, callBack);
	}
	@Override
	public <T extends View> void display(T container, String url,
			BitmapLoadCallBack<T> callBack) {
		// TODO Auto-generated method stub
		if(container==null || url==null) return;
//		if (Session.get(context).isOnlyWifi() && AppUtils.isMobileNetwork(context)) {
//			super.display(container, "", callBack);
//			return;
//		}
		super.display(container, url, callBack);
	}
}
