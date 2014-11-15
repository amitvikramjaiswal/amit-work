package com.manditrades.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.manditrades.jsonwrapper.MTCommodity;
import com.manditrades.util.MTURLHelper;

public class CommoditiesCache {

	private static CommoditiesCache commoditiesCache;
	private static Map<String, ArrayList<String>> varieties;
	private ArrayList<MTCommodity> singleVarietyCommodities;
	private Map<String, String> commodityUrl;

	static {
		commoditiesCache = new CommoditiesCache();
		varieties = new HashMap<String, ArrayList<String>>();
	}

	public static CommoditiesCache getCommoditiesCache() {
		return commoditiesCache;
	}

	public ArrayList<MTCommodity> getSingleVarietyCommodities() {
		return singleVarietyCommodities;
	}

	public void setSingleVarietyCommodities(
			ArrayList<MTCommodity> mtCommodityList) {
		this.singleVarietyCommodities = new ArrayList<MTCommodity>();
		this.commodityUrl = new HashMap<String, String>();
		for (MTCommodity commodity : mtCommodityList) {
			commodityUrl.put(
					commodity.getCommodity() + "_" + commodity.getVariety(),
					String.format("%s/images/%s/%s/%s",
							MTURLHelper.PRODUCTION_IP, commodity.getCategory()
									.toLowerCase(), commodity.getCommodity()
									.toLowerCase().replace(" ", "_"), commodity
									.getImage()));
			if (!singleVarietyCommodities.contains(commodity))
				singleVarietyCommodities.add(commodity);
		}
	}

	public void setVarieties(ArrayList<MTCommodity> mtCommodities,
			String selectedCommodity) {
		ArrayList<String> arlVarieties = new ArrayList<String>();
		if (mtCommodities != null)
			for (MTCommodity mtCommodity1 : mtCommodities) {
				if (selectedCommodity.equalsIgnoreCase(mtCommodity1
						.getCommodity())) {
					arlVarieties.add(mtCommodity1.getVariety());
				}
				varieties.put(selectedCommodity, arlVarieties);
			}
	}

	public ArrayList<String> getVarietiesForCommodity(String commodity) {
		return varieties.get(commodity);
	}

	public String getCommodityUrl(String key) {
		return commodityUrl != null ? commodityUrl.get(key) : "";
	}

}
