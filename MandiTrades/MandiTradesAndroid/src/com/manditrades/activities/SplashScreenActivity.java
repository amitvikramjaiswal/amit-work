package com.manditrades.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.manditrades.R;
import com.manditrades.cache.UserProfileCache;

public class SplashScreenActivity extends Activity {

	private Context context;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// MTUtil.getDeviceId(getApplicationContext());
		// MTUtil.getAndroidId(getApplicationContext());

		context = this;
		preferences = PreferenceManager.getDefaultSharedPreferences(context);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_splash_screen);
		Thread th = new Thread() {
			public void run() {
				try {
					sleep(1000);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					onPause();
					if (preferences.contains("MOBILE_NO")) {
						startActivity(new Intent(
								"com.manditrades.activities.HomeActivity"));
						UserProfileCache.getUserProfile().setProfile(null,
								preferences.getString("MOBILE_NO", ""));
					} else {
						Intent intent = new Intent(getApplicationContext(),
								SignInSignUpActivity.class);
						startActivity(intent);
					}
				}
			}
		};
		th.start();
	}

	@Override
	public void onPause() {
		super.onPause();
		finish();
	}

}
