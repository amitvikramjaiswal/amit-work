package com.manditrades.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manditrades.R;

public class SubscriptionFragment extends Fragment {

	private View rootView;
	private Context context;
	private SharedPreferences preferences;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_subscription, container,
				false);
		context = container.getContext();

		initComponents();
		setValues();
		setListeners();

		return rootView;
	}

	private void setListeners() {

	}

	private void setValues() {

	}

	private void initComponents() {

	}

}
