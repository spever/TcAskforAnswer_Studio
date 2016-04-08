package com.tuanche.askforanswer.source.bean;

public class BankCityProvince {

	private String province_code;
	
	private String province_name;

	public BankCityProvince() {
		super();
	}

	public BankCityProvince(String province_code, String province_name) {
		super();
		this.province_code = province_code;
		this.province_name = province_name;
	}

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	@Override
	public String toString() {
		return "BankCityProvince [province_code=" + province_code
				+ ", province_name=" + province_name + "]";
	}
	
	
}
