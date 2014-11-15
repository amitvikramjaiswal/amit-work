package com.manditrades.jsonwrapper;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class AlertList extends BaseWrapper {

	private static final long serialVersionUID = 1L;

	@SerializedName("root")
	private ArrayList<Alert> alerts;

	public ArrayList<Alert> getAlerts() {
		return alerts;
	}

	public void setAlerts(ArrayList<Alert> alerts) {
		this.alerts = alerts;
	}

}
