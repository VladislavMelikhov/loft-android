package com.loftschool.loftcoin.domain;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.loftschool.loftcoin.data.Currencies;
import com.loftschool.loftcoin.data.Currency;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

public final class PriceFormatterImpl implements PriceFormatter {

	private final Currencies currencies;
	private final Provider<Locale> localeProvider;

	@Inject
	public PriceFormatterImpl(@NonNull final Currencies currencies,
	                          @NonNull final Provider<Locale> localeProvider) {
		this.currencies = Objects.requireNonNull(currencies);
		this.localeProvider = Objects.requireNonNull(localeProvider);
	}

	@NonNull
	public String format(final double value) {
		final Currency currency = currencies.getCurrent();
		final Locale locale = localeProvider.get();

		final NumberFormat format = NumberFormat.getCurrencyInstance(locale);
		final DecimalFormat decimalFormat = (DecimalFormat) format;
		final DecimalFormatSymbols symbols = decimalFormat.getDecimalFormatSymbols();
		symbols.setCurrencySymbol(currency.getSign());
		decimalFormat.setDecimalFormatSymbols(symbols);
		return format.format(value);
	}
}
