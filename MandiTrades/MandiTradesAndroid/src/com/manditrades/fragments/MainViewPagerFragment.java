package com.manditrades.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manditrades.R;

public class MainViewPagerFragment extends Fragment {

	private Context context;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private List<Fragment> fragmentsList;

	public MainViewPagerFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.main_view_pager, container,
				false);

		context = container.getContext();

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getChildFragmentManager());

		// Set up the ViewPager with the sections adapter.
		ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager.setCurrentItem(1);

		return rootView;
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			fragmentsList = new ArrayList<Fragment>();

			fragmentsList.add(new SellersListFragment());
			fragmentsList.add(new HomeFragment());
			fragmentsList.add(new NewSaleFragment());

			return super.instantiateItem(container, position);
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentsList.get(position);
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "Seller's List";
			case 1:
				return "Mandi Trades";
			case 2:
				return "Select Commodity";
			}
			return null;
		}
	}
}