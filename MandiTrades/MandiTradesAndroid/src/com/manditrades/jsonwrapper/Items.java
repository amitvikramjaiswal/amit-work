package com.manditrades.jsonwrapper;

import com.google.gson.annotations.SerializedName;

public class Items extends BaseWrapper {

	private static final long serialVersionUID = 1L;

	@SerializedName("state")
	private String state;
	@SerializedName("district")
	private String district;
	@SerializedName("market")
	private String market;
	@SerializedName("commodity")
	private String commodity;
	@SerializedName("variety")
	private String variety;
	@SerializedName("min_price")
	private String min_price;
	@SerializedName("max_price")
	private String max_price;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}

	public String getMin_price() {
		return min_price;
	}

	public void setMin_price(String min_price) {
		this.min_price = min_price;
	}

	public String getMax_price() {
		return max_price;
	}

	public void setMax_price(String max_price) {
		this.max_price = max_price;
	}

}
