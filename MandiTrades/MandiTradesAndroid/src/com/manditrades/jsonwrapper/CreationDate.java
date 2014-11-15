package com.manditrades.jsonwrapper;

import com.google.gson.annotations.SerializedName;

public class CreationDate extends BaseWrapper {

	private static final long serialVersionUID = 1L;

	@SerializedName("date")
	private String date;

	@SerializedName("timezone")
	private String timezone;

	@SerializedName("timezone_type")
	private int timezoneType;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public int getTimezoneType() {
		return timezoneType;
	}

	public void setTimezoneType(int timezoneType) {
		this.timezoneType = timezoneType;
	}

}
