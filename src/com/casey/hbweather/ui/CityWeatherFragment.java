package com.casey.hbweather.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;
import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.http.StringCallBack;
import org.kymjs.aframe.utils.ImageLoadutils;
import org.kymjs.aframe.widget.sliding.AbOnChangeListener;
import org.kymjs.aframe.widget.sliding.AbOnItemClickListener;
import org.kymjs.aframe.widget.sliding.AbSlidingPlayView;

import com.baidu.platform.comapi.map.t;
import com.casey.hbweather.R;
import com.casey.hbweather.base.MyBaseFragment;
import com.casey.hbweather.controller.UserBeanController;
import com.casey.hbweather.model.CityBean;
import com.casey.hbweather.model.CityForecasts;
import com.casey.hbweather.model.CollectionCity;
import com.casey.hbweather.model.UserBean;
import com.casey.hbweather.utils.AppUtils;
import com.casey.hbweather.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.R.integer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月4日 下午9:03:40    类说明  
 *
 */
public class CityWeatherFragment extends MyBaseFragment {

	View rootView;
	Context context;
	AbSlidingPlayView abSlidingPlayView;
	ImageButton addCity, userCenter;
	ArrayList<CollectionCity> collectionCitys = new ArrayList<CollectionCity>();
	ArrayList<CityForecasts> mCityForecasts = new ArrayList<CityForecasts>();
	// 天气详情
	ImageView day01Icon, day02Icon, day03Icon;
	TextView day01, day01High, day01Low, day01Text, day02, day02High, day02Low, day02Text, day03, day03High, day03Low, day03Text;

	String TemperatureSymbol;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.frag_cityweather, null);
			initView(rootView);
		}

		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		initData();

		return rootView;
	}

	private void initView(View view) {
		context = getActivity();

		day01Icon = (ImageView) view.findViewById(R.id.day01_icon);
		day01 = (TextView) view.findViewById(R.id.day01);
		day01High = (TextView) view.findViewById(R.id.day01_high);
		day01Low = (TextView) view.findViewById(R.id.day01_low);
		day01Text = (TextView) view.findViewById(R.id.day01_text);

		day02Icon = (ImageView) view.findViewById(R.id.day02_icon);
		day02 = (TextView) view.findViewById(R.id.day02);
		day02High = (TextView) view.findViewById(R.id.day02_high);
		day02Low = (TextView) view.findViewById(R.id.day02_low);
		day02Text = (TextView) view.findViewById(R.id.day02_text);

		day03Icon = (ImageView) view.findViewById(R.id.day03_icon);
		day03 = (TextView) view.findViewById(R.id.day03);
		day03High = (TextView) view.findViewById(R.id.day03_high);
		day03Low = (TextView) view.findViewById(R.id.day03_low);
		day03Text = (TextView) view.findViewById(R.id.day03_text);

		day02.setText(AppUtils.getWeekNum(1));
		day03.setText(AppUtils.getWeekNum(2));

		abSlidingPlayView = (AbSlidingPlayView) view.findViewById(R.id.mAbSlidingPlayView);
		abSlidingPlayView.setBackgroundColor(getResources().getColor(R.color.transparent));
		view.findViewById(R.id.add_city).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CityWeather_AddCity.class);
				startActivity(intent);
			}
		});
		view.findViewById(R.id.user_center).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UserBean userBean;
				try {
					userBean = new UserBeanController().getSavedUserBean(context);
				} catch (Exception e) {
					userBean = null;
				}
				Intent intent = new Intent();
				if (null == userBean) {
					intent.setClass(context, UserLoginActivity.class);
				} else {
					intent.setClass(context, UserCenterActivity.class);
				}
				startActivity(intent);
			}
		});
		abSlidingPlayView.setPageLineHorizontalVisibility(View.GONE);

		abSlidingPlayView.setOnPageChangeListener(new AbOnChangeListener() {

			@Override
			public void onChange(int position) {
				try {
					refershBelowWeather(position);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private void initData() {

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.weathercity.refresh");
		getActivity().registerReceiver(mRefreshCitys, intentFilter);
		TemperatureSymbol = context.getResources().getString(R.string.temperature_left);
		onRefresh();
	}

	private void getWebCityForecast() {
		if (collectionCitys.size() <= 0) {
			return;
		}
		dialogShow();
		transparentDialog.setCancelable(false);
		for (int i = 0; i < collectionCitys.size(); i++) {
			final CollectionCity collectionCity = collectionCitys.get(i);
		}

		for (CollectionCity collectionCity : collectionCitys) {
			getSingleCityForecast(false, collectionCity);
		}

	}

	private void getSingleCityForecast(final boolean show, final CollectionCity collectionCity) {
		if (show) {
			dialogShow();
		}

		KJLoger.debug_i("Forecast URL", Constants.WEATHER_URL + "&weaid=" + collectionCity.getWeaid()
				+ "&appkey=11589&sign=cbef83074bd7e67d79590c2f36c3161d&format=json");
		Constants.mHttpClient.get(Constants.WEATHER_URL + "&weaid=" + collectionCity.getWeaid()
				+ "&appkey=11589&sign=cbef83074bd7e67d79590c2f36c3161d&format=json", new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				KJLoger.debug_i("getWebCityForecast", content);
				try {
					JSONObject json = new JSONObject(content);
					if (json.optInt("success") == 1) {
						CityForecasts item = new CityForecasts(collectionCity.getWeaid());
						item.setCityForeCast((ArrayList<CollectionCity>) new Gson().fromJson(json.optString("result"),
								new TypeToken<ArrayList<CollectionCity>>() {
								}.getType()));

						int position = hasWeaidForecasts(collectionCity.getWeaid());
						if (position != -1) {
							mCityForecasts.set(position, item);
						} else {
							mCityForecasts.add(item);
						}
						try {
							CollectionCity cityItem = item.getCityForeCast().get(0);
							cityItem.setId(item.getWeaid());
							Constants.HUDataBase.update(cityItem);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (show && transparentDialog.isShowing()) {
						transparentDialog.dismiss();
						refreshCurrentView();
					} else if (mCityForecasts.size() == collectionCitys.size()) {
						dialogDismiss();
						refreshCurrentView();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				if (transparentDialog.isShowing() && show) {
					transparentDialog.dismiss();
				}
			}
		});

	}

	private void refreshCurrentView() {
		abSlidingPlayView.removeAllViews();
		for (int i = 0; i < mCityForecasts.size(); i++) {
			final CityForecasts cityForecast = mCityForecasts.get(i);
			final CollectionCity collectionCity = cityForecast.getCityForeCast().get(0);
			View mPlayView = LayoutInflater.from(context).inflate(R.layout.fragcity_viewitem, null);
			TextView cityName = (TextView) mPlayView.findViewById(R.id.weather_cityname);
			TextView cityWeather = (TextView) mPlayView.findViewById(R.id.weather_city);
			TextView cityTemperature = (TextView) mPlayView.findViewById(R.id.weather_temperature);
			TextView cityCurrentTemperature = (TextView) mPlayView.findViewById(R.id.weather_currenttemperature);
			ImageView refersh = (ImageView) mPlayView.findViewById(R.id.weather_refersh);
			ImageView detail = (ImageView) mPlayView.findViewById(R.id.weather_detail);

			cityName.setText(collectionCity.getCitynm() + "市");
			cityWeather.setText(AppUtils.getWeatherStringFromLevelAll(collectionCity.getWeatid()));
			cityTemperature.setText(collectionCity.getTemp_low() + "~" + collectionCity.getTemp_high());
			cityCurrentTemperature.setText(String.valueOf(collectionCity.getTemp_high() - 3));

			refersh.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					getSingleCityForecast(true, collectionCity);
				}
			});
			detail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					Intent i = new Intent(context, CityWeatherDetail.class);
					bundle.putSerializable("cityForecast", cityForecast);
					i.putExtras(bundle);
					startActivity(i);
				}
			});
			abSlidingPlayView.addView(mPlayView);
		}
		refershBelowWeather(0);
	}

	/**
	 * 刷新三天的天气
	 */
	private void refershBelowWeather(int posiotion) {
		try {
			CityForecasts itemCityForecasts = mCityForecasts.get(posiotion);

			CollectionCity dayOne = itemCityForecasts.getCityForeCast().get(0);
			day01High.setText(dayOne.getTemp_high() + TemperatureSymbol);
			day01Low.setText(dayOne.getTemp_low() + TemperatureSymbol);
			day01Text.setText(dayOne.getWeather());
			day01Icon.setBackgroundResource(AppUtils.getWeatherIcon(dayOne.getWeatid()));

			CollectionCity dayTwo = itemCityForecasts.getCityForeCast().get(1);
			day02High.setText(dayTwo.getTemp_high() + TemperatureSymbol);
			day02Low.setText(dayTwo.getTemp_low() + TemperatureSymbol);
			day02Text.setText(dayTwo.getWeather());
			day02Icon.setBackgroundResource(AppUtils.getWeatherIcon(dayTwo.getWeatid()));

			CollectionCity dayThree = itemCityForecasts.getCityForeCast().get(2);
			day03High.setText(dayThree.getTemp_high() + TemperatureSymbol);
			day03Low.setText(dayThree.getTemp_low() + TemperatureSymbol);
			day03Text.setText(dayThree.getWeather());
			day03Icon.setBackgroundResource(AppUtils.getWeatherIcon(dayThree.getWeatid()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否已存在该城市的天气预报
	 */
	private int hasWeaidForecasts(String weaid) {
		for (int i = 0; i < mCityForecasts.size(); i++) {
			if (weaid.equals(mCityForecasts.get(i).getWeaid())) {
				return i;
			} else {
				continue;
			}
		}
		return -1;
	}

	private BroadcastReceiver mRefreshCitys = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("action.weathercity.refresh")) {
				onRefresh();
			}
		}
	};

	private void onRefresh() {
		collectionCitys.clear();
		collectionCitys = (ArrayList<CollectionCity>) Constants.HUDataBase.findAll(CollectionCity.class);
		if (collectionCitys.size() <= 0) {
			initMyCity();
			collectionCitys = (ArrayList<CollectionCity>) Constants.HUDataBase.findAll(CollectionCity.class);
		}
		mCityForecasts.clear();
		for (CollectionCity collectionCity : collectionCitys) {
			CityForecasts item = new CityForecasts(collectionCity.getWeaid());
			mCityForecasts.add(item);
		}
		getWebCityForecast();
	}

	/**
	 * 初始化城市
	 */
	private void initMyCity() {
		if (Constants.mPosition == null) {
			CollectionCity collectionCity = new CollectionCity();
			collectionCity.setWeaid("248");
			collectionCity.setId("248");
			collectionCity.setCitynm("武汉市");
			collectionCity.setCityno("wuhan");
			collectionCity.setCityid("101200101");
			Constants.HUDataBase.save(collectionCity);
		} else {
			try {
				String cityname = Constants.mPosition.getCity();
				List<CityBean> citys = Constants.HUDataBase.findAllByWhere(CityBean.class, "citynm ='" + cityname + "'");
				if (citys.size() > 0) {
					CityBean item = citys.get(0);
					CollectionCity collectionCity = new CollectionCity();
					collectionCity.setWeaid(item.getWeaid());
					collectionCity.setId(item.getId());
					collectionCity.setCitynm(item.getCitynm());
					collectionCity.setCityno(item.getCityno());
					collectionCity.setCityid(item.getCityid());
					Constants.HUDataBase.save(collectionCity);
				}
			} catch (Exception e) {
				e.printStackTrace();
				CollectionCity collectionCity = new CollectionCity();
				collectionCity.setWeaid("248");
				collectionCity.setId("248");
				collectionCity.setCitynm("武汉市");
				collectionCity.setCityno("wuhan");
				collectionCity.setCityid("101200101");
				Constants.HUDataBase.save(collectionCity);
			}

		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		context.unregisterReceiver(mRefreshCitys);
	}

}
