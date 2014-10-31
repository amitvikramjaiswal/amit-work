package com.manditrades.util;

public class MTURLHelper {

	public static String STAGING_IP = "http://54.255.195.79";
	public static String DEVELOPMENT_IP = "http://54.179.136.5";
	public static String PRODUCTION_IP = "http://54.255.195.79";

	private static String API_ENDPOINT_STAGING = STAGING_IP + "/apimandi";
	private static String API_ENDPOINT_DEVELOPMENT = DEVELOPMENT_IP
			+ "/apimandi";
	private static String API_ENDPOINT_PRODUCTION = PRODUCTION_IP + "/apimandi";

	public static final String createEndpointURL = "/v1/index.php/createEndpoint";
	public static final String signInSignUpURL = "/v1/?";
	public static final String comodityURL = "/v4/?action=getCategory";
	public static final String sellersListURL = "/v1/index.php/sellers";
	public static final String searchItemURL = "/v1/index.php";
	public static final String searchAutoCompleteURL = "/v1/index.php";
	public static final String callURL = "/call";
	public static final String sellerInfoURL = "/v1/index.php/images";
	public static final String newPostURL = "/v1/index.php/posts";
	public static final String newsURL = "/v2/index.php/news_feed";
	public static final String submitGovtFeedbackURL = "/v2/index.php/schemes_feedback";
	public static final String myPostURL = "/v1/index.php/userposts";
	public static final String notificationURL = "/v1/index.php/notifications";
	public static final String logoutURL = "/v1/index.php/logout";
	public static final String rateUserURL = "/v2/index.php/rating";
	public static final String interestedUserURL = "/v2/index.php/caller_insert";
	public static final String deleteMyPostURL = "/v1/index.php/deletepost";
	public static final String getProfileURL = "/v1/index.php/prof";
	public static final String saveProfileURL = "/v1/index.php/profile";
	public static final String displayAlertsURL = "/v1/index.php/alerts_list";

	public static final String livePrices = "http://54.169.49.149/apimandi/v1/?";

	public static final String pushNotificationUrl = "http://192.168.1.22/newpush.php/createEndpoint";

	public static String getAPIEndpointURL(String requstedAPI) {
		return String.format("%s%s", API_ENDPOINT_DEVELOPMENT, requstedAPI);
	}

	public static String getAPIEndpointDevelopmentURL1(String requstedAPI) {
		return String.format("%s%s", API_ENDPOINT_DEVELOPMENT, requstedAPI);
	}

	public static String getAPIEndpointProductionURL(String requstedAPI) {
		return String.format("%s%s", API_ENDPOINT_PRODUCTION, requstedAPI);
	}

}
