package com.kids.test11.entity;

public class CatInfo {
	private String CatName;
	private String catID;
	
	public CatInfo(String catID , String catName) {
		super();
		this.catID = catID;
		this.CatName = catName;
	}
	public String getCatName() {
		return CatName;
	}
	public void setCatName(String catName) {
		CatName = catName;
	}
	public String getCatID() {
		return catID;
	}
	public void setCatID(String catID) {
		this.catID = catID;
	}
	
}
