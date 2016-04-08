package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
/**
 * 银行卡信息
 * @author zpf
 *
 */
public class BankResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String provice;
	private int status;
	private String bankType;
	private String bankName;
	private String tenpayBankType;
	private String bankAccount;
	private String city;
	public BankResult() {
		super();
	}
	public BankResult(String province, int status, String bankType,
			String bankName, String tenpayBankType, String bankAccount,
			String city) {
		super();
		this.provice = province;
		this.status = status;
		this.bankType = bankType;
		this.bankName = bankName;
		this.tenpayBankType = tenpayBankType;
		this.bankAccount = bankAccount;
		this.city = city;
	}
	public String getProvince() {
		return provice;
	}
	public void setProvince(String province) {
		this.provice = province;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getTenpayBankType() {
		return tenpayBankType;
	}
	public void setTenpayBankType(String tenpayBankType) {
		this.tenpayBankType = tenpayBankType;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "BankResult [province=" + provice + ", status=" + status
				+ ", bankType=" + bankType + ", bankName=" + bankName
				+ ", tenpayBankType=" + tenpayBankType + ", bankAccount="
				+ bankAccount + ", city=" + city + "]";
	}
	
	
}
