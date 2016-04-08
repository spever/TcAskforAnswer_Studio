package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
import java.util.List;

public class AdutitListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int status;

	private List<AuditPiclistBean> piclist;
	private String mark;

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<AuditPiclistBean> getPiclist() {
		return piclist;
	}

	public void setPiclist(List<AuditPiclistBean> piclist) {
		this.piclist = piclist;
	}
}
