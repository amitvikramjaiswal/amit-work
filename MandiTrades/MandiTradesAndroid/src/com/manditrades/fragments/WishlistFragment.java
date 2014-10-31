package com.manditrades.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.manditrades.R;
import com.manditrades.activities.SellerInfoActivity;
import com.manditrades.adapters.WishListAdapter;
import com.manditrades.jsonwrapper.MTSeller;
import com.manditrades.jsonwrapper.MTSellerList;

public class WishlistFragment extends Fragment {

	private Context context;
	private ListView sellersList;
	private WishListAdapter wishListAdapter;

	private ArrayList<MTSeller> mtSellerList;
	private SharedPreferences preferences;

	public WishlistFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_wishlist, container,
				false);
		context = container.getContext();

		initComponents(rootView);

		loadData();

		// setListeners();

		return rootView;
	}

	private void initComponents(View rootView) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		sellersList = (ListView) rootView.findViewById(R.id.sellers_list);

	}

	private void loadData() {

		Gson gson = new Gson();

		String json = preferences.getString("WISH_LIST", null);

		MTSellerList sellerList = gson.fromJson(json, MTSellerList.class);

		if (sellerList == null)
			return;

		mtSellerList = sellerList.getMTSellerList();
		wishListAdapter = new WishListAdapter(context, mtSellerList,
				"WishlistFragment");
		sellersList.setAdapter(wishListAdapter);

	}

	private void setListeners() {
		sellersList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {

				Intent intent = new Intent(context, SellerInfoActivity.class);

				intent.putExtra("SELLER_INFO", mtSellerList.get(position));
				intent.putExtra("SOURCE", "WishlistFragment");
				startActivity(intent);

			}
		});
	}
}
