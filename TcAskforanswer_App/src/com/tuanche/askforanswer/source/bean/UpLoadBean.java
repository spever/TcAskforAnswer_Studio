package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;

public class UpLoadBean extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UploadResultBean result;
	
	public class UploadResultBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int picCount;

		public int getPicCount() {
			return picCount;
		}

		public void setPicCount(int picCount) {
			this.picCount = picCount;
		}
	}

	public UploadResultBean getResult() {
		return result;
	}

	public void setResult(UploadResultBean result) {
		this.result = result;
	}
}
