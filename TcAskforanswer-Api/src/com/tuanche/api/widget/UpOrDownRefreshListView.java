package com.tuanche.api.widget;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tuanche.api.R;
import com.tuanche.api.utils.BitmapUtils;
import com.tuanche.api.utils.LogUtils;

public class UpOrDownRefreshListView extends ListView implements OnScrollListener{

	private Context context;
	/**注册一个监听 */
	private OnRefreshListener refreshListener;
	/**头部加载刷新布局*/
	private LinearLayout headViewLayout;
	private ImageView headArrowImageView;
	private ProgressBar headProgressBar;
	private TextView headTextView;
	private TextView headTimeTextView;
	/**底部加载刷新布局*/
	private LinearLayout footerViewLayout;
	/**顶部刷新是否开启，默认否*/
	private boolean isHead=false;
	/**底部刷新是否开启，默认否*/
	private boolean isFooter=false;
	private int measuredHeaderHeight=0;
	/**下拉箭头动画 */
	private RotateAnimation mAnimation;
	/**下拉箭头释放动画*/
	private RotateAnimation mReverseAnimation;
	
	private View footerLinearView;
	
	private ProgressBar footerProgressBar;
	
	private int event_first_Y=0;
	private int event_last_Y=0;
	private final int PER=4;
	
	/**用于保证event_down_Y的值在一个完整的touch事件中只被记录一次*/
	private boolean lock=false;
	 
	/**列表中首行索引，用来记录其与头部距离*/
	private int firstIndex=0;
	
	private boolean refreshBack=false;
	/**最大记录数 */
	public static final int MAX_LIMIT_COUNT = 200000;
	
	private boolean isFooterLoading=false;
	
	private BitmapUtils bitmapUtils;
	private enum HEADER_CONTROL_STATE {
		/**释放更新 */
		RELEASE_To_REFRESH,
		/**下拉更新 */
		PULL_To_REFRESH,
		/**释放中...*/
		REFRESHING,
		/**数据读取中...*/
		LOADING ,
		/**完成加载 */
		DONE;
	}
	public static enum REFRESH_WHO{
		HEAD,
		FOOTER;
	}
	 
	private HEADER_CONTROL_STATE header_control;
	
	public UpOrDownRefreshListView(Context context){
		
		super(context);
		this.context=context;
		
	}
	public UpOrDownRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		// TODO Auto-generated constructor stub
	}

	public OnRefreshListener getRefreshListener() {
		return refreshListener;
	}

	/**
	 * @param refreshListener	注册监听
	 * @param isHead			开启头部刷新
	 * @param isFooter			开启底部刷新
	 */
	public void setRefreshListener(OnRefreshListener refreshListener,boolean isHead,boolean isFooter,BitmapUtils bitmapUtils) {
		this.refreshListener = refreshListener;
		this.isFooter=isFooter;
		this.isHead=isHead;
		this.bitmapUtils=bitmapUtils;
		init();
	}

	private void init(){
		LayoutInflater layoutInflater=LayoutInflater.from(this.context);
		
		if(isHead){
			headViewLayout=(LinearLayout) layoutInflater.inflate(R.layout.head, null);
			headArrowImageView=(ImageView) headViewLayout.findViewById(R.id.head_arrowImageView);
			headArrowImageView.setMinimumHeight(50);
			headProgressBar=(ProgressBar) headViewLayout.findViewById(R.id.head_progressBar);
			headTimeTextView=(TextView) headViewLayout.findViewById(R.id.head_lastUpdatedTextView);
			headTextView=(TextView) headViewLayout.findViewById(R.id.head_tipsTextView);
			/**测量头布局*/
			measureView(headViewLayout);
			measuredHeaderHeight=headViewLayout.getMeasuredHeight(); 
			headViewLayout.setPadding(0, -1* measuredHeaderHeight, 0, 0);
			addHeaderView(headViewLayout);
			
			mAnimation = new RotateAnimation(0, -180,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			mAnimation.setInterpolator(new LinearInterpolator());
			mAnimation.setDuration(250);
			mAnimation.setFillAfter(true);

			mReverseAnimation = new RotateAnimation(-180, 0,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			mReverseAnimation.setInterpolator(new LinearInterpolator());
			mReverseAnimation.setDuration(200);
			mReverseAnimation.setFillAfter(true);
			header_control=HEADER_CONTROL_STATE.DONE;
		}
		if(isFooter){
			footerViewLayout=(LinearLayout) layoutInflater.inflate(R.layout.footer,null);
			footerLinearView=(View) footerViewLayout.findViewById(R.id.footer_linear);
			footerProgressBar = (ProgressBar) footerViewLayout
					.findViewById(R.id.footer_progressBar);
 			measureView(footerViewLayout);
			footerViewLayout.invalidate();
			addFooterView(footerViewLayout);
			showMore(false, false);
		}
		setOnScrollListener(this);
	}
	public void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			/**EXACTLY--父元素决定子元素的确切大小，子元素将被限定在给定的边界里，而忽略它本身大小*/
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			/**父布局没有给子布局任何限制，子布局可以任意大小*/
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
	/* (non-Javadoc)
	 * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)
	 *  firstVisibleItem 	当前能看见的第一个列表项ID（从0开始）
	 *  visibleItemCount	当前能看见的列表项总数（小半个也算，部分显示的都算）  
	 *  totalItemCount		列表项共数
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}
	
	
	/* (non-Javadoc) 
	 * @see android.widget.AbsListView.OnScrollListener#onScrollStateChanged(android.widget.AbsListView, int)
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
		switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_IDLE:
				/**视图停止滚动,静止*/
				bitmapUtils.resumeTasks();
	            LogUtils.i("当前ListView停止滚动，加载图片开始......................");
				LogUtils.d("ListView 已经停止滚动");
				if(isFooter && null!=refreshListener){
					/**总数据数*/
					int totalItemCount=view.getCount();
					if(totalItemCount>MAX_LIMIT_COUNT){
						showMore(false, false);
						return ;
					}
					/**可视区域最后一条数据位置*/
					int lastVisiblePostion=view.getLastVisiblePosition();
					if(lastVisiblePostion>=(totalItemCount-1)){
						 showMore(true,true);
						 refreshListener.onRefresh(REFRESH_WHO.FOOTER);
					}
				}
				break;
			case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				/**手指一直触摸屏幕并未离开，并拖动滚动状态中*/
				 bitmapUtils.pauseTasks();
                 LogUtils.i("当前ListView被触摸滚动，暂停加载......................");
				break;
			case OnScrollListener.SCROLL_STATE_FLING:
				/**手指触摸屏幕并滑动，且手指离开后，现在正在滑行，直到停止*/
				LogUtils.d("ListView 手指触摸屏幕并滑动，且手指离开后，现在正在滑行，直到停止");
				bitmapUtils.pauseTasks();
                LogUtils.i("当前ListView被手势离开滚动，暂停加载......................");
				break;
		default:
			break;
		}
	}

	
	/**
	 * 控制底部加载控件
	 * @param isShow	是否显示底部控件
	 * @param isShowProgress	是否有滚动条
	 */
	public void showMore(boolean isShow, boolean isShowProgress) {
		if (isFooter) {
			if (isShow) {
				footerLinearView.setVisibility(View.VISIBLE);
				footerViewLayout.setVisibility(View.VISIBLE);
				if (footerProgressBar != null) {
					if (isShowProgress) {
						footerProgressBar.setVisibility(View.VISIBLE);
					} else {
						footerProgressBar.setVisibility(View.GONE);
					}
				}
			} else {
				footerLinearView.setVisibility(View.GONE);
				footerProgressBar.setVisibility(View.GONE);
				footerViewLayout.setVisibility(View.GONE);
			}

		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
				event_first_Y=(int) ev.getY();
				/**锁定上拉或下跩*/
				lock=true;	
			break;
		case MotionEvent.ACTION_UP:
				if(header_control==null ) return super.onTouchEvent(ev);
 				switch (header_control) {
					case DONE:
						break;
					case PULL_To_REFRESH:
						header_control = HEADER_CONTROL_STATE.DONE;
						changeHeaderViewByState();
						break;
					case RELEASE_To_REFRESH:
						/**释放并刷新*/
						header_control = HEADER_CONTROL_STATE.REFRESHING;
						changeHeaderViewByState();
						refreshListener.onRefresh(REFRESH_WHO.HEAD);
						break;
					default:
						break;
				}
 			/**解锁上拉或下跩*/
 			lock=false;
			refreshBack = false;
			break;
		case MotionEvent.ACTION_MOVE:
			ActionMove(ev);
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	public void ActionMove(MotionEvent ev){
		int firstPostion=getFirstVisiblePosition();
		event_last_Y=(int) ev.getY();
		int postion=event_last_Y-event_first_Y;
		if(postion<0){
			/**上拉更新底部刷新*/
			return ;
		}
		if(header_control==null) return; 
		switch (header_control) {
		case PULL_To_REFRESH:
			/**下拉并释放*/
			setSelection(0);
			if((event_last_Y-event_first_Y)/PER>=measuredHeaderHeight){
				/**当下拉头部到PER分之一处时，设置头部下拉状态为“释放并更新”*/
				header_control=HEADER_CONTROL_STATE.RELEASE_To_REFRESH;
				/**当前要松开手，箭头从下变换为向上*/
				refreshBack=true;
				changeHeaderViewByState();
			} else if (event_last_Y - event_first_Y <= 0) {
				/**顶部刷新完成，下拉布局已恢复*/
				header_control = HEADER_CONTROL_STATE.DONE;
				changeHeaderViewByState();
					LogUtils.i( "change to done");
			}
			headViewLayout.setPadding(0, -1 * measuredHeaderHeight
					+ (event_last_Y - event_first_Y) / PER, 0, 0);
			break;
		case RELEASE_To_REFRESH:
			/**释放并更新*/
			setSelection(0);
			if (((event_last_Y - event_first_Y) / PER < measuredHeaderHeight)
					&& (event_last_Y - event_first_Y) > 0) {
				/**下拉头部，手松开，正在恢复过程中*/
				header_control = HEADER_CONTROL_STATE.PULL_To_REFRESH;
				changeHeaderViewByState();
			} else if (event_last_Y - event_first_Y <= 0) {
				/**下拉头部，手松开，已经恢复*/
				header_control = HEADER_CONTROL_STATE.DONE;
				changeHeaderViewByState();
			}  
			headViewLayout.setPadding(0, (event_last_Y - event_first_Y) / PER
					- measuredHeaderHeight, 0, 0);
			break;
		case DONE:
			/**更新完成*/ 
			if (event_last_Y - event_first_Y > 0) {
				header_control = HEADER_CONTROL_STATE.PULL_To_REFRESH;
				changeHeaderViewByState();
			}
			break;
		default:
			break;
		}
		
	}
	
	private void changeHeaderViewByState() {
		if(header_control==null) return;
		switch (header_control) {
		case RELEASE_To_REFRESH:
			headArrowImageView.setVisibility(View.VISIBLE);
			headProgressBar.setVisibility(View.GONE);
			headTextView.setVisibility(View.VISIBLE);
			headTimeTextView.setVisibility(View.VISIBLE);
			headArrowImageView.clearAnimation();
			headArrowImageView.startAnimation(mAnimation);
			headTextView.setText(R.string.release_refresh);
			break;
		case PULL_To_REFRESH:
			headProgressBar.setVisibility(View.GONE);
			headTextView.setVisibility(View.VISIBLE);
			headTimeTextView.setVisibility(View.VISIBLE);
			headArrowImageView.clearAnimation();
			headArrowImageView.setVisibility(View.VISIBLE);
			if (refreshBack) {
				refreshBack = false;
				headArrowImageView.clearAnimation();
				headArrowImageView.startAnimation(mReverseAnimation);
				headTextView.setText(R.string.drop_down);
			} else {
				headTextView.setText(R.string.drop_down);
			}
			break;
		case REFRESHING:
			headViewLayout.setPadding(0, 0, 0, 0);
			headProgressBar.setVisibility(View.VISIBLE);
			headArrowImageView.clearAnimation();
			headArrowImageView.setVisibility(View.GONE);
			headTextView.setText(R.string.refreshing);
			headTimeTextView.setVisibility(View.VISIBLE);
				LogUtils.i( "Current state refreshing...");
			break;
		case DONE:
			headViewLayout.setPadding(0, -1 * measuredHeaderHeight, 0, 0);
			headProgressBar.setVisibility(View.GONE);
			headArrowImageView.clearAnimation();
			headArrowImageView.setImageResource(R.drawable.z_arrow_down);
			headTextView.setText(R.string.drop_down);
			headTimeTextView.setVisibility(View.VISIBLE);
				LogUtils.i( "Current state done...");
			break;
		}
	}

	public void onComplateFresh(REFRESH_WHO who){
		switch (who) {
		case FOOTER:
			showMore(false, false);
			break;
		case HEAD: 
			showHeader();
			break; 
		default:
			break;
		}
	}
	public void onErrorFresh(REFRESH_WHO who){
		switch (who) {
		case FOOTER:
			showMore(false, false);
			break;
		case HEAD: 
			showHeader();
			break; 
		default:
			break;
		}
	}
	public void showHeader(){
		header_control = HEADER_CONTROL_STATE.DONE;
		String dateTime = DateFormat.getDateTimeInstance().format(new Date());
		headTimeTextView.setText(getContext()
				.getString(R.string.update_time) + ":" + dateTime);
		changeHeaderViewByState();
	}
	public interface OnRefreshListener {
		/**刷新回调*/
		public void onRefresh(REFRESH_WHO who);
		 
	}
}
