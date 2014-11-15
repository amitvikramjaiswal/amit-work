package com.manditrades.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.manditrades.R;
import com.manditrades.util.MTUtil;

public class InterestedUserActivity extends Activity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.interested_user_list);

		context = this;

		ListView interestedUserLV = (ListView) findViewById(R.id.interested_user_lv);

		MTUtil.setActionBar(context, "Interested User", true);

		ArrayList<String> interestedUsers = getIntent().getExtras()
				.getStringArrayList("INTERESTED_USERS");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				interestedUsers);

		interestedUserLV.setAdapter(adapter);

	}

}
