package com.casey.hbweather.ui;

import java.util.ArrayList;

import org.kymjs.aframe.utils.ImageLoadutils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.casey.hbweather.R;
import com.casey.hbweather.base.HeadBaseActivity;
import com.casey.hbweather.model.CollectionCity;
import com.casey.hbweather.utils.AppUtils;
import com.casey.hbweather.utils.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月9日 上午11:22:26    类说明  
 *
 */
public class CityWeather_CityManage extends HeadBaseActivity {

	GridView cityGridView;
	ArrayList<CollectionCity> cityLists = new ArrayList<CollectionCity>();
	CityManageAdapter cityManageAdapter = new CityManageAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_citymanage);
		initView();
	}

	@Override
	protected void initView() {
		
		super.initView();
		setHeadTitle("城市管理");
		headMenu.setText("删除");
		cityLists = (ArrayList<CollectionCity>) Constants.HUDataBase.findAll(CollectionCity.class);

		cityGridView = (GridView) findViewById(R.id.cityGrid);
		cityGridView.setAdapter(cityManageAdapter);
	}

	@Override
	public void mOnClickHeadMenu() {
		cityManageAdapter.setDeleteCity(cityManageAdapter.getDeleteCity() ? false : true);
	}

	class CityManageAdapter extends BaseAdapter {

		VHolder vHolder;
		boolean isDelete;

		public CityManageAdapter() {
			isDelete = false;
		}

		public boolean getDeleteCity() {
			return this.isDelete;
		}

		public void setDeleteCity(boolean isDelete) {
			this.isDelete = isDelete;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return cityLists.size();
		}

		@Override
		public Object getItem(int position) {
			return cityLists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final CollectionCity item = cityLists.get(position);
			if (convertView == null) {
				vHolder = new VHolder();
				convertView = getLayoutInflater().inflate(R.layout.citymanage_viewitem, null);
				vHolder.citynm = (TextView) convertView.findViewById(R.id.citynm);
				vHolder.weather = (TextView) convertView.findViewById(R.id.weather);
				vHolder.cityDelete = (ImageView) convertView.findViewById(R.id.city_delete);
				vHolder.highTemperature = (TextView) convertView.findViewById(R.id.highTemperature);
				vHolder.lowTemperature = (TextView) convertView.findViewById(R.id.lowTemperature);
				vHolder.weathericon = (ImageView) convertView.findViewById(R.id.weathericon);
				convertView.setTag(vHolder);
			} else {
				vHolder = (VHolder) convertView.getTag();
			}

			if (isDelete) {
				vHolder.cityDelete.setVisibility(View.VISIBLE);
			} else {
				vHolder.cityDelete.setVisibility(View.GONE);
			}

			vHolder.citynm.setText(item.getCitynm().contains("市") ? item.getCitynm() : item.getCitynm() + "市");
			vHolder.highTemperature.setText(item.getTemp_high() + "°");
			vHolder.lowTemperature.setText(item.getTemp_low() + "°");

			vHolder.weather.setText(AppUtils.getWeatherStringFromLevel(item.getWeatid()));
			vHolder.weathericon.setBackgroundResource(AppUtils.getWeatherIcon(item.getWeatid()));
			vHolder.cityDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					deleteCollectionCity(item);
				}
			});

			return convertView;
		}

		class VHolder {
			TextView citynm, weather, highTemperature, lowTemperature;
			ImageView weathericon, cityDelete;
		}
	}

	private void deleteCollectionCity(CollectionCity city) {
		cityLists = (ArrayList<CollectionCity>) Constants.HUDataBase.findAll(CollectionCity.class);
		if (cityLists.size() <= 1) {
			Toast.makeText(CityWeather_CityManage.this, "请至少保留一个城市", Toast.LENGTH_SHORT).show();
			return;
		}
		Constants.HUDataBase.delete(city);
		cityLists.clear();
		cityLists = (ArrayList<CollectionCity>) Constants.HUDataBase.findAll(CollectionCity.class);
		cityManageAdapter.notifyDataSetChanged();
		Intent intent = new Intent();
		intent.setAction("action.weathercity.refresh");
		sendBroadcast(intent);
	}
}
