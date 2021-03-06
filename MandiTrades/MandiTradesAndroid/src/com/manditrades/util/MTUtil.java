package com.manditrades.util;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.manditrades.R;

public class MTUtil {

	public static String computeDistance(String lat, String lng) {

		double latitude = CurrentLocation.getCurrentLatitude();
		double longitude = CurrentLocation.getCurrentLongitude();

		Location currentLocation = new Location("point A");
		currentLocation.setLatitude(latitude);
		currentLocation.setLongitude(longitude);
		Location sellerLocation = new Location("point B");
		if (lat == null || lng == null)
			return "0.0 km";
		sellerLocation.setLatitude(Double.parseDouble(lat));
		sellerLocation.setLongitude(Double.parseDouble(lng));
		double totalDistance = currentLocation.distanceTo(sellerLocation) / 1000.00;

		return String.format("%.2f %s", totalDistance,
				totalDistance == 1 ? "Km" : "Kms");

	}

	public static void setActionBar(Context context, String title,
			final boolean isActivity) {

		final Activity activity = (Activity) context;
		activity.getActionBar()
				.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		activity.getActionBar().setCustomView(
				R.layout.layout_action_bar_with_back);

		View view = LayoutInflater.from(activity).inflate(
				R.layout.layout_action_bar_with_back, null);

		TextView customView = (TextView) view
				.findViewById(R.id.action_bar_title);

		customView.setText(title);

		ImageButton backButton = (ImageButton) view.findViewById(R.id.backBtn);
		backButton.setImageResource(R.drawable.ios_back_button);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isActivity)
					activity.finish();
				else
					activity.getFragmentManager().popBackStack();
			}
		});

		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);

		activity.getActionBar().setCustomView(view, params);

	}

	public static String getDeviceId(Context context) {
		String deviceId = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

		System.out.println("** DEVICE_ID ** " + deviceId);

		return deviceId;
	}

	public static String getAndroidId(Context context) {
		String androidId = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);

		System.out.println("@@ ANDROID_ID @@ " + androidId);

		return androidId;
	}

}
