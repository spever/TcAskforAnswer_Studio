package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class MineInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ret;
	private String msg;
	private MineBean result;
	public MineInfo() {
		super();
	}
	public MineInfo(String ret, String msg, MineBean result) {
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
	public MineBean getResult() {
		return result;
	}
	public void setResult(MineBean result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "MineInfo [ret=" + ret + ", msg=" + msg + ", result=" + result
				+ "]";
	}
	
	
	
}
