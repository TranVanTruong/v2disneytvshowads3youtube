package com.kids.test11.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kids.test11.entity.InfoVideo;
import com.kidstv.disneyshows.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class SlideShowImageAdapter1 extends PagerAdapter {

	Context context;
	// LoadImageFromUrlCache loadImageFromUrl;
	LayoutInflater inflater;
	private setOnClickItemListener onClickItemListener;
	
	private ArrayList<InfoVideo> datas;
	
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	private DisplayImageOptions options;

	public SlideShowImageAdapter1(Context mContext , ArrayList<InfoVideo> datas, ImageLoader imageLoader, DisplayImageOptions options) {
		this.context = mContext;
		this.datas = datas;
		
		this.imageLoader = imageLoader;
		this.options = options;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object instantiateItem(ViewGroup view, final int position) {
		View imageLayout = inflater.inflate(R.layout.item_pager_image, view,false);
		
		final ImageView linkImage = (ImageView) imageLayout.findViewById(R.id.image);
		
		InfoVideo infoVideo = datas.get(position);
		
		if(infoVideo != null)
		{
			imageLoader.displayImage(infoVideo.getvAvatar(), linkImage, options , new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String imageUri, View view) {
				}
				@Override
				public void onLoadingFailed(String imageUri, View view,
						FailReason failReason) {
					
				}
				
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					FadeInBitmapDisplayer.animate(linkImage, 500);
				}
				
				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					
				}
			});
			
			linkImage.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onClickItemListener.onCallClick(v, position);
				}
			});
			((ViewPager) view).addView(imageLayout, 0);
		}
		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}
	public void setOnClickItemListener(setOnClickItemListener listenerActive) {
		this.onClickItemListener = listenerActive;
	}
	
	public interface setOnClickItemListener {
		void onCallClick(View v, int idImage);
	}
}
