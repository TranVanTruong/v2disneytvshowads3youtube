package com.kids.test11;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Application;

import com.kids.test11.entity.CatInfo;

public class KidsTVApplication extends Application {
    private int catID = -1;
    private boolean isIndex = true;

    private JSONArray jArrAdsExit;

    private ArrayList<CatInfo> datas;
    
    private boolean isAds = true;
    
    private ArrayList<CatInfo> datasMenu;
    
    

    public boolean isAds() {
        return isAds;
    }

    public void setAds(boolean isAds) {
        this.isAds = isAds;
    }

    public int getCatID() {
	return catID;
    }

    public void setCatID(int catID) {
	this.catID = catID;
    }

    public boolean isIndex() {
	return isIndex;
    }

    public void setIndex(boolean isIndex) {
	this.isIndex = isIndex;
    }

    public ArrayList<CatInfo> getDatas() {
	return datas;
    }

    public void setDatas(ArrayList<CatInfo> datas) {
	this.datas = datas;
    }

    public JSONArray getjArrAdsExit() {
	return jArrAdsExit;
    }

    public void setjArrAdsExit(JSONArray jArrAdsExit) {
	this.jArrAdsExit = jArrAdsExit;
    }

	public ArrayList<CatInfo> getDatasMenu() {
		return datasMenu;
	}

	public void setDatasMenu(ArrayList<CatInfo> datasMenu) {
		this.datasMenu = datasMenu;
	}



}
