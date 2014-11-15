package com.manditrades.activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.manditrades.R;
import com.manditrades.cache.UserProfileCache;
import com.manditrades.pushreceiver.MessageReceivingService;
import com.manditrades.util.BadgeUtil;

public class SplashScreenActivity extends Activity {

	private Context context;
	private SharedPreferences preferences;
	private SharedPreferences sharedPreferences;
	private String numOfMissedMessages;
	public static Boolean inBackground = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// MTUtil.getDeviceId(getApplicationContext());
		// MTUtil.getAndroidId(getApplicationContext());

		context = this;
		sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);

		BadgeUtil.setBadge(context, 0);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_splash_screen);

		numOfMissedMessages = getString(R.string.num_of_missed_messages);
		startService(new Intent(this, MessageReceivingService.class));

		Thread th = new Thread() {
			public void run() {
				try {
					sleep(1000);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					onPause();
					if (sharedPreferences.contains("MOBILE_NO")) {
						Intent intent = new Intent(context, HomeActivity.class);
						startActivity(intent);
						UserProfileCache.getUserProfile().setProfile(null,
								sharedPreferences.getString("MOBILE_NO", ""));
					} else {
						Intent intent = new Intent(getApplicationContext(),
								ShowEULAActivity.class);
						startActivity(intent);
					}
				}
			}
		};
		th.start();
	}

	public void onStop() {
		super.onStop();
		inBackground = true;
	}

	public void onResume() {
		super.onResume();
		inBackground = false;
		preferences = MessageReceivingService.savedValues;
		int numOfMissedMessages = 0;
		if (preferences != null) {
			numOfMissedMessages = preferences.getInt(this.numOfMissedMessages,
					0);
		}
		String newMessage = getMessage(numOfMissedMessages);
		if (newMessage != "") {
			Log.i("displaying message", newMessage);
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		finish();
	}

	private String getMessage(int numOfMissedMessages) {
		String message = "";
		String linesOfMessageCount = getString(R.string.lines_of_message_count);
		if (numOfMissedMessages > 0) {
			String plural = numOfMissedMessages > 1 ? "s" : "";
			Log.i("onResume", "missed " + numOfMissedMessages + " message"
					+ plural);
			for (int i = 0; i < preferences.getInt(linesOfMessageCount, 0); i++) {
				String line = preferences.getString("MessageLine" + i, "");
				message += (line + "\n");
			}
			NotificationManager mNotification = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mNotification.cancel(R.string.notification_number);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putInt(this.numOfMissedMessages, 0);
			editor.putInt(linesOfMessageCount, 0);
			editor.commit();
		} else {
			Log.i("onResume", "no missed messages");
			Intent intent = getIntent();
			if (intent != null) {
				Bundle extras = intent.getExtras();
				if (extras != null) {
					for (String key : extras.keySet()) {
						message += key + "=" + extras.getString(key) + "\n";
					}
				}
			}
		}
		message += "\n";
		return message;
	}

}
