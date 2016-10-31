package com.casey.hbweather.ui;

import org.kymjs.aframe.widget.ActionSheet;

import com.casey.hbweather.R;
import com.casey.hbweather.base.HeadBaseActivity;
import com.casey.hbweather.base.MyBaseFragmentAvtivity;
import com.casey.hbweather.controller.UserBeanController;
import com.casey.hbweather.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月19日 下午7:02:46    类说明  
 *
 */
public class UserCenterActivity extends MyBaseFragmentAvtivity implements OnClickListener, org.kymjs.aframe.widget.ActionSheet.ActionSheetListener {

	CheckBox setWeather, setWarn, setPushTime, setUpdate;
	RelativeLayout setCache, setPhone, setFeedBack, setLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_center);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		setHeadTitle(getResources().getString(R.string.usercenter_title));
		changeMenuBg(false, 0);
		// 设置点击事件
		findViewById(R.id.usercenter_weather).setOnClickListener(this);
		findViewById(R.id.usercenter_warn).setOnClickListener(this);
		findViewById(R.id.usercenter_cache).setOnClickListener(this);
		findViewById(R.id.usercenter_time).setOnClickListener(this);
		findViewById(R.id.usercenter_phone).setOnClickListener(this);
		findViewById(R.id.usercenter_update).setOnClickListener(this);
		findViewById(R.id.usercenter_feedback).setOnClickListener(this);
		findViewById(R.id.usercenter_logout).setOnClickListener(this);

	}

	@Override
	public void mOnClickHeadMenu() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.usercenter_weather:

			break;
		case R.id.usercenter_warn:

			break;
		case R.id.usercenter_cache:

			break;
		case R.id.usercenter_time:

			break;
		case R.id.usercenter_phone:
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:12121"));
			startActivity(intent);
			break;
		case R.id.usercenter_update:

			break;
		case R.id.usercenter_feedback:

			break;
		case R.id.usercenter_logout:
			setTheme(R.style.ActionSheetStyleIOS7);
			ActionSheet.createBuilder(this, getSupportFragmentManager()).setCancelButtonTitle("取消").setOtherButtonTitles("退出账号？")
					.setCancelableOnTouchOutside(true).setListener(this).show();
			break;

		default:
			break;
		}

	}

	@Override
	public void onDismiss(ActionSheet arg0, boolean arg1) {
		

	}

	@Override
	public void onOtherButtonClick(ActionSheet arg0, int index) {
		Constants.userBean = null;
		new UserBeanController().cleatSaveUserBean(this);
		onBackPressed();
	}

	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
		finish();
	}

}
