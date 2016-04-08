package com.tuanche.askforanswer.source.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tuanche.api.utils.DensityUtil;
import com.tuanche.api.utils.RotateShowProgressDialog;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.app.utils.Utils;
import com.tuanche.askforanswer.source.adapter.SelectCityAdapter;
import com.tuanche.askforanswer.source.bean.EachCity;
import com.tuanche.askforanswer.source.bean.SelectCity;
import com.tuanche.askforanswer.source.bean.SortModel;
import com.tuanche.askforanswer.source.view.CharacterParser;
import com.tuanche.askforanswer.source.view.PinnedHeaderListView;
import com.tuanche.askforanswer.source.view.PinyinComparator;
import com.tuanche.askforanswer.source.view.SideBar;
import com.tuanche.askforanswer.source.view.SideBar.OnTouchingLetterChangedListener;
/**
 * 选择城市
 * @author zpf
 *
 */
public class SelectCityActivity extends BaseActivity implements OnClickListener, ApiRequestListener{

	public final static String CITY_NAME = "select_city_name";
	public final static String CITY_ID = "select_city_ID";
	private View goBack;
	
	private PinnedHeaderListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SelectCityAdapter adapter;

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
	
	private List<EachCity>  mAllCity = new ArrayList<EachCity>();

	
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_city);
		
		new AppApi().CITYAll(mContext, this);
		RotateShowProgressDialog.ShowProgressOn(mContext, false);
		linearLayout = (LinearLayout) findViewById(R.id.lay_view_slidr_city);
	}
	
	
	private void initViews(List<EachCity> cityList , List<String> letters){
		
		goBack = findViewById(R.id.go_back);
		goBack.setOnClickListener(this);
		
		
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		
		String[] abc = new String[letters.size()];
		for(int i=0; i<letters.size(); i++){
			abc[i] = letters.get(i);
		}
		
		
	    sideBar = new SideBar(mContext, abc);
	    /**
	     * 动态设置匡高
	     */
	    float length=abc.length;
	    float xiaoshu=length/24f;
	    int height=(int) (xiaoshu*Utils.getHeight(mContext)-Utils.getStatusBarHeight(mContext)-getResources().getDimensionPixelSize(R.dimen.top_bar_height));
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(DensityUtil.dpToPx(mContext, 30),height);
		sideBar.setLayoutParams(params);
	    linearLayout.addView(sideBar);
		dialog = (TextView) findViewById(R.id.dialog);
	    sideBar.setTextView(dialog);
		
		sortListView = (PinnedHeaderListView) findViewById(R.id.city_lvcountry);
		
	
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
					
					@Override
					public void onTouchingLetterChanged(String s) {
						
						int position = adapter.getPositionForSection(s.charAt(0));
						Log.e("eeeeeeeeeeeeeee", ""+position);
						if(position != -1){
							sortListView.setSelection(position);
						}
					}
				});
				
				sortListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						
						Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(SelectCityActivity.this,RegisterActivity2.class);
						intent.putExtra(CITY_NAME, ((SortModel)adapter.getItem(position)).getCity_code());
						intent.putExtra(CITY_ID, ((SortModel)adapter.getItem(position)).getName());
						setResult(RegisterActivity2.RESPOSE_SELECT_CITY_CODE, intent);
						finish();
					}
				});
				
//				SourceDateList = filledData(getResources().getStringArray(R.array.date));
				
				SourceDateList = filledData(cityList);
				
				sideBar.setVisibility(View.VISIBLE);
				
				Collections.sort(SourceDateList, pinyinComparator);
				adapter = new SelectCityAdapter(this, SourceDateList);
				sortListView.setAdapter(adapter);
				sortListView.setOnScrollListener(adapter);
				sortListView
					.setPinnedHeaderView(this.getLayoutInflater()
							.inflate(R.layout.listview_city_item_header, sortListView, false));
				
				
		
	}
	
	private List<SortModel> filledData(List<EachCity> cityList){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<cityList.size(); i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(cityList.get(i).getCity_name());
			sortModel.setCity_code(cityList.get(i).getCity_code());
			
			
			String pinyin = characterParser.getSelling(cityList.get(i).getCity_name());
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.go_back:
			finish();
			break;

		default:
			break;
		}
	}
	
	@Override
	public void getViews() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setViews() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub

	}


	@Override
	public void onSuccess(Action method, Object obj) {
		// TODO Auto-generated method stub
		switch (method) {
		case SECLECTCITY_NOSIGN:
			SelectCity city = (SelectCity)obj;
			Log.e("城市解析成功。。。。。。", city.toString()+"");
			List<List<EachCity>> cityList = city.getResult().getCityinfo();
			for(List<EachCity> eachList : cityList){
				mAllCity.addAll(eachList);
			}
			List<String> letters = city.getResult().getLetters();
			initViews(mAllCity,letters);
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
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
		
		case SECLECTCITY_NOSIGN:
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
