package com.manditrades.util;

public class CurrentLocation {

	private static double currentLatitude;
	private static double currentLongitude;

	public static double getCurrentLatitude() {
		return currentLatitude;
	}

	public static void setCurrentLatitude(double currentLatitude) {
		CurrentLocation.currentLatitude = currentLatitude;
	}

	public static double getCurrentLongitude() {
		return currentLongitude;
	}

	public static void setCurrentLongitude(double currentLongitude) {
		CurrentLocation.currentLongitude = currentLongitude;
	}

}
