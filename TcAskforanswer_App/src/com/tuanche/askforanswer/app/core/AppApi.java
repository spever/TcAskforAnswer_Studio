package com.tuanche.askforanswer.app.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.tuanche.askforanswer.app.utils.Utils;

public class AppApi {
	public static final String TC_APK_DOWNLOAD_FILENAME = "Tuanche_App.apk";

	/** 服务器接口 */
	// public static final String BASIC_URL = "http://mapi.tuanche.com/service";
	// 测试环境
	// public static final String BASIC_URL = "http://172.16.12.23:8111";
	// public static final String BaseUrl = "http://oa.tuanche.net";
	// 玉利
	// public static final String BASIC_URL = "http://172.16.5.117:8080";

	// public static final String BaseUrl = "http://oa.tuanche.net";

	// public static final String BaseUrl = "http://oa.tuanche.net";

	/**
	 * 常用的一些key值 ,签名、时间戳、token、params
	 */
	public static final String SIGN = "sign";
	public static final String TIME = "time";
	public static final String TOKEN = "token";
	public static final String PARAMS = "params";

	
	/**
	 * Action-自定义行为 注意：自定义后缀必须为以下结束 _FORM:该请求是Form表单请求方式 _JSON:该请求是Json字符串
	 * _XML:该请求是XML请求描述文件 _GOODS_DESCRIPTION:图文详情 __NOSIGN:参数不需要进行加密
	 * */
	public static enum Action {


		LOGIN_NOSIGN , LOGINDENLU_NOSIGN , NETWORK_FAILED, LOCATION, LOCATION_ADDGRESS, WEIPING_NOSIGN, CARTYPE_NOSIGN,  HOME_NOSIGN, TAG_NOSIGN, ANSWER_NOSIGN, MQADA_NOSIGN,

		DETAIL_NOSIGN, ASKMSG_NOSIGN, ASKMSG_READED_NOSIGN, PHONECODE_NOSIGN, SECLECTCITY_NOSIGN , MINEINFO_NOSIGN , UPDATEBANK_NOSIGN, AUDIT_NOSIGN, COMPLETE_NOSIGN,
		
		BANKINFO_NOSIGN , PROVINCE_NOSIGN , CITYBANK_NOSIGN ,UPLOAD_NOSIGN , BANKTYPE_NOSIGN , MONEYS_NOSIGN , GETMONEY_NOSIGN , TIMEBOTHER_NOSIGN ,
		
		VOICE_NOSIGN,
		//上传头像
		UPLOAD_PORTRAI_NOSIGN
		
	};

	/** API_URLS:TODO(URL集合) */
	public static final HashMap<Action, String> API_URLS = new HashMap<Action, String>() {
		private static final long serialVersionUID = -8469661978245513712L;
		{

			put(Action.WEIPING_NOSIGN, weiping);
			
			/**
			 * 技师 擅长品牌
			 */
			put(Action.CARTYPE_NOSIGN,  Utils.getSecuityUrl(Config.CAR_TYPE));
			/**
			 * 完善注册
			 */
			put(Action.COMPLETE_NOSIGN, Utils.getSecuityUrl(Config.COMPLETE));
			/**
			 * 技师登陆
			 */
			put(Action.LOGINDENLU_NOSIGN, Utils.getSecuityUrl(Config.LOGIN_USER));

			/**
			 * 我的信息
			 */
			put(Action.MINEINFO_NOSIGN, Utils.getSecuityUrl(Config.MINE_INFO));

			/**
			 * 银行卡信息 这个接口
			 */

			put(Action.BANKINFO_NOSIGN, Utils.getSecuityUrl(Config.BANK_INFO));
			
			/**
			 * 银行卡 开通省份
			 */
			put(Action.PROVINCE_NOSIGN, Utils.getSecuityUrl(Config.PROVINCE_INFO));
			
			/**
			 * 银行卡 开通省份 下的城市
			 */
			put(Action.CITYBANK_NOSIGN, Utils.getSecuityUrl(Config.CITY_BANK_INFO));
			
			/**
			 * 银行卡 类型
			 */
			put(Action.BANKTYPE_NOSIGN, Utils.getSecuityUrl(Config.BANK_TYPE__INFO));
			
			/**
			 * 战利品
			 */
			put(Action.MONEYS_NOSIGN, Utils.getSecuityUrl(Config.MONEYS__INFO));
			
			/**
			 * 提现接口
			 */
			put(Action.GETMONEY_NOSIGN, Utils.getSecuityUrl(Config.GETMONEYS__RESULT));
			
			
			/**
			 * 银行卡信息录入          
			 */
			put(Action.UPDATEBANK_NOSIGN, Utils.getSecuityUrl(Config.UPDATE_BANK__INFO));
			
			/**
			 * 免打扰         
			 */
			put(Action.TIMEBOTHER_NOSIGN,  Utils.getSecuityUrl(Config.TIME_NOT_BROTHER));
			
			/**
			 * 是否显示语音验证码       
			 */
			put(Action.VOICE_NOSIGN,  Utils.getSecuityUrl(Config.VOICE_SHOW_CODE));
			

//			put(Action.CITY_NOSIGN, "http://172.16.3.143/signin/info/getCity");

			put(Action.HOME_NOSIGN, Utils.getSecuityUrl(Config.HOME_ADDRESS));

			put(Action.TAG_NOSIGN, Utils.getSecuityUrl(Config.SELECT_TAGS));

			put(Action.PHONECODE_NOSIGN, Utils.getSecuityUrl(Config.GET_PHONE_CODE));
			
			put(Action.LOGIN_NOSIGN, Utils.getSecuityUrl(Config.LOGIN_REGISTER));

			put(Action.SECLECTCITY_NOSIGN, Utils.getSecuityUrl(Config.SELECT_CITY));

			put(Action.ANSWER_NOSIGN, Utils.getSecuityUrl(Config.ASK_ANSWER));
			put(Action.MQADA_NOSIGN, Utils.getSecuityUrl(Config.ASK_ANSWER_NUMBER));
			put(Action.DETAIL_NOSIGN, Utils.getSecuityUrl(Config.ASK_DETIAL));
			put(Action.ASKMSG_NOSIGN, Utils.getSecuityUrl(Config.ASK_MSG));
			put(Action.ASKMSG_READED_NOSIGN, Utils.getSecuityUrl(Config.ASK_MSG_READED));
			
			put(Action.AUDIT_NOSIGN, Utils.getSecuityUrl(Config.AUDIT_STATUS));

			put(Action.UPLOAD_NOSIGN, Utils.getSecuityUrl(Config.UPLOADPICTURE));
			put(Action.UPLOAD_PORTRAI_NOSIGN, Utils.getSecuityUrl(Config.UPLOADHEADPICTURE));
			
			
		}
	};

	/**
	 * 
	 * @param context
	 * @param userId
	 * @param waitFlag
	 *            1等待回答/0补充回答
	 * @param start
	 * @param size
	 * @param tag
	 * @param token
	 * @param handler
	 */
	public static void getHomeListInfo(Context context, int userId, int waitFlag, int start, int size, String tag, String token,
			ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("waitFlag", waitFlag);
		params.put("start", start);
		params.put("size", size);
		params.put("tag", tag);
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		new AppService(context, Action.HOME_NOSIGN, handler, param1).post(false, false, false, false);
	}

	public static void BankInfo(Context context, com.tuanche.askforanswer.source.bean.BankInfo info, String token, ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		/*
		 * 
		 * 肖卫平 2015/5/26 18:15:15 {user_id:1,bank_type:
		 * '工商银行',bank_account:'62220000123244433443',bank_name:'肖卫平'}
		 */
		params.put("user_id", "1");
		params.put("bank_account", "62220000123244433111");
		params.put("bank_type", "我是测试银行");
		params.put("bank_name", "我是第一次测试");
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("time", String.valueOf(new Date().getTime()));
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		new AppService(context, Action.WEIPING_NOSIGN, handler, param1).post(false, false, false, false);

	}

	/**
	 * 获取验证码
	 * 
	 * @param context
	 * @param phone
	 * @param handler
	 */
	public static void PhoneCode(Context context, String phone, ApiRequestListener handler) {

		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_phone", phone);
		params.put("source_type", "4");
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("time", String.valueOf(new Date().getTime()));
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		new AppService(context, Action.PHONECODE_NOSIGN, handler, param1).post(false, false, false, false);

	}

	/**
	 * 注册 或 登陆
	 * 
	 * @param context
	 * @param phone
	 * @param code
	 * @param inviteCode
	 * @param handler
	 */
	public static void LoginOrRegister(Context context, String phone, String code, String inviteCode, ApiRequestListener handler) {

		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_phone", phone);
		params.put("validate_code", code);
		params.put("app_type", "2");
		params.put("invitation_code", inviteCode);
		params.put("source_type", "4");
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("time", String.valueOf(new Date().getTime()));
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		new AppService(context, Action.LOGIN_NOSIGN, handler, param1).post(false, false, false, false);

	}
	
	/**
	 * 
	 * @param context
	 * @param phone
	 * @param code
	 * @param inviteCode
	 * @param handler
	 */
	public static void Login_denglu(Context context, String phone, String code, ApiRequestListener handler) {
		
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_phone", phone);
		params.put("validate_code", code);
		params.put("app_type", "2");
		params.put("source_type", "4");
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("time", String.valueOf(new Date().getTime()));
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		new AppService(context, Action.LOGINDENLU_NOSIGN, handler, param1).post(false, false, false, false);
		
	}

	/**
	 * 完善技师注册
	 * 
	 * @param context
	 * @param user_id
	 * @param brand_id
	 * @param city
	 * @param user_phone
	 * @param tags
	 * @param nick
	 * @param handler
	 */
	public static void CompleteRegister(Context context, String user_id, String brand_id, String city, String user_phone, String tags, String nick,
			ApiRequestListener handler) {

		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("crm_user_id", user_id);
		params.put("brand_id", brand_id);
		params.put("city", city);
		params.put("user_phone", user_phone);
		params.put("tags", tags);
		params.put("nick", nick);
		params.put("mobile_type", "4");
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("time", String.valueOf(new Date().getTime()));
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		new AppService(context, Action.COMPLETE_NOSIGN, handler, param1).post(false, false, false, false);

	}
	
	

	/**
	 * 车型品牌通了
	 */
	public static void CarTypeAll(Context context, ApiRequestListener handler) {
		
		final HashMap<String, Object> params = new HashMap<String, Object>();
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.CARTYPE_NOSIGN, handler, param1).post(false, false, false, false);
	
	}
	
	
	/**
	 * 是否显示语音验证码
	 */
	public static void VOICE_SHOW(Context context, ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.VOICE_NOSIGN, handler, param1).post(false, false, false, false);
	}

	/**
	 * 技师擅长的方向
	 */
	public static void TagsCarAll(Context context, String userName, String password, String token, ApiRequestListener handler) {

		final HashMap<String, Object> params = new HashMap<String, Object>();
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.TAG_NOSIGN, handler, param1).post(false, false, false, false);
		
	}

	/**
	 * 我的信息首页
	 */
	public static void MINE_INFO(Context context, String userId, ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.MINEINFO_NOSIGN, handler, param1).post(false, false, false, false);

	}
	
	/**
	 * 免打扰时间
	 * @param context
	 * @param userId
	 * @param start_time
	 * @param end_time
	 * @param switch_status
	 * @param handler
	 */
	public static void NOT_TIME_BROTHER(Context context, String userId, String start_time, String end_time,int switch_status, ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_id", userId);
		params.put("start_time", start_time);
		params.put("end_time", end_time);
		params.put("switch_status", switch_status);
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.TIMEBOTHER_NOSIGN, handler, param1).post(false, false, false, false);
		
	}

	/**
	 * 银行卡信息   查询
 	 * @param context
	 * @param userId
	 * @param handler
	 */
	public static void BANK_INFO(Context context, String userId, ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId",userId);
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.BANKINFO_NOSIGN, handler, param1).post(false, false, false, false);
		
	}
	/**
	 * 银行卡 开通省份 
	 * @param context
	 * @param handler
	 */
	public static void BANK_PRO_INFO(Context context,ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.PROVINCE_NOSIGN, handler, param1).post(false, false, false, false);
		
	}
	
	/**
	 * 银行  类型
	 * @param context
	 * @param handler
	 */
	public static void BANK_TYPE_INFO(Context context,ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.BANKTYPE_NOSIGN, handler, param1).post(false, false, false, false);
		
	}
	


	/**

	 * 银行卡 开通省份   下的城市
	 * @param context
	 * @param province_code
	 * @param handler
	 */
	public static void CITY_BANK_All(Context context, String province_code , ApiRequestListener handler) {
		
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("province_code", province_code);
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.CITYBANK_NOSIGN, handler, param1).post(false, false, false, false);
		
	}
	
	/**
	 * 
	 * 战利品  
	 * @param context
	 * @param id   用户id
	 * @param handler
	 */
	public static void MONEY_All(Context context, String  id , ApiRequestListener handler) {
		
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id",  id);
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.MONEYS_NOSIGN, handler, param1).post(false, false, false, false);
	}
	
	/**
	 * 提现
	 * @param context
	 * @param userId
	 * @param handler
	 */
	public static void GETMONEY(Context context, String  userId , String money, ApiRequestListener handler) {
		
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId",  userId);
		params.put("money",  money);
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.GETMONEY_NOSIGN, handler, param1).post(false, false, false, false);
	}
	
	/**
	 * 完善 银行卡信息
	 * @param context
	 * @param user_id  用户id
	 * @param bank_type 开户行
	 * @param bank_name 账号开户名
	 * @param bank_account 银行账号
	 * @param tenpay_bank_type 银行类型
	 * @param province_code 省份code
	 * @param city_code 城市code
	 * @param handler
	 */
	public static void UPDATE_BANK_INFO(Context context,String user_id, String bank_type,String bank_name,String bank_account,
			String tenpay_bank_type,String province_code,String city_code, ApiRequestListener handler) {
		
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_id", user_id);
		params.put("bank_type", bank_type);
		params.put("bank_name", bank_name);
		params.put("bank_account", bank_account);
		params.put("tenpay_bank_type", tenpay_bank_type);
		params.put("province_code", province_code);
		params.put("city_code", city_code);
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.UPDATEBANK_NOSIGN, handler, param1).post(false, false, false, false);
		
	}
	
	/**
	 * 技师 注册 所在 城市
	 */
	public static void CITYAll(Context context, ApiRequestListener handler) {

		final HashMap<String, Object> params = new HashMap<String, Object>();
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.SECLECTCITY_NOSIGN, handler, param1).post(false, false, false, false);

	}

	

	/**
	 * 回答问题
	 * 
	 * @param context
	 * @param questionId
	 *            问题id
	 * @param answerUserId
	 *            回答用户id
	 * @param answerContent
	 * @param answerType
	 *            1.追问 2.回答
	 * @param token
	 * @param handler
	 */
	public static void askAnswer(Context context, int questionId, int answerUserId, String answerContent, int answerType, String token,
			ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("questionId", questionId);
		params.put("answerUserId", answerUserId);
		params.put("answerContent", answerContent);
		params.put("answerType", answerType);
		final HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.ANSWER_NOSIGN, handler, param1).post(false, false, false, false);
	}

	/**
	 * 我的答题量
	 * 
	 * @param context
	 * @param pageNo
	 *            页数
	 * @param user_id
	 *            用户id
	 * @param accept_tag
	 *            标签（-1：代表我的答题量；1代表我的被采纳）
	 * @param token
	 * @param handler
	 */
	public static void getMqaDa(Context context, int pageNo,int pageSize, int user_id, int accept_tag, String token, ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pageNo", pageNo);
		params.put("user_id", user_id);
		params.put("accept_tag", accept_tag);
		params.put("pageSize", pageSize);

		final HashMap<String, Object> param1 = new HashMap<String, Object>();
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		param1.put("time", String.valueOf(new Date().getTime()));
		new AppService(context, Action.MQADA_NOSIGN, handler, param1).post(false, false, false, false);

	}

	/**
	 * 问题详情
	 * 
	 * @param context
	 * @param id
	 *            问题id
	 * @param user_id
	 *            用户id
	 * @param token
	 * @param handler
	 */
	public static void Detail(Context context, int id, int user_id, int pageNo, int size, String token, ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("userId", user_id);
		params.put("pageNo", pageNo);
		params.put("size", size);
		final HashMap<String, Object> param1 = new HashMap<String, Object>();
		param1.put("time", String.valueOf(new Date().getTime()));
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		new AppService(context, Action.DETAIL_NOSIGN, handler, param1).post(false, false, false, false);

	}

	/**
	 * 消息通知
	 * 
	 * @param context
	 * @param userId
	 *            用户id
	 * @param status
	 *            问题通知：1 /系统消息：0
	 * @param token
	 * @param handle
	 */
	public static void getMsg(Context context, int userId, int status, int pageNo, int size, String token, ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("userId", userId);
		params.put("pageNo", pageNo);
		params.put("size", size);

		final HashMap<String, Object> param1 = new HashMap<String, Object>();
		param1.put("time", String.valueOf(new Date().getTime()));
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		new AppService(context, Action.ASKMSG_NOSIGN, handler, param1).post(false, false, false, false);
	}

	/**
	 * 消息已读
	 * 
	 * @param context
	 * @param msgId
	 *            消息ID
	 * @param handler
	 */
	public static void msgReaded(Context context, int msgId, ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", msgId);
		final HashMap<String, Object> param1 = new HashMap<String, Object>();
		param1.put("time", String.valueOf(new Date().getTime()));
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		new AppService(context, Action.ASKMSG_READED_NOSIGN, handler, param1).post(false, false, false, false);
	}

	/**
	 * 认证查询
	 * 
	 * @param context
	 * @param userid
	 * @param token
	 * @param handler
	 */
	public static void getAuditStatus(Context context, int userid, String token, ApiRequestListener handler) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userid);
		final HashMap<String, Object> param1 = new HashMap<String, Object>();
		param1.put("time", String.valueOf(new Date().getTime()));
		param1.put("params", ApiRequestFactory.JsonEntity(params));
		new AppService(context, Action.AUDIT_NOSIGN, handler, param1).post(false, false, false, false);
	}
	
	/**
	 * 
	 * @param context
	 * @param userid 用户id
	 * @param type
	 * @param paths 
	 * @param token
	 * @param handler
	 */
	public static void upLoadPicture(Context context, int userid, int type, List<String> paths, String token, ApiRequestListener handler) {
		final HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userid+"");
		params.put("type", type+"");
		params.put("count", paths.size()+"");
		// params.put("MultipartFile", str);
		// final HashMap<String, Object> param1 = new HashMap<String, Object>();
		// param1.put("time", String.valueOf(new Date().getTime()));
		// param1.put("params", ApiRequestFactory.JsonEntity(params));
		new AppService(context, Action.UPLOAD_NOSIGN, handler, null).uploadMuchFile(paths, true, false, params);
	}
	
	public static void upPortraitPicture(Context context, int userid,  String path,  ApiRequestListener handler) {
		
		final HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userid+"");
		new AppService(context, Action.UPLOAD_PORTRAI_NOSIGN, handler, null).uploadSingleFile(path, false, false, params);
	}
	
	

	// 超时（网络）异常
	public static final String ERROR_TIMEOUT = "3001";
	// 业务异常
	public static final String ERROR_BUSSINESS = "3002";
	// 网络断开
	public static final String ERROR_NETWORK_FAILED = "3003";

	public static final String RESPONSE_CACHE = "3004";

	/** 从这里定义业务的错误码 */
	public static final int HTTP_RESPONSE_STATE_SUCCESS = 10000;
	/** 登录状态码 */
	public static final int HTTP_RESPONSE_NEED_LOGIN = 310;
	/** 收藏成功 */
	public static final int HTTP_RESPONSE_ADD_GOODS_COLLECT = 10401;
	/** 收藏成功 */
	public static final int HTTP_RESPONSE_GOODS_CANCEL_COLLECT = 403;
	/** 从缓存中取数据 */
	public static final int HTTP_RESPONSE_STATE_CACHE = 99999;
	/** 密码错误或签名错误 */
	public static final int HTTP_RESPONSE_STATE_ERROR_PWDORSIGN = 400;
	/** 绑定手机号用户无余额 */
	public static final int HTTP_RESPONSE_CHECKMC_NO_MONEY = 10004;

	/**
	 * 卫平
	 */
	public static final String weiping = Config.LOCALTESTURL + "/qa/user/updateByBankInfo";

}
