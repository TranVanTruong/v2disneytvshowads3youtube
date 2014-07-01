package com.kids.test11;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.bzydroid.app.SlidingFragmentActivity;
import com.bzydroid.network.http.IRequestHandler;
import com.bzydroid.network.http.NetworkResponse;
import com.bzydroid.network.http.client.BzydroidHttpClient;
import com.bzydroid.network.http.exception.BzydroidNetworkExeception;
import com.bzydroid.network.http.exception.BzydroidProcessResponseException;
import com.bzydroid.network.http.exception.BzydroidServerException;
import com.bzydroid.utils.BzydroidLog;
import com.bzydroid.utils.Device;
import com.bzydroid.view.SlidingMenu;
import com.bzydroid.view.SlidingMenu.CanvasTransformer;
import com.kids.test11.customize.ActionBarView;
import com.kids.test11.entity.InfoAlbum;
import com.kids.test11.entity.InfoVideo;
import com.kids.test11.fragment.SlideLeftFragment;
import com.kids.test11.util.Debug;
import com.kidstv.disneyshows.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * CucRe Base activity class. <br/>
 * All activity class for this project must extend from it
 * 
 * @author HUNGTDO
 * */
@SuppressLint("SetJavaScriptEnabled")
public class E4KidsSlidingMenuActivity extends SlidingFragmentActivity {

	protected ActionBarView actionBar = null;

	protected E4KidsSlidingMenuActivity self;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	protected KidsTVApplication app;

	protected ProgressDialog progressDialog = null;

	protected Dialog dialog = null;

	protected DisplayImageOptions options;

	protected String packageName;

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		self = this;
		app = getCucReApplication();
		packageName = getApplicationContext().getPackageName();
		// app.setPackageName(getApplicationContext().getPackageName());
		//
		Debug.debug("Package Name : " + packageName);

		File cacheDir = StorageUtils.getOwnCacheDirectory(this,
				"SongKids/imageCache");

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisc(true)
				// .considerExifParams(true)
				.build();
		// //
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

	}

	@Override
	public void setBehindContentView(final int resId) {
		this.setStyleForSlideMenu();
		super.setBehindContentView(resId);
	}

	@Override
	public void setBehindContentView(final View view) {
		this.setStyleForSlideMenu();
		super.setBehindContentView(view);
	}

	@Override
	public void setBehindContentView(final View arg0,
			final android.view.ViewGroup.LayoutParams arg1) {
		this.setStyleForSlideMenu();
		super.setBehindContentView(arg0, arg1);
	}

	public static int getSlidingBehindOffSet(Context context) {
		return Device.getScreenWidth(context) / 5;
	}

	/**
	 * Remove sliding menu
	 * */
	public void removeSlidingMenu() {
		this.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
	}

	public void enableSlidingMenu() {
		this.getSlidingMenu().setTouchModeAbove(
				SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	private void setStyleForSlideMenu() {
		this.getSlidingMenu().setBehindOffset(getSlidingBehindOffSet(self));
		this.getSlidingMenu().setMode(SlidingMenu.LEFT);
		this.getSlidingMenu().setTouchModeAbove(
				SlidingMenu.TOUCHMODE_FULLSCREEN);
		this.getSlidingMenu().setFadeDegree(0.3f);
		this.getSlidingMenu().setFadeEnabled(true);
		this.getSlidingMenu().setBackgroundColor(0xff252525);
		this.getSlidingMenu().setBehindCanvasTransformer(
				new CanvasTransformer() {
					@Override
					public void transformCanvas(final Canvas canvas,
							final float percentOpen) {
						Interpolator interp = new Interpolator() {
							@Override
							public float getInterpolation(float t) {
								t -= 1.0f;
								return t * t * t + 1.0f;
							}
						};
						canvas.translate(
								0,
								canvas.getHeight()
										* (1 - interp
												.getInterpolation(percentOpen)));
					}
				});
	}

	protected void configLeftSlideMenu() {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_container, SlideLeftFragment.newInstance())
				.commit();
	}

	protected void configRightSlideMenu() {
		this.getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame_two, SlideLeftFragment.newInstance())
				.commit();
	}

	/**
	 * Set default action bar view
	 * 
	 * @return ActionBarView
	 */
	protected ActionBarView setDefaultBar() {
		// throw runtime exception if set action bar view second times
		if (actionBar != null)
			throw new RuntimeException("You cannot re set actionbar");
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				getResources().getDimensionPixelSize(
						R.dimen.action_bar_height_button));
		setActionBarView(actionBar = new ActionBarView(this), params);
		return actionBar;
	}

	/**
	 * Set actionbar for home screen
	 * 
	 * @return HomeActionBarView
	 */
	// protected HomeActionBarView setHomeBar() {
	// // throw runtime exception if set action bar view second times
	// if (actionBar != null || homeBar != null)
	// throw new RuntimeException("You cannot re set actionbar");
	// LinearLayout.LayoutParams params = new LayoutParams(
	// LayoutParams.MATCH_PARENT,
	// getResources().getDimensionPixelSize(
	// R.dimen.default_actionbar_height));
	// setActionBarView(homeBar = new HomeActionBarView(this), params);
	// return homeBar;
	// }

	/**
	 * Open progress dialog loading
	 * 
	 * @param text
	 */
	public void showProgressDialog(final String text) {
		if (progressDialog == null) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					progressDialog = ProgressDialog
							.show(self, null, text, true);
					progressDialog.setCancelable(false);
				}
			});

		}
	}

	protected void gotoActivity(Activity activity, Class<?> cls) {
		Intent i = new Intent(activity, cls);
		startActivity(i);
	}

	protected void gotoActivity(Activity activity, Class<?> cls, Bundle b) {
		Intent i = new Intent(activity, cls);
		i.putExtras(b);
		startActivity(i);
	}

	protected void gotoActivityForResult(Activity activity, Class<?> cls,
			int requestCode) {
		Intent i = new Intent(activity, cls);
		startActivityForResult(i, requestCode);
	}

	protected void gotoActivityForResult(Activity activity, Class<?> cls,
			Bundle b, int requestCode) {
		Intent i = new Intent(activity, cls);
		i.putExtras(b);
		startActivityForResult(i, requestCode);
	}

	/**
	 * Close progress dialog loading
	 */
	public void closeProgressDialog() {

		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.cancel();
			progressDialog = null;
		}
	}

	protected void showServerError(final int code) {
		// getCucReApplication().showInformationDialog(getString(R.string.message_server_error_title),
		// getString(R.string.message_server_error_content, code),
		// getString(R.string.text_finished),
		// new DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO force close application
		// }
		// }, false);
	};

	protected void showDialogErrorAndRetry(
			final CucreRequestBaseHandler cucreRequestBaseHandler,
			final BzydroidHttpClient request) {
		// getCucReApplication().showInformationDialog(getString(R.string.message_server_error_title),
		// getString(R.string.message_server_error_content,
		// cucreRequestBaseHandler),
		// getString(R.string.text_finished), new
		// DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// cucreRequestBaseHandler.onRetry(request);
		// }
		// }, false);
	}

	/**
	 * Get Cuc Re application
	 * 
	 * @return CucReApplication
	 */
	protected KidsTVApplication getCucReApplication() {
		return (KidsTVApplication) getApplication();
	}

	protected abstract class CucreRequestBaseHandler implements IRequestHandler {

		public void onRetry(BzydroidHttpClient request) {
			request.sendRequest();
		}

		@Override
		public void onSuccess(BzydroidHttpClient request,
				NetworkResponse networkResponse) {
		}

		@Override
		public void onFinished(BzydroidHttpClient request) {

		}

		@Override
		public void onStarted(BzydroidHttpClient request) {

		}

		@Override
		public void onWait(BzydroidHttpClient absHtttpClient) {

		}

		@Override
		public void onError(BzydroidHttpClient request,
				BzydroidNetworkExeception e) {
			dumpNetworkResponse(e.networkResponse);
			if (e instanceof BzydroidServerException) {
				BzydroidLog.error("Server error: " + request.toString(), e);
				showServerError(e.networkResponse == null ? -1
						: e.networkResponse.statusCode);
			} else if (e instanceof BzydroidProcessResponseException) {
				BzydroidLog.error(
						"Process response error: " + request.toString(), e);
				// showError(e.networkResponse == null ? -1 :
				// e.networkResponse.statusCode);
				// TODO Process process response error
			} else {
				showDialogErrorAndRetry(this, request);
			}
		}

		private void dumpNetworkResponse(NetworkResponse networkResponse) {
			if (networkResponse != null)
				networkResponse.dumpToLog();
		}
	}

	/**
	 * Go Other Activity
	 * 
	 * @param context
	 * @param cls
	 */
	public void goActivity(Context context, Class<?> cls) {
		Intent intent = new Intent(context, cls);
		startActivity(intent);
	}

	/**
	 * Go Other Activity
	 * 
	 * @param context
	 * @param cls
	 * @param key
	 * @param values
	 */
	public void goActivitySearch(Context context, Class<?> cls, String key,
			String values) {
		Intent intent = new Intent(context, cls);
		intent.putExtra(key, values);
		startActivity(intent);
	}

	/**
	 * Go Other Activity
	 * 
	 * @param context
	 * @param cls
	 * @param key
	 * @param values
	 */
	public void goActivitySearch(Context context, Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(context, cls);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * Show soft keyboard
	 * 
	 * @param editText
	 */
	public void showKeyboard(EditText editText) {
		InputMethodManager inputMethodManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null) {
			inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,
					0);
		}
	}

	/**
	 * Hide soft keyboard
	 * 
	 * @param editText
	 */
	public void hideKeyboard(EditText editText) {
		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	// ---------------------TOAST ---------------------------------

	/**
	 * Show short toast message
	 * 
	 * @param str
	 *            alert message
	 */
	public void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	protected void showBoard(EditText yourEditText) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(yourEditText, InputMethodManager.SHOW_IMPLICIT);

	}

	protected void hiddenBoard(EditText yourEditText) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(yourEditText.getWindowToken(), 0);
	}

	protected InfoVideo parseJsonVideo(JSONObject object) {
		InfoVideo infoVideo = null;
		try {
			String id = object.getString(E4KidsConfig.KeyVideo.KEY_ID);
			String idYoutube = object
					.getString(E4KidsConfig.KeyVideo.KEY_ID_YOU);
			String catId = object.getString(E4KidsConfig.KeyVideo.KEY_CATID);
			String title = object.getString(E4KidsConfig.KeyVideo.KEY_TITLE);
			String duration = object
					.getString(E4KidsConfig.KeyVideo.KEY_DURATION);
			String view = object.getString(E4KidsConfig.KeyVideo.KEY_VIEW);
			String avatar = object.getString(E4KidsConfig.KeyVideo.KEY_IMAGE);

			infoVideo = new InfoVideo(id, idYoutube, title, avatar, catId,
					view, duration, "");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return infoVideo;
	}

	protected InfoVideo parseJsonSearch(JSONObject object) {
		InfoVideo infoVideo = null;
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

	protected InfoAlbum parseJsonPla(JSONObject object, String countVideo) {
		InfoAlbum infoAlbum = null;
		try {
			String id = object.getString(E4KidsConfig.KeyPla.KEY_ID);
			String name = object.getString(E4KidsConfig.KeyPla.KEY_NAME);
			String image = object.getString(E4KidsConfig.KeyPla.KEY_IMAGE);

			infoAlbum = new InfoAlbum(id, name, image, countVideo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return infoAlbum;
	}

	/*
	 * Method to share either text or URL.
	 */
	protected void shareTextUrl(String pack) {
		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.setType("text/plain");
		share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

		StringBuilder builderPackage = new StringBuilder();
		builderPackage.append("https://play.google.com/store/apps/details?id=")
				.append(pack);
		
		Debug.info("shareTextUrl:::" + builderPackage.toString() );
		// Add data to the intent, the receiving app will decide
		// what to do with it.
		share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
		share.putExtra(Intent.EXTRA_TEXT, builderPackage.toString());

		startActivity(Intent.createChooser(share, "Share link!"));
	}

}
