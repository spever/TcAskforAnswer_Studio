package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class BankInfo implements Serializable{

	private String ret;
	private String msg;
	private BankResult result;
	public BankInfo() {
		super();
	}
	public BankInfo(String ret, String msg, BankResult result) {
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
	public BankResult getResult() {
		return result;
	}
	public void setResult(BankResult result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "BankInfo [ret=" + ret + ", msg=" + msg + ", result=" + result
				+ "]";
	}
	
}
