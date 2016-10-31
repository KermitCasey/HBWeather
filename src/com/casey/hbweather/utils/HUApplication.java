package com.casey.hbweather.utils;

import org.kymjs.aframe.utils.ImageLoadutils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月7日 下午3:40:14    类说明  
 *
 */
public class HUApplication extends Application {

	public MyLocationListenner myListener = new MyLocationListenner();
	// 定位相关
	LocationClient mLocClient;
	public boolean isFirst = true;

	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
		initLocationClient();
		ImageLoadutils.initImageLoader(getApplicationContext());
	}

	/**
	 * 初始化定位
	 */
	private void initLocationClient() {
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		// uiSettings.set
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			} else {
				Constants.mPosition = location;
				if (isFirst) {
					isFirst = false;
					Intent intent = new Intent();
					intent.setAction("action.mapview.location.refersh");
					sendBroadcast(intent);
				}
				
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

}
