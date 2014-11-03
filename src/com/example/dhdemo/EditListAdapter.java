package com.example.dhdemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


class NormalModeAdapter extends BaseAdapter 
{
	LayoutInflater inflater = null;
	Activity mActivity;

	@Override
	public int getCount() 
	{
		return 0;
	}

	@Override
	public Object getItem(int arg0) 
	{
		return null;
	}

	@Override
	public long getItemId(int arg0) 
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolderNormal viewHolder;
		
		if (convertView == null) 
		{
			if (inflater == null) 
			{
				inflater = (LayoutInflater) mActivity.getSystemService("layout_inflater");
			}
		
			//convertView = inflater.inflate(R.layout.item_normal, null);
			convertView = inflater.inflate(R.layout.item_edit, null);
			//viewHolder = new ViewHolderNormal();
			//viewHolder.artistText = (TextView) convertView.findViewById(R.id.artistTextNormal);
			//viewHolder.titleText = (TextView) convertView.findViewById(R.id.titleTextNormal);
			//viewHolder.durationText = (TextView) convertView.findViewById(R.id.durationTextNormal);
			//convertView.setTag(viewHolder);
		} 
		else 
		{
			//viewHolder = (ViewHolderNormal) convertView.getTag();
		}
			
		//viewHolder.titleText.setText(mySongCollection[position].title);
		//viewHolder.artistText.setText(mySongCollection[position].artist);
		//viewHolder.durationText.setText(mySongCollection[position].duration);
			
		return convertView;
	}
	
	static class ViewHolderNormal 
	{
		TextView titleText;
		TextView artistText;
		TextView durationText;
	}
}

	
