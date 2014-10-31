package com.manditrades.activities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

import com.manditrades.R;
import com.manditrades.cache.CommoditiesCache;
import com.manditrades.util.FileUtils;
import com.manditrades.util.GPSTracker;
import com.manditrades.util.JsonDataCallback;
import com.manditrades.util.MTAlertUtil;
import com.manditrades.util.MTURLHelper;
import com.manditrades.util.MTUtil;

public class NewPostActivity extends Activity {

	private SharedPreferences preferences;

	private Button playPauseBtn;
	private TextView slideToCancelTV;
	private Spinner varietySpinner;
	private EditText quantityET;
	private Switch quantityUnitSwitch;
	private EditText expectedPriceET;
	private TextView currentLocationTV;
	private Button postBtn;
	private Context context;
	private MediaRecorder audioRecorder;
	private TreeSet<Integer> tsImagePositions;
	private double lat;
	private double lng;

	private ImageView removeVideoIV;

	private VideoView videoView;
	private Button tabBtnImage;
	private Button tabBtnVideo;
	private LinearLayout mediaLL;
	private LinearLayout toggleLL;
	private RelativeLayout videoRL;
	private RelativeLayout imageRL;
	private LinearLayout audioLL;
	private LinearLayout uploadImageLL;

	private ArrayList<Bitmap> imagesBitmap;

	private int selectedPosition;

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	private Uri fileUri;
	private Bitmap mSelectedImage;
	private String fileExtension;
	private String commodity;
	private ArrayList<String> arlVarieties;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	private static String mFileName = null;

	public static int PIC_IMAGE_FROM_GALLERY = 1;
	public static int PIC_VIDEO_FROM_GALLERY = 2;
	private static String mVideoFile;
	private LayoutInflater inflater;

	private int imageUploadedCount;
	private ImageView clickedItem;
	private Bitmap tempBitmap;
	private ImageView setImageView;
	private String category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_new_sale);

		context = this;

		commodity = getIntent().getExtras().getString("SELECTED_COMMODITY");
		category = getIntent().getExtras().getString("SELECTED_CATEGORY");

		arlVarieties = CommoditiesCache.getCommoditiesCache()
				.getVarietiesForCommodity(commodity);

		initComponents();

		setListenters();

		setValues();

		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
		mFileName += "/audiorecordtest.3gp";
		System.out.println(mFileName);

		MTUtil.setActionBar(context, commodity, true);

	}

	private void setValues() {
		varietySpinner.setAdapter(new ArrayAdapter(context,
				android.R.layout.simple_spinner_dropdown_item, arlVarieties));

		currentLocationTV.setText(getCurrentAddress());
	}

	private void setListenters() {

		playPauseBtn.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View view) {
				startRecording();
				return false;
			}
		});

		playPauseBtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					stopRecording();
					break;
				case MotionEvent.ACTION_CANCEL:
					stopRecording();
					break;
				}

				return false;
			}
		});

		postBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				try {

					String quantity = quantityET.getText().toString();
					String expectedPrice = expectedPriceET.getText().toString();
					String currentLocation = currentLocationTV.getText()
							.toString();

					if (quantity == null || quantity.equals("")) {
						MTAlertUtil.showMessesBox(context, "Enter Detail",
								"Enter a vlid quantity",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(
											DialogInterface dialogInterface,
											int i) {
									}
								});
					} else if (expectedPrice == null
							|| expectedPrice.equals("")) {
						MTAlertUtil.showMessesBox(context, "Enter Detail",
								"Enter a vlid expected price",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(
											DialogInterface dialogInterface,
											int i) {
									}
								});
					} else if (currentLocation == null
							|| currentLocation.equals("")
							|| currentLocation
									.equals("Unable to fetch location")) {
						MTAlertUtil.showMessesBox(context,
								"Unable to create post",
								"Sorry! We are unable to fetch your location.",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(
											DialogInterface dialogInterface,
											int i) {
									}
								});
					} else {

						String mobileNo = preferences.getString("MOBILE_NO",
								" ");
						String username = preferences.getString("USERNAME",
								"user" + (int) Math.random());

						String variety = (String) varietySpinner
								.getSelectedItem();

						String base64Audio = "";
						File file = new File(mFileName);

						if (file.length() > 0) {
							base64Audio = FileUtils
									.encodeFileToBase64Binary(mFileName);
							file.delete();
						}
						getImages();

						String base64Video = "";

						if (mVideoFile != null) {
							base64Video = FileUtils
									.encodeFileToBase64Binary(mVideoFile);
						}

						String url = MTURLHelper
								.getAPIEndpointURL(MTURLHelper.newPostURL);
						String method = "POST";
						List<NameValuePair> params = new ArrayList<NameValuePair>();

						params.add(new BasicNameValuePair("seller_mobile",
								mobileNo));
						params.add(new BasicNameValuePair("seller_name",
								username));
						params.add(new BasicNameValuePair("category", category));
						params.add(new BasicNameValuePair("commodity",
								commodity));
						params.add(new BasicNameValuePair("price",
								expectedPrice));
						params.add(new BasicNameValuePair("quantity", quantity));
						params.add(new BasicNameValuePair("variety", variety));

						String encodedImage = "";
						for (int j = 0; j < 4; j++) {
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							if (j < imagesBitmap.size()) {
								imagesBitmap.get(j).compress(
										Bitmap.CompressFormat.JPEG, 100, baos);
								byte[] imageBytes = baos.toByteArray();

								encodedImage = Base64.encodeToString(
										imageBytes, Base64.DEFAULT);
								params.add(new BasicNameValuePair("images" + j,
										encodedImage));
							} else {
								params.add(new BasicNameValuePair("images" + j,
										""));
							}
						}

						params.add(new BasicNameValuePair("address",
								currentLocation));
						params.add(new BasicNameValuePair("latitude", lat + ""));
						params.add(new BasicNameValuePair("longitude", lng + ""));
						params.add(new BasicNameValuePair("audio", base64Audio));
						params.add(new BasicNameValuePair("video", base64Video));

						Object[] objects = { url, method, params };

						JsonDataCallback callback = new JsonDataCallback(
								context) {

							@Override
							public void receiveData(JSONObject responseJson) {
								JSONObject root;
								try {
									root = responseJson.getJSONObject("root");
									if (root.has("seller_name")) {

										MTAlertUtil
												.showMessesBox(
														context,
														"Mandi Trades",
														"Posted Successfully",
														new DialogInterface.OnClickListener() {
															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																Intent intent = new Intent(
																		context,
																		GovtFeedbackActivity.class);
																intent.putExtra(
																		"SCHEME_INDEX",
																		0);
																startActivity(intent);
															}
														});
									} else {
										MTAlertUtil
												.showMessesBox(
														context,
														"Mandi Trades",
														"Unable to post",
														new DialogInterface.OnClickListener() {
															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																finish();
															}
														});
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						};

						callback.execute(objects);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		tabBtnImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				videoRL.setVisibility(View.GONE);
				imageRL.setVisibility(View.VISIBLE);
				tabBtnImage.setBackgroundColor(Color.BLACK);
				tabBtnVideo.setBackgroundColor(Color.LTGRAY);
				tabBtnImage.setTextColor(Color.LTGRAY);
				tabBtnVideo.setTextColor(Color.BLACK);
			}
		});

		tabBtnVideo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				videoRL.setVisibility(View.VISIBLE);
				// videoView
				// .setVideoPath("http://192.168.1.19/apimandi/v1/media/541a9ae788c09d81048b4567/fruits/grapes/18-09-14:18:25:174.mp4");
				// videoView.setMediaController(mediaController);
				// videoView.setFitsSystemWindows(true);
				imageRL.setVisibility(View.GONE);
				tabBtnImage.setBackgroundColor(Color.LTGRAY);
				tabBtnVideo.setBackgroundColor(Color.BLACK);
				tabBtnVideo.setTextColor(Color.LTGRAY);
				tabBtnImage.setTextColor(Color.BLACK);

				launchVideoPickerOption();

			}
		});

		removeVideoIV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mVideoFile = "";
				videoView.setVideoPath(mVideoFile);
				removeVideoIV.setVisibility(View.GONE);
			}
		});

	}

	public void launchVideoPickerOption() {

		MTAlertUtil.showMessesBox(context, "Choose video picker", "",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						openCamera(2);
					}
				}, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						openGallery(2);
					}
				}, "Open Camera", "Open Gallery");

	}

	public void launchPhotoPickerOption() {

		MTAlertUtil.showMessesBox(context, "Choose image picker", "",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						openCamera(1);
					}
				}, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						openGallery(1);
					}
				}, "Open Camera", "Open Gallery");

	}

	public void openCamera(int type) {

		Intent intent = null;
		if (type == 2) {
			intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image
																// file
			// name
			startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
		} else if (type == 1) {
			// create Intent to take a picture and return control to the calling
			// application
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file
																// to save the
																// image
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image
																// file
			// name
			startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		}
	}

	private void openGallery(int type) {
		if (type == 1) {
			Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
			photoPickerIntent.setType("image/*");
			startActivityForResult(photoPickerIntent, PIC_IMAGE_FROM_GALLERY);
		} else if (type == 2) {
			Intent videoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
			videoPickerIntent.setType("video/*");
			startActivityForResult(videoPickerIntent, PIC_VIDEO_FROM_GALLERY);
		}
	}

	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");

			System.out.println("!@!@!@!@!@  " + mediaFile);

		} else {
			return null;
		}

		return mediaFile;
	}

	private synchronized boolean hasValidBitmap() {
		return mSelectedImage != null && !mSelectedImage.isRecycled();
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		String mFilePath = null;
		if ((requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
				&& resultCode == RESULT_OK) {
			String filePath = fileUri.getPath();
			try {
				hasValidBitmap();

				mFilePath = filePath;
				fileExtension = "jpg";

			} catch (OutOfMemoryError e) {
				Log.v("Create Group", "Path:" + filePath);
			}

		} else if (requestCode == PIC_IMAGE_FROM_GALLERY) {
			if (intent != null && resultCode == RESULT_OK) {

				Uri selectedImage = intent.getData();

				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String filePath = cursor.getString(columnIndex);
				cursor.close();

				try {
					hasValidBitmap();
					mFilePath = filePath;

					int dot = mFilePath.lastIndexOf(".");
					fileExtension = mFilePath.substring(dot + 1);

				} catch (OutOfMemoryError e) {
					Log.v("Create Group", "Path:" + filePath);
				}
			}
		} else if ((requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE)
				&& resultCode == RESULT_OK) {
			String filePath = fileUri.getPath();
			try {
				mFilePath = filePath;
				fileExtension = "mp4";

			} catch (OutOfMemoryError e) {
				Log.v("Create Group", "Path:" + filePath);
			}

		} else if (requestCode == PIC_VIDEO_FROM_GALLERY) {
			if (intent != null && resultCode == RESULT_OK) {

				Uri selectedImage = intent.getData();

				String[] filePathColumn = { MediaStore.Video.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String filePath = cursor.getString(columnIndex);
				cursor.close();

				try {
					hasValidBitmap();
					mFilePath = filePath;

					int dot = mFilePath.lastIndexOf(".");
					fileExtension = mFilePath.substring(dot + 1);

				} catch (OutOfMemoryError e) {
					Log.v("Create Group", "Path:" + filePath);
				}
			}
		}

		if (mFilePath != null) {
			if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE
					|| requestCode == PIC_IMAGE_FROM_GALLERY) {
				tsImagePositions.add(selectedPosition);

				setImageView
						.setImageBitmap(BitmapFactory.decodeFile(mFilePath));

			} else if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE
					|| requestCode == PIC_VIDEO_FROM_GALLERY) {
				videoView.setVideoPath(mFilePath);
				videoView.setMediaController(new MediaController(context));
				mVideoFile = mFilePath;
				removeVideoIV.setVisibility(View.VISIBLE);
			}
		}

	}

	private void startRecording() {
		audioRecorder = new MediaRecorder();
		audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		audioRecorder.setOutputFile(mFileName);
		audioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			audioRecorder.prepare();
		} catch (IOException e) {
			Log.e("Recording", "prepare() failed");
		}

		audioRecorder.start();
	}

	private void stopRecording() {
		if (audioRecorder != null) {
			audioRecorder.stop();
			audioRecorder.reset();
			audioRecorder.release();
			audioRecorder = null;
		}

		final MediaPlayer mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.setVolume(100.0f, 100.0f);
			mPlayer.prepare();
			mPlayer.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					mPlayer.start();
				}
			});

			mPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer arg0) {
					mPlayer.release();
				}
			});
		} catch (IOException e) {
			Log.e("Playing", "prepare() failed");
		}
	}

	private void initComponents() {
		imageUploadedCount = 0;

		preferences = PreferenceManager.getDefaultSharedPreferences(context);

		playPauseBtn = (Button) findViewById(R.id.play_pause);
		slideToCancelTV = (TextView) findViewById(R.id.slide_to_cancel);
		varietySpinner = (Spinner) findViewById(R.id.variety_spinner);
		quantityET = (EditText) findViewById(R.id.quantity_et);
		quantityUnitSwitch = (Switch) findViewById(R.id.quantity_unit_switch);
		expectedPriceET = (EditText) findViewById(R.id.expected_price);
		currentLocationTV = (TextView) findViewById(R.id.current_location);
		postBtn = (Button) findViewById(R.id.post_btn);

		tsImagePositions = new TreeSet<Integer>();
		videoView = (VideoView) findViewById(R.id.video_view);
		tabBtnImage = (Button) findViewById(R.id.tab_button_image);
		tabBtnVideo = (Button) findViewById(R.id.tab_button_video);
		mediaLL = (LinearLayout) findViewById(R.id.media_ll);
		toggleLL = (LinearLayout) findViewById(R.id.toggle_ll);
		videoRL = (RelativeLayout) findViewById(R.id.video_rl);
		imageRL = (RelativeLayout) findViewById(R.id.images_rl);
		audioLL = (LinearLayout) findViewById(R.id.audio_bar_ll);

		removeVideoIV = (ImageView) findViewById(R.id.remove_video_icon);
		uploadImageLL = (LinearLayout) findViewById(R.id.upload_image_ll);

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		RelativeLayout placeholderRL;
		final ImageView placeholderUploadImageView;
		final ImageView placeholderDeleteImageView;

		placeholderRL = (RelativeLayout) inflater.inflate(
				R.layout.upload_image_template, uploadImageLL, false);

		placeholderUploadImageView = (ImageView) placeholderRL
				.findViewById(R.id.upload_image_icon);

		placeholderDeleteImageView = (ImageView) placeholderRL
				.findViewById(R.id.remove_image_icon);

		uploadImageLL.addView(placeholderRL);

		placeholderUploadImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int i = Integer.parseInt((String) placeholderUploadImageView
						.getTag());
				if (i == 0) {
					addPhoto(placeholderUploadImageView);
				} else {

					updatePhoto(placeholderUploadImageView);
				}

			}
		});

	}

	private String getCurrentAddress() {

		GPSTracker tracker = new GPSTracker(context);
		List<Address> addresses = null;
		Geocoder geocoder = new Geocoder(context);
		lat = tracker.getLatitude();
		lng = tracker.getLongitude();
		try {
			addresses = geocoder.getFromLocation(lat, lng, 5);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (addresses != null && addresses.size() > 0)
			return addresses.get(0).getAddressLine(0);
		else
			return "Unable to fetch location";
	}

	private void addPhoto(final ImageView iv) {
		imageUploadedCount++;
		setImageView = iv;
		launchPhotoPickerOption();

		if (imageUploadedCount <= 3) {
			RelativeLayout rl = (RelativeLayout) inflater.inflate(
					R.layout.upload_image_template, uploadImageLL, false);
			final ImageView tempIV = (ImageView) rl
					.findViewById(R.id.upload_image_icon);

			tempIV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					addPhoto(tempIV);

				}
			});
			uploadImageLL.addView(rl);
		}
		final RelativeLayout rl = (RelativeLayout) iv.getParent();
		final ImageView removeImageIcon = (ImageView) rl
				.findViewById(R.id.remove_image_icon);
		removeImageIcon.setVisibility(View.VISIBLE);
		removeImageIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				deletePhoto(rl);

			}
		});

		iv.setTag(1);

		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int i = (Integer) iv.getTag();
				if (i == 0) {
					addPhoto(iv);
				} else {

					updatePhoto(iv);
				}

			}
		});

	}

	private void updatePhoto(ImageView iv) {
		setImageView = iv;
		launchPhotoPickerOption();

	}

	private void deletePhoto(View v) {
		uploadImageLL.removeView(v);
		if (imageUploadedCount == 4) {
			RelativeLayout rl = (RelativeLayout) inflater.inflate(
					R.layout.upload_image_template, uploadImageLL, false);
			final ImageView tempIV = (ImageView) rl
					.findViewById(R.id.upload_image_icon);

			tempIV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					addPhoto(tempIV);

				}
			});
			uploadImageLL.addView(rl);
		}
		imageUploadedCount--;

	}

	private void getImages() {
		imagesBitmap = new ArrayList<Bitmap>();
		int noOfRL = uploadImageLL.getChildCount();
		for (int i = 0; i < noOfRL; i++) {
			ImageView imageView = (ImageView) uploadImageLL.getChildAt(i)
					.findViewById(R.id.upload_image_icon);

			Object object = imageView.getTag();
			int tag = 0;

			if (object instanceof Integer) {
				tag = (Integer) object;

			} else if (object instanceof String) {
				tag = Integer.parseInt((String) object);

			}

			if (tag == 1) {
				Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable())
						.getBitmap();
				imagesBitmap.add(bitmap);
			}

		}
	}
}
// private class ImagePagerAdapter extends PagerAdapter {
//
// private Context context;
//
// public ImagePagerAdapter(Context context) {
// this.context = context;
// }
//
// @Override
// public void destroyItem(final ViewGroup container, final int position,
// final Object object) {
// ((ViewPager) container).removeView((SmartImageView) object);
// }
//
// @Override
// public int getCount() {
// return imagesBitmap.size();
// }
//
// @Override
// public Object instantiateItem(final ViewGroup container,
// final int position) {
//
// final SmartImageView imageView = new SmartImageView(context);
// // (SmartImageView) LayoutInflater
// // .from(context).inflate(R.layout.images_template, container)
// // .findViewById(R.id.images_iv);
// ((ViewPager) container).addView(imageView, 0);
//
// LayoutParams params = imageView.getLayoutParams();
// params.height = (int) context.getResources().getDimension(
// R.dimen.image_height);
// imageView.setLayoutParams(params);
//
// final int padding = context.getResources().getDimensionPixelSize(
// R.dimen.padding_image);
// imageView.setPadding(padding, padding, padding, padding);
// // imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//
// imageView.setImageBitmap(imagesBitmap.get(position));
//
// imageView.setOnClickListener(new OnClickListener() {
//
// @Override
// public void onClick(View view) {
// selectedPosition = position;
// launchPhotoPickerOption();
// }
// });
//
// return imageView;
// }
//
// @Override
// public boolean isViewFromObject(final View view, final Object object) {
// return view == ((ImageView) object);
// }
// }