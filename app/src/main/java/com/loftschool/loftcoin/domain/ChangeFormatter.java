package com.loftschool.loftcoin.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;

import java.util.Locale;

public final class ChangeFormatter {

	private final Locale locale;

	public ChangeFormatter(@NonNull final Context context) {
		final LocaleListCompat locales = ConfigurationCompat.getLocales(
				context.getResources().getConfiguration()
		);
		locale = locales.get(0);
	}

	@NonNull
	public String format(final double value) {
		return String.format(locale, "%.4f%%", value);
	}
}
