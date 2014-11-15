package com.manditrades.jsonwrapper;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class MTSellerList extends BaseWrapper {

	private static final long serialVersionUID = 1L;

	@SerializedName("root")
	public ArrayList<MTSeller> mtSellerList;

	public ArrayList<MTSeller> getMTSellerList() {
		return mtSellerList;
	}

	public void setMTSellerList(ArrayList<MTSeller> mtSellerList) {
		this.mtSellerList = mtSellerList;
	}

}
