package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class BankTypeEach implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String bank_code;
	
	private String bank_name;

	public BankTypeEach() {
		super();
	}

	public BankTypeEach(String bank_code, String bank_name) {
		super();
		this.bank_code = bank_code;
		this.bank_name = bank_name;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	@Override
	public String toString() {
		return "BankTypeList [bank_code=" + bank_code + ", bank_name="
				+ bank_name + "]";
	}
	
}
