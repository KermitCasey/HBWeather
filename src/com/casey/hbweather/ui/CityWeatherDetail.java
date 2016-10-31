package com.casey.hbweather.ui;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.casey.hbweather.R;
import com.casey.hbweather.base.HeadBaseActivity;
import com.casey.hbweather.model.CityForecasts;
import com.casey.hbweather.utils.AppUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月9日 下午3:11:35    类说明  
 *
 */
public class CityWeatherDetail extends HeadBaseActivity {

	LineChart mLineChart;
	int[] mColors = ColorTemplate.VORDIPLOM_COLORS;
	CityForecasts cityForecasts;
	int daynum = 0;

	LinearLayout sevenDayLayout;
	View sevenDayLine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cityweather_detail);
		initView();
		Bundle bundle = getIntent().getExtras();
		if (null != bundle) {
			cityForecasts = (CityForecasts) bundle.getSerializable("cityForecast");
			daynum = cityForecasts.getCityForeCast().size();
			setHeadTitle(cityForecasts.getCityForeCast().get(0).getCitynm() + "市");
			refershView();
		}
	}

	/**
	 * 
	 */
	private void refershView() {
		try {

			addEmptyData();
			mLineChart.invalidate();

			((TextView) findViewById(R.id.day01_text)).setText(AppUtils.getWeatherStringFromLevel(cityForecasts.getCityForeCast().get(0).getWeatid()));
			((TextView) findViewById(R.id.day02_text)).setText(AppUtils.getWeatherStringFromLevel(cityForecasts.getCityForeCast().get(1).getWeatid()));
			((TextView) findViewById(R.id.day03_text)).setText(AppUtils.getWeatherStringFromLevel(cityForecasts.getCityForeCast().get(2).getWeatid()));
			((TextView) findViewById(R.id.day04_text)).setText(AppUtils.getWeatherStringFromLevel(cityForecasts.getCityForeCast().get(3).getWeatid()));
			((TextView) findViewById(R.id.day05_text)).setText(AppUtils.getWeatherStringFromLevel(cityForecasts.getCityForeCast().get(4).getWeatid()));
			((TextView) findViewById(R.id.day06_text)).setText(AppUtils.getWeatherStringFromLevel(cityForecasts.getCityForeCast().get(5).getWeatid()));

			((ImageView) findViewById(R.id.day01_icon)).setBackgroundResource(AppUtils.getWeatherIcon(cityForecasts.getCityForeCast().get(0).getWeatid()));
			((ImageView) findViewById(R.id.day02_icon)).setBackgroundResource(AppUtils.getWeatherIcon(cityForecasts.getCityForeCast().get(1).getWeatid()));
			((ImageView) findViewById(R.id.day03_icon)).setBackgroundResource(AppUtils.getWeatherIcon(cityForecasts.getCityForeCast().get(2).getWeatid()));
			((ImageView) findViewById(R.id.day04_icon)).setBackgroundResource(AppUtils.getWeatherIcon(cityForecasts.getCityForeCast().get(3).getWeatid()));
			((ImageView) findViewById(R.id.day05_icon)).setBackgroundResource(AppUtils.getWeatherIcon(cityForecasts.getCityForeCast().get(4).getWeatid()));
			((ImageView) findViewById(R.id.day06_icon)).setBackgroundResource(AppUtils.getWeatherIcon(cityForecasts.getCityForeCast().get(5).getWeatid()));

			if (daynum < 7) {
				sevenDayLayout.setVisibility(View.GONE);
				sevenDayLine.setVisibility(View.GONE);
			} else {
				((TextView) findViewById(R.id.day07_text)).setText(cityForecasts.getCityForeCast().get(6).getWeather());
				((ImageView) findViewById(R.id.day07_icon)).setBackgroundResource(AppUtils.getWeatherIcon(cityForecasts.getCityForeCast().get(6).getWeatid()));
			}
			addDataSet(true);
			addDataSet(false);
			mLineChart.setPadding(10, 0, 10, 0);
			mLineChart.setYRange(mLineChart.getYMin() - 2, mLineChart.getYMax() + 2, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void initView() {
		super.initView();
		setHeadTitle("武汉市");
		changeMenuBg(false, 0);
		sevenDayLine = findViewById(R.id.sevenDayLine);
		sevenDayLayout = (LinearLayout) findViewById(R.id.sevenDayLayout);

		((TextView) findViewById(R.id.day02_week)).setText(AppUtils.getWeekNum(1));
		((TextView) findViewById(R.id.day03_week)).setText(AppUtils.getWeekNum(2));
		((TextView) findViewById(R.id.day04_week)).setText(AppUtils.getWeekNum(3));
		((TextView) findViewById(R.id.day05_week)).setText(AppUtils.getWeekNum(4));
		((TextView) findViewById(R.id.day06_week)).setText(AppUtils.getWeekNum(5));
		((TextView) findViewById(R.id.day07_week)).setText(AppUtils.getWeekNum(6));

		mLineChart = (LineChart) findViewById(R.id.chart1);
		mLineChart.setDrawYValues(true);// 显示Y轴数据、
		mLineChart.setDrawGridBackground(false);// 表格背景
		mLineChart.setTouchEnabled(false);// 是否可点击
		mLineChart.setDragEnabled(false);//
		mLineChart.setDrawHorizontalGrid(false);// 水平网格线
		mLineChart.setDrawVerticalGrid(false);// 垂直网格线
		mLineChart.setValueTextColor(Color.WHITE);// Y轴数据字体颜色
		mLineChart.setValueFormatter(new ValueFormatter() {

			@Override
			public String getFormattedValue(float value) {
				
				return (int) value + "";
			}
		});//取整
		mLineChart.setValueTextSize(15f);// Y轴字体大小
		mLineChart.setDescription("");// 表格描述
		mLineChart.setDrawXLabels(false);// X轴标志位
		mLineChart.setDrawYLabels(false);// Y轴标志位
		mLineChart.setDrawBorder(false);// 底部横线
		mLineChart.setDrawLegend(false);// 底部表示块
	}

	private void addEmptyData() {

		String[] xVals = new String[daynum];

		for (int i = 0; i < daynum; i++)
			xVals[i] = "";

		LineData data = new LineData(xVals);

		mLineChart.setData(data);
		mLineChart.invalidate();
	}

	private void addDataSet(boolean hl) {

		LineData data = mLineChart.getData();

		if (data != null) {

			ArrayList<Entry> yVals = new ArrayList<Entry>();
			for (int i = 0; i < data.getXValCount(); i++) {
				// Entry entry = new Entry(val, 0)
				if (hl) {
					yVals.add(new Entry(cityForecasts.getCityForeCast().get(i).getTemp_high(), i));
				} else {
					yVals.add(new Entry(cityForecasts.getCityForeCast().get(i).getTemp_low(), i));
				}
			}
			LineDataSet set = new LineDataSet(yVals, null);
			set.setLineWidth(3.5f);
			set.setCircleSize(5.5f);

			int color;
			if (hl) {
				color = Color.BLUE;
			} else {
				color = Color.WHITE;
			}
			set.setColor(color);
			set.setCircleColor(color);
			data.addDataSet(set);
			mLineChart.notifyDataSetChanged();
			mLineChart.invalidate();
		}
	}

	@Override
	public void mOnClickHeadMenu() {
		

	}

}
