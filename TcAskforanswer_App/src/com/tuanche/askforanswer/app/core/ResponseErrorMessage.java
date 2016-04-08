package com.tuanche.askforanswer.app.core;

import java.io.Serializable;
 

/**
 * @author 朵朵花开
 *
 */
public class ResponseErrorMessage implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	private String msg;
	private String  ret; 
	private String json;
	private String result;
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}

	
	
}
