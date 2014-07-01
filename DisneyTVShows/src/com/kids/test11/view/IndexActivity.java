package com.kids.test11.view;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;
import com.ironsource.mobilcore.CallbackResponse;
import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.MobileCore.LOG_TYPE;
import com.kids.test11.E4KidsConfig;
import com.kids.test11.E4KidsSlidingMenuActivity;
import com.kids.test11.KidsTVApplication;
import com.kids.test11.entity.CatInfo;
import com.kids.test11.fragment.SIndexFragment;
import com.kids.test11.fragment.SPlaylistFragment;
import com.kids.test11.util.Debug;
import com.kidstv.disneyshows.R;
import com.viewpagerindicator.TabPageIndicator;

public class IndexActivity extends E4KidsSlidingMenuActivity implements
		OnClickListener {
	private ViewPager pager;

	private ImageView ivABCancelSearch;
	private ImageView ivABSearch;
	private EditText edtABSearch;
	private LinearLayout llContainEdt;
	private TextView tvABTitle;
	private ImageView ivABLogo;
	private AdView adView;
	private ImageView ivShare;

	private KidsTVApplication application;

	private ArrayList<CatInfo> datas;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_tabs);

		this.setBehindContentView(R.layout.frame_layout_container);
		// this.getSlidingMenu().setSecondaryMenu(R.layout.frame_right_container);
		this.setDefaultBar();
		configLeftSlideMenu();
		this.removeSlidingMenu();

		init();

		application = (KidsTVApplication) this.getApplication();

		datas = new ArrayList<CatInfo>();
		datas = application.getDatas();

		FragmentPagerAdapter adapter = new GoogleMusicAdapter(
				getSupportFragmentManager(), datas);

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		pager.setOffscreenPageLimit(application.getDatas().size());

		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);

		// pager.setAdapter(adapter);
		// pager.setOffscreenPageLimit(2);

	}

	private void init() {
		LinearLayout ll=(LinearLayout)findViewById(R.id.rlContainAds);
		
		adView = (AdView) findViewById(R.id.ad);
		ivABCancelSearch = actionBar.getIvABCancelSearch();
		ivABSearch = actionBar.getIvABSearch();
		edtABSearch = actionBar.getEdtABSearch();
		llContainEdt = actionBar.getLlContainEdt();
		ivABLogo = actionBar.getIvABLogo();
		tvABTitle = actionBar.getTvABTitle();
		ivShare = actionBar.getIvShare();

		ivShare.setOnClickListener(this);
		adView.setAdListener(new AdListener() {
			
			@Override
			public void onReceiveAd(Ad arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPresentScreen(Ad arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLeaveApplication(Ad arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDismissScreen(Ad arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		ivABLogo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				toggle();
			}
		});

		// edtABSearch.setOnEditorActionListener(searchAction);
		ivABSearch.setOnClickListener(this);
		ivABCancelSearch.setOnClickListener(this);

		edtABSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		edtABSearch.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int actionID,
					KeyEvent arg2) {
				// RequestUrlService.requestSearchNameSinger(delegateSinger,
				// "tuan hung");
				String text = edtABSearch.getText().toString().trim();
				if (!text.equals("") || !TextUtils.isEmpty(text)) {
					goActivitySearch(IndexActivity.this,
							SearchResultActivity.class,
							E4KidsConfig.KEY_INTENT_SEARCH, text);
					return true;
				}
				return false;
			}
		});

		pager = (ViewPager) findViewById(R.id.cate_info_pager);

	}

	private void hidenDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.iv_search:
			if (llContainEdt.getVisibility() == View.GONE) {
				llContainEdt.setVisibility(View.VISIBLE);
				ivABSearch.setVisibility(View.GONE);
				tvABTitle.setVisibility(View.GONE);
				edtABSearch.setFocusable(true);
				edtABSearch.requestFocus();
				showBoard(edtABSearch);
			}
			break;
		case R.id.iv_cancel:
			String text = edtABSearch.getText().toString().trim();
			if (!text.equals("")) {
				edtABSearch.setText("");
			} else {
				llContainEdt.setVisibility(View.GONE);
				ivABSearch.setVisibility(View.VISIBLE);
				tvABTitle.setVisibility(View.VISIBLE);
				edtABSearch.setFocusable(true);
				hiddenBoard(edtABSearch);
			}
			break;
		
		case R.id.ivShare:
			shareTextUrl(packageName);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		 super.onBackPressed();
		finish();
	}

	class GoogleMusicAdapter extends FragmentPagerAdapter {
		private ArrayList<CatInfo> datas;

		public GoogleMusicAdapter(FragmentManager fm, ArrayList<CatInfo> datas) {
			super(fm);
			this.datas = datas;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment;
			if (datas.get(position).getCatID().equals("-1")) {
				fragment = new SIndexFragment();
			} else {
//				fragment = new SPlaylistFragment(datas.get(position).getCatID());
			    fragment = SPlaylistFragment.newInstance(datas.get(position).getCatID());
			}
			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return datas.get(position).getCatName().toUpperCase();
		}

		@Override
		public int getCount() {
			return datas.size();
		}
	}
}
