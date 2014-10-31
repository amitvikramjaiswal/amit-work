package com.manditrades.jsonwrapper;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class MTCommodityList extends BaseWrapper {

	private static final long serialVersionUID = 1L;

	@SerializedName("root")
	public ArrayList<MTCommodity> mtCommodityList;

	private ArrayList<MTCommodity> singleVarietyCommodities;

	public ArrayList<MTCommodity> getMtCommodityList() {
		return mtCommodityList;
	}

	public void setMtCommodityList(ArrayList<MTCommodity> mtCommodityList) {
		this.mtCommodityList = mtCommodityList;
	}

	public void setSingleVarietyCommodities(
			ArrayList<MTCommodity> mtCommodityList) {
		singleVarietyCommodities = new ArrayList<MTCommodity>();
		for (MTCommodity commodity : mtCommodityList)
			if (!singleVarietyCommodities.contains(commodity))
				singleVarietyCommodities.add(commodity);
	}

	public ArrayList<MTCommodity> getSingleVarietyCommodities() {
		return singleVarietyCommodities;
	}

}
