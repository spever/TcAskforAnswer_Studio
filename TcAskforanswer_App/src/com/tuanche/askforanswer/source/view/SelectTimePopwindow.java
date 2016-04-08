package com.tuanche.askforanswer.source.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TimePicker;

import com.tuanche.askforanswer.R;
/**
 * 
 * @author zpf
 *
 */
public class SelectTimePopwindow extends PopupWindow {

	public  TimePicker mTimePickerStart;
	public  TimePicker mTimePickerEnd;
	
	private View  btn_cancel,btn_sure;
	private View mMenuView;
	
	public String mStart_hour;
	public String mStart_minute;
	
	public String mEnd_hour;
	public String mEnd_minute;
	

	public SelectTimePopwindow(Activity context,OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.select_time_popwindow, null);
		btn_cancel =  mMenuView.findViewById(R.id.btn_cancel);
		btn_sure = mMenuView.findViewById(R.id.btn_sure);
		mTimePickerStart = (TimePicker) mMenuView.findViewById(R.id.select_time_start);
		mTimePickerStart.setIs24HourView(true);
		mTimePickerEnd = (TimePicker) mMenuView.findViewById(R.id.select_time_end);
		mTimePickerEnd.setIs24HourView(true);
		
		
		
		//取消按钮
		btn_cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//销毁弹出框
				dismiss();
			}
		});
		
		//设置按钮监听
		btn_sure.setOnClickListener(itemsOnClick);
		
		
		//设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}				
				return true;
			}
		});

	}

	
	
}
