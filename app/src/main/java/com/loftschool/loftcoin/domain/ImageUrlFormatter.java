package com.loftschool.loftcoin.domain;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.BuildConfig;

import java.util.Locale;

public final class ImageUrlFormatter {

	@NonNull
	public String format(final int id) {
		return String.format(
			Locale.US,
			"%scoins/64x64/%d.png",
			BuildConfig.CMC_IMG_ENDPOINT,
			id);
	}
}
