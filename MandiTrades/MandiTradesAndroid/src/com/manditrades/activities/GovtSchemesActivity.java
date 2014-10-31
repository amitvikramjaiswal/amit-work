package com.manditrades.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.manditrades.R;
import com.manditrades.adapters.SimpleListAdapter;
import com.manditrades.util.MTUtil;

public class GovtSchemesActivity extends Activity {

	private ListView govSchemesLV;
	private Context context;
	private String schemes[];
	private String schemeTitles[];
	private Button feedbackAnalysisButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.govt_schemes);
		context = this;
		govSchemesLV = (ListView) findViewById(R.id.scheme_list);
		feedbackAnalysisButton = (Button) findViewById(R.id.feedback_analysis_button);
		setActionBar();
		setListeners();
		initSchemes();

		govSchemesLV.setAdapter(new SimpleListAdapter(context, schemeTitles));

	}

	private void setActionBar() {
		MTUtil.setActionBar(context, "Government Schemes", true);
	}

	private void initSchemes() {
		schemes = getResources().getStringArray(R.array.schemes_array);
		schemeTitles = getResources().getStringArray(R.array.schemes_title_array);

	}

	private void setListeners() {
		govSchemesLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long l) {

				Intent intent = new Intent(context, GovtFeedbackActivity.class);
				intent.putExtra("SCHEME_INDEX", position);
				startActivity(intent);

			}
		});

		feedbackAnalysisButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						GovtFeedbackAnalysisActivity.class);
				startActivity(intent);
			}
		});
	}

}
