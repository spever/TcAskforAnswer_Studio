package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
import java.util.List;

public class AllTags implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<EachTag> tags;

	public AllTags() {
		super();
	}

	public AllTags(List<EachTag> tags) {
		super();
		this.tags = tags;
	}

	public List<EachTag> getTags() {
		return tags;
	}

	public void setTags(List<EachTag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "AllTags [tags=" + tags + "]";
	}
	
	
}
