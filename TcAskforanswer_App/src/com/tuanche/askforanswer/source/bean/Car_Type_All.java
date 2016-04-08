package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
/**
 * 技师   注册  选择  品牌
 * @author zpf
 *
 */
public class Car_Type_All implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ret;
	
	private String msg;
	
	private Car_Tapes result;

	public Car_Type_All() {
		super();
	}

	public Car_Type_All(String ret, String msg, Car_Tapes result) {
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

	public Car_Tapes getResult() {
		return result;
	}

	public void setResult(Car_Tapes result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Car_Type_All [ret=" + ret + ", msg=" + msg + ", result="
				+ result + "]";
	}
	
	
}
