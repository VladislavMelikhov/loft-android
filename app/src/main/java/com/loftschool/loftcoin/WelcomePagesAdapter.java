package com.loftschool.loftcoin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.Objects;

public final class WelcomePagesAdapter extends PagerAdapter {

	private static final int[] IMAGES = {
		R.drawable.welcome_page_1,
		R.drawable.welcome_page_2,
		R.drawable.welcome_page_3
	};

	private static final int[] TITLES = {
		R.string.welcome_page_1_title,
		R.string.welcome_page_2_title,
		R.string.welcome_page_3_title
	};

	private static final int[] SUBTITLES = {
		R.string.welcome_page_1_subtitle,
		R.string.welcome_page_2_subtitle,
		R.string.welcome_page_3_subtitle
	};

	private final LayoutInflater mInflater;

	public WelcomePagesAdapter(@NonNull final LayoutInflater mInflater) {
		this.mInflater = Objects.requireNonNull(mInflater);
	}

	@Override
	public int getCount() {
		return IMAGES.length;
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {
		final View view = mInflater.inflate(R.layout.layout_welcome_page, container, false);

		container.addView(view, new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.MATCH_PARENT
		));

		view.<AppCompatImageView>findViewById(R.id.iv_welcome_image).setImageResource(IMAGES[position]);
		view.<AppCompatTextView>findViewById(R.id.tv_welcome_title).setText(TITLES[position]);
		view.<AppCompatTextView>findViewById(R.id.tv_welcome_subtitle).setText(SUBTITLES[position]);
		return view;
	}

	@Override
	public void destroyItem(@NonNull ViewGroup container,
	                        int position,
	                        @NonNull Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
		return Objects.equals(view, object);
	}
}
