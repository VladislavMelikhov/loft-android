package com.loftschool.loftcoin.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CurrenciesImpl implements Currencies {

	private static final String KEY_CODE = "code";

	private final SharedPreferences preferences;

	@Inject
	CurrenciesImpl(@NonNull final Context context) {
		Objects.requireNonNull(context);
		this.preferences = context.getSharedPreferences("currencies", Context.MODE_PRIVATE);
	}

	@NonNull
	@Override
	public List<Currency> getAvailableCurrencies() {
		return Arrays.asList(Currency.values());
	}

	@Override
	public void setCurrent(@NonNull final Currency currency) {
		Objects.requireNonNull(currency);
		preferences
			.edit()
			.putString(KEY_CODE, currency.getCode())
			.apply();
	}

	@NonNull
	@Override
	public Currency getCurrent() {
		return Currency.valueOf(
			preferences.getString(KEY_CODE, Currency.USD.getCode())
		);
	}

	@NonNull
	@Override
	public Observable<Currency> current() {
		return Observable.create(emitter -> {
			final SharedPreferences.OnSharedPreferenceChangeListener listener = (prefs, key) -> {
				emitter.onNext(getCurrent());
			};
			emitter.setCancellable(() -> preferences.unregisterOnSharedPreferenceChangeListener(listener));
			preferences.registerOnSharedPreferenceChangeListener(listener);
			emitter.onNext(getCurrent());
		});
	}
}
