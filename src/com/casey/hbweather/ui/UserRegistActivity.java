package com.casey.hbweather.ui;

import org.json.JSONObject;
import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;
import org.kymjs.aframe.utils.MD5;
import org.kymjs.aframe.widget.ClearEditText;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.casey.hbweather.R;
import com.casey.hbweather.base.HeadBaseActivity;
import com.casey.hbweather.utils.Constants;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月19日 下午6:50:44    类说明  
 *
 */
public class UserRegistActivity extends HeadBaseActivity {

	ClearEditText name, newPassword, confirePassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_regist);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		setHeadTitle(getResources().getString(R.string.user_registtitle));
		// headMenu.setText(getResources().getString(R.string.user_regist));
		changeMenuBg(true, R.drawable.user_regintbg);
		name = (ClearEditText) findViewById(R.id.username);
		newPassword = (ClearEditText) findViewById(R.id.user_newpassword);
		confirePassword = (ClearEditText) findViewById(R.id.user_confirepassword);
	}

	@Override
	public void mOnClickHeadMenu() {
		isAllowRegist();
	}

	/**
	 * 判断是否符合输入条件
	 */
	private void isAllowRegist() {
		if (TextUtils.isEmpty(name.getText())) {
			Toast.makeText(this, getResources().getString(R.string.userphone_nonull), Toast.LENGTH_SHORT).show();
		} else if (TextUtils.isEmpty(newPassword.getText())) {
			Toast.makeText(this, getResources().getString(R.string.userpassword_nonull), Toast.LENGTH_SHORT).show();
		} else if (TextUtils.isEmpty(confirePassword.getText())) {
			Toast.makeText(this, getResources().getString(R.string.userpassword_confirenonull), Toast.LENGTH_SHORT).show();
		} else if (!newPassword.getText().toString().equals(confirePassword.getText().toString())) {
			Toast.makeText(this, getResources().getString(R.string.userconfire_notequal), Toast.LENGTH_SHORT).show();
		} else {
			RegistSubmit();
		}
	}

	/**
	 * 注册账号
	 */
	private void RegistSubmit() {
		dialogShow();
		KJStringParams params = new KJStringParams();
		params.put("method", "reg");
		params.put("user", name.getText().toString().trim());
		params.put("psw", MD5.get32MD5(newPassword.getText().toString().trim()));
		Constants.mHttpClient.post(Constants.BASE_USER_URL, params, new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				KJLoger.debug_i("login", content);
				dialogDismiss();
				try {
					JSONObject json = new JSONObject(content);
					if (json.optString("result").equals("failed")) {
						Toast.makeText(UserRegistActivity.this, getResources().getString(R.string.user_registfail), Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(UserRegistActivity.this, getResources().getString(R.string.user_registsuccess), Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(UserRegistActivity.this, UserLoginActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dialogDismiss();
			}
		});
	}

}
