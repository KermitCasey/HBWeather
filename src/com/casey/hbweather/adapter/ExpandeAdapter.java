package com.casey.hbweather.adapter;

import java.util.ArrayList;
import java.util.List;

import com.casey.hbweather.R;
import com.casey.hbweather.model.IndicesBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandeAdapter extends BaseExpandableListAdapter{
	
	private Context mContext;  
	private LayoutInflater mInflater = null;
	private String[] mGroupStrings = null; 
	private List<ArrayList<IndicesBean>> mData = null;

	
	public ExpandeAdapter(Context ctx, List<ArrayList<IndicesBean>> list) {  
        mContext = ctx;  
        mInflater = (LayoutInflater) mContext  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        mGroupStrings = mContext.getResources().getStringArray(R.array.groups);
        mData = list;
    }  
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		
		return mData.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		if(convertView == null)
		{
			convertView = mInflater.inflate(R.layout.specialtravel_child_item_layout, null);
		}
		TextView tempText = (TextView)convertView.findViewById(R.id.indices_text_item);
		//ImageView tempImage = (ImageView)convertView.findViewById(R.id.indices_detials_item);
		tempText.setText(mData.get(groupPosition).get(childPosition).getName());
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		
		return mData.get(groupPosition).size();  
	}

	@Override
	public Object getGroup(int groupPosition) {
		
		return mData.get(groupPosition);  
	}

	@Override
	public int getGroupCount() {
		
		return mData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		if(convertView == null)
		{
			convertView = mInflater.inflate(R.layout.specialtravel_group_item_layout, null);
		}
		 ImageView mgroupimage=(ImageView)convertView.findViewById(R.id.special_travel_group_up);
         if(!isExpanded){
             mgroupimage.setImageResource(R.drawable.arrow_down);
          }
         else
         {
        	 mgroupimage.setImageResource(R.drawable.arrow_up);
         }
         if(mData.get(groupPosition).size() == 0)
         {
        	 mgroupimage.setVisibility(View.INVISIBLE);
         }
         else
         {
        	 mgroupimage.setVisibility(View.VISIBLE);
         }
		TextView tempText = (TextView)convertView.findViewById(R.id.special_travel_group_text);
		tempText.setText(mGroupStrings[groupPosition]);
		switch(groupPosition)
		{
			case 0:
				tempText.setTextColor(mContext.getResources().getColor(R.color.specailGreenColor));
				break;
			case 1:
				tempText.setTextColor(mContext.getResources().getColor(R.color.specailBluesColor));
				break;
			case 2:
				tempText.setTextColor(mContext.getResources().getColor(R.color.specailOrangeColor));
				break;
			case 3:
				tempText.setTextColor(mContext.getResources().getColor(R.color.specailRedsColor));
				break;
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		
		return true;
	}

}
