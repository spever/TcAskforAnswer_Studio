package com.tuanche.askforanswer.app.core;

public interface UploadRequestListener extends ApiRequestListener{
	void onLoading(long total, long current,boolean isUploading);
	void updateProgress(long total, long current,boolean forceUpdateUI);
}
