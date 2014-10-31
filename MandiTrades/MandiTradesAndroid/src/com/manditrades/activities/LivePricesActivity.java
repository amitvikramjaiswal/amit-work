package com.manditrades.activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.manditrades.R;
import com.manditrades.jsonwrapper.Items;
import com.manditrades.util.JsonDataCallback;
import com.manditrades.util.MTURLHelper;
import com.manditrades.util.MTUtil;

public class LivePricesActivity extends Activity {

	private Button changeStateBtn;
	private Spinner stateSpinner;
	private Spinner marketSpinner;
	private Spinner commoditySpinner;
	private Spinner optionSpinner;
	private TextView dateTV;

	private List<String> states;
	private TreeSet<String> statesTree = new TreeSet<String>();
	private String aDate;
	private List<String> markets;
	private List<String> commodities;
	private TreeSet<String> marketsTree = new TreeSet<String>();
	private TreeSet<String> commoditiesTree = new TreeSet<String>();
	private ArrayList<Items> arlItems;

	private TableLayout tl;
	private String language;

	private TextView tvVarCol;
	private TextView tvVariety;
	private TextView tvMinPrice;
	private TextView tvMaxPrice;

	private Context context;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mandi_prices);

		context = this;

		MTUtil.setActionBar(context, "Mandi Prices", true);

		initComponents();
		setValues();
		fetchLivePrices();
		setListeners();

	}

	private void setValues() {
		states.add("Andhra Pradesh");
		states.add("Assam");
		states.add("Bihar");
		states.add("Chandigarh");
		states.add("Chattisgarh");
		states.add("Goa");
		states.add("Gujarat");
		states.add("Haryana");
		states.add("Himachal Pradesh");
		states.add("Jammu and Kashmir");
		states.add("Jharkhand");
		states.add("Karnataka");
		states.add("Kerala");
		states.add("Madhya Pradesh");
		states.add("Maharashtra");
		states.add("Manipur");
		states.add("Meghalaya");
		states.add("Mizoram");
		states.add("NCT of Delhi");
		states.add("Nagaland");
		states.add("Orissa");
		states.add("Pondicherry");
		states.add("Punjab");
		states.add("Rajasthan");
		states.add("Tamil Nadu");
		states.add("Telangana");
		states.add("Tripura");
		states.add("Uttar Pradesh");
		states.add("Uttrakhand");
		states.add("West Bengal");
	}

	private void setListeners() {
		changeStateBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				stateSpinner.performClick();
			}
		});
	}

	private void initComponents() {
		states = new ArrayList<String>();

		changeStateBtn = (Button) findViewById(R.id.changeStateBtn);
		stateSpinner = (Spinner) findViewById(R.id.statePicker);
		marketSpinner = (Spinner) findViewById(R.id.valuePicker);
		commoditySpinner = (Spinner) findViewById(R.id.valuePicker);
		optionSpinner = (Spinner) findViewById(R.id.optionPicker);
		tl = (TableLayout) findViewById(R.id.maintable);
		dateTV = (TextView) findViewById(R.id.InfoLbl);
	}

	private void fetchLivePrices() {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();

		String date = (dateFormat.format(calendar.getTime())).toString();

		String state = stateSpinner.getSelectedItem() != null ? (String) stateSpinner
				.getSelectedItem() : states.get(0);

		dateTV.setText(date);

		aDate = date;

		String url = MTURLHelper.livePrices + "state="
				+ state.replace(" ", "%20") + "&date=" + date;
		String method = "GET";

		Object[] objects = { url, method, null };

		JsonDataCallback callback = new JsonDataCallback(context) {

			@Override
			public void receiveData(JSONObject responseJson) {
				try {
					arlItems = new ArrayList<Items>();
					JSONArray array = responseJson.getJSONArray("root");
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = (JSONObject) array.get(i);
						Items item = new Gson().fromJson(object.toString(),
								Items.class);
						arlItems.add(item);
					}
					populateState();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		};

		callback.execute(objects);

	}

	private void populateOption() {

		String options[] = getResources().getStringArray(R.array.option_array);

		optionSpinner = (Spinner) findViewById(R.id.optionPicker);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, options);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		optionSpinner.setAdapter(dataAdapter);
		optionSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

				((TextView) parent.getChildAt(0)).setPadding(20, 0, 35, 5);
				String selecetedOption = parent.getItemAtPosition(pos)
						.toString();

				if (selecetedOption.equals("Market")) {

					populateMarket();
				} else {

					populateCommodity();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	// to populate state
	private void populateState() {

		stateSpinner.setEnabled(false);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, states);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		stateSpinner.setAdapter(dataAdapter);

		stateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long l) {
				((TextView) adapterView.getChildAt(0)).setPadding(12, 0, 20, 5);

				String selecetedState = adapterView.getItemAtPosition(position)
						.toString();

				Iterator<Items> iterator = arlItems.iterator();
				// saving state
				// SharedPreferences.Editor editor = prefs.edit();
				// editor.putString("STATE", selecetedState);
				// editor.commit();

				// putting data in marketTree and commodityTree
				marketsTree.clear();
				commoditiesTree.clear();
				while (iterator.hasNext()) {
					Items items = iterator.next();
					if (items.getState().equals(selecetedState)) {
						marketsTree.add(items.getMarket());
						commoditiesTree.add(items.getCommodity());
					}
				}

				populateOption();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}

	// to populate market spinner
	private void populateMarket() {
		markets = new ArrayList<String>();

		if (marketsTree.size() != 0) {
			for (String s : marketsTree) {

				markets.add(s);
			}

		}

		else {
			// handleError();

		}

		marketSpinner = (Spinner) findViewById(R.id.valuePicker);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, markets);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		marketSpinner.setAdapter(dataAdapter);

		marketSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long l) {
				((TextView) adapterView.getChildAt(0))
						.setTextColor(Color.WHITE);
				((TextView) adapterView.getChildAt(0)).setPadding(20, 0, 35, 5);

				String market = adapterView.getItemAtPosition(position)
						.toString();

				marketBasedTable(market);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
	}

	// to populate commodity spinner
	private void populateCommodity() {
		commodities = new ArrayList<String>();

		if (commoditiesTree.size() != 0) {

			for (String s : commoditiesTree) {

				commodities.add(s);
			}
		} else {
			// handleError();

		}

		commoditySpinner = (Spinner) findViewById(R.id.valuePicker);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, commodities);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		commoditySpinner.setAdapter(dataAdapter);

		commoditySpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int position, long l) {

						((TextView) adapterView.getChildAt(0))
								.setTextColor(Color.WHITE);
						((TextView) adapterView.getChildAt(0)).setPadding(20,
								0, 35, 5);

						String commodity = adapterView.getItemAtPosition(
								position).toString();
						commodityBasedTable(commodity);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

	}

	// To populate data in commodityBasedTable
	void commodityBasedTable(String commodity) {
		final List<Items> list = new ArrayList<Items>();
		tl.removeAllViews();

		for (Items i : arlItems) {
			if (i.getCommodity().equals(commodity))
				list.add(i);
		}

		for (final Items item : list) {

			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View view = inflater.inflate(R.layout.table_row, tl, false);

			tvVarCol = (TextView) view.findViewById(R.id.varCol);
			tvVariety = (TextView) view.findViewById(R.id.varietyCol);
			tvMinPrice = (TextView) view.findViewById(R.id.minCol);
			tvMaxPrice = (TextView) view.findViewById(R.id.maxCol);

			tvVarCol.setText(item.getMarket());
			tvVariety.setText(item.getVariety());
			tvMinPrice.setText(item.getMin_price());
			tvMaxPrice.setText(item.getMax_price());

			tl.addView(view);
			info();

		}

	}

	// To populate data in marketBasedTable
	void marketBasedTable(String market) {
		final List<Items> list = new ArrayList<Items>();
		tl.removeAllViews();

		for (Items i : arlItems) {
			if (i.getMarket().equals(market))
				list.add(i);
		}

		for (final Items item : list) {

			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			final View view = inflater.inflate(R.layout.table_row, tl, false);

			tvVarCol = (TextView) view.findViewById(R.id.varCol);
			tvVariety = (TextView) view.findViewById(R.id.varietyCol);
			tvMinPrice = (TextView) view.findViewById(R.id.minCol);
			tvMaxPrice = (TextView) view.findViewById(R.id.maxCol);

			tvVarCol.setText(item.getCommodity());
			tvVariety.setText(item.getVariety());
			tvMinPrice.setText(item.getMin_price());
			tvMaxPrice.setText(item.getMax_price());

			tl.addView(view);
			info();

		}

	}

	private void info() {
		dateTV.setText(aDate);
	}

}