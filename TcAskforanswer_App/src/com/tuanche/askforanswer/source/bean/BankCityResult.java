package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
import java.util.List;

public class BankCityResult implements Serializable{

	private List<String> letter;
	
	private List<List<BankCityProvince>> province;

	public BankCityResult() {
		super();
	}

	public BankCityResult(List<String> letter,
			List<List<BankCityProvince>> province) {
		super();
		this.letter = letter;
		this.province = province;
	}

	public List<String> getLetter() {
		return letter;
	}

	public void setLetter(List<String> letter) {
		this.letter = letter;
	}

	public List<List<BankCityProvince>> getProvince() {
		return province;
	}

	public void setProvince(List<List<BankCityProvince>> province) {
		this.province = province;
	}

	@Override
	public String toString() {
		return "BankCityResult [letter=" + letter + ", province=" + province
				+ "]";
	}
	
}
