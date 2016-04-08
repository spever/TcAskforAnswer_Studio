package com.tuanche.askforanswer.source.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class HomePagerAdapter extends FragmentStatePagerAdapter{
	
	private List list;
	private FragmentManager fm;
	private Fragment fragment;
	
	public HomePagerAdapter(FragmentManager fm) {
		super(fm);
	}
	public HomePagerAdapter(FragmentManager fm,List list) {
		super(fm);
		this.list=list;
	}

	
	
	@Override
	public Fragment getItem(int arg0) {
		
		if(arg0==0){
			
		}else{
			
		}
		
		return null;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}

	@Override
	public Object instantiateItem(ViewGroup arg0, int arg1) {
		return super.instantiateItem(arg0, arg1);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return super.isViewFromObject(view, object);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		
	}

	
	
}
