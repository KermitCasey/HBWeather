package com.casey.hbweather.utils;

import java.util.ArrayList;

import org.kymjs.aframe.database.KJDB;
import org.kymjs.aframe.http.KJHttp;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.casey.hbweather.model.SceneryBean;
import com.casey.hbweather.model.UserBean;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月7日 下午4:04:16    类说明  
 *
 */
public class Constants {

	// DIRS
	public static String APP_DIR = ".HuBeiWeather";
	public static String IMAGE_DIR = "Images";
	public static String FILR_DIR = "Files";
	public static String IMAGE_CACHE = "cache";

	public static String DBName = "HBDataBase.db";
	public static KJDB HUDataBase;

	// USER
	public static UserBean userBean = null;

	// FORMAT TIME
	public static String FORMAT_01 = "yyyy年MM月dd日 HH时mm分";
	public static String FORMAT_02 = "yyyy-MM-dd";
	public static String FORMAT_03 = "yyyy-MM-dd HH:mm";
	public static String FORMAT_04 = "yyyy-MM-dd HH:mm:ss";
	// RESPONSE_CLIENT
	public static KJHttp mHttpClient;

	public static boolean DEBUG = true;
	public static boolean canGoLogin = false;

	// URL
	public static String WEATHER_URL = "http://api.k780.com:88/?app=weather.future";
	public static String BASE_IP = "http://219.140.167.87:8000";
	public static String BASE_URL = BASE_IP + "/hbqx/trip.do";
	public static String BASE_QUERY_URL = BASE_IP + "/hbqx/query.do";
	public static String BASE_USER_URL = BASE_IP + "/hbqx/appuser.do";
	public static String BASE_LIVE_VIEW = BASE_IP + "/hbqx/liveview.do";
	// MAP
	public static LatLng mapCenter = new LatLng(30.567652, 114.31965);
	public static BDLocation mPosition;
	

}
