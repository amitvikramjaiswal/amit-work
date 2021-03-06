package com.manditrades.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.manditrades.R;
import com.manditrades.jsonwrapper.Alert;
import com.manditrades.jsonwrapper.AlertList;
import com.manditrades.util.JsonDataCallback;
import com.manditrades.util.MTAlertUtil;
import com.manditrades.util.MTURLHelper;
import com.manditrades.util.MTUtil;

public class CreateAlertActivity extends Activity {

	private Context context;
	private SharedPreferences preferences;
	private EditText priceET;
	private Button createAlertBtn;
	private String mobileNo;
	private String commodity;
	private String deviceId;
	private String deviceInfo;
	private String price;
	private boolean isUpdate;
	private String endpoint;
	private String deviceToken;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_alert);

		context = this;

		if (getIntent().hasExtra("UPDATE"))
			isUpdate = getIntent().getExtras().getBoolean("UPDATE", false);

		initComponent();
		setValues();
		setActionBar();

		setListeners();
	}

	private void setValues() {
		if (isUpdate) {
			createAlertBtn.setText("Update Alert");
			Alert alert = (Alert) getIntent().getExtras().getSerializable(
					"ALERT");
			mobileNo = alert.getMobileNo();
			endpoint = alert.getDeviceArn();
			commodity = alert.getCommodity();
			deviceId = MTUtil.getDeviceId(context);
			deviceInfo = alert.getDeviceInfo();
			deviceToken = alert.getDeviceToken();
		} else {
			mobileNo = preferences.getString("MOBILE_NO", null);
			endpoint = preferences.getString("ENDPOINT", null);
			commodity = getIntent().getExtras().getString("SELECTED_COMMODITY");
			deviceId = MTUtil.getDeviceId(context);
			deviceInfo = "android";
			deviceToken = preferences.getString("DEVICE_TOKEN", null);
		}
	}

	private void setActionBar() {
		MTUtil.setActionBar(context, commodity, true);
	}

	private void setListeners() {
		createAlertBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				price = priceET.getText().toString();
				if (price == null || price.equals("")) {
					MTAlertUtil.showMessesBox(context, "Mandi Trades",
							"Please enter the price.",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});
				} else {
					createAlert();
				}

			}
		});
	}

	private void createAlert() {
		String url = MTURLHelper.getAPIEndpointURL(MTURLHelper.notificationURL);
		String method = "POST";
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		Object[] objects = { url, method, params };

		params.add(new BasicNameValuePair("mobile_no", mobileNo));
		params.add(new BasicNameValuePair("deviceArn", endpoint));
		params.add(new BasicNameValuePair("device_info", deviceInfo));
		params.add(new BasicNameValuePair("commodity", commodity));
		params.add(new BasicNameValuePair("price", price));
		params.add(new BasicNameValuePair("delete", "false"));
		params.add(new BasicNameValuePair("deviceToken", deviceToken));

		JsonDataCallback callback = new JsonDataCallback(context) {
			@Override
			public void receiveData(JSONObject responseJson) {

				try {
					Gson gson = new Gson();

					Alert alert = gson.fromJson(
							responseJson.getJSONObject("root").toString(),
							Alert.class);

					String strAlert;
					AlertList alertList;

					alertList = (AlertList) getIntent().getExtras()
							.getSerializable("ALERT_LIST");

					String strMsg;
					if (alertList != null) {

						int index = Collections.binarySearch(
								alertList.getAlerts(), alert,
								new MTAlertComparator());

						if (index < 0) {
							alertList.getAlerts().add(alert);
							strMsg = "Alert Added successfully";
						} else {
							alertList.getAlerts().set(index, alert);
							strMsg = "Alert Updated successfully";
						}
						strAlert = gson.toJson(alertList);
					} else {
						ArrayList<Alert> alerts = new ArrayList<Alert>();
						alerts.add(alert);

						alertList = new AlertList();
						alertList.setAlerts(alerts);

						strAlert = gson.toJson(alertList);
						strMsg = "Alert Added successfully";
					}
					Editor editor = preferences.edit();
					editor.putString("STR_ALERT_LIST", strAlert);
					editor.commit();

					MTAlertUtil.showMessesBox(context, "Mandi Trades", strMsg,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Editor editor = preferences.edit();
									editor.putString("SOURCE",
											"CreateAlertActivity");
									editor.commit();
									finish();
								}
							});

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		};

		callback.execute(objects);
	}

	private void initComponent() {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		priceET = (EditText) findViewById(R.id.price_et);
		createAlertBtn = (Button) findViewById(R.id.create_alert);
	}

}

class MTAlertComparator implements Comparator<Alert> {

	@Override
	public int compare(Alert alert1, Alert alert2) {
		if (alert1.getAlertId().getId().equals(alert2.getAlertId().getId())) {
			return 0;
		} else {
			return -1;
		}
	}

}