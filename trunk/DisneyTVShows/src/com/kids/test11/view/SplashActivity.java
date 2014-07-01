package com.kids.test11.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bzydroid.network.http.AbsRequestHandler;
import com.bzydroid.network.http.NetworkResponse;
import com.bzydroid.network.http.client.BzydroidHttpClient;
import com.bzydroid.network.http.exception.BzydroidNetworkExeception;
import com.bzydroid.network.http.response.HttpJSONObjectResponse;
import com.kids.test11.E4KidsConfig;
import com.kids.test11.KidsTVApplication;
import com.kids.test11.entity.CatInfo;
import com.kids.test11.service.RequestService;
import com.kids.test11.util.Debug;
import com.kids.test11.util.Util;
import com.kidstv.disneyshows.R;

public class SplashActivity extends Activity {

	private KidsTVApplication application;
	private ArrayList<CatInfo> datas;
	private String packageName;
	private LinearLayout llNotInternet;
	private Button btTry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash);
		
		packageName = getApplicationContext().getPackageName();
		application = (KidsTVApplication) this.getApplication();
		
		llNotInternet = (LinearLayout) findViewById(R.id.llNoInternet);
		btTry = (Button) findViewById(R.id.btTry);
		
		btTry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Debug.debug("SplashActivity Try...");

				datas = new ArrayList<CatInfo>();

				RequestService.rqListCat(packageName, new RequestListCat());
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!Util.isConnectingToInternet(SplashActivity.this)) {
			llNotInternet.setVisibility(View.VISIBLE);

		} else {
			llNotInternet.setVisibility(View.GONE);

			if (application.getDatas() != null) {
				Debug.debug("Da co du lieu k can phai request!!!!");

				datas = application.getDatas();
				application.setDatas(datas);
				Intent intent = new Intent(SplashActivity.this,
						IndexActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
			} else {
				Debug.debug("Chua du lieu ----> can phai request!!!!");

				datas = new ArrayList<CatInfo>();

				RequestService.rqListCat(packageName, new RequestListCat());
			}
		}
	}

	private class RequestListCat extends AbsRequestHandler {
		@Override
		public void onStarted(BzydroidHttpClient arg0) {
			super.onStarted(arg0);
			Debug.debug("onStarted");
		}

		@Override
		public void onSuccess(BzydroidHttpClient arg0, NetworkResponse arg1) {

			HttpJSONObjectResponse jsonObjectResponse = (HttpJSONObjectResponse) arg0
					.getResponse();

			Debug.debug("Danh sach cat : " + jsonObjectResponse.toString());
			try {
				JSONObject object = jsonObjectResponse.toJSONObject();

				JSONObject jObjData = object
						.getJSONObject(E4KidsConfig.KEY_DATA);

				int code = jObjData.getInt(E4KidsConfig.KEY_CODE);

				if (code == 1) {
					JSONArray jArrCat = jObjData
							.getJSONArray(E4KidsConfig.KEY_LISTCAT);
					int length = jArrCat.length();
					CatInfo catInfo = new CatInfo("-1", "Home");
					datas.add(catInfo);
					for (int i = 0; i < length; i++) {
						JSONObject objectCat = jArrCat.getJSONObject(i);

						String id = objectCat
								.getString(E4KidsConfig.KEY_LISTCAT_ID);

						String name = objectCat
								.getString(E4KidsConfig.KEY_LISTCAT_NAME);

						CatInfo catInfo1 = new CatInfo(id, name);

						datas.add(catInfo1);

					}
					application.setDatas(datas);
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
		}

		@Override
		public void onFinished(BzydroidHttpClient arg0) {
			super.onFinished(arg0);
			Debug.debug("onFinished");
			if (datas.size() > 0) {
				Intent intent = new Intent(SplashActivity.this,
						IndexActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
			}
		}

	}
}
