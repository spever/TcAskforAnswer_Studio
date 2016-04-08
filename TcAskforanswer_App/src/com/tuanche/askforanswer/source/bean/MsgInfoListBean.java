package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
import java.util.HashMap;

public class MsgInfoListBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int askId;
	private String content;
	private String dateStr;
	private int icon;
	private int id;
	private int replyNum;
	private int status;
	private String title; 
	private String type;
	private int userId;
	private String url;
	private HashMap<String, Long> greateDate;
	public MsgInfoListBean() {
		super();
	}
	public int getAskId() {
		return askId;
	}
	public void setAskId(int askId) {
		this.askId = askId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public HashMap<String, Long> getGreateDate() {
		return greateDate;
	}
	public void setGreateDate(HashMap<String, Long> greateDate) {
		this.greateDate = greateDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
