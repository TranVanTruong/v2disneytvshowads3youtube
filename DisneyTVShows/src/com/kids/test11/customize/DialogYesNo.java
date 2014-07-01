package com.kids.test11.customize;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.kidstv.disneyshows.R;

public class DialogYesNo {
	private TextView tvNotice;
	private Dialog dialog;
	private TextView tvTextLeft;
	private TextView tvTextRight;
	private TextView tvTitle;
	

	public DialogYesNo(Context context) {
		dialog = new Dialog(context);
		init();
	}

	/**
	 * Creates the.
	 * 
	 * @return the dialog
	 */
	public Dialog create() {
		return dialog;
	}
	public void cancel() {
		if (dialog != null) {
			dialog.cancel();
			dialog = null;
		}
	}

	private void init() {
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.layout_dialog_template);
		tvNotice = (TextView) dialog.findViewById(R.id.tvContent);
		tvTextLeft = (TextView) dialog.findViewById(R.id.tvLeft);
		tvTextRight = (TextView) dialog.findViewById(R.id.tvRight);
		tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
		
	}

	// Set Message
	public DialogYesNo setTextNotice(String text) {
		tvNotice
		.setText(text);
		return this;
	}

	// Set Title
	public DialogYesNo setTextTitle(String text) {
		tvTitle.setText(text);
		return this;
	}

	// Set click button Left
	public DialogYesNo setClickButtonLeft(String text,
			OnClickListener onClickListener) {
		tvTextLeft.setText(text);
		
		tvTextLeft.setOnClickListener(onClickListener);
		return this;
	}

	// Set Click Button Right
	public DialogYesNo setClickButtonRight(String text,
			OnClickListener onClickListener) {
		tvTextRight.setText(text);
		tvTextRight.setOnClickListener(onClickListener);
//		cancel();
		return this;
	}

	/**
	 * Set visible button left
	 */
	public DialogYesNo setVisibleBtLeft(boolean isVisible) {
		if (isVisible) {
			tvTextLeft.setVisibility(View.VISIBLE);
		} else {
			tvTextLeft.setVisibility(View.GONE);
		}
		return this;
	}

	/**
	 * Set visible button reight
	 */
	public DialogYesNo setVisibleBtRight(boolean isVisible) {
		if (isVisible) {
			tvTextRight.setVisibility(View.VISIBLE);
		} else {
			tvTextRight.setVisibility(View.GONE);
		}
		return this;
	}

}
