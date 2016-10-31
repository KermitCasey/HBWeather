package com.casey.hbweather.ui;

import java.util.ArrayList;

import org.json.JSONObject;
import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;
import org.kymjs.aframe.ui.widget.KJListView;
import org.kymjs.aframe.ui.widget.KJRefreshListener;
import org.kymjs.aframe.utils.HandleDate;

import com.casey.hbweather.R;
import com.casey.hbweather.base.MyBaseFragment;
import com.casey.hbweather.model.CityWarnBean;
import com.casey.hbweather.model.CollectionCity;
import com.casey.hbweather.model.SceneryBean;
import com.casey.hbweather.utils.AppUtils;
import com.casey.hbweather.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月4日 下午10:16:27    类说明  
 *
 */
public class TravelFrag_Warn extends MyBaseFragment implements KJRefreshListener {

	View rootView;
	Context context;
	KJListView mWarnListView;
	ArrayList<CityWarnBean> CityWarnBeanList = new ArrayList<CityWarnBean>();
	WarnListAdapter mWarnListAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragtravel_warn, null);
			initView(rootView);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		getWarnListData();
		return rootView;
	}

	/**
	 * 初始化view
	 */
	private void initView(View view) {
		context = getActivity();

		mWarnListView = (KJListView) view.findViewById(R.id.warnListView);
		mWarnListView.setFooterDividersEnabled(false);
		mWarnListView.setPullLoadEnable(false);
		mWarnListView.setOnRefreshListener(this);
		mWarnListAdapter = new WarnListAdapter();
		mWarnListView.setAdapter(mWarnListAdapter);

		mWarnListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				CityWarnBean item = (CityWarnBean) adapterView.getItemAtPosition(position);
				View dialogView = LayoutInflater.from(context).inflate(R.layout.travelcity_warndialog, null);
				TextView title = (TextView) dialogView.findViewById(R.id.warn_title);
				TextView content = (TextView) dialogView.findViewById(R.id.warn_content);
				content.setMovementMethod(ScrollingMovementMethod.getInstance());
				
				title.setText(item.getDisaster() + item.getLevel() + "预警");
				content.setText(item.getContent());
				final Dialog dialog = new AlertDialog.Builder(context).create();
				dialog.show();
				dialog.setContentView(dialogView);

				dialogView.findViewById(R.id.dismissDialog).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
			}
		});

	}

	/**
	 * 获取网络数据
	 */
	private void getWarnListData() {
		dialogShow();
		KJStringParams params = new KJStringParams();

		params.put("method", "getDisasterList");
		Constants.mHttpClient.post(Constants.BASE_URL, params, new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				dialogDismiss();
				KJLoger.debug_i("getWarnListData", content);
				CityWarnBeanList.clear();
				try {
					JSONObject json = new JSONObject(content);
					ArrayList<CityWarnBean> CityWarnList = new Gson().fromJson(json.optString("list"), new TypeToken<ArrayList<CityWarnBean>>() {
					}.getType());
					for (CityWarnBean cityWarnBean : CityWarnList) {
						// if (AppUtils.isValidWarn(cityWarnBean.getTime())) {
						CityWarnBeanList.add(cityWarnBean);
						// } else {
						// continue;
						// }
					}
					mWarnListAdapter.notifyDataSetChanged();
					mWarnListView.setRefreshTime(HandleDate.ConverNowText(Constants.FORMAT_01));
					mWarnListView.stopPullRefresh();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				dialogDismiss();
				super.onFailure(t, errorNo, strMsg);
			}
		});

	}

	public class WarnListAdapter extends BaseAdapter {

		VHolder vHolder;

		@Override
		public int getCount() {
			return CityWarnBeanList.size();
		}

		@Override
		public Object getItem(int position) {
			return CityWarnBeanList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CityWarnBean item = CityWarnBeanList.get(position);
			if (convertView == null) {
				vHolder = new VHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.fragtravel_warnlistitem, null);
				vHolder.name = (TextView) convertView.findViewById(R.id.warnCity_name);
				vHolder.weather = (TextView) convertView.findViewById(R.id.warnCity_weather);
				vHolder.warn = (TextView) convertView.findViewById(R.id.warnCity_color);
				vHolder.time = (TextView) convertView.findViewById(R.id.warnCity_time);
				convertView.setTag(vHolder);
			} else {
				vHolder = (VHolder) convertView.getTag();
			}
			vHolder.name.setText(item.getName());
			vHolder.weather.setText(item.getDisaster());
			vHolder.warn.setText(item.getLevel() + "预警");
			vHolder.warn.setTextColor(AppUtils.getWarnTextColor(item.getLevel()));
			vHolder.time.setText(item.getTime());
			return convertView;
		}

		class VHolder {
			TextView name, weather, warn, time;
		}
	}

	@Override
	public void onLoadMore() {

	}

	@Override
	public void onRefresh() {
		getWarnListData();
	}
}
