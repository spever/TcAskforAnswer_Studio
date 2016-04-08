package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
import java.util.List;

public class MsgInfoBean implements Serializable {

	/**
	 * "count": 3, "pageNum": 2, "currentPage": 1
	 */
	private static final long serialVersionUID = 1L;
	private boolean msgIsOrNo;
	private List<MsgInfoListBean> list;
	private int count;
	private int pageNum;
	private int currentPage;

	public List<MsgInfoListBean> getList() {
		return list;
	}

	public void setList(List<MsgInfoListBean> list) {
		this.list = list;
	}

	public boolean isMsgIsOrNo() {
		return msgIsOrNo;
	}

	public void setMsgIsOrNo(boolean msgIsOrNo) {
		this.msgIsOrNo = msgIsOrNo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

}
