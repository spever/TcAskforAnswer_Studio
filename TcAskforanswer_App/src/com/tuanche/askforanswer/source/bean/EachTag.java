package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class EachTag implements Serializable{

	private String id;
	
	private String name;
	
	public Boolean bool = false;  //图片 是否  被点击

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EachTag() {
		super();
	}

	public EachTag(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "EachTag [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
