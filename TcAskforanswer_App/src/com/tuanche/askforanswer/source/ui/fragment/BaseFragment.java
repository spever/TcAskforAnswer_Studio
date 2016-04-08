package com.tuanche.askforanswer.source.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.app.core.Session;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.app.utils.Utils;
import com.tuanche.askforanswer.source.bean.UserBean;
import com.tuanche.askforanswer.source.view.DynamicBox;

public abstract  class BaseFragment extends  Fragment{
	protected boolean isAttatch ;// 是否attatch到页面
	protected boolean isFirst=true;
	protected int pageNum=1;
	protected int pageSize=20;
	protected int userId;
	protected boolean ispulldown=false;
	protected DynamicBox box;
	protected Context context;
	protected boolean ispullup=false;
	protected View emptyView;
	protected TextView no_content;
	private UserBean bean;
	protected int status;
	//	protected PictureUtils pictureUtils;
//	protected BitmapDisplayConfig config;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (null!= Utils.getUserBeann(context)) {
			userId = Utils.getUserId(context);
			status = Utils.getUserStatus(context);
		}	
		emptyView=View.inflate(context, R.layout.exception_nocontent, null);
		emptyView.setVisibility(View.GONE);
		no_content=(TextView) emptyView.findViewById(R.id.no_content);
//		initBitmapUtils();
	}
	
//	protected void initBitmapUtils() {
//		pictureUtils = PictureUtils.getInstance(getActivity());
//		config = new BitmapDisplayConfig();
//		config.setLoadingDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.head_s_boy));
//		config.setLoadFailedDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.head_s_boy));
//	}
	
	protected void showToast(int id) {
		if (isAttatch)
			ToastUtil.showToast(getActivity(), getResources().getString(id));
	}
	
	protected void showToast(String string) {
		if (isAttatch)
			ToastUtil.showToast(getActivity(), string);
	}
	
	@Override
	public void onAttach(Activity activity) {

		isAttatch=true;
		context=getActivity();
		super.onAttach(activity);
		
	}
	
	protected void showLoading() {
		box.showLoadingLayout();
	}
	
	@Override
	public void onDetach() {

		isAttatch=false;
		super.onDetach();
	}
	
	/**
	 * 首页第一次加载20 包括下拉刷新也用
	 */
	public abstract void firstload();
	
	public abstract void pullUpload(int index);
}
