package com.manditrades.adapters;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.manditrades.R;
import com.manditrades.jsonwrapper.Alert;
import com.manditrades.jsonwrapper.AlertList;
import com.manditrades.util.JsonDataCallback;
import com.manditrades.util.MTAlertUtil;
import com.manditrades.util.MTURLHelper;

public class AlertListAdapter extends BaseAdapter {

	private Context context;
	private Alert alert;
	private ArrayList<Alert> alerts;
	private TextView commodityTV;
	private ImageView deleteIV;

	public AlertListAdapter(Context context, ArrayList<Alert> alerts) {
		this.context = context;
		this.alerts = alerts;
	}

	@Override
	public int getCount() {
		return alerts.size();
	}

	@Override
	public Alert getItem(int position) {
		return alerts.get(position);
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
			convertView = mInflater.inflate(R.layout.alert_list_template, null);
		}
		alert = getItem(position);
		deleteIV = (ImageView) convertView.findViewById(R.id.delete);
		commodityTV = (TextView) convertView.findViewById(R.id.commodity);

		commodityTV.setText(alert.getCommodity() + " < " + alert.getPrice());

		deleteIV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				deleteAlert(position);
			}
		});

		return convertView;
	}

	private void deleteAlert(final int position) {

		Alert alert = alerts.get(position);

		String url = MTURLHelper.getAPIEndpointURL(MTURLHelper.notificationURL);
		String method = "POST";
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		Object[] objects = { url, method, params };

		params.add(new BasicNameValuePair("mobile_no", alert.getMobileNo()));
		params.add(new BasicNameValuePair("deviceArn", alert.getDeviceArn()));
		params.add(new BasicNameValuePair("device_info", alert.getDeviceInfo()));
		params.add(new BasicNameValuePair("commodity", alert.getCommodity()));
		params.add(new BasicNameValuePair("price", alert.getPrice()));
		params.add(new BasicNameValuePair("delete", "true"));
		params.add(new BasicNameValuePair("deviceToken", alert.getDeviceToken()));

		JsonDataCallback callback = new JsonDataCallback(context) {
			@Override
			public void receiveData(JSONObject responseJson) {
				try {
					alerts.remove(position);
					notifyDataSetChanged();

					AlertList alertList = new AlertList();
					alertList.setAlerts(alerts);

					String strAlerts = new Gson().toJson(alertList);

					Editor editor = PreferenceManager
							.getDefaultSharedPreferences(context).edit();
					editor.putString("STR_ALERT_LIST", strAlerts);
					editor.commit();

					MTAlertUtil.showMessesBox(context, "Mandi Trades",
							"Alert Deleted successfully",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int i) {

								}
							});

				} catch (Exception exception) {

				}
			}
		};

		callback.execute(objects);

	}

}
