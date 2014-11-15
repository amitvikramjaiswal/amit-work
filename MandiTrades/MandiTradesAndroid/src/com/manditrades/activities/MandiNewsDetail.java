package com.manditrades.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.manditrades.R;
import com.manditrades.jsonwrapper.MTNews;
import com.manditrades.util.MTURLHelper;
import com.manditrades.util.MTUtil;

public class MandiNewsDetail extends Activity {

	private Context context;
	private MTNews mtNews;
	private SmartImageView imageView;
	private TextView headingTV;
	private TextView newsTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mandi_news_detail);

		context = this;

		initComponents();

		setActionBar();

		mtNews = (MTNews) getIntent().getExtras().getSerializable("NEWS_DATA");

		imageView.setImageUrl(String.format("%s/apimandi/v1/%s",
				MTURLHelper.DEVELOPMENT_IP, mtNews.getImage()));
		headingTV.setText(mtNews.getHeading());
		newsTV.setText(mtNews.getContent());

	}

	private void setActionBar() {
		MTUtil.setActionBar(context, "Mandi Trades", true);
	}

	private void initComponents() {
		imageView = (SmartImageView) findViewById(R.id.image);
		headingTV = (TextView) findViewById(R.id.news_title);
		newsTV = (TextView) findViewById(R.id.new_detail);
	}

}
