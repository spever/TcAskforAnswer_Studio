package com.tuanche.api.widget.gallery.zoomGallery;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import uk.co.senab.photoview.PhotoView;


public class ZoomImageDetailFragment extends Fragment {
    private static BitmapUtils mPictureUtils;
	private PhotoView mImageView;
	private ProgressBar progressBar;
	private LaShouImageParcel mImage;
	private OnViewPagerClickListener mListener;
    BitmapDisplayConfig config;
    private Activity context;

//    public static ImageDetailFragment newInstance(LaShouImageParcel image,BitmapUtils bitmapUtils) {
//		final ImageDetailFragment f = new ImageDetailFragment();
//		final Bundle args = new Bundle();
//		args.putParcelable("Image", image);
//		f.setArguments(args);
////        mPictureUtils = bitmapUtils;
//		return f;
//	}

    public static ZoomImageDetailFragment newInstance(LaShouImageParcel image,BitmapUtils pictureUtils) {
		final ZoomImageDetailFragment f = new ZoomImageDetailFragment();
		final Bundle args = new Bundle();
		args.putParcelable("Image", image);
		f.setArguments(args);
        mPictureUtils = pictureUtils;
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImage = (LaShouImageParcel) (getArguments()!=null ? getArguments().getParcelable("Image"):null);
        config = new BitmapDisplayConfig();
        // config.setLoadingDrawable(getActivity().getResources().getDrawable(
        // R.drawable.ic_empty));
        config.setLoadFailedDrawable(getActivity().getResources().getDrawable(
                R.drawable.ic_empty));
//		Log.i("info", "图片描述：" +  "图片地址：" + mImage.getImage());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.image_zoom_detail_fragment,
				container,false);
		mImageView = (PhotoView) v.findViewById(R.id.image);
		progressBar = (ProgressBar) v.findViewById(R.id.loading);
		mImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mListener!=null) {
					mListener.onViewPagerClick(v);
				}
			}
		});
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

        mPictureUtils.display(mImageView, mImage.getUrl(), config,
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

//        mImageLoader.displayImage(mImage.getImage(),mImageView,options,new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//            }
//        });
	}
	
	public interface OnViewPagerClickListener {
		void onViewPagerClick(View view);
	}
	
	public void setOnViewPagerClickListener(OnViewPagerClickListener listener) {
		this.mListener = listener;
	}
}
