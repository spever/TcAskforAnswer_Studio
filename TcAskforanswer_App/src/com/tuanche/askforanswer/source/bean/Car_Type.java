package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class Car_Type implements Serializable{
	
	
	private int id;
	
	private String logo;
	
	private String name;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Car_Type() {
		super();
	}

	public Car_Type(int id, String logo, String name) {
		super();
		this.id = id;
		this.logo = logo;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Car_Type [id=" + id + ", logo=" + logo + ", name=" + name + "]";
	}
	

}
