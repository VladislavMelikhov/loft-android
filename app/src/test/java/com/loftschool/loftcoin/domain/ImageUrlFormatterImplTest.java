package com.loftschool.loftcoin.domain;

import com.google.common.truth.Truth;
import com.loftschool.loftcoin.BuildConfig;

import org.junit.Before;
import org.junit.Test;

public final class ImageUrlFormatterImplTest {

	private ImageUrlFormatterImpl imageUrlFormatter;

	@Before
	public void setUp() {
		imageUrlFormatter = new ImageUrlFormatterImpl();
	}

	@Test
	public void format() {
		final String url = imageUrlFormatter.format(123L);
		Truth.assertThat(url).isEqualTo(BuildConfig.CMC_IMG_ENDPOINT + "coins/64x64/123.png");
	}
}