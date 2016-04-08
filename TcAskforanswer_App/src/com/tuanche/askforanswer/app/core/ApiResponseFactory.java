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

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tuanche.api.http.ResponseInfo;
import com.tuanche.api.utils.DesUtils;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.source.bean.AuditStatusBean;
import com.tuanche.askforanswer.source.bean.AwardMoney;
import com.tuanche.askforanswer.source.bean.BankCityBean;
import com.tuanche.askforanswer.source.bean.BankInfo;
import com.tuanche.askforanswer.source.bean.BankTypeAll;
import com.tuanche.askforanswer.source.bean.BaseBean;
import com.tuanche.askforanswer.source.bean.Car_Type_All;
import com.tuanche.askforanswer.source.bean.CityAll;
import com.tuanche.askforanswer.source.bean.CompleteBankInfo;
import com.tuanche.askforanswer.source.bean.DatialBean;
import com.tuanche.askforanswer.source.bean.HomeListInfo;
import com.tuanche.askforanswer.source.bean.LoginOrRegist;
import com.tuanche.askforanswer.source.bean.MineInfo;
import com.tuanche.askforanswer.source.bean.MoneyResult;
import com.tuanche.askforanswer.source.bean.MqaNumBean;
import com.tuanche.askforanswer.source.bean.MsgInfo;
import com.tuanche.askforanswer.source.bean.MsgReaded;
import com.tuanche.askforanswer.source.bean.ResponseBean;
import com.tuanche.askforanswer.source.bean.SelectCity;
import com.tuanche.askforanswer.source.bean.SubmiterResultBean;
import com.tuanche.askforanswer.source.bean.TagsOfCar;
import com.tuanche.askforanswer.source.bean.TimeNotResult;
import com.tuanche.askforanswer.source.bean.UpLoadBean;
import com.tuanche.askforanswer.source.bean.Validate_Code;
import com.tuanche.askforanswer.source.bean.VoiceResult;

/**
 * API 响应结果解析工厂类，所有的API响应结果解析需要在此完成。
 * 
 * @author andrew
 * @date 2011-4-22
 * 
 */
public class ApiResponseFactory {
	public final static String TAG = "ApiResponseFactory";
	// 当前服务器时间
	private static String webtime = "";

	public static Object getResponse(Context context, Action action, ResponseInfo response, String key, boolean isCache, int payId) {
		// 转换器

		String requestMethod = "";
		Object result = null;
		boolean isDes = false;
		Session.get(context);
		String jsonResult = (String) response.result;
		if (jsonResult == null) {
			return null;
		}
		// try {
		// int responseCode = Integer.parseInt(jsonResult.trim());
		// //
		// return Integer.valueOf(responseCode);
		// } catch (NumberFormatException e1) {
		// e1.printStackTrace();
		// }
		// long start = System.currentTimeMillis();
		Header header = response.getFirstHeader("des");
		if (header != null) {
			isDes = true;
		}
		if (isDes) {
			jsonResult = DesUtils.decrypt(jsonResult);
		}
		Header[] headers = response.getHeaders("webtime");
		if (headers != null && headers.length > 0) {
			webtime = headers[0].getValue();
		}
		LogUtils.i("jsonResult:" + jsonResult);
		// long end = System.currentTimeMillis();
		JSONObject rSet;
		try {

			rSet = new JSONObject(jsonResult);
			rSet.toString();
			int code = 10000;
			code = rSet.getInt("ret");

			if (10000 != code) {
				ResponseErrorMessage errorMessage = new ResponseErrorMessage();
				errorMessage.setMsg(rSet.getString("msg"));
				errorMessage.setRet(rSet.getString("ret"));
				result = errorMessage;
			} else {
				result = parseResponse(action, jsonResult, null, payId);
			}
		} catch (Exception e) {
			LogUtils.d(requestMethod + " has other unknown Exception", e);
			e.printStackTrace();
			result = new ResponseErrorMessage();
			((ResponseErrorMessage) result).setMsg(String.format(context.getResources().getString(R.string.gson_error), action.name()));
		}

		return result;
	}

	public static Object parseResponse(Action action, String info, JSONObject ret, int payId) {
		Object result = null;
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		if (info == null) {
			return result;
		}
		switch (action) {
		case LOGIN_NOSIGN:
			LoginOrRegist loginInfo = gson.fromJson(info, new TypeToken<LoginOrRegist>() {
			}.getType());
			result = loginInfo;
			break;
		case LOGINDENLU_NOSIGN:
			LoginOrRegist loginInfo2 = gson.fromJson(info, new TypeToken<LoginOrRegist>() {
			}.getType());
			result = loginInfo2;
			break;
		case WEIPING_NOSIGN:
			ResponseBean bean = gson.fromJson(info, new TypeToken<ResponseBean>() {
			}.getType());
			result = bean;
			break;
		case CARTYPE_NOSIGN:
			Car_Type_All car_type = gson.fromJson(info, new TypeToken<Car_Type_All>() {
			}.getType());
			result = car_type;
			break;

		case TAG_NOSIGN:
			TagsOfCar tags = gson.fromJson(info, new TypeToken<TagsOfCar>() {
			}.getType());
			result = tags;
			break;
		case PHONECODE_NOSIGN:
			Validate_Code phoneCode = gson.fromJson(info, new TypeToken<Validate_Code>() {
			}.getType());
			result = phoneCode;
			break;
		case SECLECTCITY_NOSIGN:
			SelectCity city = gson.fromJson(info, new TypeToken<SelectCity>() {
			}.getType());
			result = city;
			break;
		case VOICE_NOSIGN:
			VoiceResult voiceResult = gson.fromJson(info, new TypeToken<VoiceResult>() {
			}.getType());
			result = voiceResult;
			break;
		case HOME_NOSIGN:
			HomeListInfo homeInfo = gson.fromJson(info, new TypeToken<HomeListInfo>() {
			}.getType());
			result = homeInfo;
			break;
		case ASKMSG_NOSIGN:
			MsgInfo msgInfo=gson.fromJson(info, new TypeToken<MsgInfo>() {
			}.getType());
			result=msgInfo;
			break;
		case MQADA_NOSIGN:
			MqaNumBean beMqaNumBean=gson.fromJson(info, new TypeToken<MqaNumBean>(){}.getType());
			result=beMqaNumBean;
			break;
		case MONEYS_NOSIGN:
			AwardMoney awardMoney=gson.fromJson(info, new TypeToken<AwardMoney>(){}.getType());
			result=awardMoney;
			break;
		case GETMONEY_NOSIGN:
			MoneyResult moneyResult=gson.fromJson(info, new TypeToken<MoneyResult>(){}.getType());
			result=moneyResult;
			break;
		case MINEINFO_NOSIGN:
			MineInfo mineInfo=gson.fromJson(info, new TypeToken<MineInfo>(){}.getType());
			result=mineInfo;
			break;
		case CITYBANK_NOSIGN:
			CityAll cityAll=gson.fromJson(info, new TypeToken<CityAll>(){}.getType());
			result = cityAll;
			break;
		case ANSWER_NOSIGN:
			SubmiterResultBean resultBean=gson.fromJson(info, new TypeToken<SubmiterResultBean>(){}.getType());
			result=resultBean;
			break;
		case COMPLETE_NOSIGN:
			LoginOrRegist completeBean=gson.fromJson(info, new TypeToken<LoginOrRegist>(){}.getType());
			result=completeBean;
			break;
		case BANKINFO_NOSIGN:
			BankInfo bankInfo=gson.fromJson(info, new TypeToken<BankInfo>(){}.getType());
			result=bankInfo;
			break;
		case BANKTYPE_NOSIGN:
			BankTypeAll bankTypeAll=gson.fromJson(info, new TypeToken<BankTypeAll>(){}.getType());
			result=bankTypeAll;
			break;
		case PROVINCE_NOSIGN:
			BankCityBean bankCityBean=gson.fromJson(info, new TypeToken<BankCityBean>(){}.getType());
			result=bankCityBean;
			break;
		case UPDATEBANK_NOSIGN:
			CompleteBankInfo completeBankInfo=gson.fromJson(info, new TypeToken<CompleteBankInfo>(){}.getType());
			result=completeBankInfo;
			break;
		case TIMEBOTHER_NOSIGN:
			TimeNotResult timeNotResult=gson.fromJson(info, new TypeToken<TimeNotResult>(){}.getType());
			result=timeNotResult;
			break;

		case DETAIL_NOSIGN:
			DatialBean detialBean=gson.fromJson(info, new TypeToken<DatialBean>(){}.getType());
			result=detialBean;
			break;
		case AUDIT_NOSIGN:
			AuditStatusBean statusBean=gson.fromJson(info, new TypeToken<AuditStatusBean>(){}.getType());
			result=statusBean;
			break;
		
		case ASKMSG_READED_NOSIGN:
			MsgReaded readed=gson.fromJson(info,  new TypeToken<MsgReaded>(){}.getType());
			result=readed;
			break;
		case UPLOAD_NOSIGN:	
			UpLoadBean loadBean=gson.fromJson(info, new TypeToken<UpLoadBean>(){}.getType());
			result=loadBean;
			break;
		case UPLOAD_PORTRAI_NOSIGN:	
			BaseBean baseBean=gson.fromJson(info, new TypeToken<BaseBean>(){}.getType());
			result=baseBean;
			break;
			
			
		default:
			break;
		}
		return result;
	}

}