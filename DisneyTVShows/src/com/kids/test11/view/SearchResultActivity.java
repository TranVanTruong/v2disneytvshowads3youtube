package com.kids.test11.view;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.bzydroid.network.http.NetworkResponse;
import com.bzydroid.network.http.client.BzydroidHttpClient;
import com.bzydroid.network.http.exception.BzydroidNetworkExeception;
import com.bzydroid.network.http.response.HttpJSONObjectResponse;
import com.google.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.ironsource.mobilcore.CallbackResponse;
import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.MobileCore.LOG_TYPE;
import com.kids.test11.E4KidsConfig;
import com.kids.test11.E4KidsSlidingMenuActivity;
import com.kids.test11.adapter.ItemVideoRelativeAdapter;
import com.kids.test11.adapter.ItemVideoRelativeAdapter.IClickItemVideo;
import com.kids.test11.adapter.ItemVideoRelativeAdapterPlay;
import com.kids.test11.customize.BaseRequestHandler;
import com.kids.test11.entity.InfoVideo;
import com.kids.test11.service.RequestService;
import com.kids.test11.util.Debug;
import com.kids.test11.util.Util;
import com.kidstv.disneyshows.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class SearchResultActivity extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener ,OnClickListener {
//    private ListView lvVideo;
	GridView gr_Video;
    private LinearLayout llLoading;
    private LinearLayout llNotInternet;
    private ItemVideoRelativeAdapter adapter;

    private boolean isLoading;
    private RequestVideoNew requestVideoNew;

    private int page = 1;

    private ImageView ivABCancelSearch;
    private ImageView ivABSearch;
    private EditText edtABSearch;
    private LinearLayout llContainEdt;
    private TextView tvABTitle;
    private LinearLayout llLoadingFooter;
    private ImageView ivShare;
    
    private String keyword;
    private TextView tvNoData;
    private ImageView ivABLogo;
//customvideo
    ScrollView sl;
	public static final String API_KEY = "AIzaSyDt9Lnv1gCU_7gxmtRarZkfQ7ZZn13EXzs";
	private YouTubePlayer youTubePlayer;
	private YouTubePlayerView youTubePlayerView;
	private MyPlayerStateChangeListener myPlayerStateChangeListener;
	private MyPlaybackEventListener myPlaybackEventListener;
	InfoVideo lsinfoVideo;
	String log = "";
	protected String packageName;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected DisplayImageOptions options;
	InfoVideo infoVideo;
	private AdView ad;
    @Override
    public void onCreate(Bundle arg0) {
	super.onCreate(arg0);
	this.setContentView(R.layout.activity_list_relative);
	packageName = getApplicationContext().getPackageName();
	File cacheDir = StorageUtils.getOwnCacheDirectory(this,
			"SongKids/imageCache");

	options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_launcher)
			.showImageForEmptyUri(R.drawable.ic_launcher)
			.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
			.cacheOnDisc(true).build();
	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
			this)
			.threadPoolSize(3)
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.memoryCache(new UsingFreqLimitedMemoryCache(2000000))
			.discCache(new UnlimitedDiscCache(cacheDir))
			.memoryCacheSize(1500000)
			// 1.5 Mb

			.denyCacheImageMultipleSizesInMemory()
			.discCache(new UnlimitedDiscCache(cacheDir))
			.discCacheFileNameGenerator(new Md5FileNameGenerator()).build();

	imageLoader.init(config);
	init();
	youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeplayerview);
	youTubePlayerView.initialize(API_KEY, this);
	myPlayerStateChangeListener = new MyPlayerStateChangeListener();
	myPlaybackEventListener = new MyPlaybackEventListener();
	Debug.debug("Package Name : " + packageName);
    }

    private void init() {
    	ivABSearch=(ImageView)findViewById(R.id.iv_search);
    	ivShare=(ImageView)findViewById(R.id.ivShare);
    	ivABSearch.setVisibility(View.GONE);
    	ivShare.setVisibility(View.GONE);
	if(getIntent().getExtras() != null)
	{
	    keyword = getIntent().getExtras().getString(E4KidsConfig.KEY_INTENT_SEARCH);
	}
	
//	ivABCancelSearch = actionBar.getIvABCancelSearch();
//	ivABSearch = actionBar.getIvABSearch();
//	edtABSearch = actionBar.getEdtABSearch();
//	llContainEdt = actionBar.getLlContainEdt();
//	ivABLogo = actionBar.getIvABLogo();
//	tvABTitle = actionBar.getTvABTitle();
//	ivShare = actionBar.getIvShare();
//	
//	ivShare.setVisibility(View.GONE);
//	
//	llContainEdt.setVisibility(View.VISIBLE);
//	ivABSearch.setVisibility(View.GONE);
//	edtABSearch.setText(keyword);
//	tvABTitle.setVisibility(View.GONE);
//	
//	ivABLogo.setOnClickListener(new OnClickListener() {
//	    
//	    @Override
//	    public void onClick(View arg0) {
//		onBackPressed();
//	    }
//	});
//
//	// edtABSearch.setOnEditorActionListener(searchAction);
//	ivABSearch.setOnClickListener(this);
//	ivABCancelSearch.setOnClickListener(this);
//
//	edtABSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
//	edtABSearch.setOnEditorActionListener(new OnEditorActionListener() {
//
//	    @Override
//	    public boolean onEditorAction(TextView arg0, int actionID,
//		    KeyEvent arg2) {
//		keyword = edtABSearch.getText().toString().trim();
//		if (!keyword.equals("") || !TextUtils.isEmpty(keyword)) {
//		    tvNoData.setVisibility(View.GONE);
//		    llLoading.setVisibility(View.VISIBLE);
//		    gr_Video.setVisibility(View.GONE);
//		    adapter = new ItemVideoRelativeAdapter(SearchResultActivity.this, imageLoader , options);
//		    page = 1;
//		    loadMore();
//		    
//		    gr_Video.setAdapter(adapter);
//		}
//		return true;
//	    }
//	});

	gr_Video = (GridView) findViewById(R.id.gv_video);
	
	tvNoData = (TextView)findViewById(R.id.tvNoData);

	 LayoutInflater inflater = getLayoutInflater();
	 View header = inflater.inflate(R.layout.layout_header, null);
	 View footer = inflater.inflate(R.layout.layout_footer, null);
	 llLoadingFooter = (LinearLayout) footer
			.findViewById(R.id.llLoadingFooter);
//	 lvVideo.addHeaderView(header);
//	 lvVideo.addFooterView(footer);

	llLoading = (LinearLayout) findViewById(R.id.ll_loading);
	llNotInternet = (LinearLayout) findViewById(R.id.llNoInternet);

	adapter = new ItemVideoRelativeAdapter(SearchResultActivity.this, imageLoader , options);

	// idCat = String.valueOf(application.getCatID());

	// if(idCat.equals("") || idCat.equals("0")|| idCat.equals("-1"))
	// {
	// idCat = "0";
	// }
	//
	// Debug.debug("CatId New Video : " + idCat);

	loadMore();
	gr_Video.setAdapter(adapter);
	gr_Video.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int po,
				long arg3) {
		 infoVideo = adapter.getItem(po);
			youTubePlayer.loadVideo(infoVideo.getvYoutube());
		}
	});
	
	gr_Video.setOnScrollListener(new OnScrollListener() {

	    @Override
	    public void onScrollStateChanged(AbsListView arg0, int arg1) {

	    }

	    @Override
	    public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		if ((arg1 + arg2) == arg3 && isLoading) {
		    if (page <= 5) {
			isLoading = false;
			page = page + 1;
			llLoadingFooter.setVisibility(View.VISIBLE);

			loadMore();

			Debug.debug("Page New: " + page);
		    }
		}
	    }
	});
    }

    private class RequestVideoNew extends BaseRequestHandler {
	public RequestVideoNew(Activity context, OnClickListener clickTry) {
	    super(context, clickTry);
	}

	@Override
	public void onWait(BzydroidHttpClient arg0) {
	    super.onWait(arg0);
//	    llLoading.setVisibility(View.VISIBLE);
	}

	@Override
	public void onStarted(BzydroidHttpClient arg0) {
	    super.onStarted(arg0);
	    Debug.debug("onStarted");
	}

	@Override
	public void onSuccess(BzydroidHttpClient arg0, NetworkResponse arg1) {

	    // Debug.debug("Danh sach : " + a);
	    HttpJSONObjectResponse jsonObjectResponse = (HttpJSONObjectResponse) arg0
		    .getResponse();
	    try {
		JSONObject object = jsonObjectResponse.toJSONObject();
		//
		Debug.debug("Danh sach : " + object.toString());

		JSONObject jObjData = object
			.getJSONObject(E4KidsConfig.KEY_DATA);

		int code = jObjData.getInt(E4KidsConfig.KEY_CODE);

		if (code == 1) {
		    Debug.debug("Con du lieu");

		    isLoading = true;

		    JSONArray jArrVideo = jObjData
			    .getJSONArray(E4KidsConfig.Search.KEY_VIDEO);

		    int length = jArrVideo.length();

		    for (int i = 0; i < length; i++) {
			JSONObject jObj = jArrVideo.getJSONObject(i);

			 infoVideo = parseJsonSearch(jObj);

			adapter.addData(infoVideo);
		    }
		    gr_Video.setVisibility(View.VISIBLE);
		} else {
		    Debug.debug("Het du lieu");
		    isLoading = false;
		    if (adapter.getCount() <= 0) {
			tvNoData.setVisibility(View.VISIBLE);
			gr_Video.setVisibility(View.GONE);
			llLoadingFooter.setVisibility(View.GONE);
		    } else {
			llLoadingFooter.setVisibility(View.GONE);
			tvNoData.setVisibility(View.GONE);
		    }
		}
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	}

	@Override
	public void onError(BzydroidHttpClient arg0,
		BzydroidNetworkExeception arg1) {
	    super.onError(arg0, arg1);
	    Debug.debug("onError");

	    if (!Util.isConnectingToInternet(SearchResultActivity.this)) {
	    	gr_Video.setVisibility(View.GONE);
		if (llLoading.getVisibility() == View.VISIBLE) {
		    llLoading.setVisibility(View.GONE);
		    llNotInternet.setVisibility(View.VISIBLE);
		}
	    } else {
		llNotInternet.setVisibility(View.GONE);
		gr_Video.setVisibility(View.VISIBLE);
	    }
	}

	@Override
	public void onFinished(BzydroidHttpClient arg0) {
	    super.onFinished(arg0);
	    Debug.debug("onFinished");
	    llLoading.setVisibility(View.GONE);
	    llLoadingFooter.setVisibility(View.GONE);
	}
    }

    private void loadMore() {
	if (requestVideoNew == null) {
	    requestVideoNew = new RequestVideoNew(SearchResultActivity.this,
		    clickTry);
	}
	RequestService.rqSearch(keyword , String.valueOf(page), packageName,
		requestVideoNew);

    }

    private OnClickListener clickTry = new OnClickListener() {

	@Override
	public void onClick(View v) {
	    Debug.debug("clickTry Page : " + page);

	    loadMore();
	}
    };

    @Override
    public void onClick(View arg0) {
	switch (arg0.getId()) {
	}
    }
    @Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult result) {
	}

	@Override
	public void onInitializationSuccess(Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		youTubePlayer = player;
		youTubePlayer.setPlayerStateChangeListener(myPlayerStateChangeListener);
		youTubePlayer.setPlaybackEventListener(myPlaybackEventListener);
		if (!wasRestored && isLoading==true) {
			player.cueVideo(infoVideo.getvYoutube());
			
		}

	}

	private final class MyPlayerStateChangeListener implements
			PlayerStateChangeListener {

		private void updateLog(String prompt) {
		};

		@Override
		public void onAdStarted() {
		}

		@Override
		public void onError(
				com.google.android.youtube.player.YouTubePlayer.ErrorReason arg0) {
		}

		@Override
		public void onLoaded(String arg0) {
		}

		@Override
		public void onLoading() {
		}

		@Override
		public void onVideoEnded() {
		}

		@Override
		public void onVideoStarted() {
		}

	}

	private final class MyPlaybackEventListener implements
			PlaybackEventListener {

		private void updateLog(String prompt) {
		};

		@Override
		public void onBuffering(boolean arg0) {
		}

		@Override
		public void onPaused() {
		}

		@Override
		public void onPlaying() {

		}

		@Override
		public void onSeekTo(int arg0) {
		}

		@Override
		public void onStopped() {
		}

	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.WRAP_CONTENT);
			youTubePlayer.play();
//			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//					WindowManager.LayoutParams.FLAG_FULLSCREEN);
//			ad.setLayoutParams(layoutParams);
			gr_Video.setVisibility(View.GONE);
		} else {
			gr_Video.setVisibility(View.VISIBLE);
		}}
	protected InfoVideo parseJsonSearch(JSONObject object) {
		 infoVideo = null;
		try {
			String idYoutube = object
					.getString(E4KidsConfig.KeyVideo.KEY_ID_YOU);
			String title = object.getString(E4KidsConfig.KeyVideo.KEY_TITLE);
			String duration = object
					.getString(E4KidsConfig.KeyVideo.KEY_DURATION);
			String view = object.getString(E4KidsConfig.KeyVideo.KEY_VIEW);
			String avatar = object.getString(E4KidsConfig.KeyVideo.KEY_IMAGE);

			infoVideo = new InfoVideo("", idYoutube, title, avatar, "", view,
					duration, "");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return infoVideo;
	}

}
