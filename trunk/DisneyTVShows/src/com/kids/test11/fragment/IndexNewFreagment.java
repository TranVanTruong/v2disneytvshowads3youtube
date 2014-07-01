package com.kids.test11.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bzydroid.network.http.NetworkResponse;
import com.bzydroid.network.http.client.BzydroidHttpClient;
import com.bzydroid.network.http.exception.BzydroidNetworkExeception;
import com.bzydroid.network.http.response.HttpJSONObjectResponse;
import com.ironsource.mobilcore.CallbackResponse;
import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.MobileCore.LOG_TYPE;
import com.kids.test11.BaseFragment;
import com.kids.test11.E4KidsConfig;
import com.kids.test11.adapter.ItemVideoRelativeAdapter;
import com.kids.test11.adapter.ItemVideoRelativeAdapter.IClickItemVideo;
import com.kids.test11.customize.BaseRequestHandler;
import com.kids.test11.entity.InfoVideo;
import com.kids.test11.service.RequestService;
import com.kids.test11.util.Debug;
import com.kids.test11.util.Util;
import com.kids.test11.view.PlayVideo;
import com.kids.test11.view.PlayVideoYoutbe;
import com.kidstv.disneyshows.R;

public class IndexNewFreagment extends BaseFragment{
	private View root;
//	private ListView lvVideo;
	private ItemVideoRelativeAdapter adapter;
	GridView gr_Video;
	private String idCat;
	private LinearLayout llLoading;
	private LinearLayout llNotInternet;
	
	private int page = 1;
	
	private boolean isLoading = false;
	
	private RequestVideoNew requestVideoNew;
	private LinearLayout llLoadingFooter;
	private TextView tvNoData;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.layout_list_relative, null);
		return root;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		gr_Video = (GridView)root.findViewById(R.id.gv_video);
		
		tvNoData = (TextView)root.findViewById(R.id.tvNoData);
		
		llLoading = (LinearLayout)root.findViewById(R.id.ll_loading);
		llNotInternet = (LinearLayout)root.findViewById(R.id.llNoInternet);
		
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View header = inflater.inflate(R.layout.layout_header, null);

		View footer = inflater.inflate(R.layout.layout_footer, null);

		llLoadingFooter = (LinearLayout) footer
				.findViewById(R.id.llLoadingFooter);
		
//		lvVideo.addHeaderView(header);
//
//		lvVideo.addFooterView(footer);
		
		adapter = new ItemVideoRelativeAdapter(getActivity(), imageLoader , options);
		
		idCat = String.valueOf(application.getCatID());
		
		if(idCat.equals("") || idCat.equals("0")|| idCat.equals("-1"))
		{
		    idCat = "0";
		}
		
		Debug.debug("CatId New Video : " + idCat);
		
		loadMore();	
		
		gr_Video.setAdapter(adapter);
		
		adapter.setClickItemVideo(new IClickItemVideo() {
		    
		    @Override
        	    public void onclickItem(final String vid, final String linkvideo) {
			if(vid.equals("4") || vid.equals("8") || vid.equals("14") || vid.equals("20"))
			{
			    if(application.isAds())
			    {
				application.setAds(false);
				MobileCore.init(getActivity(), E4KidsConfig.MOBICORE, LOG_TYPE.DEBUG);
			        
				MobileCore.showOfferWall(getActivity(), new CallbackResponse() {
				    @Override
				    public void onConfirmation(TYPE arg0) {
					Bundle bundle = new Bundle();
		        		bundle.putString(E4KidsConfig.KEY_INTENT_VID, vid);
		        		bundle.putString(E4KidsConfig.KEY_INTENT_LINK, linkvideo);
		        
		        		gotoActivity(PlayVideo.class, bundle);
				    }
				});
			    }
			    else
			    {
				Bundle bundle = new Bundle();
				bundle.putString(E4KidsConfig.KEY_INTENT_VID, vid);
				bundle.putString(E4KidsConfig.KEY_INTENT_LINK, linkvideo);
				
				gotoActivity(PlayVideo.class, bundle);
			    }
			}
			else
			{
			    Bundle bundle = new Bundle();
	        		bundle.putString(E4KidsConfig.KEY_INTENT_VID, vid);
	        		bundle.putString(E4KidsConfig.KEY_INTENT_LINK, linkvideo);
	        
	        		gotoActivity(PlayVideo.class, bundle);
			}
        		
        	    }
		});
		
		if(gr_Video.isSmoothScrollbarEnabled())
		{
		    Debug.debug("Keo duoc listview");
		}
		else
		{
		    Debug.debug("Khong Keo duoc listview");
		}
		
		gr_Video.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			    long arg3) {
			Intent intent = new Intent(getActivity(), PlayVideoYoutbe.class);
			startActivity(intent);
		    }
		});
		
		gr_Video.setOnScrollListener(new OnScrollListener() {
		    
		    @Override
		    public void onScrollStateChanged(AbsListView arg0, int arg1) {
			
		    }
		    
		    @Override
		    public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
			if((arg1 + arg2) == arg3 && isLoading)
			{
			    isLoading = false;
			    page = page + 1;
			    llLoadingFooter.setVisibility(View.VISIBLE);
			    loadMore();
			    
			    Debug.debug("Page New: " + page);
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
		
//		Debug.debug("Danh sach : " + a);
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
		    
		    JSONArray jArrVideo = jObjData.getJSONArray(E4KidsConfig.ListViewNew.KEY_VIDEO);

		    int length = jArrVideo.length();

		    for (int i = 0; i < length; i++) {
			JSONObject jObj = jArrVideo.getJSONObject(i);

			InfoVideo infoVideo = parseJsonVideo(jObj);

			adapter.addData(infoVideo);
		    }
		    gr_Video.setVisibility(View.VISIBLE);
		}
		else
		{
		    
		    Debug.debug("Het du lieu");
		    isLoading = false;
		    if(adapter.getCount() <= 0)
		    {
			tvNoData.setVisibility(View.VISIBLE);
			gr_Video.setVisibility(View.GONE);
			llLoadingFooter.setVisibility(View.GONE);
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
	    
	    if(!Util.isConnectingToInternet(getActivity()))
	    {
	    	gr_Video.setVisibility(View.GONE);
		if(llLoading.getVisibility() == View.VISIBLE)
		{
		    llLoading.setVisibility(View.GONE);
		    llNotInternet.setVisibility(View.VISIBLE);
		}
	    }
	    else
	    {
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
    
    private void loadMore()
    {
	if(requestVideoNew == null)
	{
	    requestVideoNew = new RequestVideoNew(getActivity(), clickTry);
	}
	RequestService.rqListNew(idCat, String.valueOf(page),packageName, requestVideoNew);
    }
    
    private OnClickListener clickTry = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
            Debug.debug("clickTry Page : " + page);
            
            loadMore();
        }
    };
}
