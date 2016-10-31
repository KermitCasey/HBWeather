package com.casey.hbweather.ui;

import java.util.ArrayList;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;
import org.kymjs.aframe.utils.ImageLoadutils;
import org.kymjs.aframe.widget.sliding.AbOnItemClickListener;
import org.kymjs.aframe.widget.sliding.AbSlidingPlayView;

import com.casey.hbweather.R;
import com.casey.hbweather.base.MyBaseFragment;
import com.casey.hbweather.model.SceneryBean;
import com.casey.hbweather.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月4日 下午10:15:08    类说明  
 *
 */
public class TravelFrag_Recommend extends MyBaseFragment {

	View rootView;
	Context context;
	AbSlidingPlayView mSlidingPlayView = null;
	ArrayList<SceneryBean> RecommendSceneryList = new ArrayList<SceneryBean>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragtravel_weather, null);
			initView(rootView);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}

		return rootView;
	}

	void initView(View view) {
		context = getActivity();

		mSlidingPlayView = (AbSlidingPlayView) view.findViewById(R.id.mAbSlidingPlayView);

		// mSlidingPlayView.setPageLineLayoutBackground(R.drawable.bottom_menu_bg);
		Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.play_display);
		Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.play_hide);
		mSlidingPlayView.setPageLineImage(bm1, bm2);
		mSlidingPlayView.setPageLineHorizontalGravity(Gravity.CENTER_HORIZONTAL);

		getRecommendScenery();
		mSlidingPlayView.setOnItemClickListener(new AbOnItemClickListener() {

			@Override
			public void onClick(int position) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("sceneryBean", RecommendSceneryList.get(position));
				Intent intent = new Intent(context, TravelSceneryPointDetail.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

	}

	private void refreshCurrentView() {
		mSlidingPlayView.removeAllViews();
		for (SceneryBean sceneryBean : RecommendSceneryList) {
			View mPlayView = LayoutInflater.from(context).inflate(R.layout.fragtravel_weatherviewitem, null);
			LinearLayout layoutBg = (LinearLayout) mPlayView.findViewById(R.id.scenery_layout);
			ImageView sceneryBg = (ImageView) mPlayView.findViewById(R.id.scenery_bg);
			TextView sceneryName = (TextView) mPlayView.findViewById(R.id.scenery_name);
			TextView sceneryTemp = (TextView) mPlayView.findViewById(R.id.scenery_temperature);
			TextView sceneryTicket = (TextView) mPlayView.findViewById(R.id.scenery_ticket);
			RatingBar sceneryLevel = (RatingBar) mPlayView.findViewById(R.id.scenery_level);
			TextView sceneryShortInfo = (TextView) mPlayView.findViewById(R.id.scenery_shortinfo);

			sceneryName.setText(sceneryBean.getSceneryName());
			sceneryTicket.setText(sceneryBean.getSceneryPrice()+"");
			sceneryTemp.setText(sceneryBean.getWeather().getLowTemp() + "~" + sceneryBean.getWeather().getHighTemp());
			sceneryLevel.setRating(sceneryBean.getSceneryLevel().length());
			sceneryShortInfo.setText(sceneryBean.getShortIntro());

			ImageLoadutils.imageLoader.displayImage(sceneryBean.getImage(), sceneryBg, ImageLoadutils.options2);
			layoutBg.setBackgroundResource(R.drawable.home_travelbg_1);
			mSlidingPlayView.addView(mPlayView);
		}
		mSlidingPlayView.startPlay();
	}

	void getRecommendScenery() {
		dialogShow();
		KJStringParams params = new KJStringParams();
		params.put("method", "getRecommendScenery");
		Constants.mHttpClient.post(Constants.BASE_URL, params, new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				dialogDismiss();
				try {
					JSONObject json = new JSONObject(content);
					RecommendSceneryList = new Gson().fromJson(json.optString("recommend"), new TypeToken<ArrayList<SceneryBean>>() {
					}.getType());
					refreshCurrentView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
