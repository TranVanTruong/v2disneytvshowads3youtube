package com.kids.test11.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
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
import com.kids.test11.adapter.ItemAlbumRelativeAdapter;
import com.kids.test11.adapter.ItemAlbumRelativeAdapter.IOnclickItemPla;
import com.kids.test11.customize.BaseRequestHandler;
import com.kids.test11.entity.InfoAlbum;
import com.kids.test11.service.RequestService;
import com.kids.test11.util.Debug;
import com.kids.test11.util.Util;
import com.kids.test11.view.ListVideoByPlaActivity;
import com.kidstv.disneyshows.R;

public class PlaylistFragment extends BaseFragment {
    private View root;
//    private ListView lvVideo;
    GridView gr_Video;
    private ItemAlbumRelativeAdapter adapter;
    private int idCat;
    private LinearLayout llLoading;
    private LinearLayout llNotInternet;
    
    private int page = 1;
    
    private boolean isLoading = false;
    
    private RequestPlaylist requestPlaylist;
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
	gr_Video = (GridView) root.findViewById(R.id.gv_video);
	tvNoData = (TextView)root.findViewById(R.id.tvNoData);
	llLoading = (LinearLayout)root.findViewById(R.id.ll_loading);
	llNotInternet = (LinearLayout)root.findViewById(R.id.llNoInternet);
	
	LayoutInflater inflater = getActivity().getLayoutInflater();

	View header = inflater.inflate(R.layout.layout_header, null);

	View footer = inflater.inflate(R.layout.layout_footer, null);

	llLoadingFooter = (LinearLayout) footer
			.findViewById(R.id.llLoadingFooter);
	
	//lvVideo.addHeaderView(header);

	//lvVideo.addFooterView(footer);

	adapter = new ItemAlbumRelativeAdapter(getActivity(), imageLoader , options);
	
	
	idCat = application.getCatID();
	
	if(idCat == -1)
	{
	    idCat = 0;
	}
	
	Debug.debug("CatId View Video : " + idCat);
	
	loadMore();
	
	gr_Video.setAdapter(adapter);
	
	adapter.setOnclickItemPla(new IOnclickItemPla() {
	    
	    @Override
	    public void onclickItem(final String aid) {
		if(aid.equals("6") || aid.equals("12") || aid.equals("18") || aid.equals("22"))
		{
		    if(application.isAds())
		    {
			application.setAds(false);
			 MobileCore.init(getActivity(), E4KidsConfig.MOBICORE, LOG_TYPE.DEBUG);
			        
				MobileCore.showOfferWall(getActivity(), new CallbackResponse() {
				    @Override
				    public void onConfirmation(TYPE arg0) {
					Bundle b = new Bundle();
					
					b.putString(E4KidsConfig.KEY_INTENT_AID, aid);
					gotoActivity(ListVideoByPlaActivity.class, b);
				    }
				});
		    }
		    else
		    {
			 Bundle b = new Bundle();
				
				b.putString(E4KidsConfig.KEY_INTENT_AID, aid);
				gotoActivity(ListVideoByPlaActivity.class, b);
		    }
		   
		}
		else
		{
		    Bundle b = new Bundle();
			
			b.putString(E4KidsConfig.KEY_INTENT_AID, aid);
			gotoActivity(ListVideoByPlaActivity.class, b);
		}
		
		
	    }
	});
	
//	lvVideo.setOnItemClickListener(new OnItemClickListener() {
//
//	    @Override
//	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//		    long arg3) {
//		Intent intent = new Intent(getActivity(), ListVideoByPlaActivity.class);
//		startActivity(intent);
//	    }
//	});
	
	gr_Video.setOnScrollListener(new OnScrollListener() {
	    
	    @Override
	    public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	    }
	    
	    @Override
	    public void onScroll(AbsListView view, int firstVisibleItem,
		    int visibleItemCount, int totalItemCount) {
		if((firstVisibleItem + visibleItemCount) == totalItemCount && isLoading)
		{
		    isLoading = false;
		    page = page + 1;
		    llLoadingFooter.setVisibility(View.VISIBLE);
		    loadMore();
		    
		    Debug.debug("Page Playlist: " + page);
		}	
	    }
	});
    }

    private class RequestPlaylist extends BaseRequestHandler {
	public RequestPlaylist(Activity context, OnClickListener clickTry) {
	    super(context, clickTry);
	}
	@Override
	public void onWait(BzydroidHttpClient arg0) {
	    super.onWait(arg0);
	    
	}
	@Override
	public void onStarted(BzydroidHttpClient arg0) {
	    super.onStarted(arg0);
	    Debug.debug("onStarted");
	}

	@Override
	public void onSuccess(BzydroidHttpClient arg0, NetworkResponse arg1) {
		
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
		    isLoading = true;
		    
		    JSONArray jArrVideo = jObjData.getJSONArray("playlists");

		    int length = jArrVideo.length();

		    for (int i = 0; i < length; i++) {
			JSONObject jObj = jArrVideo.getJSONObject(i);
			String countVideo = jObj.getString("total");
			
			InfoAlbum infoVideo = parseJsonPla(jObj, countVideo);

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
	if(requestPlaylist == null)
	{
	    requestPlaylist = new RequestPlaylist(getActivity(), clickTry);
	}
	RequestService.rqListPlaylist(String.valueOf(application.getCatID()), String.valueOf(page), packageName, requestPlaylist);
    }
    
    public OnClickListener clickTry = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
            loadMore();
        }
    };
}
