package com.manditrades.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.manditrades.R;
import com.manditrades.util.MTUtil;

public class FeedbackSuggestionsActivity extends Activity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.feedback_suggession);

		context = this;

		initComponents();
		MTUtil.setActionBar(context, "Feedback and Suggestions", true);

	}

	private void initComponents() {

	}

}
