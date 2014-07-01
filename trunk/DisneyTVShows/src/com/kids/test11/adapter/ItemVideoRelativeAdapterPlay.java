package com.kids.test11.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kids.test11.customize.ImageVideoDetailCustomize;
import com.kids.test11.entity.InfoVideo;
import com.kidstv.disneyshows.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class ItemVideoRelativeAdapterPlay extends BaseAdapter{
	private ArrayList<InfoVideo> datas;
	private LayoutInflater inflater;
	
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	private DisplayImageOptions options;
	
	private IClickItemVideo clickItemVideo;
	private Context context;
	
	public ItemVideoRelativeAdapterPlay(Context context, ImageLoader imageLoader, DisplayImageOptions options) {
		datas = new ArrayList<InfoVideo>();
		inflater = LayoutInflater.from(context);
		
		this.context = context;
		
		this.imageLoader = imageLoader;
		this.options = options;
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public InfoVideo getItem(int arg0) {
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	public void addData(InfoVideo InfoVideoTest)
	{
		datas.add(InfoVideoTest);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if(arg1 == null)
		{
			arg1 = inflater.inflate(R.layout.item_video_relative, null);
			
			ViewHolder holder = new ViewHolder();
			
			holder.ivAvatar = (ImageVideoDetailCustomize)arg1.findViewById(R.id.imgVideo);
			
			holder.tvDuration = (TextView)arg1.findViewById(R.id.tvDuration);
			
			holder.tvNameVideo = (TextView)arg1.findViewById(R.id.tvNameVideo);
			
			holder.tvViewVideo  = (TextView)arg1.findViewById(R.id.tvViewVideo);
			
			holder.llIndex = (LinearLayout)arg1.findViewById(R.id.llIndex);
			
			arg1.setTag(holder);
			
		}
		
		final InfoVideo infoVideo = datas.get(arg0);
		if(infoVideo != null)
		{
			ViewHolder holder = (ViewHolder)arg1.getTag();
			
//			Debug.debug("Link Image : " + infoVideo.getvAvatar());
			
			imageLoader.displayImage(infoVideo.getvAvatar(), holder.ivAvatar, options , new ImageLoadingListener() {
			    
			    @Override
			    public void onLoadingStarted(String imageUri, View view) {
			    }
			    
			    @Override
			    public void onLoadingFailed(String imageUri, View view,
				    FailReason failReason) {
			    }
			    
			    @Override
			    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			    }
			    
			    @Override
			    public void onLoadingCancelled(String imageUri, View view) {
			    }
			});
			
			holder.tvNameVideo.setText(infoVideo.getvName());
			holder.tvDuration.setText(infoVideo.getvDuration());
			
			int view = Integer.valueOf(infoVideo.getvView());
			
			String viewStr = String.format("%,d", view);
			
			viewStr = viewStr.replace(",", ".");
			
			holder.tvViewVideo.setText(new StringBuilder().append(viewStr).append(" ").append(context.getString(R.string.text_view)));
			
			holder.llIndex.setOnClickListener(new OnClickListener() {
			    
			    @Override
			    public void onClick(View v) {
				clickItemVideo.onclickItem(infoVideo.getVid(), infoVideo.getvYoutube());
			    }
			});
		}
		return arg1;
	}

	class ViewHolder
	{
	    	ImageVideoDetailCustomize ivAvatar;
		TextView tvDuration;
		TextView tvNameVideo;
		TextView tvViewVideo;
		LinearLayout llIndex;
	}
	
	public IClickItemVideo getClickItemVideo() {
	    return clickItemVideo;
	}

	public void setClickItemVideo(IClickItemVideo clickItemVideo) {
	    this.clickItemVideo = clickItemVideo;
	}

	public interface IClickItemVideo
	{
	    void onclickItem(String vid , String linkvideo);
	}
}
