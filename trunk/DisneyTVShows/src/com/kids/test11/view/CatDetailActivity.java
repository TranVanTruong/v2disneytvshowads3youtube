package com.kids.test11.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;
import com.kids.test11.E4KidsSlidingMenuActivity;
import com.kids.test11.adapter.DetailVideoPagerAdapter;
import com.kids.test11.fragment.IndexNewFreagment;
import com.kids.test11.fragment.IndexViewFragment;
import com.kids.test11.fragment.PlaylistFragment;
import com.kids.test11.util.Debug;
import com.kids.test11.util.TabFactory;
import com.kidstv.disneyshows.R;

public class CatDetailActivity extends E4KidsSlidingMenuActivity implements
	OnTabChangeListener, OnPageChangeListener, OnClickListener {
    private TabHost mTabHost;
    private ViewPager pager;

    private ImageView ivABCancelSearch;
    private ImageView ivABSearch;
    private EditText edtABSearch;
    private LinearLayout llContainEdt;
    private TextView tvABTitle;
    private ImageView ivABLogo;
    private AdView adView;
    private ImageView ivCloseAds;
    private RelativeLayout rlContainAds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.layout_index);

	// NHGlobal.config(getApplicationContext());
	// setContentView(R.layout.layout_detail_video_pager);

	this.setBehindContentView(R.layout.frame_layout_container);
	// this.getSlidingMenu().setSecondaryMenu(R.layout.frame_right_container);
	this.setDefaultBar();
	configLeftSlideMenu();
	// this.removeSlidingMenu();

	init();

	List<Fragment> listFragments = new ArrayList<Fragment>();
	listFragments.add(new PlaylistFragment());
	listFragments.add(new IndexNewFreagment());
	listFragments.add(new IndexViewFragment());

	DetailVideoPagerAdapter adapter = new DetailVideoPagerAdapter(
		CatDetailActivity.this, getSupportFragmentManager(),
		listFragments);

	pager.setAdapter(adapter);
	pager.setOffscreenPageLimit(2);
	pager.setOnPageChangeListener(this);

    }

    private void init() {
	adView = (AdView) findViewById(R.id.ad);
	ivCloseAds = (ImageView) findViewById(R.id.ivCloseAds);
	rlContainAds = (RelativeLayout)findViewById(R.id.rlContainAds);
	
	ivCloseAds.setOnClickListener(this);

	adView.setAdListener(new AdListener() {
		@Override
		public void onReceiveAd(Ad arg0) {
			Debug.debug("onReceiveAd");

			if (adView.getVisibility() == View.VISIBLE) {
				ivCloseAds.setVisibility(View.VISIBLE);
				rlContainAds.setVisibility(View.VISIBLE);
			} else {
				ivCloseAds.setVisibility(View.GONE);
				rlContainAds.setVisibility(View.GONE);
			}
		}

		@Override
		public void onPresentScreen(Ad arg0) {
			Debug.debug("onPresentScreen");
		}

		@Override
		public void onLeaveApplication(Ad arg0) {
			Debug.debug("onLeaveApplication");
		}

		@Override
		public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
			Debug.debug("onFailedToReceiveAd");
			ivCloseAds.setVisibility(View.GONE);
		}

		@Override
		public void onDismissScreen(Ad arg0) {
			Debug.debug("onDismissScreen");
		}
	});
	
	ivABCancelSearch = actionBar.getIvABCancelSearch();
	ivABSearch = actionBar.getIvABSearch();
	edtABSearch = actionBar.getEdtABSearch();
	llContainEdt = actionBar.getLlContainEdt();
	ivABLogo = actionBar.getIvABLogo();
	tvABTitle = actionBar.getTvABTitle();
	
	ivABLogo.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View arg0) {
		toggle();
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
		    goActivitySearch(CatDetailActivity.this,
			    CatDetailActivity.class, "123", text);
		}
		return true;
	    }
	});

	mTabHost = (TabHost) findViewById(android.R.id.tabhost);
	mTabHost.setOnTabChangedListener(this);
	pager = (ViewPager) findViewById(R.id.cate_info_pager);

	mTabHost.setup();
	addTab();
    }

    private void addTab() {
	View tabIndicator = LayoutInflater.from(CatDetailActivity.this).inflate(
		R.layout.tab_tabhost, mTabHost.getTabWidget(), false);
	View tabIndicator1 = LayoutInflater.from(CatDetailActivity.this).inflate(
		R.layout.tab_tabhost, mTabHost.getTabWidget(), false);
	View tabIndicator2 = LayoutInflater.from(CatDetailActivity.this).inflate(
		R.layout.tab_tabhost, mTabHost.getTabWidget(), false);
	TextView tvNew = (TextView) tabIndicator.findViewById(R.id.title);
	TextView tvIndex = (TextView) tabIndicator1.findViewById(R.id.title);
	TextView tvlike = (TextView) tabIndicator2.findViewById(R.id.title);

	tvNew.setText(getString(R.string.tab_new));
	tvIndex.setText(getString(R.string.tab_playlist));
	tvlike.setText(getString(R.string.tab_view));

	TabHost.TabSpec tSpecIndex = mTabHost.newTabSpec(getString(R.string.tab_playlist));
	tSpecIndex.setIndicator(tabIndicator1);
	tSpecIndex.setContent(new TabFactory(CatDetailActivity.this));
	mTabHost.addTab(tSpecIndex);

	TabHost.TabSpec tSpecNew = mTabHost.newTabSpec(getString(R.string.tab_new));
	tSpecNew.setIndicator(tabIndicator);

	tSpecNew.setContent(new TabFactory(CatDetailActivity.this));
	mTabHost.addTab(tSpecNew);

	TabHost.TabSpec tSpecLike = mTabHost.newTabSpec(getString(R.string.tab_view));
	tSpecLike.setIndicator(tabIndicator2);
	tSpecLike.setContent(new TabFactory(CatDetailActivity.this));
	mTabHost.addTab(tSpecLike);

    }

    @Override
    public void onTabChanged(String arg0) {
	int pos = this.mTabHost.getCurrentTab();
	this.pager.setCurrentItem(pos);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
	if (arg0 == 0) {
	    enableSlidingMenu();
	} else {
	    this.removeSlidingMenu();
	}
	this.mTabHost.setCurrentTab(arg0);
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
	    case R.id.ivCloseAds:
		rlContainAds.setVisibility(View.GONE);
		break;
	}
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        app.setCatID(-1);
    }
}
