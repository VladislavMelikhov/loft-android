package com.loftschool.loftcoin.domain;

import android.widget.ImageView;

import androidx.annotation.NonNull;

public interface ImageLoader {

	void loadImage(@NonNull String url,
	               @NonNull ImageView imageView);
}