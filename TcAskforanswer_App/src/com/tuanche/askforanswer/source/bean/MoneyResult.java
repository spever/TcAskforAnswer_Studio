package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
/**
 * 提现结果
 * @author zpf
 *
 */
public class MoneyResult implements Serializable{

	private String ret;
	
	private String msg;

	public MoneyResult() {
		super();
	}

	public MoneyResult(String ret, String msg) {
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
		return "MoneyResult [ret=" + ret + ", msg=" + msg + "]";
	}
	
	
}
