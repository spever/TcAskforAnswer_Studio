package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
import java.util.List;

public class AllCities implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> letters;
	
	private List<List<EachCity>> cityinfo;

	public AllCities() {
		super();
	}

	public AllCities(List<String> letters, List<List<EachCity>> cityinfo) {
		super();
		this.letters = letters;
		this.cityinfo = cityinfo;
	}

	public List<String> getLetters() {
		return letters;
	}

	public void setLetters(List<String> letters) {
		this.letters = letters;
	}

	public List<List<EachCity>> getCityinfo() {
		return cityinfo;
	}

	public void setCityinfo(List<List<EachCity>> cityinfo) {
		this.cityinfo = cityinfo;
	}

	@Override
	public String toString() {
		return "AllCities [letters=" + letters + ", cityinfo=" + cityinfo + "]";
	}


	
}
