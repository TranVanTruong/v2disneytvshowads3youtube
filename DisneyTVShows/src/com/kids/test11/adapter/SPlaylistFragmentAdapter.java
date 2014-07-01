package com.kids.test11.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kids.test11.customize.ImageAlbumCustomize;
import com.kids.test11.entity.InfoAlbum;
import com.kidstv.disneyshows.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


public class SPlaylistFragmentAdapter extends BaseAdapter {
	private ArrayList<InfoAlbum> datas;
	private LayoutInflater inflater;
	
	private ImageLoader imageLoader;
	
	private DisplayImageOptions options;
	
	private IClickPla clickPla;
	
	public SPlaylistFragmentAdapter(Context context, ImageLoader imageLoader, DisplayImageOptions options) {
		datas = new ArrayList<InfoAlbum>();
		inflater = LayoutInflater.from(context);
		
		this.imageLoader = imageLoader;
		this.options = options;
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public InfoAlbum getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void addData(InfoAlbum infoAlbum){
		datas.add(infoAlbum);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.item_album_index, null);
		}
			ImageAlbumCustomize ivAvatar = (ImageAlbumCustomize)convertView.findViewById(R.id.ivAvatar);
			
			TextView tvName = (TextView)convertView.findViewById(R.id.tvName);
			
			final InfoAlbum infoVideo = datas.get(position);
			
			if(infoVideo != null)
			{
//				ivAvatar.setBackgroundResource(R.drawable.mqdefault3);
			    	imageLoader.displayImage(infoVideo.getaAvatar(), ivAvatar, options);
				tvName.setText(infoVideo.getaName());
				
//				ivAvatar.setOnClickListener(new OnClickListener() {
//				    
//				    @Override
//				    public void onClick(View arg0) {
//					clickPla.clickItemPla(infoVideo.getAid());
//				    }
//				});
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
	
	public IClickPla getClickPla() {
	    return clickPla;
	}

	public void setClickPla(IClickPla clickPla) {
	    this.clickPla = clickPla;
	}

	public interface IClickPla
	{
	    void clickItemPla(String id);
	}
}
