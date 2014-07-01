package com.kids.test11;

public class E4KidsConfig {
    public static final String API_YOUTUBE = "AIzaSyDt9Lnv1gCU_7gxmtRarZkfQ7ZZn13EXzs";
    
    public static String domain = "http://api.edugames.info";
    
    public static final String MOBICORE = "5IXB360DWXS1RRSQV823TU39S83UT";

    public static final String API_HOME_INDEX = "/?r=home&pack=";
    
    public static final String KEY_INTENT_SEARCH = "keyword";
    public static final String KEY_INTENT_VID = "vid";
    public static final String KEY_INTENT_LINK = "linkvideo";
    public static final String KEY_INTENT_AID = "aid";

    public static final String KEY_VERSION = "version";
    public static final String KEY_DATA = "data";
    public static final String KEY_CODE = "code";

    public static final String API_LISTCAT = "/?r=category&pack=";
    public static final String KEY_LISTCAT_ID = "id";
    public static final String KEY_LISTCAT_NAME = "title";
    public static final String KEY_LISTCAT = "categories";

    public static final class Home {
	public static final String KEY_ADS = "ads";

	public static final String KEY_VIDEO = "videos";

	public static final String KEY_PLAYLIST = "playlists";//Danh sach cat
	
	public static final String KEY_PLAYLIST_INDEX = "playlists_index";//Danh sach playlist
	
	

	public static final String KEY_ADS_EXIT = "ads_exit";

    }

    public static final class KeyVideo {
	public static final String KEY_ID = "id";

	public static final String KEY_ID_YOU = "id_youtube";

	public static final String KEY_CATID = "cat_id";

	public static final String KEY_APPID = "app_id";

	public static final String KEY_TITLE = "title";

	public static final String KEY_DURATION = "duration";

	public static final String KEY_VIEW = "view";

	public static final String KEY_IMAGE = "image";
    }

    public static final class KeyPla {
	public static final String KEY_ID = "id";

	public static final String KEY_NAME = "title";

	public static final String KEY_IMAGE = "image";

    }

    public static final class ListPlayList {
	public static final String API_LIST_PLA = "/?r=playlist";

	public static final String KEY_ROWTER_CATID = "&catid=";

	public static final String KEY_ROWTER_PAGE = "&page=";

	public static final String KEY_ROWTER_PACKAGE = "&pack=";

    }

    public static final class LstVideoByPla {
	public static final String API = "/?r=video/playlist";

	public static final String KEY_ROWTER_PLA_ID = "&plaid=";

	public static final String KEY_ROWTER_PAGE = "&page=";

	public static final String KEY_ROWTER_PACKAGE = "&pack=";
    }

    public static final class ListViewMore {
	public static final String API_VIEW = "/?r=video/topview";

	public static final String KEY_ROWTER_CATID = "&catid=";

	public static final String KEY_ROWTER_PAGE = "&page=";

	public static final String KEY_ROWTER_PACKAGE = "&pack=";
    }

    public static final class ListViewNew {
	public static final String API_NEW = "/?r=video/topnew";

	public static final String KEY_VIDEO = "video";

	public static final String KEY_ROWTER_CATID = "&catid=";

	public static final String KEY_ROWTER_PAGE = "&page=";

	public static final String KEY_ROWTER_PACKAGE = "&pack=";
    }

    public static final class Search {
	public static final String API_SEARCH = "/?r=search";

	public static final String KEY_VIDEO = "videos";

	public static final String KEY_ROWTER_KEYWORD = "&keyword=";

	public static final String KEY_ROWTER_PAGE = "&page=";

	public static final String KEY_ROWTER_PACKAGE = "&pack=";
    }
    
    public static final class ViewTang
    {
	public static final String API = "/?r=view&vid=";
    }

}
