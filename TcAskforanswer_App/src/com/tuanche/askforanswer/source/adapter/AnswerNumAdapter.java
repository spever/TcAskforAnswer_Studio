package com.tuanche.askforanswer.source.adapter;

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
import com.tuanche.askforanswer.source.bean.MqaResultBean.QuestList;
import com.tuanche.askforanswer.source.view.CircularImage;

public class AnswerNumAdapter extends MyBaseAdapter{
	private List<QuestList> asks; 
	private LayoutInflater inflater;
	private Resources res;
	private Drawable img_off;
//	private PictureUtils utils;
	public AnswerNumAdapter(Context context,List<QuestList> lists){
		super(context);
		this.asks=lists;
		inflater=LayoutInflater.from(context);
		res=context.getResources();
		img_off = res.getDrawable(R.drawable.label);
		//调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		img_off.setBounds(0, 0, res.getDimensionPixelSize(R.dimen.textsize1), res.getDimensionPixelSize(R.dimen.textsize1));
//		this.utils=utils;
	}
	public List<QuestList> getAsks() {
		return asks;
	}
	public void setAsks(List<QuestList> asks) {
		this.asks = asks;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return asks.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		QuestList bean=asks.get(arg0);
		if(null==convertView){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.listview_answer_mine_item, null);
			holder.itme_circle_iv=(CircularImage) convertView.findViewById(R.id.itme_circle_iv);
			holder.item_ask_tv=(TextView) convertView.findViewById(R.id.item_ask_tv);
			holder.item_ask_time=(TextView) convertView.findViewById(R.id.item_ask_time);
			holder.item_adopt_view=(ImageView) convertView.findViewById(R.id.item_adopt_view);
			holder.item_review=(TextView) convertView.findViewById(R.id.item_review);
			holder.item_info=(TextView) convertView.findViewById(R.id.item_info);
			holder.item_tag=(TextView) convertView.findViewById(R.id.item_tag);

			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		holder.item_ask_time.setText(bean.getAskDate());
		holder.item_ask_tv.setText(bean.getNickName());
		holder.item_info.setText(bean.getContent());
		holder.item_tag.setCompoundDrawables(img_off, null, null, null);

		holder.item_tag.setText(bean.getTag().replace(","," "));
		holder.item_review.setText(String.format(res.getString(R.string.item_review),bean.getReplyNum()));
		pictureUtils.display(holder.itme_circle_iv, bean.getIcon(),config);
		//采纳状态
		if(bean.getOffStatus()==0){
			holder.item_adopt_view.setVisibility(View.GONE);;
		}else{
			holder.item_adopt_view.setVisibility(View.VISIBLE);;
		}
		
		return convertView;
	}

	static class ViewHolder{
		CircularImage itme_circle_iv;
		TextView item_ask_tv;
		TextView item_ask_time;
		ImageView item_adopt_view;
		TextView item_tag;
		TextView item_info;
		TextView item_review;

	}
}
