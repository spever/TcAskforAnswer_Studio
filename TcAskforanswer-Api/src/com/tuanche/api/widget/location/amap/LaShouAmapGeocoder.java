package com.tuanche.api.widget.location.amap;

import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.StreetNumber;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.tuanche.api.widget.location.LaShouGeocoderUtils.LaShouGeocoderListener;
import com.tuanche.api.widget.location.core.LaShouGeocoder;

/**
 * 拉手地理位置解析
 * @author Wangzhen
 *
 */
public class LaShouAmapGeocoder implements LaShouGeocoder,
		OnGeocodeSearchListener {

	private Context mContext;
	private LaShouGeocoderListener laShouGeocoderListener;
	private GeocodeSearch geocoderSearch;

	public LaShouAmapGeocoder(Context context,
			LaShouGeocoderListener laShouGeocoderListener) {
		this.mContext = context;
		this.laShouGeocoderListener = laShouGeocoderListener;
	}

	/**
	 * 根据经纬度获取地址信息
	 */
	@Override
	public void getAddress(String longitude, String latitude) {
		geocoderSearch = new GeocodeSearch(mContext);
		geocoderSearch.setOnGeocodeSearchListener(this);
		// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		LatLonPoint latLonPoint = new LatLonPoint(Double.valueOf(latitude),
				Double.valueOf(longitude));
		RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
				GeocodeSearch.AMAP);
		geocoderSearch.getFromLocationAsyn(query);
	}
	
	/**
	 * 根据地址信息获取经纬度
	 * 暂不使用
	 */
	@Override
	public void getLocation(String address) {

	}

	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
		/*
		 * 暂不使用，根据地址信息获取经纬度
		 */
	}
	
	/**
	 * 根据经纬度获取到地址信息时的回调函数
	 */
	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
		if (rCode == 0) {
			if (result != null && result.getRegeocodeAddress() != null
					&& result.getRegeocodeAddress().getFormatAddress() != null) {
				String addressName = result.getRegeocodeAddress()
						.getFormatAddress() + "附近";
				RegeocodeAddress address =  result.getRegeocodeAddress();
				StreetNumber streetNumber = address.getStreetNumber();
	            String street = "";
	            String number = "";
	            if(streetNumber!=null){
	            	street = streetNumber.getStreet();
	            	number = streetNumber.getNumber();
	            }
//	            List<Crossroad> crossroads = address.getCrossroads();
	            String district = address.getDistrict();
//	            String formatAddress = address.getFormatAddress();
//	            String neighborhood = address.getNeighborhood();
//	            String townShip = address.getTownship();
//	            List<RegeocodeRoad> roads = address.getRoads();
	            addressName = "" + district +" "+ street + number;
				
				laShouGeocoderListener.onRegeocodeSearched(addressName);
			} else {
			}
		} else {
		}
	}

}
