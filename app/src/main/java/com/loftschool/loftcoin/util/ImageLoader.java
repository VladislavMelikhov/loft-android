package com.loftschool.loftcoin.util;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

public final class ImageLoader {

	public void loadImage(@NonNull final String url,
	                      @NonNull final ImageView imageView) {
		Picasso
			.get()
			.load(url)
			.into(imageView);
	}
}
