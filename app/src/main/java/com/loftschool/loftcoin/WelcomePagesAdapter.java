package com.loftschool.loftcoin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;
import java.util.Objects;

public final class WelcomePagesAdapter extends PagerAdapter {

	private static final String PAGE_TITLE = "•";

	@NonNull private final List<WelcomePage> welcomePages;
	@NonNull private final LayoutInflater inflater;

	public WelcomePagesAdapter(@NonNull final List<WelcomePage> welcomePages,
	                           @NonNull final LayoutInflater inflater) {
		this.welcomePages = Objects.requireNonNull(welcomePages);
		this.inflater = Objects.requireNonNull(inflater);
	}

	@Override
	public int getCount() {
		return welcomePages.size();
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull final ViewGroup container,
	                              final int position) {
		final View view = inflater.inflate(R.layout.layout_welcome_page, container, false);
		final WelcomePage welcomePage = welcomePages.get(position);

		container.addView(view, new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.MATCH_PARENT
		));

		view.<AppCompatImageView>findViewById(R.id.iv_welcome_image).setImageResource(welcomePage.getImageId());
		view.<AppCompatTextView>findViewById(R.id.tv_welcome_title).setText(welcomePage.getTitleId());
		view.<AppCompatTextView>findViewById(R.id.tv_welcome_subtitle).setText(welcomePage.getSubTitleId());
		return view;
	}

	@Override
	public void destroyItem(@NonNull final ViewGroup container,
	                        final int position,
	                        @NonNull final Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(@NonNull final View view,
	                                @NonNull final Object object) {
		return Objects.equals(view, object);
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(final int position) {
		return PAGE_TITLE;
	}
}
