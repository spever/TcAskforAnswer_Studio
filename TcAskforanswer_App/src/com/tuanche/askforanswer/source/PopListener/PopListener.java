package com.tuanche.askforanswer.source.PopListener;

public interface PopListener {
	void takeCamera();
	void choosePicture();
	void submitAnswer(String content); 
	void popDimiss();
}
