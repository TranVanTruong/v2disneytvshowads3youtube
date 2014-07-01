package com.kids.test11;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.kids.test11.entity.InfoAlbum;
import com.kids.test11.entity.InfoVideo;
import com.kidstv.disneyshows.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class BaseFragment extends Fragment {
    protected KidsTVApplication application;

    protected E4KidsSlidingMenuActivity activity;

    protected DisplayImageOptions options;
    
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    
    protected String packageName;

    @Override
    public void onAttach(Activity activity) {
	super.onAttach(activity);
	packageName = getActivity().getPackageName();
	activity = (E4KidsSlidingMenuActivity) this.getActivity();

	application = (KidsTVApplication) getActivity().getApplication();
	
	File cacheDir = StorageUtils.getOwnCacheDirectory(getActivity(),
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
		getActivity())
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

    protected void gotoActivity(Class<?> cls, Bundle b) {
	Intent i = new Intent(getActivity(), cls);
	i.putExtras(b);
	startActivity(i);
    }

    protected void gotoActivity(Class<?> cls) {
	Intent i = new Intent(getActivity(), cls);
	startActivity(i);
    }
}
