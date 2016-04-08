package com.tuanche.askforanswer.source.bean;
/**
 * 几个类     都关联 此类
 * @author zpf
 *
 */
public class SortModel {

	
	private String name;  
	private String sortLetters;  
	
	private String city_code;     //针对    选择城市 
	
	private int id;          //针对    品牌对应的id
	private String logo;     //品牌  logo
	
	
	private String province_code;  //针对   银行开通省份

	
	
	
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	
	
}
