package com.kids.test11.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.kids.test11.entity.InfoAds;
import com.kidstv.disneyshows.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SlideShowImageAdapter extends PagerAdapter {

	Context context;
	// LoadImageFromUrlCache loadImageFromUrl;
	LayoutInflater inflater;
	private setOnClickItemListener onClickItemListener;
	private ArrayList<InfoAds> datas;
	
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	private DisplayImageOptions options;

	public SlideShowImageAdapter(Context mContext, ImageLoader imageLoader, DisplayImageOptions options) {
		this.context = mContext;
		// loadImageFromUrl = new LoadImageFromUrlCache(mContext);
		inflater = LayoutInflater.from(mContext);
		datas = new ArrayList<InfoAds>();
		
		this.imageLoader = imageLoader;
		this.options = options;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	public void add(InfoAds getAdsHome) {
		datas.add(getAdsHome);
		notifyDataSetChanged();
	}
	@Override
	public Object instantiateItem(View container, int position) {
		View imageLayout = inflater.inflate(R.layout.item_pager_image , null);
		ImageView linkImage = (ImageView) imageLayout.findViewById(R.id.image);
		
		final InfoAds infoAds = datas.get(position);
		
		if(infoAds != null)
		{
		    imageLoader.displayImage(infoAds.getImage(), linkImage, options);
		    //linkImage.setBackgroundResource(R.drawable.mqdefault5);
		}
		
		linkImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    	if(infoAds.getType() == 0)
			    	{
			    	    onClickItemListener.onCallClick(true , "" , infoAds.getId());
			    	}
			    	else
			    	{
			    	    onClickItemListener.onCallClick(false ,  infoAds.getId() , infoAds.getIdYoutube());
			    	}
			}
		});
		((ViewPager) container).addView(imageLayout, 0);
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
		void onCallClick(boolean isPla , String vid , String linkVideo);
	}
}
