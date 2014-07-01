package com.kids.test11.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;

import com.bzydroid.network.http.NetworkResponse;
import com.bzydroid.network.http.client.BzydroidHttpClient;
import com.bzydroid.network.http.exception.BzydroidNetworkExeception;
import com.bzydroid.network.http.response.HttpJSONObjectResponse;
import com.ironsource.mobilcore.CallbackResponse;
import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.MobileCore.LOG_TYPE;
import com.kids.test11.E4KidsConfig;
import com.kids.test11.SBaseFragment;
import com.kids.test11.adapter.SPlaylistFragmentAdapter;
import com.kids.test11.customize.BaseRequestHandler;
import com.kids.test11.entity.InfoAlbum;
import com.kids.test11.service.RequestService;
import com.kids.test11.util.Debug;
import com.kids.test11.util.Util;
import com.kids.test11.view.ListVideoByPlaActivity;
import com.kidstv.disneyshows.R;

public class SPlaylistFragment extends SBaseFragment {
    private View root;
    // private ListView gvPlayList;
    private GridView gvPlayList;
    // private ItemAlbumRelativeAdapter adapter;

    private SPlaylistFragmentAdapter adapterAlbum;

    private String idCat;
    private LinearLayout llLoading;
    private LinearLayout llNotInternet;

    private int page = 1;

    private boolean isLoading = false;

    private RequestPlaylist requestPlaylist;
    // private LinearLayout llLoadingFooter;
    private TextView tvNoData;

    public static Fragment newInstance(String idCat) {
	SPlaylistFragment fragment = new SPlaylistFragment();
	Bundle bundle = new Bundle();
	bundle.putString("idCatPla", idCat);
	fragment.setArguments(bundle);
	return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	root = inflater.inflate(R.layout.layout_list_relative, null);
	return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	
	idCat = getArguments().getString("idCatPla");
	
	gvPlayList = (GridView) root.findViewById(R.id.gvPlayList);
	tvNoData = (TextView) root.findViewById(R.id.tvNoData);
	llLoading = (LinearLayout) root.findViewById(R.id.ll_loading);
	llNotInternet = (LinearLayout) root.findViewById(R.id.llNoInternet);

	LayoutInflater inflater = getActivity().getLayoutInflater();

	View header = inflater.inflate(R.layout.layout_header, null);

	View footer = inflater.inflate(R.layout.layout_footer, null);

	// llLoadingFooter = (LinearLayout) footer
	// .findViewById(R.id.llLoadingFooter);

	// gvPlayList.addHeaderView(header);

	// gvPlayList.addFooterView(footer);

	// adapter = new ItemAlbumRelativeAdapter(getActivity(), imageLoader ,
	// options);
	adapterAlbum = new SPlaylistFragmentAdapter(getActivity(), imageLoader,
		options);

	// idCat = application.getCatID();
	//
	// if(idCat == -1)
	// {
	// idCat = 0;
	// }

	// Debug.debug("CatId View Video : " + idCat);

	loadMore();

	gvPlayList.setAdapter(adapterAlbum);

	gvPlayList.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		    long arg3) {
		InfoAlbum infoAlbum = adapterAlbum.getItem(arg2);
		if (infoAlbum != null) {
		    final String aid = infoAlbum.getAid();

		    if (aid.equals("6") || aid.equals("12") || aid.equals("18")
			    || aid.equals("22")) {
			if (application.isAds()) {
			    application.setAds(false);
			    MobileCore.init(getActivity(),
				    E4KidsConfig.MOBICORE, LOG_TYPE.PRODUCTION);

			    MobileCore.showOfferWall(getActivity(),
				    new CallbackResponse() {
					@Override
					public void onConfirmation(TYPE arg0) {
					    Bundle b = new Bundle();

					    b.putString(
						    E4KidsConfig.KEY_INTENT_AID,
						    aid);
					    gotoActivity(
						    ListVideoByPlaActivity.class,
						    b);
					}
				    });
			} else {
			    Bundle b = new Bundle();

			    b.putString(E4KidsConfig.KEY_INTENT_AID, aid);
			    gotoActivity(ListVideoByPlaActivity.class, b);
			}

		    } else {
			Bundle b = new Bundle();

			b.putString(E4KidsConfig.KEY_INTENT_AID, aid);
			gotoActivity(ListVideoByPlaActivity.class, b);
		    }
		}
	    }
	});

	// gvPlayList.setOnItemClickListener(new OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	// long arg3) {
	// Intent intent = new Intent(getActivity(),
	// ListVideoByPlaActivity.class);
	// startActivity(intent);
	// }
	// });

	gvPlayList.setOnScrollListener(new OnScrollListener() {

	    @Override
	    public void onScrollStateChanged(AbsListView view, int scrollState) {

	    }

	    @Override
	    public void onScroll(AbsListView view, int firstVisibleItem,
		    int visibleItemCount, int totalItemCount) {
		if ((firstVisibleItem + visibleItemCount) == totalItemCount
			&& isLoading) {
		    isLoading = false;
		    page = page + 1;
		    // llLoadingFooter.setVisibility(View.VISIBLE);
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

			adapterAlbum.addData(infoVideo);
		    }
		    gvPlayList.setVisibility(View.VISIBLE);
		} else {

		    Debug.debug("Het du lieu");
		    isLoading = false;
		    if (adapterAlbum.getCount() <= 0) {
			tvNoData.setVisibility(View.VISIBLE);
			gvPlayList.setVisibility(View.GONE);
			// llLoadingFooter.setVisibility(View.GONE);
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

	    if (!Util.isConnectingToInternet(getActivity())) {
		gvPlayList.setVisibility(View.GONE);
		if (llLoading.getVisibility() == View.VISIBLE) {
		    llLoading.setVisibility(View.GONE);
		    llNotInternet.setVisibility(View.VISIBLE);
		}
	    } else {
		llNotInternet.setVisibility(View.GONE);
		gvPlayList.setVisibility(View.VISIBLE);
	    }
	}

	@Override
	public void onFinished(BzydroidHttpClient arg0) {
	    super.onFinished(arg0);
	    Debug.debug("onFinished");
	    llLoading.setVisibility(View.GONE);
	    // llLoadingFooter.setVisibility(View.GONE);
	}
    }

    private void loadMore() {
	if (requestPlaylist == null) {
	    requestPlaylist = new RequestPlaylist(getActivity(), clickTry);
	}
	RequestService.rqListPlaylist(idCat, String.valueOf(page), packageName,
		requestPlaylist);
    }

    public OnClickListener clickTry = new OnClickListener() {

	@Override
	public void onClick(View v) {
	    loadMore();
	}
    };
}
