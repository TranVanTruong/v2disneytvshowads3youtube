package com.kids.test11.entity;

public class InfoVideo {
    private String vid;
    private String vYoutube;
    private String vName;
    private String vAvatar;
    private String vCatId;
    private String vView;
    private String vDuration;
    private String vAppId;

    public InfoVideo(String vid, String vYoutube, String vName, String vAvatar,
	    String vCatId, String vView, String vDuration , String vAppId) {
	super();
	this.vid = vid;
	this.vYoutube = vYoutube;
	this.vName = vName;
	this.vAvatar = vAvatar;
	this.vCatId = vCatId;
	this.vView = vView;
	this.vDuration = vDuration;
	this.vAppId = vAppId;
    }

    public String getvYoutube() {
	return vYoutube;
    }

    public void setvYoutube(String vYoutube) {
	this.vYoutube = vYoutube;
    }

    public String getvDuration() {
	return vDuration;
    }

    public void setvDuration(String vDuration) {
	this.vDuration = vDuration;
    }

    public String getVid() {
	return vid;
    }

    public void setVid(String vid) {
	this.vid = vid;
    }

    public String getvName() {
	return vName;
    }

    public void setvName(String vName) {
	this.vName = vName;
    }

    public String getvAvatar() {
	return vAvatar;
    }

    public void setvAvatar(String vAvatar) {
	this.vAvatar = vAvatar;
    }

    public String getvCatId() {
        return vCatId;
    }

    public void setvCatId(String vCatId) {
        this.vCatId = vCatId;
    }

    public String getvView() {
	return vView;
    }

    public void setvView(String vView) {
	this.vView = vView;
    }

}
