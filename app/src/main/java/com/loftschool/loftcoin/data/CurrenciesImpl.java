package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

public class CurrenciesImpl implements Currencies {

	private final Provider<Locale> localeProvider;

	@Inject CurrenciesImpl(@NonNull final Provider<Locale> localeProvider) {
		this.localeProvider = Objects.requireNonNull(localeProvider);
	}

	@NonNull
	@Override
	public Pair<Currency, Locale> getCurrent() {
		final Locale locale = localeProvider.get();
		return Pair.create(
			Currency.getInstance(locale),
			locale
		);
	}
}
