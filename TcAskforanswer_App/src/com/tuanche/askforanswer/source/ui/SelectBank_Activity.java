package com.tuanche.askforanswer.source.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.tuanche.askforanswer.source.bean.BankCityBean;
import com.tuanche.askforanswer.source.bean.BankCityProvince;
import com.tuanche.askforanswer.source.bean.SortModel;
import com.tuanche.askforanswer.source.view.CharacterParser;
import com.tuanche.askforanswer.source.view.PinnedHeaderListView;
import com.tuanche.askforanswer.source.view.PinyinComparator;
import com.tuanche.askforanswer.source.view.SideBar;
import com.tuanche.askforanswer.source.view.SideBar.OnTouchingLetterChangedListener;
/**
 * 选择银行卡开通的省份
 * @author zpf
 *
 */
public class SelectBank_Activity extends BaseActivity implements OnClickListener, ApiRequestListener{

	public final static String PROVINCE_NAME = "select_province_name";
	public final static String PROVINCE_CODE = "select_province_code";
	private View goBack;
	
	private PinnedHeaderListView sortListView;
	private SideBar sideBar;
	private TextView dialog; 
	private SelectCityAdapter adapter;      // 与技师注册中的选择城市 共用一个adapter。 其他数据则不同

	
	
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
		
	
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_city2);
		
		
		new AppApi().BANK_PRO_INFO(mContext, this);
		RotateShowProgressDialog.ShowProgressOn(mContext, false);
		linearLayout = (LinearLayout) findViewById(R.id.lay_view_slidr_city);
		
	}
	
	
	private void initViews(List<BankCityProvince> listProvince, List<String> letters){
		
		goBack = findViewById(R.id.go_back);
		goBack.setOnClickListener(this);
		
		
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		
		String[] abc = new String[letters.size()];
		for(int i=0; i<letters.size(); i++){
			if(letters.get(i).matches("[a-z]")){
				abc[i] = letters.get(i).toUpperCase();
			}else{
				abc[i] = letters.get(i);
			}
			
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
		
		SourceDateList = filledData(listProvince);
		
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SelectCityAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);
		sortListView.setOnScrollListener(adapter);
		sortListView
			.setPinnedHeaderView(this.getLayoutInflater()
					.inflate(R.layout.listview_city_item_header, sortListView, false));		

		
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
						
						Intent intent = new Intent(SelectBank_Activity.this,BankAboutCity.class);
						intent.putExtra(PROVINCE_NAME, ((SortModel)adapter.getItem(position)).getName());
						intent.putExtra(PROVINCE_CODE, ((SortModel)adapter.getItem(position)).getProvince_code());
						startActivity(intent);
						finish();
					}
				});
				
	}
	
	private List<SortModel> filledData(List<BankCityProvince> listProvince){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<listProvince.size(); i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(listProvince.get(i).getProvince_name());
			sortModel.setProvince_code(listProvince.get(i).getProvince_code());
			
			String pinyin = characterParser.getSelling(listProvince.get(i).getProvince_name());
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
		case PROVINCE_NOSIGN:
			BankCityBean bean = (BankCityBean) obj;
			List<List<BankCityProvince>> provinceAll = bean.getResult().getProvince();
			List<String> letters = bean.getResult().getLetter();
			allProvince(provinceAll,letters);
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
//			Log.i("我的省份---------------", bean.toString());
			break;
		}
	}

	private void allProvince(List<List<BankCityProvince>> provinceAll, List<String> letters){
		List<BankCityProvince> listProvince = new ArrayList<BankCityProvince>();
		for(List<BankCityProvince> li : provinceAll){
			listProvince.addAll(li);
		}
		initViews(listProvince,letters);
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
		case PROVINCE_NOSIGN:
			if(statusCode instanceof ResponseErrorMessage){
				//后台返回了错误码和错误信息
				ToastUtil.showToast(mContext, ((ResponseErrorMessage)statusCode).getMsg()+"" );
			} else if(statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode) ){
				//网络超时
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_timeout));
			}
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			break;
		}
	}

	
}
