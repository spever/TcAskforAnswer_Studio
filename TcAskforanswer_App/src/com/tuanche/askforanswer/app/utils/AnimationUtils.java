package com.tuanche.askforanswer.app.utils;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * 动画工具类
 * @author Administrator
 *
 */
/**
 * 动画工具类
 * 动画从使用复杂度来说分为两类，基本动画和组合动画。
 * 从效果来说分为两大类，帧动画和补间动画。
 * 帧动画：一帧一帧的播放，类似gif的动画方式。
 * 补间动画：通过透明度、位移、旋转、缩放的方式来播放动画。
 * 动画的执行流程：
 * 一、创建动画对象，Animation或AnimationSet。
 * 二、设置动画播放效果，播放时间、播放后是否保留在最后一帧等。
 * 三、播放动画。
 * 
 * Interpolator:动画播放速率,常用的有九种AccelerateDeccelerateIntepolator、AccelerateIntepolator、DeccelerateInterpolator、CycleInterpolator、LinearInterpolator、BounceInterpolator、AnticipateInterpolator、OvershootInterpolator、AnticipateOvershootInterpolator
 * AccelerateDeccelerateInterpolator:先加速在减速
 * AccelerateInterpolator：加速
 * DeccelerateInterpolator：减速
 * LinearInterpolator：匀速
 * CycleInterpolator：正玄曲线速率变化,速率一直在变化
 * AnticipateInterpolator：先回退 一小步在向前加速
 * OvershootInterpolator：超出终点一小步在回退到终点。
 * AnticipateOvershootInterpolator：AnticipateInterpolator和OvershootInterpolator的组合
 * BounceInterpolator：终点回弹。
 * 
 * 自定义动画播放速率，详情见官方文档。
 * @author hezd
 *
 */
public class AnimationUtils {
	public static final int DEFAULT = -1;
	public static final int LINEAR_INTERPOLATOR = 0;
	public static final int ACCELERATE_DECCELERATE_INTERPOLATOR = 1;
	public static final int ACCELERATE_INTERPOLATOR = 2;
	public static final int DECELERATE_INTERPOLATOR = 3;
	public static final int CYCLE_INTERPOLATOR = 4;
	public static final int BOUNCE_INTERPOLATOR = 5;
	public static final int ANTICIPATE_OVERSHOOT_INTERPOLATOR = 6;
	public static final int ANTICIPATE_INTERPOLATOR = 7;
	public static final int OVERSHOOT_INTERPOLATOR = 8;
	
	/**
	 * 设置动画播放速率
	 * @param animation 动画对象
	 * @param interpolator 播放速率
	 * @param cycles 如果是旋转动画需要设置旋转几周
	 * @param durationMillis 动画播放时间
	 * @param startOffset 播放前停留多久
	 */
	public static void setEffect(Animation animation,int interpolator,int cycles,long durationMillis,long startOffset) {
		switch (interpolator) {
		case LINEAR_INTERPOLATOR:
			animation.setInterpolator(new LinearInterpolator());
			break;
		case ACCELERATE_DECCELERATE_INTERPOLATOR:
			animation.setInterpolator(new AccelerateDecelerateInterpolator());
			break;
		case ACCELERATE_INTERPOLATOR:
			animation.setInterpolator(new AccelerateInterpolator());
			break;
		case DECELERATE_INTERPOLATOR:
			animation.setInterpolator(new DecelerateInterpolator());
			break;
		case CYCLE_INTERPOLATOR:
			animation.setInterpolator(new CycleInterpolator(cycles));
			break;
		case ANTICIPATE_OVERSHOOT_INTERPOLATOR:
			animation.setInterpolator(new AnticipateInterpolator());
			break;
		case ANTICIPATE_INTERPOLATOR:
			animation.setInterpolator(new AnticipateInterpolator());
			break;
		case OVERSHOOT_INTERPOLATOR:
			animation.setInterpolator(new OvershootInterpolator());
		break;
		default:
			break;
		}
		animation.setDuration(durationMillis);
		animation.setStartOffset(startOffset);
		animation.setFillAfter(true);
	}
	
	
	public static void startAnimation(View view,Animation animation,int repeatMode,int repeatCount) {
		animation.setRepeatMode(repeatMode);
		animation.setRepeatCount(repeatCount);
		
//		animation.setFillBefore(fillBefore);
//		animation.setAnimationListener(new CustomAnimationListener());
		view.startAnimation(animation);
	}
	
	public static class CustomAnimationListener implements AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {
			
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}
		
	}
}
