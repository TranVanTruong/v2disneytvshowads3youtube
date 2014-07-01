package com.kids.test11.customize;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.kidstv.disneyshows.R;

public class ImageVideoPagerCustomize extends ImageView{
	private int width;

	public ImageVideoPagerCustomize(Context context) {
		super(context);
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
	}

	public ImageVideoPagerCustomize(Context context, AttributeSet attrs) {
		super(context, attrs);
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int widthImage = width -2*(getResources()
				.getDimensionPixelOffset(R.dimen.item_video_padding)) ;
		int heightImage = (widthImage * 40) / 100;
		
		this.setMeasuredDimension(widthImage, heightImage);
	}

}
