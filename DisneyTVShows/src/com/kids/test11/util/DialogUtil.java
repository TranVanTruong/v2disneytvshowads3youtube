package com.kids.test11.util;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.kids.test11.customize.DialogYesNo;

public class DialogUtil {

	/**
	 * Dialog Nomal
	 * 
	 * @param activity
	 * @param content
	 * @param textCancel
	 * @param textOk
	 * @param onClickListener
	 * @param visileBtLeft
	 * @param visibleRight
	 */
	public static void dialogNomal(final Context activity, String title,
			String content, String textLeft, String textRight,
			final OnClickListener onClickListener, boolean visileBtLeft,
			boolean visibleRight) {
		final DialogYesNo builder = new DialogYesNo(activity);
		builder.setTextNotice(content).setTextTitle(title)
				.setVisibleBtLeft(visileBtLeft).setVisibleBtRight(visibleRight)
				.setClickButtonLeft(textLeft, new OnClickListener() {
					@Override
					public void onClick(View v) {
						builder.create().dismiss();
					}
				}).setClickButtonRight(textRight, new OnClickListener() {
					@Override
					public void onClick(View v) {
						builder.create().dismiss();
						onClickListener.onClick(v);
					}
				});
		builder.create().show();
	}
	
	/**
	 * Dialog Nomal
	 * 
	 * @param activity
	 * @param content
	 * @param textCancel
	 * @param textOk
	 * @param onClickListener
	 * @param visileBtLeft
	 * @param visibleRight
	 */
	public static void dialogNomal(final Context activity, String title,
			String content, String textLeft, String textRight,
			final OnClickListener onClickLeft , final OnClickListener onClickRight, boolean visileBtLeft,
			boolean visibleRight) {
		final DialogYesNo builder = new DialogYesNo(activity);
		builder.setTextNotice(content).setTextTitle(title)
				.setVisibleBtLeft(visileBtLeft).setVisibleBtRight(visibleRight)
				.setClickButtonLeft(textLeft, new OnClickListener() {
					@Override
					public void onClick(View v) {
						builder.create().dismiss();
						onClickLeft.onClick(v);
					}
				}).setClickButtonRight(textRight, new OnClickListener() {
					@Override
					public void onClick(View v) {
						builder.create().dismiss();
						onClickRight.onClick(v);
					}
				});
		builder.create().show();
	}
}
