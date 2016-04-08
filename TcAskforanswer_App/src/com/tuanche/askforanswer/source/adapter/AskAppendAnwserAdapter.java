package com.tuanche.askforanswer.source.adapter;

import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.source.bean.HomeListInfoBean.HomeInfoQuestionBean;
import com.tuanche.askforanswer.source.view.CircularImage;

public class AskAppendAnwserAdapter extends MyBaseAdapter{
	private List<HomeInfoQuestionBean> asks; 
	private LayoutInflater inflater;
	private Resources res;
	private LinkedHashMap<String, String> lables=new LinkedHashMap<String, String>();
	private Drawable img_off;
	//private PictureUtils utils;
	public AskAppendAnwserAdapter(Context context,List<HomeInfoQuestionBean> asks){
		super(context);
		this.asks=asks;
		res=context.getResources();
		inflater=LayoutInflater.from(context);
		img_off = res.getDrawable(R.drawable.label);
		//调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		img_off.setBounds(0, 0, res.getDimensionPixelSize(R.dimen.textsize1), res.getDimensionPixelSize(R.dimen.textsize1));
	//	this.utils=utils;
	}
	
	public List<HomeInfoQuestionBean> getAsks() {
		return asks;
	}

	public void setAsks(List<HomeInfoQuestionBean> asks) {
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
		HomeInfoQuestionBean bean= asks.get(arg0);
		if(null==convertView){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.listview_answer_append_item, null);
			holder.itme_circle_iv=(CircularImage) convertView.findViewById(R.id.itme_circle_iv);
			holder.item_ask_tv=(TextView) convertView.findViewById(R.id.item_ask_tv);
			holder.item_ask_time=(TextView) convertView.findViewById(R.id.item_ask_time);
			holder.item_adopt_view=(ImageView) convertView.findViewById(R.id.item_adopt_view);
			holder.item_info=(TextView) convertView.findViewById(R.id.item_info);
			holder.item_review=(TextView) convertView.findViewById(R.id.item_review);
			holder.item_tag=(TextView) convertView.findViewById(R.id.item_tag);
	
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		if(bean.getOffStatus()==1){
			holder.item_adopt_view.setVisibility(View.VISIBLE);
		}else{
			holder.item_adopt_view.setVisibility(View.GONE);
		}
		holder.item_ask_time.setText(bean.getAskDate());
		holder.item_info.setText(bean.getContent());
		holder.item_ask_tv.setText(bean.getNickName());
		holder.item_review.setText(String.format(res.getString(R.string.item_review),bean.getReplyNum()));
		holder.item_tag.setCompoundDrawables(img_off, null, null, null);
		holder.item_tag.setText(bean.getTag().replace(",","  "));
		pictureUtils.display(holder.itme_circle_iv, bean.getIcon(),config);
		return convertView;
	}

	public LinkedHashMap<String, String> getLables() {
		return lables;
	}

	public void setLables(LinkedHashMap<String, String> lables) {
		this.lables = lables;
	}

	static class ViewHolder{
		CircularImage itme_circle_iv;
		TextView item_ask_tv;
		TextView item_ask_time;
		ImageView item_adopt_view;
		TextView item_review;
		TextView item_tag;
		TextView item_info;
	}
	
	String getLable(String key){
		String[] arr=key.split(",");
		key="";
		for (int i = 0; i < arr.length; i++) {
			key+=lables.get(arr[i])+" ";
		}
		return key;
	}
}
