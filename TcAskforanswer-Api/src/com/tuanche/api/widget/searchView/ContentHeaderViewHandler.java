package com.tuanche.api.widget.searchView;

import java.util.List;


/**
 * 内容列表listview的headerview处理器
 * @author Administrator
 *
 */
public interface ContentHeaderViewHandler {
	/**
	 * 处理内容列表listview的headerview的数据展示
	 * @param contenList 原始数据
	 * @param layoutId 内容列表headerview布局资源id
	 */
	void initContentHeaderView(List<IContentItem> contentList,int layoutId);
}
