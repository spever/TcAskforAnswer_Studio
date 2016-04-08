package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class BeanIsAudited implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private boolean isAudited;
	public boolean isAudited() {
		return isAudited;
	}
	public void setAudited(boolean isAudited) {
		this.isAudited = isAudited;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
