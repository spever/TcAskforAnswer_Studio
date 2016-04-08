package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class VoiceResult implements Serializable{

	private String ret;
	
	private VoiceBean result;
	
	private String msg;

	public VoiceResult(String ret, VoiceBean result, String msg) {
		super();
		this.ret = ret;
		this.result = result;
		this.msg = msg;
	}

	public VoiceResult() {
		super();
	}

	@Override
	public String toString() {
		return "VoiceResult [ret=" + ret + ", result=" + result + ", msg="
				+ msg + "]";
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public VoiceBean getResult() {
		return result;
	}

	public void setResult(VoiceBean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
