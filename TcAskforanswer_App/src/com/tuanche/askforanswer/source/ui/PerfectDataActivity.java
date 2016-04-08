package com.tuanche.askforanswer.source.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tuanche.api.bitmap.BitmapDisplayConfig;
import com.tuanche.api.utils.AppUtils;
import com.tuanche.api.utils.AppUtils.StorageFile;
import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.utils.RotateShowProgressDialog;
import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.core.ApiRequestListener;
import com.tuanche.askforanswer.app.core.AppApi;
import com.tuanche.askforanswer.app.core.AppApi.Action;
import com.tuanche.askforanswer.app.core.Config;
import com.tuanche.askforanswer.app.core.ResponseErrorMessage;
import com.tuanche.askforanswer.app.core.Session;
import com.tuanche.askforanswer.app.core.UploadRequestListener;
import com.tuanche.askforanswer.app.utils.FileUtils;
import com.tuanche.askforanswer.app.utils.ImageLoader;
import com.tuanche.askforanswer.app.utils.PhotoUtils;
import com.tuanche.askforanswer.app.utils.PictureFileUtils;
import com.tuanche.askforanswer.app.utils.ToastUtil;
import com.tuanche.askforanswer.source.PopListener.PopListener;
import com.tuanche.askforanswer.source.bean.AuditPiclistBean;
import com.tuanche.askforanswer.source.bean.AuditStatusBean;
import com.tuanche.askforanswer.source.bean.UpLoadBean;
import com.tuanche.askforanswer.source.bean.UserBean;
import com.tuanche.askforanswer.source.view.ChooseOrCameraPop;
import com.umeng.analytics.MobclickAgent;

/**
 * 完善资料上传身份证页面
 */
public class PerfectDataActivity extends BaseActivity implements ApiRequestListener, OnClickListener, PopListener, UploadRequestListener {
	public enum CardsEnum {
		idcard, profession_card
	};

	private ImageView upload_card, upload_id;
	private ImageView upload_professional_card, upload_profess;
	private TextView bottom_info, tv_perfect_red, tv_perfect_black;;
	private ChooseOrCameraPop popCameraPop;
	private LinearLayout main;
	private ImageView back;
	private RelativeLayout submit;
	private EnumMap<CardsEnum, String> paths = new EnumMap<CardsEnum, String>(CardsEnum.class);// 上传文件的路径
	private String path;
	private boolean isUploadCard; // 是否是上传身份证
	private AuditStatusBean statusBean; // 状态
	private List<String> cardList = new ArrayList<String>();
	private List<String> professionList = new ArrayList<String>();
	private boolean isFinishCardUpload;
	private UpLoadBean upLoadBean;
	private String cardPath;//身份证
	private String professpath;//职业证书
	private boolean isFromHome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfect_data);
		config = new BitmapDisplayConfig();
		config.setLoadingDrawable(getResources().getDrawable(R.drawable.ic_empty));
		config.setLoadFailedDrawable(getResources().getDrawable(R.drawable.ic_empty));
		getViews();
		setViews();
		setListeners();
		isFromHome = getIntent().getBooleanExtra("fromHome", false);

		// new AppApi().getAuditStatus(mContext, useId, "", this);
		if ("".equals(mSession.loadKey(Config.STATUS_BEAN, ""))) {
			fillSetDataForView();
		} else {
			statusBean = new Gson().fromJson(mSession.loadKey(Config.STATUS_BEAN, ""), AuditStatusBean.class);
			fillSetDataForView();
			for (int i = 0; i < statusBean.getResult().getPiclist().size(); i++) {
				AuditPiclistBean bean = statusBean.getResult().getPiclist().get(i);
				if (bean.getType() == 1) {//
					cardPath =bean.getPicUrl();
				} else {
					professpath =bean.getPicUrl();
				}
			}
		}
	}

	@Override
	public void getViews() {
		back = (ImageView) findViewById(R.id.top_back);
		main = (LinearLayout) findViewById(R.id.main);
		upload_card = (ImageView) findViewById(R.id.upload_idcard);
		upload_professional_card = (ImageView) findViewById(R.id.upload_professioncard);
		bottom_info = (TextView) findViewById(R.id.bottom_info);
		popCameraPop = new ChooseOrCameraPop(mContext, upload_card);
		submit = (RelativeLayout) findViewById(R.id.data_submit);
		upload_id = (ImageView) findViewById(R.id.upload_id);
		upload_profess = (ImageView) findViewById(R.id.upload_prfess);
		tv_perfect_black = (TextView) findViewById(R.id.tv_perfect_black);
		tv_perfect_red = (TextView) findViewById(R.id.tv_perfect_red);
	}

	@Override
	public void setViews() {
		Resources res = getResources();
		if (!TextUtils.isEmpty(bean.getCardInfo())) {
			String text = bean.getCardInfo().replace(bean.getCardInfoCount(), "<font color='#ff4359'>" + bean.getCardInfoCount() + "</font>");
			bottom_info.setText(Html.fromHtml(text));
		}
		ImageLoader.getInstance();
	}

	@Override
	public void setListeners() {
		back.setOnClickListener(this);
		upload_card.setOnClickListener(this);
		upload_professional_card.setOnClickListener(this);
		popCameraPop.setListener(this);
		submit.setOnClickListener(this);
		upload_id.setOnClickListener(this);
		upload_profess.setOnClickListener(this);
	}

	private void setNoClickable(){
		upload_card.setClickable(false);;
		upload_professional_card.setClickable(false);
	}
	
	@Override
	public void onSuccess(Action method, Object obj) {
		switch (method) {
		case AUDIT_NOSIGN:
			if (obj instanceof AuditStatusBean) {
				statusBean = (AuditStatusBean) obj;
				 fillSetDataForView();
			}

			break;
		case UPLOAD_NOSIGN:
			if (obj instanceof UpLoadBean) {
				upLoadBean = (UpLoadBean) obj;
				if (isFinishCardUpload == false) {
					uploadPic(professionList, 2);
					isFinishCardUpload = true;
				} else {
					ToastUtil.showToast(mContext, upLoadBean.getMsg());
					if (RotateShowProgressDialog.isDialogShowing()) {
						RotateShowProgressDialog.ShowProgressOff();
					}
					changState();
					Bundle bundle=new Bundle();
					bundle.putBoolean("isFromPerfectDataActivity", true);
					openActivity(HomeActivity.class, bundle);
					finish();
				}
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 修改状态上传成功后状态置为1审核中
	 */
	private void changState() {
		String json = mSession.loadKey(Config.USER_BEAN, "");
		if (!"".equals(json)) {
			UserBean bean = new Gson().fromJson(json, UserBean.class);
			if (null != bean.getUserIplementInfo()) {
				bean.getUserIplementInfo().setStatus(1);
			}
			mSession.saveKey(Config.USER_BEAN, new Gson().toJson(bean));
		}
		state = 1;
		fillSetDataForView();

	}

	public void fillSetDataForView() {
		Resources res = getResources();
		if (state == 1) {
			// auditing
			tv_perfect_red.setVisibility(View.GONE);
			tv_perfect_black.setText(res.getString(R.string.pefect_data_auditing));
			submit.setVisibility(View.GONE);
			shopic();
			setNoClickable();
		} else if (state == 2) {
			// pass
			tv_perfect_red.setVisibility(View.GONE);
			tv_perfect_black.setText(res.getString(R.string.audit_ed));
			submit.setVisibility(View.GONE);
			setNoClickable();
			for (int i = 0; i < statusBean.getResult().getPiclist().size(); i++) {
				AuditPiclistBean bean = statusBean.getResult().getPiclist().get(i);
				if (bean.getType() == 1) {
					pictureUtils.display(upload_id,bean.getPicUrl(), config);
				} else {
					pictureUtils.display(upload_profess,bean.getPicUrl(), config);
				}
			}

		} else if (state == 3) {
			// refuse
			tv_perfect_red.setVisibility(View.VISIBLE);
			AuditStatusBean auditStatusBean=new Gson().fromJson(Session.get(mContext).loadKey(Config.STATUS_BEAN, null), AuditStatusBean.class);
			if(auditStatusBean!=null){

			}

			for (int i = 0; i < statusBean.getResult().getPiclist().size(); i++) {
				AuditPiclistBean bean = statusBean.getResult().getPiclist().get(i);
				if (bean.getType() == 1) {
					pictureUtils.display(upload_id,bean.getPicUrl(), config);
				} else {
					pictureUtils.display(upload_profess,bean.getPicUrl(), config);
				}
			}
		} else {
			tv_perfect_red.setVisibility(View.GONE);
			tv_perfect_black.setText(res.getString(R.string.pefect_data_suggest));
			submit.setVisibility(View.VISIBLE);
		}
	}

	private void shopic() {
		if (null != statusBean) {
			for (int i = 0; i < statusBean.getResult().getPiclist().size(); i++) {
				AuditPiclistBean bean = statusBean.getResult().getPiclist().get(i);
				if (bean.getType() == 1) {
					LogUtils.e(bean.getPicUrl());
					pictureUtils.display(upload_id,bean.getPicUrl(), config);
				} else {
					pictureUtils.display(upload_profess,bean.getPicUrl(), config);
				}
			}
		}
	}

	@Override
	public void onError(Action method, Object statusCode) {
		switch (method) {
		case NETWORK_FAILED:
			if (AppApi.ERROR_NETWORK_FAILED.equals(statusCode)) {
				ToastUtil.showToast(this, getResources().getString(R.string.net_no));
			}
			break;
		case AUDIT_NOSIGN:
			if (statusCode instanceof ResponseErrorMessage) {
				// 后台返回了错误码和错误信息
				ToastUtil.showToast(this, ((ResponseErrorMessage) statusCode).getMsg());
			} else if (statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode)) {
				// 网络超时
				ToastUtil.showToast(this, getResources().getString(R.string.net_timeout));
			}
			break;
		case UPLOAD_NOSIGN:
			if (statusCode instanceof ResponseErrorMessage) {
				// 后台返回了错误码和错误信息
				ToastUtil.showToast(this, ((ResponseErrorMessage) statusCode).getMsg());
			} else if (statusCode instanceof String && AppApi.ERROR_TIMEOUT.equals(statusCode)) {
				// 网络超时
				ToastUtil.showToast(this, getResources().getString(R.string.net_timeout));
			}
			if (RotateShowProgressDialog.isDialogShowing()) {
				RotateShowProgressDialog.ShowProgressOff();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.upload_idcard:
			showPopWindow();
			isUploadCard = true;
			break;

		case R.id.upload_professioncard:
			showPopWindow();
			isUploadCard = false;
			break;
		case R.id.top_back:
			/*if (isFromHome) {
				openActivity(HomeActivity.class);
			}*/
			finish();
			break;
		case R.id.data_submit:
			isHaveTwoPictures();
			break;
		case R.id.upload_id:
			// new PhotoPop(mContext, main, pictureUtils,
			// cardPath).showAtLocation(main, Gravity.CENTER, 0, 0);
			Intent intent = new Intent(this, DialogMaskActivity.class);
			intent.putExtra("impath", cardPath);
			intent.putExtra("iscard", true);
			startActivity(intent);

			break;
		case R.id.upload_prfess:
			// new PhotoPop(mContext, main, pictureUtils,
			// professpath).showAtLocation(main, Gravity.CENTER, 0, 0);
			Intent intent2 = new Intent(this, DialogMaskActivity.class);
			intent2.putExtra("iscard", false);
			intent2.putExtra("impath", professpath);
			startActivity(intent2);

			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)拍照
	 * 
	 * @see com.tuanche.askforanswer.source.PopListener.PopListener#takeCamera()
	 */
	@Override
	public void takeCamera() {
		selectCamera();
	}

	/*
	 * (non-Javadoc)图库选图
	 * 
	 * @see
	 * com.tuanche.askforanswer.source.PopListener.PopListener#choosePicture()
	 */
	@Override
	public void choosePicture() {
		selectPhoto();
	}

	void showPopWindow() {
		if (!popCameraPop.isShowing()) {
			// popCameraPop.setAnimationStyle(R.style.AnimationFade);
			popCameraPop.showAtLocation(main, Gravity.BOTTOM, 0, 0);
		}
	}

	@Override
	public void submitAnswer(String content) {

	}

	@Override
	public void popDimiss() {

	}

	// 调用图库的照片
	@SuppressLint("InlinedApi")
	private void selectPhoto() {
		if (Build.VERSION.SDK_INT < 19) {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*");
			startActivityForResult(intent, Config.gallery);
		} else {
			Intent intent = new Intent();
			intent.setType("image/*");
			// 由于Intent.ACTION_OPEN_DOCUMENT的版本是4.4以上的内容
			// 所以注意这个方法的最上面添加了@SuppressLint("InlinedApi")
			// 如果客户使用的不是4.4以上的版本，因为前面有判断，所以根本不会走else，
			// 也就不会出现任何因为这句代码引发的错误
			intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
			startActivityForResult(intent, Config.gallery);

		}
	}

	// 调用相机拍照
	private void selectCamera() {
		path = null;
		File file = FileUtils.buildFile(
				AppUtils.getPath(mContext, StorageFile.tempfile) + String.valueOf(System.currentTimeMillis() / 1000) + ".jpg", false);
		path = file.getPath();
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
		startActivityForResult(intent, Config.camera);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case Config.gallery:
			if (data != null) {
				Uri uri = data.getData();
//				Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//				if (cursor == null) {
//				} else {
//					cursor.moveToFirst();
//					String pathgrally = cursor.getString(1);
//					path = pathgrally;
//					LogUtils.e(path);
//					cursor.close();
//					detialPathAndShowImage();
//				}
				path = PhotoUtils.getInstance().getPath(mContext, uri);
				LogUtils.e(path);
				detialPathAndShowImage();
			} else {
				ToastUtil.showToast(mContext, "图片选择失败请使用拍照功能或重新选择图片！");
			}
			break;

		case Config.camera:
			detialPathAndShowImage();
			break;
		}
	}

	/**
	 * 选择的图片加入到集合
	 */
	private void detialPathAndShowImage() {
		if (!TextUtils.isEmpty(path) && new File(path).exists()) {
			new PictureZoomTask().execute(path);
		}
	}

	// 将路径添加到集合中
	private void addPaths() {
		if (isUploadCard) {
			paths.put(CardsEnum.idcard, path);
			pictureUtils.display(upload_id, path, config);
			cardList.add(path);
			cardPath = path;
		} else {
			paths.put(CardsEnum.profession_card, path);
			pictureUtils.display(upload_profess, path, config);
			professionList.add(path);
			professpath = path;
		}
	}

	/**
	 * 判断有是否选择了两张图片
	 */
	public void isHaveTwoPictures() {
		if (paths.size() < 2) {
			ToastUtil.showToast(mContext, getResources().getString(R.string.perfect_must));
			return;
		}
		RotateShowProgressDialog.ShowProgressOn(mContext, false);
		uploadPic(cardList, 1);
	}

	/**
	 * 上传图片
	 */
	@SuppressWarnings("static-access")
	public void uploadPic(List<String> list, int type) {

		AppApi.upLoadPicture(mContext, useId, type, list, "", this);
	}

	@Override
	public void onLoading(long total, long current, boolean isUploading) {
		LogUtils.e(current / total + "%");
	}

	@Override
	public void updateProgress(long total, long current, boolean forceUpdateUI) {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			/*if (isFromHome) {
				openActivity(HomeActivity.class);
			}*/
			finish();
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		MobclickAgent.onEvent(mContext, "certificates_pv");

	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onResume(this);
	}

	class PictureZoomTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			path = result;
			addPaths();
		}

		@Override
		protected String doInBackground(String... arg0) {
			String path = arg0[0];
			try {
				path = PictureFileUtils.compressImage(mContext, path, "", 80);
			} catch (FileNotFoundException e) {
				ToastUtil.showToast(mContext, "获取图片失败");
			}

			return path;
		}

	}

}
