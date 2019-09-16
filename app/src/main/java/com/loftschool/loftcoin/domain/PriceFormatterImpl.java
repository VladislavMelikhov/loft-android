package com.loftschool.loftcoin.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;
import androidx.fragment.app.Fragment;

import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;

public final class PriceFormatterImpl implements PriceFormatter {

	private final Locale locale;

	//TODO: avoid code duplication
	@Inject
	public PriceFormatterImpl(@NonNull final Fragment fragment) {
		final LocaleListCompat locales = ConfigurationCompat.getLocales(
			fragment.getResources().getConfiguration()
		);
		locale = locales.get(0);
	}

	@NonNull
	public String format(final double value) {
		return NumberFormat.getCurrencyInstance(locale).format(value);
	}
}
