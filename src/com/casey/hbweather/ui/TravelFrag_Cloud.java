package com.casey.hbweather.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.http.FileCallBack;
import org.kymjs.aframe.http.HttpCallBack;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;
import org.kymjs.aframe.http.downloader.FileDownLoader;
import org.kymjs.aframe.utils.ImageLoadutils;
import org.kymjs.aframe.widget.sliding.AbSlidingPlayView;

import com.casey.hbweather.R;
import com.casey.hbweather.base.MyBaseFragment;
import com.casey.hbweather.utils.Constants;
import com.google.gson.Gson;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月4日 下午10:16:48    类说明  
 *
 */
public class TravelFrag_Cloud extends MyBaseFragment {

	View rootView;
	ImageView abSlidingPlayView;
	Context context;
	JSONArray cloudURLList;
	TextView cloudTime;
	int pageNum = 0;
	AnimationDrawable mAnimationDrawable;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragtravel_cloud, null);
			initView(rootView);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	private void initView(View view) {
		context = getActivity();
		mAnimationDrawable = new AnimationDrawable();
		abSlidingPlayView = (ImageView) view.findViewById(R.id.mAbSlidingPlayView);
		cloudTime = (TextView) view.findViewById(R.id.cloud_time);
		abSlidingPlayView.setBackgroundColor(getResources().getColor(R.color.transparent));
		getCloudUrlList();
	}

	public void DownLoadAsyncTask() {

		if (cloudURLList.length() <= 0) {
			dialogDismiss();
			return;
		}
		for (int i = 0; i < cloudURLList.length(); i++) {
			new DownloadImageTask().execute(cloudURLList.optString(i));
		}

	}

	private void getCloudUrlList() {
		dialogShow();
		KJStringParams params = new KJStringParams();
		params.put("method", "getCloudImages");
		Constants.mHttpClient.post(Constants.BASE_QUERY_URL, params, new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				KJLoger.debug_i("cloud", content);
				try {
					JSONObject json = new JSONObject(content);
					cloudURLList = json.optJSONArray("cloud");
					cloudTime.setText(json.optString("name"));
					DownLoadAsyncTask();
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

	private Drawable loadImageFromNetwork(String imageUrl) {
		Drawable drawable = null;
		try {
			// 可以在这里通过文件名来判断，是否本地有此图片
			drawable = Drawable.createFromStream(new URL(imageUrl).openStream(), getImageNameFromURL(imageUrl));
		} catch (IOException e) {
			KJLoger.debug_d("loadImageFromNetwork", e.getMessage());
		}
		if (drawable == null) {
			KJLoger.debug_d("loadImageFromNetwork", "null drawable");
		}
		return drawable;
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {

		protected Drawable doInBackground(String... urls) {
			return loadImageFromNetwork(urls[0]);
		}

		protected void onPostExecute(Drawable result) {
			pageNum++;
			if (result != null) {
				mAnimationDrawable.addFrame(result, 500);
			}
			if (pageNum >= cloudURLList.length()) {
				dialogDismiss();
				mAnimationDrawable.setOneShot(false);
				abSlidingPlayView.setBackgroundDrawable(mAnimationDrawable);
				mAnimationDrawable.start();
			}
		}
	}

	private String getImageNameFromURL(String imageUrl) {
		int start = imageUrl.lastIndexOf("/");
		return imageUrl.substring(start + 1);

	}
}
