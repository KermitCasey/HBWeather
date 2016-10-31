package com.casey.hbweather.base;

import org.json.JSONObject;
import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import com.casey.hbweather.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public abstract class HeadBaseActivity extends MyBaseActivity {

	protected TextView headTitle;
	protected RadioButton headMenu;

	protected void initView() {
		headTitle = (TextView) findViewById(R.id.whole_headtitle);
		headMenu = (RadioButton) findViewById(R.id.whole_headmenu);
		findViewById(R.id.whole_headback).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
				finish();
			}
		});
		headMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mOnClickHeadMenu();
			}
		});
	}

	public void changeMenuBg(boolean isShow, int resId) {
		if (isShow) {
			headMenu.setText("");
			headMenu.setBackgroundResource(resId);
		} else {
			headMenu.setVisibility(View.GONE);
		}

	}

	public abstract void mOnClickHeadMenu();

	public void setHeadTitle(String title) {
		headTitle.setText(title);
	}
}
