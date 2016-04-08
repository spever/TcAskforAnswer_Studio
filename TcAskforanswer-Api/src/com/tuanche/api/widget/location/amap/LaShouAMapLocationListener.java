package com.tuanche.api.widget.location.amap;

import android.location.Location;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.tuanche.api.widget.location.LocationUtils.LaShouLocationListener;
import com.tuanche.api.widget.location.entity.LashouLocation;

public class LaShouAMapLocationListener implements AMapLocationListener {

	private LaShouLocationListener laShouLocationListener;

	public LaShouAMapLocationListener(
			LaShouLocationListener laShouLocationListener) {
		this.laShouLocationListener = laShouLocationListener;
	}

	@Override
	public void onLocationChanged(Location location) {
		if (laShouLocationListener != null) {

		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		if (laShouLocationListener != null) {
			laShouLocationListener.onStatusChanged(provider, status, extras);
		}
	}

	@Override
	public void onProviderEnabled(String provider) {
		if (laShouLocationListener != null) {
			laShouLocationListener.onProviderEnabled(provider);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		if (laShouLocationListener != null) {
			laShouLocationListener.onProviderDisabled(provider);
		}
	}

	@Override
	public void onLocationChanged(AMapLocation aMapLocation) {
		if (laShouLocationListener != null) {
			LashouLocation lashouLocation = new LashouLocation();
			lashouLocation.setLatitude(String.valueOf(aMapLocation
					.getLatitude()));
			lashouLocation.setLongitude(String.valueOf(aMapLocation
					.getLongitude()));
			
			Bundle locBundle = aMapLocation.getExtras();
			if (locBundle != null) {
				lashouLocation.setDesc(locBundle.getString("desc"));
			}
			
			laShouLocationListener.onLocationChanged(lashouLocation);
			
		}
	}

}