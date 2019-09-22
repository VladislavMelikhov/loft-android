package com.loftschool.loftcoin.domain;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.BuildConfig;

import java.util.Locale;

import javax.inject.Inject;

public final class ImageUrlFormatterImpl implements ImageUrlFormatter {

	@Inject
	public ImageUrlFormatterImpl() {
	}

	@NonNull
	@Override
	public String format(final int id) {
		return String.format(
			Locale.US,
			"%scoins/64x64/%d.png",
			BuildConfig.CMC_IMG_ENDPOINT,
			id);
	}
}
