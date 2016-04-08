package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class ResponseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ret;
	private String msg;
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public ResponseBean() {
		super();
	}
	@Override
	public String toString() {
		return "ResponseBean [ret=" + ret + ", msg=" + msg + "]";
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
