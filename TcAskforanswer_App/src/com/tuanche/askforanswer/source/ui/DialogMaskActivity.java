package com.tuanche.askforanswer.source.ui;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tuanche.askforanswer.R;
import com.umeng.analytics.MobclickAgent;

/**
 * 图片放大界面
 * 
 * @author wang
 * 
 */
public class DialogMaskActivity extends BaseActivity {
	PhotoView photoView;

	private PhotoViewAttacher mAttacher;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_activity);
		photoView = (PhotoView) findViewById(R.id.photoView);
		String url = getIntent().getStringExtra("impath");
		mAttacher = new PhotoViewAttacher(photoView);
		mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
			@Override
			public void onPhotoTap(View view, float x, float y) {
				finish();
			}
		});
	
		if(!TextUtils.isEmpty(url)){
			pictureUtils.display(photoView, url);
		}else{
			boolean iscard=getIntent().getBooleanExtra("iscard", false);
			if(iscard){
				photoView.setImageResource(R.drawable.card);
			}else{
				photoView.setImageResource(R.drawable.infocard);
			}
		}
			
	}

	@Override
	protected void onDestroy() {
		 if (mAttacher != null) {
	            mAttacher.cleanup();
	        }
		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
	private Bitmap getUsableImage(String path) {
		Options opts = new Options(); // 选项对象(在加载图片时使用)
		opts.inJustDecodeBounds = true; // 修改选项, 只获取大小
		BitmapFactory.decodeFile(path, opts); // 加载图片(只得到图片大小)

		WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE); // 获取窗体管理器
		int scaleX = opts.outWidth / manager.getDefaultDisplay().getWidth(); // X轴缩放比例(图片宽度/屏幕宽度)
		int scaleY = opts.outHeight / manager.getDefaultDisplay().getHeight(); // Y轴缩放比例
		int scale = scaleX > scaleY ? scaleX : scaleY; // 图片的缩放比例(X和Y哪个大选哪个)

		opts.inJustDecodeBounds = false; // 修改选项, 不只解码边界
		opts.inSampleSize = scale > 1 ? scale : 1; // 修改选项, 加载图片时的缩放比例
		return BitmapFactory.decodeFile(path, opts); // 加载图片(得到缩放后的图片)
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
	protected void onResume() {
		super.onResume();
		 MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onResume(this);
	}
}
