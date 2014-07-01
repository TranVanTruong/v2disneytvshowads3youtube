package com.kids.test11.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bzydroid.network.http.IRequestHandler;
import com.bzydroid.network.http.NetworkResponse;
import com.bzydroid.network.http.client.BzydroidHttpClient;
import com.bzydroid.network.http.exception.BzydroidNetworkExeception;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.kids.test11.E4KidsConfig;
import com.kids.test11.YouTubeFailureRecoveryActivity;
import com.kids.test11.service.RequestService;
import com.kids.test11.util.Debug;
import com.kidstv.disneyshows.R;

public class PlayVideoYoutbe extends YouTubeFailureRecoveryActivity implements
		OnFullscreenListener, OnClickListener {

	private boolean fullscreen;
	private YouTubePlayerView youTubeView;
	private RelativeLayout rlAds;
	private LinearLayout baseLayout;
	private ImageView ivCancel;
	private AdView adView;
	
	private String linkVideo;
	private String idVideo;
	
	private RelativeLayout rlContainAds;
	private ImageView ivCloseAds;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_detail_video);
		
		Bundle bundle = getIntent().getExtras();
		
		if(bundle != null)
		{
		    linkVideo = bundle.getString(E4KidsConfig.KEY_INTENT_LINK);
		    idVideo = bundle.getString(E4KidsConfig.KEY_INTENT_VID);
		    
		    Debug.debug("Link Video : " + linkVideo);
		}
		
		RequestService.rqTangView(idVideo, new IRequestHandler() {
		    
		    @Override
		    public void onWait(BzydroidHttpClient arg0) {
		    }
		    
		    @Override
		    public void onSuccess(BzydroidHttpClient arg0, NetworkResponse arg1) {
			Debug.debug("Tang View thanh cong : " + arg0.getResponse().toString());
		    }
		    
		    @Override
		    public void onStarted(BzydroidHttpClient arg0) {
		    }
		    
		    @Override
		    public void onFinished(BzydroidHttpClient arg0) {
		    }
		    
		    @Override
		    public void onError(BzydroidHttpClient arg0, BzydroidNetworkExeception arg1) {
		    }
		});
		
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
		
		baseLayout = (LinearLayout)findViewById(R.id.layout);

		youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
		youTubeView.initialize(E4KidsConfig.API_YOUTUBE, this);
		
	}

	@Override
	public void onInitializationSuccess(Provider arg0, YouTubePlayer arg1,
			boolean arg2) {
		if (!arg2) {
			
			arg1.setOnFullscreenListener(this);
			arg1.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
			
//			arg1.loadPlaylist("PL9C2D08CA03BB249A");
			arg1.loadVideo(linkVideo);
			
			arg1.setPlayerStyle(PlayerStyle.DEFAULT);
			
//			arg1.
			
		}
	}

	@Override
	protected Provider getYouTubePlayerProvider() {
		return (YouTubePlayerView) findViewById(R.id.youtube_view);
	}

	@Override
	public void onFullscreen(boolean arg0) {
		if(arg0)
		{
			rlAds.setVisibility(View.GONE);
		}
		else
		{
			rlAds.setVisibility(View.VISIBLE);
		}
	}

	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onClick(View v) {
	    if(v == ivCloseAds)
	    {
		rlContainAds.setVisibility(View.GONE);
	    }
	}

}
