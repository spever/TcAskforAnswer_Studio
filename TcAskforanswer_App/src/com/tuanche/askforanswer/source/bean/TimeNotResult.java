package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class TimeNotResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ret;
	
	private String msg;

	public TimeNotResult() {
		super();
	}

	public TimeNotResult(String ret, String msg) {
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
		return "TimeNotResult [ret=" + ret + ", msg=" + msg + "]";
	}
	
	
	
}
