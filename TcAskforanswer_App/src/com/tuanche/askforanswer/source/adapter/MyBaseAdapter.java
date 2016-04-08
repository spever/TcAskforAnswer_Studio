package com.tuanche.askforanswer.source.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.tuanche.api.bitmap.BitmapDisplayConfig;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.utils.PictureUtils;

public abstract class MyBaseAdapter extends BaseAdapter{
	protected BitmapDisplayConfig config;
	protected PictureUtils pictureUtils;

	public MyBaseAdapter(Context context){
		pictureUtils = PictureUtils.getInstance(context);
		config = new BitmapDisplayConfig();
		config.setLoadingDrawable(context.getApplicationContext().getResources().getDrawable(R.drawable.head_user));
		config.setLoadFailedDrawable(context.getApplicationContext().getResources().getDrawable(R.drawable.head_user));
	}
	
}
