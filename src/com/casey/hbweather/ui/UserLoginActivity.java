package com.casey.hbweather.ui;

import java.io.ByteArrayOutputStream;

import org.json.JSONObject;
import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;
import org.kymjs.aframe.utils.MD5;
import org.kymjs.aframe.widget.ClearEditText;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.baidu.lbsapi.auth.i;
import com.casey.hbweather.R;
import com.casey.hbweather.base.HeadBaseActivity;
import com.casey.hbweather.base.MyBaseActivity;
import com.casey.hbweather.controller.UserBeanController;
import com.casey.hbweather.model.UserBean;
import com.casey.hbweather.utils.Constants;
import com.google.gson.Gson;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月19日 下午5:53:34    类说明  
 *
 */
public class UserLoginActivity extends HeadBaseActivity {

	ClearEditText username, userpassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		setHeadTitle(getResources().getString(R.string.user_myOwn));
		changeMenuBg(false, 0);

		username = (ClearEditText) findViewById(R.id.username);
		userpassword = (ClearEditText) findViewById(R.id.userpassword);

		findViewById(R.id.userRegist).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserLoginActivity.this, UserRegistActivity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.userSubmit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isAllowLogin();
			}
		});
	}

	@Override
	public void mOnClickHeadMenu() {

	}

	/**
	 * 判断条件是否满足
	 */
	private void isAllowLogin() {
		if (TextUtils.isEmpty(username.getText())) {
			Toast.makeText(this, getResources().getString(R.string.username_nonull), Toast.LENGTH_SHORT).show();
		} else if (TextUtils.isEmpty(userpassword.getText())) {
			Toast.makeText(this, getResources().getString(R.string.userpassword_nonull), Toast.LENGTH_SHORT).show();
		} else {
			LoginSubmit();
		}

	}

	/**
	 * 登录
	 */
	private void LoginSubmit() {
		dialogShow();
		KJStringParams params = new KJStringParams();
		params.put("method", "login");
		params.put("user", username.getText().toString().trim());
		params.put("psw", MD5.get32MD5(userpassword.getText().toString().trim()));
		Constants.mHttpClient.post(Constants.BASE_USER_URL, params, new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				KJLoger.debug_i("login", content);
				dialogDismiss();
				try {
					JSONObject json = new JSONObject(content);
					if (json.optBoolean("result")) {
						Constants.userBean = new Gson().fromJson(json.optString("user"), UserBean.class);
						new UserBeanController().saveUserBean(Constants.userBean, UserLoginActivity.this);
						skipActivity(UserLoginActivity.this, UserCenterActivity.class);
					} else {
						Toast.makeText(UserLoginActivity.this, getResources().getString(R.string.user_loginfail), Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
				super.onFailure(t, errorNo, strMsg);
				dialogDismiss();
				Toast.makeText(UserLoginActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
