package com.casey.hbweather.ui;

import org.kymjs.aframe.utils.ImageLoadutils;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.casey.hbweather.R;
import com.casey.hbweather.base.HeadBaseActivity;
import com.casey.hbweather.ui.ImageDate;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月9日 上午9:51:03    类说明  
 *
 */
public class SceneryPhotoDetail extends HeadBaseActivity {
	private ImageDate imageDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scenery_photodetail);
		Intent intent = this.getIntent(); 
		imageDate= SceneryForumFragment.imageDate.get((Integer) intent.getSerializableExtra("pos"));
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		changeMenuBg(false, 0);
		headMenu.setText("");
		setHeadTitle("查看详情");
		ImageView image = (ImageView) findViewById(R.id.photo);
		ImageLoadutils.imageLoader.displayImage(imageDate.getLargeUrl(), image,ImageLoadutils.options2);
		
		TextView address = (TextView) findViewById(R.id.dizhi);
		address.setText(imageDate.getUploadPlaceDesc());
		TextView title = (TextView) findViewById(R.id.textView1);
		title.setText(imageDate.getImageInfo());
		TextView data = (TextView) findViewById(R.id.data);
		data.setText(imageDate.getUploadTime().substring(0, 9));
		TextView time = (TextView) findViewById(R.id.time);
		time.setText(imageDate.getUploadTime().substring(11, 16));
		TextView infonum = (TextView) findViewById(R.id.infonum);
		infonum.setText("评论"+0);
	}

	@Override
	public void mOnClickHeadMenu() {
		Intent intent = new Intent(this, CityWeather_CityManage.class);
		startActivity(intent);
	}

}
