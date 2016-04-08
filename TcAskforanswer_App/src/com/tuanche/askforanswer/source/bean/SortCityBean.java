package com.tuanche.askforanswer.source.bean;

public class SortCityBean {

	private String name;    // 对应EachCity 中的   city_name
	private String sortLetters;  
	
	private String city_code;

	public SortCityBean() {
		super();
	}

	public SortCityBean(String name, String sortLetters, String city_code) {
		super();
		this.name = name;
		this.sortLetters = sortLetters;
		this.city_code = city_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	@Override
	public String toString() {
		return "SortCityBean [name=" + name + ", sortLetters=" + sortLetters
				+ ", city_code=" + city_code + "]";
	}
	
	
}
