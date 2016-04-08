package com.tuanche.askforanswer.source.ui;

import java.util.HashSet;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.utils.RotateShowProgressDialog;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.utils.CheckUtil;
import com.tuanche.askforanswer.app.utils.MySharedPreferences;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.source.adapter.CarTagsAdapter;
import com.tuanche.askforanswer.source.bean.EachTag;
import com.tuanche.askforanswer.source.bean.LoginOrRegist;
import com.tuanche.askforanswer.source.bean.TagsOfCar;
import com.tuanche.askforanswer.source.bean.UserBean;
/**
 * 
 * @author zpf
 *
 */
public class RegisterActivity2 extends BaseActivity implements ApiRequestListener,OnClickListener{

	public static final int REQUEST_SELECT_CITY_CODE = 100;
	public static final int RESPOSE_SELECT_CITY_CODE = 101;
	
	public static final int REQUEST_SELECT_CAR_TYPE = 200;
	public static final int RESPOSE_SELECT_CAR_TYPE = 201;
	
	
	private Context mContext;
	
	private String selectCity;  // 已选城市      （所接收的 是  城市ID ）
	private String selectType;  // 已选品牌
	
	
	private HashSet<Integer> tagSelectEd = new HashSet<Integer>();
	
	private ListView mListView;
	/**
	 * 技师   擅长的方向    列表
	 */
	private List<EachTag> mTagList;
	private CarTagsAdapter tagsAdapter;
	
	private View goBack;
	private View btnSubmit;
	private EditText userName;
	
	private TextView cityShow;
	private TextView carTypeShow;
	
	/**
	 * 城市
	 */
	private View mSelectCity;
	/**
	 * 品牌
	 */
	private View mSelectType;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register2);
		
		mContext = this;
		
		getViews();
		setViews();
		setListeners();
		
	}
	
	
	
	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.go_back:
			this.finish();
			break;
		case R.id.select_city:
			Intent intent = new Intent(RegisterActivity2.this,SelectCityActivity.class);
			startActivityForResult(intent, REQUEST_SELECT_CITY_CODE);
			
			break;
		case R.id.select_type:
			Intent intent2 = new Intent(RegisterActivity2.this,SelectTypeActivity.class);
			startActivityForResult(intent2, REQUEST_SELECT_CAR_TYPE);
			break;
		case R.id.btnNext2:{
			
			String userId = MySharedPreferences.getValueByKey(mContext, MySharedPreferences.USER_ID);
			String userPhone = MySharedPreferences.getValueByKey(mContext, MySharedPreferences.USER_PHONE);
			
			String nick = userName.getText().toString().trim();
			if(!CheckUtil.isEmpty(nick)){
				if(!CheckUtil.isEmpty(selectCity)){
					if(!CheckUtil.isEmpty(selectType)){
						String strTAG = getTags();
						if(!CheckUtil.isEmpty(strTAG)){
							new AppApi().CompleteRegister(mContext, userId, selectType, selectCity, userPhone, strTAG, nick, this);
							RotateShowProgressDialog.ShowProgressOn(mContext, false);
						}else{
							ToastUtil.showToast(mContext, "请选择品牌");
						}
					}else{
						ToastUtil.showToast(mContext, "请选择品牌");
					}
				}else{
					ToastUtil.showToast(mContext, "请选择城市");
				}
			}else{
				ToastUtil.showToast(mContext, "请输入昵称");
			}
			}
		break;
		default:
			break;
		}
	}
	
	private String getTags(){
		StringBuilder builder = new StringBuilder();
		String typeTags = null;
		if(tagSelectEd.size()>0){
			for(int in : tagSelectEd){
				builder.append(mTagList.get(in).getId()+",");
			}
			String typeTags0 = builder.toString();
			int i = typeTags0.lastIndexOf(",");
			typeTags = typeTags0.substring(0, i);
		}
		
		return typeTags;
		
	}
	
	@Override
	public void getViews() {
		// TODO Auto-generated method stub
		goBack = findViewById(R.id.go_back);
		mSelectCity = findViewById(R.id.select_city);
		mSelectType = findViewById(R.id.select_type);
		mListView = (ListView) findViewById(R.id.car_tag_select);
		btnSubmit = findViewById(R.id.btnNext2);
		userName = (EditText) findViewById(R.id.master_user_name);
		
		cityShow = (TextView) findViewById(R.id.city_name);
		carTypeShow = (TextView) findViewById(R.id.car_type_names);
		
		mSelectCity.setFocusable(true);
	}

	@SuppressWarnings("static-access")
	@Override
	public void setViews() {
		// TODO Auto-generated method stub
		goBack.setOnClickListener(this);
		mSelectCity.setOnClickListener(this);
		mSelectType.setOnClickListener(this);
		new AppApi().TagsCarAll(mContext, null, null, null, this);
		RotateShowProgressDialog.ShowProgressOn(mContext, false);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ImageView imageView = (ImageView) arg1.findViewById(R.id.select_image);
				if(tagSelectEd.contains(arg2)){
					imageView.setImageResource(R.drawable.choose_right);
					tagSelectEd.remove(arg2);
				}else{
					imageView.setImageResource(R.drawable.choose_right_p);
					tagSelectEd.add(arg2);
				}
				
				
			}
		});
	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		btnSubmit.setOnClickListener(this);
	}



	@Override
	public void onSuccess(Action method, Object obj) {
		// TODO Auto-generated method stub
		switch (method) {
		case TAG_NOSIGN:
			TagsOfCar bean = (TagsOfCar) obj;
			mTagList = bean.getResult().getTags();
			tagsAdapter = new CarTagsAdapter(mContext, mTagList);
			mListView.setAdapter(tagsAdapter);
			LogUtils.i("技师擅长方向-----------"+bean.toString());
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			break;
		case COMPLETE_NOSIGN:
			LoginOrRegist completeBean = (LoginOrRegist) obj;
			UserBean userBean = completeBean.getResult();
			String ret = completeBean.getRet();
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			if("10000".equals(ret)){
				MySharedPreferences.save(mContext, MySharedPreferences.USER_ID, userBean.getCrmUser().getId()+"");
				MySharedPreferences.save(mContext, MySharedPreferences.USER_PHONE, userBean.getCrmUser().getPhone()+"");
				Gson gson = new Gson();
				String user_message = gson.toJson(userBean);
				mSession.saveKey(Config.USER_BEAN, user_message);
				mSession.saveKey(Config.USER_ID, userBean.getCrmUser().getId()+"");
				mSession.saveKey(Config.USER_PHONE, userBean.getCrmUser().getPhone()+"");
				openActivity(HomeActivity.class);
				finish();
			}
			break;
		}
	}



	@Override
	public void onError(Action method, Object statusCode) {
		// TODO Auto-generated method stub
		switch (method) {
		case NETWORK_FAILED:
			if(AppApi.ERROR_NETWORK_FAILED.equals(statusCode)){
				ToastUtil.showToast(mContext, getResources().getString(R.string.net_timeout));
			}
			if(RotateShowProgressDialog.isDialogShowing()){
				RotateShowProgressDialog.ShowProgressOff();
			}
			break;
		case LOGIN_NOSIGN:
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
		case TAG_NOSIGN:
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESPOSE_SELECT_CITY_CODE:
			String cityid= data.getStringExtra(SelectCityActivity.CITY_NAME);
			String cityName  = data.getStringExtra(SelectCityActivity.CITY_ID);
			if(!CheckUtil.isEmpty(cityid)){
				selectCity = cityid;
				cityShow.setText(cityName+"");
//				Log.e("cityName-------------", ""+cityName);
			}
			break;
		case RESPOSE_SELECT_CAR_TYPE:
			String carType = data.getStringExtra(SelectTypeActivity.CAR_TYPE_ID);
			String typeName = data.getStringExtra(SelectTypeActivity.CAR_TYPE_NAME);
			
			if(!CheckUtil.isEmpty(carType)){
				selectType = carType;
				Log.e("selectType***************", ""+selectType);
			}
			if(!CheckUtil.isEmpty(typeName)){
				carTypeShow.setText(""+typeName);
				Log.e("selectType***************", ""+typeName);
			}
			break;
		default:
			break;
		}
		
		
		
	}



}
