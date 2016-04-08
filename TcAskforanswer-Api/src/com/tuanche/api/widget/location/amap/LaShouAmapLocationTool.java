package com.tuanche.api.widget.location.amap;

import android.content.Context;

import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.tuanche.api.widget.location.LocationUtils.LaShouLocationListener;
import com.tuanche.api.widget.location.core.LaShouLocationTool;

public class LaShouAmapLocationTool implements LaShouLocationTool {

	private LocationManagerProxy aMapLocManager = null;
	private LaShouAMapLocationListener laShouAMapLocationListener;

	@Override
	public void startLocation(Context context,
			LaShouLocationListener laShouLocationListener) {
		if (aMapLocManager == null) {
			aMapLocManager = LocationManagerProxy.getInstance(context);
			this.laShouAMapLocationListener = new LaShouAMapLocationListener(
					laShouLocationListener);
			aMapLocManager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork, 2000, 10,
					this.laShouAMapLocationListener);
		}
		
	}

	/**
	 * 销毁定位
	 */
	@Override
	public void stopLocation() {
		if (aMapLocManager != null) {
			aMapLocManager.removeUpdates(this.laShouAMapLocationListener);
		}
	}

	@Override
	public void destory() {
		if (aMapLocManager != null) {
			aMapLocManager.removeUpdates(this.laShouAMapLocationListener);
			aMapLocManager.destory();
		}
		aMapLocManager = null;
	}

}
