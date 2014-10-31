package com.manditrades.jsonwrapper;

import com.google.gson.annotations.SerializedName;

public class MTId extends BaseWrapper {

	private static final long serialVersionUID = 1L;

	@SerializedName("$id")
	private String Id;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
}
