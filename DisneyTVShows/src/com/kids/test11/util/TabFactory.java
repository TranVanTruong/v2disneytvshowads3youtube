package com.kids.test11.util;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class TabFactory implements TabContentFactory {
	private Context mContext;
	public TabFactory(Context context) {
		mContext = context;
	}
	@Override
	public View createTabContent(String tag) {
		View v = new View(mContext);
		return v;
	}
}
