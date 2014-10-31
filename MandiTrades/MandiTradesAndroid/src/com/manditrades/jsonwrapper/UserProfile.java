package com.manditrades.jsonwrapper;

import com.google.gson.annotations.SerializedName;

public class UserProfile extends BaseWrapper {

	private static final long serialVersionUID = 1L;

	@SerializedName("name")
	private String username;
	@SerializedName("mobile_no")
	private String mobile;
	@SerializedName("photo")
	private String imageUrl;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
