package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class UserBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CrmUser crmUser;
	
	private UserInfo userIplementInfo;
	
	private int isNewUser;

	private String cardInfo;
	
	private String cardInfoCount;
	
	public UserBean() {
		super();
	}

	public UserBean(CrmUser crmUser, UserInfo userIplementInfo, int isNewUser,
			String cardInfo, String cardInfoCount) {
		super();
		this.crmUser = crmUser;
		this.userIplementInfo = userIplementInfo;
		this.isNewUser = isNewUser;
		this.cardInfo = cardInfo;
		this.cardInfoCount = cardInfoCount;
	}


	public CrmUser getCrmUser() {
		return crmUser;
	}

	public void setCrmUser(CrmUser crmUser) {
		this.crmUser = crmUser;
	}

	public UserInfo getUserIplementInfo() {
		return userIplementInfo;
	}

	public void setUserIplementInfo(UserInfo userIplementInfo) {
		this.userIplementInfo = userIplementInfo;
	}

	public int getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(int isNewUser) {
		this.isNewUser = isNewUser;
	}

	public String getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}

	public String getCardInfoCount() {
		return cardInfoCount;
	}

	public void setCardInfoCount(String cardInfoCount) {
		this.cardInfoCount = cardInfoCount;
	}

	@Override
	public String toString() {
		return "UserBean [crmUser=" + crmUser + ", userIplementInfo="
				+ userIplementInfo + ", isNewUser=" + isNewUser + ", cardInfo="
				+ cardInfo + ", cardInfoCount=" + cardInfoCount + "]";
	}


}
