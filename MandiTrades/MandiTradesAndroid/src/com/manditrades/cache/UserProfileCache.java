package com.manditrades.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.manditrades.jsonwrapper.UserProfile;
import com.manditrades.util.HttpUrlConnectionClient;
import com.manditrades.util.MTURLHelper;

public class UserProfileCache {

	private static UserProfileCache userProfile;
	private UserProfile profile;

	static {
		userProfile = new UserProfileCache();
	}

	private UserProfileCache() {

	}

	public static UserProfileCache getUserProfile() {
		return userProfile;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile, String mobile) {
		if (profile != null) {
			this.profile = profile;
			return;
		} else {
			fetchProfile(mobile);
		}
	}

	public void fetchProfile(final String mobile) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				String url = MTURLHelper
						.getAPIEndpointURL(MTURLHelper.getProfileURL);
				String method = "POST";
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("mobile_no", mobile));

				try {

					JSONObject jsonObject = HttpUrlConnectionClient
							.makeRequest(url, method, params, null);
					if (jsonObject != null
							&& !jsonObject.toString().contains("status")) {
						UserProfile userProfile = new Gson().fromJson(
								jsonObject.getJSONObject("root").toString(),
								UserProfile.class);
						profile = userProfile;
					} else {

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}
	
	public void clearCache() {
		profile = null;
	}
	
}
