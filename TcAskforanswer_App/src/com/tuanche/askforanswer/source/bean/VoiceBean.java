package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class VoiceBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int isShow;

	public VoiceBean(int isShow) {
		super();
		this.isShow = isShow;
	}

	public VoiceBean() {
		super();
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	@Override
	public String toString() {
		return "VoiceBean [isShow=" + isShow + "]";
	}
	
	
}
