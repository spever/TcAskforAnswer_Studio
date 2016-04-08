package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class HomeListInfoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int msgCount; //消息数
	private int questionCount; //问题数
	private String advert;
	private int pageNum;
	private int currentPage;
	private CueContent cueWords;

	public CueContent getCueWords() {
		return cueWords;
	}

	public void setContent(CueContent cueWords) {
		this.cueWords = cueWords;
	}

	private LinkedHashMap<String, String> title_tag=new LinkedHashMap<String, String>();
	private List<HomeInfoQuestionBean> list=new ArrayList<HomeInfoQuestionBean>();
	public int getMsgCount() {
		return msgCount;
	}
	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}
	public int getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}
	public String getAdvert() {
		return advert;
	}
	public void setAdvert(String advert) {
		this.advert = advert;
	}
	public LinkedHashMap<String, String> getTitle_tag() {
		return title_tag;
	}
	public void setTitle_tag(LinkedHashMap<String, String> title_tag) {
		this.title_tag = title_tag;
	}
	
	public List<HomeInfoQuestionBean> getList() {
		return list;
	}
	public void setList(List<HomeInfoQuestionBean> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "HomeListInfoBean [msgCount=" + msgCount + ", questionCount=" + questionCount + ", advert=" + advert + ", title_tag=" + title_tag
				+ ", list=" + list + "]";
	}
	
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public class HomeInfoQuestionBean implements Serializable{

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
		private int replyNum;
		private int status;
		
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
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getStyleId() {
			return styleId;
		}
		public void setStyleId(String styleId) {
			this.styleId = styleId;
		}
		public int getOffStatus() {
			return offStatus;
		}
		public void setOffStatus(int offStatus) {
			this.offStatus = offStatus;
		}
		private String styleId;
		private String tag;
		private int userId;
	}
	public class CueContent{
		private String context;
		private String title;
		private String url;
		private String version;

		public String getContext() {
			return context;
		}

		public void setContext(String context) {
			this.context = context;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getUrl() {
			return url;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}
}
