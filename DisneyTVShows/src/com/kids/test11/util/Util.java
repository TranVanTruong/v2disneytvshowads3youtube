package com.kids.test11.util;

import java.text.DecimalFormat;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {
    static public String customFormat(String pattern, double value) {
	DecimalFormat myFormatter = new DecimalFormat(pattern);
	String output = myFormatter.format(value);
	System.out.println(value + "  " + pattern + "  " + output);

	return output;
    }

    /**
     * isConnectingToInternet
     * 
     * @param _context
     * @return
     */
    public static boolean isConnectingToInternet(Context _context) {
	ConnectivityManager connectivity = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
	if (connectivity != null) {
	    NetworkInfo[] info = connectivity.getAllNetworkInfo();
	    if (info != null)
		for (int i = 0; i < info.length; i++)
		    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
			return true;
		    }
	}
	return false;
    }
}
