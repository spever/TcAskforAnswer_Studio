package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class SelectCity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ret;
	
	private String msg;
	
	private AllCities result;

	public SelectCity() {
		super();
	}

	
	
	public SelectCity(String ret, String msg, AllCities result) {
		super();
		this.ret = ret;
		this.msg = msg;
		this.result = result;
	}




	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}



	public AllCities getResult() {
		return result;
	}



	public void setResult(AllCities result) {
		this.result = result;
	}



	@Override
	public String toString() {
		return "SelectCity [ret=" + ret + ", msg=" + msg + ", result=" + result
				+ "]";
	}

	
}
