package com.casey.hbweather.ui;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;
import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;
import org.kymjs.aframe.ui.widget.KJListView;
import org.kymjs.aframe.ui.widget.KJRefreshListener;
import org.kymjs.aframe.utils.HandleDate;

import com.casey.hbweather.R;
import com.casey.hbweather.base.MyBaseFragment;
import com.casey.hbweather.model.ForeCastBean;
import com.casey.hbweather.model.IndicesBean;
import com.casey.hbweather.ui.SpecialTravelFrag_Forecast.AllSceneAdapter;
import com.casey.hbweather.ui.SpecialTravelFrag_Forecast.ListDetailAdapter;
import com.casey.hbweather.ui.SpecialTravelFrag_Forecast.ListDetailAdapter.VHolder;
import com.casey.hbweather.utils.Constants;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class SpecialTravelFrag_Rt extends MyBaseFragment implements OnFocusChangeListener,OnClickListener,TextWatcher,OnItemClickListener,KJRefreshListener{
	View rootView;
	private ImageView mImageView;
	private ImageView mImageViewHint;
	private TextView mImageViewCancel;
	private EditText  mTextView;
	private ListView mListView;
	private KJListView mListaViewDetail;
	private View mLayout;
	private ArrayAdapter<String> av;
	private Context context;
	private ArrayList<IndicesBean> AllSceneryBeanListResave= new ArrayList<IndicesBean>();
	private ArrayList<IndicesBean> AllSceneryBeanList = new ArrayList<IndicesBean>();
	private ArrayList<String> xAxis = new ArrayList<String>(); // 坐标轴
	private ArrayList<ArrayList<Integer>> allData = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> humidity = new ArrayList<Integer>(); //湿度，百分比
	private ArrayList<Integer> windGradeArray = new ArrayList<Integer>(); //风力
	private ArrayList<Integer> tempArray = new ArrayList<Integer>(); // 温度，°c
	private ArrayList<Integer> rainArray = new ArrayList<Integer>(); // 降雨量，毫升/小时
	private ArrayList<String> xAxisUV = new ArrayList<String>(); // 紫外线横轴
	private ArrayList<String> xAxisAnion = new ArrayList<String>(); //负离子横轴
	private ArrayList<Integer> uvArray = new ArrayList<Integer>(); // 紫外线纵轴
	private ArrayList<Integer> anionArray = new ArrayList<Integer>(); //负离子纵轴
	private ListDetailAdapter mWarnListAdapter;
	private LineChart mLineChart;
	private int[] mColors = ColorTemplate.VORDIPLOM_COLORS;
	private AllSceneAdapter mAllSceneAdapter;
	private String id;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragspecialtravel_rt, null);
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
		
		mImageView = (ImageView)rootView.findViewById(R.id.special_rt_travel_rt_imageView);
		mTextView = (EditText)rootView.findViewById(R.id.special_rt_travel_rt_editText);
		mImageViewCancel = (TextView)rootView.findViewById(R.id.special_rt_travel_rt_text);
		mImageViewHint = (ImageView)rootView.findViewById(R.id.special_rt_travel_rt_hint);
		mListView = (ListView)rootView.findViewById(R.id.special_rt_travel_rt_list);
		mListaViewDetail = (KJListView) rootView.findViewById(R.id.special_rt_travel_rt_list_detail);
		mLayout = (View)rootView.findViewById(R.id.special_rt_travel_rt_layout);
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

		params.put("method", "getJxhSceneryList");
		Constants.mHttpClient.post(Constants.BASE_URL, params, new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				dialogDismiss();
				KJLoger.debug_i("getWarnListData", content);
				try {
					JSONObject json = new JSONObject(content);
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
		transparentDialog.setOnCancelListener(new OnCancelListener(){

			@Override
			public void onCancel(DialogInterface dialog) {
				
				
			}
			
		});
		KJStringParams params = new KJStringParams();

		params.put("method", "getShikuang");
		params.put("id", id);
		Constants.mHttpClient.post(Constants.BASE_URL, params, new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				dialogDismiss();
				KJLoger.debug_i("getWarnListData", content);
				try {
					JSONObject json = new JSONObject(content);
					xAxis = new Gson().fromJson(json.optString("xAxis"), new TypeToken<ArrayList<String>>() {
					}.getType());
					humidity = new Gson().fromJson(json.optString("humidity"), new TypeToken<ArrayList<Integer>>() {
					}.getType());
					windGradeArray = new Gson().fromJson(json.optString("windGradeArray"), new TypeToken<ArrayList<Integer>>() {
					}.getType());
					tempArray = new Gson().fromJson(json.optString("tempArray"), new TypeToken<ArrayList<Integer>>() {
					}.getType());
					rainArray = new Gson().fromJson(json.optString("rainArray"), new TypeToken<ArrayList<Integer>>() {
					}.getType());
					xAxisUV = new Gson().fromJson(json.optString("xAxisUV"), new TypeToken<ArrayList<String>>() {
					}.getType());
					xAxisAnion = new Gson().fromJson(json.optString("xAxisAnion"), new TypeToken<ArrayList<String>>() {
					}.getType());
					uvArray = new Gson().fromJson(json.optString("uvArray"), new TypeToken<ArrayList<Integer>>() {
					}.getType());
					anionArray = new Gson().fromJson(json.optString("anionArray"), new TypeToken<ArrayList<Integer>>() {
					}.getType());
					allData.clear();
					allData.add(tempArray);
					allData.add(humidity);
					allData.add(rainArray);
					allData.add(windGradeArray);
					allData.add(uvArray);
					allData.add(anionArray);
					
					mWarnListAdapter.notifyDataSetChanged();
					mListaViewDetail.setRefreshTime(HandleDate.ConverNowText(Constants.FORMAT_01));
					mListaViewDetail.stopPullRefresh();
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
		
		if(AllSceneryBeanList.get(arg2).getName().contains("建设中"))
		{
			Toast.makeText(getActivity(), "该景区正在建设中..请等待..", 1500).show();
			return;
		}
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
			return allData.size();
		}

		@Override
		public Object getItem(int position) {
			return allData.get(position);
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
			if (convertView == null) {
				vHolder = new VHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.fragspecialtravel_rt_listitem, null);
				vHolder.x1 = (TextView) convertView.findViewById(R.id.speccialtravel_rt_text1);
				vHolder.x2 = (TextView) convertView.findViewById(R.id.speccialtravel_rt_text2);
				vHolder.x3 = (TextView) convertView.findViewById(R.id.speccialtravel_rt_tex3);
				vHolder.x4 = (TextView) convertView.findViewById(R.id.speccialtravel_rt_text4);
				vHolder.x5 = (TextView) convertView.findViewById(R.id.speccialtravel_rt_text5);
				vHolder.x6 = (TextView) convertView.findViewById(R.id.speccialtravel_rt_text6);
				vHolder.x7 = (TextView) convertView.findViewById(R.id.speccialtravel_rt_text7);
				vHolder.x8 = (TextView) convertView.findViewById(R.id.speccialtravel_rt_text8);
				vHolder.type = (TextView) convertView.findViewById(R.id.speccialtravel_rt_text);
				convertView.setTag(vHolder);
			} else {
				vHolder = (VHolder)convertView.getTag();
			}
			
			if(xAxis.size()==8)
			{
				vHolder.x1.setText(xAxis.get(0));
				vHolder.x2.setText(xAxis.get(1));
				vHolder.x3.setText(xAxis.get(2));
				vHolder.x4.setText(xAxis.get(3));
				vHolder.x5.setText(xAxis.get(4));
				vHolder.x6.setText(xAxis.get(5));
				vHolder.x7.setText(xAxis.get(6));
				vHolder.x8.setText(xAxis.get(7));
			}
	
			
			switch(position)
			{
			case 0:
				vHolder.type.setText("温度(°C)");
				break;
			case 1:
				vHolder.type.setText("湿度(%)");
				break;
			case 2:
				vHolder.type.setText("降雨量(ml/h)");
				break;
			case 3:
				vHolder.type.setText("风力(级)");
				break;
			case 4:
				vHolder.type.setText("紫外线(uw/m2)");
				if(xAxisUV.size()==8)
				{
					vHolder.x1.setText(xAxisUV.get(0));
					vHolder.x2.setText(xAxisUV.get(1));
					vHolder.x3.setText(xAxisUV.get(2));
					vHolder.x4.setText(xAxisUV.get(3));
					vHolder.x5.setText(xAxisUV.get(4));
					vHolder.x6.setText(xAxisUV.get(5));
					vHolder.x7.setText(xAxisUV.get(6));
					vHolder.x8.setText(xAxisUV.get(7));
				}
				break;
			case 5:
				vHolder.type.setText("负离子(m2/(V.s))");
				if(xAxisAnion.size()==8)
				{
					vHolder.x1.setText(xAxisAnion.get(0));
					vHolder.x2.setText(xAxisAnion.get(1));
					vHolder.x3.setText(xAxisAnion.get(2));
					vHolder.x4.setText(xAxisAnion.get(3));
					vHolder.x5.setText(xAxisAnion.get(4));
					vHolder.x6.setText(xAxisAnion.get(5));
					vHolder.x7.setText(xAxisAnion.get(6));
					vHolder.x8.setText(xAxisAnion.get(7));
				}
				break;
			}
			mLineChart = (LineChart) convertView.findViewById(R.id.speccialtravel_rt_chart1);
			initLineData(mLineChart,position);
			return convertView;
		}

		class VHolder {
			TextView x1, x2, x3 ,x4 , x5, x6 ,x7 , x8 ,type;
		}
		
		public void initLineData(LineChart LineChart,int position)
		{
			mLineChart.setDrawYValues(true);//显示Y轴数据、
			mLineChart.setDrawGridBackground(false);//表格背景
			mLineChart.setTouchEnabled(false);//是否可点击
			mLineChart.setDragEnabled(false);//
			mLineChart.setDrawHorizontalGrid(false);//水平网格线
			mLineChart.setDrawVerticalGrid(false);//垂直网格线
			mLineChart.setValueTextColor(Color.WHITE);//Y轴数据字体颜色
			mLineChart.setValueTextSize(15f);//Y轴字体大小
			mLineChart.setDescription("");//表格描述
			mLineChart.setDrawXLabels(false);//X轴标志位
			mLineChart.setDrawYLabels(false);//Y轴标志位
			mLineChart.setDrawBorder(false);//底部横线
			mLineChart.setDrawLegend(false);//底部表示块
			if(position!=2)
			{
				mLineChart.setValueFormatter(new ValueFormatter()
				{

					@Override
					public String getFormattedValue(float value) {
						
						return (int)value+"";
					}
					
				});
			}
			
			
			addEmptyData();
			addDataSet(position);
			mLineChart.invalidate();
		}
		
		private void addEmptyData() {

			String[] xVals = new String[8];

			for (int i = 0; i < 8; i++)
				xVals[i] = "";

			LineData data = new LineData(xVals);

			mLineChart.setData(data);
			mLineChart.invalidate();
		}

		private void addDataSet(int positon) {

			LineData data = mLineChart.getData();

			if (data != null) {

				int count = (data.getDataSetCount() + 1);

				// create 10 y-vals
				ArrayList<Entry> yVals = new ArrayList<Entry>();

				for (int i = 0; i < data.getXValCount(); i++)
					if(allData.get(positon).size()==8)
					{
						yVals.add(new Entry(Integer.valueOf(allData.get(positon).get(i)), i));
					}
					else
					{
						yVals.add(new Entry(0, i));
					}

				LineDataSet set = new LineDataSet(yVals, null);
				set.setLineWidth(2.0f);
				set.setCircleSize(2.5f);

				//int color = mColors[count % mColors.length];

				set.setColor(Color.rgb(255, 255, 255));
				set.setCircleColor(Color.rgb(255, 255, 255));

				data.addDataSet(set);
				mLineChart.notifyDataSetChanged();
				mLineChart.invalidate();
			}
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
