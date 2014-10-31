package com.manditrades.jsonwrapper;

import com.google.gson.annotations.SerializedName;

public class MTNews extends BaseWrapper {

	private static final long serialVersionUID = 1L;

	@SerializedName("heading")
	private String heading;
	@SerializedName("image")
	private String image;
	@SerializedName("content")
	private String content;
	
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

	

	

}
