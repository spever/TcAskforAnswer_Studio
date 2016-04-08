package com.tuanche.askforanswer.source.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.source.bean.QuestionDatialBean.DetialResultDetialBean.ReplyItemDetail;
import com.tuanche.askforanswer.source.view.CircularImage;

public class AskInfoAdapter extends MyBaseAdapter {
	private List<ReplyItemDetail> asks;
	private LayoutInflater inflater;
	int size;
	boolean isEmpty = false;
	private int height;
	final int TYPE_1 = 0;
	final int TYPE_2 = 1;
//	private PictureUtils utils;
	public AskInfoAdapter(Context context, List<ReplyItemDetail> asks, RelativeLayout view, int height) {
		super(context);
		this.asks = asks;
		inflater = LayoutInflater.from(context);
		this.height = height;
//		this.utils=utils;
	}

	public List<ReplyItemDetail> getAsks() {
		return asks;
	}

	public void setAsks(List<ReplyItemDetail> asks) {
		this.asks = asks;
	}

	@Override
	public int getCount() {
		if (asks.size() == 0) {
			size = 1;
			isEmpty = true;
		} else {
			isEmpty = false;
			size = asks.size();
		}
		return size;
	}

	@Override
	public Object getItem(int arg0) {
		return asks.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		int type = getItemViewType(arg0);
		ReplyItemDetail tiDetail = null;
		if(convertView==null){
		switch (type) {
		case TYPE_1:
			convertView = inflater.inflate(R.layout.exception_nocontent, null);
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, height);
			convertView.setLayoutParams(params);
			break;
		case TYPE_2:
			tiDetail = asks.get(arg0);
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.listview_answer_info_item, null);
			holder.itme_circle_iv = (CircularImage) convertView.findViewById(R.id.itme_circle_iv);
			holder.item_info_time = (TextView) convertView.findViewById(R.id.item_info_time);
			holder.item_info_tv = (TextView) convertView.findViewById(R.id.item_info_tv);
			holder.item_info_info = (TextView) convertView.findViewById(R.id.item_info_info);
			holder.item_adopt_view = (ImageView) convertView.findViewById(R.id.item_adopt_view);
			convertView.setTag(holder);
			pictureUtils.display(holder.itme_circle_iv, tiDetail.getIcon()+"?"+System.currentTimeMillis(),config);
			holder.item_info_info.setText(tiDetail.getAnswerContent());
			holder.item_info_time.setText(tiDetail.getAnswerDateStr());
			holder.item_info_tv.setText(tiDetail.getUserName());
			if (tiDetail.getAcceptStatus() == 1) {
				holder.item_adopt_view.setVisibility(View.VISIBLE);
			} else {
				holder.item_adopt_view.setVisibility(View.GONE);
			}
			break;
		default:
			break;
		}
		}else{
			switch (type) {
			case TYPE_1:
				convertView = inflater.inflate(R.layout.exception_nocontent, null);
				AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, height);
				convertView.setLayoutParams(params);
				break;
			case TYPE_2:
				tiDetail = asks.get(arg0);
				holder = (ViewHolder) convertView.getTag();
				pictureUtils.display(holder.itme_circle_iv, tiDetail.getIcon(),config);
				holder.item_info_info.setText(tiDetail.getAnswerContent());
				holder.item_info_time.setText(tiDetail.getAnswerDateStr());
				holder.item_info_tv.setText(tiDetail.getUserName());
				if (tiDetail.getAcceptStatus() == 1) {
					holder.item_adopt_view.setVisibility(View.VISIBLE);
				} else {
					holder.item_adopt_view.setVisibility(View.GONE);
				}
				break;
			default:
				break;
			}
		}
	
		return convertView;
	}

	@Override
	public int getItemViewType(int position) {
		if(isEmpty){
			return TYPE_1;
		}else{
			return TYPE_2;
		}
	}
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	static class ViewHolder {
		CircularImage itme_circle_iv;
		TextView item_info_time;
		TextView item_info_tv;
		TextView item_info_info;
		ImageView item_adopt_view;

	}
}
