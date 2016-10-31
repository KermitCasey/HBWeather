package com.casey.hbweather.controller;

import android.widget.ImageView;
import android.widget.TextView;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月13日 下午5:43:24    类说明  
 *
 */
public class CityWeatherController {

	interface CurrentResersh {
		void onRefersh();
	}

	class CurrentView {
		String cityName, cityWeather, cityTemperature, cityCurrentWeather;
	}
}
