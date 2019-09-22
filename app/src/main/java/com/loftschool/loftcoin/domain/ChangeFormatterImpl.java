package com.loftschool.loftcoin.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import javax.inject.Inject;

public final class ChangeFormatterImpl implements ChangeFormatter {

	private final Locale locale;

	@Inject
	public ChangeFormatterImpl(@NonNull final Fragment fragment) {
		final LocaleListCompat locales = ConfigurationCompat.getLocales(
				fragment.getResources().getConfiguration()
		);
		locale = locales.get(0);
	}

	@NonNull
	@Override
	public String format(final double value) {
		return String.format(locale, "%.4f%%", value);
	}
}
