package com.casey.hbweather.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;
import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;
import org.kymjs.aframe.ui.widget.KJListView;
import org.kymjs.aframe.ui.widget.KJRefreshListener;
import org.kymjs.aframe.utils.HandleDate;

import com.casey.hbweather.R;
import com.casey.hbweather.base.MyBaseFragment;
import com.casey.hbweather.model.CityWarnBean;
import com.casey.hbweather.model.ForeCastBean;
import com.casey.hbweather.model.IndicesBean;
import com.casey.hbweather.model.SceneryBean;
import com.casey.hbweather.ui.SpecialTravelFrag_Forecast.ListDetailAdapter;
import com.casey.hbweather.ui.SpecialTravelFrag_Rt.AllSceneAdapter.ViewHolder;
import com.casey.hbweather.ui.TravelFrag_Warn.WarnListAdapter;
import com.casey.hbweather.ui.TravelFrag_Warn.WarnListAdapter.VHolder;
import com.casey.hbweather.utils.AppUtils;
import com.casey.hbweather.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SpecialTravelFrag_Forecast extends MyBaseFragment implements OnFocusChangeListener,OnClickListener,TextWatcher,OnItemClickListener,KJRefreshListener{
	View rootView;
	private ImageView mImageView;
	private ImageView mImageViewHint;
	private TextView mImageViewCancel;
	private EditText  mTextView;
	private ListView mListView;
	private KJListView mListaViewDetail;
	private View mLayout;
	private String id;
	//private ArrayAdapter<String> av;
	private Context context;
	private ArrayList<IndicesBean> AllSceneryBeanListResave= new ArrayList<IndicesBean>();
	private ArrayList<IndicesBean> AllSceneryBeanList = new ArrayList<IndicesBean>();
	private ArrayList<ForeCastBean> ForeCastBeanList = new ArrayList<ForeCastBean>();
	private ListDetailAdapter mWarnListAdapter;
	private AllSceneAdapter mAllSceneAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragspecialtravel_forecast, null);
			initView(rootView);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		init();
		return rootView;
	}
	
	private void initView(View rootView){
		mImageView = (ImageView)rootView.findViewById(R.id.special_travel_rt_imageView);
		mTextView = (EditText)rootView.findViewById(R.id.special_travel_rt_editText);
		mImageViewCancel = (TextView)rootView.findViewById(R.id.special_travel_rt_text);
		mImageViewHint = (ImageView)rootView.findViewById(R.id.special_travel_rt_hint);
		mListView = (ListView)rootView.findViewById(R.id.special_travel_rt_list);
		mListaViewDetail = (KJListView) rootView.findViewById(R.id.special_travel_rt_list_detail);
		mLayout = (View)rootView.findViewById(R.id.special_travel_rt_layout);
	}
	
	private void init(){
		initData();
		mTextView.setOnFocusChangeListener(this);
		mTextView.addTextChangedListener(this);
		mImageViewCancel.setOnClickListener(this);
		mImageViewHint.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
		mImageView.setFocusable(true);
		mImageView.setFocusableInTouchMode(true);
	}
	
	private void initData(){
		context = getActivity();
		dialogShow();
		KJStringParams params = new KJStringParams();

		params.put("method", "getSceneryList");
		Constants.mHttpClient.post(Constants.BASE_URL, params, new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				dialogDismiss();
				KJLoger.debug_i("getWarnListData", content);
				
				try {
					JSONObject json = new JSONObject(content);
					Log.e("infor", json.toString());
					AllSceneryBeanListResave = new Gson().fromJson(json.optString("list"), new TypeToken<ArrayList<IndicesBean>>() {
					}.getType());
					constructer(AllSceneryBeanList,AllSceneryBeanListResave);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				dialogDismiss();
				super.onFailure(t, errorNo, strMsg);
			}
		});
		mAllSceneAdapter = new AllSceneAdapter();
		mListView.setAdapter(mAllSceneAdapter);  
		
		mListaViewDetail.setFooterDividersEnabled(false);
		mListaViewDetail.setPullLoadEnable(false);
		mListaViewDetail.setOnRefreshListener(this);
		mWarnListAdapter = new ListDetailAdapter();
		mListaViewDetail.setAdapter(mWarnListAdapter);
		
	}

	private void constructer(ArrayList<IndicesBean> one,ArrayList<IndicesBean> two)
	{
		one.clear();
		for(Iterator<IndicesBean> it = two.iterator();it.hasNext();)
		{
			IndicesBean temp = it.next();
			one.add(temp);
		}
	}
	
	private void getForeCastBeanData(String id)
	{
		dialogShow();
		transparentDialog.setCancelable(false);
		KJStringParams params = new KJStringParams();

		params.put("method", "getJxh");
		params.put("id", id);
		Log.e("infor", id);
		Constants.mHttpClient.post(Constants.BASE_URL, params, new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				dialogDismiss();
				KJLoger.debug_i("getWarnListData", content);
				try {
					JSONObject json = new JSONObject(content);
					ForeCastBeanList = new Gson().fromJson(json.optString("list"), new TypeToken<ArrayList<ForeCastBean>>() {
					}.getType());
					mWarnListAdapter.notifyDataSetChanged();
					mListaViewDetail.setRefreshTime(HandleDate.ConverNowText(Constants.FORMAT_01));
					mListaViewDetail.stopPullRefresh();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}
	
	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		
		if(arg0 == mTextView && arg1)
		{
			rootView.setBackgroundColor(context.getResources().getColor(R.color.white));
			RelativeLayout.LayoutParams layoutParams=
				    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
			layoutParams.setMargins(10, 0, 0, 0);
			mLayout.setLayoutParams(layoutParams);
			mImageViewCancel.setVisibility(View.VISIBLE);
			mListaViewDetail.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);
			mTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
		}
	}

	@Override
	public void onClick(View v) {
		
		if(v == mImageViewCancel)
		{
			constructer(AllSceneryBeanList,AllSceneryBeanListResave);
			//Log.e("infor", "sizeCancel == "+AllSceneryBeanList.size());
			mAllSceneAdapter.notifyDataSetChanged();
			
			mTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
			mTextView.setText("");
			RelativeLayout.LayoutParams layoutParams=
				    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			mLayout.setLayoutParams(layoutParams);
			// 隐藏输入法
			InputMethodManager imm = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			// 显示或者隐藏输入法
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			mImageViewCancel.setVisibility(View.GONE);
			mImageView.requestFocus();
		}
		else if(v == mImageViewHint)
		{
			mTextView.setText("");
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
		
	}

	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
		constructer(AllSceneryBeanList,AllSceneryBeanListResave);
		//Log.e("infor", "siezde ==== "+AllSceneryBeanList.size());
		if (s.length() == 0) {
			mImageViewHint.setVisibility(View.GONE);
		} else {
			mImageViewHint.setVisibility(View.VISIBLE);
		}
		String key =  mTextView.getText().toString();
		if(key.equals(""))
		{
			constructer(AllSceneryBeanList,AllSceneryBeanListResave);
			Log.e("infor", "siezde ==== "+AllSceneryBeanList.size());
			mAllSceneAdapter.notifyDataSetChanged();
			Log.e("infor", AllSceneryBeanList.size()+"");
			return;
		}
		//Log.e("infor", key);
		for(Iterator<IndicesBean> it = AllSceneryBeanList.iterator();it.hasNext();)
		{
			IndicesBean temp = it.next();
			if(temp.getName().indexOf(key)!=0)
			{
				it.remove();
			}
		}
		//Log.e("infor", "size1111 == "+AllSceneryBeanList.size());
		mAllSceneAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		rootView.setBackgroundResource(R.drawable.special_travel_back);
		mImageViewCancel.setVisibility(View.GONE);
		// 隐藏输入法
		InputMethodManager imm = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		// 显示或者隐藏输入法
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		mImageView.requestFocus();
		mListView.setVisibility(View.GONE);
		
		mListaViewDetail.setVisibility(View.VISIBLE);
		id = AllSceneryBeanList.get(arg2).getId();
		ForeCastBeanList.clear();
		mWarnListAdapter.notifyDataSetChanged();
		getForeCastBeanData(id);
		mTextView.setText(AllSceneryBeanList.get(arg2).getName());
		mTextView.setTextColor(getActivity().getResources().getColor(R.color.black75PercentColor));
	}
	
	
	public class AllSceneAdapter extends BaseAdapter {


		@Override
		public int getCount() {
			return AllSceneryBeanList.size();
		}

		@Override
		public Object getItem(int position) {
			return AllSceneryBeanList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public boolean isEnabled(int position) {
			return true;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder  myViews;
			if (convertView == null) {
				myViews = new ViewHolder ();
				convertView = LayoutInflater.from(context).inflate(R.layout.fragspecailtravel_seacheritem, null);
				myViews.mNameText  = (TextView) convertView.findViewById(R.id.seacher_item);
				convertView.setTag(myViews);
			} else {
				 myViews = (ViewHolder ) convertView.getTag();
			}
			myViews.mNameText.setText(AllSceneryBeanList.get(position).getName());
			return convertView;
		}
		
		 class ViewHolder {
		      private TextView mNameText;
		 }

	
	}
	
	
	public class ListDetailAdapter extends BaseAdapter {

		VHolder vHolder;

		@Override
		public int getCount() {
			return ForeCastBeanList.size();
		}

		@Override
		public Object getItem(int position) {
			return ForeCastBeanList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public boolean isEnabled(int position) {
			return false;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ForeCastBean item = ForeCastBeanList.get(position);
			if (convertView == null) {
				vHolder = new VHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.fragspecailtravel_forecast_listitem, null);
				vHolder.time = (TextView) convertView.findViewById(R.id.specail_tracel_forecast_listitem_text1);
				vHolder.weatherCode = (TextView) convertView.findViewById(R.id.specail_tracel_forecast_listitem_text4);
				vHolder.weatherDesc = (TextView) convertView.findViewById(R.id.specail_tracel_forecast_listitem_text3);
				vHolder.windDirect = (TextView) convertView.findViewById(R.id.specail_tracel_forecast_listitem_text2);
				vHolder.windGrade = (TextView) convertView.findViewById(R.id.specail_tracel_forecast_listitem_text5);
				vHolder.weatherIcon = (ImageView) convertView.findViewById(R.id.specail_tracel_forecast_listitem_iamge);
				convertView.setTag(vHolder);
			} else {
				vHolder = (VHolder) convertView.getTag();
			}
			if(item.getTime().indexOf("#")!=-1)
			{
				vHolder.time.setText(item.getTime().substring(0, item.getTime().indexOf("#"))+
						"\n" + item.getTime().substring(item.getTime().indexOf("#")+1));
			}
			else
			{
				vHolder.time.setText(item.getTime());
			}
			vHolder.weatherCode.setText(item.getTemperature()+"°C");
			vHolder.weatherDesc.setText(item.getWeatherDesc());
			vHolder.windDirect.setText(item.getWindDirect()+"风");
			vHolder.windGrade.setText(item.getWindGrade()+"级");
			vHolder.weatherIcon.setImageResource(AppUtils.getWeatherIconFromString(item.getWeatherDesc()));
			return convertView;
		}

		class VHolder {
			TextView time, weatherCode, weatherDesc ,windDirect,windGrade ;
			ImageView weatherIcon;
		}
	}


	@Override
	public void onRefresh() {
		
		getForeCastBeanData(id);
	}

	@Override
	public void onLoadMore() {
		
		
	}
}
