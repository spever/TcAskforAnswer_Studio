package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class AuditPiclistBean implements Serializable{

	/**
	 * {"auditId":29,"desc":"","id":30,"picName":"imgapp.jpg","picUrl":"http://172.16.12.101/opt/web/static/qa_imgimgapp.jpg","type":1}]},"ret":"10000","msg":"成功"}
	 */
	private static final long serialVersionUID = 1L;
	private int auditId;
	private String desc;
	private int id;
	private String picName;
	private String picUrl;
	private int type;
	public int getAuditId() {
		return auditId;
	}
	public void setAuditId(int auditId) {
		this.auditId = auditId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
