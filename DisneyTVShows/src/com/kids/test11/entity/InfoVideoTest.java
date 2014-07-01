package com.kids.test11.entity;

public class InfoVideoTest {
	private String vid;
	private String vName;
	private int vAvatar;
	private String vTime;
	private String vView;

	public InfoVideoTest(String vid, String vName, int vAvatar, String vTime,
			String vView) {
		super();
		this.vid = vid;
		this.vName = vName;
		this.vAvatar = vAvatar;
		this.vTime = vTime;
		this.vView = vView;
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

	public int getvAvatar() {
		return vAvatar;
	}

	public void setvAvatar(int vAvatar) {
		this.vAvatar = vAvatar;
	}

	public String getvTime() {
		return vTime;
	}

	public void setvTime(String vTime) {
		this.vTime = vTime;
	}

	public String getvView() {
		return vView;
	}

	public void setvView(String vView) {
		this.vView = vView;
	}

}
