package com.tuanche.askforanswer.source.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.source.bean.BankTypeEach;

public class BankTypeAdapter extends BaseAdapter {

	private Context mContext;
	private List<BankTypeEach> mList;
	private LayoutInflater mInflater;
	
	public BankTypeAdapter(Context mContext, List<BankTypeEach> mList) {
		super();
		this.mContext = mContext;
		this.mList = mList;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
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
			convertView = mInflater.inflate(R.layout.listview_banktype_item, null);
			viewHold.textName = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(viewHold);
			
		}else{
			viewHold=(ViewHold) convertView.getTag();
		}
		
		BankTypeEach bankTypeEach = mList.get(arg0);
		viewHold.textName.setText(bankTypeEach.getBank_name());
		return convertView;
	}

	class ViewHold{
		TextView textName;
	}
}
