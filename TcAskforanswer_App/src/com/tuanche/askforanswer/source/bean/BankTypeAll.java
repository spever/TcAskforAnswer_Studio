package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class BankTypeAll implements Serializable{

	private String ret;
	
	private String msg;
	
	private BankTypeResult result;

	public BankTypeAll() {
		super();
	}

	public BankTypeAll(String ret, String msg, BankTypeResult result) {
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

	public BankTypeResult getResult() {
		return result;
	}

	public void setResult(BankTypeResult result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "BankTypeAll [ret=" + ret + ", msg=" + msg + ", result="
				+ result + "]";
	}
	
}
