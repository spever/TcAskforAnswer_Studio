package com.tuanche.askforanswer.source.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuanche.api.utils.DensityUtil;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.utils.RotateShowProgressDialog;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.app.utils.Utils;
import com.tuanche.askforanswer.source.adapter.SelectTypeAdapter;
import com.tuanche.askforanswer.source.bean.Car_Type;
import com.tuanche.askforanswer.source.bean.Car_Type_All;
import com.tuanche.askforanswer.source.bean.SortModel;
import com.tuanche.askforanswer.source.view.CharacterParser;
import com.tuanche.askforanswer.source.view.PinnedHeaderListView;
import com.tuanche.askforanswer.source.view.PinyinComparator;
import com.tuanche.askforanswer.source.view.SideBar;
import com.tuanche.askforanswer.source.view.SideBar.OnTouchingLetterChangedListener;
/**
 * 选择擅长品牌
 * @author zpf
 *
 */
public class SelectTypeActivity extends BaseActivity implements OnClickListener,ApiRequestListener{

	public static final String CAR_TYPE_ID = "car_type";
	public static final String CAR_TYPE_NAME = "car_name";
	
	private Context mContext;
	private View goBack;
	
	private View btnSure;
	
	private PinnedHeaderListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SelectTypeAdapter adapter;

	private LinearLayout linearLayout;
	
	
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	
	/**
	 * 代指 ：已选择  擅长品牌的 下标
	 */
	private HashSet<Integer> idSelect = new HashSet<Integer>();  
	
	private List<String> letters;
	
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_select_type);
			
			mContext = this;
			
			getViews();
			setViews();
			setListeners();
			
		}
	
	private List<SortModel> filledData(List<Car_Type> carAllList){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<carAllList.size(); i++){
			
			SortModel sortModel = new SortModel();
			sortModel.setName(carAllList.get(i).getName());
			sortModel.setId(carAllList.get(i).getId());
			sortModel.setLogo(carAllList.get(i).getLogo());
			
			String pinyin = characterParser.getSelling(carAllList.get(i).getName());
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			mSortList.add(sortModel);
		}	
		return mSortList;
		
	}
	@Override
	public void getViews() {
		// TODO Auto-generated method stub
		
		linearLayout = (LinearLayout)findViewById(R.id.lay_view_slidr);
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void setViews() {
		// TODO Auto-generated method stub
		
		new AppApi().CarTypeAll(mContext,  this);
		RotateShowProgressDialog.ShowProgressOn(mContext, false);
	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub

	}

	private void initViews(List<Car_Type> carAllList,List<String> letters){
		goBack = findViewById(R.id.go_back);
		btnSure =  findViewById(R.id.btn_sure);
		goBack.setOnClickListener(this);
		btnSure.setOnClickListener(this);
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		
		
		
		dialog = (TextView) findViewById(R.id.dialog);
		
	    String[] abc = new String[letters.size()];
		for(int i=0; i<letters.size(); i++){
			abc[i] = letters.get(i);
		}
		
		sideBar = new SideBar(mContext,abc);
		/**
	     * 动态设置匡高
	     */
	    float length=abc.length;
	    float xiaoshu=length/24f;
	    int height=(int) (xiaoshu*Utils.getHeight(mContext)-Utils.getStatusBarHeight(mContext)-DensityUtil.dpToPx(mContext, 40)-getResources().getDimensionPixelSize(R.dimen.top_bar_height));
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(DensityUtil.dpToPx(mContext, 30),height);
		sideBar.setLayoutParams(params);
		linearLayout.addView(sideBar);
		sideBar.setTextView(dialog);
	    
	    
		sortListView = (PinnedHeaderListView) findViewById(R.id.city_lvcountry);
		
	
		SourceDateList = filledData(carAllList);    //数据源
		
		
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SelectTypeAdapter(this, SourceDateList , sortListView , pictureUtils);
		sortListView.setAdapter(adapter);
		sortListView.setOnScrollListener(adapter);
		sortListView
			.setPinnedHeaderView(this.getLayoutInflater()
					.inflate(R.layout.listview_type_item_header, sortListView, false));
		
		
		
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
					
					@Override
					public void onTouchingLetterChanged(String s) {
						
						int position = adapter.getPositionForSection(s.charAt(0));
						if(position != -1){
							sortListView.setSelection(position);
						}
					}
				});
				
		sortListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						
						adapter.update(position);
						Map<String, Boolean> maps = adapter.maps;
						Set<String> set= maps.keySet();
						for(String str : set){
							if(maps.get(str)){
								idSelect.add(Integer.valueOf(str));
							}
						}
					}
				});
				
			
				
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.go_back:
			finish();
			break;
		case R.id.btn_sure: //把数据  带回去
			StringBuilder builder = new StringBuilder();
			StringBuilder builderName = new StringBuilder();
			if(idSelect.size() != 0){
				for(int in : idSelect){
					builder.append(SourceDateList.get(in).getId()+",");
					builderName.append(SourceDateList.get(in).getName()+",");
				}
				String typeId0 = builder.toString();
				String typeName0 = builderName.toString();
				int i = typeId0.lastIndexOf(",");
				int j = typeName0.lastIndexOf(",");
				
				
				String typeIds = typeId0.substring(0, i);
				String typeNames = typeName0.substring(0, j);
				
				Intent intent = new Intent(SelectTypeActivity.this,RegisterActivity2.class);
				
				intent.putExtra(CAR_TYPE_ID, typeIds);
				intent.putExtra(CAR_TYPE_NAME, typeNames);
				
				setResult(RegisterActivity2.RESPOSE_SELECT_CAR_TYPE, intent);
				
				finish();
				
			}else{
				ToastUtil.showToast(mContext, "请选择擅长品牌");
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onSuccess(Action method, Object obj) {
		// TODO Auto-generated method stub
		switch (method) {
		case CARTYPE_NOSIGN:
			Car_Type_All bean = (Car_Type_All) obj;
			List<Car_Type> carAllList = new ArrayList<Car_Type>();
			List<List<Car_Type>> car_list = bean.getResult().getCarinfo();
			for(List<Car_Type> list : car_list){
				carAllList.addAll(list);
			}
			
			letters = bean.getResult().getLetters();
			
			if(carAllList.size() > 0){
				initViews(carAllList,letters);
			}
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			LogUtils.i("111111111---------------"+bean.toString());
			break;
		default:
			break;
		}
	}

	@Override
	public void onError(Action method, Object statusCode) {
		// TODO Auto-generated method stub
		switch (method) {
		case NETWORK_FAILED:
			if(AppApi.ERROR_NETWORK_FAILED.equals(statusCode)){
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_no));
			}
			break;
		case CARTYPE_NOSIGN:
			if(statusCode instanceof ResponseErrorMessage){
				//后台返回了错误码和错误信息
			} else if(statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode) ){
				//网络超时
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_timeout));
			}
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			break;
		default:
			break;
		}
	}

}
