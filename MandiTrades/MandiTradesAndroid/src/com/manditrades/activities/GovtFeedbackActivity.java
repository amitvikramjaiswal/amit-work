package com.manditrades.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.manditrades.R;
import com.manditrades.util.JsonDataCallback;
import com.manditrades.util.MTAlertUtil;
import com.manditrades.util.MTURLHelper;
import com.manditrades.util.MTUtil;

public class GovtFeedbackActivity extends Activity {
	private Context context;
	private int schemeId;
	private TextView schemeTitleTV;
	private TextView schemeTV;
	private Button submitBtn;
	private SharedPreferences preferences;
	private EditText feedbackMessaegED;
	private RatingBar ratingBar;
	private String schemes[];
	private String schemeTitles[];
	private int currentSchemeIndex;
	private Button exitButton;
	private Button nextButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.govt_feedback);
		context = this;

		initComponents();

		setActionBar();
		initSchemes();

		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String comment = feedbackMessaegED.getText().toString();

				comment = comment != null && !comment.equals("") ? comment
						: " ";

				String url = MTURLHelper
						.getAPIEndpointURL(MTURLHelper.submitGovtFeedbackURL);
				String method = "POST";

				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("feedback_id", schemeId + ""));
				params.add(new BasicNameValuePair("feedback_content", comment));
				params.add(new BasicNameValuePair("feedback_rating", ratingBar
						.getRating() + ""));
				params.add(new BasicNameValuePair("mobile_no", preferences
						.getString("MOBILE_NO", null)));

				Object[] object = { url, method, params, null };

				JsonDataCallback callback = new JsonDataCallback(context) {

					@Override
					public void receiveData(JSONObject responseJson) {
						try {
							JSONObject root = responseJson
									.getJSONObject("root");
							String status = root.getString("status");

							if (status
									.equalsIgnoreCase("Feedback Posted Successfully")) {
								MTAlertUtil.showMessesBox(context,
										"Mandi Trades",
										"Thank you for your feedback.",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												finish();
											}
										});
							} else {
								MTAlertUtil.showMessesBox(context,
										"Mandi Trades",
										"Unable to send feedback.",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												finish();
											}
										});
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				};
				callback.execute(object);

			}
		});

		schemeTitleTV.setText(schemeTitles[currentSchemeIndex]);
		schemeTV.setText(schemes[currentSchemeIndex]);

	}

	private void setActionBar() {
		final Activity activity = (Activity) context;
		activity.getActionBar()
				.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		activity.getActionBar().setCustomView(
				R.layout.layout_action_bar_govt_feedback);

		View view = LayoutInflater.from(activity).inflate(
				R.layout.layout_action_bar_govt_feedback, null);

		TextView customView = (TextView) view
				.findViewById(R.id.action_bar_title);

		customView.setText("Government Feedback");

		exitButton = (Button) view.findViewById(R.id.exitBtn);
		nextButton = (Button) view.findViewById(R.id.nextBtn);

		exitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				activity.finish();

			}
		});

		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentSchemeIndex++;

				if (currentSchemeIndex == schemes.length) {

					currentSchemeIndex = 0;
				}

				schemeId = currentSchemeIndex + 1;
				schemeTitleTV.setText(schemeTitles[currentSchemeIndex]);
				schemeTV.setText(schemes[currentSchemeIndex]);

			}
		});

		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);

		activity.getActionBar().setCustomView(view, params);

	}

	private void initComponents() {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);

		currentSchemeIndex = getIntent().getExtras().getInt("SCHEME_INDEX");

		schemeId = currentSchemeIndex + 1;

		schemeTitleTV = (TextView) findViewById(R.id.title);
		schemeTV = (TextView) findViewById(R.id.description);
		submitBtn = (Button) findViewById(R.id.submit);
		feedbackMessaegED = (EditText) findViewById(R.id.feedback_message);
		ratingBar = (RatingBar) findViewById(R.id.rating_bar);
	}

	private void initSchemes() {
		schemes = getResources().getStringArray(R.array.schemes_array);
		schemeTitles = getResources().getStringArray(
				R.array.schemes_title_array);

	}

}
