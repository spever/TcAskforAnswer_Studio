package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class MineBean implements Serializable{

	private int myQuestionCount;
	private String nick;
	private String calStandard;
	private String start_time;
	private String end_time;
	private String name;
	private float money;
	private int  acceptCount;
	private String head;
	private String cardInfo;
	private int switch_status;
	private String rewardText;
	private String rewardUrl;
	public MineBean() {
		super();
	}
	

	public MineBean(int myQuestionCount, String nick, String calStandard,
			String start_time, String end_time, String name, float money,
			int acceptCount, String head, String cardInfo, int switch_status,String rewardText,String rewardUrl) {
		super();
		this.myQuestionCount = myQuestionCount;
		this.nick = nick;
		this.calStandard = calStandard;
		this.start_time = start_time;
		this.end_time = end_time;
		this.name = name;
		this.money = money;
		this.acceptCount = acceptCount;
		this.head = head;
		this.cardInfo = cardInfo;
		this.switch_status = switch_status;
		this.rewardText = rewardText;
		this.rewardUrl = rewardUrl;
	}


	public String getRewardText() {
		return rewardText;
	}

	public void setRewardText(String rewardText) {
		this.rewardText = rewardText;
	}

	public String getRewardUrl() {
		return rewardUrl;
	}

	public void setRewardUrl(String rewardUrl) {
		this.rewardUrl = rewardUrl;
	}

	public int getMyQuestionCount() {
		return myQuestionCount;
	}
	public void setMyQuestionCount(int myQuestionCount) {
		this.myQuestionCount = myQuestionCount;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAcceptCount() {
		return acceptCount;
	}
	
	public float getMoney() {
		return money;
	}


	public void setMoney(float money) {
		this.money = money;
	}


	public void setAcceptCount(int acceptCount) {
		this.acceptCount = acceptCount;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getCardInfo() {
		return cardInfo;
	}
	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}
	
	public int getSwitch_status() {
		return switch_status;
	}

	public void setSwitch_status(int switch_status) {
		this.switch_status = switch_status;
	}

	
	public String getCalStandard() {
		return calStandard;
	}


	public void setCalStandard(String calStandard) {
		this.calStandard = calStandard;
	}


	@Override
	public String toString() {
		return "MineBean{" +
				"myQuestionCount=" + myQuestionCount +
				", nick='" + nick + '\'' +
				", calStandard='" + calStandard + '\'' +
				", start_time='" + start_time + '\'' +
				", end_time='" + end_time + '\'' +
				", name='" + name + '\'' +
				", money=" + money +
				", acceptCount=" + acceptCount +
				", head='" + head + '\'' +
				", cardInfo='" + cardInfo + '\'' +
				", switch_status=" + switch_status +
				", rewardText='" + rewardText + '\'' +
				", rewardUrl='" + rewardUrl + '\'' +
				'}';
	}

}
