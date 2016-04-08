package com.tuanche.api.widget.location.entity;

public class LashouLocation {

	/** 纬度 */
	private String latitude;
	/** 经度 */
	private String longitude;
	/**位置描述信息*/
	private String desc;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
