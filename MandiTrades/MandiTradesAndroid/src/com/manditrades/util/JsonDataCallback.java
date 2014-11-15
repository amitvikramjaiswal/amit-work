package com.manditrades.util;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.manditrades.R;

public abstract class JsonDataCallback extends
		AsyncTask<Object, String, JSONObject> implements CallbackReciever {

	private ProgressDialog mProgressDialog;
	Handler handler;
	Runnable callback;
	Context context;

	public JsonDataCallback(Context context) {

		this.context = context;
		mProgressDialog = new ProgressDialog(context,
				R.style.CustomProgressDialog);
		mProgressDialog.setMessage("");
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setCancelable(true);
	}

	public abstract void receiveData(JSONObject responseJson);

	@Override
	protected void onPreExecute() {
//		mProgressDialog.show();
		super.onPreExecute();
	}

	private String url;
	private String method;
	private List<NameValuePair> nameValuePair;
	private JSONObject jsonObject = null;

	@SuppressWarnings("unchecked")
	@Override
	protected JSONObject doInBackground(Object... params) {

		try {

			url = (String) params[0];
			method = (String) params[1];
			nameValuePair = (List<NameValuePair>) params[2];

			if (params.length == 4) {

				jsonObject = (JSONObject) params[3];
			}

			if (method.equals("DELETE")) {
				// results = HttpUrlConnectionClient.makeDeleteRequest(url);
			} else {

				jsonObject = HttpUrlConnectionClient.makeRequest(url, method,
						nameValuePair, jsonObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	protected void onPostExecute(JSONObject responseJson) {
		if (mProgressDialog != null || mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
		if (responseJson != null) {
			receiveData(responseJson);
		}
	}
}