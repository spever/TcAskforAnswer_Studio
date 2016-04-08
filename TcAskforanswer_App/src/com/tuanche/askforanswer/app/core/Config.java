package com.tuanche.askforanswer.app.core;

public class Config {

	/**
	 * 用户User_id
	 */
	public static final String USER_ID = "user_id";
	public static final String USER_PHONE = "user_phone";
	public static final String USER_BEAN = "user_bean";
	public static final String IDENTIFY="identify";
	
	//
	//public  static String LOCALTESTURL="http://test2.yangche.tuanche.com";

	//public final static String LOCALTESTURL="http://mapi.tuanche.com/sold";  //线上  域名

	//public static  String LOCALTESTURL = "http://testyangche.tuanche.com/sold";  //开发101环境

	public static  String LOCALTESTURL = "http://test2.mapi.tuanche.com/sold";  //测试环境

	//public static  String LOCALTESTURL = "http://test.mapi.tuanche.com/sold";  //预生产环境

	//public static String LOCALTESTURL = "http://172.16.12.101:8867";

//	public static String LOCALTESTURL="http://172.16.3.75:8888";     //小野  本机服务器地址

	//首页接口
	public final static String HOME_ADDRESS=LOCALTESTURL+"/qa/answer/list";
	//回答 和追问的回答
	public final static String ASK_ANSWER=LOCALTESTURL+"/qa/answer/submitanswer";
	//详情
	public final static String ASK_DETIAL=LOCALTESTURL+"/qa/answer/detail";
	//答题量
	public final static String ASK_ANSWER_NUMBER=LOCALTESTURL+"/qa/question/mqdata";
	//消息通知
	public final static String ASK_MSG=LOCALTESTURL+"/qa/msg/list";
	//消息已读
	public final static String ASK_MSG_READED=LOCALTESTURL+"/qa/msg/read";
	
	
	/**
	 * 车大师协议
	 */
	public final static String CAR_PROTECOL =  "http://static.tuanche.com/sold/qa/chedashi/user_protocol.html";
		
	/**
	 * 选择城市列表
	 */
	public final static String SELECT_CITY =  LOCALTESTURL + "/qa/signin/info/getCity";
	
	/**
	 * 选择擅长方向
	 */
	public final static String SELECT_TAGS =  LOCALTESTURL + "/qa/signin/info/getTags";
	
	/**
	 * 获取手机验证码
	 */
	public final static String GET_PHONE_CODE = LOCALTESTURL + "/qa/login/getCode";
	
	/**
	 * 技师注册（下一步，请填写信息） 或  登录
	 */
	public final static String LOGIN_REGISTER =  LOCALTESTURL + "/qa/login/regist";
	
	/**
	 * 技师擅长品牌
	 */
	public final static String CAR_TYPE =  LOCALTESTURL + "/qa/signin/info/getCarBrand";
	
	/**
	 * 完善注册
	 */
	public final static String COMPLETE =  LOCALTESTURL + "/qa/signin/info/reg/tech";
	
	/**
	 * 登陆接口
	 */
	public final static String LOGIN_USER =  LOCALTESTURL + "/qa/login/login";
	
	
	/**
	 * 我的信息首页
	 */
	public final static String MINE_INFO =  LOCALTESTURL + "/qa/answer/getTechHomeData";
	
	/**
	 * 银行卡信息 查询
	 */
	public final static String BANK_INFO =  LOCALTESTURL + "/qa/answer/getTechBankData";
	
	/**
	 * 银行卡 开通省份
	 */
	public final static String PROVINCE_INFO =  LOCALTESTURL + "/qa/tenpay/toProvince";
	
	/**
	 * 银行卡 开通省份  下的城市
	 */
	public final static String CITY_BANK_INFO =  LOCALTESTURL + "/qa/tenpay/toCityByProvince";
	
	/**
	 * 银行卡 类型
	 */
	public final static String BANK_TYPE__INFO =  LOCALTESTURL + "/qa/tenpay/toBankType";
	
	/**
	 * 银行卡信息 录入
	 */
	public final static String UPDATE_BANK__INFO =  LOCALTESTURL + "/qa/user/updateByBankInfo";
	
	/**
	 * 战利品
	 */
	public final static String MONEYS__INFO =  LOCALTESTURL + "/qa/user/myBooty";
	
	/**
	 * 提现接口
	 */
	public final static String GETMONEYS__RESULT =  LOCALTESTURL + "/qa/takecashes/apply";
	
	/**
	 * 免打扰
	 */
	public final static String TIME_NOT_BROTHER =  LOCALTESTURL + "/qa/user/updateByNoTime";
	
	/**
	 * 是否显示语音验证码
	 */
	public final static String VOICE_SHOW_CODE =  LOCALTESTURL + "/qa/login/voiceCode";
	
	

	/**
	 * 选择照片是图库还是照相机拍照
	 * @author Administrator
	 *
	 */
	public final static int gallery=5;

	public final static int camera=6 ; 

	/**
	 * 认证查询
	 */
	public final static String AUDIT_STATUS=LOCALTESTURL+"/qa/up/user/getTecAuditInfo";
	/**
	 * 认证资料上传
	 */
	public final static String UPLOADPICTURE=LOCALTESTURL+"/qa/up/user/uploadImg";
	
	
	/**
	 * 头像上传
	 */
	public final static String UPLOADHEADPICTURE=LOCALTESTURL+"/qa/up/user/uploadImg/userLogo";

	public final static String LABLES="lables";//6个标签
	/**
	 * 消息跳到详情
	 */
	public final static String MSGINFOTODETIAL="msginfo";
	/**
	 * 首页跳到详情
	 */
	public final static String HOMETODETIAL="homeinfo";
	
	/**
	 * 首页跳到详情
	 */
	public final static String QUESTIONTODETIAL="questinfo";
	
	
	/**
	 * 图片root路径
	 */
	public final static String PHOTO_PATH="http://pic.tuanche.com/sold";
	
	/**
	 * 审核状态
	 */
	public final static String STATUS_BEAN="status_bean";
	
	/**
	 * 是否有消息用于判断是否收到推送
	 */
	public final static String IFHAVENSG="ifhavemsg";
	public final static String ISPASS="ispass";

	/**
	 * 系统通知的消息
	 */
	public final static String MSGTYPE="msgtype";
	public final static String MSGTURL="url";
	public final static String MSGTITLE="msgtitle";
}
