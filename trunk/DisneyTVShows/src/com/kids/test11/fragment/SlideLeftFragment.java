package com.kids.test11.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bzydroid.network.http.AbsRequestHandler;
import com.bzydroid.network.http.NetworkResponse;
import com.bzydroid.network.http.client.BzydroidHttpClient;
import com.bzydroid.network.http.exception.BzydroidNetworkExeception;
import com.bzydroid.network.http.response.HttpJSONObjectResponse;
import com.bzydroid.utils.BzydroidLog;
import com.kids.test11.BaseFragment;
import com.kids.test11.E4KidsConfig;
import com.kids.test11.E4KidsSlidingMenuActivity;
import com.kids.test11.KidsTVApplication;
import com.kids.test11.adapter.ItemCatAdapter;
import com.kids.test11.entity.CatInfo;
import com.kids.test11.service.RequestService;
import com.kids.test11.util.Debug;
import com.kids.test11.util.ViewUtil;
import com.kids.test11.view.CatDetailActivity;
import com.kids.test11.view.IndexActivity;
import com.kidstv.disneyshows.R;

public class SlideLeftFragment extends BaseFragment {

	private ItemCatAdapter adapter;
	private ListView listView;
	private ScrollView scrollView;
	private View vSelectedIndex;

	private TextView tvIndex;
	private TextView tvInfo;

	private View root;
	private KidsTVApplication application;
	private ArrayList<CatInfo> datas;
	private E4KidsSlidingMenuActivity activity;
	private Dialog dialog;

	public static SlideLeftFragment newInstance() {
		return new SlideLeftFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.layout_slide_listcat, null);
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = (E4KidsSlidingMenuActivity) getActivity();
		listView = (ListView) root.findViewById(R.id.lvListCat);
		scrollView = (ScrollView) root.findViewById(R.id.scrollView);
		vSelectedIndex = (View) root.findViewById(R.id.vSelectedIndex);
		tvIndex = (TextView) root.findViewById(R.id.tvIndex);
		tvInfo = (TextView)root.findViewById(R.id.tvInfo);
		
		application = (KidsTVApplication) getActivity().getApplication();
		if (application.getCatID() == -1) {
			vSelectedIndex.setVisibility(View.VISIBLE);
			tvIndex.setBackgroundColor(getResources().getColor(
					R.color.color_bg_slide_selected));

		} else {
			vSelectedIndex.setVisibility(View.GONE);
			tvIndex.setBackgroundColor(getResources().getColor(
					R.color.color_bg_slide));
		}

		tvIndex.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				application.setCatID(-1);
				if (!application.isIndex()) {
					application.setIndex(true);
					Intent intent = new Intent(getActivity(),
							IndexActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				} else {
					application.setIndex(true);
					activity.getSlidingMenu().toggle();
				}

			}
		});
		
		tvInfo.setOnClickListener(new OnClickListener() {
		    
		    @Override
		    public void onClick(View v) {
//			Debug.debug("CLick Info : ");
			showDialog();
			activity.getSlidingMenu().toggle();
		    }
		});
		
		if(application.getDatas() !=null)
		{
		    Debug.debug("Da co du lieu k can phai request!!!!");
		    
		    datas = application.getDatas();
		    application.setDatas(datas);
			
		    adapter = new ItemCatAdapter(getActivity(), datas, application.getCatID());

		    listView.setAdapter(adapter);
		    
		    ViewUtil.setListViewHeightBasedOnChildren(listView);
		    
		}
		else
		{
		    Debug.debug("Chua du lieu ----> can phai request!!!!");
		    
		    datas = new ArrayList<CatInfo>();
			
		    RequestService.rqListCat(packageName, new RequestListCat());
		}
		
		// scrollView.fullScroll(ScrollView.FOCUS_UP);
		scrollView.smoothScrollTo(0, 0);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (Integer.valueOf(datas.get(arg2).getCatID()) == application
						.getCatID()) {
					activity.getSlidingMenu().toggle();
				} else {
					application.setCatID(Integer.valueOf(datas.get(arg2)
							.getCatID()));

					Intent intent = new Intent(getActivity(),
							CatDetailActivity.class);
					if (!application.isIndex()) {
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						application.setIndex(false);
						startActivity(intent);
					} else {
						application.setIndex(false);
						startActivity(intent);
					}
				}
				// getActivity().finish();
			}
		});
	}

	private void addData() {
		datas = new ArrayList<CatInfo>();

		CatInfo catInfo1 = new CatInfo("1", "Song Kids");
		CatInfo catInfo2 = new CatInfo("2", "English Lesson");
		CatInfo catInfo3 = new CatInfo("3", "English Stories");
		CatInfo catInfo5 = new CatInfo("4", "Cartoon");
		CatInfo catInfo4 = new CatInfo("5", "Funny Clip");
		CatInfo catInfo6 = new CatInfo("6", "The Voice Kids");

		datas.add(catInfo6);
		datas.add(catInfo5);
		datas.add(catInfo4);
		datas.add(catInfo3);
		datas.add(catInfo2);
		datas.add(catInfo1);

		adapter = new ItemCatAdapter(getActivity(), datas,
				application.getCatID());

	}
	
	private class RequestListCat extends AbsRequestHandler
	{
	    @Override
	    public void onStarted(BzydroidHttpClient arg0) {
	        super.onStarted(arg0);
	        BzydroidLog.debug("onStarted");
	    }
	    @Override
	    public void onSuccess(BzydroidHttpClient arg0, NetworkResponse arg1) {
		
		HttpJSONObjectResponse jsonObjectResponse = (HttpJSONObjectResponse)arg0.getResponse();
		
		Debug.debug("Danh sach cat : " + jsonObjectResponse.toString());
		try {
		    JSONObject object = jsonObjectResponse.toJSONObject();
		    
		    JSONObject jObjData = object.getJSONObject(E4KidsConfig.KEY_DATA);
		    
		    int code = jObjData.getInt(E4KidsConfig.KEY_CODE);
		    
		    if(code == 1)
		    {
			JSONArray jArrCat = jObjData.getJSONArray(E4KidsConfig.KEY_LISTCAT);
			int length = jArrCat.length();
			
			for (int i = 0; i < length; i++) {
			    JSONObject objectCat = jArrCat.getJSONObject(i);
			    
			    String id = objectCat.getString(E4KidsConfig.KEY_LISTCAT_ID);
			    
			    String name = objectCat.getString(E4KidsConfig.KEY_LISTCAT_NAME);
			    
			    CatInfo catInfo = new CatInfo(id, name);
			    
			    datas.add(catInfo);
			    
			}
			
			application.setDatas(datas);
			
			adapter = new ItemCatAdapter(getActivity(), datas, application.getCatID());
			
//			addData();

			listView.setAdapter(adapter);

			ViewUtil.setListViewHeightBasedOnChildren(listView);
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
	    }
	    
	    @Override
	    public void onFinished(BzydroidHttpClient arg0) {
	        super.onFinished(arg0);
	        BzydroidLog.debug("onFinished");
	    }
	    
	}
	
	 private void showDialog() {

		if (dialog == null) {
		    dialog = new Dialog(getActivity());
		    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		}

		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(R.layout.layout_info_app);
		
		dialog.show();
	 }
	 
	 private void hidenDialog() {
		if (dialog != null) {
		    dialog.dismiss();
		    dialog = null;
		}
	    }
}
