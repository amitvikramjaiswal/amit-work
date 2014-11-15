package com.manditrades.pushreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.manditrades.activities.SplashScreenActivity;

public class ExternalReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		if (intent != null) {
			Bundle extras = intent.getExtras();

			StringBuilder payload = new StringBuilder();
			for (String key : extras.keySet()) {
				payload.append(String.format("%s=%s", key,
						extras.getString(key)) + '\n');
			}

			System.out.println("$$$$$$$$$$$$ ------- " + payload.toString());

			if (!SplashScreenActivity.inBackground) {
				MessageReceivingService.sendToApp(extras, context);
			} else {
				MessageReceivingService.saveToLog(extras, context);
			}
		}
	}
}