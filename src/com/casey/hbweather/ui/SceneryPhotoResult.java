package com.casey.hbweather.ui;

import com.casey.hbweather.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SceneryPhotoResult extends Activity{
	Bundle bundle;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scenery_photoresult);
		Intent intent = this.getIntent();
		bundle = intent.getExtras(); 
		Bitmap bitmap = (Bitmap) bundle.get("data");
		
		ImageView photo = (ImageView)findViewById(R.id.photoresult);
		photo.setImageBitmap(bitmap);
		
		TextView re = (TextView)findViewById(R.id.textView1);
		re.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent data=new Intent();  
	            setResult(1, data);  
	            //关闭掉这个Activity  
	            finish();  
			}
			
		});
		
		TextView use = (TextView)findViewById(R.id.textView2);
		use.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent data=new Intent();
				data.putExtras(bundle);
	            setResult(2, data);  
	            //关闭掉这个Activity  
	            finish();  
			}
			
		});
	}
}
