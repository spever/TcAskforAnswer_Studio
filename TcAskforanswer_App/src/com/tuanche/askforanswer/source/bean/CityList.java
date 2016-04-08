package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
import java.util.List;

public class CityList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CityEach> city;


	public CityList() {
		super();
	}


	public CityList(List<CityEach> city) {
		super();
		this.city = city;
	}


	public List<CityEach> getCity() {
		return city;
	}


	public void setCity(List<CityEach> city) {
		this.city = city;
	}


	@Override
	public String toString() {
		return "CityList [city=" + city + "]";
	}

	
}
