package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class BankCityBean implements Serializable{

	private String ret;
	
	private String msg;
	
	private BankCityResult result;

	public BankCityBean() {
		super();
	}

	public BankCityBean(String ret, String msg, BankCityResult result) {
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

	public BankCityResult getResult() {
		return result;
	}

	public void setResult(BankCityResult result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "BankCityBean [ret=" + ret + ", msg=" + msg + ", result="
				+ result + "]";
	}
	
}
