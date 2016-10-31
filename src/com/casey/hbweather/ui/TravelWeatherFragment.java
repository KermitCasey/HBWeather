package com.casey.hbweather.ui;

import com.casey.hbweather.R;
import com.casey.hbweather.model.UserBean.permission;
import com.casey.hbweather.utils.AppUtils;
import com.casey.hbweather.utils.Constants;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月4日 下午9:01:44    类说明  
 *
 */
public class TravelWeatherFragment extends Fragment {
	View rootView;
	RadioButton title00, title01, title02, title03;
	private Fragment[] mFragments;
	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.frag_travelweather, null);
			initView(rootView);
		}
		mFragments = new Fragment[4];
		fragmentManager = getFragmentManager();

		mFragments[0] = fragmentManager.findFragmentById(R.id.bodyZero);
		mFragments[1] = fragmentManager.findFragmentById(R.id.bodyOne);
		mFragments[2] = fragmentManager.findFragmentById(R.id.bodyTwo);
		mFragments[3] = fragmentManager.findFragmentById(R.id.bodyThree);

		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}

		fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]);
		fragmentTransaction.show(mFragments[0]).commit();
		return rootView;
	}

	void initView(View view) {
		title00 = (RadioButton) view.findViewById(R.id.travel_title00);
		title01 = (RadioButton) view.findViewById(R.id.travel_title01);
		title02 = (RadioButton) view.findViewById(R.id.travel_title02);
		title03 = (RadioButton) view.findViewById(R.id.travel_title03);

		title00.setOnClickListener(cityTitleOnClickListener);
		title01.setOnClickListener(cityTitleOnClickListener);
		title02.setOnClickListener(cityTitleOnClickListener);
		title03.setOnClickListener(cityTitleOnClickListener);
	}

	OnClickListener cityTitleOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]);
			switch (v.getId()) {
			case R.id.travel_title00:
				fragmentTransaction.show(mFragments[0]).commit();
				break;
			case R.id.travel_title01:
				fragmentTransaction.show(mFragments[1]).commit();
				break;
			case R.id.travel_title02:
				fragmentTransaction.show(mFragments[2]).commit();
				break;
			case R.id.travel_title03:
				if (Constants.userBean == null) {
					AppUtils.showUserCenterDialog(getActivity(), "  查看卫星云图");
					fragmentTransaction.show(mFragments[0]).commit();
					title00.setChecked(true);
				} else {
					for (permission permiss : Constants.userBean.getPermissions()) {
						if (permiss.getResource() == 4) {
							if (permiss.getAllow()) {
								fragmentTransaction.show(mFragments[3]).commit();
							} else {
								Toast.makeText(getActivity(), "没有权限", Toast.LENGTH_SHORT).show();
							}
							break;
						}
					}

				}
				break;
			default:
				break;
			}

		}
	};
}
