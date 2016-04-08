package com.tuanche.askforanswer.source.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.source.bean.EachTag;

public class CarTagsAdapter extends BaseAdapter {

	private Context mContext;
	private List<EachTag> tagsList;
	private LayoutInflater mInflater;
	
	public CarTagsAdapter(Context mContext, List<EachTag> tagsList) {
		super();
		this.mContext = mContext;
		this.tagsList = tagsList;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tagsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return tagsList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHold viewHold;
		if(convertView == null){
			viewHold = new ViewHold();
			convertView = mInflater.inflate(R.layout.listview_tags_car, null);
			viewHold.tagName = (TextView) convertView.findViewById(R.id.tag_name);
			viewHold.image = (ImageView) convertView.findViewById(R.id.select_image);
			convertView.setTag(viewHold);
			
		}else{
			viewHold=(ViewHold) convertView.getTag();
		}
		EachTag tag = tagsList.get(arg0);
		viewHold.tagName.setText(tag.getName());
		
		return convertView;
	}

	class ViewHold{
		TextView tagName;
		ImageView image;
	}
	
}
