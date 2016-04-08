package com.tuanche.askforanswer.source.adapter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.tuanche.askforanswer.R;

/**
 * 头部六个问题种类
 * 
 * @author Administrator
 * 
 */
public class AskKindsAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	public int index = 100;
	private GridView gridView;
	private LinkedHashMap<String, String> lables = new LinkedHashMap<String, String>();
	public int lastindex = 100;
	private Map<String, Boolean> map;

	public LinkedHashMap<String, String> getLables() {
		return lables;
	}

	public void setLables(LinkedHashMap<String, String> lables) {
		this.lables = lables;
	}

	public AskKindsAdapter(Context context, LinkedHashMap<String, String> lables, GridView view) {
		this.context = context;
		this.lables = lables;
		this.gridView = view;
		inflater = LayoutInflater.from(context);
		map=new HashMap<String,Boolean>();
		for (int i = 0; i < lables.size(); i++) {
			map.put(i + "", false);
		}
	}

	@Override
	public int getCount() {
		return lables.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lables.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.gridview_header_item, null);
			holder.item_ask_tv = (TextView) convertView.findViewById(R.id.item_ask_kinds);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.item_ask_tv.setText(lables.get(arg0 + 1 + ""));

		return convertView;
	}

	public void updateView(int index) {
		int visiblePos = gridView.getFirstVisiblePosition();
		Log.i("imageInfo", visiblePos + "---visiblePos");
		View view = gridView.getChildAt(index - visiblePos);
		ViewHolder holder = (ViewHolder) view.getTag();
		Log.i("imageInfo", index + "---index");

		if (index != lastindex) {
			holder.item_ask_tv.setEnabled(false);
			holder.item_ask_tv.setTextColor(context.getResources().getColor(R.color.textcolor3));
			map.put(index + "", true);
			if (lastindex < lables.size() && index != lastindex) {
				View lastviView = gridView.getChildAt(lastindex - visiblePos);
				ViewHolder holder1 = (ViewHolder) lastviView.getTag();
				holder1.item_ask_tv.setEnabled(true);
				holder1.item_ask_tv.setTextColor(context.getResources().getColor(R.color.textcolor2));
				map.put(lastindex + "", false);
			}
			lastindex = index;
		}else{
			if(map.get(index+"")){
				holder.item_ask_tv.setEnabled(true);
				holder.item_ask_tv.setTextColor(context.getResources().getColor(R.color.textcolor2));
				map.put(index+"", false);
				lastindex=100;
			}
		}
	}

	public Map<String, Boolean> getMap() {
		return map;
	}

	public void setMap(Map<String, Boolean> map) {
		this.map = map;
	}

	static class ViewHolder {
		TextView item_ask_tv;
	}
}
