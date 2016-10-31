package com.casey.hbweather.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import com.casey.hbweather.R;
import com.casey.hbweather.adapter.ExpandeAdapter;
import com.casey.hbweather.base.MyBaseFragment;
import com.casey.hbweather.model.IndicesBean;
import com.casey.hbweather.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class SpecialTravelFrag_Indices extends MyBaseFragment implements OnChildClickListener {
	View rootView;
	private ArrayList<IndicesBean> mBeanList1 = new ArrayList<IndicesBean>();
	private ArrayList<IndicesBean> mBeanList2 = new ArrayList<IndicesBean>();
	private ArrayList<IndicesBean> mBeanList3 = new ArrayList<IndicesBean>();
	private ArrayList<IndicesBean> mBeanList4 = new ArrayList<IndicesBean>();
	private List<ArrayList<IndicesBean>> mList = new ArrayList<ArrayList<IndicesBean>>();
	private Context context;
	private ExpandableListView mListView;
	ExpandeAdapter expandeAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragspecialtravel_indices, null);
			initView(rootView);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		init();
		return rootView;
	}

	private void initView(View rootView) {
		mListView = (ExpandableListView) rootView.findViewById(R.id.special_travel_inddices_list);
	}

	private void init() {
		context = getActivity();
		expandeAdapter = new ExpandeAdapter(context, mList);
		mListView.setAdapter(expandeAdapter);
		mListView.setOnChildClickListener(this);
		initData();
	}

	private void initData() {
		dialogShow();
		KJStringParams params = new KJStringParams();
		params.put("method", "getTripIndex");
		Constants.mHttpClient.post(Constants.BASE_URL, params, new StringCallBack() {

			@Override
			public void onSuccess(String content) {
				dialogDismiss();
				KJLoger.debug_i("getIndicesListData", content);
				try {
					JSONObject json = new JSONObject(content);
					mBeanList1 = new Gson().fromJson(json.optString("1"), new TypeToken<ArrayList<IndicesBean>>() {
					}.getType());
					mBeanList2 = new Gson().fromJson(json.optString("2"), new TypeToken<ArrayList<IndicesBean>>() {
					}.getType());
					mBeanList3 = new Gson().fromJson(json.optString("3"), new TypeToken<ArrayList<IndicesBean>>() {
					}.getType());
					mBeanList4 = new Gson().fromJson(json.optString("4"), new TypeToken<ArrayList<IndicesBean>>() {
					}.getType());
					mList.add(mBeanList1);
					mList.add(mBeanList2);
					mList.add(mBeanList3);
					mList.add(mBeanList4);
					expandeAdapter.notifyDataSetChanged();
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

	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

		Intent intent = new Intent(context, TravelSceneryPointDetail.class);
		Bundle bundle = new Bundle();
		bundle.putInt("type", 1);
		bundle.putSerializable("IndicesBean", mList.get(groupPosition).get(childPosition));
		intent.putExtras(bundle);
		startActivity(intent);

		return true;
		/*
		 * Item item = mAdapter.getChild(groupPosition, childPosition); new
		 * AlertDialog.Builder(this) .setTitle(item.getName())
		 * .setMessage(item.getDetail())
		 * .setIcon(android.R.drawable.ic_menu_more)
		 * .setNegativeButton(android.R.string.cancel, new OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { //
		 * TODO Auto-generated method stub
		 * 
		 * } }).create().show(); return true;
		 */
	}
}
