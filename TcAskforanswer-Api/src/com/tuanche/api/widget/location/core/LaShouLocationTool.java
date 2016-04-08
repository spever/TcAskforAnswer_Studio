package com.tuanche.api.widget.location.core;

import android.content.Context;

import com.tuanche.api.widget.location.LocationUtils.LaShouLocationListener;

/**
 * 拉手定位标准接口
 * 
 * @author Wangzhen
 * 
 */
public interface LaShouLocationTool {

	/**
	 * 开始定位
	 * 
	 * @param context
	 * @param laShouLocationListener
	 */
	void startLocation(Context context,
			LaShouLocationListener laShouLocationListener);

	/**
	 * 停止定位
	 */
	void stopLocation();
	
	void destory();

}
