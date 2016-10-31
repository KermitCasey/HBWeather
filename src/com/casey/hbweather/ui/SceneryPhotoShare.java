package com.casey.hbweather.ui;

import java.io.ByteArrayOutputStream;

import org.kymjs.aframe.http.HttpCallBack;
import org.kymjs.aframe.http.KJFileParams;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;
import org.kymjs.aframe.utils.MD5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.casey.hbweather.R;
import com.casey.hbweather.base.HeadBaseActivity;
import com.casey.hbweather.ui.ImageDate;
import com.casey.hbweather.utils.Constants;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月9日 上午9:51:03    类说明  
 *
 */
public class SceneryPhotoShare extends HeadBaseActivity {
	private Bundle bundle;
	private EditText edit;
	private String addr = "武汉市，洪山区，珞喻路";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scenery_photoshare);
		Intent intent = this.getIntent();
		bundle = intent.getExtras(); 
		//imageDate= SceneryForumFragment.imageDate.get((Integer) intent.getSerializableExtra("pos"));
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		changeMenuBg(true, R.drawable.yes);
		headMenu.setText("");
		setHeadTitle("发布实景");
		ImageView image = (ImageView) findViewById(R.id.photoshare);
		Bitmap bitmap = (Bitmap) bundle.get("data");
		image.setImageBitmap(bitmap);
		
		TextView ad = (TextView)findViewById(R.id.textView1);
		ad.setText(splitLocation(Constants.mPosition.getAddrStr()));
		
		edit = (EditText)findViewById(R.id.edit_text);
		edit.setOnEditorActionListener(new OnEditorActionListener() {            
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				
				//Log.i("edit", (String) v.getText());
				//Toast.makeText(SceneryPhotoShare.this, String.valueOf(actionId), Toast.LENGTH_SHORT).show();  
				return false;
			}
    });
	}

	private String splitLocation(String location){
		String result = "";
		int s = location.indexOf("省")+1;
		int e = location.indexOf("市")+1;
		if (s >=0 && e >= 0)
			result = result + location.substring(s, e) + ",";
		s = e;
		e = location.indexOf("区")+1;
		if (s >=0 && e >= 0)
			result = result + location.substring(s, e) + ",";
		s = e;
		if (s >=0)
			result = result + location.substring(s);
		return result;
	}
	
	@Override
	public void mOnClickHeadMenu() {

		KJFileParams params = new KJFileParams();
		Bitmap bit = (Bitmap) bundle.get("data");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bit.compress(Bitmap.CompressFormat.PNG, 100, baos);
		params.put("latitude", ""+Constants.mPosition.getLatitude());
		params.put("longitude", ""+Constants.mPosition.getLongitude());
		params.put("location", ""+splitLocation(Constants.mPosition.getAddrStr()));
		params.put("method", "uploadImage");
		params.put(baos.toByteArray());
		StringCallBack fcb = new StringCallBack() {

			@Override
			public void onSuccess(String json) {
				
				Log.i("content", json);
				Intent data=new Intent();
		        setResult(1, data);
				finish();
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				//Toast.makeText(this, "上传失败", 1);
				Log.i("content", "fail"+errorNo+" "+strMsg);
			}
		};
		fcb.setProgress(true);//http://192.168.1.102:8080/hbqx/liveview.do?method=uploadImage
		Constants.mHttpClient.post(Constants.BASE_LIVE_VIEW+"?method=uploadImage", params, fcb);
	}

}
