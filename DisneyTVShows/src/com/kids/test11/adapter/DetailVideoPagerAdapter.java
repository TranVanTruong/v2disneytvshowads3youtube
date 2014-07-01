package com.kids.test11.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kidstv.disneyshows.R;

public class DetailVideoPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> listFragments;
//	private Context context;
	private String[] names;
	public DetailVideoPagerAdapter(Context context, FragmentManager fm, List<Fragment> listFragments) {
		super(fm);
		this.listFragments = listFragments;
//		this.context = context;
		names = context.getResources().getStringArray(R.array.detail_indicator_name);
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return listFragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listFragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return names[position];
	}
	

}
