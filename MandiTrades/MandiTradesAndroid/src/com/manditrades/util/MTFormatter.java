package com.manditrades.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MTFormatter {

	public static String localTimeStringFromDate(Date date) {
		if (date == null)
			return "";

		SimpleDateFormat format = new SimpleDateFormat("HH:mm a",
				DateFormatSymbols.getInstance());
		format.setTimeZone(TimeZone.getDefault());

		return format.format(date);
	}

	public static String getDayOfTheWeek(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat("EEEE",
				DateFormatSymbols.getInstance());

		format.setTimeZone(TimeZone.getDefault());
		System.out.println("@@@@ --- " + TimeZone.getDefault());

		return format.format(date);
	}

	public static Date getUTCDateFromDateString(String dateString)
			throws ParseException {

		if (dateString == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSSZZZ",
				DateFormatSymbols.getInstance(new Locale("en_US_POSIX")));

		return format.parse(dateString);
	}

	public static Date getISODateFromDateString(String dateString)
			throws ParseException {
		if (dateString == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		format.setTimeZone(TimeZone.getDefault());
		Date localTime = format.parse(dateString);

		localTime = new Date(localTime.getTime()
				+ TimeZone.getDefault().getOffset(localTime.getTime()));

		return localTime;

	}

	public static String formatDateString(Date date, boolean includeYear) {

		if (date == null)
			return "";
		SimpleDateFormat format = null;

		if (includeYear) {
			format = new SimpleDateFormat("MMM d, yyyy",
					DateFormatSymbols.getInstance());
		} else {
			format = new SimpleDateFormat("MMM d 'at' hh:mm a",
					DateFormatSymbols.getInstance());
		}

		return format.format(date);

	}

	public static String formatDateMMDDYYYY(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy",
				DateFormatSymbols.getInstance());

		return format.format(date);

	}

	public static String formatTimeString(String time) {
		if (time == null || time == "")
			return "";

		String[] splitTime = time.split(":");

		int hour = Integer.parseInt(splitTime[0].trim());

		String formatedTime = "";

		if (hour > 12) {
			hour = hour - 12;
			formatedTime += hour + ":" + splitTime[1] + " PM";
		} else if (hour == 0) {
			hour = 12;
			formatedTime += hour + ":" + splitTime[1] + " AM";
		} else {
			formatedTime += hour + ":" + splitTime[1] + " AM";
		}
		return formatedTime;

	}

	public static String formatCurrency(double value) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

		return format.format(value);
	}

	public static Date getDateFromString(String strDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(TimeZone.getDefault());

		Date date = null;

		try {
			date = format.parse(strDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return date;
	}

	public static String getFormattedPostDate(String postedOnDate) {

		String postedDateString;

		Calendar calendar = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		Date todayDate = calendar.getTime();
		long todayMillis = calendar.getTimeInMillis();

		Date postDate = getDateFromString(postedOnDate);
		long postMillis = postDate.getTime();

		long diffTimeSecs = (todayMillis - postMillis) / 1000;

		int hours = (int) diffTimeSecs / 3600;
		int minutes = (int) diffTimeSecs / 60;

		if ((minutes > 55) || (minutes > 0)) {
			minutes = 0;
			hours++;
		}

		if (diffTimeSecs <= 3 * 60) {
			postedDateString = String.format("just now");
		} else if (diffTimeSecs > 3 * 60 && diffTimeSecs < 60 * 60) {
			postedDateString = String.format("%s minutes ago",
					diffTimeSecs / 60);
		} else if (diffTimeSecs >= 1 * 60 * 60 && diffTimeSecs < 24 * 60 * 60) {
			postedDateString = String.format("%s %s ago", diffTimeSecs / 3600,
					diffTimeSecs > (2 * 60 * 60) ? "hours" : "hour");
		} else if (diffTimeSecs >= 24 * 60 * 60
				&& diffTimeSecs < 2 * 24 * 60 * 60) {
			postedDateString = String.format("yesterday");
		} else if (diffTimeSecs >= 2 * 24 * 60 * 60
				&& diffTimeSecs < 3 * 24 * 60 * 60) {
			postedDateString = String.format("2 days ago");
		} else {
			postedDateString = formatDateString(postDate, false);
		}

		return postedDateString;
	}

}
