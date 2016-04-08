package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class CrmUser implements Serializable{

	private int id;
	private String name;
	private int sex;
	private long phone;
	private int state;
	private long addtime;
	private long uptime;
	private int accountType;
	private int source;
	private int memberScore;
	private String identify;
	private String soldIdentify;
	private int hasPwd;
	public CrmUser() {
		super();
	}
	public CrmUser(int id, String name, int sex, long phone, int state,
			long addtime, long uptime, int accountType, int source,
			int memberScore, String identify, String soldIdentify, int hasPwd) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.phone = phone;
		this.state = state;
		this.addtime = addtime;
		this.uptime = uptime;
		this.accountType = accountType;
		this.source = source;
		this.memberScore = memberScore;
		this.identify = identify;
		this.soldIdentify = soldIdentify;
		this.hasPwd = hasPwd;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public long getAddtime() {
		return addtime;
	}
	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}
	public long getUptime() {
		return uptime;
	}
	public void setUptime(long uptime) {
		this.uptime = uptime;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getMemberScore() {
		return memberScore;
	}
	public void setMemberScore(int memberScore) {
		this.memberScore = memberScore;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getSoldIdentify() {
		return soldIdentify;
	}
	public void setSoldIdentify(String soldIdentify) {
		this.soldIdentify = soldIdentify;
	}
	public int getHasPwd() {
		return hasPwd;
	}
	public void setHasPwd(int hasPwd) {
		this.hasPwd = hasPwd;
	}
	@Override
	public String toString() {
		return "CrmUser [id=" + id + ", name=" + name + ", sex=" + sex
				+ ", phone=" + phone + ", state=" + state + ", addtime="
				+ addtime + ", uptime=" + uptime + ", accountType="
				+ accountType + ", source=" + source + ", memberScore="
				+ memberScore + ", identify=" + identify + ", soldIdentify="
				+ soldIdentify + ", hasPwd=" + hasPwd + "]";
	}
	
}
