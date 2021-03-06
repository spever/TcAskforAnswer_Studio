package com.tuanche.api.widget.banner;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tuanche.api.R;
import com.tuanche.api.widget.indicator.LaShouImgIndicator;
import com.tuanche.api.widget.indicator.LaShouIndicator;

/**
 * 可循环自动滚动的Gallery.<br>
 * 1、控制是否自动滚动<br>
 * 2、删除<br>
 * <pre><strong>使用方法:</strong>
 * <li>在XML布局文件中以使用普通控件的方式引入，在Activity中使用{@link Activity#findViewById(int)}方法完成初始化。
 * <li>调用<strong>{@link #setOnDelCallBack(OnDelCallBack)}</strong>来设置点击删除按钮时的回调.<br>
 * <li>调用<strong>{@link #setData(List)}</strong>来设置广告数据集合。List集合的元素只能是<strong>{@link AdsEntity}</strong>类型
 * </pre>
 * @author Wangzhen
 * 
 */
public class AdsLooper extends LinearLayout {

	private Drawable bannerDefaultPic;
	private AutoScrollGallery autoScrollGallery;
	private AdvGalleryAdapter advGalleryAdapter;
	private ImageView deleteBanner;
	private LaShouImgIndicator laShouIndicator;

	private boolean isAutoScroll = true;
	private OnDelCallBack onDelCallBack;

	private Context mContext;
	
	public OnGalleryClickListener mListener;
	
	private int height;
	private float heightFactor;
	
	public interface OnGalleryClickListener {
		public void onGalleryItemClick(AdsEntity entity);
	}
	
	public void setOnGalleryClickListener(OnGalleryClickListener listener) {
		this.mListener = listener;
	}

	public AdsLooper(Context context) {
		super(context);
		init(context);
	}

	public AdsLooper(Context context, AttributeSet attrs) {
		super(context, attrs);
		int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
		heightFactor = 126.0f / 720;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AdsBanner);
		heightFactor = a.getFloat(R.styleable.AdsBanner_bannerHeightFactor, heightFactor);
		bannerDefaultPic = a.getDrawable(R.styleable.AdsBanner_bannerDefaultPic);
		height = (int) (1.0f * screenWidth * heightFactor);
		a.recycle();
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(context);
		View child = inflater.inflate(R.layout.auto_scroll_gallery, null);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,(int) height);
		autoScrollGallery = (AutoScrollGallery) child
				.findViewById(R.id.home_advs_gallery);
		
		autoScrollGallery.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
		autoScrollGallery.setFocusable(true);
		deleteBanner = (ImageView) child.findViewById(R.id.deleteBanner);
		laShouIndicator = (LaShouImgIndicator) child.findViewById(R.id.home_advs_gallery_mark);
		addView(child,params);
	}

	public void setOnDelCallBack(OnDelCallBack onDelCallBack) {
		this.onDelCallBack = onDelCallBack;
	}
	
	/**
	 * 填充数据
	 * @param data
	 */
	public void setData(final List<AdsEntity> data) {
		if (advGalleryAdapter==null){
			autoScrollGallery.setImagesLength(data.size());
			advGalleryAdapter = new AdvGalleryAdapter(mContext, data,
					autoScrollGallery,height,bannerDefaultPic);
		}else{
			advGalleryAdapter.setData(data);
			advGalleryAdapter.notifyDataSetChanged();
		}
		setCanAutoScroll(isAutoScroll);
		autoScrollGallery.setAdapter(advGalleryAdapter);
		autoScrollGallery.setSelection(0);
		autoScrollGallery
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						laShouIndicator.check(arg2 % data.size());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						
					}

				});
		
		
		autoScrollGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				if (null != mListener) {
					mListener.onGalleryItemClick(data.get(position % data.size()));
				}
			}
		});

		if (data.size() > 1) {
			laShouIndicator.setCount(data.size());
			laShouIndicator.setVisibility(View.VISIBLE);
			autoScrollGallery.startAutoScroll();
		} else {
			laShouIndicator.setVisibility(View.GONE);
		}

		deleteBanner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setVisibility(View.GONE);
				cancleAutoScroll();
				if (onDelCallBack != null)
					onDelCallBack.onDel();
			}
		});
	}

	/**
	 * 设置是否可以自动滚动，true可以自动滚动,false不可以。默认为true
	 * 
	 * @param canAutoScroll
	 */
	public void setCanAutoScroll(boolean canAutoScroll) {
		this.isAutoScroll = canAutoScroll;
		autoScrollGallery.setAutoScroll(canAutoScroll);
	}

	/**
	 * 开始自动滚动
	 */
	public void startAutoScroll() {
		this.isAutoScroll = true;
		autoScrollGallery.startAutoScroll();
	}
	
	/**
	 * 取消自动滚动
	 */
	public void cancleAutoScroll() {
		this.isAutoScroll = false;
		autoScrollGallery.cancleAutoScroll();
	}
	
	public static int dpToPx(Context context, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}
	
	/**
	 * 点击删除按钮时的回调
	 * @author Wangzhen
	 *
	 */
	public interface OnDelCallBack {
		void onDel();
	}
	
	/**
	 * Ad实体类
	 * @author Wangzhen
	 *
	 */
	public static class AdsEntity {

		private int index;
		private View view;
		private String uri;
		private Object object;

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public View getView() {
			return view;
		}

		public void setView(View view) {
			this.view = view;
		}

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}
	}

}
