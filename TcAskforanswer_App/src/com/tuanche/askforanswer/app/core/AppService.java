package com.tuanche.askforanswer.app.core;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.tuanche.api.core.CompatibleAsyncTask;
import com.tuanche.api.exception.HttpException;
import com.tuanche.api.http.HttpHandler;
import com.tuanche.api.http.RequestParams;
import com.tuanche.api.http.ResponseInfo;
import com.tuanche.api.http.callback.FileDownProgress;
import com.tuanche.api.http.callback.RequestCallBack;
import com.tuanche.api.http.client.HttpRequest.HttpMethod;
import com.tuanche.api.utils.AppUtils;
import com.tuanche.api.utils.AppUtils.StorageFile;
import com.tuanche.api.utils.HttpUtils;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.vo.CacheData;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.utils.ParamsUtils;
import com.tuanche.askforanswer.app.utils.Utils;
import com.tuanche.askforanswer.app.utils.Validator;


public class AppService {
	private Context mContext;
	private Action action;
	private ApiRequestListener handler;
	private Object mParameter;
	private HttpUtils httpUtils;
	private HttpHandler<String> httpHandler;
	private boolean isNeedUpdateUI = true;
	/** 应用Session */
	protected Session appSession;
	private HttpCacheManager httpCacheManager;

	// 支付方式id,默认值为余额支付
	private int mPayId = -1;

	private boolean isHome;

	public AppService(Context context, Action action) {
		this.mContext = context;
		this.action = action;
		appSession = Session.get(context);
		httpUtils = new HttpUtils(15*1000*4);
	}

	public AppService(Context context, Action action, ApiRequestListener handler, Object params, boolean isHome) {
		this(context, action, handler, params);
		this.isHome = isHome;
	}

	public AppService(Context context, Action action, ApiRequestListener handler, Object params) {
		this.mContext = context;
		this.action = action;
		this.handler = handler;
		this.mParameter = params;
		httpUtils = new HttpUtils();
		appSession = Session.get(context);
		httpCacheManager = HttpCacheManager.getInstance(context);
	}

	public AppService(Context context, Action action, ApiRequestListener handler, Object params, int payId) {
		this.mContext = context;
		this.action = action;
		this.handler = handler;
		this.mParameter = params;
		httpUtils = new HttpUtils();
		appSession = Session.get(context);
		this.mPayId = payId;
		httpCacheManager = HttpCacheManager.getInstance(context);
	}

	public void cancel() {
		if (httpHandler != null) {
			httpHandler.cancel(true);
			// httpHandler.stop();
		}
	}

	public synchronized boolean isNeedUpdateUI() {
		return isNeedUpdateUI;
	}

	public synchronized void setNeedUpdateUI(boolean isNeedUpdateUI) {
		this.isNeedUpdateUI = isNeedUpdateUI;
	}

	public void post(boolean isNeedUserAgent) {
		post(false, false, false, isNeedUserAgent);
	}

	/**
	 * @param isCache
	 *            是否需要缓存
	 * @param isGzip
	 *            是否需要服务端返回的数据Gzip
	 * @param isDes
	 *            是否需要返回的数据进行Des加密
	 * @param isNeedUserAgent
	 *            是否需要设置User-Agent请求头
	 */
	public void post(final boolean isCache, boolean isGzip, boolean isDes, boolean isNeedUserAgent) {
		final String requestUrl = Utils.AddUrlExtra(mContext,AppApi.API_URLS.get(action),false);
		LogUtils.e(requestUrl);
		try {
			final StringEntity obj;
			/** 序列化请求包体json */
			final RequestParams params = new RequestParams();
			obj = (StringEntity) ApiRequestFactory.getRequestEntity(action, mParameter, appSession, params);
			// if(isHome){
			// params.addHeader("traceinfo", appSession.getDeviceInfo());
			// params.addHeader("event","home");
			// }else{
			// params.addHeader("traceinfo", appSession.getDeviceInfo());
			// }
			//params.addHeader("traceinfo", appSession.getDeviceInfo());
			 LogUtils.d("traceinfo-->"+appSession.getDeviceInfo());
			if (isGzip) {
				params.addHeader("Accept-Encoding", "gzip");
			}
			if (isNeedUserAgent) {
				params.addHeader("User-Agent", "tcphone");// 添加请求头
			}
			params.setBodyEntity(obj);

			String key = null;
			if (!AppUtils.isNetworkAvailable(mContext)) {

				/** NewWork is error... */
				if (isCache) {
					// readCache(params, isCache, requestUrl);
					if (isNeedUpdateUI()) {
						new loadCacheTask().execute(params, isCache, requestUrl);
					}
				} else {
					// handler.onError(action, AppApi.ERROR_TIMEOUT);
					 handler.onError(Action.NETWORK_FAILED,
							 AppApi.ERROR_NETWORK_FAILED);
				}
				return;
			} else {
				/** 网络正常情况，判断是否是cache的请求，如果是，携带head相关参数 */
				if (isCache) {
					key = fromToCacheReadKey(params, isCache, requestUrl);
					/** 查找本地key是否存在，存在就header写入 */
					boolean isHave = addHeaderForCache(params, key);
					/** 直接从缓存中读取数据并刷新界面 */
					if (isHave) {
						// readCache(params,isCache,requestUrl);
						if (isNeedUpdateUI()) {
							new loadCacheTask().execute(params, isCache, requestUrl);
						}
					}

				}
			}

			final String k = key;
			if (isDes) {
				params.addHeader("des", "true");
			}
			httpHandler = httpUtils.send(HttpMethod.POST, requestUrl, params, new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					Object response;

					try {

						response = ApiResponseFactory.getResponse(mContext, action, responseInfo, k, isCache, mPayId);
						if (response == null) {
							// handler.onError(action, AppApi.ERROR_BUSSINESS);
							return;
						} else if (response instanceof Integer) {
							Integer res = (Integer) response;
							// if(res!=null
							// &&AppApi.HTTP_RESPONSE_STATE_CACHE==res){
							// LogUtils.d("from HttpServer cache data...");
							// //handler.onError(action, AppApi.RESPONSE_CACHE);
							// return;
							// }
							// TODO 根据团车网接口添加的
							handler.onSuccess(action, res);
						} else if (response instanceof ResponseErrorMessage) {
							handler.onError(action, response);
							return;
						}
						setNeedUpdateUI(false);
						handler.onSuccess(action, response);

						// cacheManager.putResponse(mContext, keys, response);
					} catch (Exception ex) {
						LogUtils.d(ex.toString());
						ex.printStackTrace();
					}
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					if (isCache) {
						// readCache(params, isCache, requestUrl);
						if (isNeedUpdateUI()) {
							new loadCacheTask().execute(params, isCache, requestUrl);
						}
					}
					LogUtils.e(error.getMessage()+msg);
					handler.onError(action, AppApi.ERROR_TIMEOUT);
				}

			});
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.d(e.toString());
		}
	}

	public String fromToCacheReadKey(RequestParams params, boolean isCache, String requestUrl) {
		return AppUtils.getMD5(requestUrl + ParamsUtils.getJsonParamsString(mParameter));
	}

	public boolean addHeaderForCache(RequestParams params, String key) {
		/** 需要缓存的请求 */
		params.addHeader("cache", "true");
		Object requestObj = httpCacheManager.loadCacheData(key + "_key");
		if (requestObj != null) {
			String tmpKey = "";
			if (requestObj instanceof CacheData) {
				CacheData data = (CacheData) requestObj;
				tmpKey = data.getMd5();
			} else if (requestObj instanceof String) {
				tmpKey = (String) requestObj;
			}
			String[] tK = tmpKey.split(":");
			if (tK != null && tK.length == 2) {

				params.setHeader("key", tK[0]);
				params.setHeader("webtime", tK[1]);
			}
			return true;
		}
		return false;
	}

	public void doPost(String url) {
		httpUtils.send(HttpMethod.POST, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				handler.onSuccess(action, responseInfo);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				handler.onError(action, AppApi.ERROR_TIMEOUT);
			}

		});
	}
	
//	public void get(){
//		String requestUrl = AppApi.BASIC_URL+AppApi.API_URLS.get(action)+"?";
//		//拼接请求参数到请求地址后
//		//获取第一步的code后，请求以下链接获取access_token：
//		//https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
//		if(mParameter instanceof Map && ((Map) mParameter).size()>0){
//			@SuppressWarnings("unchecked")
//			Map<String, Object> map = (Map<String, Object>) mParameter;
//			Set<Entry<String, Object>> params = map.entrySet();
//			for(Entry<String, Object> param:params){
//				String key = param.getKey();
//				String val = (String) param.getValue();
//				requestUrl = requestUrl+key+"="+val+"&";
//			}
//			if(requestUrl.endsWith("&")){
//				requestUrl = requestUrl.substring(0,requestUrl.length()-1);
//				LogUtils.i("requestUrl:"+requestUrl);
//			}
//		}
//		httpUtils.send(HttpMethod.GET, requestUrl, new RequestCallBack<String>(){
//
//			@Override
//			public void onSuccess(ResponseInfo<String> responseInfo) {
//				// TODO Auto-generated method stub
//				JSONObject rSet = null;
//				String jsonResult = (String) responseInfo.result;
//				LogUtils.i("jsonResult:"+responseInfo.toString());
//				if (jsonResult == null){
//					return;
//				}
//				try {
//					rSet = new JSONObject(jsonResult);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				handler.onSuccess(action, rSet);
//			}
//
//			@Override
//			public void onFailure(HttpException error, String msg) {
//				// TODO Auto-generated method stub
//				handler.onError(action, AppApi.ERROR_TIMEOUT);
//				LogUtils.i("responseInfo:"+msg);
//			}
//			
//		});
//	}
	/**
	 * get请求
	 * @param isNeedBaseURL 是否需要baseURL
	 */
//	public void get(boolean isNeedBaseURL) {
//		try {
//			String requestUrl = "";
//			if (isNeedBaseURL) {
//			//	requestUrl = AppApi.BASIC_URL + AppApi.API_URLS.get(action);
//			} else {
//				requestUrl = AppApi.API_URLS.get(action);
//			}
//			if (mParameter instanceof Map && ((Map) mParameter).size() > 0) {
//				@SuppressWarnings("unchecked")
//				Map<String, Object> map = (Map<String, Object>) mParameter;
//				Set<Entry<String, Object>> params = map.entrySet();
//				for (Entry<String, Object> param : params) {
//					String key = param.getKey();
//					String val = (String) param.getValue();
//					requestUrl = requestUrl + key + "=" + val + "&";
//				}
//				if (requestUrl.endsWith("&")) {
//					requestUrl = requestUrl.substring(0, requestUrl.length() - 1);
//					LogUtils.i("requestUrl:" + requestUrl);
//				}
//			}
//			
//			httpUtils.send(HttpMethod.GET, requestUrl, new RequestCallBack<String>() {
//				@Override
//				public void onSuccess(ResponseInfo<String> responseInfo) {
//					Object response;
//					try {
//						response = ApiResponseFactory.getResponse(mContext, action, responseInfo, "", false, -1);
//						if (response == null) {
//							// handler.onError(action, AppApi.ERROR_BUSSINESS);
//							return;
//						} else if (response instanceof Integer) {
//							Integer res = (Integer) response;
//						} else if (response instanceof ResponseErrorMessage) {
//							handler.onError(action, response);
//							return;
//						}
//						setNeedUpdateUI(false);
//						handler.onSuccess(action, response);
//
//					} catch (Exception ex) {
//						LogUtils.d(ex.toString());
//						ex.printStackTrace();
//					}
//				}
//
//				@Override
//				public void onFailure(HttpException error, String msg) {
//					handler.onError(action, AppApi.ERROR_TIMEOUT);
//					LogUtils.i("responseInfo:" + msg);
//				}
//
//			});
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	/**
	 * get请求，一般是需要baseURL的
	 */

//	public void get() {
//		get(true);
//	}

	public void doGet() {
		String requestUrl = AppApi.API_URLS.get(action) + "?";
		httpUtils.send(HttpMethod.GET, requestUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				JSONObject rSet = null;
				String jsonResult = (String) responseInfo.result;
				//LogUtils.i("jsonResult:" + responseInfo.toString());
				if (jsonResult == null) {
					return;
				}
				try {
					rSet = new JSONObject(jsonResult);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				handler.onSuccess(action, rSet);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				handler.onError(action, AppApi.ERROR_TIMEOUT);
				LogUtils.i("responseInfo:" + msg);
			}

		});
	}

	public void downLoad(String url, String targetFile) {

		httpUtils.download(url, targetFile, new RequestCallBack<File>() {

			@Override
			public void onSuccess(ResponseInfo<File> responseInfo) {
				// TODO Auto-generated method stub
				if (responseInfo != null) {
					handler.onSuccess(action, responseInfo.result);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				//handler.onError(action, Action.ACTION_UPGRADEDOWN);
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				// TODO Auto-generated method stub

				super.onLoading(total, current, isUploading);
				FileDownProgress obj = new FileDownProgress();
				obj.setLoading(isUploading);
				obj.setNow(current);
				obj.setTotal(total);
				handler.onSuccess(action, obj);
			}
		});
	}

	private class loadCacheTask extends CompatibleAsyncTask<Object, Void, Object> {
		RequestParams params;
		boolean isCache;
		String requestUrl;

		@Override
		protected Object doInBackground(Object... p) {
			// TODO Auto-generated method stub
			params = (RequestParams) p[0];
			isCache = (Boolean) p[1];
			requestUrl = (String) p[2];
			Object obj = readCache(params, isCache, requestUrl);
			return obj;
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			if (result == null) {
				handler.onError(Action.NETWORK_FAILED, AppApi.ERROR_NETWORK_FAILED);
			} else {
				handler.onSuccess(action, result);
			}
		}

		public Object readCache(RequestParams params, boolean isCache, String requestUrl) {
			String key = fromToCacheReadKey(params, isCache, requestUrl);
			Object resObject = null;
			String cacheMessageS = (String) HttpCacheManager.getInstance(mContext).loadCacheData(key + "_value");
			if (cacheMessageS == null) {
				return resObject;
			} else {
				resObject = ApiResponseFactory.parseResponse(action, cacheMessageS, null, -1);
				return resObject;
			}

			/*
			 * if(cacheMessageS==null){ handler.onError(Action.NETWORK_FAILED,
			 * AppApi.ERROR_NETWORK_FAILED); }else { Object
			 * resObject=ApiResponseFactory.parseResponse(action,
			 * cacheMessageS,null,-1); if(resObject!=null){
			 * handler.onSuccess(action, resObject); } }
			 */
		}
	}

	public void uploadErrorFile() {
		String destUrl = AppApi.API_URLS.get(action);
		RequestParams params = new RequestParams();
		// params.addHeader("traceinfo", appSession.getDeviceInfo());
		String destPath = AppUtils.getPath(mContext, StorageFile.file);
		final File srcFile = new File(destPath + "crash.log");
		final File destFile = new File(destPath + "crash.zip");
		if (!srcFile.exists()) {
			return;
		} else {

			try {
				boolean is = AppUtils.zipFile(srcFile, destFile, "error file upload");
				if (!is)
					return;
			} catch (Exception e) {
				// TODO: handle exception
				LogUtils.e(e.toString());
			}
		}

		params.addBodyParameter("errorfile", destFile);
		httpUtils.send(HttpMethod.POST, destUrl, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				LogUtils.d("文件上传成功");
				try {
					if (destFile.exists()) {
						destFile.delete();
					}
					// if(srcFile.exists()){
					// srcFile.delete();
					// }
				} catch (Exception e) {
					// TODO: handle exception
					LogUtils.e(e.toString());
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				LogUtils.e("文件上传失败");
				try {
					if (destFile.exists()) {
						destFile.delete();
					}
					// if(srcFile.exists()){
					// srcFile.delete();
					// }
				} catch (Exception e) {
					// TODO: handle exception
					LogUtils.e(e.toString());
				}
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				super.onLoading(total, current, isUploading);
			}
		});
	}

	public void postInfo(HashMap<String, String> obj) {
		final String requestUrl = AppApi.API_URLS.get(action);
		final RequestParams params = new RequestParams();
		for (Map.Entry<String, String> entry : obj.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			params.addHeader(key, val);
		}

		httpUtils.send(HttpMethod.POST, requestUrl, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				if (responseInfo != null && responseInfo.result != null)
					LogUtils.d(responseInfo.result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				LogUtils.e(msg);
			}
		});
	}

	/**
	 * 
	 * 上传文件
	 * 
	 * @param paramKey
	 *            上传时的文件参数名
	 * @param filePath
	 *            文件本地路径
	 * @param isCache
	 * @param isGzip
	 * @param isDes
	 */
	public void uploadFile(String paramKey, String filePath, final boolean isCache, boolean isGzip, boolean isDes) {
		final File srcFile = new File(filePath);
		if (!srcFile.exists()) {
			return;
		}
		final String requestUrl = AppApi.API_URLS.get(action);
		try {
			/** 序列化请求包体json */
			final RequestParams rParams = new RequestParams();
			// rParams.addHeader("traceinfo", appSession.getDeviceInfo());
			if (isGzip) {
				rParams.addHeader("Accept-Encoding", "gzip");
			}
			HashMap<String, Object> map = (HashMap<String, Object>) mParameter;
			/** lashou，签名 开始 */
			String sign = "";
			String token = "";
			String paramJson = "";
			long timestamp = 0;
			// add parameter node
			final Iterator<String> keySet = map.keySet().iterator();
			JSONObject jsonParams = new JSONObject();
			JSONObject requestJsonParams = new JSONObject();
			try {
				while (keySet.hasNext()) {
					final String key = keySet.next();
					Object val = map.get(key);
					if (val == null) {
						val = "";
					}
					jsonParams.put(key, val);
				}
				timestamp = System.currentTimeMillis() / 1000;
				try {
					/** token值为：md5(md5(username|appKey|pwd)) */
					// token =
					// DigestUtils.md5Hex(DigestUtils.md5Hex(username+"|"+AppApi.API_LASHOU_KEY+"|"+pwd));
					token = appSession.getToken();
					/** sign：由公私钥和params和time值做的md5值 */
					paramJson = ParamsUtils.getJsonParamsString(map);
					if (map.size() > 0) {
						sign = Validator.getSignStr(map, paramJson, timestamp + "");
					}
				} catch (Exception e) {
					LogUtils.d("AppService->post():", e);
				}
				if (jsonParams.length() > 0) {
					requestJsonParams.accumulate("params", jsonParams);
					requestJsonParams.accumulate("sign", sign);
				}
				requestJsonParams.accumulate("time", timestamp + "");
				if (!TextUtils.isEmpty(token)) {
					requestJsonParams.accumulate("token", token);
				}

				LogUtils.d("请求数据包参数:" + requestJsonParams.toString());
				/** lashou，签名 结束 */
			} catch (Exception e) {
				e.printStackTrace();
			}
			rParams.addBodyParameter("sign", sign);
			rParams.addBodyParameter("token", token);
			rParams.addBodyParameter("params", paramJson);
			rParams.addBodyParameter("time", timestamp + "");
			// rParams.addBodyParameter("traceinfo",
			// appSession.getDeviceInfo());
			rParams.addBodyParameter(paramKey, srcFile);

			// Log.i("upload",
			// "上传图片：sign:"+sign+"\n"+" token:"+token+" \n params:"+paramJson+" \ntimestamp:"+timestamp+" \ntraceinfo:"+appSession.getDeviceInfo()+" \n"+filePath);
			String key = null;
			if (!AppUtils.isNetworkAvailable(mContext)) {

				/** NewWork is error... */
				if (isCache) {
					// readCache(params, isCache, requestUrl);
					if (isNeedUpdateUI()) {
						new loadCacheTask().execute(rParams, isCache, requestUrl);
					}
				} else {
					handler.onError(action, AppApi.ERROR_TIMEOUT);
					//handler.onError(Action.NETWORK_FAILED, AppApi.ERROR_NETWORK_FAILED);
				}
				return;
			} else {
				/** 网络正常情况，判断是否是cache的请求，如果是，携带head相关参数 */
				if (isCache) {
					key = fromToCacheReadKey(rParams, isCache, requestUrl);
					/** 查找本地key是否存在，存在就header写入 */
					boolean isHave = addHeaderForCache(rParams, key);
					/** 直接从缓存中读取数据并刷新界面 */
					if (isHave) {
						// readCache(params,isCache,requestUrl);
						if (isNeedUpdateUI()) {
							new loadCacheTask().execute(rParams, isCache, requestUrl);
						}
					}

				}
			}

			final String k = key;
			if (isDes) {
				rParams.addHeader("des", "true");
			}
			httpHandler = httpUtils.send(HttpMethod.POST, requestUrl, rParams, new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					LogUtils.d("评价图片上传成功");
					// try {
					// if(srcFile.exists()){
					// srcFile.delete();
					// }
					// } catch (Exception e) {
					// LogUtils.e(e.toString());
					// }

					Object response;
					try {
						response = ApiResponseFactory.getResponse(mContext, action, responseInfo, k, isCache, mPayId);
						if (response == null) {
							handler.onError(action, AppApi.ERROR_BUSSINESS);
							return;
						} else if (response instanceof Integer) {
							Integer res = (Integer) response;
							if (res != null && AppApi.HTTP_RESPONSE_STATE_CACHE == res) {
								LogUtils.d("from HttpServer cache data...");
								return;
							}
						} else if (response instanceof ResponseErrorMessage) {
							handler.onError(action, response);
							return;
						}
						setNeedUpdateUI(false);
						handler.onSuccess(action, response);
					} catch (Exception ex) {
						LogUtils.d(ex.toString());
						ex.printStackTrace();
					}
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					if (isCache) {
						if (isNeedUpdateUI()) {
							new loadCacheTask().execute(rParams, isCache, requestUrl);
						}
					}
					handler.onError(action, AppApi.ERROR_TIMEOUT);
				}

				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					super.onLoading(total, current, isUploading);
					if (handler instanceof UploadRequestListener) {
						UploadRequestListener uploadRequestListener = (UploadRequestListener) handler;
						uploadRequestListener.onLoading(total, current, isUploading);
					}
				}

				@Override
				public void updateProgress(long total, long current, boolean forceUpdateUI) {
					super.updateProgress(total, current, forceUpdateUI);
					if (handler instanceof UploadRequestListener) {
						UploadRequestListener uploadRequestListener = (UploadRequestListener) handler;
						uploadRequestListener.updateProgress(total, current, forceUpdateUI);
					}
				}

			});
		} catch (Exception e) {
			LogUtils.d(e.toString());
		}
	}
	
	/**
	 * 上传多个文件
	 * @param paramKey
	 * @param filePath
	 * @param isCache
	 * @param isGzip
	 * @param isDes
	 */
	public void uploadMuchFile(List<String> paths, boolean isGzip,final boolean isCache,HashMap<String, String> params) {
//		final File srcFile = new File(filePath);
//		if (!srcFile.exists()) {
//			return;
//		}
		final String requestUrl = AppApi.API_URLS.get(action);
		LogUtils.e(requestUrl);

		try {
			/** 序列化请求包体json */
			final RequestParams rParams = new RequestParams();
			// rParams.addHeader("traceinfo", appSession.getDeviceInfo());
			if (isGzip) {
				rParams.addHeader("Accept-Encoding", "gzip");
			}
			
			Iterator<String> pIterator = params.keySet().iterator();
			 while (pIterator.hasNext()) {
				    String key = pIterator.next();
				    String val=params.get(key);
				    LogUtils.e(key+"------"+val);
				    rParams.addBodyParameter(key, val);
			 }
			
			 for (int i = 0; i < paths.size(); i++) {
				 File srcFile = new File(paths.get(i));
					if (!srcFile.exists()) {
						continue;
					}
					
				 rParams.addBodyParameter("urlName"+(i+1), srcFile);
			}
			 
			//rParams.addBodyParameter("urlName", new File("/storage/emulated/0/TcMaster/tempfile/1433400742893.jpg"));
			if (!AppUtils.isNetworkAvailable(mContext)) {
				return;
			} else {
				/** 网络正常情况，判断是否是cache的请求，如果是，携带head相关参数 */
			}
			
			httpHandler = httpUtils.send(HttpMethod.POST, requestUrl, rParams, new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					LogUtils.d("评价图片上传成功");
				
					Object response;
					try {
						response = ApiResponseFactory.getResponse(mContext, action, responseInfo, requestUrl, isCache, mPayId);
						if (response == null) {
							handler.onError(action, AppApi.ERROR_BUSSINESS);
							return;
						} else if (response instanceof Integer) {
							Integer res = (Integer) response;
							if (res != null && AppApi.HTTP_RESPONSE_STATE_CACHE == res) {
								LogUtils.d("from HttpServer cache data...");
								return;
							}
						} else if (response instanceof ResponseErrorMessage) {
							handler.onError(action, response);
							return;
						}
						setNeedUpdateUI(false);
						handler.onSuccess(action, response);
					} catch (Exception ex) {
						LogUtils.d(ex.toString());
						ex.printStackTrace();
					}
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					if (isCache) {
						if (isNeedUpdateUI()) {
							new loadCacheTask().execute(rParams, isCache, requestUrl);
						}
					}
					LogUtils.e(error.getMessage()+msg);

					handler.onError(action, AppApi.ERROR_TIMEOUT);
				}

				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					super.onLoading(total, current, isUploading);
					if (handler instanceof UploadRequestListener) {
						UploadRequestListener uploadRequestListener = (UploadRequestListener) handler;
						uploadRequestListener.onLoading(total, current, isUploading);
					}
				}

				@Override
				public void updateProgress(long total, long current, boolean forceUpdateUI) {
					super.updateProgress(total, current, forceUpdateUI);
					if (handler instanceof UploadRequestListener) {
						UploadRequestListener uploadRequestListener = (UploadRequestListener) handler;
						uploadRequestListener.updateProgress(total, current, forceUpdateUI);
					}
				}

			});
		} catch (Exception e) {
			LogUtils.d(e.toString());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 上传多个文件
	 * @param paramKey
	 * @param filePath
	 * @param isCache
	 * @param isGzip
	 * @param isDes
	 */
	public void uploadSingleFile(String path, boolean isGzip,final boolean isCache,HashMap<String, String> params) {
//		final File srcFile = new File(filePath);
//		if (!srcFile.exists()) {
//			return;
//		}
		final String requestUrl = AppApi.API_URLS.get(action);
		LogUtils.e(requestUrl);

		try {
			/** 序列化请求包体json */
			final RequestParams rParams = new RequestParams();
			// rParams.addHeader("traceinfo", appSession.getDeviceInfo());
			if (isGzip) {
				rParams.addHeader("Accept-Encoding", "gzip");
			}
			
			Iterator<String> pIterator = params.keySet().iterator();
			 while (pIterator.hasNext()) {
				    String key = pIterator.next();
				    String val=params.get(key);
				    LogUtils.e(key+"------"+val);
				    rParams.addBodyParameter(key, val);
			 }
			
			
				 File srcFile = new File(path);
				 rParams.addBodyParameter("urlName", srcFile);
			
			 
			//rParams.addBodyParameter("urlName", new File("/storage/emulated/0/TcMaster/tempfile/1433400742893.jpg"));
			if (!AppUtils.isNetworkAvailable(mContext)) {
				return;
			} else {
				/** 网络正常情况，判断是否是cache的请求，如果是，携带head相关参数 */
			}
			
			httpHandler = httpUtils.send(HttpMethod.POST, requestUrl, rParams, new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					LogUtils.d("评价图片上传成功");
				
					Object response;
					try {
						response = ApiResponseFactory.getResponse(mContext, action, responseInfo, requestUrl, isCache, mPayId);
						if (response == null) {
							handler.onError(action, AppApi.ERROR_BUSSINESS);
							return;
						} else if (response instanceof Integer) {
							Integer res = (Integer) response;
							if (res != null && AppApi.HTTP_RESPONSE_STATE_CACHE == res) {
								LogUtils.d("from HttpServer cache data...");
								return;
							}
						} else if (response instanceof ResponseErrorMessage) {
							handler.onError(action, response);
							return;
						}
						setNeedUpdateUI(false);
						handler.onSuccess(action, response);
					} catch (Exception ex) {
						LogUtils.d(ex.toString());
						ex.printStackTrace();
					}
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					if (isCache) {
						if (isNeedUpdateUI()) {
							new loadCacheTask().execute(rParams, isCache, requestUrl);
						}
					}
					LogUtils.e(error.getMessage()+msg);

					handler.onError(action, AppApi.ERROR_TIMEOUT);
				}

				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					super.onLoading(total, current, isUploading);
					if (handler instanceof UploadRequestListener) {
						UploadRequestListener uploadRequestListener = (UploadRequestListener) handler;
						uploadRequestListener.onLoading(total, current, isUploading);
					}
				}

				@Override
				public void updateProgress(long total, long current, boolean forceUpdateUI) {
					super.updateProgress(total, current, forceUpdateUI);
					if (handler instanceof UploadRequestListener) {
						UploadRequestListener uploadRequestListener = (UploadRequestListener) handler;
						uploadRequestListener.updateProgress(total, current, forceUpdateUI);
					}
				}

			});
		} catch (Exception e) {
			LogUtils.d(e.toString());
			e.printStackTrace();
		}
	}
}
