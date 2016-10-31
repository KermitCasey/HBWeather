package com.casey.hbweather.ui;

import org.json.JSONObject;
import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;
import org.kymjs.aframe.utils.ImageLoadutils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.casey.hbweather.R;
import com.casey.hbweather.base.HeadBaseActivity;
import com.casey.hbweather.model.IndicesBean;
import com.casey.hbweather.model.SceneryBean;
import com.casey.hbweather.model.SceneryBean;
import com.casey.hbweather.utils.AppUtils;
import com.casey.hbweather.utils.Constants;
import com.google.gson.Gson;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月7日 下午10:50:40    类说明  
 */
public class TravelSceneryPointDetail extends HeadBaseActivity {

	private SceneryBean sceneryBean;
	// 天气详情
	ImageView day01Icon, day02Icon, day03Icon, sceneryBgView;
	TextView day01, day01High, day01Low, day01Text, day02, day02High, day02Low, day02Text, day03, day03High, day03Low, day03Text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.travelscenery_pointdetail);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();

		changeMenuBg(false, R.drawable.delete_selector);
		sceneryBgView = (ImageView) findViewById(R.id.sceneryBgView);

		day01Icon = (ImageView) findViewById(R.id.day01_icon);
		day01 = (TextView) findViewById(R.id.day01);
		day01High = (TextView) findViewById(R.id.day01_high);
		day01Low = (TextView) findViewById(R.id.day01_low);
		day01Text = (TextView) findViewById(R.id.day01_text);

		day02Icon = (ImageView) findViewById(R.id.day02_icon);
		day02 = (TextView) findViewById(R.id.day02);
		day02High = (TextView) findViewById(R.id.day02_high);
		day02Low = (TextView) findViewById(R.id.day02_low);
		day02Text = (TextView) findViewById(R.id.day02_text);

		day03Icon = (ImageView) findViewById(R.id.day03_icon);
		day03 = (TextView) findViewById(R.id.day03);
		day03High = (TextView) findViewById(R.id.day03_high);
		day03Low = (TextView) findViewById(R.id.day03_low);
		day03Text = (TextView) findViewById(R.id.day03_text);

		day02.setText(AppUtils.getWeekNum(1));
		day03.setText(AppUtils.getWeekNum(2));
		Bundle bundle = getIntent().getExtras();
		if (null != bundle) {
			Message msg = new Message();
			msg.what = 1;
			if (bundle.getInt("type") == 0) {
				sceneryBean = (SceneryBean) bundle.getSerializable("sceneryBean");
				msg.obj = sceneryBean.getId();
			} else if (bundle.getInt("type") == 1) {
				IndicesBean indicesBean = (IndicesBean) bundle.getSerializable("IndicesBean");
				msg.obj = indicesBean.getId();
			}
			mHandler.sendMessage(msg);
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				setHeadTitle(sceneryBean.getSceneryName());
				refershCurrentView(sceneryBean);
				break;
			case 1:
				getSingleScenery(msg.obj.toString());
				break;
			default:
				break;
			}
		};
	};

	private void getSingleScenery(String id) {
		dialogShow();
		KJStringParams params = new KJStringParams();
		params.put("method", "getLatestScenery");
		params.put("id", id);
		KJLoger.debug_i("getLatestScenery", Constants.BASE_URL + "?" + params.toString());
		Constants.mHttpClient.post(Constants.BASE_URL, params, new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				dialogDismiss();
				try {
					JSONObject json = new JSONObject(content);
					sceneryBean = new Gson().fromJson(json.optString("scenery"), SceneryBean.class);
					mHandler.sendEmptyMessage(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
				super.onFailure(t, errorNo, strMsg);
				dialogDismiss();
				Toast.makeText(TravelSceneryPointDetail.this, "网络错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void refershCurrentView(SceneryBean sceneryBean) {
		if (null == sceneryBean) {
			return;
		}

		ImageLoadutils.imageLoader.displayImage(sceneryBean.getImage(), sceneryBgView);

		((TextView) findViewById(R.id.scenery_temperature)).setText(sceneryBean.getWeather().getLowTemp() + "~" + sceneryBean.getWeather().getHighTemp());
		((RatingBar) findViewById(R.id.scenery_level)).setRating(TextUtils.isEmpty(sceneryBean.getSceneryLevel()) ? 0 : sceneryBean.getSceneryLevel().length());
		((TextView) findViewById(R.id.scenery_name)).setText(TextUtils.isEmpty(sceneryBean.getSceneryName()) ? "暂无" : sceneryBean.getSceneryName());
		((TextView) findViewById(R.id.scenery_ticket)).setText(sceneryBean.getSceneryPrice() + "");
		((TextView) findViewById(R.id.scenery_detail01)).setText(TextUtils.isEmpty(sceneryBean.getAddress()) ? "暂无" : sceneryBean.getAddress());
		((TextView) findViewById(R.id.scenery_detail02)).setText(TextUtils.isEmpty(sceneryBean.getShortIntro()) ? "暂无" : sceneryBean.getShortIntro());
		((TextView) findViewById(R.id.scenery_detail03)).setText(sceneryBean.getSceneryPrice() + "");

		((TextView) findViewById(R.id.scenery_detail04)).setText(TextUtils.isEmpty(sceneryBean.getIndex().getUltravioletIndex()) ? "暂无" : sceneryBean
				.getIndex().getUltravioletIndex());
		((TextView) findViewById(R.id.scenery_detail05)).setText(TextUtils.isEmpty(sceneryBean.getIndex().getClothesIndex()) ? "暂无" : sceneryBean.getIndex()
				.getClothesIndex());
		((TextView) findViewById(R.id.scenery_detail06)).setText(TextUtils.isEmpty(sceneryBean.getIndex().getTripIndex()) ? "暂无" : sceneryBean.getIndex()
				.getTripIndex());
		((TextView) findViewById(R.id.scenery_detail07)).setText(TextUtils.isEmpty(sceneryBean.getIndex().getHumanIndex()) ? "暂无" : sceneryBean.getIndex()
				.getHumanIndex());
		((TextView) findViewById(R.id.scenery_detail08)).setText(TextUtils.isEmpty(sceneryBean.getIndex().getColdIndex()) ? "暂无" : sceneryBean.getIndex()
				.getColdIndex());

		((TextView) findViewById(R.id.scenery_history)).setText(TextUtils.isEmpty(sceneryBean.getSceneryHistory()) ? "暂无" : sceneryBean.getSceneryHistory());

		try {
			day01Text.setText(sceneryBean.getForecastArray().get(0).getWeather().getWeatherName());
			day01High.setText(sceneryBean.getForecastArray().get(0).getWeather().getHighTemp() + "°");
			day01Low.setText(sceneryBean.getForecastArray().get(0).getWeather().getLowTemp() + "°");
			day01Icon.setBackgroundResource(AppUtils.getWeatherIconFromString(sceneryBean.getForecastArray().get(0).getWeather().getWeatherName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			day02Text.setText(sceneryBean.getForecastArray().get(1).getWeather().getWeatherName());
			day02High.setText(sceneryBean.getForecastArray().get(1).getWeather().getHighTemp() + "°");
			day02Low.setText(sceneryBean.getForecastArray().get(1).getWeather().getLowTemp() + "°");
			day02Icon.setBackgroundResource(AppUtils.getWeatherIconFromString(sceneryBean.getForecastArray().get(1).getWeather().getWeatherName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			day03Text.setText(sceneryBean.getForecastArray().get(2).getWeather().getWeatherName());
			day03High.setText(sceneryBean.getForecastArray().get(2).getWeather().getHighTemp() + "°");
			day03Low.setText(sceneryBean.getForecastArray().get(2).getWeather().getLowTemp() + "°");
			day03Icon.setBackgroundResource(AppUtils.getWeatherIconFromString(sceneryBean.getForecastArray().get(2).getWeather().getWeatherName()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void mOnClickHeadMenu() {

	}

}
