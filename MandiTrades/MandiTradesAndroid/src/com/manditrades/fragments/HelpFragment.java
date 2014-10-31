package com.manditrades.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.manditrades.R;
import com.manditrades.activities.FAQActivity;
import com.manditrades.activities.FeedbackSuggestionsActivity;
import com.manditrades.adapters.SimpleListAdapter;

public class HelpFragment extends Fragment {
	private Context context;
	private ListView helpMenu;
	private Button callUsButton;
	private Button emailUsButton;
	private View rootView;
	Intent intent;

	public HelpFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.help_faq, container, false);
		context = container.getContext();
		initComponents();

		callUsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:918040945125"));
				startActivity(callIntent);
			}
		});

		emailUsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String[] to = { "manditrades@appface.in" };
				String[] cc = { "" };
				sendEmail(to, cc, "Mandi Trades Feedback/Help", "");

			}

		});

		String[] menuItemArray = { "FAQs", "Feedback & Suggestions" };
		helpMenu.setAdapter(new SimpleListAdapter(context, menuItemArray));
		helpMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
				

				switch (arg2) {
				
				
		

				case 0:
					intent = new Intent(context, FAQActivity.class);
					startActivity(intent);
					 
					break;
				case 1:
					intent = new Intent(context, FeedbackSuggestionsActivity.class);
					startActivity(intent);
					break;
					
				

				}
				
				
				

			}
		});

		return rootView;
	}

	private void initComponents() {
		emailUsButton = (Button) rootView.findViewById(R.id.email_us_button);
		callUsButton = (Button) rootView.findViewById(R.id.call_us_button);
		helpMenu = (ListView) rootView.findViewById(R.id.help_menu);
	}

	public void sendEmail(String[] to, String[] cc, String subject,
			String message) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setData(Uri.parse("mailto:"));
		emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
		emailIntent.putExtra(Intent.EXTRA_CC, cc);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(Intent.EXTRA_TEXT, message);
		emailIntent.setType("message/rfc822");
		startActivity(Intent.createChooser(emailIntent, "Email"));
	}

}
