package com.manditrades.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.manditrades.R;
import com.manditrades.adapters.MandiNewsAdapter;
import com.manditrades.jsonwrapper.MTNews;
import com.manditrades.jsonwrapper.MTNewsList;
import com.manditrades.util.JsonDataCallback;
import com.manditrades.util.MTURLHelper;
import com.manditrades.util.MTUtil;

public class MandiNewsActivity extends Activity {

	private ListView newsLV;

	private Context context;
	private MandiNewsAdapter mandiNewsAdapter;
	private List<MTNews> mtNewsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mandi_news);
		context = this;
		newsLV = (ListView) findViewById(R.id.news_list);
		setListeners();

		setActionBar();

		String url = MTURLHelper
				.getAPIEndpointURL(MTURLHelper.newsURL);
		String method = "POST";

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("from", "1"));
		params.add(new BasicNameValuePair("to", "100"));

		Object[] object = { url, method, params, null };

		JsonDataCallback callback = new JsonDataCallback(context) {

			@Override
			public void receiveData(JSONObject responseJson) {

				Gson gson = new Gson();

				MTNewsList mtNewsListObj = gson.fromJson(
						responseJson.toString(), MTNewsList.class);

				mtNewsList = mtNewsListObj.getMtNewsList();

				mandiNewsAdapter = new MandiNewsAdapter(context, mtNewsList);
				newsLV.setAdapter(mandiNewsAdapter);

			}
		};
		callback.execute(object);
	}

	private void setActionBar() {
		MTUtil.setActionBar(context, "Mandi News", true);
	}

	private void setListeners() {
		newsLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long l) {

				Intent intent = new Intent(context, MandiNewsDetail.class);
				intent.putExtra("NEWS_DATA", mtNewsList.get(position));
				startActivity(intent);

			}
		});
	}

}
