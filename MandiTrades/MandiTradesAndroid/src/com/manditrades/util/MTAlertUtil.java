package com.manditrades.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MTAlertUtil {

	public MTAlertUtil() {

	}

	public static boolean isOnline(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {

			NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
			boolean isConnected = activeNetwork != null
					&& activeNetwork.isConnectedOrConnecting();
			return isConnected;

		}
		return false;

	}

	public static void showMessesBox(Context context, int heading, int msg,
			DialogInterface.OnClickListener clickListener) {

		AlertDialog.Builder builder;

		builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getResources().getString(heading));
		builder.setMessage(context.getResources().getString(msg));

		builder.setPositiveButton("OK", clickListener);

		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		if (!((Activity) context).isFinishing())
			alert.show();
	}

	public static void showMessesBox(Context context, int heading, int msg,
			DialogInterface.OnClickListener positiveListener,
			DialogInterface.OnClickListener negativeListener) {

		AlertDialog.Builder builder;

		builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getResources().getString(heading));
		builder.setMessage(context.getResources().getString(msg));

		builder.setPositiveButton("OK", positiveListener);
		builder.setNegativeButton("Cancel", negativeListener);

		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		if (!((Activity) context).isFinishing())
			alert.show();
	}

	public static void showMessesBox(Context context, int heading, int msg,
			DialogInterface.OnClickListener positiveListener,
			DialogInterface.OnClickListener negativeListener, String okString,
			String cancelString) {

		AlertDialog.Builder builder;

		builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getResources().getString(heading));
		builder.setMessage(context.getResources().getString(msg));

		builder.setPositiveButton(okString, positiveListener);
		builder.setNegativeButton(cancelString, negativeListener);

		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		if (!((Activity) context).isFinishing())
			alert.show();
	}

	public static void showMessesBox(Context context, String heading,
			String msg,
			android.content.DialogInterface.OnClickListener clickListener) {

		AlertDialog.Builder builder;

		builder = new AlertDialog.Builder(context);
		builder.setTitle(heading);
		builder.setMessage(msg);
		builder.setPositiveButton("OK", clickListener);
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		if (!((Activity) context).isFinishing())
			if (!((Activity) context).isFinishing())
				alert.show();

	}

	public static void showMessesBox(Context context, String heading,
			String msg, DialogInterface.OnClickListener positiveListener,
			DialogInterface.OnClickListener negativeListener, String okString,
			String cancelString) {

		AlertDialog.Builder builder;

		builder = new AlertDialog.Builder(context);
		builder.setTitle(heading);
		builder.setMessage(msg);

		builder.setPositiveButton(okString, positiveListener);
		builder.setNegativeButton(cancelString, negativeListener);

		AlertDialog alert = builder.create();
		alert.setCancelable(true);
		if (!((Activity) context).isFinishing())
			if (!((Activity) context).isFinishing())
				alert.show();
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int radious) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, radious, radious, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

}
