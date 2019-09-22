package com.loftschool.loftcoin.domain;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.loftschool.loftcoin.data.Currencies;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

public final class PriceFormatterImpl implements PriceFormatter {

	private final Currencies currencies;

	@Inject
	public PriceFormatterImpl(@NonNull final Currencies currencies) {
		this.currencies = Objects.requireNonNull(currencies);
	}

	@NonNull
	public String format(final double value) {
		final Pair<Currency, Locale> pair = currencies.getCurrent();
		final Locale locale = Objects.requireNonNull(pair.second);
		return NumberFormat.getCurrencyInstance(locale).format(value);
	}
}
