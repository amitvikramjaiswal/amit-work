package com.manditrades.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.loopj.android.image.SmartImageView;
import com.manditrades.R;
import com.manditrades.util.MTURLHelper;

public class ImagePagerAdapter extends PagerAdapter {

	private Context context;
	private ArrayList<String> imageUrls;

	public ImagePagerAdapter(Context context, ArrayList<String> imageUrls) {

		this.context = context;
		this.imageUrls = imageUrls;

	}

	@Override
	public void destroyItem(final ViewGroup container, final int position,
			final Object object) {
		((ViewPager) container).removeView((SmartImageView) object);
	}

	@Override
	public int getCount() {
		return imageUrls.size();
	}

	@Override
	public Object instantiateItem(final ViewGroup container, final int position) {

		final SmartImageView imageView = new SmartImageView(context);
		// (SmartImageView) LayoutInflater
		// .from(context).inflate(R.layout.images_template, container)
		// .findViewById(R.id.images_iv);
		((ViewPager) container).addView(imageView, 0);

		LayoutParams params = imageView.getLayoutParams();
		params.height = (int) context.getResources().getDimension(
				R.dimen.image_height);
		imageView.setLayoutParams(params);

		final int padding = context.getResources().getDimensionPixelSize(
				R.dimen.padding_image);
		imageView.setPadding(padding, padding, padding, padding);
		// imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

		if (!imageUrls.get(0).equalsIgnoreCase("noimage")) {

			String url = String.format("%s%s%s",
					MTURLHelper.getAPIEndpointURL(""), "/v1/",
					imageUrls.get(position)).replace(" ", "%20");

			System.out.println("@@@@ " + url);

			imageView.setImageUrl(url);

		} else {
			imageView.setImageResource(R.drawable.noimage);
		}

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				// Intent intent = new Intent(context,
				// ProductImageActivity.class);
				//
				// intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// intent.putStringArrayListExtra("IMAGE_URLS", imageUrls);
				// intent.putExtra("POSITION", position);
				//
				// startActivity(intent);

			}
		});

		return imageView;
	}

	@Override
	public boolean isViewFromObject(final View view, final Object object) {
		return view == ((ImageView) object);
	}
}
