package com.tuanche.askforanswer.app.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.Session;

public class LocationUtils implements AMapLocationListener,Runnable,OnGeocodeSearchListener{
	public    LocationManagerProxy locationMannager = null;
	private AMapLocation aMapLocation;// 用于判断定位超时
	private final int LOCATION_DELAY_TIME=3*1000;
	private Handler handler = new Handler();
	private Session session=null;
	private ApiRequestListener listener=null;
	private Context context=null;
	private boolean isGetCityInfo=false;
	private boolean isGetAddressInfo=false;
	private LatLonPoint point=null;
	private int range=200;
	private boolean isStopped = false;
	public  void getLocation(Context context,ApiRequestListener listener,boolean isGetCityInfo,boolean isGetAddressInfo){
			session=Session.get(context);
			this.context=context;
			this.listener=listener;
			this.isGetCityInfo=isGetCityInfo;
			this.isGetAddressInfo=isGetAddressInfo;
			locationMannager = LocationManagerProxy.getInstance(context);
			/*
			 * mAMapLocManager.setGpsEnable(false);//
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
			locationMannager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork, 2000, 10, this);
			handler.postDelayed(this, LOCATION_DELAY_TIME);// 设置超过12秒还没有定位到就停止定位
	}
 
	public void getLocationFromPoint(Context context,ApiRequestListener listener,LatLonPoint point,int range){
		 	this.context=context;
		 	this.listener=listener;
		 	session=Session.get(context);
		 	GeocodeSearch	geocoderSearch = new GeocodeSearch(context);
			geocoderSearch.setOnGeocodeSearchListener(this);
			RegeocodeQuery query = new RegeocodeQuery(point, 200,
					GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
			
			geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
//		// TODO Auto-generated method stub
//		if (locationMannager != null && !isStopped) {
//			this.aMapLocation = location;// 判断超时机制
//			Double geoLat = location.getLatitude();
//			Double geoLng = location.getLongitude();
//			String cityCode = "";
//			String desc = "";
//			Bundle locBundle = location.getExtras();
//			if (locBundle != null) {
//				cityCode = locBundle.getString("citycode");
//				desc = locBundle.getString("desc");
//			}
//			LogUtils.d("精度===>"+geoLat+",纬度===>"+geoLng);
//			String latitude=String.valueOf(geoLat);
//			session.setLocation_city_lat(latitude);
//			String longtitude=String.valueOf(geoLng);
//			session.setLocation_city_lng(longtitude);
//			//设置当前定位成功
//			session.setLocationSuccess(true);
//			listener.onSuccess(Action.LOCATION, "定位成功");
//			if(isGetCityInfo){
////				AppApi.getCityByLocation(this.context, listener, latitude, longtitude);
//			}
//			/*
//			 * isGetAddressInfo || 
//			 * 根据产品需要，每次定位成功都要获取位置信息
//			 */
//			// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
////			if(point==null){
//				point=new LatLonPoint(geoLat, geoLng);
////			}
//			if(point!=null){
////				session.setLocationAddress("我的位置");
//				GeocodeSearch	geocoderSearch = new GeocodeSearch(context); 
//				geocoderSearch.setOnGeocodeSearchListener(this);
//				// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
////				if(point==null){
////					point=new LatLonPoint(geoLat, geoLng);
////				}
//				RegeocodeQuery query = new RegeocodeQuery(point, range,GeocodeSearch.AMAP); 
//				geocoderSearch.getFromLocationAsyn(query); 
//			}
//			/*
//			String str = ("定位成功:(" + geoLng + "," + geoLat + ")"
//					+ "\n精    度    :" + location.getAccuracy() + "米"
//					+ "\n定位方式:" + location.getProvider() + "\n定位时间:"
//					+ AMapUtil.convertToTime(location.getTime()) + "\n城市编码:"
//					+ cityCode + "\n位置描述:" + desc + "\n省:"
//					+ location.getProvince() + "\n市:" + location.getCity()
//					+ "\n区(县):" + location.getDistrict() + "\n区域编码:" + location
//					.getAdCode());
//			myLocation.setText(str);
//			*/
//			stopLocation();
//		}
	}
	/**
	 * 销毁定位
	 */
	private void stopLocation() {
		if (locationMannager != null) {
			locationMannager.removeUpdates(this);
			locationMannager.destroy();
		}
		locationMannager = null;
 	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (aMapLocation == null) {
//			ToastUtil.show(this, "12秒内还没有定位成功，停止定位");
//			myLocation.setText("12秒内还没有定位成功，停止定位");
			stopLocation();// 销毁掉定位
			if(listener!=null && !isStopped){
				listener.onError(Action.LOCATION, "^_^亲，本次定位失败，请重试");
			}
		}
	}

	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
		// TODO Auto-generated method stub
//		if(rCode == 0){ 
//	        if(result != null&&result.getRegeocodeAddress() != null 
//	                 &&result.getRegeocodeAddress().getFormatAddress()!=null){ 
//	            String addressName = result.getRegeocodeAddress().getFormatAddress() + "附近";
//	            RegeocodeAddress address =  result.getRegeocodeAddress();
////	            String building = address.getBuilding();
////	            String province = address.getProvince();
////	            String city = address.getCity();
//	            StreetNumber streetNumber = address.getStreetNumber();
//	            String street = "";
//	            String number = "";
//	            if(streetNumber!=null){
//	            	street = streetNumber.getStreet();
//	            	number = streetNumber.getNumber();
//	            }
////	            List<Crossroad> crossroads = address.getCrossroads();
//	            String district = address.getDistrict();
////	            String formatAddress = address.getFormatAddress();
////	            String neighborhood = address.getNeighborhood();
////	            String townShip = address.getTownship();
////	            List<RegeocodeRoad> roads = address.getRoads();
//	            addressName = "" + district +" "+ street + number;
//	            
//	            if(session!=null){
//	            	session.setLocationAddress(addressName);
//	            }else{
//	            	try {
//	            		session = Session.get(context);
//	            		session.setLocationAddress(addressName);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//	            }
//	            if(listener!=null && !isStopped){
//	            	listener.onSuccess(Action.LOCATION_ADDGRESS, addressName);
//	            }
//	        }else{ 
//	        	if(listener!=null && !isStopped){
//	        		if(!TextUtils.isEmpty(session.getLocationAddress())){
//	        			listener.onSuccess(Action.LOCATION_ADDGRESS, session.getLocationAddress());
//	        		}else{
//	        			listener.onError(Action.LOCATION_ADDGRESS, "定位失败，请重试");
//	        		}
//	        	}
//	        } 
//	    }else{ 
//	    	 
//	    	if(listener!=null && !isStopped){
//        		if(!TextUtils.isEmpty(session.getLocationAddress())){
//        			listener.onSuccess(Action.LOCATION_ADDGRESS, session.getLocationAddress());
//        		}else{
//        			listener.onError(Action.LOCATION_ADDGRESS, "定位失败，请重试");
//        		}
//        	}
//	    } 
	}

	public boolean isStopped() {
		return isStopped;
	}

	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}
	
}
