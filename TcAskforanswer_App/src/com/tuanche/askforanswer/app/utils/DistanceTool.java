package com.tuanche.askforanswer.app.utils;

public class DistanceTool {

	public static String getDistanceStr(float distance) {
		if (distance < 1000) {
			return String.valueOf(distance) + "米";
		} else {
			float km = distance / 1000;

			return String.valueOf(Math.round(km * 10) / 10.0) + "公里";
		}
	}

	public static String getDurationStr(long duration) {

		if (duration <= 60)

			return "约1分钟";

		int mintues = (int) (duration / 60);
		if (mintues < 60) {
			return "约" + mintues + "分钟";
		}

		int hours = mintues / 60;
		int modMinutes = mintues % 60;

		if (hours < 24) {
			return "约" + hours + "小时" + modMinutes + "分钟";
		}

		int days = hours / 24;
		int modHours = hours % 24;

		return "约" + days + "天" + modHours + "小时" + modMinutes + "分钟";

	}

}
