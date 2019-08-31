package com.loftschool.loftcoin.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;

import java.text.NumberFormat;
import java.util.Locale;

public final class PriceFormatter {

	private final Locale locale;

	//TODO: avoid code duplication
	public PriceFormatter(@NonNull final Context context) {
		final LocaleListCompat locales = ConfigurationCompat.getLocales(
			context.getResources().getConfiguration()
		);
		locale = locales.get(0);
	}

	@NonNull
	public String format(final double value) {
		return NumberFormat.getCurrencyInstance(locale).format(value);
	}
}
