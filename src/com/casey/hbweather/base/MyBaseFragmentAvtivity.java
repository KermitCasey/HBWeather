package com.casey.hbweather.base;

import org.kymjs.aframe.widget.TransparentDialog;

import com.casey.hbweather.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TextView;

public class MyBaseFragmentAvtivity extends FragmentActivity {
	public TransparentDialog transparentDialog;

	protected TextView headTitle;
	protected RadioButton headMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 无title
		transparentDialog = new TransparentDialog(this, R.style.TransparentDialog, R.drawable.transparentdialog2);
	}

	public void dialogShow() {
		transparentDialog.show();
	}

	public void dialogDismiss() {
		if (transparentDialog.isShowing()) {
			transparentDialog.dismiss();
		}
	}

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

	public void mOnClickHeadMenu() {
	}

	public void setHeadTitle(String title) {
		headTitle.setText(title);
	}

}
