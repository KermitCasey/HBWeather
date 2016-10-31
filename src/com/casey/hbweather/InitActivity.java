package com.casey.hbweather;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.aframe.widget.ExitDialog;
import org.kymjs.aframe.widget.ViewPageScroll;

import com.casey.hbweather.adapter.SectionsPagerAdapter;
import com.casey.hbweather.base.MyBaseFragmentAvtivity;
import com.casey.hbweather.model.UserBean.permission;
import com.casey.hbweather.ui.CityWeatherFragment;
import com.casey.hbweather.ui.SceneryForumFragment;
import com.casey.hbweather.ui.SpecialTravelFragment;
import com.casey.hbweather.ui.TravelWeatherFragment;
import com.casey.hbweather.utils.AppUtils;
import com.casey.hbweather.utils.Constants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Toast;

public class InitActivity extends MyBaseFragmentAvtivity {

	Context mContext;
	RadioButton menu01, menu02, menu03, menu04;
//	ViewPageScroll homeViewPage;
	private Fragment[] mFragments;
//	private List<Fragment> homeFragments = new ArrayList<Fragment>();
//	SectionsPagerAdapter homePageAdapter;
	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.init);
		mContext = this;
		init();
	}

	private void init() {
		menu01 = (RadioButton) findViewById(R.id.home_bottom01);
		menu02 = (RadioButton) findViewById(R.id.home_bottom02);
		menu03 = (RadioButton) findViewById(R.id.home_bottom03);
		menu04 = (RadioButton) findViewById(R.id.home_bottom04);
//		homeViewPage = (ViewPageScroll) findViewById(R.id.homeViewPage);

		menu01.setOnClickListener(mOnClickListener);
		menu02.setOnClickListener(mOnClickListener);
		menu03.setOnClickListener(mOnClickListener);
		menu04.setOnClickListener(mOnClickListener);
		
		mFragments = new Fragment[4];
		fragmentManager = this.getSupportFragmentManager();

		mFragments[0] = fragmentManager.findFragmentById(R.id.home_bodyOne);
		mFragments[1] = fragmentManager.findFragmentById(R.id.home_bodytwo);
		mFragments[2] = fragmentManager.findFragmentById(R.id.home_bodythree);
		mFragments[3] = fragmentManager.findFragmentById(R.id.home_bodyfour);
		
		fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]);
		fragmentTransaction.show(mFragments[0]).commit();

//		CityWeatherFragment cityWeatherFragment = new CityWeatherFragment();
//		SceneryForumFragment sceneryForumFragment = new SceneryForumFragment();
//		SpecialTravelFragment specialTravelFragment = new SpecialTravelFragment();
//		TravelWeatherFragment travelWeatherFragment = new TravelWeatherFragment();
//
//		homeFragments.add(travelWeatherFragment);
//		homeFragments.add(specialTravelFragment);
//		homeFragments.add(cityWeatherFragment);
//		homeFragments.add(sceneryForumFragment);
//
//		homePageAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), homeFragments);
//		homeViewPage.setOffscreenPageLimit(homeFragments.size());
//		homeViewPage.setAdapter(homePageAdapter);
//		homeViewPage.setOnPageChangeListener(new OnPageChangeListener() {
//
//			@Override
//			public void onPageSelected(int arg0) {
//				changeBottomBackground(arg0);
//			}
//
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//			}
//		});
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]);

			switch (v.getId()) {
			case R.id.home_bottom01:
//				homeViewPage.setCurrentItem(0);
				fragmentTransaction.show(mFragments[0]).commit();
				changeBottomBackground(0);
				break;
			case R.id.home_bottom02:
//				homeViewPage.setCurrentItem(1);
				fragmentTransaction.show(mFragments[1]).commit();
				changeBottomBackground(1);
				break;
			case R.id.home_bottom03:
//				homeViewPage.setCurrentItem(2);
				fragmentTransaction.show(mFragments[2]).commit();
				changeBottomBackground(2);
				break;
			case R.id.home_bottom04:
//				homeViewPage.setCurrentItem(3);
				fragmentTransaction.show(mFragments[3]).commit();
				changeBottomBackground(3);
				break;
			}
		}
	};

	public void changeBottomBackground(int selected) {
		switch (selected) {
		case 0:
			changeRadioButtonBg(menu01, true);
			changeRadioButtonBg(menu02, false);
			changeRadioButtonBg(menu03, false);
			changeRadioButtonBg(menu04, false);
			break;
		case 1:
			changeRadioButtonBg(menu01, false);
			changeRadioButtonBg(menu02, true);
			changeRadioButtonBg(menu03, false);
			changeRadioButtonBg(menu04, false);
			break;
		case 2:
			changeRadioButtonBg(menu01, false);
			changeRadioButtonBg(menu02, false);
			changeRadioButtonBg(menu03, true);
			changeRadioButtonBg(menu04, false);
			break;
		case 3:
			changeRadioButtonBg(menu01, false);
			changeRadioButtonBg(menu02, false);
			changeRadioButtonBg(menu03, false);
			changeRadioButtonBg(menu04, true);
			break;
		default:
			break;
		}
	}

	@SuppressLint("ResourceAsColor")
	public void changeRadioButtonBg(RadioButton radio, boolean temp) {
		if (temp) {
			// radio.setTextColor(getResources().getColor(R.color.white));
			radio.setBackgroundResource(R.drawable.bottom_radio_p);
			radio.setChecked(true);
		} else {
			// radio.setTextColor(getResources().getColor(R.color.background_holo_dark));
			radio.setBackgroundColor(R.color.transparent);
			radio.setChecked(false);
		}
	}

	@Override
	public void onBackPressed() {
		ExitDialog.show(this, "确定要退出程序吗？");
	}

}
