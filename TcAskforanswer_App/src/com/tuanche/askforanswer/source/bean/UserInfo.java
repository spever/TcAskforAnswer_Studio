package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int crmUserId;
	private int type;
	private String brandId;
	private String city;
	private int status;
	private String userPhone;
	private String tags;
	private int mobileType;
	private int switchStatus;
	public UserInfo(int id, int crmUserId, int type, String brandId,
			String city, int status, String userPhone, String tags,
			int mobileType, int switchStatus) {
		super();
		this.id = id;
		this.crmUserId = crmUserId;
		this.type = type;
		this.brandId = brandId;
		this.city = city;
		this.status = status;
		this.userPhone = userPhone;
		this.tags = tags;
		this.mobileType = mobileType;
		this.switchStatus = switchStatus;
	}
	public UserInfo() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCrmUserId() {
		return crmUserId;
	}
	public void setCrmUserId(int crmUserId) {
		this.crmUserId = crmUserId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public int getMobileType() {
		return mobileType;
	}
	public void setMobileType(int mobileType) {
		this.mobileType = mobileType;
	}
	public int getSwitchStatus() {
		return switchStatus;
	}
	public void setSwitchStatus(int switchStatus) {
		this.switchStatus = switchStatus;
	}
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", crmUserId=" + crmUserId + ", type="
				+ type + ", brandId=" + brandId + ", city=" + city
				+ ", status=" + status + ", userPhone=" + userPhone + ", tags="
				+ tags + ", mobileType=" + mobileType + ", switchStatus="
				+ switchStatus + "]";
	}
	
}
