package com.manditrades.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpUrlConnectionClient {

	public static JSONObject makeRequest(String url, String method,
			List<NameValuePair> params, JSONObject pJsonObject) {

		URL obj = null;
		HttpURLConnection request = null;
		int responseCode = 0;

		try {

			if (method.equals("GET")) {

				obj = new URL(url);
				request = (HttpURLConnection) obj.openConnection();

				responseCode = request.getResponseCode();

				System.out.println("\nSending 'GET' request to URL : " + url);
				System.out.println("Response Code : " + responseCode);

			} else if (method.equals("POST")) {

				try {
					List<NameValuePair> nameValuePairs = params;
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
							nameValuePairs);

					obj = new URL(url);
					request = (HttpURLConnection) obj.openConnection();

					request.setUseCaches(false);
					request.setDoOutput(true);
					request.setDoInput(true);

					request.setRequestMethod(method);
					OutputStream post = request.getOutputStream();
					entity.writeTo(post);
					post.flush();

					responseCode = request.getResponseCode();

					System.out.println("\nSending 'POST' request to URL : "
							+ url);
					System.out.println("Response Code : " + responseCode);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedReader in;
		String inputLine;
		// int maxBufferSize = 1024 ;
		StringBuffer response = null;
		String json = "";
		JSONObject jObj;

		try {
			in = new BufferedReader(new InputStreamReader(
					request.getInputStream()));

			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine + "\n");
			}

			in.close();

			// String temp = response.toString();

			if (response.toString().contains("root")) {
				jObj = new JSONObject(response.toString());
				return jObj;
			} else {
				json = "{root:" + response.toString() + ", responseCode : "
						+ responseCode + "}";
				jObj = new JSONObject(json);
				return jObj;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			try {
				jObj = new JSONObject("{\"res\":\"false\"}");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		json = "{root:" + response.toString() + ", responseCode : "
				+ responseCode + "}";

		return null;
	}
}
