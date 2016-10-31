package com.casey.hbweather.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.bitmap.KJBitmap;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;
import org.kymjs.aframe.ui.widget.KJListView;
import org.kymjs.aframe.ui.widget.KJRefreshListener;
import org.kymjs.aframe.utils.ImageLoadutils;

import com.casey.hbweather.R;
import com.casey.hbweather.utils.AppUtils;
import com.casey.hbweather.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月4日 下午9:04:35    类说明  
 *
 */
public class SceneryForumFragment extends Fragment implements KJRefreshListener {
	Context context;
	View rootView;
	private KJListView gridView;
	private ImageView camera;
	private PhotoAdapter adapter;
	private LayoutInflater inflater;

	static List<ImageDate> imageDate = new ArrayList<ImageDate>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.frag_sceneryforum, null);
		}
		getData();

		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		context = getActivity();
		gridView = (KJListView) rootView.findViewById(R.id.photogrid);
		gridView.setPullLoadEnable(false);
		gridView.setFooterDividersEnabled(false);
		gridView.setOnRefreshListener(this);

		gridView.setOnRefreshListener(new KJRefreshListener() {

			@Override
			public void onRefresh() {
				
				getData();
			}

			@Override
			public void onLoadMore() {
				

			}

		});

		camera = (ImageView) rootView.findViewById(R.id.camera);
		camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (Constants.userBean == null) {
					AppUtils.showUserCenterDialog(context, "拍摄实景照片");
				} else {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
					startActivityForResult(intent, 1);
				}
			}

		});

		return rootView;
	}

	public void getData() {
		KJStringParams params = new KJStringParams();
		params.put("method", "getLatestLiveView");
		params.put("limit", "" + (imageDate.size() + 10));
		Constants.mHttpClient.post(Constants.BASE_LIVE_VIEW, params, new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				KJLoger.debug_i("实景列表", content);
				try {
					JSONObject json = new JSONObject(content);
					imageDate = new Gson().fromJson(json.optString("images"), new TypeToken<ArrayList<ImageDate>>() {
					}.getType());
					adapter = new PhotoAdapter(imageDate, inflater);
					gridView.setAdapter(adapter);
					gridView.stopPullRefresh();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {

				super.onFailure(t, errorNo, strMsg);
				KJLoger.debug_i("实景列表", "onFailure");
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			switch (resultCode) {
			case Activity.RESULT_OK:// 照相完成点击确定
				Bundle bundle = data.getExtras();
				Intent intent = new Intent(context, SceneryPhotoResult.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, 2);
				break;
			case Activity.RESULT_CANCELED:// 取消
				break;
			}
		case 2:
			switch (resultCode) {
			case 1:// 重照
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
				startActivityForResult(intent, 1);
				break;
			case 2:// 确定
				Bundle bundle = data.getExtras();
				Intent intent2 = new Intent(context, SceneryPhotoShare.class);
				intent2.putExtras(bundle);
				startActivityForResult(intent2, 3);

				break;
			}
		case 3:
			switch (resultCode) {
			case 1:// 重照
				getData();
				break;
			}
		}

	}

	private class PhotoAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		private List<Picture> pictures;

		public PhotoAdapter(List<ImageDate> list, LayoutInflater inflater) {
			super();
			this.inflater = inflater;
			pictures = new ArrayList<Picture>();
			for (int i = 0; i < list.size(); i++) {

				Picture picture = new Picture(list.get(i).getUploadPlaceDesc(), list.get(i).getSmallUrl(), list.get(i).getLargeUrl());
				pictures.add(picture);
			}
		}

		@Override
		public int getCount() {
			
			if (pictures.size() % 3 > 0) {
				return pictures.size() / 3 + 1;
			} else {
				return pictures.size() / 3;
			}
		}

		@Override
		public Object getItem(int position) {
			
			return pictures.get(position);
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.frag_scenerygriditem, parent, false);
				viewHolder = new ViewHolder();
				viewHolder.title1 = (TextView) convertView.findViewById(R.id.phototitle1);
				viewHolder.image1 = (ImageView) convertView.findViewById(R.id.photo1);
				viewHolder.p1 = (RelativeLayout) convertView.findViewById(R.id.p1);
				viewHolder.title2 = (TextView) convertView.findViewById(R.id.phototitle2);
				viewHolder.image2 = (ImageView) convertView.findViewById(R.id.photo2);
				viewHolder.p2 = (RelativeLayout) convertView.findViewById(R.id.p2);
				viewHolder.title3 = (TextView) convertView.findViewById(R.id.phototitle3);
				viewHolder.image3 = (ImageView) convertView.findViewById(R.id.photo3);
				viewHolder.p3 = (RelativeLayout) convertView.findViewById(R.id.p3);
				viewHolder.index = position;
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			final int index = position * 3;
			if (pictures.size() > index) {
				if (pictures.get(index).getTitle().length() > 7) {
					String temp = pictures.get(index).getTitle().substring(0, 5) + "...";
					viewHolder.title1.setText(temp);
				} else
					viewHolder.title1.setText(pictures.get(index).getTitle());
				ImageLoadutils.imageLoader.displayImage(pictures.get(index).small, viewHolder.image1, ImageLoadutils.options2);
				viewHolder.p1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						
						Intent intent = new Intent(context, SceneryPhotoDetail.class);
						Bundle bundle = new Bundle();
						Log.i("index", "" + index);
						bundle.putSerializable("pos", index);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
				viewHolder.p1.setVisibility(View.VISIBLE);
			} else {
				viewHolder.p1.setVisibility(View.INVISIBLE);
			}

			if (pictures.size() > index + 1) {
				if (pictures.get(index + 1).getTitle().length() > 7) {
					String temp = pictures.get(index + 1).getTitle().substring(0, 5) + "...";
					viewHolder.title2.setText(temp);
				} else
					viewHolder.title2.setText(pictures.get(index + 1).getTitle());
				ImageLoadutils.imageLoader.displayImage(pictures.get(index + 1).small, viewHolder.image2);
				viewHolder.p2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						
						Intent intent = new Intent(context, SceneryPhotoDetail.class);
						Bundle bundle = new Bundle();
						Log.i("index", "" + index + 1);
						bundle.putSerializable("pos", index + 1);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
				viewHolder.p2.setVisibility(View.VISIBLE);
			} else {
				viewHolder.p2.setVisibility(View.INVISIBLE);
			}

			if (pictures.size() > index + 2) {
				if (pictures.get(index).getTitle().length() > 7) {
					String temp = pictures.get(index + 2).getTitle().substring(0, 5) + "...";
					viewHolder.title3.setText(temp);
				} else
					viewHolder.title3.setText(pictures.get(index + 2).getTitle());
				ImageLoadutils.imageLoader.displayImage(pictures.get(index + 2).small, viewHolder.image3, ImageLoadutils.options2);
				viewHolder.p3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						
						Intent intent = new Intent(context, SceneryPhotoDetail.class);
						Bundle bundle = new Bundle();
						Log.i("index", "" + index + 2);
						bundle.putSerializable("pos", index + 2);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
				viewHolder.p3.setVisibility(View.VISIBLE);
			} else {
				viewHolder.p3.setVisibility(View.INVISIBLE);
			}

			return convertView;
		}

		class ViewHolder {
			public TextView title1;
			public ImageView image1;
			public RelativeLayout p1;
			public TextView title2;
			public ImageView image2;
			public RelativeLayout p2;
			public TextView title3;
			public ImageView image3;
			public RelativeLayout p3;
			public int index;
		}

		class Picture {
			private String title;
			private String small;
			private String large;

			public Picture() {
				super();
			}

			public Picture(String title, String small, String large) {
				super();
				this.title = title;
				this.setSmall(small);
				this.setLarge(large);
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getSmall() {
				return small;
			}

			public void setSmall(String small) {
				this.small = small;
			}

			public String getLarge() {
				return large;
			}

			public void setLarge(String large) {
				this.large = large;
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kymjs.aframe.ui.widget.KJRefreshListener#onLoadMore()
	 */
	@Override
	public void onLoadMore() {
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kymjs.aframe.ui.widget.KJRefreshListener#onRefresh()
	 */
	@Override
	public void onRefresh() {
		

	}
}

class ImageDate implements Serializable {
	private int imageId;
	private String imageName;
	private String smallUrl;
	private String largeUrl;
	private String userId;
	private String uploadPlaceDesc;
	private String uploadTime;
	private String imageInfo;

	public ImageDate(int id, String name, String small, String large, String user, String PlaceDesc, String Time, String Info) {
		super();
		this.setImageId(id);
		this.setImageName(name);
		this.setSmallUrl(small);
		this.setLargeUrl(large);
		this.setUserId(user);
		this.setUploadPlaceDesc(PlaceDesc);
		this.setUploadTime(Time);
		this.setImageInfo(Info);
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getUploadPlaceDesc() {
		return uploadPlaceDesc;
	}

	public void setUploadPlaceDesc(String uploadPlaceDesc) {
		String temp[] = uploadPlaceDesc.split(",");
		String result = "";
		if (!temp[0].equalsIgnoreCase("(null)"))
			result = result + temp[0];
		if (temp[1] != "(null)")
			result = result + "," + temp[1];
		if (temp[2] != "(null)")
			result = result + "," + temp[2];
		this.uploadPlaceDesc = result;
	}

	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}

	public String getLargeUrl() {
		return largeUrl;
	}

	public void setLargeUrl(String largeUrl) {
		this.largeUrl = largeUrl;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(String imageInfo) {
		this.imageInfo = imageInfo;
	}
}
