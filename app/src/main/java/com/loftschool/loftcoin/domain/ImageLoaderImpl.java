package com.loftschool.loftcoin.domain;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public final class ImageLoaderImpl implements ImageLoader {

	@Inject
	public ImageLoaderImpl() {
	}

	@Override
	public void loadImage(@NonNull final String url,
	                      @NonNull final ImageView imageView) {
		Picasso
			.get()
			.load(url)
			.into(imageView);
	}
}
