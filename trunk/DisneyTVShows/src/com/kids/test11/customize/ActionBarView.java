package com.kids.test11.customize;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kidstv.disneyshows.R;

/**
 * Common Action Bar view
 * 
 * @author HUNGTDO
 * */
public class ActionBarView extends LinearLayout {

	private TextView tvTitleBar;
	private ImageView ivABCancelSearch;
	private ImageView ivABSearch;
	private EditText edtABSearch;
	private LinearLayout llContainEdt;
	private TextView tvABTitle;
	private ImageView ivABLogo;
	private ImageView ivShare;

	public ActionBarView(Context context) {
		super(context);
		init();
	}

	public ActionBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.action_bar, this);
		ivABCancelSearch = (ImageView) findViewById(R.id.iv_cancel);
		ivABSearch = (ImageView) findViewById(R.id.iv_search);
		edtABSearch = (EditText) findViewById(R.id.edt_search);
		llContainEdt = (LinearLayout) findViewById(R.id.ll_contain_edt);
		ivABLogo = (ImageView)findViewById(R.id.iv_logo);
		tvABTitle = (TextView) findViewById(R.id.tv_title);
		ivShare = (ImageView)findViewById(R.id.ivShare);

	}
	
	

	public TextView getTvTitleBar() {
		return tvTitleBar;
	}

	public void setTvTitleBar(TextView tvTitleBar) {
		this.tvTitleBar = tvTitleBar;
	}

	public ImageView getIvABCancelSearch() {
		return ivABCancelSearch;
	}

	public void setIvABCancelSearch(ImageView ivABCancelSearch) {
		this.ivABCancelSearch = ivABCancelSearch;
	}

	public ImageView getIvABSearch() {
		return ivABSearch;
	}

	public void setIvABSearch(ImageView ivABSearch) {
		this.ivABSearch = ivABSearch;
	}

	public EditText getEdtABSearch() {
		return edtABSearch;
	}

	public void setEdtABSearch(EditText edtABSearch) {
		this.edtABSearch = edtABSearch;
	}

	public LinearLayout getLlContainEdt() {
		return llContainEdt;
	}

	public void setLlContainEdt(LinearLayout llContainEdt) {
		this.llContainEdt = llContainEdt;
	}

	public TextView getTvABTitle() {
		return tvABTitle;
	}

	public void setTvABTitle(TextView tvABTitle) {
		this.tvABTitle = tvABTitle;
	}

	public TextView getTextTitle() {
		return tvTitleBar;
	}

	/**
	 * Set title for action bar
	 * 
	 * @param text
	 *            title
	 * */
	public void setTextTitle(String text) {
		this.tvTitleBar.setText(text);
	}

	/**
	 * Set title for action bar
	 * 
	 * @param resid
	 *            Resource id
	 * */
	public void setTextTitle(int resid) {
		this.tvTitleBar.setText(resid);
	}

	public ImageView getIvABLogo() {
	    return ivABLogo;
	}

	public void setIvABLogo(ImageView ivABLogo) {
	    this.ivABLogo = ivABLogo;
	}

	public ImageView getIvShare() {
		return ivShare;
	}

	public void setIvShare(ImageView ivShare) {
		this.ivShare = ivShare;
	}
	
	
}
