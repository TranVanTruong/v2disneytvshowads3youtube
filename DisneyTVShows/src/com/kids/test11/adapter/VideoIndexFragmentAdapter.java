package com.kids.test11.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kids.test11.customize.ImageVideoCustomize;
import com.kids.test11.entity.InfoVideo;
import com.kidstv.disneyshows.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


public class VideoIndexFragmentAdapter extends BaseAdapter {
	private ArrayList<InfoVideo> datas;
	private LayoutInflater inflater;
	
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	private DisplayImageOptions options;
	
	private ClickItemVideo IclickItemVideo;
	
	public VideoIndexFragmentAdapter(Context context , ArrayList<InfoVideo> datas, ImageLoader imageLoader, DisplayImageOptions options) {
		this.datas = datas;
		inflater = LayoutInflater.from(context);
		
		this.imageLoader = imageLoader;
		this.options = options;
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public InfoVideo getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.item_video_index, null);
		}
			ImageVideoCustomize ivAvatar = (ImageVideoCustomize)convertView.findViewById(R.id.ivAvatar);
			
			TextView tvName = (TextView)convertView.findViewById(R.id.tvName);
			
			final InfoVideo infoVideo = datas.get(position);
			
			if(infoVideo != null)
			{
//				ivAvatar.setBackgroundResource(R.drawable.mqdefault4);
				imageLoader.displayImage(infoVideo.getvAvatar(), ivAvatar, options ,new AnimateFirstDisplayListener());
				
				tvName.setText(infoVideo.getvName());
				
				ivAvatar.setOnClickListener(new OnClickListener() {
				    
				    @Override
				    public void onClick(View v) {
					IclickItemVideo.clickItem(infoVideo.getVid() , infoVideo.getvYoutube());
				    }
				});
			}
			
			
			
		return convertView;
	}
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
	
	
	public ClickItemVideo getIclickItemVideo() {
	    return IclickItemVideo;
	}

	public void setIclickItemVideo(ClickItemVideo iclickItemVideo) {
	    IclickItemVideo = iclickItemVideo;
	}

	public interface ClickItemVideo
	{
	    void clickItem(String vid , String id);
	}
	
}
