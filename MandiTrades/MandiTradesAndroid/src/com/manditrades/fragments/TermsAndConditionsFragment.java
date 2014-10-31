package com.manditrades.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.manditrades.R;

public class TermsAndConditionsFragment extends Fragment {

	private WebView termsWV;
	private View rootView;
	private SharedPreferences preferences;
	private Context context;

	public TermsAndConditionsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		context = container.getContext();

		rootView = inflater.inflate(R.layout.fragment_terms, container, false);

		initComponents();
		setValues();

		return rootView;
	}

	private void setValues() {
		termsWV.loadData(
				context.getResources().getString(R.string.termsconditions),
				"text/html", "utf-8");
	}

	private void initComponents() {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		termsWV = (WebView) rootView.findViewById(R.id.terms);
	}
}
