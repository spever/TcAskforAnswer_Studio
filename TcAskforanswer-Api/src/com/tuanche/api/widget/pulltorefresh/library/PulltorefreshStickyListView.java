package com.tuanche.api.widget.pulltorefresh.library;

import com.tuanche.api.R;
import com.tuanche.api.widget.pinned.StickyListHeadersAdapter;
import com.tuanche.api.widget.pinned.StickyListHeadersListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class PulltorefreshStickyListView extends
		PullToRefreshBase<StickyListHeadersListView> implements OnScrollListener {

	private OnLastItemVisibleListener mOnLastItemVisibleListener;
	private OnScrollListener mOnScrollListener;
	private boolean mLastItemVisible;
	private TextView mFooterTv;
	private View mFooterProgress;

	public PulltorefreshStickyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mRefreshableView.setOnScrollListener(this);
		initLoadMore(context);
	}

	public PulltorefreshStickyListView(
			Context context,
			PullToRefreshBase.Mode mode,
			PullToRefreshBase.AnimationStyle animStyle) {
		super(context, mode, animStyle);
		mRefreshableView.setOnScrollListener(this);
		initLoadMore(context);
	}

	public PulltorefreshStickyListView(Context context,
			PullToRefreshBase.Mode mode) {
		super(context, mode);
		mRefreshableView.setOnScrollListener(this);
		initLoadMore(context);
	}

	public PulltorefreshStickyListView(Context context) {
		super(context);
		mRefreshableView.setOnScrollListener(this);
		initLoadMore(context);
	}

	@Override
	public PullToRefreshBase.Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected StickyListHeadersListView createRefreshableView(Context context,
			AttributeSet attrs) {
		StickyListHeadersListView listView;
		listView = new StickyListHeadersListView(context, attrs);
		return listView;
	}

	@Override
	protected boolean isReadyForPullEnd() {
		final Adapter adapter = mRefreshableView.getAdapter();

		if (null == adapter || adapter.isEmpty()) {
//			if (DEBUG) {
//				Log.d(LOG_TAG, "isLastItemVisible. Empty View.");
//			}
			return true;
		} else {
			final int lastItemPosition = mRefreshableView.getCount() - 1;
			final int lastVisiblePosition = mRefreshableView.getLastVisiblePosition();

//			if (DEBUG) {
//				Log.d(LOG_TAG, "isLastItemVisible. Last Item Position: " + lastItemPosition + " Last Visible Pos: "
//						+ lastVisiblePosition);
//			}

			/**
			 * This check should really just be: lastVisiblePosition ==
			 * lastItemPosition, but PtRListView internally uses a FooterView
			 * which messes the positions up. For me we'll just subtract one to
			 * account for it and rely on the inner condition which checks
			 * getBottom().
			 */
			if (lastVisiblePosition >= lastItemPosition - 1) {
				final int childIndex = lastVisiblePosition - mRefreshableView.getFirstVisiblePosition();
				final View lastVisibleChild = mRefreshableView.getChildAt(childIndex);
				if (lastVisibleChild != null) {
					return lastVisibleChild.getBottom() <= mRefreshableView.getBottom();
				}
			}
		}

		return false;
	}

	@Override
	protected boolean isReadyForPullStart() {
		final Adapter adapter = mRefreshableView.getAdapter();

		if (null == adapter || adapter.isEmpty()) {
//			if (DEBUG) {
//				Log.d(LOG_TAG, "isFirstItemVisible. Empty View.");
//			}
			return true;

		} else {

			/**
			 * This check should really just be:
			 * mRefreshableView.getFirstVisiblePosition() == 0, but PtRListView
			 * internally use a HeaderView which messes the positions up. For
			 * now we'll just add one to account for it and rely on the inner
			 * condition which checks getTop().
			 */
			if (mRefreshableView.getFirstVisiblePosition() <= 1) {
				final View firstVisibleChild = mRefreshableView.getWrappedList().getChildAt(0);
				if (firstVisibleChild != null) {
					return firstVisibleChild.getTop() >= mRefreshableView.getWrappedList().getTop();
				}
			}
		}

		return false;
	}
	
	@Override
	public final void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
			final int totalItemCount) {
		/**
		 * Set whether the Last Item is Visible. lastVisibleItemIndex is a
		 * zero-based index, so we minus one totalItemCount to check
		 */
		if (null != mOnLastItemVisibleListener) {
			mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
		}

		// Finally call OnScrollListener if we have one
		if (null != mOnScrollListener) {
			mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	public void setAdapter(StickyListHeadersAdapter mTestBaseAdapter) {
		mRefreshableView.setAdapter(mTestBaseAdapter);
	}
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		mRefreshableView.setOnItemClickListener(listener);
	}

	public final void setOnLastItemVisibleListener(OnLastItemVisibleListener listener) {
		mOnLastItemVisibleListener = listener;
	}

	public final void setOnScrollListener(OnScrollListener listener) {
		mOnScrollListener = listener;
	}

	public final void setScrollEmptyView(boolean doScroll) {
	}

	@Override
	public void onScrollStateChanged(final AbsListView view, final int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && null != mOnLastItemVisibleListener && mLastItemVisible) {
			if (isLoadAvailable()) {
				setLoadAvailable(false);
				mOnLastItemVisibleListener.onLastItemVisible();
			}
		}

		if (null != mOnScrollListener) {
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}
	
	private void initLoadMore(Context context) {
		View mFooterView = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_footer, null);
		mFooterTv = (TextView) mFooterView.findViewById(R.id.pull_to_loadmore_text);
		mFooterProgress = mFooterView.findViewById(R.id.pull_to_loadmore_progress);
		mRefreshableView.addFooterView(mFooterView, null, false);
	}

	private void updateUI(boolean hasMore) {
		updateUI(hasMore, !hasMore);
	}
	/**
	 * 最下面一条需要显示“已显示完毕”的提示语---只有在这种情况吓才使用，默认情况下是使用一个参数的的onLoadComplete
	 */
	public void onLoadComplete(boolean isMore,boolean isShowAll) {
		setLoadAvailable(isMore);
		updateUI(isMore,isShowAll);
	}
	/**
	 * 显示最后一个条目
	 * @param hasMore
	 * @param isShowAll
	 */
	private void updateUI(boolean hasMore,boolean isShowAll) {
		if(isShowAll){//显示所有
			mFooterProgress.setVisibility(View.INVISIBLE);
			mFooterTv.setText(R.string.pull_to_refresh_all);
		}else{
			mFooterProgress.setVisibility(View.VISIBLE);
			mFooterTv.setText(R.string.pull_to_refresh_loadmore);
		}
	}

	/*
	 * 加载完成后,为true则需要再次加载，为false则隐藏载入中的提示
	 */
	public void onLoadComplete(boolean isMore) {
		setLoadAvailable(isMore);
		updateUI(isMore);
	}
}
