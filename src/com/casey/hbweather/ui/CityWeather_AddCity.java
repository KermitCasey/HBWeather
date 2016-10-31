package com.casey.hbweather.ui;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.aframe.widget.ClearEditText;
import org.kymjs.aframe.widget.ClearEditText.OnClearEditTextChangedListener;

import android.content.Intent;
import android.content.ClipData.Item;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.casey.hbweather.R;
import com.casey.hbweather.base.HeadBaseActivity;
import com.casey.hbweather.model.CityBean;
import com.casey.hbweather.model.CollectionCity;
import com.casey.hbweather.utils.Constants;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月9日 上午9:51:03    类说明  
 *
 */
public class CityWeather_AddCity extends HeadBaseActivity implements OnClearEditTextChangedListener {

	ListView cityListView;
	List<CityBean> citys = new ArrayList<CityBean>();
	CityListAdapter cityListAdapter;
	ClearEditText seachCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_addcity);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		changeMenuBg(true, R.drawable.cityweather_citymanage);
		headMenu.setText("");
		setHeadTitle("添加城市");
		seachCity = (ClearEditText) findViewById(R.id.seachCity);
		seachCity.setClearTextChangedListenr(this);

		cityListView = (ListView) findViewById(R.id.mCityListView);
		cityListAdapter = new CityListAdapter();
		cityListView.setAdapter(cityListAdapter);

		cityListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				CityBean cityBean = (CityBean) adapterView.getItemAtPosition(position);
				CollectionCity collectionCity = new CollectionCity();
				collectionCity.setId(cityBean.getId());
				collectionCity.setWeaid(cityBean.getWeaid());
				collectionCity.setCitynm(cityBean.getCitynm());
				collectionCity.setCityno(cityBean.getCityno());
				collectionCity.setCityid(cityBean.getCityid());
				collectionCity.setRefershTime(String.valueOf(System.currentTimeMillis()));
				if (Constants.HUDataBase.findById(cityBean.getId(), CollectionCity.class) != null) {
					Toast.makeText(CityWeather_AddCity.this, "已添加该城市", Toast.LENGTH_LONG).show();
				} else {
					Constants.HUDataBase.save(collectionCity);
				}
				Toast.makeText(CityWeather_AddCity.this, "添加成功", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.setAction("action.weathercity.refresh");
				sendBroadcast(intent);
				onBackPressed();
			}
		});

		new GetCityList().execute();
	}

	@Override
	public void mOnClickHeadMenu() {
		Intent intent = new Intent(this, CityWeather_CityManage.class);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	class GetCityList extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			citys = Constants.HUDataBase.findAll(CityBean.class);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			cityListAdapter.notifyDataSetChanged();
		}

	}

	class CityListAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return citys.size();
		}

		@Override
		public Object getItem(int position) {

			return citys.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView cityItem;
			CityBean item = citys.get(position);
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.city_addcityitem, null);
				cityItem = (TextView) convertView.findViewById(R.id.item);
				convertView.setTag(cityItem);
			} else {
				cityItem = (TextView) convertView.getTag();
			}
			cityItem.setText(item.toString());
			return convertView;
		}

	}

	@Override
	public void OnTextChangedListener(CharSequence s, int start, int before, int count) {
		try {
			citys = Constants.HUDataBase.findAllByWhere(CityBean.class, "citynm like '%" + s + "%'");
			cityListAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

}
