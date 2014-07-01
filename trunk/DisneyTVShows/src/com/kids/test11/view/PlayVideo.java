package com.kids.test11.view;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bzydroid.network.http.IRequestHandler;
import com.bzydroid.network.http.NetworkResponse;
import com.bzydroid.network.http.client.BzydroidHttpClient;
import com.bzydroid.network.http.exception.BzydroidNetworkExeception;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;
import com.kids.test11.E4KidsConfig;
import com.kids.test11.service.RequestService;
import com.kids.test11.util.Debug;
import com.kids.test11.util.YoutubeV2;
import com.kids.test11.util.YoutubeV2.VideoFetcherDelegate;
import com.kids.test11.util.YoutubeV2.VideoFetcherMobileV2;
import com.kidstv.disneyshows.R;

public class PlayVideo extends Activity implements OnClickListener {

	private VideoView videoView;
	private ProgressBar loading;
	private Handler mHandler;

	private RelativeLayout rlAdsTop;
	// private ImageView ivCancelTop;
	private String linkVideo;
	private String idVideo;
	private AdView adView;
	private ImageView ivCancelTop;
	private RelativeLayout layout_ads_bottom;
	private TextView tvclose;
	private LinearLayout rlContainAds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_play_activity);
		// getSupportActionBar().hide();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
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
				Debug.debug("Tang View thanh cong : "
						+ arg0.getResponse().toString());
			}

			@Override
			public void onStarted(BzydroidHttpClient arg0) {
			}

			@Override
			public void onFinished(BzydroidHttpClient arg0) {
			}

			@Override
			public void onError(BzydroidHttpClient arg0,
					BzydroidNetworkExeception arg1) {
			}
		});

		adView = (AdView) findViewById(R.id.ad);
		adView.setVisibility(View.VISIBLE);
		tvclose = (TextView) findViewById(R.id.tvclose);
		tvclose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rlContainAds.setVisibility(View.GONE);
			}
		});
		rlContainAds = (LinearLayout) findViewById(R.id.rlContainAds);

		adView.setAdListener(new AdListener() {
			@Override
			public void onReceiveAd(Ad arg0) {
				Debug.debug("onReceiveAd");

				if (adView.getVisibility() == View.VISIBLE) {
					rlContainAds.setVisibility(View.GONE);
					tvclose.setVisibility(View.GONE);
				} else {
					rlContainAds.setVisibility(View.GONE);
					tvclose.setVisibility(View.GONE);
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
				tvclose.setVisibility(View.GONE);
			}

			@Override
			public void onDismissScreen(Ad arg0) {
				Debug.debug("onDismissScreen");
			}
		});

		videoView = (VideoView) findViewById(R.id.video_view_play);
		loading = (ProgressBar) findViewById(R.id.progressBarWait);

		final MediaController controller = new MediaController(PlayVideo.this);
		controller.setAnchorView(videoView);

		mHandler = new Handler();

		YoutubeV2.VideoFetcherMobileV2 videoFetcher = new VideoFetcherMobileV2(
				linkVideo, PlayVideo.this);
		videoFetcher.setDelegate(new VideoFetcherDelegate() {

			@Override
			public void onStart() {

			}

			@Override
			public void onFinish(final String link) {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						Debug.debug("Chay vao day...." + link);
						videoView.setMediaController(controller);
						videoView.setVideoURI(Uri.parse(link));

						videoView.requestFocus();
						videoView
								.setOnPreparedListener(new OnPreparedListener() {

									@Override
									public void onPrepared(MediaPlayer mp) {
										loading.setVisibility(View.GONE);
										videoView.start();
									}
								});
						videoView.setOnErrorListener(new OnErrorListener() {

							@Override
							public boolean onError(MediaPlayer mp, int what,
									int extra) {
								onBackPressed();
								Toast.makeText(getApplicationContext(),
										getString(R.string.error_video),
										Toast.LENGTH_SHORT).show();
								return false;
							}
						});

					}
				});
			}

			@Override
			public void onError(Throwable error) {

			}
		});
		videoFetcher.start();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		videoView = null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// if (!ConnectivityUtils.isInternetConnected(getApplicationContext()))
		// {
		// Dialog.dialogNetworkDisconected(PlayVideo.this);
		// return;
		// }
	}

	public void closeAds(View view) {
		rlAdsTop.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		int key = v.getId();
		switch (key) {
		
		default:
			break;
		}
	}
}
