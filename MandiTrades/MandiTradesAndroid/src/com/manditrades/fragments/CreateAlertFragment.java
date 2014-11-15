package com.manditrades.fragments;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.manditrades.R;
import com.manditrades.activities.CreateAlertActivity;
import com.manditrades.adapters.AlertListAdapter;
import com.manditrades.jsonwrapper.AlertList;
import com.manditrades.util.JsonDataCallback;
import com.manditrades.util.MTURLHelper;

public class CreateAlertFragment extends Fragment implements OnKeyListener {

	private Context context;
	private SharedPreferences preferences;
	private AlertList alertList;
	private ListView alertsLV;
	private int position;
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_alerts, container, false);

		context = container.getContext();

		initComponents();
		setActionBar();
		setValues();
		setListeners();

		return rootView;
	}

	private void setActionBar() {
		final Activity activity = (Activity) context;
		activity.getActionBar()
				.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		activity.getActionBar().setCustomView(
				R.layout.layout_action_bar_create_alert);

		View view = LayoutInflater.from(activity).inflate(
				R.layout.layout_action_bar_create_alert, null);

		TextView customView = (TextView) view
				.findViewById(R.id.action_bar_title);

		customView.setText("Create Alert");

		final ImageButton createImgBtn = (ImageButton) view
				.findViewById(R.id.create_img_btn);
		final ImageButton backImgBtn = (ImageButton) view
				.findViewById(R.id.backBtn);

		backImgBtn.setVisibility(View.VISIBLE);

		backImgBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});

		createImgBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Fragment fragment = new NewSaleFragment();
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				transaction.replace(R.id.frame_container, fragment);
				Bundle args = new Bundle();
				args.putString("FROM", "AlertsFragment");
				args.putSerializable("ALERT_LIST", alertList);
				fragment.setArguments(args);
				transaction.addToBackStack(null);
				transaction.commit();
				createImgBtn.setVisibility(View.INVISIBLE);
			}
		});

		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);

		activity.getActionBar().setCustomView(view, params);
	}

	private void setListeners() {
		alertsLV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long l) {
				Intent intent = new Intent(context, CreateAlertActivity.class);
				intent.putExtra("UPDATE", true);
				intent.putExtra("ALERT", alertList.getAlerts().get(position));
				startActivity(intent);
			}
		});
	}

	private void setValues() {
		String mobileNo = preferences.getString("MOBILE_NO", null);

		String url = MTURLHelper
				.getAPIEndpointURL(MTURLHelper.displayAlertsURL);
		String method = "POST";
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mobile_no", mobileNo));

		Object[] objects = { url, method, params };

		JsonDataCallback callback = new JsonDataCallback(context) {

			@Override
			public void receiveData(JSONObject responseJson) {
				try {
					if (!responseJson.get("root").toString().contains("No Data Found")) {
						alertList = new Gson().fromJson(
								responseJson.toString(), AlertList.class);
						AlertListAdapter adapter = new AlertListAdapter(
								context, alertList.getAlerts());
						alertsLV.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};

		callback.execute(objects);

	}

	private void initComponents() {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		alertsLV = (ListView) rootView.findViewById(R.id.alerts_list);
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			((Activity) context).finish();
			System.out.println("!$@#%$^%&^*!@$#%$^%&^&!@#%$^%&^*&*");
			return true;
		}
		return false;
	}

}
