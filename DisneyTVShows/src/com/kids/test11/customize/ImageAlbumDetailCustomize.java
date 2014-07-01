package com.kids.test11.customize;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.kidstv.disneyshows.R;

public class ImageAlbumDetailCustomize extends ImageView {
	private int width;

	public ImageAlbumDetailCustomize(Context context) {
		super(context);
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
	}

	public ImageAlbumDetailCustomize(Context context, AttributeSet attrs) {
		super(context, attrs);
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int widthImage = (width - (2 * (getContext().getResources()
				.getDimensionPixelOffset(R.dimen.padding_image_nomal)))) / 4;

		this.setMeasuredDimension(widthImage, widthImage);
	}

}
