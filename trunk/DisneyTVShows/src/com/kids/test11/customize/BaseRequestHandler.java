package com.kids.test11.customize;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.bzydroid.network.http.AbsRequestHandler;
import com.bzydroid.network.http.NetworkResponse;
import com.bzydroid.network.http.client.BzydroidHttpClient;
import com.bzydroid.network.http.exception.BzydroidNetworkExeception;
import com.kids.test11.util.DialogUtil;
import com.kids.test11.util.Util;
import com.kidstv.disneyshows.R;

public class BaseRequestHandler extends AbsRequestHandler {

    private Activity context;
    private OnClickListener clickTry;

    public BaseRequestHandler(Activity context, OnClickListener clickTry) {
	this.context = context;
	this.clickTry = clickTry;
    }

    @Override
    public void onSuccess(BzydroidHttpClient arg0, NetworkResponse arg1) {
    }

    @Override
    public void onError(BzydroidHttpClient arg0, BzydroidNetworkExeception arg1) {
	super.onError(arg0, arg1);
	try {
	    arg0.interrupt();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	if (Util.isConnectingToInternet(context)) {
	    DialogUtil.dialogNomal(context,
		    context.getString(R.string.dialog_thongbao),
		    context.getString(R.string.dialog_content_error),
		    context.getString(R.string.dialog_boqua),
		    context.getString(R.string.dialog_thulai), onClickLeft,
		    clickTry, true, true);
	}

    }

    @Override
    public void onFinished(BzydroidHttpClient arg0) {
	super.onFinished(arg0);
    }

    @Override
    public void onStarted(BzydroidHttpClient arg0) {
	super.onStarted(arg0);
    }

    @Override
    public void onWait(BzydroidHttpClient arg0) {
	super.onWait(arg0);
    }

    private OnClickListener onClickLeft = new OnClickListener() {
	@Override
	public void onClick(View v) {
	    context.finish();
	}
    };

}
