package com.tuanche.askforanswer.app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.tuanche.askforanswer.app.core.Session;
import com.tuanche.askforanswer.source.application.MyApp;

public class STIDUtil {

	private static File file = new File(
			"data/data/com.lashou.groupurchasing/file.xml");

	public static String toSTID(Context context) {
		String softName = "groupbuy";
		String deviceName = "android";
		String source = "lashou";
		String userId = "";
		String cityId = "";
		String deviceId = "000000000000";
		String softVersion = "";
		String STID = "";

		String idfa = "0";
		String openudid = "0";

		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			softVersion = info.versionName;

			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);

			source = getChannelIdByChannelName(ai.metaData
					.getString("UMENG_CHANNEL"));

			deviceId = getDeviceId(context);

			// userId =
			// Preferences.getUserId(PreferenceManager.getDefaultSharedPreferences(context));
			Session session = Session.get(context);
//			userId = session.getUser_id();
//			cityId = session.getCity_id();

			STID = softName
					+ "_"
					+ softVersion
					+ "_"
					+ deviceName
					+ "_"
					+ source
					+ "_"
					+ deviceId
					+ "_"
					+ userId
					+ "_"
					+ cityId
					+ "_"
					+ replaceBlank((android.os.Build.MANUFACTURER + android.os.Build.MODEL)
							.replace("_", "").trim())
					+ ","
					+ ((MyApp) context.getApplicationContext())
							.getmScreenSize() + "_"
					+ replaceBlank(android.os.Build.VERSION.RELEASE) + "_"
					+ idfa + "_" + openudid + "_" + getMacId(context);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return STID;
	}

	// add by chenqiang 20130912 s
	// public static String toSTIDForNearby(Context context){
	// String softName = "groupbuy";
	// String deviceName = "android";
	// String source = "lashou";
	// String userId = "";
	// String cityId = "";
	// String deviceId = "000000000000";
	// String softVersion = "";
	// String STID = "";
	// try {
	// PackageInfo info =
	// context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
	// softVersion = info.versionName;
	//
	// ApplicationInfo ai =
	// context.getPackageManager().getApplicationInfo(context.getPackageName(),
	// PackageManager.GET_META_DATA);
	// source =
	// getChannelIdByChannelName(ai.metaData.getString("UMENG_CHANNEL"));
	//
	// deviceId = getDeviceId(context);
	// Logger.i("stid device-->", deviceId);
	//
	// SharedPreferences pre =
	// PreferenceManager.getDefaultSharedPreferences(context);
	// userId = Preferences.getUserId(pre);
	// cityId = Preferences.getLocationCity(pre).getCityId();
	//
	// STID = softName + "_" + softVersion + "_" + deviceName + "_" + source +
	// "_" + deviceId + "_" + userId + "_" + cityId + "_"
	// + replaceBlank((android.os.Build.MANUFACTURER +
	// android.os.Build.MODEL).replace("_", "").trim()) + ","
	// + ((GroupPurchaseApplication)
	// context.getApplicationContext()).getScreenSize() + "_" +
	// replaceBlank(android.os.Build.VERSION.RELEASE);
	//
	// } catch (NameNotFoundException e) {
	// }
	// return STID;
	// }

	public static String getDeviceId(Context context) {
		String deviceId = "";
		try {
			deviceId = getIMIEStatus(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (deviceId == null || "".equals(deviceId) || "0".equals(deviceId)) {
			try {
				deviceId = getLocalMac(context).replace(":", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (deviceId == null || "".equals(deviceId) || "0".equals(deviceId)) {
			try {
				deviceId = getAndroidId(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (deviceId == null || "".equals(deviceId) || "0".equals(deviceId)) {
			try {
				deviceId = read();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (deviceId == null || "".equals(deviceId) || "0".equals(deviceId)) {
				UUID uuid = UUID.randomUUID();
				deviceId = uuid.toString().replace("-", "");
				write(deviceId);
			}
		}
		return deviceId;
	}

	private static String replaceBlank(String mString) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		String str = mString;
		Matcher m = p.matcher(str);
		String after = m.replaceAll("");
		return after;
	}

	private static String getMacId(Context context) {
		String mac = "";
		try {
			mac = getLocalMac(context);
		} catch (Exception e) {

		}

		if (TextUtils.isEmpty(mac) || "0".equals(mac)) {
			try {
				mac = getAndroidId(context);
			} catch (Exception e) {

			}
		}

		return mac;
	}

	// IMEI码
	private static String getIMIEStatus(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = tm.getDeviceId();
		return deviceId;
	}

	// Mac地址
	private static String getLocalMac(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	// Mac地址
	private static String getAndroidId(Context context) {
		String androidId = Settings.Secure.getString(
				context.getContentResolver(), Settings.Secure.ANDROID_ID);
		return androidId;
	}

	public static void write(String str) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(str);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String read() {
		StringBuffer buffer = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			Reader in = new BufferedReader(isr);
			int i;
			while ((i = in.read()) > -1) {
				buffer.append((char) i);
			}
			in.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getChannelIdByChannelName(String channelName) {
		String channelId = "10062";
		if ("91market".equals(channelName)) {channelId = "10001";
		} else if ("aibala".equals(channelName)) {channelId = "10002";
		} else if ("aimi8".equals(channelName)) {channelId = "10003";
		} else if ("aliyun".equals(channelName)) {channelId = "10004";
		} else if ("ChinaMobile".equals(channelName)) {channelId = "10005";
		} else if ("androidmarket".equals(channelName)) {channelId = "10006";
		} else if ("anzhi".equals(channelName)) {channelId = "10007";
		} else if ("anzhuo".equals(channelName)) {channelId = "10008";
		} else if ("anzhuoluntan".equals(channelName)) {channelId = "10009";
		} else if ("bangbang".equals(channelName)) {channelId = "10010";
		} else if ("baoping".equals(channelName)) {channelId = "10011";
		} else if ("bubugao".equals(channelName)) {channelId = "10012";
		} else if ("dangle".equals(channelName)) {channelId = "10013";
		} else if ("duomeng001".equals(channelName)) {channelId = "10014";
		} else if ("dx01".equals(channelName)) {channelId = "10015";
		} else if ("dx02".equals(channelName)) {channelId = "10016";
		} else if ("dx03".equals(channelName)) {channelId = "10017";
		} else if ("dx04".equals(channelName)) {channelId = "10018";
		} else if ("dx05".equals(channelName)) {channelId = "10019";
		} else if ("dx06".equals(channelName)) {channelId = "10020";
		} else if ("dx07".equals(channelName)) {channelId = "10021";
		} else if ("dx08".equals(channelName)) {channelId = "10022";
		} else if ("dx09".equals(channelName)) {channelId = "10023";
		} else if ("dx10".equals(channelName)) {channelId = "10024";
		} else if ("dx11".equals(channelName)) {channelId = "10025";
		} else if ("dx12".equals(channelName)) {channelId = "10026";
		} else if ("dx13".equals(channelName)) {channelId = "10027";
		} else if ("dx14".equals(channelName)) {channelId = "10028";
		} else if ("dx15".equals(channelName)) {channelId = "10029";
		} else if ("dx16".equals(channelName)) {channelId = "10030";
		} else if ("dx18".equals(channelName)) {channelId = "10031";
		} else if ("dx19".equals(channelName)) {channelId = "10032";
		} else if ("dx20".equals(channelName)) {channelId = "10033";
		} else if ("dx21".equals(channelName)) {channelId = "10034";
		} else if ("dx22".equals(channelName)) {channelId = "10035";
		} else if ("dx23".equals(channelName)) {channelId = "10036";
		} else if ("dx24".equals(channelName)) {channelId = "10037";
		} else if ("dx25".equals(channelName)) {channelId = "10038";
		} else if ("eoemarket".equals(channelName)) {channelId = "10039";
		} else if ("feiyangwuxian".equals(channelName)) {channelId = "10040";
		} else if ("gaode".equals(channelName)) {channelId = "10041";
		} else if ("gaoyang001".equals(channelName)) {channelId = "10042";
		} else if ("gaoyang005".equals(channelName)) {channelId = "10043";
		} else if ("jiashi001".equals(channelName)) {channelId = "10044";
		} else if ("jiashi002".equals(channelName)) {channelId = "10045";
		} else if ("jiashi003".equals(channelName)) {channelId = "10046";
		} else if ("jiashi004".equals(channelName)) {channelId = "10047";
		} else if ("jiashi005".equals(channelName)) {channelId = "10048";
		} else if ("jiashi006".equals(channelName)) {channelId = "10049";
		} else if ("jiashi007".equals(channelName)) {channelId = "10050";
		} else if ("jiashi008".equals(channelName)) {channelId = "10051";
		} else if ("jiashi009".equals(channelName)) {channelId = "10052";
		} else if ("jiashi010".equals(channelName)) {channelId = "10053";
		} else if ("jiashi011".equals(channelName)) {channelId = "10054";
		} else if ("jiashi012".equals(channelName)) {channelId = "10055";
		} else if ("jiashi013".equals(channelName)) {channelId = "10056";
		} else if ("jiashi014".equals(channelName)) {channelId = "10057";
		} else if ("jiashi015".equals(channelName)) {channelId = "10058";
		} else if ("jifeng".equals(channelName)) {channelId = "10059";
		} else if ("junzheng".equals(channelName)) {channelId = "10060";
		} else if ("lanmo".equals(channelName)) {channelId = "10061";
		} else if ("lashou".equals(channelName)) {channelId = "10062";
		} else if ("lenovo".equals(channelName)) {channelId = "10063";
		} else if ("meizu".equals(channelName)) {channelId = "10064";
		} else if ("mike".equals(channelName)) {channelId = "10065";
		} else if ("motorolar".equals(channelName)) {channelId = "10066";
		} else if ("mumayi".equals(channelName)) {channelId = "10067";
		} else if ("nduo".equals(channelName)) {channelId = "10068";
		} else if ("newsmy".equals(channelName)) {channelId = "10069";
		} else if ("qq-appstore".equals(channelName)) {channelId = "10070";
		} else if ("renexing".equals(channelName)) {channelId = "10071";
		} else if ("samsung".equals(channelName)) {channelId = "10072";
		} else if ("sina-soft".equals(channelName)) {channelId = "10073";
		} else if ("sina_weibo".equals(channelName)) {channelId = "10074";
		} else if ("skycn".equals(channelName)) {channelId = "10075";
		} else if ("test".equals(channelName)) {channelId = "10076";
		} else if ("waps001".equals(channelName)) {channelId = "10077";
		} else if ("waps002".equals(channelName)) {channelId = "10078";
		} else if ("waps003".equals(channelName)) {channelId = "10079";
		} else if ("waps004".equals(channelName)) {channelId = "10080";
		} else if ("waps005".equals(channelName)) {channelId = "10081";
		} else if ("waps006".equals(channelName)) {channelId = "10082";
		} else if ("waps007".equals(channelName)) {channelId = "10083";
		} else if ("waps008".equals(channelName)) {channelId = "10084";
		} else if ("waps009".equals(channelName)) {channelId = "10085";
		} else if ("waps010".equals(channelName)) {channelId = "10086";
		} else if ("waps011".equals(channelName)) {channelId = "10087";
		} else if ("waps012".equals(channelName)) {channelId = "10088";
		} else if ("waps013".equals(channelName)) {channelId = "10089";
		} else if ("waps014".equals(channelName)) {channelId = "10090";
		} else if ("waps015".equals(channelName)) {channelId = "10091";
		} else if ("yingyonghui".equals(channelName)) {channelId = "10092";
		} else if ("yuxingshuma".equals(channelName)) {channelId = "10093";
		} else if ("163-appstore".equals(channelName)) {channelId = "10094";
		} else if ("oppo".equals(channelName)) {channelId = "10095";
		} else if ("dx17".equals(channelName)) {channelId = "10096";
		} else if ("gaoyang002".equals(channelName)) {channelId = "10097";
		} else if ("gaoyang003".equals(channelName)) {channelId = "10098";
		} else if ("gaoyang004".equals(channelName)) {channelId = "10099";
		} else if ("paojiao".equals(channelName)) {channelId = "10100";
		} else if ("liantongduancai".equals(channelName)) {channelId = "10101";
		} else if ("xingkong".equals(channelName)) {channelId = "10102";
		} else if ("huawei".equals(channelName)) {channelId = "10103";
		} else if ("liqucn".equals(channelName)) {channelId = "10104";
		} else if ("anzu".equals(channelName)) {channelId = "10105";
		} else if ("feifan".equals(channelName)) {channelId = "10106";
		} else if ("feipeng".equals(channelName)) {channelId = "10107";
		} else if ("xiyouyou".equals(channelName)) {channelId = "10108";
		} else if ("sohushuma".equals(channelName)) {channelId = "10109";
		} else if ("aizhuo".equals(channelName)) {channelId = "10110";
		} else if ("aoyou".equals(channelName)) {channelId = "10111";
		} else if ("tiantian".equals(channelName)) {channelId = "10112";
		} else if ("anzhuozaixian".equals(channelName)) {channelId = "10113";
		} else if ("hot".equals(channelName)) {channelId = "10114";
		} else if ("7xi".equals(channelName)) {channelId = "10115";
		} else if ("lezhi".equals(channelName)) {channelId = "10116";
		} else if ("anzhuoke".equals(channelName)) {channelId = "10117";
		} else if ("7xiazi".equals(channelName)) {channelId = "10118";
		} else if ("anfensi".equals(channelName)) {channelId = "10119";
		} else if ("anzhuodidai".equals(channelName)) {channelId = "10120";
		} else if ("kuqu".equals(channelName)) {channelId = "10121";
		} else if ("apkxyx".equals(channelName)) {channelId = "10122";
		} else if ("apk3".equals(channelName)) {channelId = "10123";
		} else if ("anzhuo4s".equals(channelName)) {channelId = "10124";
		} else if ("jiqimao".equals(channelName)) {channelId = "10125";
		} else if ("sonyericson".equals(channelName)) {channelId = "10126";
		} else if ("duote".equals(channelName)) {channelId = "10127";
		} else if ("anfeng".equals(channelName)) {channelId = "10128";
		} else if ("wangxun".equals(channelName)) {channelId = "10129";
		} else if ("jiashi016".equals(channelName)) {channelId = "10130";
		} else if ("jiashi017".equals(channelName)) {channelId = "10131";
		} else if ("jiashi018".equals(channelName)) {channelId = "10132";
		} else if ("jiashi019".equals(channelName)) {channelId = "10133";
		} else if ("jiashi020".equals(channelName)) {channelId = "10134";
		} else if ("uc001".equals(channelName)) {channelId = "10135";
		} else if ("wandoujia".equals(channelName)) {channelId = "10136";
		} else if ("youmi001".equals(channelName)) {channelId = "10137";
		} else if ("youmi002".equals(channelName)) {channelId = "10138";
		} else if ("youmi003".equals(channelName)) {channelId = "10139";
		} else if ("youmi005".equals(channelName)) {channelId = "10140";
		} else if ("youmi006".equals(channelName)) {channelId = "10141";
		} else if ("youmi007".equals(channelName)) {channelId = "10142";
		} else if ("youmi008".equals(channelName)) {channelId = "10143";
		} else if ("youmi009".equals(channelName)) {channelId = "10144";
		} else if ("youmi010".equals(channelName)) {channelId = "10145";
		} else if ("360appstore".equals(channelName)) {channelId = "10146";
		} else if ("jinliaoruan".equals(channelName)) {channelId = "10147";
		} else if ("youmi004".equals(channelName)) {channelId = "10148";
		} else if ("anzhiluntan".equals(channelName)) {channelId = "10149";
		} else if ("jifengluntan".equals(channelName)) {channelId = "10150";
		} else if ("mumayiluntan".equals(channelName)) {channelId = "10151";
		} else if ("qitaluntan".equals(channelName)) {channelId = "10152";
		} else if ("gaoyang006".equals(channelName)) {channelId = "10153";
		} else if ("gaoyang007".equals(channelName)) {channelId = "10154";
		} else if ("gaoyang008".equals(channelName)) {channelId = "10155";
		} else if ("yima001".equals(channelName)) {channelId = "10156";
		} else if ("yima002".equals(channelName)) {channelId = "10157";
		} else if ("yima003".equals(channelName)) {channelId = "10158";
		} else if ("yima004".equals(channelName)) {channelId = "10159";
		} else if ("yima005".equals(channelName)) {channelId = "10160";
		} else if ("yima006".equals(channelName)) {channelId = "10161";
		} else if ("yima007".equals(channelName)) {channelId = "10162";
		} else if ("yima008".equals(channelName)) {channelId = "10163";
		} else if ("yima009".equals(channelName)) {channelId = "10164";
		} else if ("yima010".equals(channelName)) {channelId = "10165";
		} else if ("ketai".equals(channelName)) {channelId = "10166";
		} else if ("pingan".equals(channelName)) {channelId = "10167";
		} else if ("shizimao".equals(channelName)) {channelId = "10168";
		} else if ("shoujizhijia".equals(channelName)) {channelId = "10169";
		} else if ("liantongwoshop".equals(channelName)) {channelId = "10170";
		} else if ("baidu".equals(channelName)) {channelId = "10171";
		} else if ("marketplace".equals(channelName)) {channelId = "10172";
		} else if ("lianxiang_intel".equals(channelName)) {channelId = "10173";
		} else if ("suning".equals(channelName)) {channelId = "10174";
		} else if ("xiaomi".equals(channelName)) {channelId = "10175";
		} else if ("microsoftMarket".equals(channelName)) {channelId = "10176";
		} else if ("unionpay".equals(channelName)) {channelId = "10177";
		} else if ("91shoujizhushou".equals(channelName)) {channelId = "10178";
		} else if ("duanxin".equals(channelName)) {channelId = "10180";
		} else if ("pplive".equals(channelName)) {channelId = "10181";
		} else if ("360shoujizhushou".equals(channelName)) {channelId = "10182";
		} else if ("91market2".equals(channelName)) {channelId = "10212";
		} else if ("yuzhuang".equals(channelName)) {channelId = "10214";
		} else if ("yuzhuang001".equals(channelName)) {channelId = "10215";
		} else if ("yuzhuang002".equals(channelName)) {channelId = "10216";
		} else if ("2345soft".equals(channelName)) {channelId = "10217";
		} else if ("sogou".equals(channelName)) {channelId = "10218";
		} else if ("baiduandroid".equals(channelName)) {channelId = "10219";
		} else if ("91jifenandroid".equals(channelName)) {channelId = "10221";
		} else if ("91jifeniphone".equals(channelName)) {channelId = "10222";
		} else if ("coolpad".equals(channelName)) {channelId = "10223";
		} else if ("tianyinyuzhuang".equals(channelName)) {channelId = "10224";
		} else if ("ucwebandroid".equals(channelName)) {channelId = "10225";
		} else if ("asdyuzhuang".equals(channelName)) {channelId = "10227";
		} else if ("asddianmian".equals(channelName)) {channelId = "10228";
		} else if ("sailongyuzhuang".equals(channelName)) {channelId = "10229";
		} else if ("admob".equals(channelName)) {channelId = "10230";
		} else if ("jihuomedia".equals(channelName)) {channelId = "10231";
		} else if ("tapjoy".equals(channelName)) {channelId = "10232";
		} else if ("coolpadstore".equals(channelName)) {channelId = "10234";
		} else if ("cmcyuzhuang".equals(channelName)) {channelId = "10235";
		} else if ("baidusou".equals(channelName)) {channelId = "10236";
		} else if ("dingdang01".equals(channelName)) {channelId = "10237";
		} else if ("dingdang02".equals(channelName)) {channelId = "10238";
		} else if ("dingdang03".equals(channelName)) {channelId = "10239";
		} else if ("dingdang04".equals(channelName)) {channelId = "10240";
		} else if ("jihuo001".equals(channelName)) {channelId = "10241";
		} else if ("jihuo002".equals(channelName)) {channelId = "10242";
		} else if ("jihuo003".equals(channelName)) {channelId = "10243";
		} else if ("jihuo004".equals(channelName)) {channelId = "10244";
		} else if ("jihuo005".equals(channelName)) {channelId = "10245";
		} else if ("jihuo006".equals(channelName)) {channelId = "10246";
		} else if ("jihuo007".equals(channelName)) {channelId = "10247";
		} else if ("jihuo008".equals(channelName)) {channelId = "10248";
		} else if ("jihuo009".equals(channelName)) {channelId = "10249";
		} else if ("jihuo010".equals(channelName)) {channelId = "10250";
		} else if ("jihuo011".equals(channelName)) {channelId = "10251";
		} else if ("jihuo012".equals(channelName)) {channelId = "10252";
		} else if ("jihuo013".equals(channelName)) {channelId = "10253";
		} else if ("jihuo014".equals(channelName)) {channelId = "10254";
		} else if ("jihuo015".equals(channelName)) {channelId = "10255";
		} else if ("jihuo016".equals(channelName)) {channelId = "10256";
		} else if ("jihuo017".equals(channelName)) {channelId = "10257";
		} else if ("jihuo018".equals(channelName)) {channelId = "10258";
		} else if ("jihuo019".equals(channelName)) {channelId = "10259";
		} else if ("jihuo020".equals(channelName)) {channelId = "10260";
		} else if ("jihuo021".equals(channelName)) {channelId = "10261";
		} else if ("jihuo022".equals(channelName)) {channelId = "10262";
		} else if ("jihuo023".equals(channelName)) {channelId = "10263";
		} else if ("jihuo024".equals(channelName)) {channelId = "10264";
		} else if ("jihuo025".equals(channelName)) {channelId = "10265";
		} else if ("jihuo026".equals(channelName)) {channelId = "10266";
		} else if ("jihuo027".equals(channelName)) {channelId = "10267";
		} else if ("jihuo028".equals(channelName)) {channelId = "10268";
		} else if ("jihuo029".equals(channelName)) {channelId = "10269";
		} else if ("jihuo030".equals(channelName)) {channelId = "10270";
		} else if ("jihuo031".equals(channelName)) {channelId = "10271";
		} else if ("jihuo032".equals(channelName)) {channelId = "10272";
		} else if ("jihuo033".equals(channelName)) {channelId = "10273";
		} else if ("jihuo034".equals(channelName)) {channelId = "10274";
		} else if ("jihuo035".equals(channelName)) {channelId = "10275";
		} else if ("jihuo036".equals(channelName)) {channelId = "10276";
		} else if ("jihuo037".equals(channelName)) {channelId = "10277";
		} else if ("jihuo038".equals(channelName)) {channelId = "10278";
		} else if ("jihuo039".equals(channelName)) {channelId = "10279";
		} else if ("jihuo040".equals(channelName)) {channelId = "10280";
		} else if ("jihuo041".equals(channelName)) {channelId = "10281";
		} else if ("jihuo042".equals(channelName)) {channelId = "10282";
		} else if ("jihuo043".equals(channelName)) {channelId = "10283";
		} else if ("jihuo044".equals(channelName)) {channelId = "10284";
		} else if ("jihuo045".equals(channelName)) {channelId = "10285";
		} else if ("jihuo046".equals(channelName)) {channelId = "10286";
		} else if ("jihuo047".equals(channelName)) {channelId = "10287";
		} else if ("jihuo048".equals(channelName)) {channelId = "10288";
		} else if ("jihuo049".equals(channelName)) {channelId = "10289";
		} else if ("jihuo050".equals(channelName)) {channelId = "10290";
		} else if ("jihuo051".equals(channelName)) {channelId = "10291";
		} else if ("jihuo052".equals(channelName)) {channelId = "10292";
		} else if ("jihuo053".equals(channelName)) {channelId = "10293";
		} else if ("jihuo054".equals(channelName)) {channelId = "10294";
		} else if ("jihuo055".equals(channelName)) {channelId = "10295";
		} else if ("jihuo056".equals(channelName)) {channelId = "10296";
		} else if ("jihuo057".equals(channelName)) {channelId = "10297";
		} else if ("jihuo058".equals(channelName)) {channelId = "10298";
		} else if ("jihuo059".equals(channelName)) {channelId = "10299";
		} else if ("jihuo060".equals(channelName)) {channelId = "10300";
		} else if ("jihuo061".equals(channelName)) {channelId = "10301";
		} else if ("jihuo062".equals(channelName)) {channelId = "10302";
		} else if ("jihuo063".equals(channelName)) {channelId = "10303";
		} else if ("jihuo064".equals(channelName)) {channelId = "10304";
		} else if ("jihuo065".equals(channelName)) {channelId = "10305";
		} else if ("jihuo066".equals(channelName)) {channelId = "10306";
		} else if ("jihuo067".equals(channelName)) {channelId = "10307";
		} else if ("jihuo068".equals(channelName)) {channelId = "10308";
		} else if ("jihuo069".equals(channelName)) {channelId = "10309";
		} else if ("jihuo070".equals(channelName)) {channelId = "10310";
		} else if ("jihuo071".equals(channelName)) {channelId = "10311";
		} else if ("jihuo072".equals(channelName)) {channelId = "10312";
		} else if ("jihuo073".equals(channelName)) {channelId = "10313";
		} else if ("jihuo074".equals(channelName)) {channelId = "10314";
		} else if ("jihuo075".equals(channelName)) {channelId = "10315";
		} else if ("jihuo076".equals(channelName)) {channelId = "10316";
		} else if ("jihuo077".equals(channelName)) {channelId = "10317";
		} else if ("jihuo078".equals(channelName)) {channelId = "10318";
		} else if ("jihuo079".equals(channelName)) {channelId = "10319";
		} else if ("zhichuang01".equals(channelName)) {channelId = "10320";
		} else if ("zhichuang02".equals(channelName)) {channelId = "10321";
		} else if ("zhichuang03".equals(channelName)) {channelId = "10322";
		} else if ("zhichuang04".equals(channelName)) {channelId = "10323";
		} else if ("dingdang05".equals(channelName)) {channelId = "10324";
		} else if ("dingdang06".equals(channelName)) {channelId = "10325";
		} else if ("dingdang07".equals(channelName)) {channelId = "10326";
		} else if ("dingdang08".equals(channelName)) {channelId = "10327";
		} else if ("jinshan".equals(channelName)) {channelId = "10328";
		} else if ("hao123".equals(channelName)) {channelId = "10329";
		} else if ("windowsphone".equals(channelName)) {channelId = "10330";
		} else if ("kuaiyong".equals(channelName)) {channelId = "10331";
		} else if ("erweima".equals(channelName)) {channelId = "10332";
		} else if ("lenovopush".equals(channelName)) {channelId = "10333";
		} else if ("gongxin".equals(channelName)) {channelId = "10334";
		} else if ("sogoumobile".equals(channelName)) {channelId = "10335";
		} else if ("jihuo080".equals(channelName)) {channelId = "10336";
		} else if ("jihuo081".equals(channelName)) {channelId = "10337";
		} else if ("jihuo082".equals(channelName)) {channelId = "10338";
		} else if ("jihuo083".equals(channelName)) {channelId = "10339";
		} else if ("jihuo084".equals(channelName)) {channelId = "10340";
		} else if ("jihuo085".equals(channelName)) {channelId = "10341";
		} else if ("jihuo086".equals(channelName)) {channelId = "10342";
		} else if ("jihuo087".equals(channelName)) {channelId = "10343";
		} else if ("jihuo088".equals(channelName)) {channelId = "10344";
		} else if ("jihuo089".equals(channelName)) {channelId = "10345";
		} else if ("jihuo090".equals(channelName)) {channelId = "10346";
		} else if ("jihuo091".equals(channelName)) {channelId = "10347";
		} else if ("jihuo092".equals(channelName)) {channelId = "10348";
		} else if ("jihuo093".equals(channelName)) {channelId = "10349";
		} else if ("jihuo094".equals(channelName)) {channelId = "10350";
		} else if ("jihuo095".equals(channelName)) {channelId = "10351";
		} else if ("jihuo096".equals(channelName)) {channelId = "10352";
		} else if ("jihuo097".equals(channelName)) {channelId = "10353";
		} else if ("jihuo098".equals(channelName)) {channelId = "10354";
		} else if ("jihuo099".equals(channelName)) {channelId = "10355";
		} else if ("jihuo100".equals(channelName)) {channelId = "10356";
		} else if ("jihuo101".equals(channelName)) {channelId = "10357";
		} else if ("jihuo102".equals(channelName)) {channelId = "10358";
		} else if ("jihuo103".equals(channelName)) {channelId = "10359";
		} else if ("jihuo104".equals(channelName)) {channelId = "10360";
		} else if ("jihuo105".equals(channelName)) {channelId = "10361";
		} else if ("jihuo106".equals(channelName)) {channelId = "10362";
		} else if ("jihuo107".equals(channelName)) {channelId = "10363";
		} else if ("jihuo108".equals(channelName)) {channelId = "10364";
		} else if ("jihuo109".equals(channelName)) {channelId = "10365";
		} else if ("jihuo110".equals(channelName)) {channelId = "10366";
		} else if ("jihuo111".equals(channelName)) {channelId = "10367";
		} else if ("jihuo112".equals(channelName)) {channelId = "10368";
		} else if ("jihuo113".equals(channelName)) {channelId = "10369";
		} else if ("jihuo114".equals(channelName)) {channelId = "10370";
		} else if ("jihuo115".equals(channelName)) {channelId = "10371";
		} else if ("jihuo116".equals(channelName)) {channelId = "10372";
		} else if ("jihuo117".equals(channelName)) {channelId = "10373";
		} else if ("jihuo118".equals(channelName)) {channelId = "10374";
		} else if ("jihuo119".equals(channelName)) {channelId = "10375";
		} else if ("jihuo120".equals(channelName)) {channelId = "10376";
		} else if ("jihuo121".equals(channelName)) {channelId = "10377";
		} else if ("jihuo122".equals(channelName)) {channelId = "10378";
		} else if ("jihuo123".equals(channelName)) {channelId = "10379";
		} else if ("jihuo124".equals(channelName)) {channelId = "10380";
		} else if ("jihuo125".equals(channelName)) {channelId = "10381";
		} else if ("jihuo126".equals(channelName)) {channelId = "10382";
		} else if ("jihuo127".equals(channelName)) {channelId = "10383";
		} else if ("jihuo128".equals(channelName)) {channelId = "10384";
		} else if ("jihuo129".equals(channelName)) {channelId = "10385";
		} else if ("ruangao".equals(channelName)) {channelId = "10386";
		} else if ("edm".equals(channelName)) {channelId = "10388";
		} else if ("wandoujiatuiguang".equals(channelName)) {channelId = "10390";
		} else if ("360banner".equals(channelName)) {channelId = "10393";
		} else if ("360integralwall".equals(channelName)) {channelId = "10394";
		} else if ("360freeflow".equals(channelName)) {channelId = "10395";
		} else if ("legao".equals(channelName)) {channelId = "10396";
		} else if ("qianqian-android".equals(channelName)) {channelId = "10397";
		} else if ("intelx86".equals(channelName)) {channelId = "10399";
		} else if ("channel001".equals(channelName)) {channelId = "10401";
		} else if ("channel002".equals(channelName)) {channelId = "10402";
		} else if ("channel003".equals(channelName)) {channelId = "10403";
		} else if ("channel004".equals(channelName)) {channelId = "10404";
		} else if ("channel005".equals(channelName)) {channelId = "10405";
		} else if ("channel006".equals(channelName)) {channelId = "10406";
		} else if ("channel007".equals(channelName)) {channelId = "10407";
		} else if ("channel008".equals(channelName)) {channelId = "10408";
		} else if ("channel009".equals(channelName)) {channelId = "10409";
		} else if ("channel010".equals(channelName)) {channelId = "10410";
		} else if ("channel011".equals(channelName)) {channelId = "10411";
		} else if ("channel012".equals(channelName)) {channelId = "10412";
		} else if ("channel013".equals(channelName)) {channelId = "10413";
		} else if ("channel014".equals(channelName)) {channelId = "10414";
		} else if ("channel015".equals(channelName)) {channelId = "10415";
		} else if ("channel016".equals(channelName)) {channelId = "10416";
		} else if ("channel017".equals(channelName)) {channelId = "10417";
		} else if ("channel018".equals(channelName)) {channelId = "10418";
		} else if ("channel019".equals(channelName)) {channelId = "10419";
		} else if ("channel020".equals(channelName)) {channelId = "10420";
		} else if ("channel021".equals(channelName)) {channelId = "10421";
		} else if ("channel022".equals(channelName)) {channelId = "10422";
		} else if ("channel023".equals(channelName)) {channelId = "10423";
		} else if ("channel024".equals(channelName)) {channelId = "10424";
		} else if ("channel025".equals(channelName)) {channelId = "10425";
		} else if ("channel026".equals(channelName)) {channelId = "10426";
		} else if ("channel027".equals(channelName)) {channelId = "10427";
		} else if ("channel028".equals(channelName)) {channelId = "10428";
		} else if ("channel029".equals(channelName)) {channelId = "10429";
		} else if ("channel030".equals(channelName)) {channelId = "10430";
		} else if ("channel031".equals(channelName)) {channelId = "10431";
		} else if ("channel032".equals(channelName)) {channelId = "10432";
		} else if ("channel033".equals(channelName)) {channelId = "10433";
		} else if ("channel034".equals(channelName)) {channelId = "10434";
		} else if ("channel035".equals(channelName)) {channelId = "10435";
		} else if ("channel036".equals(channelName)) {channelId = "10436";
		} else if ("channel037".equals(channelName)) {channelId = "10437";
		} else if ("channel038".equals(channelName)) {channelId = "10438";
		} else if ("channel039".equals(channelName)) {channelId = "10439";
		} else if ("channel040".equals(channelName)) {channelId = "10440";
		} else if ("channel041".equals(channelName)) {channelId = "10441";
		} else if ("channel042".equals(channelName)) {channelId = "10442";
		} else if ("channel043".equals(channelName)) {channelId = "10443";
		} else if ("channel044".equals(channelName)) {channelId = "10444";
		} else if ("channel045".equals(channelName)) {channelId = "10445";
		} else if ("channel046".equals(channelName)) {channelId = "10446";
		} else if ("channel047".equals(channelName)) {channelId = "10447";
		} else if ("channel048".equals(channelName)) {channelId = "10448";
		} else if ("channel049".equals(channelName)) {channelId = "10449";
		} else if ("channel050".equals(channelName)) {channelId = "10450";
		} else if ("channel051".equals(channelName)) {channelId = "10451";
		} else if ("channel052".equals(channelName)) {channelId = "10452";
		} else if ("channel053".equals(channelName)) {channelId = "10453";
		} else if ("channel054".equals(channelName)) {channelId = "10454";
		} else if ("channel055".equals(channelName)) {channelId = "10455";
		} else if ("channel056".equals(channelName)) {channelId = "10456";
		} else if ("channel057".equals(channelName)) {channelId = "10457";
		} else if ("channel058".equals(channelName)) {channelId = "10458";
		} else if ("channel059".equals(channelName)) {channelId = "10459";
		} else if ("channel060".equals(channelName)) {channelId = "10460";
		} else if ("channel061".equals(channelName)) {channelId = "10461";
		} else if ("channel062".equals(channelName)) {channelId = "10462";
		} else if ("channel063".equals(channelName)) {channelId = "10463";
		} else if ("channel064".equals(channelName)) {channelId = "10464";
		} else if ("channel065".equals(channelName)) {channelId = "10465";
		} else if ("channel066".equals(channelName)) {channelId = "10466";
		} else if ("channel067".equals(channelName)) {channelId = "10467";
		} else if ("channel068".equals(channelName)) {channelId = "10468";
		} else if ("channel069".equals(channelName)) {channelId = "10469";
		} else if ("channel070".equals(channelName)) {channelId = "10470";
		} else if ("channel071".equals(channelName)) {channelId = "10471";
		} else if ("channel072".equals(channelName)) {channelId = "10472";
		} else if ("channel073".equals(channelName)) {channelId = "10473";
		} else if ("channel074".equals(channelName)) {channelId = "10474";
		} else if ("channel075".equals(channelName)) {channelId = "10475";
		} else if ("channel076".equals(channelName)) {channelId = "10476";
		} else if ("channel077".equals(channelName)) {channelId = "10477";
		} else if ("channel078".equals(channelName)) {channelId = "10478";
		} else if ("channel079".equals(channelName)) {channelId = "10479";
		} else if ("channel080".equals(channelName)) {channelId = "10480";
		} else if ("channel081".equals(channelName)) {channelId = "10481";
		} else if ("channel082".equals(channelName)) {channelId = "10482";
		} else if ("channel083".equals(channelName)) {channelId = "10483";
		} else if ("channel084".equals(channelName)) {channelId = "10484";
		} else if ("channel085".equals(channelName)) {channelId = "10485";
		} else if ("channel086".equals(channelName)) {channelId = "10486";
		} else if ("channel087".equals(channelName)) {channelId = "10487";
		} else if ("channel088".equals(channelName)) {channelId = "10488";
		} else if ("channel089".equals(channelName)) {channelId = "10489";
		} else if ("channel090".equals(channelName)) {channelId = "10490";
		} else if ("channel091".equals(channelName)) {channelId = "10491";
		} else if ("channel092".equals(channelName)) {channelId = "10492";
		} else if ("channel093".equals(channelName)) {channelId = "10493";
		} else if ("channel094".equals(channelName)) {channelId = "10494";
		} else if ("channel095".equals(channelName)) {channelId = "10495";
		} else if ("channel096".equals(channelName)) {channelId = "10496";
		} else if ("channel097".equals(channelName)) {channelId = "10497";
		} else if ("channel098".equals(channelName)) {channelId = "10498";
		} else if ("channel099".equals(channelName)) {channelId = "10499";
		} else if ("channel100".equals(channelName)) {channelId = "10500";
		} else if ("channel101".equals(channelName)) {channelId = "10501";
		} else if ("channel102".equals(channelName)) {channelId = "10502";
		} else if ("channel103".equals(channelName)) {channelId = "10503";
		} else if ("channel104".equals(channelName)) {channelId = "10504";
		} else if ("channel105".equals(channelName)) {channelId = "10505";
		} else if ("channel106".equals(channelName)) {channelId = "10506";
		} else if ("channel107".equals(channelName)) {channelId = "10507";
		} else if ("channel108".equals(channelName)) {channelId = "10508";
		} else if ("channel109".equals(channelName)) {channelId = "10509";
		} else if ("channel110".equals(channelName)) {channelId = "10510";
		} else if ("channel111".equals(channelName)) {channelId = "10511";
		} else if ("channel112".equals(channelName)) {channelId = "10512";
		} else if ("channel113".equals(channelName)) {channelId = "10513";
		} else if ("channel114".equals(channelName)) {channelId = "10514";
		} else if ("channel115".equals(channelName)) {channelId = "10515";
		} else if ("channel116".equals(channelName)) {channelId = "10516";
		} else if ("channel117".equals(channelName)) {channelId = "10517";
		} else if ("channel118".equals(channelName)) {channelId = "10518";
		} else if ("channel119".equals(channelName)) {channelId = "10519";
		} else if ("channel120".equals(channelName)) {channelId = "10520";
		} else if ("channel121".equals(channelName)) {channelId = "10521";
		} else if ("channel122".equals(channelName)) {channelId = "10522";
		} else if ("channel123".equals(channelName)) {channelId = "10523";
		} else if ("channel124".equals(channelName)) {channelId = "10524";
		} else if ("channel125".equals(channelName)) {channelId = "10525";
		} else if ("channel126".equals(channelName)) {channelId = "10526";
		} else if ("channel127".equals(channelName)) {channelId = "10527";
		} else if ("channel128".equals(channelName)) {channelId = "10528";
		} else if ("channel129".equals(channelName)) {channelId = "10529";
		} else if ("channel130".equals(channelName)) {channelId = "10530";
		} else if ("channel131".equals(channelName)) {channelId = "10531";
		} else if ("channel132".equals(channelName)) {channelId = "10532";
		} else if ("channel133".equals(channelName)) {channelId = "10533";
		} else if ("channel134".equals(channelName)) {channelId = "10534";
		} else if ("channel135".equals(channelName)) {channelId = "10535";
		} else if ("channel136".equals(channelName)) {channelId = "10536";
		} else if ("channel137".equals(channelName)) {channelId = "10537";
		} else if ("channel138".equals(channelName)) {channelId = "10538";
		} else if ("channel139".equals(channelName)) {channelId = "10539";
		} else if ("channel140".equals(channelName)) {channelId = "10540";
		} else if ("channel141".equals(channelName)) {channelId = "10541";
		} else if ("channel142".equals(channelName)) {channelId = "10542";
		} else if ("channel143".equals(channelName)) {channelId = "10543";
		} else if ("channel144".equals(channelName)) {channelId = "10544";
		} else if ("channel145".equals(channelName)) {channelId = "10545";
		} else if ("channel146".equals(channelName)) {channelId = "10546";
		} else if ("channel147".equals(channelName)) {channelId = "10547";
		} else if ("channel148".equals(channelName)) {channelId = "10548";
		} else if ("channel149".equals(channelName)) {channelId = "10549";
		} else if ("channel150".equals(channelName)) {channelId = "10550";
		} else if ("channel151".equals(channelName)) {channelId = "10551";
		} else if ("channel152".equals(channelName)) {channelId = "10552";
		} else if ("channel153".equals(channelName)) {channelId = "10553";
		} else if ("channel154".equals(channelName)) {channelId = "10554";
		} else if ("channel155".equals(channelName)) {channelId = "10555";
		} else if ("channel156".equals(channelName)) {channelId = "10556";
		} else if ("channel157".equals(channelName)) {channelId = "10557";
		} else if ("channel158".equals(channelName)) {channelId = "10558";
		} else if ("channel159".equals(channelName)) {channelId = "10559";
		} else if ("channel160".equals(channelName)) {channelId = "10560";
		} else if ("channel161".equals(channelName)) {channelId = "10561";
		} else if ("channel162".equals(channelName)) {channelId = "10562";
		} else if ("channel163".equals(channelName)) {channelId = "10563";
		} else if ("channel164".equals(channelName)) {channelId = "10564";
		} else if ("channel165".equals(channelName)) {channelId = "10565";
		} else if ("channel166".equals(channelName)) {channelId = "10566";
		} else if ("channel167".equals(channelName)) {channelId = "10567";
		} else if ("channel168".equals(channelName)) {channelId = "10568";
		} else if ("channel169".equals(channelName)) {channelId = "10569";
		} else if ("channel170".equals(channelName)) {channelId = "10570";
		} else if ("channel171".equals(channelName)) {channelId = "10571";
		} else if ("channel172".equals(channelName)) {channelId = "10572";
		} else if ("channel173".equals(channelName)) {channelId = "10573";
		} else if ("channel174".equals(channelName)) {channelId = "10574";
		} else if ("channel175".equals(channelName)) {channelId = "10575";
		} else if ("channel176".equals(channelName)) {channelId = "10576";
		} else if ("channel177".equals(channelName)) {channelId = "10577";
		} else if ("channel178".equals(channelName)) {channelId = "10578";
		} else if ("channel179".equals(channelName)) {channelId = "10579";
		} else if ("channel180".equals(channelName)) {channelId = "10580";
		} else if ("channel181".equals(channelName)) {channelId = "10581";
		} else if ("channel182".equals(channelName)) {channelId = "10582";
		} else if ("channel183".equals(channelName)) {channelId = "10583";
		} else if ("channel184".equals(channelName)) {channelId = "10584";
		} else if ("channel185".equals(channelName)) {channelId = "10585";
		} else if ("channel186".equals(channelName)) {channelId = "10586";
		} else if ("channel187".equals(channelName)) {channelId = "10587";
		} else if ("channel188".equals(channelName)) {channelId = "10588";
		} else if ("channel189".equals(channelName)) {channelId = "10589";
		} else if ("channel190".equals(channelName)) {channelId = "10590";
		} else if ("channel191".equals(channelName)) {channelId = "10591";
		} else if ("channel192".equals(channelName)) {channelId = "10592";
		} else if ("channel193".equals(channelName)) {channelId = "10593";
		} else if ("channel194".equals(channelName)) {channelId = "10594";
		} else if ("channel195".equals(channelName)) {channelId = "10595";
		} else if ("channel196".equals(channelName)) {channelId = "10596";
		} else if ("channel197".equals(channelName)) {channelId = "10597";
		} else if ("channel198".equals(channelName)) {channelId = "10598";
		} else if ("channel199".equals(channelName)) {channelId = "10599";
		} else if ("channel200".equals(channelName)) {channelId = "10600";
		} else if ("tmall-x86".equals(channelName)) {channelId = "10601";
		} else if ("lequ".equals(channelName)) {channelId = "10602";
		} else if ("meitu".equals(channelName)) {channelId = "10605";
		} else if ("yitiji".equals(channelName)) {channelId = "10606";
		} else if ("tangshanwanda".equals(channelName)) {channelId = "10607";
		} else if ("liantongwomenhu".equals(channelName)) {channelId = "10609";
		} else if ("wap".equals(channelName)) {channelId = "10610";
		} else if ("message".equals(channelName)) {channelId = "10611";
		} else if ("huodongzhuanyong".equals(channelName)) {channelId = "10612";
		} else if ("opera".equals(channelName)) {channelId = "10613";
		} else if ("xiaomipad".equals(channelName)) {channelId = "10614";
		} else if ("ayida".equals(channelName)) {channelId = "10620";
		} else if ("wapscreen".equals(channelName)) {channelId = "10621";
		} else if ("baiduchunhua".equals(channelName)) {channelId = "10622";
		} else if ("baiduqiushi".equals(channelName)) {channelId = "10623";
		} else if ("baiduapptuiguang1".equals(channelName)) {channelId = "10624";
		} else if ("baiduapptuiguang2".equals(channelName)) {channelId = "10625";
		} else if ("baiduapptuiguang3".equals(channelName)) {channelId = "10626";
		} else if ("baiduapptuiguang4".equals(channelName)) {channelId = "10627";
		} else if ("edm2".equals(channelName)) {channelId = "10628";
		} else if ("dgwanda".equals(channelName)) {channelId = "10629";
		} else if ("miniblog".equals(channelName)) {channelId = "10630";
		} else if ("Microletter".equals(channelName)) {channelId = "10631";
		} else if ("inapp".equals(channelName)) {channelId = "10632";
		} else if ("yinchuan".equals(channelName)) {channelId = "10633";
		} else if ("erduosi".equals(channelName)) {channelId = "10634";
		} else if ("nanchang".equals(channelName)) {channelId = "10635";
		} else if ("baoding".equals(channelName)) {channelId = "10636";
		} else if ("changsha".equals(channelName)) {channelId = "10637";
		} else if ("dalian".equals(channelName)) {channelId = "10638";
		} else if ("beijing".equals(channelName)) {channelId = "10639";
		} else if ("guangzhou".equals(channelName)) {channelId = "10640";
		} else if ("chongqing".equals(channelName)) {channelId = "10641";
		} else if ("xiaoshidai".equals(channelName)) {channelId = "10642";
		} else if ("wapscreenbaidu1".equals(channelName)) {channelId = "10643";
		} else if ("wapscreenbaidu2".equals(channelName)) {channelId = "10644";
		} else if ("jihuo130".equals(channelName)) {channelId = "10645";
		} else if ("jihuo131".equals(channelName)) {channelId = "10646";
		} else if ("jihuo132".equals(channelName)) {channelId = "10647";
		} else if ("jihuo133".equals(channelName)) {channelId = "10648";
		} else if ("jihuo134".equals(channelName)) {channelId = "10649";
		} else if ("jihuo135".equals(channelName)) {channelId = "10650";
		} else if ("jihuo136".equals(channelName)) {channelId = "10651";
		} else if ("jihuo137".equals(channelName)) {channelId = "10652";
		} else if ("jihuo138".equals(channelName)) {channelId = "10653";
		} else if ("jihuo139".equals(channelName)) {channelId = "10654";
		} else if ("jihuo140".equals(channelName)) {channelId = "10655";
		} else if ("jihuo141".equals(channelName)) {channelId = "10656";
		} else if ("jihuo142".equals(channelName)) {channelId = "10657";
		} else if ("jihuo143".equals(channelName)) {channelId = "10658";
		} else if ("jihuo144".equals(channelName)) {channelId = "10659";
		} else if ("jihuo145".equals(channelName)) {channelId = "10660";
		} else if ("jihuo146".equals(channelName)) {channelId = "10661";
		} else if ("jihuo147".equals(channelName)) {channelId = "10662";
		} else if ("jihuo148".equals(channelName)) {channelId = "10663";
		} else if ("jihuo149".equals(channelName)) {channelId = "10664";
		} else if ("jihuo150".equals(channelName)) {channelId = "10665";
		} else if ("jihuo151".equals(channelName)) {channelId = "10666";
		} else if ("jihuo152".equals(channelName)) {channelId = "10667";
		} else if ("jihuo153".equals(channelName)) {channelId = "10668";
		} else if ("jihuo154".equals(channelName)) {channelId = "10669";
		} else if ("jihuo155".equals(channelName)) {channelId = "10670";
		} else if ("jihuo156".equals(channelName)) {channelId = "10671";
		} else if ("jihuo157".equals(channelName)) {channelId = "10672";
		} else if ("jihuo158".equals(channelName)) {channelId = "10673";
		} else if ("jihuo159".equals(channelName)) {channelId = "10674";
		} else if ("buffet".equals(channelName)) {channelId = "10675";
		} else if ("Nanchong".equals(channelName)) {channelId = "10676";
		} else if ("QX".equals(channelName)) {channelId = "10677";
		} else if ("wapscreen-nanchong".equals(channelName)) {channelId = "10678";
		} else if ("onego".equals(channelName)) {channelId = "10679";
		} else if ("inapp2".equals(channelName)) {channelId = "10680";
		} else if ("wapfuceng".equals(channelName)) {channelId = "10681";
		} else if ("androidonegoPC".equals(channelName)) {channelId = "10682";
		} else if ("0.99movie".equals(channelName)) {channelId = "10683";
		} else if ("0.99moviePC".equals(channelName)) {channelId = "10684";
		} else if ("duanxin2".equals(channelName)) {channelId = "10685";
		} else if ("duanxin3".equals(channelName)) {channelId = "10686";
		} else if ("duanxin4".equals(channelName)) {channelId = "10687";
		} else if ("duanxin5".equals(channelName)) {channelId = "10688";
		} else if ("5yuanjianshen".equals(channelName)) {channelId = "10689";
		} else if ("5yuanjianshenPC".equals(channelName)) {channelId = "10690";
		} else if ("Dazhong".equals(channelName)) {channelId = "10691";
		} else if ("Dixintong".equals(channelName)) {channelId = "10692";
		} else if ("saoma".equals(channelName)) {channelId = "10693";
		} else if ("WAPschoolyincangdan".equals(channelName)) {channelId = "10694";
		} else if ("gotowave".equals(channelName)) {channelId = "10695";
		} else if ("zhangxingliyi".equals(channelName)) {channelId = "10696";
		} else if ("guangdiantong1".equals(channelName)) {channelId = "10697";
		} else if ("guangdiantong2".equals(channelName)) {channelId = "10698";
		} else if ("womenhu".equals(channelName)) {channelId = "10699";
		} else if ("weather".equals(channelName)) {channelId = "10700";
		} else if ("zhifubaohuodon".equals(channelName)) {channelId = "10753";
		} else if ("PClogin".equals(channelName)) {channelId = "10754";
		} else if ("PCsignup".equals(channelName)) {channelId = "10755";
		} else if ("PCsignuped".equals(channelName)) {channelId = "10756";
		} else if ("PClashou".equals(channelName)) {channelId = "10757";
		} else if ("PCgoodsdetail".equals(channelName)) {channelId = "10758";
		} else if ("PCorderbuy".equals(channelName)) {channelId = "10759";
		} else if ("PCorderpay".equals(channelName)) {channelId = "10760";
		} else if ("PCorderpayfeedback".equals(channelName)) {channelId = "10761";
		} else if ("PCappevent".equals(channelName)) {channelId = "10762";
		} else if ("EDMdiyongquan".equals(channelName)) {channelId = "10763";
		} else {
			channelId = "10062";
		}
		return channelId;
	}

	// public static String getUserId(Context context) {
	// return
	// Preferences.getUserId(PreferenceManager.getDefaultSharedPreferences(context));
	// }
	//
	// public static String getCityId(Context context) {
	// return
	// Preferences.getCityId(PreferenceManager.getDefaultSharedPreferences(context));
	// }

	public static String getSoftVersion(Context context) {
		PackageInfo info;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "4.3";
		}
	}

	public static String getChannelId(Context context) {
		ApplicationInfo ai;
		try {
			ai = context.getPackageManager().getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			return getChannelIdByChannelName(ai.metaData
					.getString("UMENG_CHANNEL"));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "10062";
		}
	}

	public static String getSystemVersion() {
		return replaceBlank(android.os.Build.VERSION.RELEASE);
	}

	/**
	 * 是否需要版本升级
	 * 
	 * @param mVersionInfo
	 * @return
	 */
//	public static boolean needUpdate(Session appSession, UpgradeInfo versionInfo) {
//		if (versionInfo != null) {
//			boolean result = ("1".equals(versionInfo.getIsPromptUpgrade() + "") || "1"
//					.equals(versionInfo.getIsForceUpgrade() + ""));
//			// 如果升级提醒与强制更新有一个打开说明此次需要更新升级
//			if (result) {
//				if (appSession.getVersionName().equals(
//						versionInfo.getNewestVersion())) {
//					result = false;
//				} else {
//					double newversionNum = 0;
//					String newverstr = versionInfo.getNewestVersion();
//					if (newverstr == null) {
//						newverstr = "0";
//					} else {
//						newverstr = newverstr.trim();
//					}
//					try {
//						newversionNum = Double.valueOf(newverstr);
//					} catch (Exception ce) {
//						newversionNum = 0;
//					}
//
//					double currversionNum = 0;
//					String currverstr = appSession.getVersionName();
//					if (currverstr == null) {
//						currverstr = "0";
//					} else {
//						currverstr = currverstr.trim();
//					}
//					try {
//						currversionNum = Double.valueOf(currverstr);
//					} catch (Exception ce) {
//						currversionNum = 0;
//					}
//					if (newversionNum <= currversionNum || newversionNum == 0) {
//						result = false;
//					} else {
//						result = true;
//					}
//				}
//			}
//			return result;
//		} else {
//			return false;
//		}
//	}
}
