package com.manditrades.jsonwrapper;

import com.google.gson.annotations.SerializedName;

public class MTCommodity extends BaseWrapper {

	private static final long serialVersionUID = 1L;

	@SerializedName("category")
	private String category;

	@SerializedName("variety")
	private String variety;

	@SerializedName("commodity")
	private String commodity;

	@SerializedName("image")
	private String image;

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object object) {

		MTCommodity commodity1 = (MTCommodity) object;
		if (this.commodity.equalsIgnoreCase(commodity1.commodity))
			return true;
		else
			return false;

	}
	
}
