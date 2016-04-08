package com.tuanche.api.widget.location;

import android.content.Context;
import android.os.Bundle;

import com.tuanche.api.widget.location.amap.LaShouAmapLocationTool;
import com.tuanche.api.widget.location.core.LaShouLocationTool;
import com.tuanche.api.widget.location.entity.LashouLocation;

/**
 * 拉手定位工具类
 * 
 * @author Wangzhen
 * 
 */
public class LocationUtils {

	private static LaShouLocationTool laShouLocationTool;
	private static LocationType mLocationType;

	/**
	 * 定位类型，一般为第三方定位
	 * 
	 * @author Wangzhen
	 * 
	 */
	public enum LocationType {
		/** 高德定位 */
		TYPE_AMAP
	}

	/**
	 * 获取定位
	 * 
	 * @param context
	 * @param laShouLocationListener
	 * @param locationType
	 */
	public static void getLocation(Context context,
			LaShouLocationListener laShouLocationListener,
			LocationType locationType) {

		initLaShouLocationTool(locationType);
		
		if(laShouLocationTool!=null)
		laShouLocationTool.startLocation(context, laShouLocationListener);
	}

	/**
	 * 初始化
	 * 
	 * @param locationType
	 */
	private static void initLaShouLocationTool(LocationType locationType) {

		/*
		 * 如果使用的地图类型没有改变，且laShouLocationTool不为空，则不创建新的laShouLocationTool
		 */
		if (locationType == mLocationType && laShouLocationTool != null) {
			return;
		}

		mLocationType = locationType;
		switch (locationType) {
		case TYPE_AMAP:
			laShouLocationTool = new LaShouAmapLocationTool();
			break;
		default:
			break;
		}
	}

	/**
	 * 停止定位
	 */
	public static void stopLocation() {
		if (laShouLocationTool == null || mLocationType == null) {
			return;
		}
		laShouLocationTool.stopLocation();
	}

	/**
	 * 销毁定位
	 */
	public static void release() {
		if (laShouLocationTool == null || mLocationType == null) {
			return;
		}
		laShouLocationTool.destory();
	}

	/**
	 * 拉手定位回调
	 * 
	 * @author Wangzhen
	 * 
	 */
	public interface LaShouLocationListener {

		void onProviderEnabled(String provider);

		void onProviderDisabled(String provider);

		void onStatusChanged(String provider, int status, Bundle extras);

		/**
		 * 位置变化
		 * 
		 * @param lashouLocation
		 */
		void onLocationChanged(LashouLocation lashouLocation);

	}

	public static class SimpleLaShouLocationListener implements
			LaShouLocationListener {

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onLocationChanged(LashouLocation lashouLocation) {

		}

	}

}
