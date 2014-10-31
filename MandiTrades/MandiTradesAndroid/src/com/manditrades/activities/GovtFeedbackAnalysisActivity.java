package com.manditrades.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.manditrades.R;
import com.manditrades.util.MTUtil;


public class GovtFeedbackAnalysisActivity extends Activity{
	
	
	Context context;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.govt_analysis_of_feedback);
		context = this;
	
		MTUtil.setActionBar(context, "Govt. Analysis of Feedback", true);
		
		
		
	}
	

}
