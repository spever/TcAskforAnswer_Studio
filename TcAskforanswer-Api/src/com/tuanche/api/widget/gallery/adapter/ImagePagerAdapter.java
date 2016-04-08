package com.tuanche.api.widget.gallery.adapter;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.tuanche.api.widget.gallery.ImageDetailFragment;
import com.tuanche.api.widget.gallery.ImageDetailFragment.OnViewPagerClickListener;
import com.tuanche.api.widget.gallery.entity.LaShouImageParcel;


public class ImagePagerAdapter extends FragmentStatePagerAdapter {

	public List<LaShouImageParcel> imageParcels;
	private OnViewPagerClickListener mListener;
	private boolean isBlockClick = true;

	public ImagePagerAdapter(FragmentManager fm, List<LaShouImageParcel> imageParcels) {
		this(fm, imageParcels, true);
	}

	public ImagePagerAdapter(FragmentManager fm, List<LaShouImageParcel> imageParcels, boolean isBlockClick) {
		super(fm);
		this.imageParcels = imageParcels;
		this.isBlockClick = isBlockClick;
	}
	
	@Override
	public int getCount() {
		return imageParcels == null ? 0 : imageParcels.size();
	}

	@Override
	public Fragment getItem(int position) {
//		LaShouImageParcel imageParcel = (LaShouImageParcel) imageParcels.get(position);
//		ImageDetailFragment fragment = ImageDetailFragment.newInstance(imageParcel);
//		fragment.setOnViewPagerClickListener(mListener);
		ImageDetailFragment fragment = new ImageDetailFragment();
		return fragment;
	}
	
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageDetailFragment fragment = (ImageDetailFragment) super.instantiateItem(container, position);
		LaShouImageParcel imageParcel = (LaShouImageParcel) imageParcels.get(position);
		final Bundle args = new Bundle();
		args.putParcelable("imageParcel", imageParcel);
		fragment.setBlockClick(isBlockClick);
		fragment.setArguments(args);
		fragment.setOnViewPagerClickListener(mListener);
		return fragment;
	}
	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
	
	/**
	 * 
	 * @param listener
	 */
	public void setViewPagerClickListener(OnViewPagerClickListener listener) {
		this.mListener = listener;
	}
	
}