package com.manditrades.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.manditrades.R;
import com.manditrades.util.MTUtil;

public class FAQActivity extends Activity {

	private Context context;
	private TextView faqTV;
	private View rootView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.faq);

		context = this;
		MTUtil.setActionBar(context, "FAQs", true);

		faqTV = ((TextView) findViewById((R.id.faq_text)));
		faqTV.setText(Html.fromHtml(getString(R.string.faq_text)));

	}

}
