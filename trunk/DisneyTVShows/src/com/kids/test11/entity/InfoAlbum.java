package com.kids.test11.entity;

public class InfoAlbum {
	private String aid;
	private String aName;
	private String aAvatar;
	private String countVideo;

	public InfoAlbum() {
		super();
	}

	public InfoAlbum(String aid, String aName, String aAvatar , String countVideo) {
		super();
		this.aid = aid;
		this.aName = aName;
		this.aAvatar = aAvatar;
		this.countVideo = countVideo;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public String getaAvatar() {
		return aAvatar;
	}

	public void setaAvatar(String aAvatar) {
		this.aAvatar = aAvatar;
	}

	public String getCountVideo() {
	    return countVideo;
	}

	public void setCountVideo(String countVideo) {
	    this.countVideo = countVideo;
	}
}
