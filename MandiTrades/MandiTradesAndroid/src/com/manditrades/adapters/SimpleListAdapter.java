package com.manditrades.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.manditrades.R;

public class SimpleListAdapter extends BaseAdapter {

	private Context context;
	private String [] stringArray ;
	private TextView textView;

	public SimpleListAdapter(Context context, String [] stringArray) {
		this.context = context;
		this.stringArray = stringArray;
	}

	@Override
	public int getCount() {
		return stringArray.length;
	}

	@Override
	public String getItem(int position) {
		return stringArray[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.simple_list_template, null);
		}

		textView = (TextView) convertView.findViewById(R.id.text);
		textView.setText(stringArray[position]);

		return convertView;
	}
}
