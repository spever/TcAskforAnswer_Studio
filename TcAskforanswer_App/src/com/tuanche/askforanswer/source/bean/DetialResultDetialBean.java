package com.tuanche.askforanswer.source.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class DetialResultDetialBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, String> question;
	private List<HashMap<String, String>> answer;
	public HashMap<String, String> getQuestion() {
		return question;
	}
	public void setQuestion(HashMap<String, String> question) {
		this.question = question;
	}
	public List<HashMap<String, String>> getAnswer() {
		return answer;
	}
	public void setAnswer(List<HashMap<String, String>> answer) {
		this.answer = answer;
	}
}
