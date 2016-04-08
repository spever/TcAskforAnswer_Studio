package com.tuanche.askforanswer.source.dialog;

import java.lang.reflect.Field;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

import com.tuanche.askforanswer.R;

public class TcDatePickerDialog extends AlertDialog implements OnClickListener, OnDateChangedListener {

	private static final String START_YEAR = "start_year";
	private static final String START_MONTH = "start_month";
	private static final String START_DAY = "start_day";

	private final DatePicker mDatePicker_start;
	private final OnDateSetListener mCallBack;
	private int startOrEnd=1;
	public interface OnDateSetListener {
		void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth,int starOrend);
	}

	public TcDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth,int starOrend) {
		this(context, 0, callBack, year, monthOfYear, dayOfMonth,starOrend);
	}

	public TcDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth,int starOrend) {
		this(context, 0, callBack, year, monthOfYear, dayOfMonth, true,starOrend);
	}

	public TcDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth, boolean isDayVisible,int starOrend) {
		super(context, theme);
		startOrEnd=starOrend;
		mCallBack = callBack;
		Context themeContext = getContext();
		setButton(BUTTON_POSITIVE, "确定", this);
		setButton(BUTTON_NEGATIVE, "取消", this);
		// setButton(BUTTON_POSITIVE,
		// themeContext.getText(android.R.string.date_time_done), this);
		setIcon(0);

		LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.date_picker_dialog, null);
		setView(view);
		
		mDatePicker_start = (DatePicker) view.findViewById(R.id.datePickerStart);
		// mDatePicker_end = (DatePicker) view.findViewById(R.id.datePickerEnd);
		mDatePicker_start.init(year, monthOfYear, dayOfMonth, this);
		TextView tv=(TextView) view.findViewById(R.id.tv_date);
		if(starOrend==1){
			tv.setText("开始时间");
		}else{
			tv.setText("结束时间");
		}
		// mDatePicker_end.init(year, monthOfYear, dayOfMonth, this);
		if (!isDayVisible) {
			hidDay(mDatePicker_start);
			// hidDay(mDatePicker_end);
		}
	}

	private void hidDay(DatePicker mDatePicker) {
		Field[] datePickerfFields = mDatePicker.getClass().getDeclaredFields();
		for (Field datePickerField : datePickerfFields) {
			if ("mDaySpinner".equals(datePickerField.getName())) {
				datePickerField.setAccessible(true);
				Object dayPicker = new Object();
				try {
					dayPicker = datePickerField.get(mDatePicker);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				// datePicker.getCalendarView().setVisibility(View.GONE);
				((View) dayPicker).setVisibility(View.GONE);
			}
		}
	}

	public void onClick(DialogInterface dialog, int which) {
		// Log.d(this.getClass().getSimpleName(), String.format("which:%d",
		// which));
		// ����ǡ�ȡ ��ť���򷵻أ�����ǡ�ȷ ������ť��������ִ��
		if (which == BUTTON_POSITIVE)
			tryNotifyDateSet();
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		if (view.getId() == R.id.datePickerStart)
			mDatePicker_start.init(year, month, day, this);
		// if (view.getId() == R.id.datePickerEnd)
		// mDatePicker_end.init(year, month, day, this);
		// updateTitle(year, month, day);
	}

	public DatePicker getDatePickerStart() {
		return mDatePicker_start;
	}

	public void updateStartDate(int year, int monthOfYear, int dayOfMonth) {
		mDatePicker_start.updateDate(year, monthOfYear, dayOfMonth);
	}

	private void tryNotifyDateSet() {
		if (mCallBack != null) {
			mDatePicker_start.clearFocus();
			// mDatePicker_end.clearFocus();
			mCallBack.onDateSet(mDatePicker_start, mDatePicker_start.getYear(), mDatePicker_start.getMonth(), mDatePicker_start.getDayOfMonth(),startOrEnd);
		}
	}

	@Override
	protected void onStop() {
		// tryNotifyDateSet();
		super.onStop();
	}

	@Override
	public Bundle onSaveInstanceState() {
		Bundle state = super.onSaveInstanceState();
		state.putInt(START_YEAR, mDatePicker_start.getYear());
		state.putInt(START_MONTH, mDatePicker_start.getMonth());
		state.putInt(START_DAY, mDatePicker_start.getDayOfMonth());
		// state.putInt(END_YEAR, mDatePicker_end.getYear());
		// state.putInt(END_MONTH, mDatePicker_end.getMonth());
		// state.putInt(END_DAY, mDatePicker_end.getDayOfMonth());
		return state;
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		int start_year = savedInstanceState.getInt(START_YEAR);
		int start_month = savedInstanceState.getInt(START_MONTH);
		int start_day = savedInstanceState.getInt(START_DAY);
		mDatePicker_start.init(start_year, start_month, start_day, this);
		// int end_year = savedInstanceState.getInt(END_YEAR);
		// int end_month = savedInstanceState.getInt(END_MONTH);
		// int end_day = savedInstanceState.getInt(END_DAY);
		// mDatePicker_end.init(end_year, end_month, end_day, this);
	}
	
	public void setMaxValue(long maxDate){
		if(mDatePicker_start!=null){
			mDatePicker_start.setMaxDate(maxDate);
		}
	}

	public void setMinValue(long minDate){
		if(mDatePicker_start!=null){
			mDatePicker_start.setMinDate(minDate);
		}
	}
	
}
