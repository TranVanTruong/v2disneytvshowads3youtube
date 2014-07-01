package com.kids.test11.customize;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.kidstv.disneyshows.R;

public class ImageVideoCustomize extends ImageView{
	private int width;

	public ImageVideoCustomize(Context context) {
		super(context);
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
	}

	public ImageVideoCustomize(Context context, AttributeSet attrs) {
		super(context, attrs);
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int widthImage = (width - (2 * (getContext().getResources()
				.getDimensionPixelOffset(R.dimen.padding_image_nomal)))) / 2;
		int heightImage = (widthImage * 65) / 100;
		
		this.setMeasuredDimension(widthImage, heightImage);
	}

}
