package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class AwardMoney implements Serializable{

	private String ret;
	
	private String msg;
	
	private MoneyBooty result;

	public AwardMoney() {
		super();
	}

	public AwardMoney(String ret, String msg, MoneyBooty result) {
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

	public MoneyBooty getResult() {
		return result;
	}

	public void setResult(MoneyBooty result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "AwardMoney [ret=" + ret + ", msg=" + msg + ", result=" + result
				+ "]";
	}
	
	
}
