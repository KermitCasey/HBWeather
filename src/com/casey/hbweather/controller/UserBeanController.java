package com.casey.hbweather.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import com.casey.hbweather.model.UserBean;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月23日 下午6:23:30    类说明  
 *
 */
public class UserBeanController {

	public void saveUserBean(UserBean userBean, Context context) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(userBean);

		SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		String userBeanString = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));

		editor.putString("userinfo", userBeanString);
		editor.commit();
		oos.close();
	}

	public UserBean getSavedUserBean(Context context) throws StreamCorruptedException, IOException, ClassNotFoundException {

		SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
		String userBean64String = sharedPreferences.getString("userinfo", "");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] userBeanBtye64 = Base64.decode(userBean64String.getBytes(), Base64.DEFAULT);
		ByteArrayInputStream bais = new ByteArrayInputStream(userBeanBtye64);

		ObjectInputStream ois = new ObjectInputStream(bais);
		UserBean userBean = (UserBean) ois.readObject();
		ois.close();
		return userBean;
	}

	public void cleatSaveUserBean(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
	}
}
