package com.loftschool.loftcoin.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class CurrenciesImpl implements Currencies {

	private static final String KEY_CODE = "code";

	private final Context context;

	@Inject
	CurrenciesImpl(@NonNull final Context context) {
		this.context = Objects.requireNonNull(context);
	}

	@NonNull
	@Override
	public List<Currency> getAvailableCurrencies() {
		return Arrays.asList(Currency.values());
	}

	@Override
	public void setCurrent(@NonNull final Currency currency) {
		Objects.requireNonNull(currency);
		getPreferences()
			.edit()
			.putString(KEY_CODE, currency.getCode())
			.apply();
	}

	@NonNull
	@Override
	public Currency getCurrent() {
		return Currency.valueOf(
			getPreferences().getString(KEY_CODE, Currency.USD.getCode())
		);
	}

	private SharedPreferences getPreferences() {
		return context.getSharedPreferences("currencies", Context.MODE_PRIVATE);
	}
}
