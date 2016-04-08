package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class CompleteBankInfo implements Serializable{

	private String ret;
	
	private String msg;

	public CompleteBankInfo() {
		super();
	}

	public CompleteBankInfo(String ret, String msg) {
		super();
		this.ret = ret;
		this.msg = msg;
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

	@Override
	public String toString() {
		return "CompleteBankInfo [ret=" + ret + ", msg=" + msg + "]";
	}
	
	
}
