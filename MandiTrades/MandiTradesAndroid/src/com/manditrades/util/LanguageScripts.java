package com.manditrades.util;

public class LanguageScripts {
	// menu page
	public String[] language, newSale, priceAlert, sellerList, myList,
			livePrices, settings, logOut;

	// live prices page
	public String[] market, commodity, changeState, variety, minPrice,
			maxPrice;

	// createAlert page

	public String[] quantity, kilogram, quintal, expectedPrice,
			priceatnearestMandi, createAlert;

	public String[] date;

	// post page

	public String[] location, currentLocation, other, post, pleaseWait;

	public String[] fruits, vegetables, cereals, pulses, spices;

	public String[] deleteAccount;

	public String[] subscription;

	public String[] termsnconditions;

	public String[] buy;

	public String[] sell;

	public String[] mandinews;

	public String[] mandiprices;

	public String[] mandihelp;

	public String[] governmentfeedback;

	public String[] selectCommodity;

	public String[] mandiTrades;

	public String[] sellersInformation;

	public String[] viewAlerts;

	public String[] ratetheapp;

	public String[] sharetheapp;

	// select commodity page
	// public String[]

	public LanguageScripts() {

		// Menu Page
		// region MainPageLanguage
		if (language == null) {
			language = (new String[] { "English", "हिन्दी ", "తెలుగు ",
					"ಕನ್ನಡ ", "தமிழ் ", "മലയാളം " });
		}
		if (newSale == null) {
			newSale = (new String[] { "New Sale", "नई बिक्री",
					"కొత్త అమ్మకానికి", "ಹೊಸ ಮಾರಾಟ", "புதிய விற்பனை",
					"പുതിയ വില്പന" });
		}
		if (priceAlert == null) {
			priceAlert = (new String[] { "Price Alert", "मूल्य अधिसूचना",
					"ధర హెచ్చరిక", "ಬೆಲೆ ಎಚ್ಚರಿಕೆ", "விலை எச்சரிக்கை",
					"വില അറിയിപ്പ്" });
		}
		if (sellerList == null) {
			sellerList = (new String[] { "Seller List", "विक्रेता सूची",
					"విక్రేత జాబితా", "ಮಾರಾಟಗಾರರ ಪಟ್ಟಿ",
					"விற்பனையாளர் பட்டியல்", "വ്യാപാരി പട്ടിക" });
		}
		if (myList == null) {
			myList = (new String[] { "My Wishlist", "मेरी इच्छा सूची",
					"నా కోరిక జాబితా", "ನನ್ನ ವಿಷ್ ಲಿಸ್ಟ್",
					"என் ஆசை பட்டியலில்", "എന്റെ  ആഗ്രഹിക്കുന്നവ" });
		}
		if (livePrices == null) {
			livePrices = (new String[] { "Live Prices", "ताज़ा मूल्य",
					"ప్రత్యక్ష ధర", "ನೇರ ಬೆಲೆ", "தற்போதிய விலை", "തത്സമയ വില" });
		}
		if (settings == null) {
			settings = (new String[] { "Settings", "सेटिंग्स", "సెట్టింగులు",
					"ಸೆಟ್ಟಿಂಗ್ಗಳನ್ನು", "செட்டிங்", "ക്രമീകരണങ്ങൾ" });
		}
		if (logOut == null) {
			logOut = (new String[] { "Logout", "लॉगआउट", "లాగ్అవుట్",
					"ಲಾಗ್ಔಟ್", "வெளியேற்றம்", "ലോഗൌട്ട്" });
		}
		if (deleteAccount == null) {
			deleteAccount = (new String[] { "Delete Account", "डिलीट अकाउंट",
					"డిలీట్ ఎకౌంటు", "ಖಾತೆಯನ್ನು ಅಳಿಸಲು", "அடையலாம் அழிப்பு",
					"ഡിലീറ്റ് അക്കൗണ്ട്‌" });
		}
		if (subscription == null) {
			subscription = (new String[] { "Subscription", "सब्सक्रिप्शन ",
					"సబ్స్క్రిప్షన్ ", "ಸುಬ್ಸ್ಚ್ರಿಪ್ತಿಒನ್ ",
					"சுப்ச்ச்ரிப்தியன் ", "സുസ്ബ്ച്രിപ്റ്റിഒൻ " });
		}
		if (termsnconditions == null) {
			termsnconditions = (new String[] { "Terms & Conditons",
					"टर्म्स एंड कंडीशंस", "టర్మ్స్ అండ్ కొన్దితిఒన్స్",
					" ಟರ್ಮ್ಸ್ ಅಂಡ್ ಚೊನ್ದಿತಿಒನ್ಸ್ ",
					" டெர்ம்ஸ் அண்ட் cஒண்டிடிஒன்ச் ",
					" റെരംസ് ആൻഡ്‌ കോണ്ടിറേന്സ് " });
		}

		// endregion

		// market Page
		// region marketPage Language
		if (market == null) {
			market = (new String[] { "Market", "बाजार", "మార్కెట్",
					"ಮಾರುಕಟ್ಟೆ", "சந்தை", "അങ്ങാടി" });
		}
		if (commodity == null) {
			commodity = (new String[] { "Commodity", "सामग्री", "వస్తువు",
					"ಸರಕು", "பொருள்", "ഉല്പന്നം" });
		}

		if (changeState == null) {
			changeState = (new String[] { "Change State", "राज्य बदले",
					"రాష్ట్ర మార్చు", "ರಾಜ್ಯ ಬದಲಾವಣೆ", "மாநிலம் மாற்றம்",
					"സംസ്ഥാനം മാറ്റുക" });
		}

		if (variety == null) {
			variety = (new String[] { "Variety", "प्रकार", "వివిధ", "ವಿವಿಧ",
					"ரகம்", "തരം" });
		}
		if (minPrice == null) {
			minPrice = (new String[] { "Min Price", "न्यूनतम  मूल्य",
					"కనీస ధర	", "ಕನಿಷ್ಠ ದರ", "குறைந்தபட்ச விலை", "കുറഞ്ഞ വില", });
		}
		if (maxPrice == null) {
			maxPrice = (new String[] { "Max Price", "अधिक्तम मूल्य",
					"గరిష్ట ధర", "ಗರಿಷ್ಠ ದರ", "அதிகபட்ச விலை", "പരമാവധി  വില" });
		}
		// endregion

		// create alert page

		// region alertPage Language
		if (quantity == null) {
			quantity = (new String[] { "Quantity", "मात्रा", "పరిమాణం",
					"ಪ್ರಮಾಣ", "அளவு", "അളവ്" });
		}
		if (kilogram == null) {
			kilogram = (new String[] { "Kilogram", "किलोग्राम", "కిలోగ్రామ్",
					"ಕಿಲೋಗ್ರ್ಯಾಮ್", "கிலோக்ராம்", "കിലോഗ്രാം" });
		}
		if (quintal == null) {
			quintal = (new String[] { "Quintal", "क्विंटल", "క్వింటాల్",
					"ಕ಼ುಇನ್ತಲ್", "குஇண்டல்", "ഖുഇന്റൽ" });
		}
		if (expectedPrice == null) {
			expectedPrice = (new String[] { "Expected Price", "उम्मीद की कीमत",
					"అంచనా ధర", "ನಿರೀಕ್ಷಿತ ಬೆಲೆ",
					"எதிர்பார்க்கப்படுகிறது விலை", "പ്രതീക്ഷിക്കുന്ന വില" });
		}

		if (createAlert == null) {
			createAlert = new String[] { "Create Alert", "अधिसूचना बनाना",
					"హెచ్చరికను సృష్టించడానికి", "ಎಚ್ಚರಿಕೆಯನ್ನು ರಚಿಸಲು",
					"எச்சரிக்கை உருவாக்கம்", "അറിയിപ്പ് ഉണ്ടാക്കുക" };
		}

		if (viewAlerts == null) {
			viewAlerts = new String[] { "View Alerts", "अधिसूचना देखें ",
					"హెచ్చరికలు  చుడండి ", "ಎಚ್ಚರಿಕೆಯನ್ನು  ನೋಡಿ",
					"பார்வை அலர்ட்", " ഉണ്ടാക്കുക കാണുക" };
		}

		// endregion

		// post page
		location = new String[] { "Location", "स्थान", "స్థలం" };
		currentLocation = new String[] { "current location", "वर्तमान स्थान",
				"ప్రస్తుత నగర", "ಪ್ರಚಲಿತ ಸ್ಥಳ", "நடப்பு இடம்",
				"ഇക്കാലത്തുളള സ്ഥാനം" };
		other = new String[] { "other", "अन्य स्थान", "ఇతర నగర", "ಇತರ ಸ್ಥಳ",
				"மற்ற இடம்", "മറ്റുള്ളവ സ്ഥാനം" };
		// enterPinCode = new String[] { "Pincode", "पिन कोड", "పిన్ కోడ్" };
		post = new String[] { "Post", "पोस्ट	", "పోస్ట్", "ಪೋಸ್ಟ್", "போஸ்ட்",
				"പതിക്കുക", "પોસ્ટ", "ପୋସ୍ଟ୍", "ਪੋਸਟ" };
		// pleaseWait = new String[] { "Please Wait", "कृपया प्रतीक्षा करें",
		// "దయచేసి వేచి ఉండండి" };

		fruits = new String[] { "Fruits", "फल", "పండ్లు", "ಹಣ್ಣುಗಳು",
				"பழங்கள்", "പഴങ്ങൾ" };

		vegetables = new String[] { "Vegetables", "सब्ज़ियां", "కూరగాయలు",
				"ತರಕಾರಿಗಳು", "காய்கறிகள்", "പച്ചക്കറികൾ" };

		cereals = new String[] { "Cereals", "अनाज", "ధాన్యాలు", "ಧಾನ್ಯಗಳು",
				"தானியங்கள்", "ധാന്യങ്ങൾ" };

		pulses = new String[] { "Pulses", "दाल", "పప్పులు", "ದ್ವಿದಳ",
				"பருப்பு வகைகள்", "പയർവർഗങ്ങൾ" };

		spices = new String[] { "Spices", "मसाले", "సుగంధ ద్రవ్యాలు",
				"ಮಸಾಲೆಗಳು", "மசாலா", "സുഗന്ധവ്യഞ്ചനങ്ങൾ" };

		buy = new String[] { "Buy", "ख़रीदे", "కొనుగోలు", "ಖರೀದಿ", "வாங்க",
				"വാങ്ങുക" };
		sell = new String[] { "Sell", "बेचे", "అమ్మకం ", "ಮಾರಾಟ", "விற்க",
				"വില്ക്കുക" };
		mandinews = new String[] { "Mandi News", "मंडी समाचार",
				"మంది వార్తలు ", "ಮಂದಿ ಸುದ್ದಿ", "மண்டி செய்தி",
				"മണ്ടി വാര്ത്ത്" };
		mandiprices = new String[] { "Mandi Prices", "मंडी मूल्य", "మంది ధర ",
				"ಮಂದಿ ಬೆಲೆ", "மண்டி விலை", "മണ്ടി വില" };
		mandihelp = new String[] { "Help", "हेल्प ", "హెల్ప్  ", "ಹೆಲ್ಪ್ ",
				"ஹெல்ப் ", "ഹെല്പ് " };
		governmentfeedback = new String[] { "Govt Feedback",
				"गवर्नमेंट फीडबेक ", "గవర్నమెంట్ ఫీడ్బచ్క్   ",
				"ಗೊವೆರ್ನ್ನ್ಮೆಂತ್  ಫೀದ್ಬಚ್ಕ್  ", "கோவேர்ந்மேன்ட் பெட்பக்  ",
				"ഗോവെര്നെമെന്റ്റ് ഫീട്ബച്ക്  " };

		selectCommodity = new String[] { "Select Commodity", "चयन सामग्री",
				"ఎంపిక వస్తువు", "ಆಯ್ದ ಸರಕು", "பொருள் தேர்ந்தெடுப்பு",
				"ഉല്പന്നം തിരഞ്ഞെടുക്കുക" };

		mandiTrades = new String[] { "Mandi Trades", "मन्डि ट्रडेस्",
				"మంది ట్రేడ్స్", "ಮಂದಿ ತ್ರದೆಸ್ ", "மண்டி திருதேஸ் ",
				"മണ്ടി  ട്രടെസ് " };

		sellersInformation = new String[] { "Seller's Information",
				"विक्रेता  सूचना", "విక్రేతలు సమాచారం", "ಮಾರಾಟಗಾರರ ಪಟ್ಟಿ",
				" விற்பனையாளர்கள்  தகவல்", "വിവരങ്ങൾ" };

		ratetheapp = new String[] { "Rate the App", "रेट  द  एप्प",
				"రేట్ ద ఆప్ప్", "ರೇಟ್  ದಿ ಆಪ್ಪ್", "ரேட் தி ஆப்ப",
				"റേറ്റ് ദി ആപ്പ്" };

		sharetheapp = new String[] { "Share the App", "शेयर द  एप्प ",
				"షేర్ ది ఆప్ప్ ", "ಶೇರ್ ದಿ ಆಪ್ಪ್ ", "ஷேர் தி ஆப்ப ",
				"ഷെയർ ദി ആപ്പ് " };

	}
}