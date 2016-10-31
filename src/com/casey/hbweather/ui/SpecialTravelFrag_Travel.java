package com.casey.hbweather.ui;

import com.casey.hbweather.R;
import com.casey.hbweather.base.MyBaseFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SpecialTravelFrag_Travel extends MyBaseFragment {
	View rootView;
	WebView mSpecialTravelWeb;
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragspecialtravel_travel, null);
			initView(rootView);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}

		return rootView;
	}

	/**
	 * @param rootView2
	 */
	private void initView(View rootView) {
		context = getActivity();
		mSpecialTravelWeb = (WebView) rootView.findViewById(R.id.mspecial_travel);
		mSpecialTravelWeb.getSettings().setJavaScriptEnabled(true);
		mSpecialTravelWeb.getSettings().setSupportZoom(false);
		mSpecialTravelWeb.getSettings().setBuiltInZoomControls(false);
		mSpecialTravelWeb.setWebViewClient(new MyWebViewClient(context));

		mSpecialTravelWeb.getSettings().setAppCacheEnabled(false);
		mSpecialTravelWeb.getSettings().setDatabaseEnabled(false);
		mSpecialTravelWeb.getSettings().setDomStorageEnabled(false);
		mSpecialTravelWeb.getSettings().setGeolocationEnabled(false);

		mSpecialTravelWeb.loadUrl("http://219.140.167.87/WEB/feature.html");

	}

	private class MyWebViewClient extends WebViewClient {
		private Context mContext;

		public MyWebViewClient(Context context) {
			super();
			mContext = context;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			dialogShow();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			dialogDismiss();
		}
	}

}
