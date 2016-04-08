package com.tuanche.api.widget.gallery;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.tuanche.api.R;
import com.tuanche.api.bitmap.BitmapDisplayConfig;
import com.tuanche.api.bitmap.callback.BitmapLoadCallBack;
import com.tuanche.api.bitmap.callback.BitmapLoadFrom;
import com.tuanche.api.utils.BitmapUtils;
import com.tuanche.api.widget.gallery.entity.LaShouImageParcel;

public class ImageDetailFragment extends Fragment {
	private String mImageUrl;
	private ClickImageView mImageView;
	private ProgressBar progressBar;
	private BitmapUtils bitmapUtils;
	private BitmapDisplayConfig config;
	private LaShouImageParcel mImageParcel;
	private OnViewPagerClickListener mListener;
	private boolean isBlockClick = true;

	public boolean isBlockClick() {
		return isBlockClick;
	}

	public void setBlockClick(boolean isBlockClick) {
		this.isBlockClick = isBlockClick;
	}

	public static ImageDetailFragment newInstance(String imageUrl) {
		final ImageDetailFragment f = new ImageDetailFragment();

		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		f.setArguments(args);

		return f;
	}
	
	public static ImageDetailFragment newInstance(LaShouImageParcel imageParcel) {
		final ImageDetailFragment f = new ImageDetailFragment();

		final Bundle args = new Bundle();
		args.putParcelable("imageParcel", imageParcel);
		f.setArguments(args);

		return f;
	}
	
	public void setImageData(LaShouImageParcel imageParcel){
		mImageParcel = imageParcel;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageParcel = (LaShouImageParcel) (getArguments()!=null?getArguments().getParcelable("imageParcel"):null);
		Log.i("info", "图片描述："+mImageParcel.getDescription() + "图片地址："+mImageParcel.getUrl());
		bitmapUtils = new BitmapUtils(getActivity());
		config = new BitmapDisplayConfig();
		// config.setLoadingDrawable(getActivity().getResources().getDrawable(
		// R.drawable.ic_empty));
		config.setLoadFailedDrawable(getActivity().getResources().getDrawable(
				R.drawable.ic_empty));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.image_detail_fragment,
				container, false);
		mImageView = (ClickImageView) v.findViewById(R.id.image);
		progressBar = (ProgressBar) v.findViewById(R.id.loading);
		mImageView.setBlockClick(isBlockClick);
		if (!isBlockClick) {
			mImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mListener != null) {
						mListener.onViewPagerClick(v);
					}
				}
			});
		}
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		bitmapUtils.display(mImageView, mImageParcel.getUrl(), config,
				new BitmapLoadCallBack<ImageView>() {

					@Override
					public void onLoadCompleted(ImageView container,
							String url, Bitmap bitmap,
							BitmapDisplayConfig config, BitmapLoadFrom from) {
						container.setImageBitmap(bitmap);
						progressBar.setVisibility(View.GONE);
					}

					@Override
					public void onLoadFailed(ImageView container, String url,
							Drawable drawable) {
						progressBar.setVisibility(View.GONE);
					}
				});

	}
	
	public interface OnViewPagerClickListener {
		void onViewPagerClick(View view);
	}
	
	public void setOnViewPagerClickListener(OnViewPagerClickListener listener) {
		this.mListener = listener;
	}
}
