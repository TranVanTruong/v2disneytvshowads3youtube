package com.kids.test11.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kids.test11.customize.ImageAlbumDetailCustomize;
import com.kids.test11.entity.InfoAlbum;
import com.kidstv.disneyshows.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ItemAlbumRelativeAdapter extends BaseAdapter{
	private ArrayList<InfoAlbum> datas;
	private LayoutInflater inflater;
	
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	private DisplayImageOptions options;
	
	private IOnclickItemPla onclickItemPla;
	
	public ItemAlbumRelativeAdapter(Context context, ImageLoader imageLoader, DisplayImageOptions options) {
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
	public InfoAlbum getItem(int arg0) {
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	public void addData(InfoAlbum InfoVideoTest)
	{
		datas.add(InfoVideoTest);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if(arg1 == null)
		{
			arg1 = inflater.inflate(R.layout.item_album_relative, null);
			
			ViewHolder holder = new ViewHolder();
			
			holder.ivAvatar = (ImageAlbumDetailCustomize)arg1.findViewById(R.id.imgVideo);
			
			holder.tvDuration = (TextView)arg1.findViewById(R.id.tvDuration);
			
			holder.tvNameVideo = (TextView)arg1.findViewById(R.id.tvNameVideo);
			
			holder.tvViewVideo  = (TextView)arg1.findViewById(R.id.tvCountVideo);
			
			holder.llIndex = (LinearLayout)arg1.findViewById(R.id.llIndex);
			
			arg1.setTag(holder);
			
		}
		
		final InfoAlbum infoVideo = datas.get(arg0);
		if(infoVideo != null)
		{
			ViewHolder holder = (ViewHolder)arg1.getTag();
			holder.tvDuration.setVisibility(View.GONE);
			
			imageLoader.displayImage(infoVideo.getaAvatar(), holder.ivAvatar, options);
			
			holder.tvNameVideo.setText(infoVideo.getaName());
//			holder.tvDuration.setText(infoVideo.getvDuration());
			
			
//			int view = Integer.valueOf(infoVideo.get);
			
//			String viewStr = String.format("%,d", view);
			
//			viewStr = viewStr.replace(",", ".");
			
			holder.tvViewVideo.setText(infoVideo.getCountVideo() + " video" );
			
			holder.llIndex.setOnClickListener(new OnClickListener() {
			    
			    @Override
			    public void onClick(View v) {
				onclickItemPla.onclickItem(infoVideo.getAid());
			    }
			});
		}
		return arg1;
	}

	class ViewHolder
	{
	    	ImageAlbumDetailCustomize ivAvatar;
		TextView tvDuration;
		TextView tvNameVideo;
		TextView tvViewVideo;
		LinearLayout llIndex;
	}
	
	public IOnclickItemPla getOnclickItemPla() {
	    return onclickItemPla;
	}

	public void setOnclickItemPla(IOnclickItemPla onclickItemPla) {
	    this.onclickItemPla = onclickItemPla;
	}

	public interface IOnclickItemPla
	{
	    void onclickItem(String aid);
	}
}
