package com.tuanche.askforanswer.source.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.utils.PictureUtils;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.source.bean.SortModel;
import com.tuanche.askforanswer.source.view.PinnedHeaderListView;
import com.tuanche.askforanswer.source.view.PinnedHeaderListView.PinnedHeaderAdapter;


public class SelectTypeAdapter extends BaseAdapter implements PinnedHeaderAdapter,SectionIndexer,OnScrollListener{
	private List<SortModel> list = null;
	private Context mContext;
	private int lastItem = 0;
	public int back = 3;
	public int firstItem;
	public ListView listView;
	public Map<String, Boolean> maps; 
	private PictureUtils pictureUtils;
	
	
	private int isCount = 0;
	
	public SelectTypeAdapter(Context mContext, List<SortModel> list, ListView listView , PictureUtils pictureUtils) {
		this.mContext = mContext;
		this.list = list;
		this.listView = listView;
		this.pictureUtils = pictureUtils;
		maps = new HashMap<String, Boolean>();
		for(int index=0; index<list.size(); index++){
			maps.put(index+"" , false);
		}
	}
	
	/**
	 * @param list
	 */
	public void updateListView(List<SortModel> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.listview_type_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.type_title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.type_catalog);
			viewHolder.image = (ImageView) view.findViewById(R.id.car_type_image);
			viewHolder.image_select = (ImageView) view.findViewById(R.id.right_image);
			
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		int section = getSectionForPosition(position);
		
		//
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		
		if(maps.get(position+"")){
			viewHolder.image_select.setImageResource(R.drawable.choose_right_p);
		}else{
			viewHolder.image_select.setImageResource(R.drawable.choose_right);
		}
		
		viewHolder.tvTitle.setText(this.list.get(position).getName());
		pictureUtils.display(viewHolder.image, this.list.get(position).getLogo());
		
		return view;

	}
	


	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		ImageView image, image_select;
		View line;
	}


	/**
	 * ����ListView�ĵ�ǰλ�û�ȡ���������ĸ��Char asciiֵ
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * ���ݷ��������ĸ��Char asciiֵ��ȡ���һ�γ��ָ�����ĸ��λ��
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * ��ȡӢ�ĵ�����ĸ����Ӣ����ĸ��#���档
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	
	
	@Override
	public int getPinnedHeaderState(int position) {
		// TODO Auto-generated method stub
		return back;
	}

	@Override
	public void configurePinnedHeader(View header, int position) {
		// TODO Auto-generated method stub
		if (lastItem != position) {
			notifyDataSetChanged();
		}
		         // 这个地方 需要注意
		((TextView) header.findViewById(R.id.type_catalog)).setText(list.get(
				position).getSortLetters());
		
		lastItem = position;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		firstItem = firstVisibleItem;
		if(view instanceof PinnedHeaderListView){
			((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
		}
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(int index){
		int visiblePos = listView.getFirstVisiblePosition();
		Log.i("imageInfo",visiblePos+"---visiblePos");
		View view = listView.getChildAt(index - visiblePos);
		ViewHolder holder = (ViewHolder) view.getTag();
		
		Log.i("imageInfo",index+"---index");
		if(maps.get(index+"")){
			maps.put(index+"", false);
			isCount --;
			holder.image_select.setImageResource(R.drawable.choose_right);

		}else{
			if(isCount < 5){
				maps.put(index+"", true);
				isCount ++;
				holder.image_select.setImageResource(R.drawable.choose_right_p);
			}else{
					ToastUtil.showToast(mContext, "最多可选择5个品牌");
			}
		}
	}
}