package com.kids.test11;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import com.kids.test11.adapter.AlbumIndexFragmentAdapter;
import com.kids.test11.adapter.SlideShowImageAdapter;
import com.kids.test11.adapter.VideoIndexFragmentAdapter;
import com.kids.test11.customize.CustomGridView;
import com.kids.test11.customize.NotInterceptInPager;
import com.kids.test11.entity.InfoVideo;
import com.kidstv.disneyshows.R;

public class IndexTestActivity extends Activity{
	private VideoIndexFragmentAdapter adapterVideo;
	private AlbumIndexFragmentAdapter adapterAlbum;
	private SlideShowImageAdapter adapterAds;
	
	private NotInterceptInPager viewPager;
	private CustomGridView gvVideo;
	private CustomGridView gvAlbum;
//	private CirclePageIndicator mIndicator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_index);
		
		init();
	}
	
	private void init()
	{
//		viewPager = (NotInterceptInPager)findViewById(R.id.viewpager);
		gvAlbum = (CustomGridView)findViewById(R.id.gvAlbum);
		gvVideo = (CustomGridView)findViewById(R.id.gvVideo);
//		mIndicator = (CirclePageIndicator)findViewById(R.id.index_layout_indicator);
		
		
//		addDataAds();
		addDataVideo();
	}
	

	private void addDataAds()
	{
		ArrayList<InfoVideo> datas = new ArrayList<InfoVideo>();
//		InfoVideo infoVideo1 = new InfoVideo("1", "Co nam", "http://i1.ytimg.com/vi/7IvHexEZ00Y/mqdefault.jpg", "12:12:12", "32323 luot xem");
//		InfoVideo infoVideo2 = new InfoVideo("1", "Co nam", "http://i1.ytimg.com/vi/7IvHexEZ00Y/mqdefault.jpg", "12:12:12", "32323 luot xem");
//		InfoVideo infoVideo3 = new InfoVideo("1", "Co nam", "http://i1.ytimg.com/vi/7IvHexEZ00Y/mqdefault.jpg", "12:12:12", "32323 luot xem");
//		InfoVideo infoVideo4 = new InfoVideo("1", "Co nam", "http://i1.ytimg.com/vi/7IvHexEZ00Y/mqdefault.jpg", "12:12:12", "32323 luot xem");
//		InfoVideo infoVideo5 = new InfoVideo("1", "Co nam", "http://i1.ytimg.com/vi/7IvHexEZ00Y/mqdefault.jpg", "12:12:12", "32323 luot xem");
		
//		datas.add(infoVideo5);
//		datas.add(infoVideo4);
//		datas.add(infoVideo3);
//		datas.add(infoVideo2);
//		datas.add(infoVideo1);
		
		
//		adapterAds = new SlideShowImageAdapter(getApplicationContext());
//		adapterAds.add(infoVideo5);
//		adapterAds.add(infoVideo4);
//		adapterAds.add(infoVideo3);
		
		viewPager.setAdapter(adapterAds);
		
//		mIndicator.setViewPager(viewPager);
	}
	
	private void addDataVideo()
	{
//		ArrayList<InfoVideo> datas = new ArrayList<InfoVideo>();
//		InfoVideo infoVideo1 = new InfoVideo("1", "Co nam", "http://i1.ytimg.com/vi/7IvHexEZ00Y/mqdefault.jpg", "12:12:12", "32323 luot xem");
//		InfoVideo infoVideo2 = new InfoVideo("1", "Co nam", "http://i1.ytimg.com/vi/7IvHexEZ00Y/mqdefault.jpg", "12:12:12", "32323 luot xem");
//		
//		datas.add(infoVideo2);
//		datas.add(infoVideo1);
//		
//		adapterVideo = new VideoIndexFragmentAdapter(getApplicationContext(), datas);
//		gvVideo.setAdapter(adapterVideo);
	}

}
