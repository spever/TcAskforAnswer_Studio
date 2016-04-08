package com.tuanche.askforanswer.app.core;

import com.tuanche.askforanswer.app.core.AppApi.Action;



public interface ApiRequestListener {
	/**
     * The CALLBACK for success aMarket API HTTP response
     * 
     * @param response
     *            the HTTP response
     */
    void onSuccess(Action method, Object obj);

    /**
     * The CALLBACK for failure aMarket API HTTP response
     * 
     * @param statusCode
     *            the HTTP response status code
     */
    void onError(Action method, Object statusCode);

}
