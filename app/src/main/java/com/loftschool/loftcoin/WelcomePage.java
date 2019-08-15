package com.loftschool.loftcoin;


import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public final class WelcomePage {

	@DrawableRes private final int imageId;
	@StringRes private final int titleId;
	@StringRes private final int subTitleId;

	public WelcomePage(@DrawableRes final int imageId,
	                   @StringRes final int titleId,
	                   @StringRes final int subTitleId) {
		this.imageId = imageId;
		this.titleId = titleId;
		this.subTitleId = subTitleId;
	}

	@DrawableRes
	public int getImageId() {
		return imageId;
	}

	@StringRes
	public int getTitleId() {
		return titleId;
	}

	@StringRes
	public int getSubTitleId() {
		return subTitleId;
	}
}
