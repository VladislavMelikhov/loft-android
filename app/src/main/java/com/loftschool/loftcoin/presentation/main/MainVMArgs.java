package com.loftschool.loftcoin.presentation.main;

import androidx.annotation.StringRes;

public final class MainVMArgs {

	private final int titleId;

	public MainVMArgs(@StringRes final int titleId) {
		this.titleId = titleId;
	}

	@StringRes
	public int getTitleId() {
		return titleId;
	}
}
