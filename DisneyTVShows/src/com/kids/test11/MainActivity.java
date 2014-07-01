package com.kids.test11;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import com.kids.test11.adapter.ItemCatAdapter;
import com.kids.test11.entity.CatInfo;
import com.kids.test11.util.TabFactory;
import com.kids.test11.util.ViewUtil;
import com.kidstv.disneyshows.R;

public class MainActivity extends Activity {
	private ItemCatAdapter adapter;
	private ListView listView;
	private ScrollView scrollView;
	private View vSelectedIndex;
	private KidsTVApplication application;
	
	private TabHost mTabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_slide_listcat);
		listView = (ListView)findViewById(R.id.lvListCat);
		scrollView = (ScrollView)findViewById(R.id.scrollView);
		vSelectedIndex = (View)findViewById(R.id.vSelectedIndex);
		application = (KidsTVApplication)this.getApplication();
		if(application.getCatID() == -1)
		{
			vSelectedIndex.setVisibility(View.VISIBLE);
		}
		else
		{
			vSelectedIndex.setVisibility(View.GONE);
		}
		addData();
		
		listView.setAdapter(adapter);
		
		ViewUtil.setListViewHeightBasedOnChildren(listView);
		
		//scrollView.fullScroll(ScrollView.FOCUS_UP);
		scrollView.smoothScrollTo(0,0);
	}

	private void addData()
	{
		ArrayList<CatInfo> datas = new ArrayList<CatInfo>();
		
		CatInfo catInfo1 = new CatInfo("1", "video for kid");
		CatInfo catInfo2 = new CatInfo("2", "song kid");
		CatInfo catInfo3 = new CatInfo("3", "video for kid");
		CatInfo catInfo5 = new CatInfo("4", "video for kid");
		CatInfo catInfo4 = new CatInfo("5", "video for kid");
		CatInfo catInfo6 = new CatInfo("6", "video for kid");
		CatInfo catInfo7 = new CatInfo("7", "video for kid");
		CatInfo catInfo8 = new CatInfo("8", "video for kid");
		CatInfo catInfo9 = new CatInfo("9", "video for kid");
		
		datas.add(catInfo9);
		datas.add(catInfo8);
		datas.add(catInfo7);
		datas.add(catInfo6);
		datas.add(catInfo5);
		datas.add(catInfo4);
		datas.add(catInfo3);
		datas.add(catInfo2);
		datas.add(catInfo1);
		
		adapter = new ItemCatAdapter(getApplicationContext(), datas, application.getCatID());
		
	}
	
	private void addTab() {
		View tabIndicator = LayoutInflater.from(MainActivity.this)
				.inflate(R.layout.tab_tabhost, mTabHost.getTabWidget(), false);
		View tabIndicator1 = LayoutInflater.from(MainActivity.this)
				.inflate(R.layout.tab_tabhost, mTabHost.getTabWidget(), false);
		View tabIndicator2 = LayoutInflater.from(MainActivity.this)
				.inflate(R.layout.tab_tabhost, mTabHost.getTabWidget(), false);
		TextView tvNew = (TextView) tabIndicator.findViewById(R.id.title);
		TextView tvIndex = (TextView) tabIndicator1.findViewById(R.id.title);
		TextView tvlike = (TextView) tabIndicator2.findViewById(R.id.title);

		tvNew.setText("Mới nhất");
		tvIndex.setText("Trang chủ");
		tvlike.setText("Yêu thích");

		TabHost.TabSpec tSpecIndex = mTabHost.newTabSpec("Trang chủ");
		tSpecIndex.setIndicator(tabIndicator1);
		tSpecIndex.setContent(new TabFactory(MainActivity.this));
		mTabHost.addTab(tSpecIndex);

		TabHost.TabSpec tSpecNew = mTabHost.newTabSpec("Mới nhất");
		tSpecNew.setIndicator(tabIndicator);

		tSpecNew.setContent(new TabFactory(MainActivity.this));
		mTabHost.addTab(tSpecNew);

		TabHost.TabSpec tSpecLike = mTabHost.newTabSpec("Yêu thích");
		tSpecLike.setIndicator(tabIndicator2);
		tSpecLike.setContent(new TabFactory(MainActivity.this));
		mTabHost.addTab(tSpecLike);

	}
}
