package com.kids.test11.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bzydroid.network.http.AbsRequestHandler;
import com.bzydroid.network.http.NetworkResponse;
import com.bzydroid.network.http.client.BzydroidHttpClient;
import com.bzydroid.network.http.exception.BzydroidNetworkExeception;
import com.bzydroid.network.http.response.HttpJSONObjectResponse;
import com.bzydroid.utils.BzydroidLog;
import com.kids.test11.E4KidsConfig;
import com.kids.test11.SBaseFragment;
import com.kids.test11.adapter.AlbumIndexFragmentAdapter;
import com.kids.test11.adapter.SlideShowImageAdapter;
import com.kids.test11.adapter.VideoIndexFragmentAdapter;
import com.kids.test11.adapter.AlbumIndexFragmentAdapter.IClickPla;
import com.kids.test11.adapter.SlideShowImageAdapter.setOnClickItemListener;
import com.kids.test11.adapter.VideoIndexFragmentAdapter.ClickItemVideo;
import com.kids.test11.customize.CustomGridView;
import com.kids.test11.customize.NotInterceptInPager;
import com.kids.test11.entity.InfoAds;
import com.kids.test11.entity.InfoAlbum;
import com.kids.test11.entity.InfoVideo;
import com.kids.test11.service.RequestService;
import com.kids.test11.util.Debug;
import com.kids.test11.util.Util;
import com.kids.test11.view.ListVideoByPlaActivity;
import com.kids.test11.view.PlayVideo;
import com.kidstv.disneyshows.R;
import com.viewpagerindicator.CirclePageIndicator;

public class SIndexFragment extends SBaseFragment implements
		OnPageChangeListener {

	private VideoIndexFragmentAdapter adapterVideo;
	private AlbumIndexFragmentAdapter adapterAlbum;
	private SlideShowImageAdapter adapterAds;

	// private ArrayList<InfoVideo> dataAds;
	private ArrayList<InfoVideo> dataVideo;
	private ArrayList<InfoAlbum> dataPlaylist;

	private NotInterceptInPager viewPager;
	private CustomGridView gvVideo;
	private CustomGridView gvAlbum;
	private CirclePageIndicator mIndicator;
	private RelativeLayout rlContainPager;

	private TextView tvTitleVideo;
	private TextView tvTitlePla;

	private LinearLayout llLoading;
	private LinearLayout llNotInternet;

	private ScrollView scroller;

	private View root;

	private Handler slideHandler;

	private boolean isTouch = false;
	private int pageViewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_index, null);
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		init();
	}

	private void init() {
		slideHandler = new Handler();

		viewPager = (NotInterceptInPager) root.findViewById(R.id.viewpager);

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;

		int widthImage = width
				- 2
				* (getResources()
						.getDimensionPixelOffset(R.dimen.item_video_padding));
		int heightImage = (widthImage * 67) / 100;

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				widthImage, heightImage);

		int marginPager = getResources().getDimensionPixelOffset(
				R.dimen.item_video_padding);
		params.setMargins(marginPager, 0, marginPager, 0);
		viewPager.setLayoutParams(params);

		gvAlbum = (CustomGridView) root.findViewById(R.id.gvAlbum);
		gvVideo = (CustomGridView) root.findViewById(R.id.gvVideo);
		mIndicator = (CirclePageIndicator) root
				.findViewById(R.id.index_layout_indicator);
		tvTitlePla = (TextView) root.findViewById(R.id.tvTitlePla);
		tvTitleVideo = (TextView) root.findViewById(R.id.tvTitleVideo);

		scroller = (ScrollView) root.findViewById(R.id.index_layout_scroller);

		llLoading = (LinearLayout) root.findViewById(R.id.ll_loading);
		llNotInternet = (LinearLayout) root.findViewById(R.id.llNoInternet);

		rlContainPager = (RelativeLayout) root
				.findViewById(R.id.index_layout_viewpager);

		// dataAds = new ArrayList<InfoVideo>();
		dataPlaylist = new ArrayList<InfoAlbum>();
		dataVideo = new ArrayList<InfoVideo>();

		adapterAds = new SlideShowImageAdapter(getActivity(), imageLoader,
				options);

		RequestService.rqHomeIndex(packageName, new RequestHome());

		Debug.debug("PackageName : " + packageName);

		viewPager.setAdapter(adapterAds);
		mIndicator.setViewPager(viewPager);

		adapterAds.setOnClickItemListener(new setOnClickItemListener() {

			@Override
			public void onCallClick(boolean isPla, String vid, String linkVideo) {
				Debug.debug("IsPlay : " + isPla + "\nID : " + vid);
				if (isPla) {
					Bundle bundle = new Bundle();
					bundle.putString(E4KidsConfig.KEY_INTENT_AID, vid);

					gotoActivity(ListVideoByPlaActivity.class, bundle);
				} else {
					Bundle bundle = new Bundle();
					bundle.putString(E4KidsConfig.KEY_INTENT_VID, vid);
					bundle.putString(E4KidsConfig.KEY_INTENT_LINK, linkVideo);

					gotoActivity(PlayVideo.class, bundle);
				}
			}

		});
		pageViewPager = 0;
		updateUI();

		// viewPager.setOnPageChangeListener(this);

		viewPager.setOnTouchListener(onSlideTouch);
	}

	/** Event to handle slide-show when user on touch */
	private OnTouchListener onSlideTouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				isTouch = true;
				// Remove runnable when moving
				slideHandler.removeCallbacks(mRunnable);
				break;
			case MotionEvent.ACTION_UP:
				isTouch = false;
				// Repost thread
				slideHandler.postDelayed(mRunnable, 2000);
				break;

			}
			return false;
		}
	};

	/** Runnable to auto sliding */
	private Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			if (!isTouch) {
				pageViewPager += 1;
				if (pageViewPager == adapterAds.getCount()) {
					pageViewPager = 0;
				}
				viewPager.setCurrentItem(pageViewPager, true);
			}
			slideHandler.postDelayed(mRunnable, 3000);
		}
	};

	private void updateUI() {
		new Handler().postDelayed(mRunnable, 3000);
	}

	private class RequestHome extends AbsRequestHandler {
		private InfoAds infoAds;

		@Override
		public void onWait(BzydroidHttpClient arg0) {
			super.onWait(arg0);
			llLoading.setVisibility(View.VISIBLE);

			if (!Util.isConnectingToInternet(getActivity())) {
				scroller.setVisibility(View.GONE);
				llNotInternet.setVisibility(View.VISIBLE);
				if (llLoading.getVisibility() == View.VISIBLE) {
					llLoading.setVisibility(View.GONE);
				}
			} else {
				scroller.setVisibility(View.VISIBLE);
				llNotInternet.setVisibility(View.GONE);
			}
		}

		@Override
		public void onStarted(BzydroidHttpClient arg0) {
			super.onStarted(arg0);
			BzydroidLog.debug("onStarted");
		}

		@Override
		public void onSuccess(BzydroidHttpClient arg0, NetworkResponse arg1) {

			HttpJSONObjectResponse jsonObjectResponse = (HttpJSONObjectResponse) arg0
					.getResponse();

			try {
				JSONObject object = jsonObjectResponse.toJSONObject();

				Debug.debug("Du lieu Response : " + object.toString());

				JSONObject jObjData = object
						.getJSONObject(E4KidsConfig.KEY_DATA);

				int code = jObjData.getInt(E4KidsConfig.KEY_CODE);

				if (code == 1) {
					JSONArray jArrAds = jObjData
							.getJSONArray(E4KidsConfig.Home.KEY_ADS);

					JSONArray jArrVideo = jObjData
							.getJSONArray(E4KidsConfig.Home.KEY_VIDEO);

					JSONArray jArrPlaylist = jObjData
							.getJSONArray(E4KidsConfig.Home.KEY_PLAYLIST_INDEX);

					JSONArray jArrAdsExit = jObjData
							.getJSONArray(E4KidsConfig.Home.KEY_ADS_EXIT);

					application.setjArrAdsExit(jArrAdsExit);

					Debug.debug("jArrAds : " + jArrAds.toString());
					Debug.debug("jArrVideo : " + jArrVideo.toString());
					Debug.debug("jArrPlaylist : " + jArrPlaylist.toString());

					int lengthAds = jArrAds.length();
					int lengthVideo = jArrVideo.length();
					int lengthPlaylist = jArrPlaylist.length();

					if (lengthAds <= 0) {
						rlContainPager.setVisibility(View.GONE);
					} else {
						rlContainPager.setVisibility(View.VISIBLE);

						for (int i = 0; i < lengthAds; i++) {
							JSONObject objectAds = jArrAds.getJSONObject(i);

							String id = objectAds.getString("id");
							String title = objectAds.getString("title");
							String image = objectAds.getString("image");
							int type = objectAds.getInt("type");
							if (type == 0) {
								infoAds = new InfoAds(id, title, image, type,
										"");
							} else if (type == 1) {
								String idYoutube = objectAds
										.getString("id_youtube");
								infoAds = new InfoAds(id, title, image, type,
										idYoutube);
							}
							adapterAds.add(infoAds);
						}
					}

					if (lengthVideo <= 0) {
						gvVideo.setVisibility(View.GONE);
						tvTitleVideo.setVisibility(View.GONE);
					} else {
						gvVideo.setVisibility(View.GONE);
						tvTitleVideo.setVisibility(View.GONE);

						for (int i = 0; i < lengthVideo; i++) {
							JSONObject objectVideo = jArrVideo.getJSONObject(i);

							InfoVideo infoVideo = parseJsonVideo(objectVideo);

							dataVideo.add(infoVideo);
						}
						adapterVideo = new VideoIndexFragmentAdapter(
								getActivity(), dataVideo, imageLoader, options);
						gvVideo.setAdapter(adapterVideo);

						adapterVideo.setIclickItemVideo(new ClickItemVideo() {

							@Override
							public void clickItem(String vid, String id) {
								Bundle bundle = new Bundle();
								bundle.putString(E4KidsConfig.KEY_INTENT_VID,
										vid);
								bundle.putString(E4KidsConfig.KEY_INTENT_LINK,
										id);

								gotoActivity(PlayVideo.class, bundle);
							}
						});

					}

					if (lengthPlaylist <= 0) {
						gvAlbum.setVisibility(View.GONE);
						tvTitlePla.setVisibility(View.GONE);
					} else {
						gvAlbum.setVisibility(View.VISIBLE);
						tvTitlePla.setVisibility(View.VISIBLE);

						for (int i = 0; i < lengthPlaylist; i++) {
							JSONObject objectPla = jArrPlaylist
									.getJSONObject(i);

							InfoAlbum infoAlbum = parseJsonPla(objectPla, "");

							dataPlaylist.add(infoAlbum);
						}
						adapterAlbum = new AlbumIndexFragmentAdapter(
								getActivity(), dataPlaylist, imageLoader,
								options);
						gvAlbum.setAdapter(adapterAlbum);

						adapterAlbum.setClickPla(new IClickPla() {

							@Override
							public void clickItemPla(String id) {
								
								if(!TextUtils.isEmpty(id)){
									 Bundle b = new Bundle();
										
									 b.putString(E4KidsConfig.KEY_INTENT_AID, id);
									 gotoActivity(ListVideoByPlaActivity.class, b);
								}
								// application.setCatID(Integer.valueOf(id));
								//
								// Intent intent = new Intent(getActivity(),
								// CatDetailActivity.class);
								// startActivity(intent);
								// application.setIndex(false);
								// if (!application.isIndex()) {
								// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								// application.setIndex(false);
								// startActivity(intent);
								// } else {
								// application.setIndex(false);
								//
								// }

								// getActivity().finish();

								// a
								// Bundle bundle = new Bundle();
								// bundle.putString(E4KidsConfig.KEY_INTENT_AID,
								// id);
								//
								// gotoActivity(ListVideoByPlaActivity.class,
								// bundle);
							}
						});
					}

					scroller.setVisibility(View.VISIBLE);
					scroller.smoothScrollTo(0, 0);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onError(BzydroidHttpClient arg0,
				BzydroidNetworkExeception arg1) {
			super.onError(arg0, arg1);
			BzydroidLog.debug("onError");

			if (!Util.isConnectingToInternet(getActivity())) {
				scroller.setVisibility(View.GONE);
				llNotInternet.setVisibility(View.VISIBLE);
				if (llLoading.getVisibility() == View.VISIBLE) {
					llLoading.setVisibility(View.GONE);
				}
			} else {
				scroller.setVisibility(View.VISIBLE);
				llNotInternet.setVisibility(View.GONE);
			}
		}

		@Override
		public void onFinished(BzydroidHttpClient arg0) {
			super.onFinished(arg0);
			BzydroidLog.debug("onFinished");
			llLoading.setVisibility(View.GONE);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		Debug.debug("onPageScrollStateChanged ");
		// activity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		Debug.debug("onPageScrolled ");
	}

	@Override
	public void onPageSelected(int arg0) {
		Debug.debug("Page change : " + arg0);
		// if(arg0 == 0)
		// {
		// activity.enableSlidingMenu();
		// }
		// else
		// {
		// activity.removeSlidingMenu();
		// }
	}

}
