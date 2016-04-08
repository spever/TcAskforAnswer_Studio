package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class DetialResultBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String advert;
	private int Count;
	private int currentPage;
	private DetialResultDetialBean detail;
	private int pageNum;
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public String getAdvert() {
		return advert;
	}
	public void setAdvert(String advert) {
		this.advert = advert;
	}
	public DetialResultDetialBean getDetail() {
		return detail;
	}
	public void setDetail(DetialResultDetialBean detail) {
		this.detail = detail;
	}
}
