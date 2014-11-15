package com.manditrades.jsonwrapper;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class MTNewsList extends BaseWrapper {

	private static final long serialVersionUID = 1L;

	@SerializedName("root")
	public ArrayList<MTNews> mtNewsList;

	public ArrayList<MTNews> getMtNewsList() {
		return mtNewsList;
	}

	public void setMtNewsList(ArrayList<MTNews> mtNewsList) {
		this.mtNewsList = mtNewsList;
	}



	
}
