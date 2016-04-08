package com.tuanche.askforanswer.app.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.tuanche.askforanswer.app.core.Session;

/**
 * 常用工具类（如：网络是否可用等）
 * 
 * @author liaiguo
 * 
 */
public class CommonUtils {

	private static final String TAG = "CommonUtils";

	/**
	 * 判断网络是否可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * long类型时间格式化
	 */
	public static String convertToTime(long time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		return df.format(date);
	}

	/**
	 * 判断是否为合法的json
	 * 
	 * @param jsonContent
	 *            需判断的字串
	 */
	public static boolean isJsonFormat(String jsonContent) {
		try {
			new JsonParser().parse(jsonContent);
			return true;
		} catch (JsonParseException e) {
			return false;
		}
	}

	/**
	 * 格式化时间
	 * 
	 * @param seconds
	 * @return
	 */
	public static String formatTime(int seconds) {
		StringBuilder time = new StringBuilder();

		int day = seconds / (24 * 60 * 60);
		int remainder = seconds - day * 24 * 60 * 60;

		int hours = remainder / (60 * 60);
		remainder = remainder - hours * 60 * 60;

		int minutes = remainder / 60;

		remainder = remainder - minutes * 60;

		int second = remainder;

		if (seconds == 0) {
			time.append("0天0时0分");
			return time.toString();
		}

		if (day > 0) {
			time.append(day + "天");
		}
		if (hours > 0) {
			time.append(hours + "时");
		}
		if (minutes > 0) {
			time.append(minutes + "分");
		}

		return time.toString();
	}

	/**
	 * 格式化距离
	 * 
	 * @param distance
	 * @return
	 */
	public static String formatDistance(double distance) {
		DecimalFormat df = new DecimalFormat("0.00");
		String d = "";
		if (distance >= 1000.0) {
			d = df.format(distance / 1000.0) + "km";
		} else {
			d = distance + "m";
		}
		return d;
	}

	/**
	 * 显示进度
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param indeterminate
	 * @param cancelable
	 * @return
	 */
	public static ProgressDialog showProgress(Context context,
			CharSequence title, CharSequence message, boolean indeterminate,
			boolean cancelable) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setIndeterminate(indeterminate);
		dialog.setCancelable(cancelable);
		dialog.setCanceledOnTouchOutside(false);
		// dialog.setDefaultButton(false);
		dialog.show();
		return dialog;
	}

	public static void setListViewHeightBasedOnChildren(ListView listView,
			boolean addHeight, float density) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			if (null != listItem) {
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		if (addHeight) {
			if (density < 1.0f) {
				params.height += (int) (density * 30 + 0.5f);
			} else {
				params.height += (int) (density * 8 + 0.5f);
			}
		} else {
			if (density >= 1.0f && density <= 1.5f) {
				params.height += (int) (density * 10 + 0.5f);
			}
			if (density < 1.0f) {
				params.height += (int) (density * 30 + 0.5f);
			}
			if (density >= 2.0f) {
				params.height += (int) (density * 6 + 0.5f);
			}
		}

		listView.setLayoutParams(params);
	}

	/**
	 * 测量ListView的高度
	 * 
	 * @param listView
	 * @return
	 */
	public static int measureListViewHeight(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return 0;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}
		int height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		return height;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(float density, float dpValue) {
		return (int) (dpValue * density + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取格式化的时间
	 * 
	 * @param time
	 * @return
	 */
	public static String getDateFormatString(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		return sdf.format(new Date(time));
	}

	/**
	 * 字符串反转
	 */
	public static String reverse(String s) {
		int length = s.length();
		if (length <= 1)
			return s;
		String left = s.substring(0, length / 2);
		String right = s.substring(length / 2, length);
		return reverse(right) + reverse(left);
	}

	

	/**
	 * 开启activity
	 */
	public static void launchActivity(Context context, Class<?> activity) {
		Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivity(intent);
	}

	public static void launchActivityForResult(Activity context,
			Class<?> activity, int requestCode) {
		Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivityForResult(intent, requestCode);
	}


	/**
	 * 格式化金额
	 */
	public static String formatMoney(String money) {
		if (TextUtils.isEmpty(money)) {
			return "";
		}
		int dotIndex = 0;
		// 如3.0 或者3.00
		if (money.endsWith(".0") || money.endsWith(".00")) {
			dotIndex = money.indexOf(".");
			money = money.substring(0, dotIndex);
		}
		// 如3.30
		if (money.endsWith("0") && money.contains(".")) {
			money = money.substring(0, money.lastIndexOf("0"));
		}
		return money;
	}

	
	public static boolean hasLogin(Session session){
		String token = session.getToken();
		if (TextUtils.isEmpty(token)||"0".equals(token)) {
			return false;
		}else {
			return true;
		}
	}
	/**
	 * 处理拉手券，每隔四位来一个空格显示
	 * @param code
	 * @return
	 */
	public static String dealLashouCode(String code){
		if(TextUtils.isEmpty(code)){
			return "";
		}else{
			StringBuffer sb = new StringBuffer();
			char[] charArray = code.toCharArray();
			for(int index=0;index<charArray.length;index++){
				if(index%4==0 && index!=0)
					sb.append(" ");
				sb.append(charArray[index]);
			}
			return sb.toString();
		}
		
	}
}
