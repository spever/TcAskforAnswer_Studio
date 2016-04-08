package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class MoneyBooty implements Serializable{

	private MonentyGet myBooty;

	public MoneyBooty() {
		super();
	}

	public MoneyBooty(MonentyGet myBooty) {
		super();
		this.myBooty = myBooty;
	}

	public MonentyGet getMyBooty() {
		return myBooty;
	}

	public void setMyBooty(MonentyGet myBooty) {
		this.myBooty = myBooty;
	}

	@Override
	public String toString() {
		return "MoneyBooty [myBooty=" + myBooty + "]";
	}
	
	
}
