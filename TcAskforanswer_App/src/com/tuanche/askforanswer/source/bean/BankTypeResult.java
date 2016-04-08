package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
import java.util.List;

public class BankTypeResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<BankTypeEach> brankType;

	public BankTypeResult() {
		super();
	}

	public BankTypeResult(List<BankTypeEach> brankType) {
		super();
		this.brankType = brankType;
	}

	public List<BankTypeEach> getBrankType() {
		return brankType;
	}

	public void setBrankType(List<BankTypeEach> brankType) {
		this.brankType = brankType;
	}

	@Override
	public String toString() {
		return "BankTypeResult [brankType=" + brankType + "]";
	}
	
	
	
}
