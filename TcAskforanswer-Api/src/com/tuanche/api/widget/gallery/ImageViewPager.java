package com.tuanche.api.widget.gallery;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ImageViewPager extends ViewPager {


	private static final String TAG = "ImageViewPager";

	public ImageViewPager(Context context) {
		super(context);
	}
	
	public ImageViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			//不理会
			return false;
		}catch(ArrayIndexOutOfBoundsException e ){
			//不理会
			return false;
		}
	}
	@Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);

      // find the first child view
      View view = getChildAt(0);
      if (view != null) {
          // measure the first child view with the specified measure spec
          view.measure(widthMeasureSpec, heightMeasureSpec);
      }

      setMeasuredDimension(getMeasuredWidth(), measureHeight(heightMeasureSpec, view));
  }
//	@Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int height = 0;
//        for(int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            int h = child.getMeasuredHeight();
//            if(h > height) height = h;
//        }
//
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @param view the base view with already measured height
     *
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec, View view) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            // set the height from the base view if available
            if (view != null) {
                result = view.getMeasuredHeight();
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

}
//	@Override  
//    protected void onMeasure(int arg0, int arg1)  
//    {  
//        int height=0;  
//        for(int i=0;i<getChildCount();i++){  
//            View v=getChildAt(i);  
//            v.measure(arg0, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));  
//            int h=v.getMeasuredHeight();  
//            if(h>height)  
//                height=h;  
//              
//        }  
//        arg1=MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);  
//        super.onMeasure(arg0, arg1);  
//    }  
//}
