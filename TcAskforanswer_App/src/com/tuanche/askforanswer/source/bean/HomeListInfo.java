package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class HomeListInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public HomeListInfoBean getResult() {
		return result;
	}
	public void setResult(HomeListInfoBean result) {
		this.result = result;
	}
	private int ret;
	private String msg;
	private HomeListInfoBean result;
	
}
