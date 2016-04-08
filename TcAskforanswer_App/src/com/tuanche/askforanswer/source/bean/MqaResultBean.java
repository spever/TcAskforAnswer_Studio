package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
import java.util.List;

public class MqaResultBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String total_count;
	private int accept_count;
	private int total_page;
	private int current_page;
	private List<QuestList> q_list;
	public List<QuestList> getQ_list() {
		return q_list;
	}
	public void setQ_list(List<QuestList> q_list) {
		this.q_list = q_list;
	}
	public String getTotal_count() {
		return total_count;
	}
	public void setTotal_count(String total_count) {
		this.total_count = total_count;
	}
	
	public int getAccept_count() {
		return accept_count;
	}
	public void setAccept_count(int accept_count) {
		this.accept_count = accept_count;
	}

	public int getCurrent_page() {
		return current_page;
	}
	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	public int getTotal_page() {
		return total_page;
	}
	public void setTotal_page(int total_page) {
		this.total_page = total_page;
	}

	public class QuestList implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String acceptStatus;
		private String askDate;
		private String brandId;
		private String content;
		private String icon;
		private String nickName;
		private int id;
		private int offStatus;
		private String phoneCode;
		private String tag;
		public String getAcceptStatus() {
			return acceptStatus;
		}
		public void setAcceptStatus(String acceptStatus) {
			this.acceptStatus = acceptStatus;
		}
		public String getAskDate() {
			return askDate;
		}
		public void setAskDate(String askDate) {
			this.askDate = askDate;
		}
		public String getBrandId() {
			return brandId;
		}
		public void setBrandId(String brandId) {
			this.brandId = brandId;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		public String getPhoneCode() {
			return phoneCode;
		}
		public void setPhoneCode(String phoneCode) {
			this.phoneCode = phoneCode;
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
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
		public int getOffStatus() {
			return offStatus;
		}
		public void setOffStatus(int offStatus) {
			this.offStatus = offStatus;
		}
		private int replyNum;
		private int status;
	} 
	
}
