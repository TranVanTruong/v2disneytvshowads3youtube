package com.kids.test11.entity;

public class InfoAds {
    private String id;
    private String title;
    private String image;
    private int type;
    private String idYoutube;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    
    public String getIdYoutube() {
        return idYoutube;
    }
    public void setIdYoutube(String idYoutube) {
        this.idYoutube = idYoutube;
    }
    public InfoAds(String id, String title, String image, int type,
	    String idYoutube) {
	super();
	this.id = id;
	this.title = title;
	this.image = image;
	this.type = type;
	this.idYoutube = idYoutube;
    }
}
