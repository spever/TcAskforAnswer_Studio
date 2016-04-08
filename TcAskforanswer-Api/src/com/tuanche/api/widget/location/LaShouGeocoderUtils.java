package com.tuanche.api.widget.location;

import android.content.Context;

import com.tuanche.api.widget.location.amap.LaShouAmapGeocoder;
import com.tuanche.api.widget.location.core.LaShouGeocoder;

public class LaShouGeocoderUtils {

	private static LaShouGeocoder laShouGeocoder;
	private static GeocoderType mGeocoderType;

	public enum GeocoderType {
		/** 高德 */
		TYPE_AMAP
	}
	
	/**
	 * 根据经纬度解析地址信息
	 * @param context
	 * @param geocoderListener
	 * @param geocoderType
	 * @param longitude
	 * @param latitude
	 */
	public static void geocoder(Context context,LaShouGeocoderListener geocoderListener,
			GeocoderType geocoderType, String longitude, String latitude) {
		if (laShouGeocoder == null || mGeocoderType != geocoderType) {
			switch (geocoderType) {
			case TYPE_AMAP:
				laShouGeocoder = new LaShouAmapGeocoder(context,geocoderListener);
				break;

			default:
				break;
			}
		}

		if (laShouGeocoder != null) {
			laShouGeocoder.getAddress(longitude, latitude);
		}

	}

	/**
	 * 逆向解析经纬度完成回调
	 * 
	 * @author Wangzhen
	 * 
	 */
	public interface LaShouGeocoderListener {
		/**
		 * 根据经纬度信息获取到地址信息的回调函数
		 * @param address
		 */
		void onRegeocodeSearched(String address);
		
		/**
		 * 根据地址信息获取到经纬度信息的回调函数
		 * @param longitude
		 * @param latitude
		 */
		void onGeocodeSearched(String longitude, String latitude);
	}

	public static class SimpleLaShouGeocoderListener implements
			LaShouGeocoderListener {

		@Override
		public void onRegeocodeSearched(String address) {
			
		}

		@Override
		public void onGeocodeSearched(String longitude, String latitude) {
			
		}

	}
}
