package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class CityEach implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String city_code;
	
	private String city_name;

	public CityEach() {
		super();
	}

	public CityEach(String city_code, String city_name) {
		super();
		this.city_code = city_code;
		this.city_name = city_name;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	@Override
	public String toString() {
		return "CityEach [city_code=" + city_code + ", city_name=" + city_name
				+ "]";
	}
	
	
}
