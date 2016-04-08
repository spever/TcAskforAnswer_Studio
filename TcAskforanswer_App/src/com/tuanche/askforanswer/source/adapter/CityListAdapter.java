package com.tuanche.askforanswer.source.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.source.bean.CityEach;

public class CityListAdapter extends BaseAdapter {

	
	private Context mContext;
	private List<CityEach> mCityLists;
	private LayoutInflater mInflater;
	
	
	public CityListAdapter(Context context, List<CityEach> mCityLists) {
		super();
		this.mContext = context;
		this.mCityLists = mCityLists;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCityLists.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mCityLists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_city, null);
			viewHolder.cityName = (TextView) convertView.findViewById(R.id.city_name);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		CityEach cityEach = mCityLists.get(arg0);
		viewHolder.cityName.setText(cityEach.getCity_name()+"");
		
		return convertView;
		
	}

	class ViewHolder{
		
			TextView cityName;
	}
	
}
