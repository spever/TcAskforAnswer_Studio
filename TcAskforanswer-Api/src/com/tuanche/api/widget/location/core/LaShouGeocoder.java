package com.tuanche.api.widget.location.core;

public interface LaShouGeocoder {
	/**
	 * 根据经纬度获取地址信息
	 * 
	 * @param longitude 经度
	 * @param latitude  纬度
	 */
	void getAddress(String longitude, String latitude);
	
	/**
	 * 根据地址信息获取经纬度
	 * @param address
	 */
	void getLocation(String address);

}
