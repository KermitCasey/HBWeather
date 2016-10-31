package com.casey.hbweather.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.kymjs.aframe.KJLoger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.casey.hbweather.R;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月16日 下午9:28:02    类说明  
 *
 */

// 1,0,晴
// 2,1,多云
// 3,2,阴
// 4,3,阵雨
// 5,4,雷阵雨
// 6,5,雷阵雨伴有冰雹
// 7,6,雨夹雪
// 8,7,小雨
// 9,8,中雨
// 10,9,大雨
// 11,10,暴雨
// 12,11,大暴雨
// 13,12,特大暴雨
// 14,13,阵雪
// 15,14,小雪
// 16,15,中雪
// 17,16,大雪
// 18,17,暴雪
// 19,18,雾
// 20,19,冻雨
// 21,20,沙尘暴
// 22,21,小雨到中雨
// 23,22,中雨到大雨
// 24,23,大雨到暴雨
// 25,24,暴雨到大暴雨
// 26,25,大暴雨到特大暴雨
// 27,26,小雪到中雪
// 28,27,中雪到大雪
// 29,28,大雪到暴雪
// 30,29,浮沉
// 31,30,扬沙
// 32,31,强沙尘暴
// 33,32,霾

public class AppUtils {

	public static final String WEEK_STRING = "周";
	public static final Calendar calendar = Calendar.getInstance();

	/**
	 * 获取周几
	 */
	public static String getWeekNum(int day) {

		int d = calendar.get(Calendar.DAY_OF_WEEK);
		d += day;
		d = d % 7;
		String str = "";
		switch (d) {
		case 0:
			str = "六";
			break;
		case 1:
			str = "日";
			break;
		case 2:
			str = "一";
			break;
		case 3:
			str = "二";
			break;
		case 4:
			str = "三";
			break;
		case 5:
			str = "四";
			break;
		case 6:
			str = "五";
			break;

		}
		return WEEK_STRING + str;
	}

	/**
	 * 获取天气图标
	 */
	public static int getWeatherIcon(int weaid) {
		int weather = 0;
		switch (weaid) {
		case 0:
			weather = R.drawable.weathericon_qing;
			break;
		case 1:
			weather = R.drawable.weathericon_qing;
			break;
		case 3:
			weather = R.drawable.weathericon_yintian;
			break;
		case 2:
			weather = R.drawable.weathericon_duoyun;
			break;
		case 8:
			weather = R.drawable.weathericon_xiaoyu;
			break;
		case 9:
			weather = R.drawable.weathericon_dayu;
			break;
		case 10:
			weather = R.drawable.weathericon_dayu;
			break;
		case 4:
			weather = R.drawable.weathericon_leiyu;
			break;
		case 5:
			weather = R.drawable.weathericon_leiyu;
			break;
		case 11:
			weather = R.drawable.weathericon_dayu;
			break;
		case 7:
			weather = R.drawable.weathericon_dayu;
			break;
		case 15:
			weather = R.drawable.weathericon_dayu;
			break;
		case 16:
			weather = R.drawable.weathericon_dayu;
			break;
		case 17:
			weather = R.drawable.weathericon_dayu;
			break;
		default:
			weather = R.drawable.weathericon_duoyun;
			break;
		}
		return weather;
	}

	/**
	 * 获取标准的天气文字
	 */
	public static String getWeatherStringFromLevelAll(int level) {

		String weatherString = "-";
		switch (level) {
		case 0:
			weatherString = "晴天";
			break;
		case 1:
			weatherString = "晴天";
			break;
		case 2:
			weatherString = "多云";
			break;
		case 3:
			weatherString = "阴天";
			break;
		case 4:
			weatherString = "阵雨";
			break;
		case 5:
			weatherString = "雷阵雨";
			break;
		case 6:
			weatherString = "雷阵雨伴有冰雹";
			break;
		case 7:
			weatherString = "雨夹雪";
			break;
		case 8:
			weatherString = "小雨";
			break;
		case 9:
			weatherString = "中雨";
			break;
		case 10:
			weatherString = "大雨";
			break;
		case 11:
			weatherString = "暴雨";
			break;
		case 12:
			weatherString = "大暴雨";
			break;
		case 13:
			weatherString = "特大暴雨";
			break;
		case 14:
			weatherString = "阵雪";
			break;
		case 15:
			weatherString = "小雪";
			break;
		case 16:
			weatherString = "中雪";
			break;
		case 17:
			weatherString = "大雪";
			break;
		case 18:
			weatherString = "暴雪";
			break;
		case 19:
			weatherString = "雾";
			break;
		case 20:
			weatherString = "冻雨";
			break;
		case 21:
			weatherString = "沙尘暴";
			break;
		case 22:
			weatherString = "小雨到中雨";
			break;
		case 23:
			weatherString = "中雨到大雨";
			break;
		case 24:
			weatherString = "大雨到暴雨";
			break;
		case 25:
			weatherString = "暴雨到大暴雨";
			break;
		case 26:
			weatherString = "大暴雨到特大暴雨";
			break;
		case 27:
			weatherString = "小雪到中雪";
			break;
		case 28:
			weatherString = "中雪到大雪";
			break;
		case 29:
			weatherString = "大雪到暴雪";
			break;
		case 30:
			weatherString = "浮沉";
			break;
		case 31:
			weatherString = "扬沙";
			break;
		case 32:
			weatherString = "强沙尘暴";
			break;
		case 33:
			weatherString = "霾";
			break;
		default:
			weatherString = "-";
			break;
		}
		return weatherString;
	}

	/**
	 * 获取处理的天气文字
	 */
	public static String getWeatherStringFromLevel(int level) {
		String weatherString = "-";

		switch (level) {
		case 0:
			weatherString = "晴天";
			break;
		case 1:
			weatherString = "晴天";
			break;
		case 3:
			weatherString = "阴天";
			break;
		case 2:
			weatherString = "多云";
			break;
		case 8:
			weatherString = "小雨";
			break;
		case 9:
			weatherString = "中雨";
			break;
		case 10:
			weatherString = "大雨";
			break;
		case 4:
			weatherString = "阵雨";
			break;
		case 5:
			weatherString = "雷阵雨";
			break;
		case 11:
			weatherString = "暴雨";
			break;
		case 7:
			weatherString = "雨夹雪";
			break;
		case 15:
			weatherString = "小雪";
			break;
		case 16:
			weatherString = "中雪";
			break;
		case 17:
			weatherString = "大雪";
			break;
		default:
			weatherString = "-";
			break;
		}
		return weatherString;
	}

	/**
	 * 获取天气图标
	 */
	public static int getWeatherIconFromString(String weatherString) {
		int weather = 0;
		if (weatherString.contains("晴")) {
			weather = R.drawable.weathericon_qing;
		} else if (weatherString.contains("阴")) {
			weather = R.drawable.weathericon_yintian;
		} else if (weatherString.contains("雷")) {
			weather = R.drawable.weathericon_leiyu;
		} else if (weatherString.contains("多云")) {
			weather = R.drawable.weathericon_duoyun;
		} else if (weatherString.contains("大") || weatherString.contains("暴")) {
			weather = R.drawable.weathericon_dayu;
		} else if (weatherString.contains("雨") || weatherString.contains("雪")) {
			weather = R.drawable.weathericon_xiaoyu;
		} else {
			weather = R.drawable.weathericon_yintian;
		}
		return weather;
	}

	/**
	 * 获取警告颜色
	 */
	public static int getWarnTextColor(String colorText) {
		if (colorText.contains("红色")) {
			return Color.RED;
		} else if (colorText.contains("橙色")) {
			return Color.rgb(255, 97, 0);
		} else if (colorText.contains("黄色")) {
			return Color.YELLOW;
		} else if (colorText.contains("蓝色")) {
			return Color.BLUE;
		} else {
			return Color.GRAY;
		}
	}

	/**
	 * 是否为12小时内的警告
	 * 
	 * @throws ParseException
	 */
	public static boolean isValidWarn(String timeString) {
		try {
			timeString = timeString.substring(0, 19);
			// KJLoger.debug_e("时间", timeString);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeString));
			long KKTime = calendar.getTimeInMillis();
			long currentTime = System.currentTimeMillis();
			if (currentTime - KKTime <= 12 * 60 * 60 * 1000) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void showUserCenterDialog(Context context, String promissString) {

		View view = LayoutInflater.from(context).inflate(R.layout.showuserinfo_dialog, null);
		TextView ShowInfo = (TextView) view.findViewById(R.id.dialog_content);
		ShowInfo.setText(promissString + ShowInfo.getText());
		Dialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.setContentView(view);
	}

}
