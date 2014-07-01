package com.kids.test11.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.bzydroid.network.http.HttpCachePolicy;
import com.bzydroid.network.http.IRequestHandler;
import com.bzydroid.network.http.client.BzydroidHttpClient;
import com.bzydroid.network.http.response.HttpJSONObjectResponse;
import com.kids.test11.E4KidsConfig;
import com.kids.test11.util.Debug;

public class RequestService {
    
    /**
	 * Home Index
	 * @param requestHandler
	 */
	public static void rqTangView(String vId , IRequestHandler requestHandler)
	{
		String url = E4KidsConfig.ViewTang.API;
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(E4KidsConfig.domain).append(url).append(vId);
		
		BzydroidHttpClient client = new BzydroidHttpClient(
			builder.toString(), requestHandler);
		
		client.setCachePolicy(HttpCachePolicy.NOCACHE);
		client.setResponse(new HttpJSONObjectResponse());
		client.sendRequest();
	}
	/**
	 * Home Index
	 * @param requestHandler
	 */
	public static void rqHomeIndex(String packageName , IRequestHandler requestHandler)
	{
		String url = E4KidsConfig.API_HOME_INDEX;
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(E4KidsConfig.domain).append(url).append(packageName);
		
		Debug.debug("Link API rqHomeIndex :::" + builder.toString());
		
		BzydroidHttpClient client = new BzydroidHttpClient(
			builder.toString(), requestHandler);
		
		client.setCachePolicy(HttpCachePolicy.NOCACHE);
		client.setResponse(new HttpJSONObjectResponse());
		client.sendRequest();
	}
	
	/**
	 * List Category
	 * @param packageName
	 * @param requestHandler
	 */
	public static void rqListCat(String packageName , IRequestHandler requestHandler)
	{
		String url = E4KidsConfig.API_LISTCAT;
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(E4KidsConfig.domain).append(url).append(packageName);
		
		Debug.debug("URL request ::::" + builder.toString());
		
		BzydroidHttpClient client = new BzydroidHttpClient(
				builder.toString(), requestHandler);
		
		client.setCachePolicy(HttpCachePolicy.NOCACHE);
		client.setResponse(new HttpJSONObjectResponse());
		client.sendRequest();
	}
	
	/**
	 * List Playlist by CatId and package
	 * @param catId
	 * @param page
	 * @param packageName
	 * @param requestHandler
	 */
	public static void rqListPlaylist(String catId , String page , String packageName , IRequestHandler requestHandler)
	{
		String url = E4KidsConfig.ListPlayList.API_LIST_PLA;
		
		StringBuilder builder = new StringBuilder();
		
		builder
		.append(E4KidsConfig.domain)
		.append(url)
		.append(E4KidsConfig.ListPlayList.KEY_ROWTER_CATID).append(catId)
		.append(E4KidsConfig.ListPlayList.KEY_ROWTER_PAGE).append(page)
		.append(E4KidsConfig.ListPlayList.KEY_ROWTER_PACKAGE).append(packageName);
		
		Debug.debug("Link Urrl Playlist:::" + builder.toString());
		BzydroidHttpClient client = new BzydroidHttpClient(
				builder.toString(), requestHandler);
		
		client.setCachePolicy(HttpCachePolicy.NOCACHE);
		client.setResponse(new HttpJSONObjectResponse());
		client.sendRequest();
	}
	
	/**
	 * List Views Video by CatId and package
	 * @param catId
	 * @param page
	 * @param packageName
	 * @param requestHandler
	 */
	public static void rqListViewMore(int catId , String page , String packageName , IRequestHandler requestHandler)
	{
		String url = E4KidsConfig.ListViewMore.API_VIEW;
		
		StringBuilder builder = new StringBuilder();
		
		builder
		.append(E4KidsConfig.domain)
		.append(url)
		.append(E4KidsConfig.ListViewMore.KEY_ROWTER_CATID).append(catId)
		.append(E4KidsConfig.ListViewMore.KEY_ROWTER_PAGE).append(page)
		.append(E4KidsConfig.ListViewMore.KEY_ROWTER_PACKAGE).append(packageName);
		
		Debug.debug("Link Url rqListViewMore: " + builder.toString());
		
		BzydroidHttpClient client = new BzydroidHttpClient(
				builder.toString(), requestHandler);
		
		client.setCachePolicy(HttpCachePolicy.NOCACHE);
		client.setResponse(new HttpJSONObjectResponse());
		client.sendRequest();
	}
	
	/**
	 * List Video New by CatId and package
	 * @param catId
	 * @param page
	 * @param packageName
	 * @param requestHandler
	 */
	public static void rqListNew(String catId , String page , String packageName , IRequestHandler requestHandler)
	{
		String url = E4KidsConfig.ListViewNew.API_NEW;
		
		StringBuilder builder = new StringBuilder();
		builder
		.append(E4KidsConfig.domain)
		.append(url)
		.append(E4KidsConfig.ListViewNew.KEY_ROWTER_CATID).append(catId)
		.append(E4KidsConfig.ListViewNew.KEY_ROWTER_PAGE).append(page)
		.append(E4KidsConfig.ListViewNew.KEY_ROWTER_PACKAGE).append(packageName);
		
		Debug.debug("Link Url : " + builder.toString());
		
		BzydroidHttpClient client = new BzydroidHttpClient(
				builder.toString(), requestHandler);
		
		client.setCachePolicy(HttpCachePolicy.NOCACHE);
		client.setResponse(new HttpJSONObjectResponse());
		client.sendRequest();
	}
	
	/**
	 * List Video New by CatId and package
	 * @param catId
	 * @param page
	 * @param packageName
	 * @param requestHandler
	 */
	public static void rqListVideoByPla(String plaId , String page , String packageName , IRequestHandler requestHandler)
	{
		String url = E4KidsConfig.LstVideoByPla.API;
		
		StringBuilder builder = new StringBuilder();
		builder
		.append(E4KidsConfig.domain)
		.append(url)
		.append(E4KidsConfig.LstVideoByPla.KEY_ROWTER_PLA_ID).append(plaId)
		.append(E4KidsConfig.LstVideoByPla.KEY_ROWTER_PAGE).append(page)
		.append(E4KidsConfig.LstVideoByPla.KEY_ROWTER_PACKAGE).append(packageName);
		
		Debug.debug("Link Url : " + builder.toString());
		
		BzydroidHttpClient client = new BzydroidHttpClient(
				builder.toString(), requestHandler);
		
		client.setCachePolicy(HttpCachePolicy.NOCACHE);
		client.setResponse(new HttpJSONObjectResponse());
		client.sendRequest();
	}
	
	
	public static void rqSearch(String keyword , String page , String packageName , IRequestHandler requestHandler)
	{
		String url = E4KidsConfig.Search.API_SEARCH;
		
		try {
		    keyword = URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		}
		
		StringBuilder builder = new StringBuilder();
		builder
		.append(E4KidsConfig.domain)
		.append(url)
		.append(E4KidsConfig.Search.KEY_ROWTER_KEYWORD).append(keyword)
		.append(E4KidsConfig.Search.KEY_ROWTER_PAGE).append(page)
		.append(E4KidsConfig.Search.KEY_ROWTER_PACKAGE).append(packageName);
		
		Debug.debug("Link Url : " + builder.toString());
		
		BzydroidHttpClient client = new BzydroidHttpClient(
				builder.toString(), requestHandler);
		
		client.setCachePolicy(HttpCachePolicy.NOCACHE);
		client.setResponse(new HttpJSONObjectResponse());
		client.sendRequest();
	}
}
