package com.tuanche.askforanswer.app.core;
/*
 * Copyright (C) 2010 mAPPn.Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.tuanche.api.http.RequestParams;
import com.tuanche.api.utils.DesUtils;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.utils.ParamsUtils;
import com.tuanche.askforanswer.app.utils.Validator;

/**
 * 这个类是获取API请求内容的工厂方法
 */
public class ApiRequestFactory {
	/**
	 * 获取API HTTP 请求内容
	 * 
	 * @param action
	 *            请求的API Code
	 * @param params
	 *            请求参数
	 * @throws UnsupportedEncodingException
	 *             假如不支持UTF8编码方式会抛出此异常
	 */
	public static Object getRequestEntity(Action action, Object params,
			Session appSession,RequestParams header) throws UnsupportedEncodingException{
		String nameSpace=action.name();
		if(nameSpace.contains("XML")){
			 /**暂不实现*/
			 return null;
			
		}else if(nameSpace.contains("JSON")){
			return getJsonRequest( params,appSession);
		}else if(nameSpace.contains("FORM")){
			return getFormRequest(action, params, appSession);
		}else if(nameSpace.contains("NOSIGN")){
			return getFormRequestWithoutSign(action, params, appSession);
		}else {
			// 不需要请求内容
			return null;
		}
	}

	private static StringEntity getFormRequest(Action action, Object params,
			Session appSession) throws UnsupportedEncodingException {
		if (params == null) {
			return null;
		}
		HashMap<String, Object> requestParams;
		if (params instanceof HashMap) {
			requestParams = (HashMap<String, Object>) params;
		} else {
			return null;
		}
		final Iterator<String> keySet = requestParams.keySet().iterator();
		ArrayList<NameValuePair> pm = new ArrayList<NameValuePair>();
		try {
			while (keySet.hasNext()) {
				final String key = keySet.next();
				pm.add(new BasicNameValuePair(key, (String) requestParams
						.get(key)));
			}

			/** 应用传递的参数是否需要签名，签名 开始 */
			String sign = "";
			String token = "";
			String username = "";
			String pwd = "";
//			String username = appSession.getLogin_name();
//			String pwd = appSession.getUser_password();
			long timestamp = System.currentTimeMillis() / 1000;
			try {
				/**token值为：md5(md5(username|appKey|pwd))*/ 
				//token = DigestUtils.md5Hex(DigestUtils.md5Hex(username+"|"+AppApi.API_GROUPLEADER_KEY+"|"+pwd));
				/** sign：由公私钥和params和time值做的md5值 */
				String paramJson = ParamsUtils.getJsonParamsString(requestParams);
				sign = Validator.getSignStr(requestParams, paramJson, timestamp+"");
			} catch (Exception e) {
				LogUtils.d("AppService->post():", e);
			}
			if (!requestParams.containsKey("sign")) {
				pm.add(new BasicNameValuePair("sign", sign));
			}
			if (!requestParams.containsKey("time")) {
				pm.add(new BasicNameValuePair("time", timestamp + ""));
			}
			if (!requestParams.containsKey("token")) {
				pm.add(new BasicNameValuePair("token", token));
			}
			/** lashou，签名 结束 */

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return new UrlEncodedFormEntity(pm, HTTP.UTF_8);
	}
	
	public static StringEntity getFormRequestWithoutSign(Action action, Object params,
			Session appSession) throws UnsupportedEncodingException {
		if (params == null) {
			return null;
		}
		HashMap<String, Object> requestParams;
		if (params instanceof HashMap) {
			requestParams = (HashMap<String, Object>) params;
		} else {
			return null;
		}
		final Iterator<String> keySet = requestParams.keySet().iterator();
		ArrayList<NameValuePair> pm = new ArrayList<NameValuePair>();
		try {
			while (keySet.hasNext()) {
				final String key = keySet.next();
				pm.add(new BasicNameValuePair(key, (String) requestParams
						.get(key)));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return new UrlEncodedFormEntity(pm, HTTP.UTF_8);
	}
	private static StringEntity getJsonRequest(Object params,Session appSession) throws UnsupportedEncodingException {
        if (params == null) {
            return new StringEntity("");
        }

        HashMap<String, Object> requestParams;
        if (params instanceof HashMap) {
            requestParams = (HashMap<String, Object>) params;
        } else {
        	return new StringEntity("");
        }

        // add parameter node
        final Iterator<String> keySet = requestParams.keySet().iterator();
        JSONObject jsonParams = new JSONObject();
        JSONObject requestJsonParams=new JSONObject();
        try {
            while (keySet.hasNext()) {
                final String key = keySet.next();
                Object val=requestParams.get(key);
                if(val==null){
                	val="";
                }
                jsonParams.put(key, val);
            }
            /** lashou，签名 开始 */
			String sign = "";
			String token = "";
			String username = "";
			String pwd = "";
//			String username = appSession.getLogin_name();
//			String pwd = appSession.getUser_password();
			long timestamp = System.currentTimeMillis() / 1000;
			try {
				/**token值为：md5(md5(username|appKey|pwd))*/ 
				//token = DigestUtils.md5Hex(DigestUtils.md5Hex(username+"|"+AppApi.API_GROUPLEADER_KEY+"|"+pwd));
//				token = appSession.getToken();
				/** sign：由公私钥和params和time值做的md5值 */
				String paramJson = ParamsUtils.getJsonParamsString(requestParams);
				if (requestParams.size()>0) {
					sign = Validator.getSignStr(requestParams, paramJson, timestamp+"");	
				}
			} catch (Exception e) {
				LogUtils.d("AppService->post():", e);
			}
			if(jsonParams.length()>0){
				requestJsonParams.accumulate("params", jsonParams);
				requestJsonParams.accumulate("sign", sign);
			}
			requestJsonParams.accumulate("time", timestamp+"");
			if (!TextUtils.isEmpty(token)) {
				requestJsonParams.accumulate("token", token);
			}
			
			LogUtils.d("请求数据包参数:"+requestJsonParams.toString());
			/** lashou，签名 结束 */
			return new StringEntity(DesUtils.encrypt(requestJsonParams.toString()), HTTP.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            try {
				return new StringEntity("");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
		return null; 
    }
 
	private static StringEntity getMyJsonRequest(Object params,Session appSession) throws UnsupportedEncodingException {
        if (params == null) {
            return new StringEntity("");
        }

        HashMap<String, Object> requestParams;
        if (params instanceof HashMap) {
            requestParams = (HashMap<String, Object>) params;
        } else {
        	return new StringEntity("");
        }

        // add parameter node
        final Iterator<String> keySet = requestParams.keySet().iterator();
        JSONObject jsonParams = new JSONObject();
        JSONObject requestJsonParams=new JSONObject();
        try {
            while (keySet.hasNext()) {
                final String key = keySet.next();
                Object val=requestParams.get(key);
                if(val==null){
                	val="";
                }
                jsonParams.put(key, val);
            }
            /** lashou，签名 开始 */
//			String sign = "";
//			String token = "";
	//		String username = "";
		//	String pwd = "";
//			String username = appSession.getLogin_name();
//			String pwd = appSession.getUser_password();
			long timestamp = System.currentTimeMillis() / 1000;
			try {
				/**token值为：md5(md5(username|appKey|pwd))*/ 
//				token = DigestUtils.md5Hex(DigestUtils.md5Hex(username+"|"+AppApi.API_GROUPLEADER_KEY+"|"+pwd));
//				token = appSession.getToken();
				/** sign：由公私钥和params和time值做的md5值 */
				String paramJson = ParamsUtils.getJsonParamsString(requestParams);
//				if (requestParams.size()>0) {
//					sign = Validator.getSignStr(requestParams, paramJson, timestamp+"");	
//				}
			} catch (Exception e) {
				LogUtils.d("AppService->post():", e);
			}
			if(jsonParams.length()>0){
				requestJsonParams.accumulate("params", jsonParams);
//				requestJsonParams.accumulate("sign", sign);
			}
			requestJsonParams.accumulate("time", timestamp+"");
//			if (!TextUtils.isEmpty(token)) {
//				requestJsonParams.accumulate("token", token);
//			}
			
			LogUtils.d("请求数据包参数:"+requestJsonParams.toString());
			/** lashou，签名 结束 */
			return new StringEntity(requestJsonParams.toString(), HTTP.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            try {
				return new StringEntity("");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
		return null; 
    }
 
	
	public static String JsonEntity(HashMap<String, Object> requestParams) {
		 Iterator<String> keySet = requestParams.keySet().iterator();
	        JSONObject jsonParams = new JSONObject();
	        
	            try {
					while (keySet.hasNext()) {
					    final String key = keySet.next();
					    Object val=requestParams.get(key);
					    if(val==null){
					    	val="";
					    }
					    jsonParams.put(key, val);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
	            LogUtils.e(jsonParams.toString());
	            return jsonParams.toString();
	
	}
	
	
}
