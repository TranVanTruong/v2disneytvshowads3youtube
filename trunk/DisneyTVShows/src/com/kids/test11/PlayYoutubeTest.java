package com.kids.test11;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.kidstv.disneyshows.R;

public class PlayYoutubeTest extends YouTubeFailureRecoveryActivity implements
		OnFullscreenListener {

	private boolean fullscreen;
	private YouTubePlayerView youTubeView;
	private RelativeLayout rlAds;
	private LinearLayout baseLayout;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_detail_video);

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
			
			arg1.loadPlaylist("PL9C2D08CA03BB249A");
//			arg1.loadVideo("SPn106LGnjU");
			
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

}
