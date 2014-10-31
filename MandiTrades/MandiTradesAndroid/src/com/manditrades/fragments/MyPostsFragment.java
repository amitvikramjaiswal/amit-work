package com.manditrades.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.manditrades.R;
import com.manditrades.adapters.MyPostAdapter;
import com.manditrades.jsonwrapper.MTSeller;
import com.manditrades.jsonwrapper.MTSellerList;
import com.manditrades.util.JsonDataCallback;
import com.manditrades.util.MTURLHelper;

public class MyPostsFragment extends Fragment {

	private Context context;
	private SharedPreferences preferences;

	private ListView myPostLV;
	protected ArrayList<MTSeller> mtSellerList;
	protected MyPostAdapter myPostAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_my_post, container,
				false);

		context = container.getContext();

		initComponents(rootView);

		makeMyPostRequest();

		setValues();

		setListeners();

		return rootView;
	}

	private void makeMyPostRequest() {
		String mobileNo = preferences.getString("MOBILE_NO", "");

		String url = MTURLHelper
				.getAPIEndpointURL(MTURLHelper.myPostURL);

		String method = "POST";

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("from", "1"));
		params.add(new BasicNameValuePair("to", "10"));
		params.add(new BasicNameValuePair("seller_mobile", mobileNo));

		Object[] object = { url, method, params, null };

		JsonDataCallback callback = new JsonDataCallback(context) {

			@Override
			public void receiveData(JSONObject responseJson) {

				Gson gson = new Gson();
				MTSellerList sellerList = gson.fromJson(
						responseJson.toString(), MTSellerList.class);

				mtSellerList = sellerList.getMTSellerList();
				myPostAdapter = new MyPostAdapter(context, mtSellerList);
				myPostLV.setAdapter(myPostAdapter);

			}
		};
		callback.execute(object);
	}

	private void setListeners() {

	}

	private void setValues() {

	}

	private void initComponents(View rootView) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		myPostLV = (ListView) rootView.findViewById(R.id.my_post_list);
	}

}
