package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class CityAll implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ret;
	
	private String msg;
	
	private CityList result;

	public CityAll() {
		super();
	}

	public CityAll(String ret, String msg, CityList result) {
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

	public CityList getResult() {
		return result;
	}

	public void setResult(CityList result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "CityAll [ret=" + ret + ", msg=" + msg + ", result=" + result
				+ "]";
	}
	
}
