package com.tuanche.askforanswer.source.adapter;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.source.bean.MsgInfoListBean;

public class MsgMineAdapter extends BaseAdapter{
	private List<MsgInfoListBean> asks; 
	private LayoutInflater inflater;
	private Resources res;
	private ListView listView;
	public MsgMineAdapter(Context context,List<MsgInfoListBean> asks,ListView listView){
		this.asks=asks;
		inflater=LayoutInflater.from(context);
		res=context.getResources();
		this.listView=listView;
	}
	
	public List<MsgInfoListBean> getAsks() {
		return asks;
	}

	public void setAsks(List<MsgInfoListBean> asks) {
		this.asks = asks;
	}

	@Override
	public int getCount() {
		return asks.size();
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
		MsgInfoListBean bean=asks.get(arg0);
		if(null==convertView){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.listview_msgmy_item, null);
			holder.item_info_time=(TextView) convertView.findViewById(R.id.item_info_time);
			holder.item_info_tv=(TextView) convertView.findViewById(R.id.item_info_tv);
			holder.item_info_info=(TextView) convertView.findViewById(R.id.item_info_info);
			holder.item_info_point=(ImageView) convertView.findViewById(R.id.point) ;
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		if (bean.getStatus()==1) {
			holder.item_info_point.setVisibility(View.INVISIBLE);
		}else{
			holder.item_info_point.setVisibility(View.VISIBLE);
		}
		holder.item_info_time.setText(bean.getDateStr());
		holder.item_info_tv.setText(String.format(res.getString(R.string.mine_msg_suggest), bean.getTitle()));
		holder.item_info_info.setText(bean.getContent());
		return convertView;
	}
	
	public void updateView(int index) {
		int visiblePos = listView.getFirstVisiblePosition();
		Log.i("imageInfo",visiblePos+"---visiblePos");
		View view = listView.getChildAt(index - visiblePos);
		ViewHolder holder = (ViewHolder) view.getTag();
		Log.i("imageInfo",index+"---index");
		holder.item_info_point.setVisibility(View.INVISIBLE);
	}
	
	
	static class ViewHolder{
		TextView item_info_time;
		TextView item_info_tv;
		TextView item_info_info;
		ImageView item_info_point;
	}
}
