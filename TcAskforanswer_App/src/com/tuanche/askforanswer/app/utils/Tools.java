package com.tuanche.askforanswer.app.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Location;
import android.media.ExifInterface;
import android.net.DhcpInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Window;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.tuanche.api.utils.AppUtils;
import com.tuanche.api.utils.AppUtils.StorageFile;
import com.tuanche.askforanswer.app.core.AppApi;

public class Tools {
	/**
	 * 判断是否为空
	 * 
	 * @param text
	 * @return true null false !null
	 */
	public static boolean isNull(String text) {
		if (text == null || "".equals(text.trim())
				|| "null".equals(text.trim()) || "0".equals(text.trim()))
			return true;
		return false;
	}

	public static boolean isEmptyList(List list) {
		return (list != null && list.size() > 0) ? false : true;
	}

	/**
	 * 获取标题栏和状态栏的总体高度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getstatusBarAndtitleBarHeight(Activity activity) {
		int contentViewTop = 0;
		// Rect rect = new Rect();
		Window window = activity.getWindow();
		// view.getWindowVisibleDisplayFrame(rect);
		// 状态栏的高度
		// int statusBarHeight = rect.top;
		// 标题栏跟状态栏的总体高度
		contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT)
				.getTop();
		// 标题栏的高度：用上面的值减去状态栏的高度及为标题栏高度
		// int titleBarHeight = contentViewTop - statusBarHeight;
		return contentViewTop;
	}

	/**
	 * 格式化距离
	 * 
	 * @param m
	 * @return
	 */
	public static String formatDistance(String m) {
		double mDou = 0;
		String mEnd = "m";
		String kmEnd = "km";
		if (isNull(m)) {
			m = "0";
		}
		try {
			mDou = Double.parseDouble(m);
			if (mDou >= 1000) {
				mDou = mDou / 1000;
				String re = String.valueOf(mDou);
				int index = re.indexOf(".");
				if (index > 0) {
					re = re.substring(0, index + 2);
				}
				return re + kmEnd;
			}

		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
		return m + mEnd;
	}

	// 4,//智能排序
	// 5,//离我最近
	// 1,//最新发布
	// 2,//人气最高
	// 3,//价格最低
	// 6//价格最高
	/**
	 * 根据id返回名字
	 * 
	 * @param id
	 * @return
	 */
	public static final String getSortName(String id) {
		int i = 0;
		if (isNull(id)) {
			id = "0";
		}
		try {
			i = Integer.parseInt(id);
		} catch (NumberFormatException e) {
		}
		switch (i) {
		case 1:
			return "最新发布";
		case 2:
			return "人气最高";
		case 3:
			return "价格最低";
		case 4:
			return "智能排序";
		case 5:
			return "离我最近";
		case 6:
			return "价格最高";
		default:
			break;
		}
		return "智能排序";
	}

	/**
	 * 根据名字返回id
	 * 
	 * @param name
	 * @return
	 */
	public static final String getSortID(String name) {
		if (isNull(name)) {
			return "4";
		} else if (name.equals("最新发布")) {
			return "1";
		} else if (name.equals("人气最高")) {
			return "2";
		} else if (name.equals("销量最高")) {
			return "2";
		} else if (name.equals("价格最低")) {
			return "3";
		} else if (name.equals("智能排序")) {
			return "4";
		} else if (name.equals("离我最近")) {
			return "5";
		} else if (name.equals("价格最高")) {
			return "6";
		}
		return "4";
	}

	public static final String fullLocation(String address) {
		return "当前:" + address;
	}

	public static final String getDistanceID(String name) {
		if (isNull(name)) {
			return "3000";
		}
		if (name.equals("1千米"))
			return "1000";
		if (name.equals("3千米"))
			return "3000";
		if (name.equals("5千米"))
			return "5000";
		if (name.equals("10千米"))
			return "10000";
		if (name.equals("全城"))
			return "50000";
		return "3000";
	}

	/**
	 * 将像素转换成厘米
	 * 
	 * @param px
	 * @return
	 */
	public static double getCmFromPx(Context context, int px) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		displayMetrics = context.getResources().getDisplayMetrics();
		int densityDpi = displayMetrics.densityDpi;
		return px / densityDpi * 2.45;
	}

	/**
	 * qu chong
	 * 
	 * @param latLng
	 * @return
	 */
	public static LatLng getRandomLatLng(LatLng latLng) {
		Random random = new Random();
		return new LatLng(latLng.latitude + random.nextDouble(),
				latLng.longitude + random.nextDouble());
	}

	// 去掉括号
	public static String deleteBrackets(String src) {
		src = src.replace("（", "(");
		src = src.replace("）", ")");
		src = src.replace("(", "");
		src = src.replace(")", "");
		return src;
	}

	/**
	 * 用来判断服务是否运行.
	 * 
	 * @param context
	 * @param className
	 *            判断的服务名字
	 * @return true 在运行 false 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	/**
	 * 是否打开不保留活动选项
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNotKeepActivities(Context context) {
		ContentResolver cr = context.getContentResolver();
		int flag = android.provider.Settings.System.getInt(cr,
				Settings.System.ALWAYS_FINISH_ACTIVITIES, 0);
		if (flag == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取一个字符串，由类名+"," 组成
	 * 
	 * @param clazz
	 * @return
	 */
	// public static String getString(Class<? extends Activity> ...clazz){
	// if(clazz == null)
	// return "";
	// if(clazz.length==0){
	// return "";
	// }
	// StringBuilder sb = new StringBuilder();
	// for (Class<? extends Activity> class1 : clazz) {
	// sb.append(class1.getSimpleName()).append(",");
	// }
	// sb.deleteCharAt(sb.length()-1);
	// return sb.toString();
	// }
	//
	// public static void sendPopSpecialActivityBroadcast(Context
	// context,Class<? extends Activity> ...clazz){
	// Intent intent = new Intent(ConstantValues.CLOSE_SPECIAL_ACTIVITY);
	// intent.putExtra(PopSpecialActivityReceiver.ACTIVITIES,
	// Tools.getString(clazz));
	// Logger.i("info", "发送广播");
	// context.sendBroadcast(intent);
	// }


	/**
	 * 格式化地理信息
	 * 
	 * @param address
	 * @return
	 */
	public static String formatLocation(RegeocodeAddress address) {
		StringBuffer buffer = new StringBuffer();
		if (address != null) {
			buffer.append(address.getDistrict());
			buffer.append(address.getNeighborhood());
			if (address.getStreetNumber() != null) {
				buffer.append(address.getStreetNumber().getStreet()).append(
						address.getStreetNumber().getNumber());
			}
		}
		return buffer.toString();
	}

	/**
	 * 判断两个地理坐标之间是否超过100米
	 * 
	 * @param SLat
	 * @param SLng
	 * @param ELat
	 * @param ELng
	 * @return
	 */
	public static boolean isExceedLocation(double SLat, double SLng,
			double ELat, double ELng) {
		if (SLat == 0 || SLng == 0 || ELat == 0 || ELng == 0)
			return true;
		float result[] = new float[1];
		Location.distanceBetween(SLat, SLng, ELat, ELng, result);
		if (result[0] >= ConstantValues.LOCATION_RANGE) {
			return true;
		}
		return false;
	}

	public static boolean isNeedReLoad(LatLonPoint oldPoint,
			LatLonPoint newPoint) {
		boolean result = false;
		try {
			LatLng old = new LatLng(oldPoint.getLatitude(),
					oldPoint.getLongitude());
			LatLng newLatLng = new LatLng(newPoint.getLatitude(),
					newPoint.getLongitude());
			float distance = AMapUtils.calculateLineDistance(old, newLatLng);
			if (distance > 100) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static float valueOfFloat(String str) {
		if (str == null || "".equals(str.trim()))
			return 0;

		try {
			return Float.valueOf(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static double valueOf(String str) {
		if (str == null || "".equals(str.trim()))
			return 0;

		try {
			return Double.valueOf(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return 1d;
	}

	public static int valueOfInt(String str) {
		if (str == null || "".equals(str))
			return 0;
		try {
			return Integer.valueOf(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return 1;
	}

	public static double valueOfDouble(double f) {
		String s = f + "";
		double d = 0.0;
		try {
			d = Double.valueOf(s);
		} catch (Exception e) {
		}
		return d;
	}

	/**
	 * 根据产品类型id获取产品类型名称
	 * 
	 * @param type
	 * @return
	 */
	public static String getCategory(String goodsType) {
		int type = Tools.valueOfInt(goodsType);
		switch (type) {
		case 1:
			return "本地生活";
		case 2:
			return "购买";
		case 3:
			return "抽奖";
		case 4:
			return "优惠券";
		default:
			return "其他";
		}
	}

	/**
	 * 获取Wifi 网关地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getWifiGateWay(Context context) {
		WifiManager wifimanage = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);// 获取WifiManager

		// 检查wifi是否开启
		if (!wifimanage.isWifiEnabled()) {

			return "";
			// wifimanage.setWifiEnabled(true);
		}

		DhcpInfo dhcpinfo = wifimanage.getDhcpInfo();

		String addressId = Formatter.formatIpAddress(dhcpinfo.gateway);

		return addressId;
	}

	public static String getSpName(String categoryStr) {
		String result = "商家名称";
		try {
			result = categoryStr.substring(categoryStr.indexOf("###") + 3,
					categoryStr.length());
		} catch (Exception e) {

		}

		return result;
	}

	public static String getCategoryStr(String categoryStr) {
		String result = "其他";
		try {
			result = categoryStr.substring(0, categoryStr.indexOf("###"));
		} catch (Exception e) {

		}

		return result;
	}

	/**
	 * Google 统计电子商务交易
	 * 
	 * @param transactionId
	 *            订单号
	 * @param affiliation
	 *            公司，商家
	 * @param orderRevenue
	 *            总价
	 * @param tax
	 *            总税费
	 * @param shipping
	 *            总运费
	 * @param currencyCode
	 *            货币类型
	 * @param productName
	 *            商品名称
	 * @param productSKU
	 *            商品编号
	 * @param productCategory
	 *            商品类别
	 * @param productPrice
	 *            商品价格
	 * @param productQuantity
	 *            商品数量
	 */
	public static void googleAnalytic(Context context, String transactionId,
			String affiliation, Double orderRevenue, Double tax,
			Double shipping, String currencyCode, String productName,
			String productSKU, String productCategory, Double productPrice,
			Long productQuantity) {

	}

	/**
	 * 不显示空字符串
	 * 
	 * @param text
	 * @return
	 */
	public static String notShowEmptyCharacter(String text) {
		if (text == null || "".equals(text.trim()) || "null".equals(text))
			return "";
		return text;
	}

	/**
	 * 不显示空字符串
	 * 
	 * @param emptyTip
	 * @param text
	 * @return
	 */
	public static String notShowEmptyCharacter(String emptyTip, String text) {
		if (text == null || "".equals(text.trim()) || "null".equals(text))
			return emptyTip;
		return text;
	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	public static int sizeOf(Bitmap data) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
			return data.getRowBytes() * data.getHeight();
		} else {
			return data.getByteCount();
		}
	}

	public static String saveBitmap(Context context, Uri uri) {
		Activity activity = (Activity) context;
		String[] proj = { MediaStore.Images.Media.DATA };
		// CursorLoader cursorLoader = new CursorLoader(context, uri, proj,
		// null,null,null);
		// cursorLoader.loadInBackground();
		@SuppressWarnings("deprecation")
		Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null,
				null);
		int actual_image_column_index = actualimagecursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		actualimagecursor.moveToFirst();
		String img_path = actualimagecursor
				.getString(actual_image_column_index);
		try {
			if (actualimagecursor != null && !actualimagecursor.isClosed()) {
				actualimagecursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Bitmap bitmap = getBitmap(img_path);
		Bitmap bitmap = compressImageFromFile(img_path);

		return saveBitmap(context, bitmap);
	}

	public static String saveBitmap(Context context, String imgPath) {
		// Bitmap mBitmap = getBitmap(imgPath);
		Bitmap mBitmap = compressImageFromFile(imgPath);
		return saveBitmap(context, mBitmap);
	}
	
	@SuppressLint("SimpleDateFormat")
	private static String saveBitmap(Context context, Bitmap mBitmap) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String path = AppUtils.getPath(context, StorageFile.cache) + "IMG_"
				+ timeStamp + "_" + System.currentTimeMillis() + ".jpg";
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		try {
			if (fOut != null) {
				fOut.flush();
				fOut.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

	public static Bitmap getBitmap(String oldPath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(oldPath, options);
		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(oldPath, options);
		Bitmap bbb = compressImage(bitmap);
		return bbb;
	}

	private static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			options -= 10;// 每次都减少10
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	/**
	 * 将一个图片变灰
	 * 
	 * @param bitmap
	 * @return
	 */
	public static final Bitmap grey(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(faceIconGreyBitmap);
		Paint paint = new Paint();
		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0);
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
				colorMatrix);
		paint.setColorFilter(colorMatrixFilter);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return faceIconGreyBitmap;
	}

	public static String replaceEmptyCharacter(String string) {
		return notShowEmptyCharacter(string).replaceAll("<br/>", "")
				.replaceAll("\r\n", "").replaceAll("\n", "");
	}

	public static void delectDownloadApk(final Context context) {
		new Thread() {
			public void run() {
				String target = AppUtils.getPath(context, StorageFile.cache);
				String targetApk = target + AppApi.TC_APK_DOWNLOAD_FILENAME;
				File tarFile = new File(targetApk);
				if (tarFile.exists()) {
					tarFile.delete();
				}
			};
		}.start();
	}

	private static Bitmap compressImageFromFile(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;// 只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
 
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 800f;//
		float ww = 480f;//
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置采样率

		newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		// return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
		// 其实是无效的,大家尽管尝试
		return bitmap;
	}

	public static int getRotation(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static void rotationBitmap(String path, int degree) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		// 创建新的图片
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		OutputStream out = null;
		try {
			out = new FileOutputStream(path);
			resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
