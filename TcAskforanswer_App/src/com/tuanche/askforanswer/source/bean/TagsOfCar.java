package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
/**
 * 技师擅长的方向
 * @author zpf
 *
 */
public class TagsOfCar implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	private String ret;
	
	private String msg;
	
    private AllTags result;

	public TagsOfCar() {
		super();
	}

	public TagsOfCar(String ret, String msg, AllTags result) {
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

	public AllTags getResult() {
		return result;
	}

	public void setResult(AllTags result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "TagsOfCar [ret=" + ret + ", msg=" + msg + ", result=" + result
				+ "]";
	}
    
    
}	