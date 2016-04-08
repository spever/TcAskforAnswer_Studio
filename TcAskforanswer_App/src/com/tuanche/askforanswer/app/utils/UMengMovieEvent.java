package com.tuanche.askforanswer.app.utils;
/**
 * 电影里面的事件
 */
public interface UMengMovieEvent {
	/*************MovieDetailActivity******************/
	/**影片详情_当前界面*/
	String filmDetail = "filmDetail";
	/**影片详情_影片图片*/
	String filmDetail_image = "filmDetail_image";
	/**影片详情_返回*/
	String filmDetail_back = "filmDetail_back";
	/**影片详情_剧情介绍下拉按钮*/
	String filmDetail_detail = "filmDetail_detail";
	
	
	/****************选择影院CinemaListActivity**********/
	/**选择影院_返回*/
	String selectCinema_back = "selectCinema_back";
	/**选择影院_当前界面*/
	String selectCinema = "selectCinema";
	
	/****************影片列表MovieListActivity**********/
//	/**影片列表_下拉刷新*/
//	String  allFilms_refresh= "allFilms_refresh";
	/**影片列表_影片点击*/
	String allFilms_film = "allFilms_film";
	/**影片列表_即将上映*/
	String allFilms_willshow = "allFilms_willshow";
	/**影片列表_正在热映*/
	String allFilms_showing = "allFilms_showing";
	/**影片列表_返回*/
//	String allFilms_back = "allFilms_back";
	/**影片列表_当前界面*/
	String allFilms = "allFilms";
	
	
	/****************CinemaDetailActivity影院详情*****************/
	/**影院详情_当前界面*/
	String cinemaDetail = "cinemaDetail";
//	/**影院详情_附近团购_查看全部*/
//	String cinemaDetail_nearGroup_all = "cinemaDetail_nearGroup_all";
	/**影院详情_附件团购_商品*/
	String cinemaDetail_nearGroup = "cinemaDetail_nearGroup";
	/**影院详情_附近团购_分类切换*/
	String cinemaDetail_nearGroupCate_change = "cinemaDetail_nearGroupCate_change";
	/**影院详情_选座按钮点击*/
	String cinemaDetail_schedule = "cinemaDetail_schedule";
	/**影院详情_排期日期切换*/
	String cinemaDetail_date_change = "cinemaDetail_date_change";
	/**影院详情_影片详情点击*/
	String cinemaDetail_film_detail = "cinemaDetail_film_detail";
	/**影院详情_影片切换*/
	String cinemaDetail_film_change = "cinemaDetail_film_change";
	/**影院详情_超值团购更多点击*/
	String cinemaDetail_group = "cinemaDetail_group";
	/**影院详情_超值团购商品*/
	String cinemaDetail_group_spread = "cinemaDetail_group_spread";
	/**影院详情_电话点击*/
	String cinemaDetail_phone = "cinemaDetail_phone";
	/**影院详情_地址点击*/
	String cinemaDetail_position = "cinemaDetail_position";
	/**影院详情_影院详情-超值团购购买按钮*/
	String cinemaDetail_buy = "cinemaDetail_group_buy";
	
	
	/**************ChooseSeatActivity********************/
	/**电影选座_确定订座点击*/
	String selectSeat_submit = "selectSeat_submit";
	/**电影选座_双手缩放座位图*/
	String selectSeat_zoom = "selectSeat_zoom";
	/**电影选座_单击座位图*/
	String selectSeat_seat = "selectSeat_seat";
	/**电影选座_切换场次*/
	String selectSeat_schedule_change = "selectSeat_schedule_change";
	/**电影选座_当前界面*/
	String selectSeat = "selectSeat";
	
	/**************购票结果BuyResultActivity**********************/
	/**当前页面*/
	String buyResult = "buyResult";
	/**电影购买结果_推荐团单选择*/
	String buyResult_nearGroup = "buyResult_nearGroup";
	/**电影购买结果_看看其他影片点击*/
	String buyResult_otherFilm = "buyResult_otherFilm";
	/**电影购买结果_查看全部团购*/
	String buyResult_neargroup_all = "buyResult_neargroup_all";
	/**电影购买结果_选择场次*/
	String buyResult_schedule_change = "buyResult_schedule_change";
	/**电影购买结果_确定订座点击*/
	String buyResult_submit = "buyResult_submit";
	/**电影购买结果_点击座位图*/
	String buyResult_seat = "buyResult_seat";
	/**电影购买结果_支付成功出票失败*/
	String buyResult_failed = "buyResult_failed";
	/**电影购买结果_刷新取票码*/
	String buyResult_refreshCode = "buyResult_refreshCode";
	/**电影购买结果_查看如何取票*/
	String buyResult_howGetCode = "buyResult_howGetCode";
	/**电影购买结果_返回首页*/
	String buyResult_channel = "buyResult_channel";
	/**电影购买结果_查看拉手券*/
	String buyResult_ticket = "buyResult_ticket";
	/**购买结果页_支付成功出票等待*/
	String buyResult_wait = "buyResult_wait";
	/**购买结果页_支付成功出票成功*/
	String buyResult_success = "buyResult_success";
	
}
