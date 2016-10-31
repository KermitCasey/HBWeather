package com.casey.hbweather;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.database.DaoConfig;
import org.kymjs.aframe.database.KJDB;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.ui.activity.BaseSplash;

import com.casey.hbweather.controller.PollXmlService;
import com.casey.hbweather.controller.UserBeanController;
import com.casey.hbweather.model.CityBean;
import com.casey.hbweather.model.CollectionCity;
import com.casey.hbweather.model.UserBean;
import com.casey.hbweather.utils.Constants;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class Welcome extends BaseSplash {

	@Override
	protected void setRootBackground(ImageView view) {

		view.setBackgroundResource(R.drawable.welcome);
		Constants.mHttpClient = new KJHttp();
		Constants.mHttpClient.setTimeout(120 * 1000);
		createSDCardDir();
		KJLoger.IS_DEBUG = Constants.DEBUG;
		// 初始化数据库

		DaoConfig daoConfig = new DaoConfig();
		daoConfig.setContext(this);
		daoConfig.setDebug(Constants.DEBUG);
		daoConfig.setDbName(Constants.DBName);
		Constants.HUDataBase = KJDB.create(daoConfig);
		try {
			Constants.userBean = new UserBeanController().getSavedUserBean(this);
		} catch (Exception e) {
			Constants.userBean = null;
		}
		new getCityAsyncTask().execute();
	}

	@Override
	protected void redirectTo() {
		super.redirectTo();
		// skipActivity(this, LeaderSearchDialog.class);
	}

	public void createSDCardDir() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			File path1 = new File(sdcardDir.getPath() + File.separator + Constants.APP_DIR);
			File path2 = new File(path1.getPath() + File.separator + Constants.IMAGE_DIR);
			File path3 = new File(path1.getPath() + File.separator + Constants.FILR_DIR);
			File pathCache = new File(path1.getPath() + File.separator + Constants.IMAGE_CACHE);
			if (!path1.exists()) {
				path1.mkdirs();
			}
			if (!path2.exists()) {
				path2.mkdirs();
			}
			if (!path3.exists()) {
				path3.mkdirs();
			}
			if (!pathCache.exists()) {
				pathCache.mkdirs();
			}
		}
	}

	class getCityAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				InputStream inputStream = getResources().getAssets().open("city.xml");
				PollXmlService.readXml(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			skipActivity(Welcome.this, InitActivity.class);
		}

	}

}
