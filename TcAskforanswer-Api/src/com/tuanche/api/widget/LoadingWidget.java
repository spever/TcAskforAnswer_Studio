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
package com.tuanche.api.widget;

import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tuanche.api.R;

/**
 * 
 * @ClassName: LoadingWidget
 * @Description: TODO(加载组件)
 * @author sun
 * @date 2014-4-24 下午6:12:42
 * 
 */
public class LoadingWidget {
	private HashMap<String, HashMap<String, FrameLayout>> loadingMap = new HashMap<String, HashMap<String, FrameLayout>>();
	private Activity activity;
	private String activityTag;
	public int styleId = 0;

	/**
	 * @Title: LoadingWidget
	 * @Description: TODO(初始化加载组件)
	 * @param Activity
	 *            activity(当前加载组件的容器)
	 * @param String
	 *            activityTag(当前加载组件的Tag)
	 * @return
	 * @throws
	 */
	public LoadingWidget(Activity activity, String activityTag) {
		this.activity = activity;
		this.activityTag = activityTag;
	}

	/**
	 * @Title: LoadingWidget
	 * @Description: TODO(初始化加载组件)
	 * @param Activity
	 *            activity(当前加载组件的容器)
	 * @param String
	 *            activityTag(当前加载组件的Tag)
	 * @param int styleId(自定义样式)
	 * @return
	 * @throws
	 */
	public LoadingWidget(Activity activity, String activityTag, int styleId) {
		this.activity = activity;
		this.activityTag = activityTag;
		this.styleId = styleId;
	}

	/**
	 * @Title: ShowLoading
	 * @Description: TODO(显示当前页面的加载组件)
	 * @param String
	 *            loadingTag(当前加载组件的唯一标示)
	 * @return
	 * @throws
	 */
	public void ShowLoading(String loadingTag) {
		LayoutInflater inflater = LayoutInflater.from(activity);
		FrameLayout layout;
		if (activity != null) {
			if (loadingMap.get(activityTag) == null
					|| loadingMap.get(activityTag).get(loadingTag) == null) {
				layout = (FrameLayout) inflater.inflate(R.layout.api_loading,
						null);
				FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT);
				ProgressBar progressbar = (ProgressBar) layout
						.findViewById(R.id.progressbar);
				setProgressBarStyle(progressbar);
				// 如当前loading图为隐藏则处理，否则不进行任何操作
				if (progressbar.getVisibility() == View.GONE) {
					progressbar.setVisibility(View.VISIBLE);
					activity.addContentView(layout, lp1);
					HashMap<String, FrameLayout> map = loadingMap
							.get(activityTag);
					if (map == null) {
						map = new HashMap<String, FrameLayout>();
					}
					map.put(loadingTag, layout);
					loadingMap.put(activityTag, map);
				}
			} else {
				layout = loadingMap.get(activityTag).get(loadingTag);
				ProgressBar progressbar = (ProgressBar) layout
						.findViewById(R.id.progressbar);
				// setProgressBarStyle(progressbar);
				layout.setVisibility(View.VISIBLE);
				progressbar.setVisibility(View.VISIBLE);
			}
		}
	}

	/**
	 * @Title: hideLoading
	 * @Description: TODO(隐藏当前页面的加载组件)
	 * @param String
	 *            loadingTag(当前加载组件的唯一标示)
	 * @return
	 * @throws
	 */
	public void hideLoading(String loadingTag) {
		FrameLayout layout;
		if (activity != null) {
			if (loadingMap.get(activityTag) == null
					|| loadingMap.get(activityTag).get(loadingTag) == null) {
				return;
			} else {
				layout = loadingMap.get(activityTag).get(loadingTag);
			}
			if (layout != null) {
				ProgressBar progressbar = (ProgressBar) layout
						.findViewById(R.id.progressbar);
				layout.setVisibility(View.GONE);
				progressbar.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * @Title: ShowLoadingAsView
	 * @Description: TODO(显示当前页面中当前父容器内的加载组件，局部显示)
	 * @param Object
	 *            parentLayout(当前加载组件的父容器LinearLayout、RelativeLayout、FrameLayout
	 *            )
	 * @param String
	 *            loadingTag(当前加载组件的唯一标示)
	 * @return
	 * @throws
	 */
	public void ShowLoadingAsView(Object parentLayout, String loadingTag) {

		LayoutInflater inflater = LayoutInflater.from(activity);
		FrameLayout layout;
		if (activity != null && parentLayout != null) {
			if (loadingMap.get(activityTag) == null
					|| loadingMap.get(activityTag).get(loadingTag) == null) {
				layout = (FrameLayout) inflater.inflate(R.layout.api_loading,
						null);
				ProgressBar progressbar = (ProgressBar) layout
						.findViewById(R.id.progressbar);
				TextView textView = (TextView) layout
						.findViewById(R.id.no_data);
				textView.setVisibility(View.GONE);
				layout.setOnClickListener(null);
				setProgressBarStyle(progressbar);
				// 如当前loading图为隐藏则处理，否则不进行任何操作
				if (progressbar.getVisibility() == View.GONE) {
					progressbar.setVisibility(View.VISIBLE);
					// 将loading添加到界面
					addViewForLayout(parentLayout, layout);
					HashMap<String, FrameLayout> map = loadingMap
							.get(activityTag);
					if (map == null) {
						map = new HashMap<String, FrameLayout>();
					}
					map.put(loadingTag, layout);
					loadingMap.put(activityTag, map);
				}
			} else {
				layout = loadingMap.get(activityTag).get(loadingTag);
				ProgressBar progressbar = (ProgressBar) layout
						.findViewById(R.id.progressbar);
				// setProgressBarStyle(progressbar);
				layout.setVisibility(View.VISIBLE);
				progressbar.setVisibility(View.VISIBLE);
				TextView textView = (TextView) layout
						.findViewById(R.id.no_data);
				textView.setVisibility(View.GONE);
				layout.setOnClickListener(null);
			}
		}
	}

	/**
	 * @Title: hideLoadingAsView
	 * @Description: TODO(隐藏当前页面中当前父容器内的加载组件)
	 * @param Object
	 *            parentLayout(当前加载组件的父容器LinearLayout、RelativeLayout、FrameLayout
	 *            )
	 * @param String
	 *            loadingTag(当前加载组件的唯一标示)
	 * @return
	 * @throws
	 */
	public void hideLoadingAsView(String loadingTag) {
		FrameLayout layout;
		if (activity != null ) {
			if (loadingMap.get(activityTag) == null
					|| loadingMap.get(activityTag).get(loadingTag) == null) {
				return;
			} else {
				layout = loadingMap.get(activityTag).get(loadingTag);
			}
			if (layout != null) {
				ProgressBar progressbar = (ProgressBar) layout
						.findViewById(R.id.progressbar);
				layout.setVisibility(View.GONE);
				progressbar.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * @Title: hideLoadingAsViewShowError
	 * @Description: TODO(隐藏当前页面中当前父容器内的加载组件)
	 * @param Object
	 *            parentLayout(当前加载组件的父容器LinearLayout、RelativeLayout、FrameLayout
	 *            )
	 * @param String
	 *            loadingTag(当前加载组件的唯一标示)
	 * @return
	 * @throws
	 */
	public void hideLoadingAsViewShowError(String loadingTag, String errorMsg,
			android.view.View.OnClickListener listener) {
		FrameLayout layout;
		if (activity != null) {
			if (loadingMap.get(activityTag) == null
					|| loadingMap.get(activityTag).get(loadingTag) == null) {
				return;
			} else {
				layout = loadingMap.get(activityTag).get(loadingTag);
			}
			if (layout != null) {
				if (layout.getVisibility() == View.VISIBLE) {
					ProgressBar progressbar = (ProgressBar)layout.findViewById(R.id.progressbar);
					TextView textView = (TextView) layout.findViewById(R.id.no_data);
					textView.setText(errorMsg);
					textView.setVisibility(View.VISIBLE);
					progressbar.setVisibility(View.GONE);
					layout.setVisibility(View.VISIBLE);
					layout.setOnClickListener(listener);
				}
			}
		}
	}

	/**
	 * @Title: addViewForLayout
	 * @Description: TODO(隐藏当前页面中当前父容器内的加载组件)
	 * @param Object
	 *            parentLayout(当前加载组件的父容器LinearLayout、RelativeLayout、FrameLayout
	 *            )
	 * @param FrameLayout
	 *            childLayout(子布局)
	 * @return
	 * @throws
	 */
	public void addViewForLayout(Object parentLayout, FrameLayout childLayout) {
		LayoutParams layoutParams;
		if (parentLayout instanceof LinearLayout) {
			layoutParams = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			LinearLayout layout = (LinearLayout) parentLayout;
			layout.addView(childLayout, layoutParams);
		} else if (parentLayout instanceof RelativeLayout) {
			layoutParams = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			RelativeLayout layout = (RelativeLayout) parentLayout;
			layout.addView(childLayout, layoutParams);
		} else if (parentLayout instanceof FrameLayout) {
			layoutParams = new FrameLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			FrameLayout layout = (FrameLayout) parentLayout;
			layout.addView(childLayout, layoutParams);
		} else {
			return;
		}
	}

	/**
	 * @Title: getLayout
	 * @Description: TODO(获取容器)
	 * @param Object
	 *            parentLayout(当前加载组件的父容器LinearLayout、RelativeLayout、FrameLayout
	 *            )
	 * @return ViewGroup (容器)
	 * @throws
	 */
	public ViewGroup getLayout(Object parentLayout) {
		ViewGroup viewGroup = (ViewGroup) parentLayout;
		return viewGroup;
	}

	public void setProgressBarStyle(ProgressBar progressbar) {
		switch (styleId) {
		case 0:

			break;
		case 1:
			progressbar.setIndeterminateDrawable(new LoadingDrawable(activity));
			progressbar.setPadding(0, 30, 0, 30);
			break;

		default:
			break;
		}
	}
}