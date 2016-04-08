package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class LoginOrRegist implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ret;
	
	private String msg;

	private UserBean result;
	
	public LoginOrRegist() {
		super();
	}

	public LoginOrRegist(String ret, String msg, UserBean result) {
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

	public UserBean getResult() {
		return result;
	}

	public void setResult(UserBean result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "LoginOrRegist [ret=" + ret + ", msg=" + msg + ", result="
				+ result + "]";
	}
	
	
}
