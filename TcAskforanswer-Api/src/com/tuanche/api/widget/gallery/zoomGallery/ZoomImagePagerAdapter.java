package com.tuanche.api.widget.gallery.zoomGallery;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tuanche.api.utils.BitmapUtils;
import com.tuanche.api.widget.gallery.entity.LaShouImageParcel;
import com.tuanche.api.widget.gallery.zoomGallery.ZoomImageDetailFragment;

import java.util.List;


public class ZoomImagePagerAdapter extends FragmentStatePagerAdapter {

	public List<LaShouImageParcel> Images;
	private ZoomImageDetailFragment.OnViewPagerClickListener mListener;
    private BitmapUtils mPictureUtils;

	public ZoomImagePagerAdapter(FragmentManager fm, List<LaShouImageParcel> Images, BitmapUtils pictureUtils) {
		super(fm);
		this.Images = Images;
        mPictureUtils = pictureUtils;
	}

	@Override
	public int getCount() {
		return Images == null ? 0 : Images.size();
	}

	@Override
	public Fragment getItem(int position) {
        LaShouImageParcel Image = (LaShouImageParcel) Images.get(position);
//        ImageDetailFragment imageDetailFragment = new ImageDetailFragment();
		ZoomImageDetailFragment fragment = ZoomImageDetailFragment.newInstance(Image, mPictureUtils);
		fragment.setOnViewPagerClickListener(mListener);
		return fragment;
	}
	
	public void setViewPagerClickListener(ZoomImageDetailFragment.OnViewPagerClickListener listener) {
		this.mListener = listener;
	}
	
}