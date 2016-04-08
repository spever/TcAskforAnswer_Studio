package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class MonentyGet implements Serializable{

	private float total;
	private float balance;
	private float casheTotal;
	private float answerTotal;
	private int bank_tag;
	private float sysTotal;
	public MonentyGet() {
		super();
	}
	public MonentyGet(float total, float balance, float casheTotal,
			float answerTotal, int bank_tag, float sysTotal) {
		super();
		this.total = total;
		this.balance = balance;
		this.casheTotal = casheTotal;
		this.answerTotal = answerTotal;
		this.bank_tag = bank_tag;
		this.sysTotal = sysTotal;
	}

	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public float getCasheTotal() {
		return casheTotal;
	}
	public void setCasheTotal(float casheTotal) {
		this.casheTotal = casheTotal;
	}
	public float getAnswerTotal() {
		return answerTotal;
	}
	public void setAnswerTotal(float answerTotal) {
		this.answerTotal = answerTotal;
	}
	public int getBank_tag() {
		return bank_tag;
	}
	public void setBank_tag(int bank_tag) {
		this.bank_tag = bank_tag;
	}
	public float getSysTotal() {
		return sysTotal;
	}
	public void setSysTotal(float sysTotal) {
		this.sysTotal = sysTotal;
	}
	@Override
	public String toString() {
		return "MonentyGet [total=" + total + ", balance=" + balance
				+ ", casheTotal=" + casheTotal + ", answerTotal=" + answerTotal
				+ ", bank_tag=" + bank_tag + ", sysTotal=" + sysTotal + "]";
	}
}
