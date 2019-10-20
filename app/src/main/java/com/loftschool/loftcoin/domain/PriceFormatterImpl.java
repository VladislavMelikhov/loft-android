package com.loftschool.loftcoin.domain;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.data.Currencies;

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

	@Override
	public String format(final double value) {
		return format(value, currencies.getCurrent().getSign());
	}

	@NonNull
	public String format(final double value,
	                     @NonNull final String sign) {
		Objects.requireNonNull(sign);
		final Locale locale = localeProvider.get();

		final NumberFormat format = NumberFormat.getCurrencyInstance(locale);
		final DecimalFormat decimalFormat = (DecimalFormat) format;
		final DecimalFormatSymbols symbols = decimalFormat.getDecimalFormatSymbols();
		symbols.setCurrencySymbol(sign);
		decimalFormat.setDecimalFormatSymbols(symbols);
		return format.format(value);
	}
}
