package com.kids.test11.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kids.test11.entity.CatInfo;
import com.kidstv.disneyshows.R;

public class ItemCatAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private ArrayList<CatInfo> datas;
	private int catIdSelected;
	private Context context;
	
	public ItemCatAdapter(Context context , ArrayList<CatInfo> datas , int catIdSelected) {
		this.datas = datas;
		this.catIdSelected = catIdSelected;
		this.context = context;
		
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public CatInfo getItem(int arg0) {
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		View v = convertView;
		if(convertView == null)
		{
			v = inflater.inflate(R.layout.item_listcate, null);
			
			Holder holder = new Holder();
			
			holder.tvNameCate = (TextView)v.findViewById(R.id.tvCatName);
			holder.vDiver = (View)v.findViewById(R.id.vSelected);
			
			v.setTag(holder);
		}
		
		CatInfo catInfo = datas.get(arg0);
		if(catInfo != null)
		{
			Holder holder = (Holder)v.getTag();
			
			holder.tvNameCate.setText(catInfo.getCatName());
			
			if(catInfo.getCatID().equals(String.valueOf(catIdSelected)))
			{
				holder.vDiver.setVisibility(View.VISIBLE);
				holder.tvNameCate.setBackgroundColor(context.getResources().getColor(R.color.color_bg_slide_selected));
			}
			else
			{
				holder.vDiver.setVisibility(View.GONE);
				holder.tvNameCate.setBackgroundColor(context.getResources().getColor(R.color.color_bg_slide));
			}
			
		}
		
		return v;
	}

	class Holder
	{
		View vDiver;
		TextView tvNameCate;
	}
}
