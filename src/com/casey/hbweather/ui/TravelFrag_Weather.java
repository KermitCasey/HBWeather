package com.casey.hbweather.ui;

import java.util.ArrayList;

import org.json.JSONObject;
import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;
import org.kymjs.aframe.utils.HandleDate;
import org.kymjs.aframe.widget.ClearEditText;
import org.kymjs.aframe.widget.ClearEditText.OnClearEditTextChangedListener;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.casey.hbweather.R;
import com.casey.hbweather.base.HeadBaseActivity;
import com.casey.hbweather.base.MyBaseFragment;
import com.casey.hbweather.model.CityBean;
import com.casey.hbweather.model.SceneryBean;
import com.casey.hbweather.utils.AppUtils;
import com.casey.hbweather.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月7日 下午5:22:19    类说明  
 *
 */
public class TravelFrag_Weather extends MyBaseFragment implements OnClickListener, BaiduMap.OnMapLoadedCallback, BaiduMap.OnMarkerClickListener,
		OnMapClickListener, OnClearEditTextChangedListener {

	String temperature;
	View rootView;
	Context context;
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;

	MapView mMapView;
	BaiduMap mBaiduMap;
	ArrayList<SceneryBean> SearchSceneryLists = new ArrayList<SceneryBean>();
	ArrayList<SceneryBean> SceneryLists = new ArrayList<SceneryBean>();

	BitmapDescriptor marketicon = BitmapDescriptorFactory.fromResource(R.drawable.scenery_mapmarket);

	LinearLayout SearchLayout;
	ClearEditText searchCity;
	Button mPosition, mapStyle;

	ListView mSceneryListView;
	CityListAdapter cityListAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.travelmap_activity, null);
			// 地图初始化
			mMapView = (MapView) rootView.findViewById(R.id.bmapView);
			mBaiduMap = mMapView.getMap();
			initView(rootView);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		initMap();
		return rootView;
	}

	protected void initView(View view) {
		context = getActivity();
		temperature = getResources().getString(R.string.temperature_left);
		SearchLayout = (LinearLayout) view.findViewById(R.id.search_layout);
		searchCity = (ClearEditText) view.findViewById(R.id.seachCity);
		mSceneryListView = (ListView) view.findViewById(R.id.mSceneryListView);

		view.findViewById(R.id.mLocation_btn).setOnClickListener(this);
		view.findViewById(R.id.mMapStyle).setOnClickListener(this);

		// searchCity.addTextChangedListener(new EditChangedListener());
		searchCity.setClearTextChangedListenr(this);

		cityListAdapter = new CityListAdapter();
		mSceneryListView.setAdapter(cityListAdapter);

		mSceneryListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				mSceneryListView.setVisibility(View.GONE);

				addSingleMarket((SceneryBean) adapterView.getItemAtPosition(position));
			}
		});
	}

	private void getAllSceneryList() {
		dialogShow();
		KJStringParams params = new KJStringParams();
		params.put("method", "getAllScenery");
		Constants.mHttpClient.post(Constants.BASE_URL, params, new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				KJLoger.debug_i("景点列表", content);
				dialogDismiss();
				try {
					JSONObject json = new JSONObject(content);
					SceneryLists = new Gson().fromJson(json.optString("list"), new TypeToken<ArrayList<SceneryBean>>() {
					}.getType());
					refershMapView(SceneryLists);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {

				super.onFailure(t, errorNo, strMsg);
				dialogDismiss();
				KJLoger.debug_i("景点列表", "onFailure");
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mLocation_btn:
			gotoMyCurrentPosition();
			break;
		case R.id.mMapStyle:
			mBaiduMap.setMapType(mBaiduMap.getMapType() == mBaiduMap.MAP_TYPE_NORMAL ? mBaiduMap.MAP_TYPE_SATELLITE : mBaiduMap.MAP_TYPE_NORMAL);
			break;
		}

	}

	private void gotoMyCurrentPosition() {
		BDLocation location;
		if (Constants.mPosition == null) {
			location = new BDLocation(Constants.mapCenter.latitude, Constants.mapCenter.longitude, 0);
		} else {
			location = Constants.mPosition;
		}
		MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius()).direction(100).latitude(location.getLatitude())
				.longitude(location.getLongitude()).build();
		mBaiduMap.setMyLocationData(locData);
		LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
		mBaiduMap.animateMapStatus(u);
	}

	protected void addSingleMarket(SceneryBean sceneryBean) {
		mBaiduMap.clear();
		Bundle bundle = new Bundle();
		bundle.putSerializable("item", sceneryBean);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(sceneryBean.getScenery_latitude(), sceneryBean.getScenery_longitude())));
		OverlayOptions overlayOptions = new MarkerOptions().position(new LatLng(sceneryBean.getScenery_latitude(), sceneryBean.getScenery_longitude()))
				.icon(marketicon).zIndex(5).extraInfo(bundle);
		mBaiduMap.addOverlay(overlayOptions);
	}

	protected void refershMapView(ArrayList<SceneryBean> sceneryList) {
		mBaiduMap.clear();
		for (SceneryBean sceneryBean : sceneryList) {
			Bundle bundle = new Bundle();
			bundle.putSerializable("item", sceneryBean);
			OverlayOptions overlayOptions = new MarkerOptions().position(new LatLng(sceneryBean.getScenery_latitude(), sceneryBean.getScenery_longitude()))
					.icon(marketicon).zIndex(5).extraInfo(bundle);
			mBaiduMap.addOverlay(overlayOptions);
		}

	}

	/**
	 * 初始化地图
	 */
	private void initMap() {

		mCurrentMode = LocationMode.NORMAL;
		// 地图初始化

		mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.location_marker);
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
		mBaiduMap.setOnMapLoadedCallback(this);
		mBaiduMap.setOnMarkerClickListener(this);
		mBaiduMap.setOnMapClickListener(this);

		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mMapView.showZoomControls(false);
		mMapView.showScaleControl(false);
		mMapView.removeViewAt(1);

	}

	@Override
	public void onDestroy() {
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		marketicon.recycle();
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}

	// @Override
	// public void mOnClickHeadMenu() {
	// SearchLayout.setVisibility(SearchLayout.getVisibility() == View.GONE ?
	// View.VISIBLE : View.GONE);
	// mSceneryListView.setVisibility(mSceneryListView.getVisibility() ==
	// View.GONE ? View.VISIBLE : View.GONE);
	// if (SearchLayout.getVisibility() == View.GONE) {
	// refershMapView(SearchSceneryLists);
	// }
	// }

	@Override
	public void onMapLoaded() {
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(Constants.mapCenter));
		getAllSceneryList();
		gotoMyCurrentPosition();
	}

	private void searchSceneryList(CharSequence s) {
		SearchSceneryLists.clear();
		for (SceneryBean sceneryBean : SceneryLists) {
			if (sceneryBean.getSceneryName().contains(s)) {
				SearchSceneryLists.add(sceneryBean);
			} else {
				continue;
			}
		}
		cityListAdapter.notifyDataSetChanged();
	}

	class CityListAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return SearchSceneryLists.size();
		}

		@Override
		public Object getItem(int position) {

			return SearchSceneryLists.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView cityItem;
			SceneryBean item = SearchSceneryLists.get(position);
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.city_addcityitem, null);
				cityItem = (TextView) convertView.findViewById(R.id.item);
				convertView.setTag(cityItem);
			} else {
				cityItem = (TextView) convertView.getTag();
			}
			cityItem.setText(item.getSceneryName());
			return convertView;
		}

	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		Bundle bundle = marker.getExtraInfo();
		final SceneryBean sceneryBean;
		if (null != bundle) {
			View view = LayoutInflater.from(context).inflate(R.layout.map_popview, null);
			sceneryBean = (SceneryBean) bundle.getSerializable("item");
			view.findViewById(R.id.scenery_weathericon).setBackgroundResource(AppUtils.getWeatherIconFromString(sceneryBean.getWeather().getWeatherName()));
			((TextView) view.findViewById(R.id.scenery_weather)).setText(sceneryBean.getWeather().getWeatherName());
			((TextView) view.findViewById(R.id.scenery_temp)).setText(sceneryBean.getWeather().getLowTemp() + "~" + sceneryBean.getWeather().getHighTemp()
					+ temperature);
			((TextView) view.findViewById(R.id.scenery_name)).setText(sceneryBean.getSceneryName());
			((TextView) view.findViewById(R.id.scenery_time)).setText(HandleDate.ConverToDate(
					TextUtils.isEmpty(sceneryBean.getWeather().getCuurTime()) ? System.currentTimeMillis() + "" : sceneryBean.getWeather().getCuurTime(),
					Constants.FORMAT_02)
					+ "发布");

			mBaiduMap.showInfoWindow(new InfoWindow(BitmapDescriptorFactory.fromView(view), new LatLng(sceneryBean.getScenery_latitude(), sceneryBean
					.getScenery_longitude()), -75, new OnInfoWindowClickListener() {

				@Override
				public void onInfoWindowClick() {
					Intent intent = new Intent(context, TravelSceneryPointDetail.class);
					Bundle bundle = new Bundle();
					bundle.putInt("type", 0);
					bundle.putSerializable("sceneryBean", sceneryBean);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}));

		}

		return true;
	}

	@Override
	public void onMapClick(LatLng arg0) {
		mBaiduMap.hideInfoWindow();
	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {

		return false;
	}

	@Override
	public void OnTextChangedListener(CharSequence s, int start, int before, int count) {
		try {
			mSceneryListView.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
			searchSceneryList(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
