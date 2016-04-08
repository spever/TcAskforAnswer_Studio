package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
import java.util.List;

public class Car_Tapes implements Serializable{

	private List<String> letters;
	
	private List<List<Car_Type>> carinfo;

	public Car_Tapes() {
		super();
	}

	public Car_Tapes(List<String> letters, List<List<Car_Type>> carinfo) {
		super();
		this.letters = letters;
		this.carinfo = carinfo;
	}

	public List<String> getLetters() {
		return letters;
	}

	public void setLetters(List<String> letters) {
		this.letters = letters;
	}

	public List<List<Car_Type>> getCarinfo() {
		return carinfo;
	}

	public void setCarinfo(List<List<Car_Type>> carinfo) {
		this.carinfo = carinfo;
	}

	@Override
	public String toString() {
		return "Car_Tapes [letters=" + letters + ", carinfo=" + carinfo + "]";
	}
	
	
}
